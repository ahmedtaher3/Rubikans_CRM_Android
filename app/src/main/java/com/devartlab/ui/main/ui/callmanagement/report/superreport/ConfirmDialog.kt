package com.devartlab.ui.main.ui.callmanagement.report.superreport

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.shared.DataManager
import com.devartlab.model.*
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


class ConfirmDialog(private val activity: AppCompatActivity, context: Context, private val dataManager: DataManager, private val ids: String, private val Date: String, private val Shift: String, private val ShiftID: Int,private val managerReportListener: ManagerReportListener) : Dialog(context) {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var close: ImageView
    lateinit var submitRating: Button
    lateinit var adapter: ItemAdapter
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    var planDao: PlanDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_dialog)
        planDao = DatabaseClient.getInstance(context)?.appDatabase?.planDao()



        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        adapter = ItemAdapter(context, ArrayList(), ArrayList(), ArrayList())

        submitRating = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.submitRating)
        recyclerView = findViewById<RecyclerView>(R.id.rv_item)
        close = findViewById<ImageView>(R.id.close)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)


        close.setOnClickListener(View.OnClickListener { dismiss() })
        submitRating.setOnClickListener(View.OnClickListener {

            if (dataManager.startShift) {
                Toast.makeText(context, context.getString(R.string.you_have_to_start_shift), Toast.LENGTH_SHORT).show()
            } else {


                var database: FirebaseDatabase = FirebaseDatabase.getInstance()
                var storageReference: DatabaseReference = database.getReference().child("Submit Double Report").child(dataManager.user.empId.toString()).child(Date)
                storageReference.child(ShiftID.toString()).setValue(System.currentTimeMillis().toString())


                uploadReport(Date, Shift , 0.0 , 0.0 , ShiftID)

            }


        })

        getAppraisal()
    }


    fun getAppraisal() {

        myAPI?.GetEMployeeDailyReportAppraisalItem(dataManager.user.accId, ids)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        progressBar.visibility = View.GONE
                        val layoutManager = LinearLayoutManager(context)

                        val list = ArrayList<String>()
                        val listIds = ArrayList<Int>()
                        val newList = ArrayList<EmPloyeeAppraisalFlag>()
                        for (model in data.correctiveAction) {
                            list.add(model.correctiveActionName)
                            listIds.add(model.correctiveActionId)
                        }

                        for (model in data.eMPloyeeAppraisal) {
                            val EmPloyeeAppraisalFlag = EmPloyeeAppraisalFlag()

                            EmPloyeeAppraisalFlag.accountId = model.accountId
                            EmPloyeeAppraisalFlag.flag = false
                            EmPloyeeAppraisalFlag.empName = model.empName
                            EmPloyeeAppraisalFlag.seteMPloyeeAppraisal(model.emPloyeeAppraisal)
                            EmPloyeeAppraisalFlag.empid = model.empid
                            EmPloyeeAppraisalFlag.correctiveActions = ""
                            EmPloyeeAppraisalFlag.correctiveActionsID = 0

                            newList.add(EmPloyeeAppraisalFlag)
                        }


                        adapter = ItemAdapter(context, newList,list, listIds)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = layoutManager

                    }

                    override fun onError(e: Throwable) {
                        System.out.println(e.message)
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })

    }

    fun uploadReport(date: String?, shift: String?, lat: Double, lang: Double, shiftId: Int) {
        ProgressLoading.show(activity)
        System.out.println(" uploadReport "+"\n"+date+"\n"+shift+"\n"+lat+"\n"+lang)

        Completable.fromAction {

            var list = ArrayList<SyncReport>();
            for (model in planDao?.getAllByDateAndShift(date, shift)!!) {


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
                        "",
                        model.taskText))

            }


            var listSlides = ArrayList<SyncSlide>();


            var listApprizal = ArrayList<AppraisalBuildsSchema>();
            for (model in adapter.itemList) {

                if (model.correctiveActionsID.toInt() == 0) {
                    activity.runOnUiThread { Toast.makeText(activity, context.getString(R.string.complete_rate), Toast.LENGTH_SHORT).show() }
                    ProgressLoading.dismiss()
                    return@fromAction
                }

                for (model2 in model.geteMPloyeeAppraisal()) {

                    if (model2.rate.toInt() == 0) {
                        activity.runOnUiThread {
                            Toast.makeText(context, context.getString(R.string.complete_rate), Toast.LENGTH_SHORT).show()

                        }
                        ProgressLoading.dismiss()
                        return@fromAction
                    }


                    listApprizal.add(AppraisalBuildsSchema(
                            model2.displayOrder,
                            0,
                            model.empid,
                            0,
                            model.accountId,
                            model2.category,
                            model2.item,
                            model2.rate.toInt(),
                            model2.itemId,
                            model.correctiveActionsID,
                            model.correctiveActionsComment,
                            model2.isCorrectiveAction

                    ))
                }


            }


            val test = DailyReportModel()
            test.evaluationList = listApprizal
            test.employeeDailyActivityList = list
            test.callEdetailingList = listSlides


            val appraisalBuildsSchema = Gson().toJsonTree(test).asJsonObject

            System.out.println(appraisalBuildsSchema.toString())



            myAPI?.uploadReport(false, shiftId, CommonUtilities.convertDateToMillis(date).toString(), dataManager.user.empId, dataManager.user.accId, "android", lat, lang, appraisalBuildsSchema)!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<ResponseModel> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(body: ResponseModel) {

                            ProgressLoading.dismiss()
                            dismiss()
                            Toast.makeText(activity, context.getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
                            managerReportListener.updateData()
                        }

                        override fun onError(e: Throwable) {
                            println(e.message)
                            System.out.println(" uploadReport " + e.message)
                           ProgressLoading.dismiss()


                        }

                        override fun onComplete() {

                        }
                    })


        }.subscribeOn(Schedulers.io())
                .subscribe()


    }



}
