package com.example.d308mobileapplication.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "vacation")
public class Vacation extends Trip{
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String hotel;

    private String startDate;
    private String endDate;

    public Vacation(){
    }

    public Vacation(int vacationID, String vacationName, String hotel, String startDate, String endDate) {
        super(vacationID, vacationID, vacationName, startDate);
        this.vacationID = vacationID;
        this.hotel = hotel;
        this.startDate = this.getDate();
        this.endDate = endDate;
    }
    @Override
    public void setDate(String date){
        setStartDate(date);
    }
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getDate(){return startDate;}
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public void setVacationID(int vacationID) {
        super.setVacationID(vacationID);
        this.vacationID = vacationID;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

}
