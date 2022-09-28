package com.sukanto.receptionist;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Authorized_Family_Member extends Fragment {
    Button Next_bt,Back_bt;
    EditText F_first_name,F_last_name,F_phone,Relation;
    String firstname,lastName,mobile,email,gender,address,date_of_birth,F_mobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        firstname=bundle.getString("first_name");
        lastName=bundle.getString("last_name");
        mobile=bundle.getString("phone");
        email=bundle.getString("email");
        date_of_birth=bundle.getString("date_of_birth");
        gender=bundle.getString("gender");
        address=bundle.getString("address");
        bundle.clear();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authorized__family__member, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);


        Next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (F_first_name.getText().toString().equals("")){
                    F_first_name.setError("Enter your first name");
                }
                else if (F_last_name.getText().toString().equals("")){
                    F_last_name.setError("Enter your last name");
                }
                /*else if (F_phone.getText().toString().equals("")){
                    F_phone.setError("Enter your phone number");
                }*/
                else  if (Relation.getText().toString().equals("")){
                    Relation.setError("Enter your date of birth");
                }
                else {

                    F_mobile=F_phone.getText().toString();

                    Bundle newBundle= new Bundle();
                    newBundle.putString("first_name",firstname);
                    newBundle.putString("last_name",lastName);
                    newBundle.putString("P_phoneNumber",mobile);
                    newBundle.putString("email",email);
                    newBundle.putString("date_of_birth",date_of_birth);
                    newBundle.putString("gender",gender);
                    newBundle.putString("address",address);
                    newBundle.putString("authorize_member_name",F_first_name.getText().toString()+" "+F_last_name.getText().toString());
                    newBundle.putString("authorize_member_phone",F_mobile);
                    newBundle.putString("authorize_member_relation",Relation.getText().toString());

            /*        Patient_photo patient_photo =new Patient_photo();
                    patient_photo.setArguments(newBundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment,new Patient_photo()).commit();*/

                    Patient_photo patient_photo =new Patient_photo(); // replace your custom fragment class
                    patient_photo.setArguments(newBundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.fragment,patient_photo);
                    fragmentTransaction.commit();
                }

            }
        });

        Back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment,new Patients_reg()).commit();
            }
        });



    }

    public void init(View view){
        F_first_name=view.findViewById(R.id.first_name);
        F_last_name=view.findViewById(R.id.last_name);
        F_phone=view.findViewById(R.id.family_phone);
        Relation=view.findViewById(R.id.relation);
        Next_bt=view.findViewById(R.id.next_bt);
        Back_bt=view.findViewById(R.id.back_bt);
    }

}