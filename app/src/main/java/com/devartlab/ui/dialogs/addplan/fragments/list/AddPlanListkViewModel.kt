package com.devartlab.ui.main.ui.callmanagement.list.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.CustomerList
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import retrofit2.Retrofit

class AddPlanListkViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    val responseLive: MutableLiveData<ArrayList<CustomerList>>
    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<ArrayList<CustomerList>>()
        progress = MutableLiveData<Int>()



    }


    public fun getCustomerList(id :String) {

        progress.postValue(1)
        myAPI?.getCustomerList(dataManager.user.accId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<CustomerList>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<CustomerList>) {

                        progress.postValue(0)
                        responseLive.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)

                    }

                    override fun onComplete() {}
                })

    }

}