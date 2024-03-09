package com.example.d308mobileapplication.Model;

import java.io.Serializable;

public class Trip implements Serializable {

    private int id;
    private  int vacationID;
    private String title;
    private String date;

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public Trip(){}
    public int getVacationId() {
        return vacationID;
    }

    public Trip(int id, int vacationID, String title){
        this.id = id;
        this.vacationID = vacationID;
        this.title = title;
    }

    public Trip(int id, int vacationID, String title, String date){
        this.id = id;
        this.vacationID = vacationID;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
