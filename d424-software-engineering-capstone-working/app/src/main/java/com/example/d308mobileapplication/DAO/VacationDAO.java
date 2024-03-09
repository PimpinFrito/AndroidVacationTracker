package com.example.d308mobileapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308mobileapplication.Model.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM vacation")
    List<Vacation> getAllVacations();

    @Query("DELETE FROM vacation WHERE vacationID = :vacationId")
    void deleteById(int vacationId);

    @Query("SELECT * FROM vacation Where vacationID = :vacationId")
    Vacation getVacation(int vacationId);

    @Query("SELECT * FROM vacation WHERE title LIKE '%' || :title || '%'")
    List<Vacation> searchVacationByTitle(String title);


}
