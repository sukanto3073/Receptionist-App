package com.sukanto.receptionist;

import com.google.gson.annotations.SerializedName;

public class Lab_Report_Response {
    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    public String msg;

    public Lab_Report_Response(boolean error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }
}
