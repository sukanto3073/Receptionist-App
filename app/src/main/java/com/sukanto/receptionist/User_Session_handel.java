package com.sukanto.receptionist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class User_Session_handel {
    private  static String SHARED_PREF_NAME="receptionist";
    private SharedPreferences sharedPreferences;
    Context context;
    private  SharedPreferences.Editor editor;

    public User_Session_handel(Context context) {
        this.context = context;
    }

    void saveUser(User user){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("mobile", user.getMobile());
        editor.putString("address", user.getAddress());
        editor.putString("hospital_name", user.getHospital_name());
        editor.putString("jwt_token", user.getJwt_token().getOriginal().getAccess_token());
        editor.putBoolean("logged",true);
        editor.commit();
    }

    public User getUser(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("mobile",null),
                sharedPreferences.getString("address",null),
                sharedPreferences.getString("hospital_name",null),null
        );
    }

    public  void saveToken(Original original){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.putString("access_token", original.getAccess_token());
        editor.putString("token_type", original.getToken_type());
        editor.putFloat("expires_in", original.getExpireIn());

        editor.commit();
    }
    public Original getToken(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new Original(sharedPreferences.getString("access_token",null),
                sharedPreferences.getString("token_type",null),
                sharedPreferences.getFloat("expires_in",-1));
    }

    public void saveRegUserdata(RegUser regUser){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.putInt("reg_user_id", regUser.getId());
        editor.putString("reg_user_name", regUser.getName());
        editor.putString("reg_user_username", regUser.getUsername());
        editor.putString("reg_user_mobile", regUser.getMobile());

        editor.commit();
    }

    public RegUser getRegUserdata(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new RegUser(sharedPreferences.getInt("reg_user_id",-1),
                sharedPreferences.getString("reg_user_name",null),
                sharedPreferences.getString("reg_user_username",null),
                sharedPreferences.getString("reg_user_mobile",null));
    }

    public boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged",false);
    }


    void logout(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public void clearRegData(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.remove("reg_user_id");
        editor.remove("reg_user_name");
        editor.remove("reg_user_username");
        editor.remove("reg_user_mobile");
        editor.commit();
    }



}
