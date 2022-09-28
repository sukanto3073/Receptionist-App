package com.sukanto.receptionist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Schedules_Response {
    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    private List<Schedule_data> data;

    public Schedules_Response(boolean error, String msg, List<Schedule_data> data) {
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Schedule_data> getSchedule_data() {
        return data;
    }

    public void setSchedule_data(List<Schedule_data> schedule_data) {
        this.data = schedule_data;
    }
}
