package com.devartlab.ui.main.ui.eShopping.pharmacyBinding.allComments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.allComments.model.AllCommentsResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCommentsViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var allCommentsResponse: MutableLiveData<AllCommentsResponse?>
        protected set

    fun getSearchForPharmacy(id: String) {
        RetrofitClient.getApis4EShopping().getAllComments("Bearer "+ UserPreferenceHelper.getUser().token,id)!!
            .enqueue(object : Callback<AllCommentsResponse?> {
                override fun onResponse(
                    call: Call<AllCommentsResponse?>,
                    response: Response<AllCommentsResponse?>
                ) {
                    if (response.isSuccessful) {
                        allCommentsResponse.postValue(response.body())
                    } else {
                        allCommentsResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<AllCommentsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    init {
        allCommentsResponse = MutableLiveData()
        errorMessage = MutableLiveData()
    }
}