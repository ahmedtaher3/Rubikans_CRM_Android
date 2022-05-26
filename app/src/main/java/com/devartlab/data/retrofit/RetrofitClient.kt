package com.devartlab.data.retrofit

import android.util.Log
import com.devartlab.AppConstants
import com.devartlab.data.shared.DataManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.BuildConfig
import java.util.concurrent.TimeUnit

/**
 * Created by Ahmed Taher on 10/20/2018.
 */
class RetrofitClient(private val dataManager: DataManager) {
    private var ourInstance: Retrofit? = null
    private var ourInstance2: Retrofit? = null
    private var ourInstance3 // to save at shard Preference
            : Retrofit? = null
    private var retrofit: Retrofit? = null //lat's talk
    private var retrofit1: Retrofit? = null //4eshopping
    private val loggingInterceptor = HttpLoggingInterceptor()


    //alaa
    val instance: Retrofit?
        get() {
            if (BuildConfig.DEBUG) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build()
            if (ourInstance == null) ourInstance = Retrofit.Builder()
                .baseUrl(dataManager.url) //alaa
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return ourInstance
        }

    //taher
    val instanceGoogleSheet: Retrofit?
        get() {
            if (BuildConfig.DEBUG) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor).build()
            if (ourInstance2 == null) ourInstance2 = Retrofit.Builder()
                .baseUrl("https://script.google.com/macros/s/") //taher
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return ourInstance2
        }

    // eslam
    val instanceDevartLink: Retrofit?
        get() {
            if (BuildConfig.DEBUG) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build()
            if (ourInstance3 == null) ourInstance3 = Retrofit.Builder()
                .baseUrl("https://devartlink.4eshopping.com/") // eslam
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return ourInstance3
        }
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor { message -> Log.e("retrofit", message) }
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://devartlink.4eshopping.com/api/")
                    .client(client)
                    .build()
            }
            return retrofit
        }


    val apis: ApiServices
        get() = client!!.create(ApiServices::class.java)
    val client4EShopping: Retrofit?
        get() {
            if (retrofit1 == null) {
                if (BuildConfig.DEBUG) {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                } else {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
                }
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
                retrofit1 = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(AppConstants.forEShoppingURL)
                    .client(client)
                    .build()
            }
            return retrofit1
        }
    val apis4EShopping: ApiServices
        get() = client4EShopping!!.create(ApiServices::class.java)
}