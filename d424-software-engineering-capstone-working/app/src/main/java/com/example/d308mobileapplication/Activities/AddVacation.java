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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d308mobileapplication.AlarmReceiver;
import com.example.d308mobileapplication.Model.DateValidator;
import com.example.d308mobileapplication.Model.Vacation;
import com.example.d308mobileapplication.R;
import com.example.d308mobileapplication.database.Repository;

import java.text.ParseException;
import java.util.Random;

public class AddVacation extends AppCompatActivity {

    private EditText titleEditText;
    private EditText hotelEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;

    private Button submitBtn;
    private int vacationId;
    private Vacation vacation;


    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vacation);

        initInstanceVariables();
        setListeners();

    }

    private void setListeners() {

        submitBtn.setOnClickListener(this::submitVacation);
        startDateEditText.setInputType(InputType.TYPE_NULL);
        startDateEditText.setOnFocusChangeListener((v, isFocused) -> {
            if (isFocused) getDateFromUser(startDateEditText);
        });

        startDateEditText.setOnClickListener( v -> getDateFromUser(startDateEditText));
        endDateEditText.setInputType(InputType.TYPE_NULL);
        endDateEditText.setOnFocusChangeListener((v, isFocused) -> {
            if (isFocused) getDateFromUser(endDateEditText);
        });
        endDateEditText.setOnClickListener( v -> getDateFromUser(endDateEditText));
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
                        //Add 1 to month as dates start from 0 as default
                        month++;
                        String date = String.valueOf(month+"/" + dayOfMonth + "/"+year);
                        textView.setText(date);

                    }
                }
                , defaultYear, defaultMonth, defaultDay);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createVacation();
        setupViews();
    }

    private void setupViews(){
        titleEditText.setText(vacation.getTitle());
        hotelEditText.setText(vacation.getHotel());
        startDateEditText.setText(vacation.getStartDate());
        endDateEditText.setText(vacation.getEndDate());
    }

    private void createVacation(){
        vacationId = getIntent().getIntExtra("vacationId", 0);
        if(vacationId == 0){
            vacation = new Vacation(0,"","","","");
        }else {
            vacation = repository.getVacation(vacationId);
        }
    }
    private void initInstanceVariables() {
        repository = new Repository(this.getApplication());
        titleEditText = findViewById(R.id.vacation_title_editText);
        hotelEditText = findViewById(R.id.hotel_edit_text);
        startDateEditText = findViewById(R.id.StartDate_editText);
        endDateEditText = findViewById(R.id.end_date_editText);
        submitBtn = findViewById(R.id.submit_vacation);
        vacationId = getIntent().getIntExtra("Id", 0);
    }

    public void submitVacation(View v){
        String title = titleEditText.getText().toString();
        String hotel = hotelEditText.getText().toString();
        String startDate = startDateEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();
        boolean isStartDateValid = DateValidator.validDate(startDate);
        boolean isEndDateValid = DateValidator.validDate(endDate);


        if(title.isBlank()){
            Toast.makeText(this, "Title can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isStartDateValid || !isEndDateValid){
            Toast.makeText(this, "Dates must be in MM/DD/YYY format", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean startDateBeforeEndDate;

        try{
             startDateBeforeEndDate = DateValidator.startsBefore(startDate, endDate);
        } catch (ParseException e){
            Toast.makeText(this, "Error checking if start date is before end", Toast.LENGTH_SHORT).show();
            startDateBeforeEndDate = false;
        }
        if(!startDateBeforeEndDate){
            Toast.makeText(this,"Start date must be before end date", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(this, "Dates are valid. Submitting...", Toast.LENGTH_SHORT).show();
        }

        //Update Vacation
        vacation.setTitle(title);
        vacation.setHotel(hotel);
        vacation.setStartDate(startDate);
        vacation.setEndDate(endDate);

        //If id == 0 is a new vacation, insert into db. Else update db
        if (vacationId == 0) {
            repository.insert(vacation);
        } else {
            repository.update(vacation);
        }
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_vacation, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        int itemId = item.getItemId();
        int likelyVacationId = vacationId;

        //get the next available vacation id to give to the excursion, to avoid giving it an id of 0
        if (vacationId == 0){
            Toast.makeText(this,"Must create and submit new vacations first"
                    , Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }

        if(itemId == R.id.excursion_list){
            intent = new Intent(this, ExcursionList.class);
            intent.putExtra("vacationId", likelyVacationId);
            startActivity(intent);
        }
        else if(itemId == R.id.add_excursion){
            intent = new Intent(this, AddExcursion.class);
            intent.putExtra("excursionId", 0);
            intent.putExtra("vacationId", likelyVacationId);
            startActivity(intent);
        } else if(itemId == R.id.delete_vacation){
            deleteVacation();

        } else if(itemId == R.id.alertVacation){
            setAlarm();

        } else if(itemId == R.id.shareVacation){
            shareViaEmail();

        }
        return super.onOptionsItemSelected(item);
    }

    private void setAlarm() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(AddVacation.this,
                        Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddVacation.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                }
            }

            Intent alarmIntentStarting = new Intent(this, AlarmReceiver.class);
            alarmIntentStarting.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            alarmIntentStarting.putExtra("title", vacation.getTitle()
                    + " is starting");

            alarmIntentStarting.putExtra("text", vacation.getTitle() + " began "
                    + vacation.getStartDate());

            Intent alarmIntentEnding = new Intent(this, AlarmReceiver.class);
            alarmIntentEnding.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            alarmIntentEnding.putExtra("title", vacation.getTitle()
                    + " is ending");

            alarmIntentEnding.putExtra("text", vacation.getTitle() + " ends "
                    + vacation.getEndDate());

            //AlarmReceiving occassionally would receive the old data, using random requestIDs
            //Was a simple workaround

            Random random = new Random();
            PendingIntent pendingIntentStarting = PendingIntent.getBroadcast(this,
                    random.nextInt(), alarmIntentStarting, PendingIntent.FLAG_MUTABLE);

            PendingIntent pendingIntentEnding = PendingIntent.getBroadcast(this,
                    random.nextInt(), alarmIntentEnding, PendingIntent.FLAG_MUTABLE);

            String currentDate = DateValidator.getCurrentDate();

            boolean startDateTodayOrFuture = DateValidator.startsBefore(currentDate, vacation.getStartDate());

            if(!startDateTodayOrFuture){
                Toast.makeText(this, "Starting date has already passed", Toast.LENGTH_SHORT).show();
            }

            long startDateInMilliseconds = DateValidator.getDateInMilliseconds(vacation.getStartDate());
            if (vacation.getStartDate().equals(currentDate)) startDateInMilliseconds = System.currentTimeMillis() + 1000;

            boolean endDateTodayOrFuture = DateValidator.startsBefore(currentDate, vacation.getEndDate());
            if(!endDateTodayOrFuture){
                Toast.makeText(this, "Ending date has already passed", Toast.LENGTH_SHORT).show();
            }
            long endDateInMilliseconds = DateValidator.getDateInMilliseconds(vacation.getEndDate());

            if (vacation.getEndDate().equals(currentDate)) endDateInMilliseconds = System.currentTimeMillis() + 4000;



            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                if (startDateTodayOrFuture) alarmManager.set(AlarmManager.RTC_WAKEUP, startDateInMilliseconds, pendingIntentStarting);
                if(endDateTodayOrFuture) alarmManager.set(AlarmManager.RTC_WAKEUP, endDateInMilliseconds, pendingIntentEnding);
            }

        } catch (Exception e) {}
    }

    public void shareViaEmail(){
        String subject = "Vacation details: " + vacation.getTitle();
        String message = "Vacation:" + vacation.getTitle() +
                "\nHotel: " + vacation.getHotel() +
                "\nStart Date: " + vacation.getStartDate() +
                "\nEnd Date: " + vacation.getEndDate();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // MIME type for email
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }

    public void deleteVacation(){
        boolean hasExcursions = repository.hasAssociatedExcursions(vacationId);
        if(hasExcursions){
            Toast.makeText(getApplicationContext(), "Must delete associated excursions first",
                    Toast.LENGTH_SHORT).show();
        } else{
            repository.delete(vacation.getVacationID());
            Toast.makeText(getApplicationContext(), "Vacation deleted",
                    Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}