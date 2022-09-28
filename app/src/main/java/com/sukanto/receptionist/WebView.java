package com.sukanto.receptionist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebView extends Fragment {
    android.webkit.WebView mywebView;
    int Reg_User_ID;
    String token,type,tokenString;
    ProgressBar Loading_effect;
    User_Session_handel user_session_handel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Loading_effect=view.findViewById(R.id.page_loading);
        user_session_handel =new User_Session_handel(getActivity());
        Reg_User_ID= user_session_handel.getRegUserdata().getId();
        token=user_session_handel.getToken().getAccess_token().toString();
        type = user_session_handel.getToken().getToken_type().toString();
        tokenString = type + " " + token;
        Loading_effect.setVisibility(View.VISIBLE);

       Call<MedicalHistoryResponse> call =Retrofit_Client.getInstance().getApi().getWebUrl(tokenString,Reg_User_ID);
       call.enqueue(new Callback<MedicalHistoryResponse>() {
           @Override
           public void onResponse(Call<MedicalHistoryResponse> call, Response<MedicalHistoryResponse> response) {

               if (response.isSuccessful()){
                   MedicalHistoryResponse medicalHistoryResponse=response.body();
                   webViewInit(view,medicalHistoryResponse.getData());
                   Loading_effect.setVisibility(View.INVISIBLE);
               }else{
                   Log.d("callmsg: ",response.message());
               }
           }

           @Override
           public void onFailure(Call<MedicalHistoryResponse> call, Throwable t) {
               Log.d("callmsg: ",t.getMessage());
               Loading_effect.setVisibility(View.INVISIBLE);
           }
       });


    }

    public void webViewInit( View view, String url){
        mywebView=view.findViewById(R.id.webView);
        //String url = "https://dev.kambaiihealth.com/MedicalHistory_view_for_app/718/739";
        mywebView.getSettings().setJavaScriptEnabled(true);
        mywebView.getSettings().setDomStorageEnabled(true);
        mywebView.loadUrl(url);






    }




}




