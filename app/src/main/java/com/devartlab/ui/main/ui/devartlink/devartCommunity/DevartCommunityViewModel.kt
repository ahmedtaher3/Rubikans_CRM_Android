package com.devartlab.ui.main.ui.devartlink.devartCommunity

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.devartlink.devartCommunity.model.DevartCommunityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevartCommunityViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var devartCommunityResponse: MutableLiveData<DevartCommunityResponse?>
        protected set
    var dataManager: DataManager
    fun getDevartCommunity(_id: String) {
        RetrofitClient(dataManager).apis.getDevartCommunity(_id)!!
            .enqueue(object : Callback<DevartCommunityResponse?> {
                override fun onResponse(
                    call: Call<DevartCommunityResponse?>,
                    response: Response<DevartCommunityResponse?>
                ) {
                    if (response.isSuccessful) {
                        devartCommunityResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
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