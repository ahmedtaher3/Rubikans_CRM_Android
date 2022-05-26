package com.devartlab.ui.main.ui.callmanagement.plan

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.activity.ActivityDao
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.slidetimer.SlideTimerDao
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.data.room.values.ValuesDao
import com.devartlab.data.shared.DataManager
import com.devartlab.data.source.plan.PlanRepository
import com.devartlab.data.source.values.ValuesRepository
import com.devartlab.model.Plan
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.PlanUtlis
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private const val TAG = "PlanViewModel"

class PlanViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null


    val responseLive: MutableLiveData<Plan>
    val responseLiveData: MutableLiveData<List<PlanEntity>>
    val responseActivitiesLive: MutableLiveData<ArrayList<ActivityEntity>>
    val responsePermissionAdd: MutableLiveData<ResponseModel>
    val responsePermissionEdit: MutableLiveData<ResponseModel>
    val responsePermissionStartPoint: MutableLiveData<ResponseModel>
    val progress: MutableLiveData<Int>

    var fmt: SimpleDateFormat? = null
    var planDao: PlanDao? = null
    var slideTimerDao: SlideTimerDao? = null
    var valuesDao: ValuesDao? = null
    var valuesRepository: ValuesRepository? = null
    var planRepository: PlanRepository? = null
    var startPointDao: StartPointDao? = null
    var activityDao: ActivityDao? = null

    init {

        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        planDao = DatabaseClient.getInstance(application)?.appDatabase?.planDao()
        slideTimerDao = DatabaseClient.getInstance(application)?.appDatabase?.slideTimerDao()
        startPointDao = DatabaseClient.getInstance(application)?.appDatabase?.startPointDao()
        valuesDao = DatabaseClient.getInstance(application)?.appDatabase?.valuesDao()
        activityDao = DatabaseClient.getInstance(application)?.appDatabase?.activityDao()
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<Plan>()
        responseActivitiesLive = MutableLiveData<ArrayList<ActivityEntity>>()
        responseLiveData = MutableLiveData<List<PlanEntity>>()
        responsePermissionAdd = MutableLiveData<ResponseModel>()
        responsePermissionStartPoint = MutableLiveData<ResponseModel>()
        responsePermissionEdit = MutableLiveData<ResponseModel>()
        progress = MutableLiveData<Int>()




        if (valuesRepository == null) {
            valuesRepository = ValuesRepository.getInstance(valuesDao)
        }

        if (planRepository == null) {
            planRepository = PlanRepository.getInstance(
                planDao!!,
                myAPI!!,
                dataManager,
                valuesRepository!!,
                startPointDao!!
            )
        }

    }

    fun syncPlan() {
        progress.postValue(1)
        PlanUtlis.syncPlan(getApplication(), object : PlanUtlis.OnSyncPlan {
            override fun onSuccess() {


                Single.timer(1, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Long?> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onError(e: Throwable) {}
                        override fun onSuccess(t: Long) {

                            progress.postValue(0)
                            Toast.makeText(getApplication(), "Success", Toast.LENGTH_LONG).show()
                        }
                    })


            }

            override fun onError() {
                TODO("Not yet implemented")
            }

            override fun onFailure(error: String) {

                Single.timer(1, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Long?> {
                        override fun onSubscribe(d: Disposable) {}

                        override fun onError(e: Throwable) {}
                        override fun onSuccess(t: Long) {

                            progress.postValue(0)
                            Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show()
                        }
                    })


            }
        })

    }

    fun updatePlan() {

        progress.postValue(1)
        PlanUtlis.updatePlan(
            getApplication(),
            fmt?.format(CommonUtilities.currentToMillis)!!,
            object : PlanUtlis.OnSyncPlan {
                override fun onSuccess() {


                    Single.timer(1, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {

                                progress.postValue(1000)
                                Toast.makeText(getApplication(), "Success", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })


                }

                override fun onError() {
                    Single.timer(1, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {
                                progress.postValue(2)
                            }
                        })
                }

                override fun onFailure(error: String) {

                    Single.timer(1, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {

                                progress.postValue(0)
                                Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show()
                            }
                        })


                }
            }, dataManager)


    }


    fun getDayPlan(date: String, shift: String) {

        Completable.fromAction {
            responseLiveData.postValue(planRepository?.getAllByDateAndShift(date, shift))
        }.subscribeOn(Schedulers.io())
            .subscribe()

    }

    fun getMonthPlan(date: String, shift: String) {


        Completable.fromAction {

            responseLiveData.postValue(planRepository?.getAllByDateAndShift(date, shift))
        }.subscribeOn(Schedulers.io())
            .subscribe()

    }

    fun getActivities() {


        if (dataManager.offlineMood) {
            Completable.fromAction {
                responseActivitiesLive.postValue(activityDao?.all as ArrayList<ActivityEntity>)
            }.subscribeOn(Schedulers.io())
                .subscribe()

        }
        else
        {
            progress.postValue(4)
            myAPI?.getUserActivity(dataManager.user.accId!!)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<ActivityEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<ActivityEntity>) {

                        progress.postValue(5)
                        responseActivitiesLive.postValue(data)
                        Completable.fromAction {

                            for (m in data) {
                                activityDao?.insert(m)
                            }
                        }.subscribeOn(Schedulers.io())
                            .subscribe()


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(5)
                        println(e.message)
                        System.out.println(e.message)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })

        }


    }

    fun insert(plan: PlanEntity?) {


        planRepository?.add(plan)!!.subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe({ success: Boolean ->
                if (success) {
                    dataManager?.saveSyncAble(false)
                } else {
                    Toast.makeText(getApplication(), "Error, Try again", Toast.LENGTH_LONG).show()
                }
            }) { throwable: Throwable? -> }


    }

    fun update(plan: PlanEntity?) {
        planRepository?.update(plan)!!.subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe({ success: Boolean ->
                if (success) {
                    dataManager?.saveSyncAble(false)
                } else {
                    Toast.makeText(getApplication(), "Error, Try again", Toast.LENGTH_LONG).show()
                }
            }) { throwable: Throwable? -> }


    }


    fun delete(model: PlanEntity?, date: String, shift: String) {

        val plan = model
        plan?.deleted = true

        planRepository?.update(plan)!!.subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe({ success: Boolean ->
                if (success) {

                    dataManager?.saveSyncAble(false)
                    getDayPlan(date, shift)
                } else {
                    Toast.makeText(getApplication(), "Error, Try again", Toast.LENGTH_LONG).show()
                }
            }) { throwable: Throwable? -> }


    }

    fun deleteDouble(plan: PlanEntity?, date: String, shift: String) {


        Completable.fromAction {
            for (model in planDao?.getDouble(plan?.doubleVAccountId.toString(), date, shift)!!) {

                val plan = model
                plan.deleted = true

                planDao?.update(plan)
                dataManager?.saveSyncAble(false)
            }


        }.subscribeOn(Schedulers.io())
            .subscribe()

        getDayPlan(date, shift)
    }

    fun getEditPermission(DayDateInMsFormat: String, type: Int) {


        progress.postValue(1)
        myAPI?.GetPLanEditPermition(
            dataManager.user.accId!!,
            dataManager.newOldCycle.currentCyclePlanId!!,
            "true",
            DayDateInMsFormat
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {

                    progress.postValue(0)
                    when (type) {
                        1 -> {
                            responsePermissionAdd.postValue(data)
                        }
                        2 -> {
                            responsePermissionEdit.postValue(data)
                        }
                        3 -> {
                            responsePermissionStartPoint.postValue(data)
                        }
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