package com.feed.feedo.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by techelogy2 on 7/1/18.
 */

public class ApiClient
{
    static Retrofit retrofit;
    private static final String API_BASE_URL = "http://passm.selfevaluation.in/Feedo/";
    private static final String API_BASE_URL2 = "http://passm.selfevaluation.in/Feedo/";

    private ApiClient()
    {

    }

    public static Retrofit getApiClient()
    {
        if (retrofit == null)
        {
            createInstance();
        }
        else
        {
            return retrofit;
        }
        return retrofit;
    }

    public static Retrofit getApiClient2()
    {
        if (retrofit == null)
        {
            createInstance2();
        }
        else
        {
            return retrofit;
        }
        return retrofit;
    }


    private static void createInstance()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);

        httpClient.addInterceptor(logging);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(httpClient.build()).build();

    }

    private static void createInstance2()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);

        httpClient.addInterceptor(logging);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL2)
                        .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(httpClient.build()).build();

    }
}
