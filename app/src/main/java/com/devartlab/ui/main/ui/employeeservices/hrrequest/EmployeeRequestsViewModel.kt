package com.devartlab.ui.main.ui.employeeservices.hrrequest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
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

class EmployeeRequestsViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServicesGoogle? = null
    var retrofit: Retrofit? = null

    val responseLive: MutableLiveData<GoogleRequestResponse>
    val responseLiveRequests: MutableLiveData<GoogleRequestResponse>
    val PendingResponseLiveRequests: MutableLiveData<GoogleRequestResponse>


    val progress: MutableLiveData<Int>
    val progressGoogle: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instanceGoogleSheet!!
        myAPI = retrofit!!.create(ApiServicesGoogle::class.java)
        responseLive = MutableLiveData<GoogleRequestResponse>()
        responseLiveRequests = MutableLiveData<GoogleRequestResponse>()
        PendingResponseLiveRequests = MutableLiveData<GoogleRequestResponse>()


        progress = MutableLiveData<Int>()
        progressGoogle = MutableLiveData<Int>()


    }

    /////////////////////////////////////////////////////////


    fun getRequests(action: String?,
                    date: String,
                    id: String,
                    name: String,
                    typeRequest: String?,
                    requestStartIn: String,
                    requestEndIn: String?,
                    notes: String,
                    managerId: String,
                    status: String,
                    comment: String,
                    approvalDate: String,
                    code: String) {


        progress.postValue(1)
        myAPI?.googleSheetRequest("HR Requests" ,action!!, date, id, name, typeRequest!!, requestStartIn, requestEndIn!!, notes, managerId, status, comment, approvalDate, code)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        progress.postValue(0)
                        System.out.println(data)
                        if (action == "getAll" || action == "delete") {
                            responseLiveRequests.postValue(data)
                        } else if (action == "getAllPending" || action == "update") {

                            PendingResponseLiveRequests.postValue(data)

                        } else {
                            responseLive.postValue(data)

                        }


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                       // Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })

    }



    fun getPendingRequests(action: String?,
                           date: String,
                           id: String,
                           name: String,
                           typeRequest: String?,
                           requestStartIn: String,
                           requestEndIn: String?,
                           notes: String,
                           managerId: String,
                           status: String,
                           comment: String,
                           approvalDate: String,
                           code: String) {


        progressGoogle.postValue(1)
        myAPI?.googleSheetRequest("HR Requests" ,action!!, date, id, name, typeRequest!!, requestStartIn, requestEndIn!!, notes, managerId, status, comment, approvalDate, code)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        System.out.println("GoogleRequestResponse " + data.toString())
                        progressGoogle.postValue(0)
                        PendingResponseLiveRequests.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progressGoogle.postValue(0)
                        println("onError " + e.message)
                        System.out.println("onError " +e.message)
                       // Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })

    }


    fun getAllPending() {


        progress.postValue(1)
        myAPI?.googleSheetRequest("All" ,"", "", "", "", "", "","", "", dataManager.user.empId.toString(), "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                         progress.postValue(0)
                        PendingResponseLiveRequests.postValue(data)


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