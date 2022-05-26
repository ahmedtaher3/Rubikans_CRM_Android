package com.devartlab.ui.main.ui.callmanagement.report

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
import com.devartlab.data.room.purchasetype.PurchaseTypeDao
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import com.devartlab.data.room.slidetimer.SlideTimerDao
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.data.room.startPoint.StartPointEntity
import com.devartlab.data.room.values.ValuesDao
import com.devartlab.data.room.visit.VisitEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.data.source.plan.PlanRepository
import com.devartlab.data.source.values.ValuesRepository
import com.devartlab.model.*
import com.devartlab.utils.CommonUtilities
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val TAG = "ReportViewModel"
class ReportViewModel(application: Application) : AndroidViewModel(application) {

    var valuesRepository: ValuesRepository? = null
    var planRepository: PlanRepository? = null


    var visits: LiveData<List<VisitEntity>>
    val responseActivitiesLive: MutableLiveData<ArrayList<ActivityEntity>>

    var model: PlanEntity

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    var allPlansByDate: MutableLiveData<List<PlanEntity>>


    var planDao: PlanDao? = null
    var slideTimerDao: SlideTimerDao? = null
    var startPointDao: StartPointDao? = null
    var valuesDao: ValuesDao? = null
    var activityDao: ActivityDao? = null
    var purchaseTypeDao: PurchaseTypeDao? = null

    var dataManager: DataManager
    val responseLive: MutableLiveData<Plan>
    val responseLivePlanEntity: MutableLiveData<PlanEntity>
    val responseLiveConfiermShift: MutableLiveData<ResponseModel>
    val responseLiveEndShift: MutableLiveData<ResponseModel>
    val typsLive: MutableLiveData<java.util.ArrayList<PurchaseTypeEntity>>
    val responseLiveIsStarted: MutableLiveData<StartPointEntity>
    val progress: MutableLiveData<Int>
    val showText: MutableLiveData<String>


    init {


        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)

        planDao = DatabaseClient.getInstance(application)?.appDatabase?.planDao()
        valuesDao = DatabaseClient.getInstance(application)?.appDatabase?.valuesDao()
        slideTimerDao = DatabaseClient.getInstance(application)?.appDatabase?.slideTimerDao()
        startPointDao = DatabaseClient.getInstance(application)?.appDatabase?.startPointDao()
        activityDao = DatabaseClient.getInstance(application)?.appDatabase?.activityDao()
        purchaseTypeDao = DatabaseClient.getInstance(application)?.appDatabase?.purchaseTypeDao()

        showText = MediatorLiveData()
        allPlansByDate = MediatorLiveData()
        responseLivePlanEntity = MediatorLiveData()
        responseLive = MutableLiveData<Plan>()
        responseLiveConfiermShift = MutableLiveData()
        typsLive = MutableLiveData()
        responseLiveEndShift = MutableLiveData()
        responseLiveIsStarted = MutableLiveData()
        progress = MutableLiveData<Int>()

