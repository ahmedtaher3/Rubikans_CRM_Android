package com.devartlab.a4eshopping.pharmacySales

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.eShopping.pharmacySales.model.PharmacySalesResponse
import com.devartlab.ui.main.ui.eShopping.pharmacySales.model.detailsPharmacySales.DetailsPharmacySalesResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PharmacySalesViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var pharmacySalesResponse: MutableLiveData<PharmacySalesResponse?>
        protected set
    var DetailsPharmacySalesResponse: MutableLiveData<DetailsPharmacySalesResponse?>
        protected set
    var dataManager: DataManager

    fun getPharmacySales(q: String) {
        RetrofitClient.getApis4EShopping().getPharmacySales("Bearer "+ UserPreferenceHelper.getUser().token,q)!!
            .enqueue(object : Callback<PharmacySalesResponse?> {
                override fun onResponse(
                    call: Call<PharmacySalesResponse?>,
                    response: Response<PharmacySalesResponse?>
                ) {
                    if (response.isSuccessful) {
                        pharmacySalesResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PharmacySalesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getDetailsPharmacySales(order_number:String) {
        RetrofitClient.getApis4EShopping().getOrderDetails("Bearer "+UserPreferenceHelper.getUser().token,order_number)!!
            .enqueue(object : Callback<DetailsPharmacySalesResponse?> {
                override fun onResponse(
                    call: Call<DetailsPharmacySalesResponse?>,
                    response: Response<DetailsPharmacySalesResponse?>
                ) {
                    if (response.isSuccessful) {
                        DetailsPharmacySalesResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DetailsPharmacySalesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    init {
        pharmacySalesResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        DetailsPharmacySalesResponse= MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}