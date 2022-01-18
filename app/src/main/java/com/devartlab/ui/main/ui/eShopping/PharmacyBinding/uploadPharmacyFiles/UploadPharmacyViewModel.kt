package com.devartlab.a4eshopping.PharmacyBinding.uploadPharmacyFiles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.a4eshopping.PharmacyBinding.uploadPharmacyFiles.model.updatePharmacyDetails
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadPharmacyViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var updatePharmacyDetails: MutableLiveData<updatePharmacyDetails?>
        protected set

    fun getUpdatePharmacyDetails(file: MultipartBody.Part,file2: MultipartBody.Part
                                 ,file3: MultipartBody.Part,
                                 send: MutableMap<String, RequestBody>) {
        RetrofitClient.getApis4EShopping().updatePharmacyDetails("Bearer "+ UserPreferenceHelper.getUser().token
            ,file,file2,file3, send)!!
            .enqueue(object : Callback<updatePharmacyDetails?> {
                override fun onResponse(
                    call: Call<updatePharmacyDetails?>,
                    response: Response<updatePharmacyDetails?>
                ) {
                    if (response.isSuccessful()) {
                        updatePharmacyDetails.postValue(response.body())
                    } else {
                        updatePharmacyDetails.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<updatePharmacyDetails?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        errorMessage = MutableLiveData()
        updatePharmacyDetails = MutableLiveData()
    }
}