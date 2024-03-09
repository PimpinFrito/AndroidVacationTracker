package com.example.d308mobileapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.d308mobileapplication.Model.DateValidator;
import com.example.d308mobileapplication.Model.Trip;
import com.example.d308mobileapplication.R;
import com.example.d308mobileapplication.database.Repository;

import java.util.ArrayList;

public class TripReport extends AppCompatActivity{
    ArrayList<Trip> trips;
    Repository repository;
    RecyclerView recyclerView;
    TripListAdapter tripListAdapter;
    TextView reportDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        repository = new Repository(getApplication());
        setupRecyclerView();
        reportDate = findViewById(R.id.report_date);
        String date = DateValidator.getCurrentDate();
        reportDate.setText(String.format("Reported generated on %s", date));
    }

    public void setupRecyclerView(){
        trips = new ArrayList<>();
        trips.addAll(repository.getAllExcursions());
        trips.addAll(repository.getAllVacations());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tripListAdapter = new TripListAdapter(trips);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tripListAdapter);
    }

}