package com.sukanto.receptionist;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {


    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    User data;

    public void ResponseLogin() {
    }

    public void ResponseLogin(boolean error, String msg, User data) {
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

    public User getData() {
        return this.data;
    }

}
