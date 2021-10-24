package com.devartlab.ui.main.ui.contactlist.data.retrofit;


import com.devartlab.ui.main.ui.contactlist.pojo.Request;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient1 {

    private static Retrofit ourInstance;
    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    okhttp3.Request request = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
                    return chain.proceed(request);
                }
            })
            .build();


    private ApiServices1 apiServices;
    private static RetrofitClient1 INSTANCE;

    public RetrofitClient1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServices1.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiServices = retrofit.create(ApiServices1.class);
    }

    public static RetrofitClient1 getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new RetrofitClient1();
        }
        return INSTANCE;
    }

    public Single<Request> getcontact() {
        return apiServices.getcontact();
    }


}


