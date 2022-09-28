package com.sukanto.receptionist;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Patients_list extends AppCompatActivity{
    RecyclerView recyclerView;
    List<patientData> patientDataList;
    EditText searchET;
    ProgressBar Loading_effect;
    String search;
    User_Session_handel user_session_handel;
    private RecyclerView.Adapter patients_adapter;
     public String token,type,tokenString,listType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        getSupportActionBar().setTitle("Patients list");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        init();
        Loading_effect.setVisibility(View.GONE);

        get_patients_data_from_api(tokenString,listType,null);


        searchET.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


          }

          @Override
          public void afterTextChanged(Editable editable) {
              if (editable.length()>2){
                  patientDataList.clear();
                  search=searchET.getText().toString();
                  get_patients_data_from_api(tokenString,listType,search);
              }else if(editable.length()==0){
                  patientDataList.clear();
                  get_patients_data_from_api(tokenString,listType,null);
              }
          }
      });




    }

    public void init(){
        user_session_handel =new User_Session_handel(getApplicationContext());
        searchET=findViewById(R.id.search_id);
        Loading_effect=findViewById(R.id.page_loading);
        patientDataList=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        // initializing our variables.
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchET.clearFocus();
        token=user_session_handel.getToken().getAccess_token().toString();
        type=user_session_handel.getToken().getToken_type().toString();
        tokenString=type+" "+token;
        listType="patients";


    }

    public void  get_patients_data_from_api(String tokenString, String listType,String search){
        Call<Patients_list_Response> call =Retrofit_Client.getInstance().getApi().patientsList(tokenString,listType,search);
        Loading_effect.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        call.enqueue(new Callback<Patients_list_Response>() {
            @Override
            public void onResponse(Call<Patients_list_Response> call, Response<Patients_list_Response> response) {
                if (response.isSuccessful()){
                    Patients_list_Response patients_list_response =response.body();
                    patientDataList.clear();
                    patientDataList=patients_list_response.getPatientData();

                    if(patientDataList.size()>0 && patients_list_response.getError()==false){
                       patients_adapter = new Patients_Adapter(Patients_list.this, patientDataList, new Patients_Adapter.ItemClick() {
                           @Override
                           public void onItemClick(patientData position) {
                               String pos =position.getName();
                               Log.d(TAG,"patent ID: "+pos);
                               Intent  intent = new Intent(Patients_list.this,Patient_Information.class);
                               startActivity(intent);
                           }
                       });
                        recyclerView.setAdapter(patients_adapter);
                    }else{
                        patientDataList.clear();
                        patients_adapter = new Patients_Adapter(Patients_list.this,patientDataList,null);
                        recyclerView.setAdapter(patients_adapter);


                        Toast.makeText(Patients_list.this, "No Patient Found!", Toast.LENGTH_LONG).show();

                    }
                    Loading_effect.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                        //Toast.makeText(Patients_list.this, "No Patient Found!", Toast.LENGTH_LONG).show();
                       // Log.d(TAG,"Different Name: "+patientDataList.get(0));


                   /* for (int i=0;i < patientDataList.size();i++)
                    {
                       // Log.d(TAG,"Different Name: "+patientDataList.get(i).getName() +" "+patientDataList.get(i).getAge() );
                      //  Log.d(TAG,"Different Name: "+search);
                    }*/




                }


            }

            @Override
            public void onFailure(Call<Patients_list_Response> call, Throwable t) {
                Toast.makeText(Patients_list.this, "Server response Error!", Toast.LENGTH_LONG).show();
            }
        });
    }


}