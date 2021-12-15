package com.devartlab.utils

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.startPoint.StartPointEntity
import com.devartlab.data.room.values.ValuesEntity
import com.devartlab.model.PlanJson
import com.devartlab.model.Shift
import com.devartlab.model.SyncPlanResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


object PlanUtlis {
    private const val TAG = "PlanUtlis"

    fun syncPlan(application: Application, onPlanData: OnSyncPlan) {


        val retrofit = RetrofitClient.getInstance()
        val myAPI = retrofit!!.create(ApiServices::class.java)
        val dataManager = (application as BaseApplication).dataManager!!
        val planDao = DatabaseClient.getInstance(application)?.appDatabase?.planDao()
        val valuesDao = DatabaseClient.getInstance(application)?.appDatabase?.valuesDao()
        val startPointDao =
            DatabaseClient.getInstance(application)?.appDatabase?.startPointDao()





        myAPI?.getPlan(dataManager.user.accId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {

                    planDao?.deleteTable()
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

                    val text = CommonUtilities.getText()
                    if (!text.isNullOrEmpty()) {
                        val gson = GsonBuilder().create()

                        try {
                            val theList = gson.fromJson<ArrayList<PlanEntity>>(
                                text,
                                object : TypeToken<ArrayList<PlanEntity>>() {}.type
                            )
                            for (i in theList) {


                                val model = planDao?.getModel(
                                    i.date!!,
                                    i.shift!!,
                                    i.customerName!!,
                                    i.customerid!!
                                )
                                i.id = model?.id
                                Log.d(TAG, "onNext: model == " + model.toString())
                                Log.d(TAG, "onNext: i == " + i.toString())


                               if (i.planAccountId == dataManager.user.accId || i.planAccountId == dataManager.user.empId)
                               {
                                   if (i?.extraVisit!!) {



                                       val n = PlanEntity(
                                           i.planAccountId,
                                           i.planCycleId,
                                           i.cycleArName,
                                           i.fromDate,
                                           i.toDate,
                                           i.shift,
                                           i.day,
                                           i.date,
                                           i.activityEnName,
                                           i.doubleVisitEmpName,
                                           i.startPoint,
                                           i.brick,
                                           i.cusSerial,
                                           i.customerName,
                                           i.speciality,
                                           i._class,
                                           i.branchType,
                                           i.placeName,
                                           i.address,
                                           i.notes,
                                           i.eventsEnName,
                                           i.eventDescription,
                                           i.taskText,
                                           i.officeDescription,
                                           i.meetingMembers,
                                           i.customerid,
                                           i.customerBranchid,
                                           i.branchPlaceId,
                                           i.callObjectives,
                                           i.activityId,
                                           i.shiftId,
                                           i.startPointId,
                                           i.terriotryEmpId,
                                           0,
                                           i.meetingMemberId,
                                           false,
                                           i.terriotryAssigntId,
                                           i.terriotryAccountId,
                                           i.doubleVAccountId,
                                           i.doubleVAccountIdStr,
                                           i.brickId,
                                           i.territoryId,
                                           i.extraVisit,
                                           i.extraVisit,
                                           i.cusLat,
                                           i.cusLang,
                                           i.call1,
                                           i.call2,
                                           i.call3,
                                           i.call4,
                                           i.isStarted,
                                           i.startAt,
                                           i.planId,
                                           i.planDetID,
                                           i.planColor,
                                           i.activityTypeID,
                                           false,
                                           false,
                                           false,
                                           ""
                                       )
                                       i.id = 0
                                       Log.d(TAG, "onNext: extraVisit == " + n.toString())

                                       planDao?.insert(n)
                                   } else {
                                       planDao?.update(i)
                                   }
                               }

                            }
                        } catch (e: Exception) {

                        }


                    }

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
                            valuesDao?.insert(valuesEntity)
                            startPointDao?.insert(
                                StartPointEntity(
                                    model.salesRptDate.take(
                                        10
                                    ), shift, false, false
                                )
                            )

                        }
                    }

                    onPlanData.onSuccess()
                }

                override fun onError(e: Throwable) {
                    onPlanData.onFailure(e.message!!)
                }

