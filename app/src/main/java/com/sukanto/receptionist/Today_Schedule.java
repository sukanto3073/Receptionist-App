package com.sukanto.receptionist;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Today_Schedule extends Fragment {
    RecyclerView recyclerView;
    TextView titleview;
    List<Schedule_data> schedule_data;
    User_Session_handel user_session_handel;
    String token,tokenString,type,defscheduleType;
    EditText searchET;
    String search;
    int user_id;
    ProgressBar Loading_effect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initializing our variables.
        titleview=view.findViewById(R.id.titleName);
        recyclerView=view.findViewById(R.id.recyclerview);
        searchET=view.findViewById(R.id.search_id);
        Loading_effect=view.findViewById(R.id.page_loading);
        Loading_effect.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        user_session_handel=new User_Session_handel(getActivity());
        schedule_data=new ArrayList<>();
        token=user_session_handel.getToken().getAccess_token().toString();
        type = user_session_handel.getToken().getToken_type().toString();
         tokenString = type + " " + token;
        user_id=user_session_handel.getUser().getId();
        defscheduleType="present";
        search=searchET.getText().toString();
        call_schedule_api(defscheduleType,null);


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
                    schedule_data.clear();
                    search=searchET.getText().toString();
                    call_schedule_api(defscheduleType,search);
                }else if(editable.length()==0){
                    schedule_data.clear();
                    call_schedule_api(defscheduleType,null);
                }

            }
        });
    }

    public void call_schedule_api(String scheduleType, String search){

       // Log.d(TAG,"scheduleType: "+scheduleType);
        this.defscheduleType=scheduleType;
        if(scheduleType.equals("present")){
            titleview.setText("Today Appointments");
        }else if(scheduleType.equals("previous")){
            titleview.setText("Previous Appointments");
        }else{
            titleview.setText("Upcomming Appointments");
        }


        Call<Schedules_Response> call = Retrofit_Client.getInstance().getApi().scheduleList(tokenString,user_id,this.defscheduleType,search);
        Loading_effect.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        call.enqueue(new Callback<Schedules_Response>() {
            @Override
            public void onResponse(Call<Schedules_Response> call, Response<Schedules_Response> response) {

                if (response.isSuccessful()){
                    Schedules_Response schedules_response =response.body();

                    schedule_data=schedules_response.getSchedule_data();

                   /*   for (int i=0;i < schedule_data.size();i++)
                    {
                        Log.d(TAG,"Different Name: "+schedule_data.get(i).getDoctor_name() +" "+schedule_data.get(i).getPatient_name() );
                    }*/

                    recyclerView.setAdapter(new Schedules_adapter(schedule_data,getActivity()));
                }
                Loading_effect.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFailure(Call<Schedules_Response> call, Throwable t) {

            }
        });
    }
}