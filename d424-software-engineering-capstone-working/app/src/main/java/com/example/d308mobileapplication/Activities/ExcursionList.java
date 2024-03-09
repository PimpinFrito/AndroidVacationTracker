package com.example.d308mobileapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.d308mobileapplication.Model.Excursion;
import com.example.d308mobileapplication.Model.Vacation;
import com.example.d308mobileapplication.R;
import com.example.d308mobileapplication.database.Repository;

import java.util.List;
import java.util.Objects;

public class ExcursionList extends AppCompatActivity implements ClickListener{
    Repository repository;
    RecyclerView recyclerView;

    ExcursionListAdapter excursionListAdapter;

    ImageButton imageButton;
    List<Excursion> excursions;
    int vacationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_list);

        repository = new Repository(getApplication());
        imageButton = findViewById(R.id.imageButtonExcursion);
        imageButton.setOnClickListener(v-> {
            goToCreateExcursionActivity();
        });

        setupRecyclerView();
    }

    private void goToCreateExcursionActivity() {
        Intent intent = new Intent(this, AddExcursion.class);
        intent.putExtra("vacationId", vacationId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    public void setupRecyclerView(){
        vacationId = getIntent().getIntExtra("vacationId", 3);
        excursions = repository.getVacationExcursions(vacationId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        excursionListAdapter = new ExcursionListAdapter(excursions);
        excursionListAdapter.setClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(excursionListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.excursion_list_menu, menu);
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
                List<Excursion> filteredList = repository.searchExcursionsByTitle(newText);
                excursionListAdapter.setList(filteredList);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.add_excursion_menu){
            goToCreateExcursionActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v, int pos) {
        Excursion excursion = excursions.get(pos);
        Intent intent = new Intent(this, AddExcursion.class);
        intent.putExtra("excursionId", excursion.getExcursionID());
        intent.putExtra("vacationId", excursion.getVacationID());
        startActivity(intent);
    }
}