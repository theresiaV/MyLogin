package com.tere_mary.mylogin.modul;

/**
 * Created by Theresia V A Mary G on 1/17/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("token_authentication")
    @Expose
    private String tokenAuthentication;

    public User(String email, String password, String token_authentication){
        this.email = email;
        this.password = password;
        this.tokenAuthentication = token_authentication;
    }

    //getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenAuthentication() {
        return tokenAuthentication;
    }

    public void setTokenAuthentication(String tokenAuthentication) {
        this.tokenAuthentication = tokenAuthentication;
    }

}
