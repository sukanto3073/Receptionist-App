package com.sukanto.receptionist;

import com.google.gson.annotations.SerializedName;

public class Patient_register_response {
    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    RegUser data;


    public void ResponseLogin(boolean error, String msg, RegUser data) {
        this.error = error;
        this.msg = msg;
        this.data=data;
    }

    public boolean getError() {
        return this.error;
    }

    public String getMessage() {
        return this.msg;
    }

    public RegUser getData() {
        return this.data;
    }


}
