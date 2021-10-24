package com.devartlab.ui.main.ui.employeeservices.approval

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.GoogleRequestResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class ApproveRequestsViewModel(application: Application) : AndroidViewModel(application) {


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
        retrofit = RetrofitClient.getInstanceGoogleSheet()
        myAPI = retrofit!!.create(ApiServicesGoogle::class.java)
        responseLive = MutableLiveData<GoogleRequestResponse>()
        responseLiveRequests = MutableLiveData<GoogleRequestResponse>()
        PendingResponseLiveRequests = MutableLiveData<GoogleRequestResponse>()


        progress = MutableLiveData<Int>()
        progressGoogle = MutableLiveData<Int>()


    }

    /////////////////////////////////////////////////////////


    fun getRequests(
        action: String?,
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
        code: String
    ) {


        progress.postValue(1)
        myAPI?.googleSheetRequest(
            "HR Requests",
            action!!,
            date,
            id,
            name,
            typeRequest!!,
            requestStartIn,
            requestEndIn!!,
            notes,
            managerId,
            status,
            comment,
            approvalDate,
            code
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GoogleRequestResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: GoogleRequestResponse) {

                    progress.postValue(0)
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


    fun getPendingRequests(
        action: String?,
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
        code: String
    ) {


        progressGoogle.postValue(1)
        myAPI?.googleSheetRequest(
            "HR Requests",
            action!!,
            date,
            id,
            name,
            typeRequest!!,
            requestStartIn,
            requestEndIn!!,
            notes,
            managerId,
            status,
            comment,
            approvalDate,
            code
        )!!
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
                    println(e.message)
                    System.out.println(e.message)
                    // Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {}
            })

    }


    fun getAll(action: String, empId: String) {


        myAPI?.googleSheetRequest(
            "All",
            action,
            "",
            empId,
            "",
            "",
            "",
            "",
            "",
            dataManager.user.empId.toString(),
            "",
            "",
            "",
            ""
        )!!
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


    fun approve(
        sheet: String?,
        action: String?,
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
        code: String
    ) {


        progress.postValue(1)
        myAPI?.googleSheetRequest(
            sheet!!,
            action!!,
            date,
            id,
            name,
            typeRequest!!,
            requestStartIn,
            requestEndIn!!,
            notes,
            managerId,
            status,
            comment,
            approvalDate,
            code
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GoogleRequestResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: GoogleRequestResponse) {

                    if (data.isSuccessful) {
                        getAll("allPending", "")
                    } else {
                        progress.postValue(0)
                    }


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

    fun approveAll(list: ArrayList<ApproveModel>) {


        var i = 0
        for (m in list) {


            if (i++ == list.size - 1) {
                progress.postValue(1)
            }

            myAPI?.googleSheetRequest(
                m.sheet!!,
                m.action!!,
                m.date,
                m.id,
                m.name,
                m.typeRequest!!,
                m.requestStartIn,
                m.requestEndIn!!,
                m.notes,
                m.managerId,
                m.status,
                m.comment,
                m.approvalDate,
                m.code
            )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        if (data.isSuccessful) {
                            getAll("allPending", "")
                        } else {
                            progress.postValue(0)
                        }


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
}