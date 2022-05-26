package com.devartlab.a4eshopping.PharmacyBinding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.connectPharmacy.ConnectPharmacyResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.searchForPharmacy.ConnetctedPharmaciesResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.searchForPharmacy.SearchForPharmacyRequest
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.searchForPharmacy.SearchForPharmacyResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PharmacyBindingViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var ConnetctedPharmaciesResponse: MutableLiveData<ConnetctedPharmaciesResponse?>
        protected set
    var searchForPharmacyResponse: MutableLiveData<SearchForPharmacyResponse?>
        protected set
    var connectPharmacyResponse: MutableLiveData<ConnectPharmacyResponse?>
        protected set
    var dataManager: DataManager

    fun getConnetctedPharmacies(q: String) {
        RetrofitClient(dataManager).apis4EShopping.getConnetctedPharmacies("Bearer "+ UserPreferenceHelper.getUser().token,q)!!
            .enqueue(object : Callback<ConnetctedPharmaciesResponse?> {
                override fun onResponse(
                    call: Call<ConnetctedPharmaciesResponse?>,
                    response: Response<ConnetctedPharmaciesResponse?>
                ) {
                    if (response.isSuccessful) {
                        ConnetctedPharmaciesResponse.postValue(response.body())
                    } else {
                        ConnetctedPharmaciesResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ConnetctedPharmaciesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    fun getSearchForPharmacy(q: String) {
        RetrofitClient(dataManager).apis4EShopping.getSearchForPharmacy("Bearer "+UserPreferenceHelper.getUser().token,q)!!
            .enqueue(object : Callback<SearchForPharmacyResponse?> {
                override fun onResponse(
                    call: Call<SearchForPharmacyResponse?>,
                    response: Response<SearchForPharmacyResponse?>
                ) {
                    if (response.isSuccessful) {
                        searchForPharmacyResponse.postValue(response.body())
                    } else {
                        searchForPharmacyResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<SearchForPharmacyResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    fun connetctedPharmacies(request: SearchForPharmacyRequest) {
        RetrofitClient(dataManager).apis4EShopping.connetctedPharmacies("Bearer "+UserPreferenceHelper.getUser().token,request)!!
            .enqueue(object : Callback<ConnectPharmacyResponse?> {
                override fun onResponse(
                    call: Call<ConnectPharmacyResponse?>,
                    response: Response<ConnectPharmacyResponse?>
                ) {
                    if (response.isSuccessful) {
                        connectPharmacyResponse.postValue(response.body())
                    } else {
                        connectPharmacyResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ConnectPharmacyResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    init {
        ConnetctedPharmaciesResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        searchForPharmacyResponse = MutableLiveData()
        connectPharmacyResponse = MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}