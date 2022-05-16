package com.devartlab

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.databinding.ActivityUpdatePlanBinding
import com.devartlab.model.PlanJson
import com.devartlab.model.PlanModel
import com.devartlab.model.SyncPlanResponse
import com.devartlab.ui.main.ui.callmanagement.plan.PlanViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "UpdatePlan"
class UpdatePlan : BaseActivity<ActivityUpdatePlanBinding>() {
    lateinit var binding: ActivityUpdatePlanBinding
    lateinit var viewModel: PlanViewModel
    var list = ArrayList<PlanModel>()


    private var DATE: String? = null
    var fmt: SimpleDateFormat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(PlanViewModel::class.java)

        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        DATE = fmt?.format(CommonUtilities.currentToMillis)

        binding.getPlan.setOnClickListener {
            getPlanToUpdate(viewModel.myAPI!!, binding.empId.text.toString().toInt())
        }
        binding.update.setOnClickListener {
            updatePlan(viewModel.myAPI!!, binding.empId.text.toString().toInt(), list)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_update_plan
    }


    fun getPlanToUpdate(myAPI: ApiServices, id: Int) {


        ProgressLoading.show(this@UpdatePlan)
        myAPI.getPlan(id)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {

                    list.clear()

                    for (model in data.data.planData!!) {

                        if (fmt?.parse( model.date.take(10))?.after(fmt?.parse(DATE))!!) {
                            list.add(model)
                        }

                    }



                    binding.count.text = list.size.toString()

                    ProgressLoading.dismiss()
                }

                override fun onError(e: Throwable) {

                    Toast.makeText(this@UpdatePlan, e.message, Toast.LENGTH_LONG).show()
                    ProgressLoading.dismiss()
                }

                override fun onComplete() {}
            })


    }


    fun updatePlan(myAPI: ApiServices, id: Int, oldList: ArrayList<PlanModel>) {

        ProgressLoading.show(this@UpdatePlan)

        var list = ArrayList<PlanJson>();
        for (model in oldList) {

            if (model.terriotryAccountId.equals(0))
                model.terriotryAccountId = viewModel.dataManager?.user?.authorityId

            if (model.terriotryEmpId == null)
                model.terriotryEmpId = 0


            var  x = try {
                model.date.take(10) + "T" + CommonUtilities.getTextAfterSlash(model.startPoint!!) + ":00"
            } catch (e: java.lang.Exception) {
                ""
            }


          list.add(PlanJson(
                binding.newValue.text.toString().toInt(),
                model.date + "",
                model.customerid + 0,
                model.customerBranchid + 0,
                model.branchPlaceId + 0,
                model.shiftId + 0,
                model.activityId + 0,
                x,
                true,
                model.terriotryEmpId + 0,
                model.territoryId + 0,
                model.brickId + 0,
                model.callObjectives + "",
                model.eventsId + 0,
                model.eventDescription + "",
                0 + model.meetingMemberId,
                model.notes + "",
                model.startPointId + 0,
                false,
                model.terriotryAssigntId + 0,
                model.terriotryAccountId + 0,
                model.doubleVAccountId + 0,
                model.doubleVAccountIdStr + "",
                model.taskText + "",
                model.officeDescription + "",
                "",
                "",
                0,
                false,
                false,
                0))

            list.add(PlanJson(
                model.planId + 0,
                model.date + "",
                model.customerid + 0,
                model.customerBranchid + 0,
                model.branchPlaceId + 0,
                model.shiftId + 0,
                model.activityId + 0,
                x,
                true,
                model.terriotryEmpId + 0,
                model.territoryId + 0,
                model.brickId + 0,
                model.callObjectives + "",
                model.eventsId + 0,
                model.eventDescription + "",
                0 + model.meetingMemberId,
                model.notes + "",
                model.startPointId + 0,
                false,
                model.terriotryAssigntId + 0,
                model.terriotryAccountId + 0,
                model.doubleVAccountId + 0,
                model.doubleVAccountIdStr + "",
                model.taskText + "",
                model.officeDescription + "",
                "",
                "",
                0,
                false,
                true,
                model.planDetID));

        }
        val jsonArray1 = Gson().toJsonTree(list).asJsonArray

        Log.d(TAG, "updatePlan: $jsonArray1")

        myAPI.updatePlan(id, "Android", jsonArray1)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SyncPlanResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(body: SyncPlanResponse) {

                    ProgressLoading.dismiss()

                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@UpdatePlan, e.message, Toast.LENGTH_LONG).show()
                    ProgressLoading.dismiss()

                }

                override fun onComplete() {

                }
            })

    }

}