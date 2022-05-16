package com.devartlab.ui.main.ui.callmanagement.plan.addplan.meeting


import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.databinding.ActivityAddMeetingBinding
import com.devartlab.model.CustomerList
import com.devartlab.ui.main.ui.callmanagement.list.list.AddPlanViewModel
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.doublee.DoubleSearchAdapter
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.doublee.SelectedPlanAdapterMeeting
import com.devartlab.utils.CommonUtilities
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers


class AddMeetingActivity : BaseActivity<ActivityAddMeetingBinding>(),   MeetingAdapter.AddMeetingActivityInterface, DoubleSearchAdapter.EmpInterface {

    lateinit var activityAddMeetingBinding: ActivityAddMeetingBinding
    lateinit var viewModel: AddPlanViewModel
    var selectedPlanAdapter: SelectedPlanAdapterMeeting? = null
    lateinit var meetingAdapter: DoubleSearchAdapter
    var planDao: PlanDao? = null
    var activity: ActivityEntity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddMeetingBinding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(AddPlanViewModel::class.java)
        activity = intent.getParcelableExtra("Activity")
        setSupportActionBar(activityAddMeetingBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add " +activity?.activityEnName + " (" + intent.getStringExtra("Date") + ")"



        meetingAdapter = DoubleSearchAdapter(this, this)
        selectedPlanAdapter = SelectedPlanAdapterMeeting(this)
        activityAddMeetingBinding.planList.layoutManager = LinearLayoutManager(this)
        activityAddMeetingBinding.planList.adapter = meetingAdapter
        planDao = DatabaseClient.getInstance(getApplication())?.appDatabase?.planDao()

        viewModel?.getMeetingMembers()

        activityAddMeetingBinding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (TextUtils.isEmpty(s)) {
                    viewModel?.getMeetingMembers()

                } else {
                    viewModel?.getFilterEmpl(viewModel?.dataManager?.user?.accId.toString(),"TblDefEmployee", "and 1=1", s.toString())

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // TODO Auto-generated method stub
            }
        })

        activityAddMeetingBinding?.selectedPlan?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activityAddMeetingBinding?.selectedPlan?.adapter = selectedPlanAdapter


        activityAddMeetingBinding?.addPlan?.setOnClickListener(View.OnClickListener {

            for (model in selectedPlanAdapter?.myData!!) {

                Completable.fromAction {

                    planDao?.insert(model)
                    viewModel?.dataManager?.saveSyncAble(false)

                }.subscribeOn(Schedulers.io())
                        .subscribe()

                finish()

            }

        })

        setObservers()

    }

    private fun setObservers() {

        viewModel?.filterEmployeeResponseLive?.observe(this, Observer {
            meetingAdapter.setMyData(it)
        })

    }

    override fun addActivity(customerList: CustomerList?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_meeting
    }

    override fun getEmpData(model: FilterDataEntity?) {

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
                , model?.empName
                , viewModel?.dataManager?.startPoint?.startPointName
                , " "
                , 0
                , System.currentTimeMillis().toString()
                , " "
                , " "
                , " "
                , " "
                , " "
                , " "
                , ""
                , ""
                , ""
                , ""
                , model?.empName+""
                , 0
                , 0
                , 0
                , ""
                , activity?.activityId!!+0
                , intent.getStringExtra("ShiftId")?.toInt()
                , viewModel?.dataManager?.startPoint?.startPointId!!+0
                , 0
                , 0
                , model?.empId!!+0
                , false
                , 0
                , 0
                , 0
                , 0.toString()!!+0
                , 0
                , 0
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
                , activity?.typeId!!+0,
                false ,false,
                false,
                ""
        )
        selectedPlanAdapter?.addItem(planEntity)

        try {
            activityAddMeetingBinding?.selectedPlan?.scrollToPosition(selectedPlanAdapter?.myData?.size!! - 1);

        } catch (e: Exception) {
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
