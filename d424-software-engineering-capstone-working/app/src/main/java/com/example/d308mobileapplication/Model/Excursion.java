package com.example.d308mobileapplication.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "excursion")
public class Excursion extends Trip{
    @PrimaryKey(autoGenerate = true)
    private int excursionID;

    public Excursion(int excursionID, String title, String date, int vacationID) {
        super(excursionID, vacationID, title, date);
        this.excursionID = excursionID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }


}
