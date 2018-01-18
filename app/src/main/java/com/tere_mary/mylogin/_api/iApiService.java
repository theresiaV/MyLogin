package com.tere_mary.mylogin._api;

import com.tere_mary.mylogin.modul.Users;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Theresia V A Mary G on 1/17/2018.
 */

public interface iApiService {

    //untuk get api-nya
    @GET("/users")
    Call<Users> getUsers();
}
