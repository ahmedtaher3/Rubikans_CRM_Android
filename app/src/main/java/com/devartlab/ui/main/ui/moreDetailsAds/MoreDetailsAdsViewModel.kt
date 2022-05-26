package com.devartlab.ui.main.ui.moreDetailsAds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.moreDetailsAds.model.SeeMoreRequest
import com.devartlab.ui.main.ui.moreDetailsAds.model.SeeMoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreDetailsAdsViewModel (application: Application) : AndroidViewModel(application) {
    var dataManager: DataManager
    var errorMessage: MutableLiveData<Int>
        protected set
    var seeMoreResponse: MutableLiveData<SeeMoreResponse>
        protected set
    fun getSeeMore(seeMoreRequest: SeeMoreRequest) {
        RetrofitClient(dataManager).apis.getSeeMore(seeMoreRequest)!!
            .enqueue(object : Callback<SeeMoreResponse?> {
                override fun onResponse(
                    call: Call<SeeMoreResponse?>,
                    response: Response<SeeMoreResponse?>
                ) {
                    if (response.isSuccessful) {
                        seeMoreResponse.postValue(response.body())
                    } else {
                        seeMoreResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<SeeMoreResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        errorMessage = MutableLiveData()
        seeMoreResponse = MutableLiveData()
    }
}