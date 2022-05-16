package com.devartlab.ui.main.ui.callmanagement.plan.addplan.single

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.databinding.ActivityAddPlanSingleBinding
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.ui.main.ui.callmanagement.list.list.AddPlanViewModel
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.FilterInterface
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.doubleextra.FilterListExtraFragment
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class AddPlanDoubleExtraActivity : BaseActivity<ActivityAddPlanSingleBinding>(), AddPlanListAdapter.AddToList, FilterInterface {

    var binding: ActivityAddPlanSingleBinding? = null
    var viewModel: AddPlanViewModel? = null
    var selectedPlanAdapter: SelectedPlanAdapter? = null
    var addPlanListAdapter: AddPlanListAdapter? = null
    var planDao: PlanDao? = null
    var activity: ActivityEntity? = null
    var cusIdes: String =""
    var cusBranchIds: String = ""
    var empModel: FilterDataEntity?= null

    override fun getLayoutId(): Int {
        return R.layout.activity_add_plan_single
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(AddPlanViewModel::class.java)
        planDao = DatabaseClient.getInstance(getApplication())?.appDatabase?.planDao()
        activity = intent.getParcelableExtra("Activity")
        cusIdes = intent.getStringExtra("cusIdes")!!
        cusBranchIds = intent.getStringExtra("cusBranchIds")!!
        empModel = intent.getParcelableExtra<FilterDataEntity>("EMPLOYEE_ID") !!


        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add " + activity?.activityEnName + " (" + intent.getStringExtra("Date") + ")"


        viewModel?.getCustomerList(empModel?.empAccountId!!,"0", "0", "0", "0", "0", cusIdes , cusBranchIds)

        addPlanListAdapter = AddPlanListAdapter(this, this, viewModel?.dataManager)
        binding?.planList?.layoutManager = LinearLayoutManager(this)
        binding?.planList?.adapter = addPlanListAdapter


        selectedPlanAdapter = SelectedPlanAdapter(this)
        binding?.selectedPlan?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding?.selectedPlan?.adapter = selectedPlanAdapter
        binding?.addPlan?.setOnClickListener(View.OnClickListener {

            for (model in selectedPlanAdapter?.myData!!) {

                Completable.fromAction {

                    planDao?.insert(model)

                }.subscribeOn(Schedulers.io())
                        .subscribe()

                finish()

            }
            viewModel?.dataManager?.saveSyncAble(false)
        })


        binding?.editTextSearch?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (TextUtils.isEmpty(s)) {
                    viewModel?.getCustomerListWithoutLoading(empModel?.empAccountId!!,"0", "0", "0", "0", "0", cusIdes , cusBranchIds)

                } else {
                    viewModel?.getCustomerListWithoutLoading(empModel?.empAccountId!!,"0", "0", "0", "0", s.toString() , cusIdes , cusBranchIds)

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // TODO Auto-generated method stub
            }
        })


        setObservers()
    }

    private fun setObservers() {

        viewModel?.responseLive?.observe(this, Observer {
            addPlanListAdapter?.setMyData(it)
        })

        viewModel?.progress?.observe(this, Observer {

            when (it) {
                1 -> {

                    ProgressLoading.show(this@AddPlanDoubleExtraActivity)
                }
                0 -> {

                    try {
                        ProgressLoading.dismiss()
                    } catch (e: java.lang.Exception) {

                    }

                }
            }

        })


    }

    override fun addToList(model: ListEntity?) {
        System.out.println(viewModel?.dataManager?.cycle?.fromDate!!)
        var planEntity = PlanEntity(
                viewModel?.dataManager?.user?.accId!!
                , viewModel?.dataManager?.cycle?.cycleId!!
                , viewModel?.dataManager?.cycle?.cycleArName!!
                , viewModel?.dataManager?.cycle?.fromDate!!
                , viewModel?.dataManager?.cycle?.toDate!!
                , intent.getStringExtra("Shift")!!
                , intent.getStringExtra("Day")!!
                , intent.getStringExtra("Date")!!
                , activity?.activityEnName
                , empModel?.empName
                , viewModel?.dataManager?.startPoint?.startPointName+""
                , model?.brickEnName + ""
                , model?.cusSerial!!
                , model?.customerEnName + ""
                , model?.oldSpeciality + ""
                , model?.oldClass + ""
                , model?.branchType + ""
                , model?.placeName + ""
                , model?.address + ""
                , model?.notes + ""
                , ""
                , ""
                , ""
                , ""
                , ""
                , model?.customerId
                , model?.branchId
                , model?.branchPlaceId
                , ""
                , activity?.activityId
                , intent.getStringExtra("ShiftId")?.toInt()
                , viewModel?.dataManager?.startPoint?.startPointId!! + 0
                , model?.empId
                , 0
                , 0
                , false
                , model?.assigntId
                , model?.accountId
                , empModel?.empAccountId!!
                , 0.toString()
                , model?.bricId
                , model?.terriotryId
                , false
                , intent.getBooleanExtra("EXTRA", false)
                , 0.toString()
                , 0.toString()
                , 0
                , 0
                , 0
                , 0
                , false
                , viewModel?.dataManager?.startPoint?.startPointTime
                , viewModel?.dataManager?.cycle?.planId!!
                , 0
                , CommonUtilities.randomColor
                , activity?.typeId,
                false ,false,
                false,
                ""
        )


        selectedPlanAdapter?.addItem(planEntity)

        try {
            binding?.selectedPlan?.scrollToPosition(selectedPlanAdapter?.myData?.size!! - 1);

        } catch (e: Exception) {
        }


        val jsonArray1 = Gson().toJsonTree(selectedPlanAdapter?.myData).asJsonArray
        print(jsonArray1.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_new, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {

                val fm: FragmentManager = getSupportFragmentManager()
                val filterListFragment: FilterListExtraFragment = FilterListExtraFragment(this , empModel?.empAccountId.toString())
                filterListFragment.show(fm, "fragment_edit_name")
                true

            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun applyFilter(brik: String, line: String, territory: String, speciality: String, classs: String) {


        viewModel?.getCustomerList(empModel?.empAccountId!!,territory, brik, speciality, classs, "0", cusIdes , cusBranchIds)

    }

}
