package com.devartlab.ui.main.ui.eShopping.nearbyPharmacy

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.eShopping.nearbyPharmacy.model.NearbyPharmacyResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NearbyPharmacyViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var responseNearbyPharmacy: MutableLiveData<NearbyPharmacyResponse?>
        protected set
    var dataManager: DataManager
    fun getNearbyPharmacy(lat_lng: String) {
        RetrofitClient(dataManager).apis4EShopping.getNearbyPharmacy("Bearer "+ UserPreferenceHelper.getUser().token,lat_lng)
            .enqueue(object : Callback<NearbyPharmacyResponse?> {
                override fun onResponse(
                    call: Call<NearbyPharmacyResponse?>,
                    response: Response<NearbyPharmacyResponse?>
                ) {
                    if (response.isSuccessful) {
                        responseNearbyPharmacy.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<NearbyPharmacyResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        responseNearbyPharmacy = MutableLiveData()
        errorMessage = MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}