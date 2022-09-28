package com.sukanto.receptionist;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String mobile;
    private String address;
    private String hospital_name;
    JwtToken jwt_token;

    public User(Integer id, String name, String email, String mobile, String address, String hospital_name,JwtToken jwt_token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.hospital_name = hospital_name;
        this.jwt_token=jwt_token;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public JwtToken getJwt_token() {
        return this.jwt_token;
    }



}
