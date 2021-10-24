package com.devartlab.ui.main.ui.employeeservices.leavework

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.GoogleRequestResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServicesGoogle
import retrofit2.Retrofit

class LeaveWorkViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServicesGoogle? = null
    var retrofit: Retrofit? = null

    val responseLive: MutableLiveData<GoogleRequestResponse>

    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstanceGoogleSheet()
        myAPI = retrofit!!.create(ApiServicesGoogle::class.java)
        responseLive = MutableLiveData<GoogleRequestResponse>()

        progress = MutableLiveData<Int>()


    }

    /////////////////////////////////////////////////////////


    fun leaveWork(
        empId: String,
        name: String,
        leaveDate: String,
        managerId: String,
        notes: String,
        addedby: String
    ) {


        progress.postValue(1)
        myAPI?.leaveWork(
            "Leave work",
            "insert",
            empId,
            name,
            leaveDate,
            managerId,
            notes,
            addedby
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GoogleRequestResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: GoogleRequestResponse) {

                    progress.postValue(0)

                    responseLive.postValue(data)


                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {}
            })

    }


}