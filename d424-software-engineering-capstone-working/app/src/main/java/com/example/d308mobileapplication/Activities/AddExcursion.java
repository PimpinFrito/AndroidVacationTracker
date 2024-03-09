package com.example.d308mobileapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d308mobileapplication.AlarmReceiver;
import com.example.d308mobileapplication.Model.DateValidator;
import com.example.d308mobileapplication.Model.Excursion;
import com.example.d308mobileapplication.Model.Vacation;
import com.example.d308mobileapplication.R;
import com.example.d308mobileapplication.database.Repository;

import java.util.Random;

public class AddExcursion extends AppCompatActivity {

    EditText titleEditText;
    EditText dateEditText;
    Button submitBtn;

    Excursion excursion;

    int vacationId;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_excursion);
        initInstanceVariables();
    }

    private void setListeners(){
        submitBtn.setOnClickListener(v -> submitExcursion());
        dateEditText.setOnFocusChangeListener((v, isFocused) -> {
            if (isFocused) getDateFromUser(dateEditText);
        });
        dateEditText.setOnClickListener( v -> getDateFromUser(dateEditText));

    }

    public void setVariables(){
        titleEditText.setText(excursion.getTitle());
        dateEditText.setText(excursion.getDate());
    }

    private void createExcursion() {
        int excursionId = getIntent().getIntExtra("excursionId",0);
        vacationId = getIntent().getIntExtra("vacationId", 0);
        if(excursionId == 0){
            excursion = new Excursion(0,"","",vacationId);
        } else {
            excursion = repository.getExcursion(excursionId);
        }
    }

    private void initInstanceVariables(){
        repository = new Repository(getApplication());
        titleEditText = findViewById(R.id.excursion_title_editText);
        dateEditText = findViewById(R.id.excursion_date_editText);
        dateEditText.setInputType(InputType.TYPE_NULL);
        submitBtn = findViewById(R.id.submit_excursion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createExcursion();
        setVariables();
        setListeners();
    }

    public void submitExcursion(){
        excursion.setTitle(titleEditText.getText().toString());
        excursion.setDate(dateEditText.getText().toString());

        //ValidateDate creates and shows the toast with what went wrong
        boolean dateValid = validateDate();
        if(!dateValid) return;

        if(excursion.getExcursionID() == 0){
            repository.insert(excursion);
        } else {
            repository.update(excursion);
        }
        Toast.makeText(this, "Adding excursion...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ExcursionList.class);
        intent.putExtra("vacationId", excursion.getVacationID());
        startActivity(intent);
    }

    private void getDateFromUser(TextView textView) {
        String[] currentDate = DateValidator.getCurrentDate().split("/");
        int defaultYear = Integer.parseInt(currentDate[2]);
        int defaultMonth = Integer.parseInt(currentDate[0]) -1;
        int defaultDay = Integer.parseInt(currentDate[1]);
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(AddExcursion.this, (defaultYear + " " +defaultMonth + " " + defaultDay), Toast.LENGTH_LONG).show();
                        //Add 1 to month as dates start from 0 as default
                        month++;
                        String date = String.valueOf(month+"/" + dayOfMonth + "/"+year);
                        textView.setText(date);

                    }
                }
                , defaultYear, defaultMonth, defaultDay);
        dialog.show();
    }


    public boolean validateDate(){
        //Date Validation begins
        boolean validDate = DateValidator.validDate(excursion.getDate());
        if(!validDate){
            Toast.makeText(this, "Dates must be in MM/DD/YYY format", Toast.LENGTH_SHORT).show();
            return false;
        }
        Vacation vacation = repository.getVacation(excursion.getVacationID());
        String vacationStartDate = vacation.getStartDate();
        String vacationEndDate = vacation.getEndDate();
        boolean dateWithinVacation = DateValidator.dateWithinConstraints(vacationStartDate,
                vacationEndDate, excursion.getDate());
        if(!dateWithinVacation){
            Toast.makeText(this,"Must be within: " + vacationStartDate + " and: " + vacationEndDate,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_excursion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(excursion.getDate().isEmpty() || excursion.getTitle().isEmpty()){
            Toast.makeText(this, "Must submit excursion with  date first!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(itemId == R.id.delete_excursion){
            repository.deleteExcursionById(excursion.getExcursionID());
            backToParent();
        } else if(itemId == R.id.alert_excursion){
                setAlarm();
                //Toast.makeText(this, "Push notification set for date", Toast.LENGTH_SHORT).show();
        } else if(itemId == android.R.id.home){
            backToParent();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAlarm() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(AddExcursion.this,
                        Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddExcursion.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                }
            }

            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            alarmIntent.putExtra("title", excursion.getTitle()
                    + " is starting");

            alarmIntent.putExtra("text", excursion.getTitle() + " began "
                    + excursion.getDate());


            Random random = new Random();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    random.nextInt(), alarmIntent, PendingIntent.FLAG_MUTABLE);

            String currentDate = DateValidator.getCurrentDate();

            boolean dateTodayOrFuture = DateValidator.startsBefore(currentDate, excursion.getDate());

            if (!dateTodayOrFuture) {
                Toast.makeText(this, "Date has already passed", Toast.LENGTH_SHORT).show();
            }

            long dateInMilliseconds = DateValidator.getDateInMilliseconds(excursion.getDate());
            if (excursion.getDate().equals(currentDate))
                dateInMilliseconds = System.currentTimeMillis() + 1000;

            //Toast.makeText(this, alarmIntent.getStringExtra("title"), Toast.LENGTH_SHORT).show();

            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && dateTodayOrFuture) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, dateInMilliseconds, pendingIntent);
            }
        } catch (Exception e) {}
    }

    public void backToParent() {
        Toast.makeText(this, "Completed. Going back...", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}