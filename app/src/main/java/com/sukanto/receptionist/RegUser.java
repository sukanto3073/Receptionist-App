package com.sukanto.receptionist;

public class RegUser {
    private int id;
    private String name;
    private String username;
    private String mobile;



    public RegUser(int id, String name, String username , String mobile) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.mobile = mobile;

    }

    public int getId() {return id;}
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getMobile() {
        return mobile;
    }




}
