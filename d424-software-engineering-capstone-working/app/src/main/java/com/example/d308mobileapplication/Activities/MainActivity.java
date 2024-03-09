package com.example.d308mobileapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.d308mobileapplication.R;

public class MainActivity extends AppCompatActivity {

    Button enterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterBtn = findViewById(R.id.enterButton);
        enterBtn.setOnClickListener(v ->{
            Intent intent = new Intent(this, VacationList.class);
            startActivity(intent);
        });
    }
}