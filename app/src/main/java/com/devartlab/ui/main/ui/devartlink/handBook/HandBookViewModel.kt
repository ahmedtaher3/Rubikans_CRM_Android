package com.devartlab.ui.main.ui.devartlink.handBook

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HandBookViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var handBookResponse: MutableLiveData<HandBookResponse?>
        protected set
    fun getHandBook() {
        RetrofitClient.getApis().getHandBook()!!
            .enqueue(object : Callback<HandBookResponse?> {
                override fun onResponse(
                    call: Call<HandBookResponse?>,
                    response: Response<HandBookResponse?>
                ) {
                    if (response.isSuccessful) {
                        handBookResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<HandBookResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        handBookResponse = MutableLiveData()
        errorMessage = MutableLiveData()
    }
}