                override fun onComplete() {}
            })


    }

    fun updatePlan(application: Application, date: String, onPlanData: OnSyncPlan) {


        val retrofit = RetrofitClient.getInstance()
        val myAPI = retrofit!!.create(ApiServices::class.java)
        val dataManager = (application as BaseApplication).dataManager!!
        val planDao = DatabaseClient.getInstance(application)?.appDatabase?.planDao()
        val valuesDao = DatabaseClient.getInstance(application)?.appDatabase?.valuesDao()
        val startPointDao = DatabaseClient.getInstance(application)?.appDatabase?.startPointDao()


        Completable.fromAction {

            if (dataManager.updateDeletedPlan)
            {

                val deletedList = planDao?.getDeletedPlan(date)
                val fullList =
                    planDao?.getListAfterTodayNotDeleted(date)
                for (m in deletedList!!) {

                    if (fullList?.any { it.date == m.date && it.shift == m.shift }!!) {

                    } else {

                        onPlanData.onError()
                        return@fromAction
                    }
                }
            }


            val uploadList = planDao?.getListAfterToday(date)

            var list = ArrayList<PlanJson>();
            for (model in uploadList!!) {

                if (model.terriotryAccountId?.equals(0)!!)
                    model.terriotryAccountId = dataManager?.user?.authorityId

                if (model.terriotryEmpId == null)
                    model.terriotryEmpId = 0

                if (model.branchPlaceId == null)
                    model.branchPlaceId = 0

                System.out.println(" Start At " + model.startAt)

                val model =  PlanJson(
                    model.planId!! + 0,
                    model.date + "",
                    model.customerid!! + 0,
                    model.customerBranchid!! + 0,
                    model.branchPlaceId!! + 0,
                    model.shiftId!! + 0,
                    model.activityId!! + 0,
                    model.startAt + "",
                    true,
                    model.terriotryEmpId!! + 0,
                    model.territoryId!! + 0,
                    model.brickId!! + 0,
                    model.callObjectives + "",
                    model.eventsId!! + 0,
                    "",
                    0 + model.meetingMemberId!!,
                    "",
                    model.startPointId!! + 0,
                    false,
                    model.terriotryAssigntId!! + 0,
                    model.terriotryAccountId!! + 0,
                    model.doubleVAccountId!! + 0,
                    model.doubleVAccountIdStr + "",
                    model.taskText + "",
                    model.officeDescription + "",
                    "",
                    "",
                    0,
                    false,
                    model.deleted!!,
                    model.planDetID!!
                )

                Log.d(TAG, "updatePlan: " + model)

                list.add(
                    model
                );

            }
            val jsonArray1 =
                Gson().toJsonTree(list/*.subList(list.size-5 , list.size)*/).asJsonArray

            Log.d(TAG, "updatePlan: " + jsonArray1.toString())
            val maxLogSize = 1000
            for (i in 0..jsonArray1.toString().length / maxLogSize) {
                val start = i * maxLogSize
                var end = (i + 1) * maxLogSize
                end =
                    if (end > jsonArray1.toString().length) jsonArray1.toString().length else end
                System.out.println(
                    "Sync Report List " + jsonArray1.toString().substring(start, end)
                )
            }


            if (list.size > 0) {
                myAPI?.updatePlan(dataManager?.user?.accId!!, "Android", jsonArray1)!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(object : Observer<SyncPlanResponse> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(body: SyncPlanResponse) {


                            if (body.table1.get(0).status) {


                                val newOldCycle = dataManager?.newOldCycle
                                newOldCycle.currentCyclePlanId = body.table[0].planId
                                dataManager?.saveNewOldCycle(newOldCycle)


                                dataManager?.saveSyncAble(true)
                                planDao?.deleteTableAfterUpdate(date)
                                val list = ArrayList<PlanEntity>()
                                for (model in body.table) {
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


                                onPlanData.onSuccess()
                            } else {
                                onPlanData.onFailure(body.table1.get(0).message)
                            }


                        }

                        override fun onError(e: Throwable) {
                            onPlanData.onFailure(e.message!!)
                        }

                        override fun onComplete() {

                        }
                    })
            } else {
                dataManager?.saveSyncAble(true)
                onPlanData.onFailure("Cannot Update Empty Plan")

            }


        }.subscribeOn(Schedulers.io())
            .subscribe()


    }


    interface OnSyncPlan {
        fun onSuccess()
        fun onError()
        fun onFailure(error: String)
    }
}