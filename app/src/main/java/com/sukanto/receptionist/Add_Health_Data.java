package com.sukanto.receptionist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Health_Data extends AppCompatActivity {
    Button NExtBT;
    EditText bloodPressureET,pulseRateET,sugarRateET,temperatureET,heightET,weightET,sleepET;
    ProgressBar Loading_effect;
    String bloodPressure,systolic,diastolic,pulseRate,sugarRate,temperature,height,weight,sleep,
            token,tokenString,type;
    int Reg_User_ID;

    User_Session_handel user_session_handel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_data);
        init();
        Loading_effect.setVisibility(View.INVISIBLE);
        NExtBT.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("Patients Vital Health Data");

        NExtBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllData();

                Log.d("height: ",height);

                if (pulseRateET.equals("")){
                    pulseRateET.setError("Enter pulse rate");
                }
                else if (bloodPressureET.equals("")){
                    bloodPressureET.setError("Enter blood pressure");
                }
                else if (sugarRateET.equals("")){
                    sugarRateET.setError("Enter sugar rate");
                }
                else if (temperatureET.equals("")){
                    temperatureET.setError("Enter temperature");
                }
                else if (heightET.equals("")){
                    heightET.setError("Enter height");
                }
                else if (weightET.equals("")){
                    weightET.setError("Enter weight");
                }
                else if (sleepET.equals("")){
                    sleepET.setError("Enter sleep time");
                }
                else{
                    NExtBT.setVisibility(View.INVISIBLE);
                    Loading_effect.setVisibility(View.VISIBLE);
                    Call<MedicalHistoryResponse> call =Retrofit_Client.getInstance().getApi().addVitalInput(tokenString,Reg_User_ID,systolic,diastolic,pulseRate,temperature,"F",height,weight,"KG",sugarRate,"mg/dl",sleep);
                    call.enqueue(new Callback<MedicalHistoryResponse>() {
                        @Override
                        public void onResponse(Call<MedicalHistoryResponse> call, Response<MedicalHistoryResponse> response) {
                            if (response.isSuccessful()){
                                MedicalHistoryResponse medicalHistoryResponse =response.body();

                                if(medicalHistoryResponse.getError()==false){

                                    Loading_effect.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Add_Health_Data.this, medicalHistoryResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent =new Intent(Add_Health_Data.this,Lab_Report.class);

                                    Thread thread = new Thread(){
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(3200); // As I am using LENGTH_LONG in Toast
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    thread.start();

                                }else {
                                    Toast.makeText(Add_Health_Data.this, medicalHistoryResponse.getMessage(), Toast.LENGTH_LONG).show();
                                   // Log.d("click",medicalHistoryResponse.getMessage());
                                }

                            }else {
                                Toast.makeText(Add_Health_Data.this, response.message(), Toast.LENGTH_LONG).show();
                                //Log.d("click",response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<MedicalHistoryResponse> call, Throwable t) {

                            Toast.makeText(Add_Health_Data.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            //Log.d("click",t.getMessage());
                            NExtBT.setVisibility(View.VISIBLE);
                            Loading_effect.setVisibility(View.INVISIBLE);
                        }
                    });




                }

            }


        });
    }

    public void init(){

        NExtBT=findViewById(R.id.Next_bt);
        Loading_effect=findViewById(R.id.page_loading);
        pulseRateET=findViewById(R.id.pulse_rat_ET);
        bloodPressureET=findViewById(R.id.blood_pressure_ET);
        sugarRateET=findViewById(R.id.blood_glucose_ET);
        temperatureET=findViewById(R.id.temperature_ET);
        heightET=findViewById(R.id.height_ET);
        weightET=findViewById(R.id.weight_Et);
        sleepET=findViewById(R.id.sleep_ET);
        user_session_handel=new User_Session_handel(getApplicationContext());
        token=user_session_handel.getToken().getAccess_token().toString();
        type = user_session_handel.getToken().getToken_type().toString();
        tokenString = type + " " + token;
        Reg_User_ID= user_session_handel.getRegUserdata().getId();
    }

    private void setAllData() {

            pulseRate= pulseRateET.getText().toString();
            bloodPressure= bloodPressureET.getText().toString();
            sugarRate= sugarRateET.getText().toString();
            height= heightET.getText().toString();
            weight= weightET.getText().toString();
            sleep= sleepET.getText().toString();
            temperature=temperatureET.getText().toString();
            String[] parts = bloodPressure. split("/");
            systolic = parts[0]!=null ? parts[0]: "0";
            diastolic = parts[1]!=null ? parts[1]: "0";

    }

    @Override
    public void onBackPressed() {
        showCustomDialog();
    }
    private void showCustomDialog() {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you want to go medical history ?");
        dialog.setTitle("Alert Message");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent =new Intent(Add_Health_Data.this,Health_History_WebView.class);
                startActivity(intent);
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