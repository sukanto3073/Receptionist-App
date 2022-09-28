package com.sukanto.receptionist;

import static com.sukanto.receptionist.R.drawable.image_capture;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prescription_Image extends AppCompatActivity {
    ImageView prescriptionImage;
    Button ImageBT;
    EditText doctorName;
    TextView textViewTitle;
    CheckBox checkBox;
    ProgressBar Loading_effect;
    String image,token,type,tokenString;
    int Reg_User_ID;
    User_Session_handel user_session_handel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_image);
        getSupportActionBar().setTitle("Patients Prescription");
        init();
        Loading_effect.setVisibility(View.INVISIBLE);
        ImageBT.setVisibility(View.VISIBLE);
        prescriptionImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Prescription_Image.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(i,0);
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }
            }
        });

        ImageBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Prescription_Image.this,Reg_succes_message.class);

                if(checkBox.isChecked()){
                    startActivity(intent);
                    finish();
                }else if (doctorName.getText().toString().length()==0){
                    doctorName.setError("Enter Dr name");
                }else if(image==null){
                    Toast.makeText(Prescription_Image.this, "please take a photo to upload", Toast.LENGTH_SHORT).show();
                }else {
                    Loading_effect.setVisibility(View.VISIBLE);
                    ImageBT.setVisibility(View.INVISIBLE);

                    Call<Lab_Report_Response> call =Retrofit_Client.getInstance().getApi().PrescriptSubmit(tokenString,Reg_User_ID,image,doctorName.getText().toString(),"prescription");
                    call.enqueue(new Callback<Lab_Report_Response>() {
                        @Override
                        public void onResponse(Call<Lab_Report_Response> call, Response<Lab_Report_Response> response) {
                            if (response.isSuccessful()){
                                Lab_Report_Response lab_report_response=response.body();
                                if (lab_report_response.isError()==false){

                                    Loading_effect.setVisibility(View.INVISIBLE);
                                    SuccessCustomDialog();

                                }else {
                                    ImageBT.setVisibility(View.VISIBLE);
                                    Loading_effect.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Prescription_Image.this, lab_report_response.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                ImageBT.setVisibility(View.VISIBLE);
                                Loading_effect.setVisibility(View.INVISIBLE);
                                Toast.makeText(Prescription_Image.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Lab_Report_Response> call, Throwable t) {
                            Toast.makeText(Prescription_Image.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Loading_effect.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode==0){

                Uri uri =data.getData();

                prescriptionImage.setImageURI(uri);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(Prescription_Image.this.getContentResolver(),uri );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image=encodeBase64Image(bitmap);

                ImageBT.setClickable(true);
                textViewTitle.setText("Your photo is ready to submit");

            }

        }
    }

    private String encodeBase64Image(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

    public void init(){

        prescriptionImage=findViewById(R.id.Image_capture2);
        ImageBT=findViewById(R.id.Submit_bt2);
        textViewTitle=findViewById(R.id.textTitle);
        doctorName=findViewById(R.id.doctorName);
        Loading_effect=findViewById(R.id.page_loading);
        checkBox=findViewById(R.id.CheckBox);
        user_session_handel=new User_Session_handel(getApplicationContext());
        token=user_session_handel.getToken().getAccess_token().toString();
        type = user_session_handel.getToken().getToken_type().toString();
        tokenString = type + " " + token;
        Reg_User_ID= user_session_handel.getRegUserdata().getId();
    }

    public void checkBoxClick(View view) {

        if (checkBox.isChecked()){
            ImageBT.setText("Next");
        }else{
            ImageBT.setText("Submit");
        }
    }

    private void SuccessCustomDialog() {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you want to go next page ?");
        dialog.setTitle("Prescription save successfully");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent =new Intent(Prescription_Image.this,Reg_succes_message.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ImageBT.setVisibility(View.VISIBLE);
                prescriptionImage.setImageBitmap(null);
                textViewTitle.setText("Click camera icon to take photo");
            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        backCustomDialog();
    }
    private void backCustomDialog() {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you want to go lab report ?");
        dialog.setTitle("Alert Message");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent =new Intent(Prescription_Image.this,Lab_Report.class);
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