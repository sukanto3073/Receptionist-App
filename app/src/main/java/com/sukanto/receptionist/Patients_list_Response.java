package com.sukanto.receptionist;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Patients_list_Response{

    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    private List<patientData> data;

    public Patients_list_Response(boolean error, String msg, List<patientData> data) {
        this.error = error;
        this.msg = msg;
        this.data=data;
    }

    public boolean getError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }

    public List<patientData> getPatientData() {
        return data;
    }

}
