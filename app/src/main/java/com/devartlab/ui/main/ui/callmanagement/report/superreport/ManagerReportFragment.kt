package com.devartlab.ui.main.ui.callmanagement.report.superreport

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.startPoint.StartPointEntity
import com.devartlab.data.room.values.ValuesEntity
import com.devartlab.databinding.FragmentSuperReportBinding
import com.devartlab.model.AdModel
import com.devartlab.model.Cycle
import com.devartlab.model.Shift
import com.devartlab.model.StartPoint
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.CallsActivity
import com.devartlab.ui.main.ui.callmanagement.plan.activities.ActivitiesAdapter
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.meeting.AddMeetingActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.office.AddOfficeActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.single.AddPlanDoubleExtraActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.single.AddPlanSingleActivity
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.ChooseStartPoint
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.ChooseStartPointInterFace
import com.devartlab.ui.main.ui.callmanagement.report.ReportInterface
import com.devartlab.ui.main.ui.callmanagement.report.ReportViewModel
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoView
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import ss.com.bannerslider.Slider
import java.text.SimpleDateFormat
import java.util.*


class ManagerReportFragment : BaseFragment<FragmentSuperReportBinding>()
    , ReportInterface, ChooseEmployeeInterFace
    , ChooseStartPointInterFace, ActivitiesAdapter.ChooseActivity, ManagerReportListener
    , SuperReportAdapter.UpdatePlan, AdapterView.OnItemSelectedListener, View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewModel: ReportViewModel
    lateinit var binding: FragmentSuperReportBinding
    lateinit var dialog: ConfirmDialog
    lateinit var mediaSource: SimpleMediaSource

    private var adapter: SuperReportAdapter? = null
    private var DATE: String? = null
    private var shift: String = "AM Shift"
    private var shiftID: String = "0"
    private var DATE_IN_MILLIS: String = "0"
    lateinit var planEntity: PlanEntity
    private var fullList: List<PlanEntity?>? = null
    lateinit var chooseStartPointDialog: ChooseStartPoint
    private var model: PlanEntity? = null
    private var modelWithStartPoint: PlanEntity? = null
    private var activityTypeDialog: android.app.AlertDialog? = null
    var spinner: Spinner? = null
    var horizontalCalendar: HorizontalCalendar? = null
    var startDate: Calendar? = null
    var endDate: Calendar? = null
    var fmt: SimpleDateFormat? = null
    var activitiesModel: ActivityEntity? = null
    lateinit var chooseEmployee: ChooseEmployee

    lateinit var locationManager: LocationManager
    var locationFlag: Int = 0

    var startTime = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_super_report
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = baseActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        viewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)
        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)
        DATE_IN_MILLIS = CommonUtilities.convertDateToMillis(DATE).toString()
        startDate = Calendar.getInstance()
        endDate = Calendar.getInstance()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding
        setHasOptionsMenu(true);

        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.MANAGER_REPORT) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
        ) {
            binding.constrAds.setVisibility(View.GONE)
        } else if (model.resourceLink.equals(null)) {
            binding.imageView.visibility = View.VISIBLE
            Glide.with(this).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
        }
        if (!model.webPageLink.equals("")) {
            openWebPage(model.webPageLink)
            binding.cardviewAds.setOnClickListener {
            }
        }
        when (model.type) {
            "Video" -> {
                binding.videoView.visibility = View.VISIBLE
                mediaSource = SimpleMediaSource(model.resourceLink)
                binding.videoView.play(mediaSource);
            }
            "Image" -> {

                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
            }
            "GIF" -> {
                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).asGif().load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView);
            }
            "Paragraph" -> {
                binding.textView.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.textView.setText(Html.fromHtml(model.resourceLink, Html.FROM_HTML_MODE_LEGACY));
                } else
                    binding.textView.setText(Html.fromHtml(model.resourceLink))
            }
            "Slider" -> {
                binding.bannerSlider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(context))
                binding.bannerSlider?.setInterval(5000)

                val list = ArrayList<String>()
                for (i in model.slideImages!!) {
                    list.add(i?.link!!)
                }
                binding.bannerSlider?.setAdapter(MainSliderAdapter(list))
            }
        }
        if (model.show_ad == true) {
            binding.btnHideShowAds.setVisibility(View.VISIBLE)
            binding.btnHideShowAds.setOnClickListener {
                if (binding.constrAds.visibility == View.VISIBLE) {
                    binding.constrAds.setVisibility(View.GONE)
                    binding.btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
                } else {
                    binding.constrAds.setVisibility(View.VISIBLE)
                    binding.btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
                }
            }
        }
        if (model.show_more == true) {
            binding.tvMoreThanAds.setVisibility(View.VISIBLE)
            binding.tvMoreThanAds.setOnClickListener {
                val  intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }


        if (viewModel.dataManager.user.isOpenReportLimit) {

            startDate?.timeInMillis = viewModel.dataManager.user?.minDate?.toLong()!!
            endDate?.timeInMillis = viewModel.dataManager.user?.maxDate?.toLong()!!

        } else {
            startDate?.timeInMillis = viewModel.dataManager.cycle?.fromDateMs?.toLong()!!
            endDate?.timeInMillis = viewModel.dataManager.user?.maxDate?.toLong()!!

        }

        horizontalCalendar = HorizontalCalendar.Builder(binding.root, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(5)
            .build();
        horizontalCalendar?.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                DATE = fmt?.format(date?.timeInMillis)
                DATE_IN_MILLIS = date?.timeInMillis.toString()
                viewModel.getAllByDateAndShift(DATE, shift, "");
            }
        }



        setListeners()
        setObservers()
        setUpRecycler()
    }

    private fun setObservers() {


        viewModel.allPlansByDate.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<List<PlanEntity?>?> {

                fullList = it
                if (!it.isNullOrEmpty()) {

                    binding.emptyList.visibility = View.GONE
                    model = it[0]
                    for (model in it) {

                        if (!model?.startPoint.isNullOrEmpty() && model?.startPointId != 0) {
                            this.modelWithStartPoint = model
                            break
                        }
                    }

                    if (modelWithStartPoint != null) {
                        binding.startPoint.text = modelWithStartPoint?.startPoint
                        startTime =
                            CommonUtilities.getTextAfterSlash(modelWithStartPoint?.startPoint!!)

                    }

                    if (model!!.reported!!) {
                        setButtonReported()
                        adapter?.setCheckEnable(false)
                    } else {
                        viewModel.isStarted(DATE, shift, 239)
                    }
                } else {
                    binding.emptyList.visibility = View.VISIBLE
                    viewModel.isStarted(DATE, shift, 244)
                }

                adapter?.updateData(ArrayList<PlanEntity>(it!!))

            })

        viewModel.responseLiveConfiermShift.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                if (it.isSuccesed) {


                    val componentName = ComponentName(baseActivity, ExampleJobService::class.java)
                    val info: JobInfo = JobInfo.Builder(123, componentName)
                        .setPersisted(true)
                        .setPeriodic(30 * 60 * 1000)
                        .build()
                    val scheduler: JobScheduler =
                        baseActivity.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                    val resultCode: Int = scheduler.schedule(info)
                    if (resultCode == JobScheduler.RESULT_SUCCESS) {

                    } else {
                    }


                    viewModel.dataManager.saveStartShift(true, 7)
                    viewModel.dataManager.saveShift(
                        Shift(
                            shiftID.toInt(),
                            shift,
                            DATE_IN_MILLIS?.toLong().toString(),
                            DATE,
                            false
                        )
                    )

                    val valuesEntity = ValuesEntity(
                        1,
                        true,
                        false,
                        "",
                        DATE_IN_MILLIS?.toLong(),
                        shift,
                        shiftID.toInt(),
                        DATE
                    )
                    viewModel.valuesRepository?.insert(valuesEntity)

                    viewModel.saveStartShift(DATE, shift, false, false)

                    viewModel.getAllByDateAndShift(DATE, shift, "1");

                } else {
                    Toast.makeText(baseActivity, it.rerurnMessage, Toast.LENGTH_SHORT).show()


                    if (it.data.startPointData != null) {
                        if (it.data.startPointData.size == 2) {


                            var shiftName = ""
                            when (it.data.startPointData[0].shiftId) {
                                8 -> {
                                    shiftName = "AM Shift"
                                }
                                9 -> {
                                    shiftName = "PM Shift"
                                }
                            }

                            DATE_IN_MILLIS = CommonUtilities.convertDateToMillis(
                                it.data.startPointData[0].salesRptDate.take(10)
                            ).toString()
                            DATE = it.data.startPointData[0].salesRptDate.take(10)
                            shift = shiftName


                            viewModel.dataManager.saveStartShift(false, 8)

                            viewModel.saveStartShift(DATE, shift, true, false)

                            viewModel.getAllByDateAndShift(DATE, shift, "1");


                        } else if (it.data.startPointData.size == 1) {

                            var shiftName = ""
                            when (it.data.startPointData[0].shiftId) {
                                8 -> {
                                    shiftName = "AM Shift"
                                }
                                9 -> {
                                    shiftName = "PM Shift"
                                }
                            }

                            DATE_IN_MILLIS = CommonUtilities.convertDateToMillis(
                                it.data.startPointData[0].salesRptDate.take(10)
                            ).toString()
                            DATE = it.data.startPointData[0].salesRptDate.take(10)
                            shift = shiftName


                            viewModel.dataManager.saveStartShift(true, 9)
                            viewModel.dataManager.saveShift(
                                Shift(
                                    it.data.startPointData[0].shiftId,
                                    shiftName,
                                    CommonUtilities.convertDateToMillis(
                                        it.data.startPointData[0].salesRptDate.take(10)
                                    ).toString(),
                                    it.data.startPointData[0].salesRptDate.take(10),
                                    false
                                )
                            )

                            val valuesEntity = ValuesEntity(
                                1,
                                true,
                                false,
                                "",
                                CommonUtilities.convertDateToMillis(
                                    it.data.startPointData[0].salesRptDate.take(10)
                                ),
                                shiftName,
                                it.data.startPointData[0].shiftId,
                                it.data.startPointData[0].salesRptDate.take(10)
                            )
                            viewModel.valuesRepository?.insert(valuesEntity)

                            viewModel.saveStartShift(DATE, shift, false, false)


                            viewModel.getAllByDateAndShift(DATE, shift, "1");


                        }
                    }
                }

            })

        viewModel.responseLiveEndShift.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it.isSuccesed) {


                val scheduler =
                    baseActivity.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                scheduler.cancel(123)



                viewModel.dataManager.saveStartShift(false, 10)

                val valuesEntity = ValuesEntity(
                    1,
                    false,
                    true,
                    "",
                    DATE_IN_MILLIS?.toLong(),
                    shift,
                    shiftID.toInt(),
                    DATE
                )
                viewModel.valuesRepository?.insert(valuesEntity)

                viewModel.updateIsStarted(DATE, shift, true, false)

                viewModel.getAllByDateAndShift(DATE, shift, "1");

            } else {
                Toast.makeText(baseActivity, it.rerurnMessage, Toast.LENGTH_SHORT).show()

                if (it.data.startPointData != null) {
                    if (it.data.startPointData.size == 2) {


                        var shiftName = ""
                        when (it.data.startPointData[0].shiftId) {
                            8 -> {
                                shiftName = "AM Shift"
                            }
                            9 -> {
                                shiftName = "PM Shift"
                            }
                        }

                        DATE_IN_MILLIS = CommonUtilities.convertDateToMillis(
                            it.data.startPointData[0].salesRptDate.take(10)
                        ).toString()
                        DATE = it.data.startPointData[0].salesRptDate.take(10)
                        shift = shiftName


                        viewModel.dataManager.saveStartShift(false, 8)

                        viewModel.saveStartShift(DATE, shift, true, false)

                        viewModel.getAllByDateAndShift(DATE, shift, "1");


                    } else if (it.data.startPointData.size == 1) {

                        var shiftName = ""
                        when (it.data.startPointData[0].shiftId) {
                            8 -> {
                                shiftName = "AM Shift"
                            }
                            9 -> {
                                shiftName = "PM Shift"
                            }
                        }

                        DATE_IN_MILLIS = CommonUtilities.convertDateToMillis(
                            it.data.startPointData[0].salesRptDate.take(10)
                        ).toString()
                        DATE = it.data.startPointData[0].salesRptDate.take(10)
                        shift = shiftName


                        viewModel.dataManager.saveStartShift(true, 9)
                        viewModel.dataManager.saveShift(
                            Shift(
                                it.data.startPointData[0].shiftId,
                                shiftName,
                                CommonUtilities.convertDateToMillis(
                                    it.data.startPointData[0].salesRptDate.take(10)
                                ).toString(),
                                it.data.startPointData[0].salesRptDate.take(10),
                                false
                            )
                        )

                        val valuesEntity = ValuesEntity(
                            1,
                            true,
                            false,
                            "",
                            CommonUtilities.convertDateToMillis(
                                it.data.startPointData[0].salesRptDate.take(10)
                            ),
                            shiftName,
                            it.data.startPointData[0].shiftId,
                            it.data.startPointData[0].salesRptDate.take(10)
                        )
                        viewModel.valuesRepository?.insert(valuesEntity)

                        viewModel.saveStartShift(DATE, shift, false, false)


                        viewModel.getAllByDateAndShift(DATE, shift, "1");


                    }
                }

            }
            viewModel.isStarted(DATE, shift, 364)
        })

        viewModel.responseLiveIsStarted.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<StartPointEntity?> {


                if (it != null) {


                    if (it.isUploaded) {
                        // shift uploaded
                        setButtonReported()
                        adapter?.setCheckEnable(false)
                    } else {
                        if (it.isEnded) {
                            // shift  started and ended

                            setButtonEnded()
                            adapter?.setCheckEnable(true)
                        } else {
                            // shift  started and not ended yet
                            setButtonStarted()
                            adapter?.setCheckEnable(true)
                            shiftID = viewModel.dataManager.shift.id.toString()
                            shift = viewModel.dataManager.shift.name
                            DATE = fmt?.format(viewModel.dataManager.shift.startedAt.toLong())
                            DATE_IN_MILLIS = viewModel.dataManager.shift.startedAt

                            if (spinner != null) {

                                if (viewModel.dataManager.shift.id == 8)
                                    spinner?.setSelection(0)
                                else
                                    spinner?.setSelection(1)

                                spinner?.isEnabled = false

                            }

                            startDate?.timeInMillis = DATE_IN_MILLIS!!.toLong()
                            endDate?.timeInMillis = DATE_IN_MILLIS!!.toLong()
                            horizontalCalendar?.setRange(startDate, endDate)
                            horizontalCalendar?.refresh()


                        }

                    }


                } else {
                    // shift not started yet
                    setButtonReady()
                    adapter?.setCheckEnable(false)
                }


            })

        viewModel.progress?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            when (it) {
                0 -> {
                    ProgressLoading.dismiss()
                }

                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }

        })

        viewModel.showText.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            try {
                Toast.makeText(baseActivity, it, Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
            }


        })


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.submitReport -> {

                submitDialog()
            }

            R.id.startShift -> {


                val builder = AlertDialog.Builder(baseActivity)
                builder.setTitle(getString(R.string.start_shift))
                builder.setMessage(getString(R.string.are_u_sure))
                builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                    dialog.dismiss()


                    if (fullList.isNullOrEmpty()) {

                        baseActivity.runOnUiThread {
                            Toast.makeText(
                                baseActivity,
                                getString(R.string.cant_start_empty_shift),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {
                        if (LocationUtils.checkPermission(baseActivity)) {
                           ProgressLoading.showWithText(baseActivity, getString(R.string.fetching_location))
                            val myLocation = MyLocation(baseActivity, true)
                            myLocation.getLocation(
                                baseActivity,
                                object : MyLocation.LocationResult() {
                                    override fun gotLocation(
                                        location: Location?,
                                        type: String?,
                                        msg: String?
                                    ) {

                                        ProgressLoading.dismiss()
                                        if (location != null) {


                                            if (CommonUtilities.isFake(baseActivity, location)) {
                                                viewModel.confirmStartPoint(
                                                    CommonUtilities.convertDateToMillis(DATE)
                                                        .toString(),
                                                    shiftID,
                                                    CommonUtilities.currentToMillis.toString(),
                                                    model?.startPointId.toString(),
                                                    "0",
                                                    "1",
                                                    "1",
                                                    true, startTime
                                                )
                                            } else {
                                                viewModel.confirmStartPoint(
                                                    CommonUtilities.convertDateToMillis(DATE)
                                                        .toString(),
                                                    shiftID,
                                                    CommonUtilities.currentToMillis.toString(),
                                                    model?.startPointId.toString(),
                                                    "0",
                                                    location?.latitude.toString(),
                                                    location?.longitude.toString(),
                                                    true, startTime
                                                )
                                            }


                                        } else {
                                            baseActivity.runOnUiThread {
                                               Toast.makeText(
                                                    baseActivity,
                                                    getString(R.string.error_location_try_again),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }


                                        }
                                    }
                                })
                        }
                    }

                }

                builder.setNegativeButton(getString(R.string.no)) { dialog, which ->

                    dialog.dismiss()
                }
                val alert = builder.create()
                alert.show()


            }

            R.id.endShift -> {
                val builder = AlertDialog.Builder(baseActivity)
                builder.setTitle(getString(R.string.end_shift))
                builder.setMessage(getString(R.string.are_u_sure))
                builder.setIcon(R.drawable.ic_warning)
                builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                    dialog.dismiss()


                    if (LocationUtils.checkPermission(baseActivity)) {
                        ProgressLoading.showWithText(baseActivity, getString(R.string.fetching_location))
                        val myLocation = MyLocation(baseActivity, true)
                        myLocation.getLocation(baseActivity, object : MyLocation.LocationResult() {
                            override fun gotLocation(
                                location: Location?,
                                type: String?,
                                msg: String?
                            ) {

                                ProgressLoading.dismiss()
                                if (location != null) {


                                    if (fullList.isNullOrEmpty()) {

                                        baseActivity.runOnUiThread {
                                            Toast.makeText(
                                                baseActivity,
                                                getString(R.string.cant_start_empty_shift),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }

                                    } else {

                                        if (CommonUtilities.isFake(baseActivity, location)) {
                                            viewModel.confirmStartPoint(
                                                CommonUtilities.convertDateToMillis(DATE)
                                                    .toString(),
                                                shiftID,
                                                CommonUtilities.currentToMillis.toString(),
                                                model?.startPointId.toString(),
                                                "0",
                                                "1",
                                                "1",
                                                false, startTime
                                            )
                                        } else {
                                            viewModel.confirmStartPoint(
                                                CommonUtilities.convertDateToMillis(DATE)
                                                    .toString(),
                                                shiftID,
                                                CommonUtilities.currentToMillis.toString(),
                                                model?.startPointId.toString(),
                                                "0",
                                                location?.latitude.toString(),
                                                location?.longitude.toString(),
                                                false, startTime
                                            )
                                        }


                                    }


                                } else {
                                    baseActivity.runOnUiThread {
                                        Toast.makeText(
                                            baseActivity,
                                            getString(R.string.error_location_try_again),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }


                                }
                            }
                        })
                    }


                }
                builder.setNegativeButton(getString(R.string.no)) { dialog, which ->

                    dialog.dismiss()
                }
                val alert = builder.create()
                alert.show()


            }

            R.id.addToReport -> {


                if (model != null) {
                    val startpoint =
                        StartPoint(model?.startPointId!!, model?.startAt, model?.startPoint)
                    viewModel.dataManager.saveStartPoint(startpoint)

                    val cycle = Cycle(
                        model?.planId,
                        model?.planCycleId,
                        model?.fromDate,
                        model?.toDate,
                        model?.planAccountId,
                        model?.cycleArName,
                        true,
                        0,
                        0
                    )
                    viewModel.dataManager.saveCycle(cycle)

                } else {
                    val startpoint = StartPoint(0, "", "")
                    viewModel.dataManager.saveStartPoint(startpoint)

                    val cycle = Cycle(0, 0, "", "", 0, "", true, 0, 0)
                    viewModel.dataManager.saveCycle(cycle)

                }
                if (shift.equals("All Day")) {
                    Toast.makeText(baseActivity, getString(R.string.choose_shift_first), Toast.LENGTH_SHORT).show()
                } else {
                    chooseActivity()
                }


            }
        }
    }

    override fun startVisit(planEntity: PlanEntity?) {
        this.planEntity = planEntity!!
        if (planEntity?.isStarted!!) {
            val intent = Intent(baseActivity, CallsActivity::class.java);
            intent.putExtra("PlanVisitModel", planEntity);
            baseActivity.startActivity(intent);
        } else {

            val builder = AlertDialog.Builder(baseActivity)
            builder.setTitle(getString(R.string.confirm))
            builder.setMessage(getString(R.string.are_u_sure))
            builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->


                if (LocationUtils.checkPermission(baseActivity)) {
                    ProgressLoading.showWithText(baseActivity, getString(R.string.fetching_location))
                    val myLocation = MyLocation(baseActivity, false)
                    myLocation.getLocation(baseActivity, object : MyLocation.LocationResult() {
                        override fun gotLocation(location: Location?, type: String?, msg: String?) {

                            ProgressLoading.dismiss()
                            if (location != null) {


                                if (CommonUtilities.isFake(baseActivity, location)) {
                                    planEntity.cusLat = "1"
                                    planEntity.cusLang = "1"
                                } else {
                                    planEntity.cusLat = location?.latitude.toString()
                                    planEntity.cusLang = location?.longitude.toString()
                                }
                                planEntity.isStarted = true
                                planEntity.startAt = CommonUtilities.currentToMillis.toString()

                                viewModel.update(planEntity)

                                val intent = Intent(baseActivity, CallsActivity::class.java);
                                intent.putExtra("PlanVisitModel", planEntity);
                                baseActivity.startActivity(intent)

                            } else {
                                baseActivity.runOnUiThread {
                                    Toast.makeText(
                                        baseActivity,
                                        "Field to get Location",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                planEntity.cusLat = "0"
                                planEntity.cusLang = "0"

                                planEntity.isStarted = true
                                planEntity.startAt = CommonUtilities.currentToMillis.toString()

                                viewModel.update(planEntity)

                                val intent = Intent(baseActivity, CallsActivity::class.java);
                                intent.putExtra("PlanVisitModel", planEntity);
                                baseActivity.startActivity(intent)

                            }
                        }
                    })
                }

            }
            builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }


    }

    override fun collect(planEntity: PlanEntity?) {
        TODO("Not yet implemented")
    }

    override fun makeCall(planEntity: PlanEntity?) {
    }

    override fun deleteExtra(planEntity: PlanEntity?) {


        val builder = AlertDialog.Builder(baseActivity)
        builder.setTitle(getString(R.string.delete_extra))
        builder.setMessage(getString(R.string.are_u_sure))
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

            viewModel.delete(planEntity, DATE, shift)
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
            // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    override fun startSocialVisit(planEntity: PlanEntity?) {

        if (!planEntity?.visit!!) {


            val dialogBuilder = android.app.AlertDialog.Builder(baseActivity)
            // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.finish_visit, null)
            dialogBuilder.setView(dialogView)
            val noteEditText = dialogView.findViewById<View>(R.id.noteEditText) as EditText
            val addButton = dialogView.findViewById<View>(R.id.addButton) as Button
            val skipButton = dialogView.findViewById<View>(R.id.skipButton) as TextView

            val alertDialog = dialogBuilder.create()
            addButton.setOnClickListener {


                planEntity?.visit = true
                planEntity?.notes = noteEditText.text.toString()
                viewModel.update(planEntity)
                viewModel.getAllByDateAndShift(DATE, shift, "5");
                alertDialog.dismiss()


            }
            skipButton.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()


        }
    }

    override fun order(planEntity: PlanEntity?) {


    }

    private fun setUpRecycler() {
        binding.recyclerView?.setLayoutManager(LinearLayoutManager(baseActivity))
        adapter = SuperReportAdapter(baseActivity, baseActivity, viewModel.dataManager, this, this)
        binding.recyclerView?.setAdapter(adapter)

        binding?.NestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val TAG = "nested_sync"
            if (scrollY > oldScrollY) {
                Log.i(TAG, "Scroll DOWN")
                binding?.addToReport?.hide();
            }
            if (scrollY < oldScrollY) {
                Log.i(TAG, "Scroll UP")
                binding?.addToReport?.show();
            }
        })
    }

    private fun setListeners() {
        binding.submitReport?.setOnClickListener(this)
        binding.startShift?.setOnClickListener(this)
        binding.endShift?.setOnClickListener(this)
        binding.addToReport?.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.main2, menu)
        val item = menu?.findItem(R.id.spinner)
        val sync = menu?.findItem(R.id.sync)
        sync?.setVisible(false)

        spinner = item?.actionView as Spinner
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.spinner_list_item_array, R.layout.spinner_item
        )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner?.adapter = adapter
        spinner?.onItemSelectedListener = this




        if (viewModel.dataManager.startShift) {


            shiftID = viewModel.dataManager.shift.id.toString()
            shift = viewModel.dataManager.shift.name
            DATE = fmt?.format(viewModel.dataManager.shift.startedAt.toLong())
            DATE_IN_MILLIS = viewModel.dataManager.shift.startedAt

            System.out.println(
                " shift info " + shiftID + "\n" +
                        DATE_IN_MILLIS + "\n" +
                        DATE + "\n"

            )

            if (spinner != null) {

                if (viewModel.dataManager.shift.id == 8)
                    spinner?.setSelection(0)
                else
                    spinner?.setSelection(1)

                spinner?.isEnabled = false

            } else {
                System.out.println(
                    " spinner != null "
                )
            }


            startDate?.timeInMillis = DATE_IN_MILLIS!!.toLong()
            endDate?.timeInMillis = DATE_IN_MILLIS!!.toLong()

            horizontalCalendar?.setRange(startDate, endDate)
            horizontalCalendar?.refresh()

            viewModel.getAllByDateAndShift(DATE, shift, "2")

            setButtonStarted()


        } else {
            System.out.println(
                " shift info else "

            )
            viewModel.isStarted(DATE, shift, 183)
        }




        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.sync -> {
                // slideshowviewModel.syncReport(DATE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * action bar spinner
     */
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    /**
     * action bar spinner
     */
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        var x = getResources().getStringArray(R.array.spinner_list_item_array)
        shift = x[p2]


        if (x[p2].equals("AM Shift")) {
            shiftID = "8"
        } else {
            shiftID = "9"
        }

        viewModel.getAllByDateAndShift(DATE, shift, "6");

    }

    fun setButtonReported() {
        binding.startShift.setBackgroundResource(R.drawable.button_grey)
        binding.endShift.setBackgroundResource(R.drawable.button_grey)
        binding.submitReport.setBackgroundResource(R.drawable.button_grey)


        binding.startShift.setEnabled(false)
        binding.endShift.setEnabled(false)
        binding.submitReport.setEnabled(false)
    }

    fun setButtonReady() {
        binding.startShift.setBackgroundResource(R.drawable.button_green)
        binding.endShift.setBackgroundResource(R.drawable.button_grey)
        binding.submitReport.setBackgroundResource(R.drawable.button_grey)


        binding.startShift.setEnabled(true)
        binding.endShift.setEnabled(false)
        binding.submitReport.setEnabled(false)


        if (spinner != null) {
            spinner?.isEnabled = true
        }

    }

    fun setButtonStarted() {
        binding.startShift.setBackgroundResource(R.drawable.button_grey)
        binding.endShift.setBackgroundResource(R.drawable.button_green)
        binding.submitReport.setBackgroundResource(R.drawable.button_grey)


        binding.startShift.setEnabled(false)
        binding.endShift.setEnabled(true)
        binding.submitReport.setEnabled(false)


        if (spinner != null) {
            spinner?.isEnabled = false
        }

    }

    fun setButtonEnded() {
        binding.startShift.setBackgroundResource(R.drawable.button_grey)
        binding.endShift.setBackgroundResource(R.drawable.button_grey)
        binding.submitReport.setBackgroundResource(R.drawable.button_green)


        binding.startShift.setEnabled(false)
        binding.endShift.setEnabled(false)
        binding.submitReport.setEnabled(true)


        if (spinner != null) {
            spinner?.isEnabled = true
        }

    }


    override fun update(planEntity: PlanEntity?) {
        viewModel.updateModel(planEntity)
        viewModel.getAllByDateAndShift(DATE, shift, "");

    }


    fun submitDialog() {
        dialog = ConfirmDialog(
            baseActivity,
            baseActivity,
            viewModel.dataManager,
            CommonUtilities.getDoubleVisitsEmpsIdes(fullList),
            DATE!!,
            shift,
            shiftID.toInt(),
            this
        );
        dialog.setCanceledOnTouchOutside(true);
        val window = dialog.getWindow();
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        );
        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
    }

    override fun updateData() {

        CommonUtilities.writeToSDFile("")
        viewModel.reportShift(DATE, shift)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.report)

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }


    /**
     * open dialog to choose type of activity
     */
    private fun chooseActivity() {


        val factory = LayoutInflater.from(baseActivity)
        val choose_activity_type: View = factory.inflate(R.layout.choose_activity_type, null)

        if (activityTypeDialog != null && activityTypeDialog?.isShowing!!) {
            return
        }

        activityTypeDialog = android.app.AlertDialog.Builder(baseActivity).create()
        activityTypeDialog?.setCancelable(true)
        activityTypeDialog?.setView(choose_activity_type)
        activityTypeDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        activityTypeDialog?.show()

        var activitiesRecyclerView: RecyclerView =
            choose_activity_type.findViewById(R.id.activitiesRecyclerView)
        var close: ImageView = choose_activity_type.findViewById(R.id.close)
        var videoView: ExoVideoView = choose_activity_type.findViewById(R.id.videoView)
        var imageView:ImageView = choose_activity_type.findViewById(R.id.imageView)
        var bannerslider: Slider = choose_activity_type.findViewById(R.id.bannerSlider)
        var cardviewAds: CardView = choose_activity_type.findViewById(R.id.cardview_ads)
        var btnHideShowAds: ImageView= choose_activity_type.findViewById(R.id.btn_hide_show_ads)
        var constrAds: ConstraintLayout = choose_activity_type.findViewById(R.id.constr_ads)
        var moreThanAds:TextView=choose_activity_type.findViewById(R.id.tv_more_than_ads)
        lateinit var mediaSource: SimpleMediaSource

        var activitiesAdapter = ActivitiesAdapter(baseActivity, this)
        activitiesRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        activitiesRecyclerView.adapter = activitiesAdapter

        close.setOnClickListener(View.OnClickListener { activityTypeDialog?.dismiss() })

        viewModel.getActivities()
        viewModel.responseActivitiesLive?.observe(this, androidx.lifecycle.Observer {

            activitiesAdapter.setMyData(it)

        })
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.CREATE_PLAN) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)) {
            imageView.visibility = View.VISIBLE
            Glide.with(this).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
        }
        if (!model.webPageLink.equals("")) {
            cardviewAds.setOnClickListener {
                openWebPage(model.webPageLink)
            }
        }
        when (model.type) {
            "Video" -> {
                videoView.visibility = View.VISIBLE
                mediaSource = SimpleMediaSource(model.resourceLink)
                videoView.play(mediaSource);
            }
            "Image" -> {
                imageView.visibility = View.VISIBLE
                Glide.with(this).load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
            }
            "GIF" -> {
                imageView.visibility = View.VISIBLE
                Glide.with(this).asGif().load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView);


            }
            "Slider" -> {
                bannerslider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(context))
                bannerslider?.setInterval(5000)

                val list = ArrayList<String>()
                for (i in model.slideImages!!) {
                    list.add(i?.link!!)
                }
                bannerslider?.setAdapter(MainSliderAdapter(list))
            }
        }
        if (model.show_ad == true) {
            btnHideShowAds.setVisibility(View.VISIBLE)
            btnHideShowAds.setOnClickListener {
                if (constrAds.visibility == View.VISIBLE) {
                    constrAds.setVisibility(View.GONE)
                    btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
                } else {
                    constrAds.setVisibility(View.VISIBLE)
                    btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
                }
            }
        }
        if (model.show_more == true) {
            moreThanAds.setVisibility(View.VISIBLE)
            moreThanAds.setOnClickListener {
                val  intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }
    }

    /**
     * save type of activity which selected into data manager
     */
    override fun saveActivityType(activities: ActivityEntity?) {

        System.out.println(model.toString())
        if (activities?.typeId?.equals(1)!!) {


            if (fullList.isNullOrEmpty()) {
                chooseStartPointDialog = ChooseStartPoint(
                    baseActivity,
                    this@ManagerReportFragment,
                    viewModel.dataManager!!,
                    activities?.typeId!!,
                    activities,
                    DATE,
                    0
                );
                chooseStartPointDialog.setCanceledOnTouchOutside(true);
                val window = chooseStartPointDialog.getWindow();
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                );
                chooseStartPointDialog.getWindow()
                    ?.setBackgroundDrawableResource(android.R.color.transparent);
                chooseStartPointDialog.getWindow()
                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                chooseStartPointDialog.show();
            } else {

                for (m in fullList!!) {

                    if (!m?.startPoint.isNullOrEmpty()) {
                        openSingleActivity(activities, AddPlanSingleActivity::class.java)
                        activityTypeDialog?.dismiss()
                        return
                    }
                }

                chooseStartPointDialog = ChooseStartPoint(
                    baseActivity,
                    this@ManagerReportFragment,
                    viewModel?.dataManager!!,
                    activities?.typeId!!,
                    activities,
                    DATE,
                    0
                );
                chooseStartPointDialog.setCanceledOnTouchOutside(true);
                val window = chooseStartPointDialog.getWindow();
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                );
                chooseStartPointDialog.getWindow()
                    ?.setBackgroundDrawableResource(android.R.color.transparent);
                chooseStartPointDialog.getWindow()
                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                chooseStartPointDialog.show();

            }


        } else if (activities?.typeId?.equals(2)!!) {

            activitiesModel = activities!!
            chooseEmployee = ChooseEmployee(baseActivity, this, viewModel?.dataManager!!);
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
            activities!!
            //  openSingleActivity(activities!!, DoubleActivity::class.java)

        } else if (activities?.typeId?.equals(3)!!) {

        } else if (activities?.typeId?.equals(4)!!) {
            openSingleActivity(activities!!, AddOfficeActivity::class.java)


        } else if (activities?.typeId?.equals(5)!!) {
            openSingleActivity(activities!!, AddOfficeActivity::class.java)

        } else if (activities?.typeId?.equals(6)!!) {

            openSingleActivity(activities!!, AddMeetingActivity::class.java)


        } else {

            openSingleActivity(activities!!, AddPlanSingleActivity::class.java)

        }

        activityTypeDialog?.dismiss()
    }

    /**
     * save start point which selected into data manager
     */
    override fun chooseStartPoint(
        id: Int,
        date: String?,
        name: String?,
        type: Int,
        activities: ActivityEntity?
    ) {


        chooseStartPointDialog.dismiss()
        val startPoint = StartPoint(id, date, name)

        System.out.println(startPoint.toString())
        viewModel.dataManager.saveStartPoint(startPoint)


        when (type) {
            1 -> {
                openSingleActivity(activities!!, AddPlanSingleActivity::class.java)
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
        TODO("Not yet implemented")
    }

    /**
     * open single activity to choose plan
     */
    private fun openSingleActivity(activityModel: ActivityEntity, cls: Class<*>?) {
        val intent = Intent(baseActivity, cls)
        intent.putExtra("Shift", shift)
        intent.putExtra("Activity", activityModel)
        intent.putExtra("ShiftId", shiftID)
        intent.putExtra("Date", DATE)
        intent.putExtra("DATE_IN_MILLIS", DATE_IN_MILLIS)
        intent.putExtra(
            "Day",
            CommonUtilities.getDayName(horizontalCalendar?.selectedDate?.timeInMillis!!)
        )
        intent.putExtra("EXTRA", true)
        intent.putExtra("cusIdes", CommonUtilities.getCusIdes(fullList))
        intent.putExtra("cusBranchIds", CommonUtilities.getBranchIdes(fullList))
        startActivity(intent)
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee.dismiss()

        System.out.println(model?.empAccountId.toString())

        val intent = Intent(baseActivity, AddPlanDoubleExtraActivity::class.java)
        intent.putExtra("Shift", shift)
        intent.putExtra("Activity", activitiesModel)
        intent.putExtra("ShiftId", shiftID)
        intent.putExtra("Date", DATE)
        intent.putExtra("EMPLOYEE_ID", model)
        intent.putExtra("DATE_IN_MILLIS", DATE_IN_MILLIS)
        intent.putExtra(
            "Day",
            CommonUtilities.getDayName(horizontalCalendar?.selectedDate?.timeInMillis!!)
        )
        intent.putExtra("EXTRA", true)
        intent.putExtra("cusIdes", CommonUtilities.getCusIdes(fullList))
        intent.putExtra("cusBranchIds", CommonUtilities.getBranchIdes(fullList))
        startActivity(intent)

    }


    override fun onResume() {
        super.onResume()
        if (spinner != null) {
            viewModel.getAllByDateAndShift(DATE, spinner?.selectedItem?.toString(), "1223");
        }
    }


}