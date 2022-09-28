package com.sukanto.receptionist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;

public class Health_History_WebView extends AppCompatActivity {
Button Web_nextBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_history_web_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new WebView()).commit();
        Web_nextBT=findViewById(R.id.web_nextBT);
        getSupportActionBar().setTitle("Patient Health History");
        Web_nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Health_History_WebView.this,Add_Health_Data.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        showCustomDialog();
    }
    private void showCustomDialog() {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you want exit registration process ?");
        dialog.setTitle("Alert Message");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent =new Intent(Health_History_WebView.this,Menu.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}