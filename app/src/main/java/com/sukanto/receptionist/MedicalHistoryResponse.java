package com.sukanto.receptionist;

import com.google.gson.annotations.SerializedName;

public class MedicalHistoryResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public String data;

    public MedicalHistoryResponse(boolean error, String msg, String data) {
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    public boolean getError() {
        return this.error;
    }

    public String getMessage() {
        return this.msg;
    }

    public String getData() {
        return this.data;
    }


}
