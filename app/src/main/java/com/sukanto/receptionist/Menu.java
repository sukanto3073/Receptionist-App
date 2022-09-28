package com.sukanto.receptionist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

TextView Profile_ET,Hospital_ET;
String name,hospital_name;
User_Session_handel user_session_handel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menu");

        user_session_handel =new User_Session_handel(getApplicationContext());
        Profile_ET =findViewById(R.id.profile_name);
        Hospital_ET =findViewById(R.id.hospital);

        name =user_session_handel.getUser().getName();
        hospital_name=user_session_handel.getUser().getHospital_name();
       // hospital_name=user_session_handel.getToken().getAccess_token();

        Profile_ET.setText(name);
        Hospital_ET.setText(hospital_name);

        if (!isConnected()){
            showCustomDialog();
        }

    }

    public void add_patient(View view) {
      Intent  intent = new Intent(Menu.this,Patients_add_Form.class);
        startActivity(intent);

    }
   public void patient_list(View view) {
       Intent  intent = new Intent(Menu.this,Patients_list.class);
       startActivity(intent);

    }
    public void schedule(View view) {
        Intent  intent = new Intent(Menu.this,Set_Schedule.class);
        startActivity(intent);
    }
    public void today_schedule(View view) {
        Intent  intent = new Intent(Menu.this,Schedule_list.class);
        startActivity(intent);

    }

    public void login_bt(View view) {
        user_session_handel.logout();
        Intent  intent = new Intent(Menu.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilecon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (mobilecon != null && mobilecon.isConnected()) || (wificon != null && wificon.isConnected());
    }
    private void showCustomDialog() {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please Check Your Internet Connection");
        dialog.setTitle("Notification");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

}