package com.sukanto.receptionist;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;


public class Patient_photo extends Fragment {

    Button Submit_bt,Back_bt;
    TextView hintTxtTX;
    ImageView patientImage,patientImage_file;
    ProgressBar Loading_effect;
    String firstname,lastName,mobile,email,gender,address,date_of_birth,emergency_contact_name,emergency_contact_number,emergency_contact_relation,image,
            token,tokenString,type,currentPhotoPath;;
    User_Session_handel user_session_handel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_photo, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


          init(view);
        user_session_handel =new User_Session_handel(getActivity());
        user_session_handel.clearRegData();

        Bundle bundle=getArguments();
        firstname=bundle.getString("first_name");
        lastName=bundle.getString("last_name");
        mobile=bundle.getString("P_phoneNumber");
        email=bundle.getString("email");
        date_of_birth=bundle.getString("date_of_birth");
        gender=bundle.getString("gender");
        address=bundle.getString("address");
        emergency_contact_name=bundle.getString("authorize_member_name");
        emergency_contact_number=bundle.getString("authorize_member_phone");
        emergency_contact_relation=bundle.getString("authorize_member_relation");



/*
          patientImage.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                      Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(i,0);
                  }
                  else {
                      requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                  }

              }
          });
*/


        patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i,0);
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},0);
                }

            }
        });

        patientImage_file.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(i,1);
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        });


        Submit_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                   Submit_bt.setVisibility(view.INVISIBLE);
                   Back_bt.setVisibility(view.INVISIBLE);
                   Loading_effect.setVisibility(View.VISIBLE);
                    Call<Patient_register_response> call = Retrofit_Client.getInstance().getApi().patient_reg(tokenString,firstname,lastName,mobile,email,gender,address,date_of_birth,emergency_contact_name,emergency_contact_number,emergency_contact_relation,image);
                    call.enqueue(new Callback<Patient_register_response>() {
                        @Override
                        public void onResponse(Call<Patient_register_response> call, Response<Patient_register_response> response) {
                            if (response.isSuccessful()){
                                Patient_register_response  patient_register_response=response.body();

                                if(patient_register_response.getError()==false){

                                    bundle.clear();
                                    user_session_handel.saveRegUserdata(patient_register_response.getData());
                                    Loading_effect.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(),patient_register_response.getMessage(), Toast.LENGTH_LONG).show();

                                  Intent intent =new Intent(getActivity(),Health_History_WebView.class);
                                    Thread thread = new Thread(){
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    thread.start();
                                }else{
                                    Loading_effect.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(), patient_register_response.getMessage(), Toast.LENGTH_LONG).show();
                                    Back_bt.setVisibility(view.VISIBLE);
                                    Submit_bt.setVisibility(view.VISIBLE);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<Patient_register_response> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                            Back_bt.setVisibility(view.INVISIBLE);
                            Submit_bt.setVisibility(view.VISIBLE);
                        }
                    });



            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode==0){

                          Bitmap bitmap = (Bitmap)  data.getExtras().get("data");
                          patientImage.setImageBitmap(bitmap);

                          image=encodeBase64Image(bitmap);
                          //after image take then submit button enable
                          Submit_bt.setClickable(true);

                          hintTxtTX.setText("Your photo is ready to submit");
                        }else if (requestCode==1){
                          Uri uri =data.getData();
                          patientImage_file.setImageURI(uri);
                          Bitmap bitmap = null;
                          try {
                              bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri );
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                          image=encodeBase64Image(bitmap);

                          Submit_bt.setClickable(true);
                          hintTxtTX.setText("Your photo is ready to submit");

                      }


            }

        }


    public void init(View view){
        Submit_bt=view.findViewById(R.id.submit_bt);
        Back_bt=view.findViewById(R.id.back_bt);
        patientImage=view.findViewById(R.id.Image_capture);
        patientImage_file=view.findViewById(R.id.Image_file);
        hintTxtTX=view.findViewById(R.id.hintTxt);
        Loading_effect=view.findViewById(R.id.page_loading);
        user_session_handel=new User_Session_handel(getActivity());
        token=user_session_handel.getToken().getAccess_token().toString();
        type = user_session_handel.getToken().getToken_type().toString();
        tokenString = type + " " + token;
        Submit_bt.setClickable(false);
    }


    private String encodeBase64Image(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

}