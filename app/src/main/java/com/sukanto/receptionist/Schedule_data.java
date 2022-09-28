package com.sukanto.receptionist;

public class Schedule_data {

    private  Integer schedule_id;
    private String doctor_name;
    private String patient_name;
    private String status;
    private String reason;
    private String schedule_time;
    private String shedule_date;

    public Schedule_data(Integer schedule_id, String doctor_name, String patient_name, String status, String reason,String schedule_time, String shedule_date) {
        this.schedule_id = schedule_id;
        this.doctor_name = doctor_name;
        this.patient_name = patient_name;
        this.status = status;
        this.reason = reason;
        this.schedule_time=schedule_time;
        this.shedule_date = shedule_date;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_name(Integer schedule_id) {
        this.schedule_id = schedule_id;
    }
    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getSchedule_time() {  return schedule_time;}
    public String getShedule_date() {
        return shedule_date;
    }

    public void setShedule_date(String shedule_date) {
        this.shedule_date = shedule_date;
    }
}
