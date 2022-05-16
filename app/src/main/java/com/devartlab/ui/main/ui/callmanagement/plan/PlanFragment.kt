package com.devartlab.ui.main.ui.callmanagement.plan

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.databinding.FragmentPlanBinding
import com.devartlab.model.AdModel
import com.devartlab.model.Cycle
import com.devartlab.model.StartPoint
import com.devartlab.ui.main.ui.callmanagement.plan.activities.ActivitiesAdapter
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.doublee.DoubleActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.meeting.AddMeetingActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.office.AddOfficeActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.single.AddPlanSingleActivity
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.ChooseStartPoint
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.ChooseStartPointInterFace
import com.devartlab.ui.main.ui.callmanagement.plan.cycles.CyclesDialog
import com.devartlab.utils.*
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


private const val TAG = "PlanFragment"

class PlanFragment : BaseFragment<FragmentPlanBinding?>(), ActivitiesAdapter.ChooseActivity, DialogInterface.OnDismissListener,
    AdapterView.OnItemSelectedListener, ChooseStartPointInterFace {

    lateinit var binding: FragmentPlanBinding
    lateinit var viewModel: PlanViewModel
    private var adapter: PlansAdapter? = null
    private var activityTypeDialog: AlertDialog? = null
    var spinner: Spinner? = null
    var startPointModel: StartPoint? = null
    lateinit var dialog: ChooseStartPoint

    private var DATE: String? = null
    var fmt: SimpleDateFormat? = null

    private var Shift: String = "AM Shift"
    private var ShiftID: String = "0"
    var fullList: ArrayList<PlanEntity>? = null
    var fm: FragmentManager? = null
    var BUTTON_WIDTH: Int = 200 // delete button on items
    var positionToDelete: Int = 0 // delete button on items

    var horizontalCalendar: HorizontalCalendar? = null
    var startDate: Calendar? = null
    var endDate: Calendar? = null


    var adList = ArrayList<AdModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_plan
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlanViewModel::class.java)


        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);
        binding = viewDataBinding!!
        viewModel.getDayPlan(DATE!!, Shift)

        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.PLAN_RECYCLER) {
                adList?.add(m)
            }
        }
        Log.d(TAG, "onViewCreated: $adList")
        viewModel.dataManager.isNewCycle(viewModel.dataManager.newOldCycle.currentCyclePlanId == 0)

        viewModel.dataManager.saveCycle(Cycle(viewModel.dataManager.newOldCycle.currentCyclePlanId,
                                              viewModel.dataManager.newOldCycle.currentCycleId,
                                              "",
                                              "",
                                              0,
                                              "",
                                              true,
                                              0,
                                              0))


        setUpCalendar()
        setUpRecyclerView()
        setObservers()

        binding.addToPlan.setOnClickListener(View.OnClickListener {
            System.out.println(" DATE =  " + CommonUtilities.convertToMillis(DATE)!!.toString())

            if (CommonUtilities.isSameDay(
                    Date(CommonUtilities.currentToMillis),
                    Date(horizontalCalendar?.selectedDate?.timeInMillis!!)
                )
            ) {

                Toast.makeText(baseActivity, "you cant edit this day", Toast.LENGTH_SHORT).show()

            } else {

                if (Date(horizontalCalendar?.selectedDate?.timeInMillis!!).after(
                        Date(
                            CommonUtilities.currentToMillis
                        )
                    )
                ) {

                    if (Date(
                            CommonUtilities.convertToMillis(DATE)!!.toLong()
                        ).after(Date(viewModel.dataManager.newOldCycle.currentFromDateMs)) && Date(
                            CommonUtilities.convertToMillis(DATE)!!.toLong()
                        ).before(Date((viewModel.dataManager.newOldCycle.currentToDateMs + 14400000)))
                    ) {
                        if (viewModel.dataManager.updatePlan) {
                            chooseActivityType()
                        } else {

                            if (viewModel.dataManager.user.isAllowToUpdatePlan) {
                                chooseActivityType()
                            } else {


                                viewModel.getEditPermission(
                                    (CommonUtilities.convertToMillis(DATE)!!
                                        .toLong() + 7200000).toString(), 1
                                )
                            }
                        }
                    } else {
                        chooseActivityType()
                    }

                } else {
                    Toast.makeText(baseActivity, "you cant edit this day", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        binding.editStartPoint.setOnClickListener(View.OnClickListener {

            if (CommonUtilities.isSameDay(
                    Date(CommonUtilities.currentToMillis),
                    Date(horizontalCalendar?.selectedDate?.timeInMillis!!)
                )
            ) {
                Toast.makeText(baseActivity, "you cant edit this day", Toast.LENGTH_SHORT).show()

            } else {

                if (Date(horizontalCalendar?.selectedDate?.timeInMillis!!).after(
                        Date(
                            CommonUtilities.currentToMillis
                        )
                    )
                ) {


                    if (fullList.isNullOrEmpty()) {

                    } else {


                        if (Date(CommonUtilities.convertToMillis(DATE)!!.toLong()).after(
                                Date(
                                    viewModel.dataManager.newOldCycle.currentFromDateMs
                                )
                            ) && Date(CommonUtilities.convertToMillis(DATE)!!.toLong()).before(
                                Date(
                                    (viewModel.dataManager.newOldCycle.currentToDateMs + 14400000)
                                )
                            )
                        ) {
                            if (viewModel.dataManager.user.isAllowToUpdatePlan) {
                                dialog = ChooseStartPoint(
                                    baseActivity,
                                    this@PlanFragment,
                                    viewModel?.dataManager!!,
                                    0,
                                    ActivityEntity(),
                                    DATE,
                                    1
                                );
                                dialog.setCanceledOnTouchOutside(true);
                                val window = dialog.getWindow();
                                window?.setLayout(
                                    WindowManager.LayoutParams.MATCH_PARENT,
                                    WindowManager.LayoutParams.MATCH_PARENT
                                );
                                dialog.getWindow()
                                    ?.setBackgroundDrawableResource(android.R.color.transparent);
                                dialog.getWindow()
                                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                dialog.show();
                            } else {
                                // chooseActivityType()
                                viewModel.getEditPermission(
                                    (CommonUtilities.convertToMillis(DATE)!!
                                        .toLong() + 7200000).toString(), 3
                                )
                            }
                        } else {
                            dialog = ChooseStartPoint(
                                baseActivity,
                                this@PlanFragment,
                                viewModel?.dataManager!!,
                                0,
                                ActivityEntity(),
                                DATE,
                                1
                            );
                            dialog.setCanceledOnTouchOutside(true);
                            val window = dialog.getWindow();
                            window?.setLayout(
                                WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.MATCH_PARENT
                            );
                            dialog.getWindow()
                                ?.setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.getWindow()
                                ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            dialog.show();
                        }


                    }


                } else {
                    Toast.makeText(baseActivity, "you cant edit this day", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })


    }

    private fun setUpRecyclerView() {

        adapter = PlansAdapter(baseActivity, viewModel.dataManager)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseActivity)
        binding.recyclerView.adapter = adapter


        val touchListener = RecyclerTouchListener(baseActivity, binding.recyclerView)
        touchListener.setClickable(object : RecyclerTouchListener.OnRowClickListener {
            override fun onRowClicked(position: Int) {
            }

            override fun onIndependentViewClicked(independentViewID: Int, position: Int) {}
        })
            .setSwipeOptionViews(R.id.delete_task, R.id.edit_task)
            .setSwipeable(
                R.id.rowFG,
                R.id.rowBG,
                object : RecyclerTouchListener.OnSwipeOptionsClickListener {
                    override fun onSwipeOptionClicked(viewID: Int, position: Int) {
                        when (viewID) {
                            R.id.delete_task -> {



                                if (CommonUtilities.isSameDay(
                                        Date(CommonUtilities.currentToMillis),
                                        Date(horizontalCalendar?.selectedDate?.timeInMillis!!)
                                    )
                                ) {
                                    Toast.makeText(
                                        baseActivity,
                                        "you cant edit this day",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    if (Date(horizontalCalendar?.selectedDate?.timeInMillis!!).after(
                                            Date(CommonUtilities.currentToMillis)
                                        )
                                    ) {


                                        if (Date(
                                                CommonUtilities.convertToMillis(DATE)!!.toLong()
                                            ).after(Date(viewModel.dataManager.newOldCycle.currentFromDateMs)) && Date(
                                                CommonUtilities.convertToMillis(DATE)!!.toLong()
                                            ).before(Date((viewModel.dataManager.newOldCycle.currentToDateMs + 14400000)))
                                        ) {


                                            if (viewModel.dataManager.user.isAllowToUpdatePlan) {


                                                val builder =
                                                    androidx.appcompat.app.AlertDialog.Builder(
                                                        baseActivity
                                                    )
                                                builder.setTitle("Delete Visit")
                                                builder.setMessage("Are you sure?")
                                                builder.setPositiveButton("YES") { dialog, which ->

                                                    if (fullList?.get(position)!!.activityTypeID == 2) {
                                                        viewModel!!.deleteDouble(
                                                            fullList?.get(
                                                                position
                                                            )!!, DATE!!, Shift
                                                        )
                                                    } else {
                                                        viewModel!!.delete(
                                                            fullList?.get(position)!!,
                                                            DATE!!,
                                                            Shift
                                                        )
                                                    }



                                                    dialog.dismiss()
                                                }
                                                builder.setNegativeButton("NO") { dialog, which ->
                                                    dialog.dismiss()
                                                }
                                                val alert = builder.create()
                                                alert.show()


                                            } else {


                                                viewModel.getEditPermission(
                                                    (CommonUtilities.convertToMillis(
                                                        DATE
                                                    )!!.toLong() + 7200000).toString(), 2
                                                )
                                                positionToDelete = position
                                            }


                                        } else {

                                            val builder =
                                                androidx.appcompat.app.AlertDialog.Builder(
                                                    baseActivity
                                                )
                                            builder.setTitle("Delete Visit")
                                            builder.setMessage("Are you sure?")
                                            builder.setPositiveButton("YES") { dialog, which ->

                                                if (fullList?.get(position)!!.activityTypeID == 2) {
                                                    viewModel!!.deleteDouble(
                                                        fullList?.get(position)!!,
                                                        DATE!!,
                                                        Shift
                                                    )
                                                } else {
                                                    Log.d("PlanViewModel", "onSwipeOptionClicked: ")
                                                    viewModel!!.delete(
                                                        fullList?.get(position)!!,
                                                        DATE!!,
                                                        Shift
                                                    )
                                                }



                                                dialog.dismiss()
                                            }
                                            builder.setNegativeButton("NO") { dialog, which ->
                                                dialog.dismiss()
                                            }
                                            val alert = builder.create()
                                            alert.show()


                                        }


                                    } else {
                                        Toast.makeText(
                                            baseActivity,
                                            "you cant edit this day",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }

                                }
                            }
                        }
                    }
                })
        binding.recyclerView.addOnItemTouchListener(touchListener)

        // binding.recyclerView!!.isNestedScrollingEnabled = false

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding?.addToPlan?.hide();
                } else {
                    binding?.addToPlan?.show();
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })


    }

    private fun setUpCalendar() {

        startDate = Calendar.getInstance();
        startDate?.timeInMillis = viewModel?.dataManager?.newOldCycle?.currentFromDateMs!!


        endDate = Calendar.getInstance();
        endDate?.timeInMillis = viewModel?.dataManager?.newOldCycle?.currentToDateMs!!


        System.out.println("currentFromDateMs = " + viewModel?.dataManager?.newOldCycle?.currentFromDateMs!! + "currentToDateMs = " + viewModel?.dataManager?.newOldCycle?.currentToDateMs!!)

        horizontalCalendar = HorizontalCalendar.Builder(binding?.root, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(5)
            .build();

        horizontalCalendar?.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {


                if (spinner != null) {
                    spinner?.setSelection(0)
                    Shift = "AM Shift"
                    ShiftID = "8"

                }



                DATE = fmt?.format(date?.timeInMillis)
                viewModel.getDayPlan(fmt?.format(date?.timeInMillis)!!, Shift)
                System.out.println("timeInMillis : " + fmt?.format(date?.timeInMillis)!!)

            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.plan_menu, menu)
        val item = menu?.findItem(R.id.spinner)

        spinner = item?.actionView as Spinner
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.spinner_list_item_array,
            R.layout.spinner_item
        )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner?.adapter = adapter
        spinner?.onItemSelectedListener = this


        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.sync -> {

                if (viewModel.dataManager?.syncAble) {

                    val builder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
                    builder.setTitle("Sync Plan?")
                    builder.setMessage("make sure from updating plan first")
                    builder.setPositiveButton("YES") { dialog, which ->

                        if (LocationUtils.checkLocationPermission(baseActivity)) {

                            viewModel.syncPlan()

                        } else {
                            Toast.makeText(
                                baseActivity,
                                "Enable Permissions First!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        dialog.dismiss()
                    }
                    builder.setNegativeButton("NO") { dialog, which ->
                        // Do nothing
                        dialog.dismiss()
                    }
                    val alert = builder.create()
                    alert.show()
                } else {
                    Toast.makeText(
                        baseActivity,
                        "You have to update plan first",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }
            R.id.updatePlan -> {
                viewModel.updatePlan()
            }

            R.id.change_cycle -> {


                showCycles()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setObservers() {

        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer { it ->


            adapter!!.setMyData(ArrayList())

            fullList = it as ArrayList



            adapter!!.setMyData(fullList!!)


            try {
                System.out.println(it[0].toString())

            }
            catch (e: java.lang.Exception) {
            }

            /* save startPoint model in shard prefrance to use it when adding new plan
            if plan has model contain startPo
                  */
            binding.emptyList.visibility = View.GONE
            startPointModel = StartPoint(0, "", "")
            viewModel?.dataManager?.saveStartPoint(startPointModel)
            binding.startPoint.text = ""

            if (!it.isNullOrEmpty()) {

                for (model in it) {

                    System.out.println(" startPoint " + "\n" + model.startPoint + "\n" + model.startPointId)
                    if (!model.startPoint.isNullOrEmpty() && model.startPointId != 0) {
                        binding.startPoint.text = model.startPoint
                        startPointModel = StartPoint(model.startPointId!!, "", model.startPoint)
                        viewModel?.dataManager?.saveStartPoint(startPointModel)
                        break
                    }
                }


            } else {
                binding.emptyList.visibility = View.VISIBLE
                startPointModel == null
            }

        })


        viewModel.responsePermissionAdd.observe(viewLifecycleOwner, Observer { it ->


            if (it.isSuccesed) {

                chooseActivityType()
            } else {
                Toast.makeText(
                    baseActivity,
                    "you havent permission to edit this day",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })


        viewModel.responsePermissionEdit.observe(viewLifecycleOwner, Observer { it ->


            if (it.isSuccesed) {
                val builder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
                builder.setTitle("Delete Visit")
                builder.setMessage("Are you sure?")
                builder.setPositiveButton("YES") { dialog, which ->

                    if (fullList?.get(positionToDelete)!!.activityTypeID == 2) {
                        viewModel!!.deleteDouble(fullList?.get(positionToDelete)!!, DATE!!, Shift)
                    } else {
                        viewModel!!.delete(fullList?.get(positionToDelete)!!, DATE!!, Shift)
                    }



                    dialog.dismiss()
                }
                builder.setNegativeButton("NO") { dialog, which ->
                    // Do nothing
                    dialog.dismiss()
                }
                val alert = builder.create()
                alert.show()
            } else {
                Toast.makeText(
                    baseActivity,
                    "you havent permission to edit this day",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })


        viewModel.responsePermissionStartPoint.observe(viewLifecycleOwner, Observer { it ->


            if (it.isSuccesed) {

                dialog = ChooseStartPoint(
                    baseActivity,
                    this@PlanFragment,
                    viewModel?.dataManager!!,
                    0,
                    ActivityEntity(),
                    DATE,
                    1
                );
                dialog.setCanceledOnTouchOutside(true);
                val window = dialog.getWindow();
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                );
                dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow()
                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();


            } else {
                Toast.makeText(
                    baseActivity,
                    "you havent permission to edit this day",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

        viewModel.progress.observe(viewLifecycleOwner, Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                    Single.timer(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {
                                ProgressLoading.dismiss()
                                //    (DATE!!, Shift)
                                viewModel.getDayPlan(DATE!!, Shift)
                            }
                        })

                }
                2 -> {

                    ProgressLoading.dismiss()

                    AlertDialog.Builder(context)
                        .setTitle("خطأ")
                        .setMessage("يجب ادخال الزيارات الجديدة اولا") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(
                            android.R.string.yes
                            , null
                        )  // A null listener allows the button to dismiss the dialog and take no further action.
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()


                }
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
                10 -> {
                    ProgressLoading.dismiss()
                    baseActivity.onBackPressed()
                }
                1000 -> {

                    ProgressLoading.dismiss()
                    Single.timer(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {
                                ProgressLoading.dismiss()
                                if (viewModel.dataManager.newCycle) {
                                    baseActivity.finish()
                                } else {
                                    viewModel.getDayPlan(DATE!!, Shift)
                                }

                            }
                        })

                }
            }
        })

    }

    private fun chooseActivityType() {

        val factory = LayoutInflater.from(baseActivity)
        val choose_activity_type: View = factory.inflate(R.layout.choose_activity_type, null)

        if (activityTypeDialog != null && activityTypeDialog?.isShowing!!) {
            return//close chooseActivityType method
        }
        activityTypeDialog = AlertDialog.Builder(baseActivity).create()
        activityTypeDialog?.setCancelable(true)
        activityTypeDialog?.setView(choose_activity_type)
        activityTypeDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        activityTypeDialog?.show()

        var activitiesRecyclerView: RecyclerView =
            choose_activity_type.findViewById(R.id.activitiesRecyclerView)
        var progressBar: ProgressBar = choose_activity_type.findViewById(R.id.ProgressBar)
        var close: ImageView = choose_activity_type.findViewById(R.id.close)
        var activitiesAdapter: ActivitiesAdapter = ActivitiesAdapter(baseActivity, this)
        activitiesRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        activitiesRecyclerView.adapter = activitiesAdapter


        close.setOnClickListener(View.OnClickListener {
            activityTypeDialog?.dismiss()
        })

        viewModel.getActivities()
        viewModel.responseActivitiesLive.observe(viewLifecycleOwner, Observer {

            activitiesAdapter.setMyData(it)

        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {

            when (it) {
                4 -> {
                    progressBar.visibility = View.VISIBLE
                }
                5 -> {
                    progressBar.visibility = View.GONE
                }
            }

        })


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Plan"

        } catch (e: Exception) {
        }
        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }

    private fun showCycles() {
        fm = baseActivity.getSupportFragmentManager()
        val filterListFragment: CyclesDialog = CyclesDialog.newInstance("Some Title", "")
        filterListFragment.setTargetFragment(this, 0)
        filterListFragment.isCancelable = false
        filterListFragment.show(fm!!, "fragment_edit_name")


    }

    override fun onDismiss(p0: DialogInterface?) {
        updateCalendar()

    }

    fun updateCalendar() {

        val model = viewModel?.dataManager?.cycle

        if (model != null) {

            System.out.println(" TIME " + model?.fromDateMs!!.toString() + "    " + model?.toDateMs!!.toString())
            startDate?.timeInMillis = model?.fromDateMs!!
            endDate?.timeInMillis = model?.toDateMs!!

            horizontalCalendar?.setRange(startDate, endDate)
            horizontalCalendar?.refresh()
            horizontalCalendar?.centerCalendarToPosition(0)

        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var x = getResources().getStringArray(R.array.spinner_list_item_array)

        Shift = x[p2]
        // (DATE!!, Shift)
        viewModel.getDayPlan(DATE!!, Shift)

        if (x[p2].equals("AM Shift")) {
            ShiftID = "8"
        } else {
            ShiftID = "9"
        }

    }

    override fun chooseStartPoint(
        id: Int,
        date: String?,
        name: String?,
        type: Int,
        activities: ActivityEntity?
    ) {

        dialog.dismiss()
        val startPoint = StartPoint(id, date, name)
        viewModel?.dataManager?.saveStartPoint(startPoint)


        if (viewModel?.dataManager?.startPoint?.startPointId != null) {
            when (type) {
                1 -> {
                    openSingleActivity(activities, AddPlanSingleActivity::class.java)
                }
            }
        }

    }

    override fun editStartPoint(
        id: Int,
        date: String?,
        name: String?,
        type: Int,
        activities: ActivityEntity?
    ) {
        dialog.dismiss()


        if (!fullList.isNullOrEmpty()) {
            for (model in fullList!!) {

                val newModel = PlanEntity(

                    model?.planAccountId,
                    model?.planCycleId,
                    model?.cycleArName,
                    model?.fromDate,
                    model?.toDate,
                    model?.shift!!,
                    model?.day,
                    model?.date,
                    model?.activityEnName,
                    model?.doubleVisitEmpName,
                    name,
                    model?.brick,
                    model?.cusSerial,
                    model?.customerName,
                    model?.speciality,
                    model?._class,
                    model?.branchType,
                    model?.placeName,
                    model?.address,
                    model?.notes,
                    model?.eventsEnName,
                    model?.eventDescription,
                    model?.taskText,
                    model?.officeDescription,
                    model?.meetingMembers,
                    model?.customerid,
                    model?.customerBranchid,
                    model?.branchPlaceId,
                    model?.callObjectives,
                    model?.activityId,
                    model?.shiftId,
                    id,
                    model?.terriotryEmpId,
                    model?.eventsId,
                    model?.meetingMemberId,
                    model?.reported,
                    model?.terriotryAssigntId,
                    model?.terriotryAccountId,
                    model?.doubleVAccountId!!,
                    model?.doubleVAccountIdStr,
                    model?.brickId,
                    model?.territoryId,
                    model?.visit,
                    model?.extraVisit,
                    model?.cusLat,
                    model?.cusLang,
                    model?.call1,
                    model?.call2,
                    model?.call3,
                    model?.call4,
                    model?.isStarted,
                    date,
                    model?.planId,
                    0,
                    model?.planColor,
                    model?.activityTypeID,
                    model?.deleted,
                    false,
                    false,
                    ""


                )
                viewModel.insert(newModel)

                model?.deleted = true
                viewModel.update(model)

            }
        }

        ProgressLoading.show(baseActivity)
        Single.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(aLong: Long) {

                    ProgressLoading.dismiss()
                    viewModel?.getDayPlan(DATE!!, Shift);
                }

                override fun onError(e: Throwable) {}
            })


    }

    override fun saveActivityType(activities: ActivityEntity?) {


        if (activities?.typeId?.equals(1)!!) {

            if (fullList.isNullOrEmpty()) {
                dialog = ChooseStartPoint(
                    baseActivity,
                    this@PlanFragment,
                    viewModel?.dataManager!!,
                    activities?.typeId!!,
                    activities,
                    DATE,
                    0
                );
                dialog.setCanceledOnTouchOutside(true);
                val window = dialog.getWindow();
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                );
                dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow()
                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();
            } else {

                for (m in fullList!!) {

                    if (!m?.startPoint.isNullOrEmpty()) {
                        openSingleActivity(activities, AddPlanSingleActivity::class.java)
                        activityTypeDialog?.dismiss()
                        return
                    }
                }

                dialog = ChooseStartPoint(
                    baseActivity,
                    this@PlanFragment,
                    viewModel?.dataManager!!,
                    activities?.typeId!!,
                    activities,
                    DATE,
                    0
                );
                dialog.setCanceledOnTouchOutside(true);
                val window = dialog.getWindow();
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                );
                dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow()
                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();


            }

        } else if (activities?.typeId?.equals(2)!!) {
            openSingleActivity(activities, DoubleActivity::class.java)
        } else if (activities?.typeId?.equals(3)!!) {
        } else if (activities?.typeId?.equals(4)!!) {
            openSingleActivity(activities, AddOfficeActivity::class.java)
        } else if (activities?.typeId?.equals(5)!!) {
            openSingleActivity(activities, AddOfficeActivity::class.java)
        } else if (activities?.typeId?.equals(6)!!) {
            openSingleActivity(activities, AddMeetingActivity::class.java)
        } else {
            openSingleActivity(activities, AddPlanSingleActivity::class.java)
        }

        activityTypeDialog?.dismiss()

    }

    override fun onResume() {
        super.onResume()
        viewModel?.getDayPlan(DATE!!, Shift);

    }

    fun openSingleActivity(activityModel: ActivityEntity?, cls: Class<*>?) {

        System.out.println("Date = " + CommonUtilities.convertToMillis(DATE))
        val intent = Intent(baseActivity, cls)
        intent.putExtra("Shift", Shift)
        intent.putExtra("Activity", activityModel)
        intent.putExtra("ShiftId", ShiftID)
        intent.putExtra("Date", DATE)
        intent.putExtra("DATE", CommonUtilities.convertToMillis(DATE).toString())
        intent.putExtra(
            "Day",
            CommonUtilities.getDayName(horizontalCalendar?.selectedDate?.timeInMillis!!)
        )
        intent.putExtra("EXTRA", false)
        intent.putExtra("cusIdes", CommonUtilities.getCusIdes(fullList))
        intent.putExtra("cusBranchIds", CommonUtilities.getBranchIdes(fullList))
        startActivity(intent)
    }
}