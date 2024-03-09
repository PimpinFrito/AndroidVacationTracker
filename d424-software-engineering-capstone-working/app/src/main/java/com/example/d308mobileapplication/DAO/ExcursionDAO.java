package com.example.d308mobileapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308mobileapplication.Model.Excursion;
import com.example.d308mobileapplication.Model.Vacation;

import java.util.List;

@Dao
public interface ExcursionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Query("DELETE FROM excursion WHERE excursionID = :excursionId")
    void deleteExcursionById(int excursionId);


    @Query("SELECT * FROM excursion")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM excursion where excursionID = :excursionId")
    Excursion getExcursion(int excursionId);

    @Query("SELECT * FROM excursion WHERE vacationid = :vacationID;")
    List<Excursion> getVacationExcursions(int vacationID);

    @Query("SELECT EXISTS(SELECT 1 FROM excursion WHERE vacationid = :vacationID LIMIT 1)")
    boolean hasAssociatedExcursions(int vacationID);

    @Query("SELECT * FROM excursion WHERE title LIKE '%' || :title || '%'")
    List<Excursion> searchExcursionsByTitle(String title);



}
