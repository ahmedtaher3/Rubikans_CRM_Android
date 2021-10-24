package com.devartlab.ui.main.ui.market

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

class MarketRequestViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    val responseLive: MutableLiveData<ResponseModel>
    val responseLiveUpdate: MutableLiveData<ResponseModel>
    val responseLiveDetails: MutableLiveData<ResponseModel>
    val responseLiveApprove: MutableLiveData<ResponseModel>

    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<ResponseModel>()
        responseLiveUpdate = MutableLiveData<ResponseModel>()
        responseLiveDetails = MutableLiveData<ResponseModel>()
        responseLiveApprove = MutableLiveData<ResponseModel>()

        progress = MutableLiveData<Int>()


    }


    fun getData(pMarkReqTypeId: Int, accountId: Int, addAccountId: String) {

        var addID = addAccountId

        if (addAccountId.isNullOrEmpty()) {
            addID = "0," + accountId
        } else {
            addID = accountId.toString() + "," + addAccountId
        }



        progress.postValue(1)
        myAPI?.getPendingRequests(addID, accountId, pMarkReqTypeId)!!
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

    fun getRequestDetails(pRequestId: Int, pRequestApprovedId: Int) {


        progress.postValue(1)
        myAPI?.showRequestDetails(pRequestId, pRequestApprovedId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {


                        progress.postValue(0)
                        responseLiveDetails.postValue(data)


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


    fun sendRequestsApproval(
            pCurrentFlowdetIdStr: String?,
            pApproved: Boolean,
            pDeny: Boolean,
            pArchiving: Boolean,
            pNotes: String?,
            AccountId: Int,
            pMacData: String?
    ) {


        progress.postValue(1)
        myAPI?.sendRequestsApproval(pCurrentFlowdetIdStr!!, pApproved, pDeny, pArchiving, pNotes!!, AccountId, pMacData!!)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {


                        progress.postValue(0)
                        responseLiveApprove.postValue(data)


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