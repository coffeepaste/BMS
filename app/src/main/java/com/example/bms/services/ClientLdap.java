package com.example.bms.services;

import com.example.bms.model.LoginLdap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ClientLdap {

    @FormUrlEncoded
    @POST("api")
    Call<LoginLdap> UserLdapClient(
            @Field("username") String username,
            @Field("password") String password
    );
}