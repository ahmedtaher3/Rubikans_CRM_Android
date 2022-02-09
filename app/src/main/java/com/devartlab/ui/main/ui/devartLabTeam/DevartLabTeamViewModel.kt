package com.devartlab.a4eshopping.pharmacySales

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.devartLabTeam.model.DevartLabTeamResponse
import com.devartlab.ui.main.ui.eShopping.pharmacySales.model.PharmacySalesResponse
import com.devartlab.ui.main.ui.eShopping.pharmacySales.model.detailsPharmacySales.DetailsPharmacySalesResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DevartLabTeamViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var devartLabTeamResponse: MutableLiveData<DevartLabTeamResponse?>
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

    init {
        devartLabTeamResponse = MutableLiveData()
        errorMessage = MutableLiveData()
    }
}