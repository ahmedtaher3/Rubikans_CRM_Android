package com.devartlab.ui.main.ui.callmanagement.plan.addplan.office

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.databinding.ActivityAddExtraBinding
import com.devartlab.ui.main.ui.callmanagement.list.list.AddPlanViewModel
import com.devartlab.utils.CommonUtilities
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class AddOfficeActivity : BaseActivity<ActivityAddExtraBinding>() {

    var addPlanViewModel: AddPlanViewModel? = null
    var planDao: PlanDao? = null
    lateinit var activityAddExtraBinding: ActivityAddExtraBinding
    lateinit var extraPlanAdapter: ExtraPlanAdapter
    lateinit var activity: ActivityEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddExtraBinding = viewDataBinding!!
        addPlanViewModel = ViewModelProviders.of(this).get(AddPlanViewModel::class.java)
        planDao = DatabaseClient.getInstance(getApplication())?.appDatabase?.planDao()


        activity = intent.getParcelableExtra("Activity")!!
        setSupportActionBar(activityAddExtraBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add " + activity.activityEnName + " (" + intent.getStringExtra("Date") + ")"



        extraPlanAdapter = ExtraPlanAdapter(this)
        activityAddExtraBinding.tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        activityAddExtraBinding.tasksRecyclerView.adapter = extraPlanAdapter
        activityAddExtraBinding.addNewTask.setOnClickListener(View.OnClickListener {


            val dialogBuilder = AlertDialog.Builder(this@AddOfficeActivity)
            // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.add_new_task, null)
            dialogBuilder.setView(dialogView)
            val newTaskEditText = dialogView.findViewById<View>(R.id.newTaskEditText) as EditText
            val newTaskAddButton = dialogView.findViewById<View>(R.id.newTaskAddButton) as Button


            val alertDialog = dialogBuilder.create()
            newTaskAddButton.setOnClickListener {
                alertDialog.dismiss()



                when (activity?.typeId) {

                    4 ->{ //


                        var planEntity = PlanEntity(
                                addPlanViewModel?.dataManager?.user?.accId!!
                                , addPlanViewModel?.dataManager?.cycle?.cycleId!!
                                , addPlanViewModel?.dataManager?.cycle?.cycleArName!!
                                , addPlanViewModel?.dataManager?.cycle?.fromDate!!
                                , addPlanViewModel?.dataManager?.cycle?.toDate!!
                                , intent.getStringExtra("Shift")!!
                                , intent.getStringExtra("Day")!!
                                , intent.getStringExtra("Date")!!
                                , activity?.activityEnName
                                , " "
                                , addPlanViewModel?.dataManager?.startPoint?.startPointName
                                , " "
                                , 0
                                , newTaskEditText.text.toString()
                                , " "
                                , " "
                                , " "
                                , " "
                                , " "
                                , " "
                                , ""
                                , ""
                                , "" +newTaskEditText.text.toString()
                                , ""
                                , ""
                                , 0
                                , 0
                                , 0
                                , ""
                                , activity?.activityId
                                , intent.getStringExtra("ShiftId")?.toInt()
                                , addPlanViewModel?.dataManager?.startPoint?.startPointId!! + 0
                                , 0
                                , 0
                                , 0
                                , false
                                , 0
                                , 0
                                , 0
                                , 0.toString()
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
                                , addPlanViewModel?.dataManager?.startPoint?.startPointTime
                                , addPlanViewModel?.dataManager?.cycle?.planId!!
                                , 0
                                , CommonUtilities.randomColor
                                , activity?.typeId,
                                false ,false,
                                false,
                                ""
                        )

                        System.out.println(planEntity.toString())
                        extraPlanAdapter.addItem(planEntity)



                    }

                    5 ->{

                        var planEntity = PlanEntity(
                                addPlanViewModel?.dataManager?.user?.accId!!
                                , addPlanViewModel?.dataManager?.cycle?.cycleId!!
                                , addPlanViewModel?.dataManager?.cycle?.cycleArName!!
                                , addPlanViewModel?.dataManager?.cycle?.fromDate!!
                                , addPlanViewModel?.dataManager?.cycle?.toDate!!
                                , intent.getStringExtra("Shift")!!
                                , intent.getStringExtra("Day")!!
                                , intent.getStringExtra("Date")!!
                                , activity?.activityEnName
                                , " "
                                , addPlanViewModel?.dataManager?.startPoint?.startPointName
                                , " "
                                , 0
                                , newTaskEditText.text.toString()
                                , " "
                                , " "
                                , " "
                                , " "
                                , " "
                                , " "
                                , ""
                                , ""
                                , ""
                                , ""+newTaskEditText.text.toString()
                                , ""
                                , 0
                                , 0
                                , 0
                                , ""
                                , activity?.activityId
                                , intent.getStringExtra("ShiftId")?.toInt()
                                , addPlanViewModel?.dataManager?.startPoint?.startPointId!! + 0
                                , 0
                                , 0
                                , 0
                                , false
                                , 0
                                , 0
                                , 0
                                , 0.toString()
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
                                , addPlanViewModel?.dataManager?.startPoint?.startPointTime
                                , addPlanViewModel?.dataManager?.cycle?.planId!!
                                , 0
                                , CommonUtilities.randomColor
                                , activity?.typeId,
                                false ,false,
                                false,
                                ""
                        )

                        System.out.println(planEntity.toString())
                        extraPlanAdapter.addItem(planEntity)



                    }

                }

            }


            alertDialog.show()
        })

        activityAddExtraBinding.addExtras.setOnClickListener(View.OnClickListener {


            Completable.fromAction {

                for (model in extraPlanAdapter.myData) {
                    planDao?.insert(model)
                }
                addPlanViewModel?.dataManager?.saveSyncAble(false)
            }
                    .subscribeOn(Schedulers.io())
                    .subscribe({ finish() }, {})

        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_extra
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
}
