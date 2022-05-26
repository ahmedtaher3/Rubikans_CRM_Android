package com.devartlab.ui.main.ui.profile

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import retrofit2.Retrofit

class ProfileViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    val responseLive: MutableLiveData<ResponseModel>
    val responseLiveUpdate: MutableLiveData<ResponseModel>

    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<ResponseModel>()
        responseLiveUpdate = MutableLiveData<ResponseModel>()

        progress = MutableLiveData<Int>()


    }


    fun getData() {


        progress.postValue(1)
        myAPI?.GetEmployeeById(dataManager.user.empId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {


                        progress.postValue(0)
                        responseLive.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })

    }

    fun updateEmployee(model: JsonObject) {


        progress.postValue(1)
        myAPI?.InsertAndUpdateEmployee(model)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {


                        progress.postValue(0)
                        responseLiveUpdate.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })

    }
}