package com.example.d308mobileapplication.database;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.d308mobileapplication.DAO.ExcursionDAO;
import com.example.d308mobileapplication.DAO.VacationDAO;
import com.example.d308mobileapplication.Model.Excursion;
import com.example.d308mobileapplication.Model.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private VacationDAO mVacationDAO;
    private ExcursionDAO mExcursionDAO;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private Vacation mVacation;

    private Excursion mExcursion;

    private boolean mHasExcursions;

    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationDatabaseBuilder db=VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO=db.excursionDAO();
        mVacationDAO=db.vacationDAO();
    }

    public Vacation getVacation(int vacationId) {
        databaseExecutor.execute(() -> {
            mVacation = mVacationDAO.getVacation(vacationId);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mVacation;
    }

    public Excursion getExcursion(int excursionId) {
        databaseExecutor.execute(() -> {
            mExcursion = mExcursionDAO.getExcursion(excursionId);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mExcursion;
    }

    public List<Vacation>getAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }
    public List<Vacation>searchVacationByTitle(String title){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDAO.searchVacationByTitle(title);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }

    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(int vacationId){
        databaseExecutor.execute(()->{
            mVacationDAO.deleteById(vacationId);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Excursion>getAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDAO.getAllExcursions();
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public List<Excursion>searchExcursionsByTitle(String title){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDAO.searchExcursionsByTitle(title);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void deleteExcursionById(int excursionId){
        //int excursionId = excursion.getExcursionID();
        databaseExecutor.execute(()->{
            mExcursionDAO.deleteExcursionById(excursionId);

        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAssociatedExcursions(int vacationID) {
        // Initialize the variable

        databaseExecutor.execute(() -> {
            mHasExcursions = mExcursionDAO.hasAssociatedExcursions(vacationID);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mHasExcursions;  // Return the correct variable
    }
    
    public List<Excursion> getVacationExcursions(int vacationId){
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getVacationExcursions(vacationId);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllExcursions;  // Return the correct variable
    }
}


