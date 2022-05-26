package com.devartlab.ui.main.ui.eShopping.pharmacyBinding.allComments

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
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

    lateinit var dataManager: DataManager
    fun getSearchForPharmacy(id: String) {
        RetrofitClient(dataManager).apis4EShopping.getAllComments("Bearer "+ UserPreferenceHelper.getUser().token,id)!!
            .enqueue(object : Callback<AllCommentsResponse?> {
                override fun onResponse(
                    call: Call<AllCommentsResponse?>,
                    response: Response<AllCommentsResponse?>
                ) {
                    if (response.isSuccessful) {
                        allCommentsResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AllCommentsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }
    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        allCommentsResponse = MutableLiveData()
        errorMessage = MutableLiveData()
    }
}