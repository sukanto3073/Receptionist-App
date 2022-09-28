package com.sukanto.receptionist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Patients_add_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_add_form);
        getSupportActionBar().setTitle("Patients Registration");


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Patients_reg()).commit();
    }
}