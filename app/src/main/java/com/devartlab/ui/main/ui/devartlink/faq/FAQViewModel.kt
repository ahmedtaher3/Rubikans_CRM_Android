package com.devartlab.ui.main.ui.devartlink.faq

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.devartlink.faq.model.faq.FAQResponse
import com.devartlab.ui.main.ui.devartlink.faq.model.section.SectionsResponse
import com.devartlab.ui.main.ui.devartlink.faq.model.sub.SubsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FAQViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var faqResponse: MutableLiveData<FAQResponse?>
        protected set
    var subsFaqResponse: MutableLiveData<SubsResponse?>
        protected set
    var sectionsResponse: MutableLiveData<SectionsResponse?>
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

    fun getSubsFAQ(id: String) {
        RetrofitClient.getApis().getSubsFAQ(id)!!
            .enqueue(object : Callback<SubsResponse?> {
                override fun onResponse(
                    call: Call<SubsResponse?>,
                    response: Response<SubsResponse?>
                ) {
                    if (response.isSuccessful) {
                        subsFaqResponse.postValue(response.body())
                    } else {
                        subsFaqResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<SubsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getSectionsFAQ(id: String) {
        RetrofitClient.getApis().getSectionsFAQ(id)!!
            .enqueue(object : Callback<SectionsResponse?> {
                override fun onResponse(
                    call: Call<SectionsResponse?>,
                    response: Response<SectionsResponse?>
                ) {
                    if (response.isSuccessful) {
                        sectionsResponse.postValue(response.body())
                    } else {
                        sectionsResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<SectionsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        faqResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        subsFaqResponse = MutableLiveData()
        sectionsResponse = MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}