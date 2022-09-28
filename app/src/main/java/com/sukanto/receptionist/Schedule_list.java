package com.sukanto.receptionist;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Schedule_list extends AppCompatActivity {
    Button today,previous,upcoming;
    EditText searchET;
    String search;
    Today_Schedule today_schedule;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        getSupportActionBar().setTitle("Schedule list");

         init();

        today_schedule = new Today_Schedule();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,today_schedule).commit();





         today.setOnClickListener(new View.OnClickListener() {

             @SuppressLint("ResourceAsColor")
             @Override
             public void onClick(View view) {
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment,today_schedule).commit();
                 today_schedule.call_schedule_api("present",null);

             }
         });
        previous.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,today_schedule).commit();

                today_schedule.call_schedule_api("previous",null);
            }
        });
        upcoming.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,today_schedule).commit();
                today_schedule.call_schedule_api("upcoming",null);

            }
        });


    }
    public void init(){
        today=findViewById(R.id.today);
        previous=findViewById(R.id.previous);
        upcoming=findViewById(R.id.upcoming);
        searchET=findViewById(R.id.search_id);

    }
}