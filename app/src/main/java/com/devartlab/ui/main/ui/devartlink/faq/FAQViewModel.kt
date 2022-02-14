package com.devartlab.ui.main.ui.devartlink.faq

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.devartlink.faq.model.FAQResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FAQViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var faqResponse: MutableLiveData<FAQResponse?>
        protected set
    var dataManager: DataManager
    fun getFAQ() {
        RetrofitClient.getApis().getFAQ()!!
            .enqueue(object : Callback<FAQResponse?> {
                override fun onResponse(
                    call: Call<FAQResponse?>,
                    response: Response<FAQResponse?>
                ) {
                    if (response.isSuccessful) {
                        faqResponse.postValue(response.body())
                    } else {
                        faqResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<FAQResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        faqResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}