        model = PlanEntity()
        responseActivitiesLive = MutableLiveData<ArrayList<ActivityEntity>>()
        visits = MediatorLiveData()

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
        Completable.fromAction {
            planDao?.update(plan)
            dataManager?.saveSyncAble(false)
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun updateModel(plan: PlanEntity?) {
        Completable.fromAction {
            planDao?.update(plan)
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }


    fun delete(plan: PlanEntity?, date: String?, shift: String?) {

        Completable.fromAction {
            planDao?.delete(plan)
        }.subscribeOn(Schedulers.io())
            .subscribe()

        getAllByDateAndShift(date, shift, "delete")


    }

    fun getInvoiceTyps() {


        if (dataManager.offlineMood) {
            Completable.fromAction {
                typsLive.postValue(purchaseTypeDao?.all as java.util.ArrayList<PurchaseTypeEntity>)
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {
            progress.postValue(1)
            myAPI?.getAllType(true)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        progress.postValue(0)
                        typsLive.postValue(data.data.invDefSalesPurchaseType)
                        Log.d(TAG, "onNext: " + data.data.invDefSalesPurchaseType)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Log.d(TAG, "onError: " +e.message)
                    }

                    override fun onComplete() {}
                })
        }


    }

    fun getActivities() {

        if (dataManager.offlineMood) {
            Completable.fromAction {
                responseActivitiesLive.postValue(activityDao?.all as java.util.ArrayList<ActivityEntity>)
            }.subscribeOn(Schedulers.io())
                .subscribe()

        } else {

            progress.postValue(1)
            myAPI?.getUserActivity(dataManager.user.accId!!)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<ActivityEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<ActivityEntity>) {


                        progress.postValue(0)
                        responseActivitiesLive.postValue(data)


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

    fun getAllByDateAndShift(date: String?, shift: String?, number: String?) {

        System.out.println(" dataa   " + date + "  " + shift + "  " + number)

        Single.timer(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : SingleObserver<Long?> {
                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {}
                override fun onSuccess(t: Long) {
                    allPlansByDate.postValue(planDao?.getAllByDateAndShift(date, shift))
                }
            })

    }


    fun uploadReport(date: String?, shift: String?, lat: Double, lang: Double, shiftId: Int) {
        progress.postValue(1)

        Completable.fromAction {

            var list = ArrayList<SyncReport>();
            for (model in planDao?.getAllByDateAndShift(date, shift)!!) {


                list.add(
                    SyncReport(
                        model.planDetID,
                        dataManager.user.empId,
                        dataManager.user.accId,
                        model.date,
                        model.shiftId,
                        model.customerid,
                        model.customerBranchid,
                        0,
                        model.visit,
                        model.extraVisit,
                        model.activityId,
                        model.terriotryEmpId,
                        model.terriotryAssigntId,
                        model.terriotryAccountId,
                        model.doubleVAccountId,//
                        model.doubleVAccountIdStr,
                        0,
                        false,
                        model.cusLat,
                        model.cusLang,
                        model.call1,
                        model.call2,
                        model.call3,
                        model.call4,
                        0,
                        model.meetingMemberId,
                        "",
                        model.taskText
                    )
                )

            }


            var listSlides = ArrayList<SyncSlide>();
            for (model in slideTimerDao?.allByID!!) {
                com.orhanobut.logger.Logger.d(model)
                System.out.println(model.toString())

                listSlides.add(
                    SyncSlide(
                        model.planDetId,
                        model.shiftId,
                        model.salesRptDate,
                        model.accountId,
                        model.customerId,
                        model.branchId,
                        model.isExtraVisit!!,
                        model.callIndex,
                        model.itemId,
                        model.messageId,
                        model.customMessage,
                        model.customMessageNotes,
                        model.slideTimeinSec,
                        model.slideId
                    )
                )
            }


            var listApprizal = ArrayList<AppraisalBuildsSchema>();


            val test = DailyReportModel()
            test.evaluationList = listApprizal
            test.employeeDailyActivityList = list
            test.callEdetailingList = listSlides


            val appraisalBuildsSchema = Gson().toJsonTree(test).asJsonObject

            System.out.println(appraisalBuildsSchema.toString())



            myAPI?.uploadReport(
                true,
                shiftId,
                CommonUtilities.convertDateToMillis(date).toString(),
                dataManager.user.empId,
                dataManager.user.accId,
                "android",
                lat,
                lang,
                appraisalBuildsSchema
            )!!
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ResponseModel) {
                        progress.postValue(0)
                        responseLiveEndShift.postValue(body)
                        System.out.println(" uploadReport " + body)

                        slideTimerDao?.deleteAll()
                        for (model in planDao?.getAllByDateAndShiftExceptDouble(date, shift)!!) {
                            model.reported = true
                            planDao?.update(model)
                        }
                        startPointDao?.insert(StartPointEntity(date, shift, true, true))

                    }

                    override fun onError(e: Throwable) {
                        System.out.println(" uploadReport " + e.message)
                        progress.postValue(0)
                        showText.postValue(e.message)

                    }

                    override fun onComplete() {

                    }
                })


        }.subscribeOn(Schedulers.io())
            .subscribe()


    }


/*
    public fun syncReport(date: String?, shift: String?, SalesRptDateInMsFormat: Long, lat: Double, lng: Double, shiftId: Int) {
        progress.postValue(1)

        Completable.fromAction {

            var list = ArrayList<SyncReport>();

            for (model in planDao?.getAllByDateAndShiftExceptDouble(date, shift)!!) {


                list.add(SyncReport(
                        model.planDetID,
                        dataManager.user.empId,
                        dataManager.user.accId,
                        model.date,
                        model.shiftId,
                        model.customerid,
                        model.customerBranchid,
                        0,
                        model.visit,
                        model.extraVisit,
                        model.activityId,
                        model.terriotryEmpId,
                        model.terriotryAssigntId,
                        model.terriotryAccountId,
                        model.doubleVAccountId,//
                        model.doubleVAccountIdStr,
                        0,
                        false,
                        model.cusLat,
                        model.cusLang,
                        model.call1,
                        model.call2,
                        model.call3,
                        model.call4,
                        0,
                        model.meetingMemberId,
                        "" + model.notes,
                        "" + model.taskText + model.officeDescription))

            }
            val jsonArray1 = Gson().toJsonTree(list).asJsonArray

            val maxLogSize = 1000
            for (i in 0..jsonArray1.toString().length / maxLogSize) {
                val start = i * maxLogSize
                var end = (i + 1) * maxLogSize
                end = if (end > jsonArray1.toString().length) jsonArray1.toString().length else end
                System.out.println("Sync Report List " + jsonArray1.toString().substring(start, end))
            }


            sendReport(jsonArray1, date, shift, SalesRptDateInMsFormat, lat, lng, shiftId)

        }.subscribeOn(Schedulers.io())
                .subscribe()


    }

    public fun sendReport(array: JsonArray?, date: String?, shift: String?, SalesRptDateInMsFormat: Long, lat: Double, lng: Double, shiftId: Int) {

        println("date = :" + date + " shift = :" + shift + " lat = :" + lat + " lng = :" + lng)
        myAPI?.syncReportCustomer(dataManager.user.accId, "android", SalesRptDateInMsFormat, lat, lng, dataManager.user.empId, shiftId, array)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : Observer<java.util.ArrayList<SyncReportMassage>> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onNext(body: java.util.ArrayList<SyncReportMassage>) {
                                System.out.println(body)



                                Completable.fromAction {

                                    var list = ArrayList<SyncSlide>();
                                    for (model in slideTimerDao?.allByID!!) {
                                        com.orhanobut.logger.Logger.d(model)
                                        System.out.println(model.toString())

                                        list.add(SyncSlide(
                                                model.planDetId,
                                                model.shiftId,
                                                model.salesRptDate,
                                                model.accountId,
                                                model.customerId,
                                                model.branchId,
                                                model.isExtraVisit,
                                                model.callIndex,
                                                model.itemId,
                                                model.messageId,
                                                model.customMessage,
                                                model.customMessageNotes,
                                                model.slideTimeinSec,
                                                model.slideId
                                        ))
                                    }

                                    val jsonArray1 = Gson().toJsonTree(list).asJsonArray
                                    println("Slides =  " + jsonArray1.toString())
                                    syncSlides(jsonArray1, body[0].retVaLasInteger, date, shift)


                                }.subscribeOn(Schedulers.io())
                                        .subscribe()


                            }

                            override fun onError(e: Throwable) {
                                progress.postValue(0)
                                println("SyncReportCustomer onError" + e.message)
                                System.out.println(e.message)
                                Toast.makeText(getApplication(), "sendReport" + e.message, Toast.LENGTH_LONG).show()


                            }

                            override fun onComplete() {
                                progress.postValue(0)

                            }
                        })

    }

    public fun syncSlides(array: JsonArray?, salesReport: Int, date: String?, shift: String?) {

        System.out.println(array.toString())
        myAPI?.syncReportSlids(dataManager.user.accId, salesReport, array)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<java.util.ArrayList<SyncReportMassage>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ArrayList<SyncReportMassage>) {

                        if (body[0].status) {
                            Toast.makeText(getApplication(), body[0].message, Toast.LENGTH_SHORT).show()
                            Completable.fromAction {
                                slideTimerDao?.deleteAll()
                                for (model in planDao?.getAllByDateAndShiftExceptDouble(date, shift)!!) {
                                    model.reported = true
                                    planDao?.update(model)
                                }
                                startPointDao?.insert(StartPointEntity(date, shift, true))
                            }.subscribeOn(Schedulers.io())
                                    .subscribe()
                        }

                        responseLiveEndShift.postValue(body)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                        Toast.makeText(getApplication(), "syncSlides" + e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {
                        progress.postValue(0)
                    }
                })

    }
*/

