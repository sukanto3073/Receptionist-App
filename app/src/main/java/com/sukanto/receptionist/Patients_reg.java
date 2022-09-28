package com.sukanto.receptionist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Patients_reg extends Fragment {

    Button Next_bt;
    EditText P_First_name,P_Last_name,P_PhoneNumber,P_Email,Village_Name,
    Union_Name,Upazila_Name,District_Name;
    TextView P_date_Birth;
    String email,gender,select_schedule_date,phone;
     RadioGroup radioGroup;
     RadioButton radioButton_male,radioButton_female;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patients_reg, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gender ="Male";
        init(view);
        ShowDateDialog();
        hideSoftKeyboard(getActivity());

          radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonId) {
                  switch (checkedButtonId){
                      case R.id.radioMale:
                          gender = radioButton_male.getText().toString();
                          break;
                      case R.id.radioFemale:
                          gender = radioButton_female.getText().toString();

                  }

              }
          });








       Next_bt.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("ResourceType")
           @Override
           public void onClick(View view) {

               if (P_First_name.getText().toString().equals("")){
                   P_First_name.setError("Enter your first name");
               }
               else if (P_Last_name.getText().toString().equals("")){
                   P_Last_name.setError("Enter your last name");
               }
              /* else if (P_PhoneNumber.getText().toString().equals("")){
                   P_PhoneNumber.setError("Enter your phone number");
               }*/
               /*else if (  email.isEmpty() && (!Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                   P_Email.setError("Enter your valid email");
               }*/
               else  if (select_schedule_date==null){
                   P_date_Birth.setError("Enter your date of birth");
               }

               else if (Village_Name.getText().toString().equals("")){
                   Village_Name.setError("Enter your village name");
               }
               else if (Union_Name.getText().toString().equals("")){
                   Union_Name.setError("Enter your union name");
               }
               else if (Upazila_Name.getText().toString().equals("")){
                   Upazila_Name.setError("Enter your upazila name");
               }
               else if (District_Name.getText().toString().equals("")){
                   District_Name.setError("Enter your district name");
               }
               else {

                   email=P_Email.getText().toString();
                   phone=P_PhoneNumber.getText().toString();

                   Bundle bundle =new Bundle();
                   bundle.putString("first_name",P_First_name.getText().toString());
                   bundle.putString("last_name",P_Last_name.getText().toString());
                   bundle.putString("phone",phone);
                   bundle.putString("email",email);
                   bundle.putString("date_of_birth",select_schedule_date);
                   bundle.putString("gender",gender);
                   bundle.putString("address",Village_Name.getText().toString()+","+Union_Name.getText().toString()+","+Upazila_Name.getText().toString()+","+District_Name.getText().toString());

                   Authorized_Family_Member authorized_family_member= new Authorized_Family_Member(); // replace your custom fragment class
                   authorized_family_member.setArguments(bundle);
                   FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.replace(R.id.fragment,authorized_family_member);
                   fragmentTransaction.commit();


               }

           }
       });
    }



    public void ShowDateDialog() {
        P_date_Birth.setOnClickListener(new View.OnClickListener() {
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
                        P_date_Birth.setText(simpleDateFormat.format(date));
                        SimpleDateFormat dateChange = new SimpleDateFormat("yyyy-mm-dd");
                        select_schedule_date=dateChange.format(date);

                    }
                };

                Calendar calendar =Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog =new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
                datePickerDialog.show();

            }
        });
    }

    public void init(View view){
        Next_bt=view.findViewById(R.id.next_bt);
        P_First_name=view.findViewById(R.id.First_name);
        P_Last_name=view.findViewById(R.id.Last_name);
        P_PhoneNumber=view.findViewById(R.id.user_phone);
        P_Email=view.findViewById(R.id.user_email);
        P_date_Birth=view.findViewById(R.id.user_date_of_birth);
        radioButton_male=view.findViewById(R.id.radioMale);
        radioButton_female=view.findViewById(R.id.radioFemale);
        Village_Name=view.findViewById(R.id.village_name);
        Union_Name=view.findViewById(R.id.union_name);
        Upazila_Name=view.findViewById(R.id.upazila_name);
        District_Name=view.findViewById(R.id.district_name);
        radioGroup =view.findViewById(R.id.radio);


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }


}