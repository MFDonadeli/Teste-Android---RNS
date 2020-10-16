package com.salcisne.androidteste.service.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

    private static String BASE_URL;

    private static Retrofit retrofit;
    private static ApiEndpoints api;
    private static Gson gson;

    public ApiModule(String BASE_URL){
        ApiModule.BASE_URL = BASE_URL;
    }

    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    private static Retrofit provideRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

    public static ApiEndpoints getApi() {
        if(api == null)
            api = ApiModule.provideRetrofit().create(ApiEndpoints.class);
        return api;
    }

    public static Gson getGsonInstance() {
        if(gson == null)
            gson = new GsonBuilder().setPrettyPrinting().create();

        return gson;
    }
}