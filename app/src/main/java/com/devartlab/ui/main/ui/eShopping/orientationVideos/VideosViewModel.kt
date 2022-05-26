package com.devartlab.ui.main.ui.eShopping.orientationVideos

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.eShopping.orientationVideos.model.ResponseVideos
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideosViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var responseVideos: MutableLiveData<ResponseVideos?>
        protected set
    var dataManager: DataManager
    fun getVideos() {
        RetrofitClient(dataManager).apis4EShopping.getVideos("Bearer "+ UserPreferenceHelper.getUser().token)!!
            .enqueue(object : Callback<ResponseVideos?> {
                override fun onResponse(
                    call: Call<ResponseVideos?>,
                    response: Response<ResponseVideos?>
                ) {
                    if (response.isSuccessful) {
                        responseVideos.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseVideos?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        responseVideos = MutableLiveData()
        errorMessage = MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}