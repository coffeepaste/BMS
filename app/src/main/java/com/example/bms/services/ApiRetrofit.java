package com.example.bms.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    //private static Retrofit retrofit = null;

    private String BASE_URL = "http://portal-bams.mncgroup.com:8008/";

    private static  ApiRetrofit mIntence;

    private Retrofit retrofit;

    public ApiRetrofit()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiRetrofit getInstance()
    {
        if (mIntence == null)
        {
            mIntence = new ApiRetrofit();
        }
        return mIntence;
    }

    public ClientLdap getApiClien(){
        return retrofit.create(ClientLdap.class);
    }

}