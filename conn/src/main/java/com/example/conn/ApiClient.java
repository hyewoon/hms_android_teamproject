package com.example.conn;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    public static String BASEURL = "";

    public static void setBASEURL(String BASEURL) {
        ApiClient.BASEURL = BASEURL;
    }

    public Retrofit getApiClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .client( new OkHttpClient.Builder().connectTimeout(60 , TimeUnit.SECONDS).build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit;
    }

}
