package com.example.d308mobileapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.d308mobileapplication.Model.Vacation;
import com.example.d308mobileapplication.R;
import com.example.d308mobileapplication.database.Repository;

import java.util.List;

public class VacationList extends AppCompatActivity implements ClickListener {

    Repository repository;
    RecyclerView recyclerView;
    VacationListAdapter vacationListAdapter;

    List<Vacation> vacations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        repository = new Repository(getApplication());

        setupRecyclerView();


    }

    public void setupRecyclerView(){
        vacations = repository.getAllVacations();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        vacationListAdapter = new VacationListAdapter(vacations);
        vacationListAdapter.setClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(vacationListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Enter Vacation Title");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Vacation> filteredList = repository.searchVacationByTitle(newText);
                vacationListAdapter.setList(filteredList);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.home_menuItem){
            this.finish();
        }
        else if(itemId == R.id.add_vacation_menuItem){
            Intent intent;
            intent = new Intent(this, AddVacation.class);
            Toast.makeText(this, "Add Vacation", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else if(itemId == android.R.id.home) {
            this.finish();
        } else if(itemId == R.id.generate_trip_report){
            Intent intent =  new Intent(this, TripReport.class);
            Toast.makeText(this, "Generating Report...", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v, int pos) {
        Vacation vacation = vacations.get(pos);
        Intent intent = new Intent(this, AddVacation.class);
        intent.putExtra("vacationId", vacation.getVacationID());
        intent.putExtra("vacationTitle", vacation.getTitle());
        intent.putExtra("vacationHotel", vacation.getHotel());
        intent.putExtra("startDate", vacation.getStartDate());
        intent.putExtra("endDate", vacation.getEndDate());
        startActivity(intent);
    }
}