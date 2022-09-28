package com.sukanto.receptionist;

public class Original {
    private String access_token;
    private String token_type;
    private float expires_in;


    // Getter Methods
    public Original(String access_token,String token_type,float expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }
    public float getExpireIn() {
        return this.expires_in;
    }

}
