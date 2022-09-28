package com.sukanto.receptionist;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button Log_button;
    EditText Username_ET,Password_ET;
    ProgressBar loading_effect;
    public  String userName,UserPassword;
    User_Session_handel user_session_handel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        if (!isConnected()){
            showCustomDialog();
        }
        user_session_handel= new User_Session_handel(getApplicationContext());
        init();
        loading_effect.setVisibility(View.GONE);

        Log_button.setOnClickListener(view -> {

            UserInput();

            user_login();


        });

    }


    public  void init(){
        Log_button=findViewById(R.id.login_bt);
        Username_ET=findViewById(R.id.username);
        Password_ET=findViewById(R.id.userPassword);
        loading_effect=findViewById(R.id.page_loading);

    }

    private void UserInput() {
        userName = Username_ET.getText().toString();
        UserPassword = Password_ET.getText().toString();
        if (!userName.isEmpty() && !UserPassword.isEmpty()){
            loading_effect.setVisibility(View.VISIBLE);
            Log_button.setVisibility(View.GONE);

        }

    }


public  void user_login(){

    Call<LoginResponse> call = Retrofit_Client.getInstance().getApi().login(userName,UserPassword);
    call.enqueue(new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

            if (response.isSuccessful() && response.body() != null){


                LoginResponse loginResponse =response.body();


                if (loginResponse.getError()==true){
                    loading_effect.setVisibility(View.GONE);
                    Log_button.setVisibility(View.VISIBLE);


                    if (userName.isEmpty()){
                        Username_ET.setError("Enter your username");

                    }else if(!UserPassword.isEmpty()){
                        Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                    }
                    else if (UserPassword.isEmpty()){
                        Password_ET.setError(loginResponse.getMessage());
                    }

                }else{
                    user_session_handel.saveUser(loginResponse.getData());
                    user_session_handel.saveToken(loginResponse.getData().getJwt_token().getOriginal());
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();


                }

            }


        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    });


}
@Override
protected void onStart(){
        super.onStart();
        if (user_session_handel.isLoggedIn()){
            Intent intent = new Intent(MainActivity.this, Menu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
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