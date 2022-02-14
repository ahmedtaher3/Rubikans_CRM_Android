package com.devartlab.a4eshopping.pharmacySales

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.callmanagement.incentiveRule.model.DevartLabIncentiveResponse
import com.devartlab.ui.main.ui.devartLabTeam.model.DevartLabTeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevartLabTeamViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var devartLabTeamResponse: MutableLiveData<DevartLabTeamResponse?>
        protected set
    var devartLabIncentiveResponse: MutableLiveData<DevartLabIncentiveResponse?>
        protected set

    fun getDevartLabTeam(_id: String) {
        RetrofitClient.getApis().getDevartLabTeam(_id)!!
            .enqueue(object : Callback<DevartLabTeamResponse?> {
                override fun onResponse(
                    call: Call<DevartLabTeamResponse?>,
                    response: Response<DevartLabTeamResponse?>
                ) {
                    if (response.isSuccessful) {
                        devartLabTeamResponse.postValue(response.body())
                    } else {
                        devartLabTeamResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DevartLabTeamResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getIncentive(_id: String) {
        RetrofitClient.getApis().getIncentive(_id)!!
            .enqueue(object : Callback<DevartLabIncentiveResponse?> {
                override fun onResponse(
                    call: Call<DevartLabIncentiveResponse?>,
                    response: Response<DevartLabIncentiveResponse?>
                ) {
                    if (response.isSuccessful) {
                        devartLabIncentiveResponse.postValue(response.body())
                    } else {
                        devartLabIncentiveResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DevartLabIncentiveResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        devartLabTeamResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        devartLabIncentiveResponse=MutableLiveData()
    }
}