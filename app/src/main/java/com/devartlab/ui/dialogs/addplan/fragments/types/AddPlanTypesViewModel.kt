package com.devartlab.ui.main.ui.callmanagement.list.fragments.types

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import retrofit2.Retrofit

class AddPlanTypesViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    val responseLive: MutableLiveData<ArrayList<SpecialtyParentEntity>>
    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<ArrayList<SpecialtyParentEntity>>()
        progress = MutableLiveData<Int>()

        getCustomerListType()
    }


    public fun getCustomerListType() {

        progress.postValue(1)
        myAPI?.getCustomerListType(dataManager.user.accId )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<SpecialtyParentEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<SpecialtyParentEntity>) {

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