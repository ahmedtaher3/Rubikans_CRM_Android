package com.devartlab.ui.main.ui.employeeservices.workfromhome

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.GoogleRequestResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

class WorkFromHomeViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServicesGoogle? = null
    var retrofit: Retrofit? = null
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' // 'hh:mm a", Locale.US)
    val responseLive: MutableLiveData<GoogleRequestResponse>
    val responseLiveInsertWorkFromHome: MutableLiveData<GoogleRequestResponse>
    val responseLiveCheckWorkFromHome: MutableLiveData<GoogleRequestResponse>
    val PendingResponseLiveRequests: MutableLiveData<GoogleRequestResponse>


    val progress: MutableLiveData<Int>
    val progressGoogle: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstanceGoogleSheet()
        myAPI = retrofit!!.create(ApiServicesGoogle::class.java)
        responseLive = MutableLiveData<GoogleRequestResponse>()
        responseLiveInsertWorkFromHome = MutableLiveData<GoogleRequestResponse>()
        responseLiveCheckWorkFromHome = MutableLiveData<GoogleRequestResponse>()
        PendingResponseLiveRequests = MutableLiveData<GoogleRequestResponse>()


        progress = MutableLiveData<Int>()
        progressGoogle = MutableLiveData<Int>()

        checkWorkToday()
    }
    /////////////////////////////////////////////////////////


    fun getAll() {
        progress.postValue(1)
        myAPI?.workFromHome("Work From Home", "getAll", dataManager.user.empId.toString(), "", "", "", "", "", "", "", "")!!
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


                    }

                    override fun onComplete() {}
                })

    }

    /////////////////////////////////////////////////////////


    fun startWorkFromHome(action: String?,
                          id: String,
                          date: String,
                          name: String,
                          managerId: String?,
                          notes: String,
                          status: String?,
                          approveDate: String,
                          code: String) {


        progress.postValue(1)
        myAPI?.workFromHome("Work From Home", "insert", id, date, name, managerId!!, notes, status!!, approveDate, code, "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {


                        progress.postValue(0)
                        responseLiveCheckWorkFromHome.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)


                    }

                    override fun onComplete() {}
                })

    }

    /////////////////////////////////////////////////////////


    fun checkWorkToday() {


        progress.postValue(1)
        myAPI?.workFromHome("Work From Home", "check", dataManager.user.empId.toString(), "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        progress.postValue(0)
                        responseLiveCheckWorkFromHome.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println("TAAAAG" + e.message)
                        System.out.println("TAAAAG" + e.message)
                    }

                    override fun onComplete() {}
                })

    }

    /////////////////////////////////////////////////////////


    fun endWorkToday(code: String){


        progress.postValue(1)
        myAPI?.workFromHome("Work From Home", "end", dataManager.user.empId.toString(), "", "", "", "", "", "", code, simpleDateFormat?.format(System.currentTimeMillis()))!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        progress.postValue(0)
                        responseLiveCheckWorkFromHome.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }



}