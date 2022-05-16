package com.devartlab.data.source.plan

import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.data.room.startPoint.StartPointEntity
import com.devartlab.data.room.values.ValuesEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.data.source.values.ValuesRepository
import com.devartlab.model.Shift
import com.devartlab.model.SyncReportMassage
import com.devartlab.utils.CommonUtilities
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PlanRepository private constructor
    (
    private val planDao: PlanDao,
    private val myAPI: ApiServices,
    private var dataManager: DataManager,
    private val valuesRepository: ValuesRepository,
    private val startPointDao: StartPointDao
) : PlanRepositoryInterface<PlanEntity?> {
    companion object {
        @Volatile
        private var INSTANCE: PlanRepository? = null

        fun getInstance(
            planDao: PlanDao,
            myAPI: ApiServices,
            dataManager: DataManager,
            valuesRepository: ValuesRepository,
            startPointDao: StartPointDao
        ): PlanRepository? {
            if (INSTANCE == null) {
                synchronized(PlanRepository::class.java) {
                    INSTANCE =
                        PlanRepository(planDao, myAPI, dataManager, valuesRepository, startPointDao)
                }
            }
            return INSTANCE
        }
    }


    override fun add(item: PlanEntity?): Observable<Boolean> {
        return Observable.fromCallable {
            planDao.insertAll(item)
            true
        }
    }

    override fun update(item: PlanEntity?): Observable<Boolean> {
        return Observable.fromCallable {
            planDao.update(item)
            true
        }
    }

    override fun remove(item: PlanEntity?): Observable<Boolean> {

        return Observable.fromCallable {
            planDao.delete(item)
            true
        }
    }

    override fun removeDouble(
        DoubleVAccountId: String,
        shift: String,
        date: String
    ): Observable<Boolean> {
        return Observable.fromCallable {

            true
        }
    }

    override fun getAll(): Flowable<List<PlanEntity>> {
        return planDao.allPlan
    }

    override fun getAllByDateAndShift(date: String, shift: String): List<PlanEntity> {

        return planDao.getAllByDateAndShift(date, shift)
    }

    override fun getPlan(userId: Int): Flowable<PlanEntity> {
        return planDao.getAllById2(userId.toString())
    }


    fun getPlanOnline(onPlanData: OnPlanData) {


        myAPI?.getPlan(dataManager.user.accId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {

                    removeAll().subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe({ success: Boolean ->
                            if (success) {
                                val list = ArrayList<PlanEntity>()
                                for (model in data.data.planData!!) {
                                    var planEntity = PlanEntity(
                                        model.pLanAccountId,
                                        model.planCycleId,
                                        model.cycleArName,
                                        model.fromDate,
                                        model.toDate,
                                        model.shift,
                                        model.day,
                                        model.date.take(10),
                                        model.activityEnName,
                                        model.doubleVisitEmpName,
                                        model.startPoint,
                                        model.brick,
                                        model.cusSerial,
                                        model.customerName,
                                        model.speciality,
                                        model.class_,
                                        model.branchType,
                                        model.placeName,
                                        model.address,
                                        model.notes,
                                        model.eventsEnName,
                                        model.eventDescription,
                                        model.taskText,
                                        model.officeDescription,
                                        model.meetingMembers,
                                        model.customerid,
                                        model.customerBranchid,
                                        model.branchPlaceId,
                                        model.callObjectives,
                                        model.activityId,
                                        model.shiftId,
                                        model.startPointId,
                                        model.terriotryEmpId,
                                        model.eventsId,
                                        model.meetingMemberId,
                                        model.isReported,
                                        model.terriotryAssigntId,
                                        model.terriotryAccountId,
                                        model.doubleVAccountId,
                                        model.doubleVAccountIdStr,
                                        model.brickId,
                                        model.territoryId,
                                        model.isVisit,
                                        model.isExtraVisit,
                                        model.cusLat,
                                        model.cusLang,
                                        0,
                                        0,
                                        0,
                                        0,
                                        false,
                                        "",
                                        model.planId,
                                        model.planDetID,
                                        CommonUtilities.randomColor,
                                        model.activityTypeID,
                                        false,
                                        true,
                                        false,
                                        ""
                                    )
                                    list.add(planEntity)
                                }
                                planDao?.insertAllEntities(list)



                                if (!data.data.startPointData.isNullOrEmpty()) {

                                    val model = data.data.startPointData[0]

                                    if (model.isStartPoint) {


                                        var shift = ""
                                        when (model.shiftId) {
                                            8 -> shift = "AM Shift"
                                            9 -> shift = "PM Shift"
                                        }
                                        dataManager.saveStartShift(true, 1)
                                        dataManager.saveShift(
                                            Shift(
                                                model.shiftId,
                                                shift,
                                                CommonUtilities.convertDateToMillis(
                                                    model.salesRptDate.take(10)
                                                ).toString(),
                                                model.salesRptDate.take(10),
                                                false
                                            )
                                        )

                                        val valuesEntity = ValuesEntity(
                                            1,
                                            true,
                                            false,
                                            "",
                                            CommonUtilities.convertDateToMillis(
                                                model.salesRptDate.take(10)
                                            ),
                                            shift,
                                            model.shiftId,
                                            model.salesRptDate.take(10)
                                        )
                                        valuesRepository?.insert(valuesEntity)
                                        startPointDao?.insert(
                                            StartPointEntity(
                                                model.salesRptDate.take(
                                                    10
                                                ), shift, false, false
                                            )
                                        )

                                    }
                                }


                            } else {
                            }
                        }) { throwable: Throwable? -> }

                    onPlanData.onSuccess()
                }

                override fun onError(e: Throwable) {
                    onPlanData.onFailure(e.message!!)
                }

                override fun onComplete() {}
            })


    }

    fun updatePlan(onPlanData: OnPlanData, date: String) {


    }

    fun SyncReport(onReportSynced: OnReportSynced, date: String?, shift: String?) {


    }

    interface OnPlanData {
        fun onSuccess()
        fun onFailure(error: String)
    }

    interface OnReportSynced {
        fun onSuccess(list: ArrayList<SyncReportMassage>)
        fun onFailure(error: String)
    }

    override fun removeAll(): Observable<Boolean> {

        return Observable.fromCallable {
            planDao.deleteTable()
            true
        }

    }

    override fun addList(item: MutableList<PlanEntity?>?): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}