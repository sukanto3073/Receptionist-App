package com.sukanto.receptionist;


import android.hardware.biometrics.BiometricManager;
import android.os.Build;

import androidx.annotation.RequiresApi;


public class patientData {
    private Integer id;
    private String name;
    private String mobile;
    private String age;
    private String gender;
    private String type;
    private String created_at;


    public patientData(int id, String name, String mobile, String age, String gender, String type, String created_at){
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.age=nullCheck(age);
        this.gender =nullCheck(gender);
        this.type=type;
        this.created_at = created_at;

    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMobile() {
        return mobile;
    }
    public String getAge() {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public String  getCreatedAt() {
        return created_at;
    }

    public String nullCheck(String arg){

        if (!arg.isEmpty() && arg!=null && arg.equals("null")) {
            return arg;
        }else {
            return  "Not Found";
        }
    }


}
