package com.devartlab.ui.main.ui.eShopping.requestVoucher

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.a4eshopping.orientationVideos.model.ResponseVideos
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.eShopping.requestVoucher.model.compaignVouchers.CompaignVouchersResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestVoucherViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var compaignVouchersResponse: MutableLiveData<CompaignVouchersResponse?>
        protected set
    var dataManager: DataManager
    fun getCompaignVouchers() {
        RetrofitClient.getApis4EShopping().getCompaignVouchers("Bearer "+ UserPreferenceHelper.getUser().token,dataManager.getLang())!!
            .enqueue(object : Callback<CompaignVouchersResponse?> {
                override fun onResponse(
                    call: Call<CompaignVouchersResponse?>,
                    response: Response<CompaignVouchersResponse?>
                ) {
                    if (response.isSuccessful) {
                        compaignVouchersResponse.postValue(response.body())
                    } else {
                        compaignVouchersResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<CompaignVouchersResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        compaignVouchersResponse = MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
        errorMessage = MutableLiveData()
    }
}