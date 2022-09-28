package com.sukanto.receptionist;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Client {


    private  static  String BASE_URL="https://dev.kambaiihealth.com/api/";
    private static  Retrofit_Client retrofit_client;
    private  static Retrofit retrofit;

    private  Retrofit_Client(){
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static  synchronized Retrofit_Client getInstance(){
        if (retrofit_client==null){
            retrofit_client =new Retrofit_Client();
        }
        return retrofit_client;
    }

    public   Retrofit_Service getApi(){
        return retrofit.create(Retrofit_Service.class);
    }
}
