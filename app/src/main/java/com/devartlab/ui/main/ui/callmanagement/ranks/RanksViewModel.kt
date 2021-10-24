package com.devartlab.ui.main.ui.callmanagement.ranks

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
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
import com.devartlab.data.room.filterdata.FilterDataEntity
import retrofit2.Retrofit

class RanksViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var myAPI: ApiServices
    lateinit var dataManager: DataManager
    lateinit var retrofit: Retrofit

    lateinit var progress: MutableLiveData<Int>
    lateinit var responseLive: MutableLiveData<ResponseModel>
    lateinit var responseLiveDetails: MutableLiveData<ResponseModel>
    lateinit var filterSpecialityResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>


    init {

        responseLive = MediatorLiveData()
        responseLiveDetails = MediatorLiveData()
        filterSpecialityResponseLive = MediatorLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        progress = MutableLiveData()

    }


    fun getMRRank(id: String, fromDate: String, toDate: String, pParentSpecialityIdStr: String, groupLevel: Int, cycleID: Int, activityIdStr: String, search: String) {
        progress.postValue(1)
        myAPI?.getMRRankReport(id, fromDate, toDate, pParentSpecialityIdStr,
                groupLevel, cycleID, activityIdStr, 1, search, 1000)!!
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

    public fun getFilterSpeciality(id: String, tableName: String, whereCondition: String, filterText: String) {

        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {

                        filterSpecialityResponseLive.postValue(data)
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {}
                })
    }


    fun getMRRankReport(id: String, fromDate: String, toDate: String, groupLevel: Int, cycleID: Int, activityIdStr: String, search: String, empId: Int) {
        progress.postValue(1)
        myAPI?.getMRRankReportDetails(id, fromDate, toDate,
                groupLevel, cycleID, activityIdStr, 1, search, 1000, empId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ResponseModel) {
                        progress.postValue(0)
                        responseLiveDetails.postValue(body)

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


    fun getSVRank(id: String, cycleID: Int) {
        progress.postValue(1)
        myAPI?.getSvRankReport(id, cycleID)!!
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


    fun getDVReport(fromDate: String, toDate: String, svIdStr: String) {
        progress.postValue(1)
        myAPI?.getDVReport(fromDate, toDate, svIdStr)!!
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

    fun getDVDetails(fromDate: String, toDate: String, empId: String) {
        progress.postValue(1)
        myAPI?.getDVDetails(fromDate, toDate, empId)!!
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

    fun getStartPointReport(id: String, date: String, shiftId: String) {
        progress.postValue(1)
        myAPI?.getStartPointReport(id, date, shiftId)!!
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

    fun getPlanAndCoverReport(pCustomerTypeIdStr: String, pEmployeeIdStr: String, pFromDate: String, pToDate: String, pCycleId: Int) {
        progress.postValue(1)
        myAPI?.getPLanAndCover(pCustomerTypeIdStr, pEmployeeIdStr, pFromDate, pToDate, pCycleId)!!
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

}