package com.devartlab.ui.main.ui.callmanagement.plan.addplan.doublee

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.databinding.ActivityDoubleBinding
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.model.DoubleVisitEmp
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.callmanagement.list.list.AddPlanViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class DoubleActivity : BaseActivity<ActivityDoubleBinding>(), ChooseEmployeeInterFace,
    DoubleSearchAdapter.EmpInterface, DoubleVisitAdapter.AddDoubleActivity {


    lateinit var binding: ActivityDoubleBinding
    lateinit var viewModel: AddPlanViewModel

    var doubleVisitAdapter: DoubleVisitAdapter? = null
    var planDao: PlanDao? = null
    var activity: ActivityEntity? = null
    var doubleVisitEmp: ArrayList<DoubleVisitEmp>? = null
    lateinit var chooseEmployee: ChooseEmployee
    var FilterData: FilterDataEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(AddPlanViewModel::class.java)

        doubleVisitAdapter = DoubleVisitAdapter(this, this, viewModel.dataManager)
        planDao = DatabaseClient.getInstance(getApplication())?.appDatabase?.planDao()
        activity = intent.getParcelableExtra("Activity")
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =
            "Add " + activity?.activityEnName + " (" + intent.getStringExtra("Date") + ")"




        binding.planList.layoutManager = LinearLayoutManager(this)
        binding.planList.adapter = doubleVisitAdapter

        binding.editTextSearch.setOnClickListener {
            getEmps()
        }

        binding.addToPlan.setOnClickListener(View.OnClickListener {

            if (doubleVisitEmp.isNullOrEmpty()) {

            } else {
                for (model in doubleVisitEmp!!) {

                    var planEntity = PlanEntity(

                        viewModel.dataManager.user?.accId!!
                        , viewModel.dataManager.cycle?.cycleId!!
                        , viewModel.dataManager.cycle?.cycleArName!!
                        , viewModel.dataManager.cycle?.fromDate!!
                        , viewModel.dataManager.cycle?.toDate!!
                        , intent.getStringExtra("Shift")!!
                        , intent.getStringExtra("Day")!!
                        , intent.getStringExtra("Date")!!
                        , activity?.activityEnName
                        , FilterData?.empName
                        , model.startPoint
                        , model.brickEnName
                        , model.cusSerial
                        , model.customerEnName
                        , " "
                        , model.cusClassEnName
                        , " "
                        , model.placeName
                        , model.address
                        , ""
                        , ""
                        , ""
                        , ""
                        , ""
                        , ""
                        , model.customerId
                        , model.customerBranchid
                        , model.branchPlaceId
                        , ""
                        , activity?.activityId
                        , intent.getStringExtra("ShiftId")?.toInt()
                        , model.startPointId
                        , model.terriotryEmpId
                        , 0
                        , 0
                        , false
                        , model.terriotryAssigntId
                        , model.terriotryAccountId
                        , this.FilterData?.empAccountId!! + 0
                        , model.doubleVAccountIdStr + "," + this.FilterData?.empAccountId.toString()
                        , model.brickId
                        , model.territoryId
                        , false
                        , intent.getBooleanExtra("EXTRA", false)
                        , ""
                        , ""
                        , 0
                        , 0
                        , 0
                        , 0
                        , false
                        , model.startTime
                        , viewModel.dataManager.cycle.planId!!
                        , 0
                        , CommonUtilities.randomColor
                        , activity?.typeId
                        , false
                        , false,
                        false,
                        ""
                    )

                    System.out.println(planEntity.toString())


                    Completable.fromAction {
                        planDao?.insert(planEntity)
                        viewModel?.dataManager?.saveSyncAble(false)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()

                }

                finish()
            }

        })
        setObservers()
        getEmps()
    }

    private fun setObservers() {

        viewModel.responseDoubleVisitEmpLive?.observe(this, Observer {
            doubleVisitAdapter?.setMyData(it)
            doubleVisitEmp = it

        })
        viewModel.progress?.observe(this, Observer {

            when (it) {
                0 -> {
                    ProgressLoading.dismiss()
                }

                1 -> {
                    ProgressLoading.show(this@DoubleActivity)
                }
            }

        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_double
    }

    override fun getEmpData(model: FilterDataEntity?) {
        System.out.println("selectedEmpAccountId ")

        viewModel.getDoubleListList(
            model?.empAccountId.toString(), intent.getStringExtra("DATE")!!,
            intent.getStringExtra("ShiftId")!!
        )
    }

    override fun addDoubleActivity(doubleVisitEmp: DoubleVisitEmp?) {

        /*   val builder = androidx.appcompat.app.AlertDialog.Builder(this)
           builder.setTitle("Add Meeting Activity")
           builder.setMessage("Are you sure?")
           builder.setPositiveButton("YES") { dialog, which ->


           }
           builder.setNegativeButton("NO") { dialog, which ->

               dialog.dismiss()
           }
           val alert = builder.create()
           alert.show()*/


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun getEmps() {

        chooseEmployee = ChooseEmployee(this, this@DoubleActivity, viewModel?.dataManager!!);
        chooseEmployee.setCanceledOnTouchOutside(true);
        val window = chooseEmployee.getWindow();
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        );
        chooseEmployee.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseEmployee.getWindow()
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseEmployee.show();
    }


    override fun chooseEmployee(model: FilterDataEntity?) {
        this.FilterData = model
        chooseEmployee.dismiss()
        viewModel.getDoubleListList(
            FilterData?.empAccountId.toString(),
            intent.getStringExtra("DATE")!!,
            intent.getStringExtra("ShiftId")!!
        )

    }

}
