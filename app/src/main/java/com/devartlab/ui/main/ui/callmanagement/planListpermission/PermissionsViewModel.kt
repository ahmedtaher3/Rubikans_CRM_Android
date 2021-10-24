package com.devartlab.ui.main.ui.callmanagement.planListpermission

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import retrofit2.Retrofit

class PermissionsViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var myAPI: ApiServices
    lateinit var dataManager: DataManager
    lateinit var retrofit: Retrofit

    lateinit var progress: MutableLiveData<Int>
    lateinit var responseLive: MutableLiveData<ResponseModel>


    init {

        responseLive = MediatorLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        progress = MutableLiveData()

    }


    fun getListPermissions(id: Int) {
        progress.postValue(1)
        myAPI?.updateListPermission(id)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ResponseModel) {
                        progress.postValue(0)
                        responseLive.postValue(body)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {
                        progress.postValue(0)
                    }
                })

    }

    fun getPlanPermissions(id: Int) {
        progress.postValue(1)
        myAPI?.updatePlanPermission(id)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ResponseModel) {
                        progress.postValue(0)
                        responseLive.postValue(body)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {
                        progress.postValue(0)
                    }
                })

    }


    fun updateListPermissions(list: ArrayList<ListPermissionData> , accId: Int, empId: Int) {
        progress.postValue(1)

        val newList = ArrayList<OpenPlanListModal>()

        for (m in list) {
            if (m.allowToDeleteCustomer || m.allowToAddCustomer || m.allowToEdit)
                newList.add(OpenPlanListModal(
                        m.listId,
                        m.assigntId,
                        accId,
                        empId,
                        m.allowToEdit,
                        m.allowToAddCustomer,
                        m.allowToDeleteCustomer,
                        m.expireDate,
                        dataManager.user.accId,
                        dataManager.user.empId,
                        "Android"
                ))
        }

        val listBuildsSchema = Gson().toJsonTree(newList).asJsonArray



        myAPI?.openPlanList(listBuildsSchema)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ResponseModel) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {
                        progress.postValue(0)
                    }
                })

    }

    fun updatePlanPermissions(list: ArrayList<PlanDayPermissionData> , empId:Int) {
        progress.postValue(1)

        val newList = ArrayList<OpenPlanModel>()

        for (m in list) {
            if (m.oPenDay)
                newList.add(OpenPlanModel(
                        m.planId,
                        m.accountId,
                        empId,
                        dataManager.user.accId,
                        dataManager.user.empId,
                        m.expireDate,
                        m.date
                ))
        }

        val listBuildsSchema = Gson().toJsonTree(newList).asJsonArray

        System.out.println(listBuildsSchema.toString())

        myAPI?.openPlanDay(listBuildsSchema)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ResponseModel) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {
                        progress.postValue(0)
                    }
                })

    }
}