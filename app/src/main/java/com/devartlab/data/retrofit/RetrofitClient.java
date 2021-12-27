package com.devartlab.data.retrofit;

import com.devartlab.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed Taher on 10/20/2018.
 */

public class RetrofitClient {

    private static Retrofit ourInstance;
    private static Retrofit ourInstance2;
    private static Retrofit ourInstance3;
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    public static Retrofit getInstance() {


        if (BuildConfig.DEBUG) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build();


        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)//alaa
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


        return ourInstance;
    }


    public static Retrofit getInstanceGoogleSheet() {


        if (BuildConfig.DEBUG) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build();


        if (ourInstance2 == null)
            ourInstance2 = new Retrofit.Builder()
                    .baseUrl("https://script.google.com/macros/s/")//taher
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


        return ourInstance2;
    }


    public static Retrofit getInstanceDevartLink() {


        if (BuildConfig.DEBUG) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build();


        if (ourInstance3 == null)
            ourInstance3 = new Retrofit.Builder()
                    .baseUrl("https://devartlink.devartlab.com/")// eslam
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


        return ourInstance3;
    }


}
