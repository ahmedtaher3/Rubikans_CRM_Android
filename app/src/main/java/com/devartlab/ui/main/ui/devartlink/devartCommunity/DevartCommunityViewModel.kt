package com.devartlab.ui.main.ui.devartlink.devartCommunity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.a4eshopping.orientationVideos.model.ResponseVideos
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.devartlink.devartCommunity.model.DevartCommunityResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevartCommunityViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var devartCommunityResponse: MutableLiveData<DevartCommunityResponse?>
        protected set
    var dataManager: DataManager
    fun getDevartCommunity() {
        RetrofitClient.getApis().getDevartCommunity()!!
            .enqueue(object : Callback<DevartCommunityResponse?> {
                override fun onResponse(
                    call: Call<DevartCommunityResponse?>,
                    response: Response<DevartCommunityResponse?>
                ) {
                    if (response.isSuccessful) {
                        devartCommunityResponse.postValue(response.body())
                    } else {
                        devartCommunityResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DevartCommunityResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        devartCommunityResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}