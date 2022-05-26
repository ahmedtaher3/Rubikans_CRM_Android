package com.devartlab.ui.main.ui.callmanagement.employee

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

class EmployeeReportViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    val progress: MutableLiveData<Int>


    val responseLiveCycle: MutableLiveData<ResponseModel>
    val responseLiveDay: MutableLiveData<ResponseModel>


    init {


        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLiveDay = MutableLiveData<ResponseModel>()
        responseLiveCycle = MutableLiveData<ResponseModel>()
        progress = MutableLiveData()

    }


    fun getDailyReport(isTemp :Boolean , EmployeeAccountId: Int, DayDateInMSFormat: Long, ShiftId: Int) {

        progress.value = 2
        System.out.println("EmployeeAccountId = " + EmployeeAccountId + " DayDateInMSFormat = " +DayDateInMSFormat +" ShiftId = " +ShiftId)
        myAPI?.GetEmployeeDailyReport(isTemp , dataManager.user.accId!!, EmployeeAccountId, DayDateInMSFormat, ShiftId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        progress.value = 3
                        responseLiveDay.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progress.value = 3
                        System.out.println(e.message)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })
    }

    fun getCycleReport(EmployeeAccountId: String , CycleId: String) {

        progress.value = 1
        System.out.println("EmployeeAccountId = " + EmployeeAccountId + " CycleId = " +CycleId )
        myAPI?.GetEMployeeDailyReportDashBoard(dataManager.user.accId!!, EmployeeAccountId, CycleId )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        progress.value = 0
                        responseLiveCycle.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.value = 0
                        System.out.println(e.message)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })
    }

}