package com.sukanto.receptionist;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Header;

public class JwtToken {
    @SerializedName("header")
    Header HeaderObject;
    @SerializedName("original")
    Original original;

    public JwtToken(Original original) {
        this.original = original;
    }

    public Original getOriginal() {
       return this.original;
    }

}
