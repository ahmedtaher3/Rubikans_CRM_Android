package com.devartlab.a4eshopping.orientationVideos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.a4eshopping.orientationVideos.model.ResponseVideos
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideosViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var responseVideos: MutableLiveData<ResponseVideos?>
        protected set

    fun getVideos() {
        RetrofitClient.getApis4EShopping().getVideos("Bearer "+ UserPreferenceHelper.getUser().token)!!
            .enqueue(object : Callback<ResponseVideos?> {
                override fun onResponse(
                    call: Call<ResponseVideos?>,
                    response: Response<ResponseVideos?>
                ) {
                    if (response.isSuccessful) {
                        responseVideos.postValue(response.body())
                    } else {
                        responseVideos.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ResponseVideos?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        responseVideos = MutableLiveData()
        errorMessage = MutableLiveData()
    }
}