package com.tere_mary.mylogin.modul;

/**
 * Created by Theresia V A Mary G on 1/17/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("users")
    @Expose

    //untuk masukkan data ke list array
    private List<UserItem> users;

    public List<UserItem> getUsers() {
        return users;
    }

    public void setUsers(List<UserItem> users) {
        this.users = users;
    }

    public Users(List<UserItem>users){
        this.users = users;
    }

    public class UserItem{
        private String id;
        public String email;
        public String password;
        public String token_authentication;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getToken_authentication() {
            return token_authentication;
        }

        public void setToken_authentication(String token_authentication) {
            this.token_authentication = token_authentication;
        }
    }
}
