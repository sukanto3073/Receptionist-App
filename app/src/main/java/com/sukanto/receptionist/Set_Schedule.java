package com.sukanto.receptionist;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Set_Schedule extends AppCompatActivity {

    TextView schedule_date_picker,Select_time;
    TextView patientsTV,doctorsTV,selectTV;
    List<patientData> patientDataList;
    ArrayList<String> patientsNames;
    EditText ReasonET;
    ArrayList<Integer> patientsIdLists;
    User_Session_handel user_session_handel;
    ProgressBar Loading_effect;
    String token,tokenString,type,select_schedule_date,select_schedule_time,reason;
    Integer select_doctor_id,select_patient_id;
    Button SubmitBT;
    int user_id;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);
        getSupportActionBar().setTitle("Set Schedule");
        init();
        Loading_effect.setVisibility(View.INVISIBLE);
        ShowDateDialog();
        ShowTimeDialog();


        patientsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (patientsTV.isClickable()){
                    Loading_effect.setVisibility(View.VISIBLE);
                    patientsTV.setClickable(false);
                    get_data_form_api("patients");
                    Log.d(TAG,"Click :true ");
                }
                else {
                    Log.d(TAG,"Click :false ");
                }
            }
        });

        doctorsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (patientsTV.isClickable()){
                    doctorsTV.setClickable(false);
                    Loading_effect.setVisibility(View.VISIBLE);
                    get_data_form_api("doctor");
                    //Log.d(TAG,"Click :true ");
                }
                else {
                   // Log.d(TAG,"Click :false ");
                }
            }

        });

        SubmitBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                reason=ReasonET.getText().toString();
                if (select_doctor_id==null){
                    doctorsTV.setError("select your doctor name");
                }
                else if (select_patient_id==null){
                    patientsTV.setError("select your patient name");
                }
                else if (select_schedule_date==null){
                    schedule_date_picker.setError("select date");
                }
                else if (select_schedule_time==null){
                    selectTV.setError("select time");
                }
                else if (ReasonET.getText().toString().length()==0){
                    ReasonET.setError(" Write Reason");
                }

                else {
                    Loading_effect.setVisibility(View.VISIBLE);
                    SubmitBT.setVisibility(View.INVISIBLE);
                    Call<Schedules_Response> call = Retrofit_Client.getInstance().getApi().set_schedule(tokenString,select_doctor_id,select_patient_id,select_schedule_date,select_schedule_time,reason);
                    call.enqueue(new Callback<Schedules_Response>() {
                        @Override
                        public void onResponse(Call<Schedules_Response> call, Response<Schedules_Response> response) {



                            if (response.isSuccessful()){
                                // Log.d(TAG,"message1"+response.body().toString());
                                SubmitBT.setVisibility(View.VISIBLE);
                                Loading_effect.setVisibility(View.INVISIBLE);
                                Schedules_Response schedules_response=response.body();

                                if(schedules_response.isError()==false){
                                    showCustomDialog();
                                }else{
                                    Toast.makeText(Set_Schedule.this, schedules_response.getMsg(), Toast.LENGTH_LONG).show();
                                    Loading_effect.setVisibility(View.INVISIBLE);
                                    SubmitBT.setVisibility(View.VISIBLE);
                                }

                            }else{
                                Loading_effect.setVisibility(View.INVISIBLE);
                                SubmitBT.setVisibility(View.VISIBLE);
                                Toast.makeText(Set_Schedule.this, response.message(), Toast.LENGTH_LONG).show();
                            }
                        }


                        @Override
                        public void onFailure(Call<Schedules_Response> call, Throwable t) {
                            Toast.makeText(Set_Schedule.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            SubmitBT.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        });


    }

    private void showCustomDialog() {

        androidx.appcompat.app.AlertDialog.Builder dialog=new androidx.appcompat.app.AlertDialog.Builder(this);
        dialog.setMessage("Appointment set successfully");
        dialog.setTitle("Message");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        androidx.appcompat.app.AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
    public void get_data_form_api(String listDataTpe){
        Call<Patients_list_Response> call = Retrofit_Client.getInstance().getApi().patientsList(tokenString,listDataTpe,null);
        call.enqueue(new Callback<Patients_list_Response>() {
            @Override
            public void onResponse(Call<Patients_list_Response> call, Response<Patients_list_Response> response) {
                if (response.isSuccessful()){
                    Patients_list_Response patients_list_response=response.body();
                    patientDataList=patients_list_response.getPatientData();
                    patientsNames.clear();
                    patientsIdLists.clear();
                    for (int i=0;i<patientDataList.size();i++){
                        patientsIdLists.add(patientDataList.get(i).getId());
                        patientsNames.add(patientDataList.get(i).getName()+" ( "+patientDataList.get(i).getMobile()+" )");
                    }
                    patientDialogBox(listDataTpe);
                     //Log.d(TAG,"Different Name: "+patientDataList.get(0).getId());
                }

            }

            @Override
            public void onFailure(Call<Patients_list_Response> call, Throwable t) {
                Toast.makeText(Set_Schedule.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    };

    public void ShowDateDialog() {
        schedule_date_picker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month=month+1;
                        String currentDate= day+"/"+month+"/"+year;
                        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd/mm/yyyy");
                        Date date=null;
                        try {
                            date=simpleDateFormat.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                         schedule_date_picker.setText(simpleDateFormat.format(date));
                        SimpleDateFormat dateChange = new SimpleDateFormat("yyyy-mm-dd");
                        select_schedule_date=dateChange.format(date);

                    }
                };

                Calendar calendar =Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog =new DatePickerDialog(Set_Schedule.this,dateSetListener,year,month,day);
                datePickerDialog.show();

            }
        });
    }

    public void ShowTimeDialog(){
        Select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(Set_Schedule.this);
                View view1 = getLayoutInflater().inflate(R.layout.custom_time_picker,null);
                builder.setView(view1);

                TimePicker time_picker =view1.findViewById(R.id.time_picker);
                Button done_bt = view1.findViewById(R.id.done_bt);
                Dialog dialog = builder.create();
                dialog.show();


                done_bt.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat timeFormat =new SimpleDateFormat("hh:mm aa");
                        int hour= time_picker.getHour();
                        int min= time_picker.getMinute();
                         Time time =new Time(hour,min,0);
                         Select_time.setText(timeFormat.format(time));
                         select_schedule_time=timeFormat.format(time);
                         dialog.dismiss();

                    }
                });




            }
        });


    }


    public  void patientDialogBox(String listDataTpe){

        dialog =new Dialog(Set_Schedule.this);
        dialog.setContentView(R.layout.dilog_searchable_spinner);
        dialog.getWindow().setLayout(900,1000);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Loading_effect.setVisibility(View.INVISIBLE);
        dialog.show();
        patientsTV.setClickable(true);
        doctorsTV.setClickable(true);


        EditText editText =dialog.findViewById(R.id.edit_text);
        ListView listView =dialog.findViewById(R.id.list_item);


        ArrayAdapter<String> adapter= new ArrayAdapter<>(Set_Schedule.this, android.R.layout.simple_list_item_1,patientsNames);
        listView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(listDataTpe.equals("doctor")){
                    select_doctor_id=patientsIdLists.get(i);
                    doctorsTV.setText(adapter.getItem(i));
                    dialog.dismiss();
                    doctorsTV.setClickable(true);
                }else{
                    select_patient_id=patientsIdLists.get(i);
                    patientsTV.setText(adapter.getItem(i));
                    dialog.dismiss();
                    patientsTV.setClickable(true);
                   // Log.d(TAG,"id"+select_patient_id);
                }


            }
        });

    }


    public void init(){
        schedule_date_picker=findViewById(R.id.scheduleDateET);
        patientsTV=findViewById(R.id.patientTV);
        doctorsTV=findViewById(R.id.doctorTV);
        selectTV=findViewById(R.id.selectTitle);
        user_session_handel=new User_Session_handel(getApplicationContext());
        token=user_session_handel.getToken().getAccess_token().toString();
        type = user_session_handel.getToken().getToken_type().toString();
        tokenString = type + " " + token;
        user_id=user_session_handel.getUser().getId();
        patientsNames=new ArrayList<>();
        patientsIdLists=new ArrayList<>();
        Loading_effect=findViewById(R.id.page_loading);
        Select_time=findViewById(R.id.scheduleTimeET);
        SubmitBT=findViewById(R.id.submit_bt);
        ReasonET=findViewById(R.id.reason);
   }


}