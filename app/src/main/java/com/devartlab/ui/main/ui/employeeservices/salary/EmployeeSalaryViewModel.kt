package com.devartlab.ui.main.ui.employeeservices.salary

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

class EmployeeSalaryViewModel(application: Application) : AndroidViewModel(application) {

    val progress: MutableLiveData<Int>
    var myAPI: ApiServices? = null
    var dataManager: DataManager? = null
    var retrofit: Retrofit? = null
    val responseLive: MutableLiveData<ResponseModel>
    val responseLiveDelete: MutableLiveData<ResponseModel>


    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        progress = MutableLiveData()
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<ResponseModel>()
        responseLiveDelete = MutableLiveData<ResponseModel>()

    }


    public fun getSalary(id: String, empId: String, Month: String, Year: String) {
        progress.postValue(1)
        myAPI?.getEmployeeMonthlyPaySlip(id.toInt(), empId.toInt(), Month, Year)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        responseLive.postValue(data)
                        progress.postValue(0)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {

                    }
                })

    }

}