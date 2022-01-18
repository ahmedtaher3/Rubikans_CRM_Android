package com.devartlab.data.retrofit;

import android.app.Application;
import android.util.Log;

import com.devartlab.AppConstants;
import com.devartlab.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed Taher on 10/20/2018.
 */

public class RetrofitClient extends Application {

    private static Retrofit ourInstance;
    private static Retrofit ourInstance2;
    private static Retrofit ourInstance3;// to save at shard Preference
    private static Retrofit retrofit = null;//lat's talk
    private static Retrofit retrofit1 = null;//4eshopping
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
                    .baseUrl(AppConstants.BaseURL)//alaa
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
                .connectTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
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
    public static Retrofit getClient() {
        if (retrofit==null){
            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override public void log(@NotNull String message) {
                            Log.e("retrofit",message);
                        }
                    });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://devartlink.devartlab.com/api/")
                    .client(client)
                    .build();
        }
        return retrofit;
    }
    public static ApiServices getApis (){
        return getClient().create(ApiServices.class);
    }


    public static Retrofit getClient4EShopping() {
        if (retrofit1==null){
            HttpLoggingInterceptor logging =
                    new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override public void log(@NotNull String message) {
                            Log.e("retrofit",message);
                        }
                    });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit1 = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://t4e.4eshopping.com/api/")
                    .client(client)
                    .build();
        }
        return retrofit1;
    }
    public static ApiServices getApis4EShopping (){
        return getClient4EShopping().create(ApiServices.class);
    }


}
