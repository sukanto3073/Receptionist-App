package com.sukanto.receptionist;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Retrofit_Service {

    /*  patients login Data get Api  */
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    Call<LoginResponse> login (@Field("username") String username, @Field("password") String password);


    /*  patients userList Data get Api  */
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("health_care_patient_and_doctor_list")
    Call<Patients_list_Response> patientsList(
            @Header("Authorization")String tokenString,
            @Field("type") String listType,
            @Field("search_keyword") String search
            ) ;

    /*  Schedule userList Data get Api  */
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("provider-schedule_list")
    Call<Schedules_Response> scheduleList(
            @Header("Authorization") String tokenString,
            @Field("user_id") Integer user_id,
            @Field("schedule_type") String schedule_type,
              @Field("search_keyword") String search
             );

    /*  Schedule set  Api  */
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("set-provider-schedule")
    Call<Schedules_Response> set_schedule(
            @Header("Authorization") String tokenString,
            @Field("doctor_id") Integer doctor_id,
            @Field("patient_id") Integer patient_id,
            @Field("scheduled_date") String scheduled_date,
            @Field("scheduled_time") String scheduled_time,
            @Field("reason") String reason
    );

    /*  patients Register inputData  Api  */
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("health_care_patient_register")
    Call<Patient_register_response> patient_reg(
            @Header("Authorization") String tokenString,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("gender") String gender,
            @Field("address") String address,
            @Field("date_of_birth") String date_of_birth,
            @Field("emergency_contact_name") String emergency_contact_name,
            @Field("emergency_contact_number") String emergency_contact_number,
            @Field("emergency_contact_relation") String emergency_contact_relation,
            @Field("image") String image

    );

    @Headers("Accept: application/json")
    @GET("MedicalHistory_view_for_app/{RegUserId}")
    Call<MedicalHistoryResponse> getWebUrl(@Header("Authorization") String tokenString,@Path("RegUserId") int Reg_User_ID);

    /*  patients Medical History inputData  Api  */
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("GeneralVitalInput")
    Call<MedicalHistoryResponse> addVitalInput(
            @Header("Authorization") String tokenString,
            @Field("userID") int UserId,
            @Field("systolicReading") String systolic,
            @Field("diastolicReading") String diastolic,
            @Field("pulseReading") String pulseRate,
            @Field("temperatureReading") String temperature,
            @Field("temperatureUnit") String temperatureUnit,
            @Field("height") String height,
            @Field("weightReading") String weight,
            @Field("weightUnit") String weightUnit,
            @Field("sugerReading") String sugarRate,
            @Field("sugerUnit") String sugarUnit,
            @Field("sleeps") String sleep

    );


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("test_create")
    Call<Lab_Report_Response> Lab_report(
            @Header("Authorization") String tokenString,
            @Field("user_id") int UserId,
            @Field("images[]") String image,
            @Field("testname") String testname
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("prescription_create")
    Call<Lab_Report_Response> PrescriptSubmit(
            @Header("Authorization") String tokenString,
            @Field("user_id") int UserId,
            @Field("image") String image,
            @Field("doctor_name") String doctorName,
            @Field("heading") String heading
    );



}
