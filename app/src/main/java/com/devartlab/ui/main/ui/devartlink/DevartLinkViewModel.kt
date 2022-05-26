package com.devartlab.ui.main.ui.devartlink

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DevartLinkViewModel(application: Application) : AndroidViewModel(application) {




    var myAPI: ApiServices? = null
    var myAPI2: ApiServicesGoogle? = null
    var dataManager: DataManager
    var retrofit: Retrofit? = null
    var retrofit2: Retrofit? = null
    var errorMessage: MutableLiveData<Int>
        protected set
    var userResponse: MutableLiveData<UserResponse?>
        protected set

    fun getUserModel(u: String?, p: String?, fcm: String?) {
        RetrofitClient(dataManager).apis.getModelUser(u, p, fcm)!!
            .enqueue(object : Callback<UserResponse?> {
                override fun onResponse(
                    call: Call<UserResponse?>,
                    response: Response<UserResponse?>
                ) {
                    if (response.isSuccessful) {
                        userResponse.postValue(response.body())
                    } else {
                        userResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instance!!
        retrofit2 = RetrofitClient(dataManager!!).instanceGoogleSheet!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        myAPI2 = retrofit2!!.create(ApiServicesGoogle::class.java)
        errorMessage = MutableLiveData()
        userResponse= MutableLiveData()



    }



}