    public fun confirmStartPoint(
        salesRptDateInMsFormat: String?,
        shiftID: String,
        startPointTimeMs: String,
        startPointId: String,
        startPointBranchId: String,
        lat: String,
        long: String,
        isStart: Boolean,
        startTime: String
    ) {
        progress.postValue(1)

        System.out.println("API DATA" + dataManager.user.accId.toString() + "\n" + salesRptDateInMsFormat + "\n" + shiftID + "\n" + startPointTimeMs + "\n" + startPointId + "\n" + startPointBranchId + "\n" + isStart.toString() + "\n" + startTime)

        myAPI?.confirmStartPoint(
            dataManager.user.accId,
            salesRptDateInMsFormat!!,
            shiftID,
            startPointTimeMs,
            startPointId,
            startPointBranchId,
            lat,
            long,
            isStart,
            startTime
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(body: ResponseModel) {
                    System.out.println(body.toString())
                    progress.postValue(0)

                    if (isStart) {
                        responseLiveConfiermShift.postValue(body)
                    } else {
                        responseLiveEndShift.postValue(body)
                    }

                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    System.out.println(e.message)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {}
            })

    }


    fun saveStartShift(date: String?, shift: String?, isEnded: Boolean, isUploaded: Boolean) {
        Completable.fromAction {
            startPointDao?.insert(StartPointEntity(date, shift, isEnded, isUploaded))
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }


    fun updateIsStarted(date: String?, shift: String?, isEnded: Boolean, isUploaded: Boolean) {
        Completable.fromAction {

            System.out.println(" updateIsStarted " + date + shift)

            val model = startPointDao?.getAll(date, shift)
            model?.isEnded = isEnded
            model?.isUploaded = isUploaded
            startPointDao?.update(model)
            getAllByDateAndShift(date, shift, "541")

        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun isStarted(date: String?, shift: String?, line: Int) {
        System.out.println(" isStarted  " + line.toString())

        Completable.fromAction {
            responseLiveIsStarted.postValue(startPointDao?.getAll(date, shift))
        }.subscribeOn(Schedulers.io())
            .subscribe()

    }

    fun reportShift(date: String?, shift: String?) {

        Completable.fromAction {

            slideTimerDao?.deleteAll()
            for (model in planDao?.getAllByDateAndShiftExceptDouble(date, shift)!!) {
                model.reported = true
                planDao?.update(model)
            }
            startPointDao?.insert(StartPointEntity(date, shift, true, true))


            val model = startPointDao?.getAll(date, shift)
            model?.isEnded = true
            model?.isUploaded = true
            startPointDao?.update(model)

            getAllByDateAndShift(date, shift, "574")


        }.subscribeOn(Schedulers.io())
            .subscribe()

    }

}