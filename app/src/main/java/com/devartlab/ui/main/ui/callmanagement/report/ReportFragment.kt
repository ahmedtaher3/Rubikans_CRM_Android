package com.devartlab.ui.main.ui.callmanagement.report

import android.app.Activity
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import com.devartlab.data.room.values.ValuesEntity
import com.devartlab.databinding.FragmentReportBinding
import com.devartlab.model.*
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.CallsActivity
import com.devartlab.ui.main.ui.callmanagement.plan.activities.ActivitiesAdapter
import com.devartlab.ui.main.ui.callmanagement.plan.activities.InvoiceTypsAdapter
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.meeting.AddMeetingActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.office.AddOfficeActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.single.AddPlanDoubleExtraActivity
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.single.AddPlanSingleActivity
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.ChooseStartPoint
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.ChooseStartPointInterFace
import com.devartlab.ui.main.ui.callmanagement.trade.customerinvoicecollect.CustomerInvoiceCollectReportActivity
import com.devartlab.ui.main.ui.callmanagement.trade.printer.OrderPrintActivity
import com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract.SelectProductsActivity
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ss.com.bannerslider.Slider
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "ReportFragment"

class ReportFragment : BaseFragment<FragmentReportBinding>(), InvoiceTypsAdapter.ChooseInvoiceType,
    ReportInterface,
    AdapterView.OnItemSelectedListener, View.OnClickListener,
    ActivitiesAdapter.ChooseActivity, ChooseStartPointInterFace, ChooseEmployeeInterFace {

    lateinit var binding: FragmentReportBinding
    lateinit var viewModel: ReportViewModel
    private var adapter: ReportAdapter? = null
    private var DATE: String? = null
    private var DATE_IN_MILLIS: String? = null
    private var shift: String = "AM Shift"
    private var shiftID: String = "8"
    private var fullList: List<PlanEntity?>? = null
    lateinit var dialog: ChooseStartPoint
    private var model: PlanEntity? = null
    private var modelOrder: PlanEntity? = null
    private var purchaseTypeEntity: PurchaseTypeEntity? = null
    private var paymentMethodId: Int? = null
    private var modelWithStartPoint: PlanEntity? = null
    lateinit var planEntity: PlanEntity
    private var alertDialog: android.app.AlertDialog? = null
    lateinit var mediaSource: SimpleMediaSource

    var spinner: Spinner? = null
    var horizontalCalendar: HorizontalCalendar? = null
    var startDate: Calendar? = null
    var endDate: Calendar? = null
    var fmt: SimpleDateFormat? = null
    var activitiesModel: ActivityEntity? = null
    lateinit var chooseEmployee: ChooseEmployee

    var startTime = ""
    var locationFlag: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_report
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)
        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        startDate = Calendar.getInstance()
        endDate = Calendar.getInstance()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        setHasOptionsMenu(true);
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.REPORT_RECYCLER) {
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
        if (!model.webPageLink.equals(null)) {
            binding.cardviewAds.setOnClickListener {
                openWebPage(model.webPageLink)
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
                    binding.textView.setText(
                        Html.fromHtml(
                            model.resourceLink,
                            Html.FROM_HTML_MODE_LEGACY
                        )
                    );
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
        setRecyclerView()
        setHorizontalCalendar()
        setListeners()
        setObservers()
    }

    private fun setObservers() {


        viewModel.allPlansByDate.observe(viewLifecycleOwner, Observer<List<PlanEntity?>?> {


            fullList = it
            binding.startPoint.text = ""

            if (!it.isNullOrEmpty()) {
                binding.emptyList.visibility = View.GONE

                this.model = it[0]

                for (model in it) {

                    if (!model?.startPoint.isNullOrEmpty() && model?.startPointId != 0) {

                        this.modelWithStartPoint = model
                        break

                    }
                }

                if (modelWithStartPoint != null) {
                    if (modelWithStartPoint?.startPoint != "null") {
                        binding.startPoint.text = modelWithStartPoint?.startPoint
                        startTime =
                            CommonUtilities.getTextAfterSlash(modelWithStartPoint?.startPoint!!)

                    }
                }

                Log.d(TAG, "setObservers: " + model.toString())

                if (model?.reported!!) {
                    setButtonReported()
                    if (spinner != null) {
                        spinner?.isEnabled = true
                    }

                } else {


                    if (viewModel.dataManager.startShift) {
                        setButtonStarted()
                        if (spinner != null) {

                            if (viewModel.dataManager.shift.id == 8)
                                spinner?.setSelection(0)
                            else
                                spinner?.setSelection(1)

                            spinner?.isEnabled = false

                        }


                        shiftID = viewModel.dataManager.shift.id.toString()
                        shift = viewModel.dataManager.shift.name
                        DATE = fmt?.format(viewModel.dataManager.shift.startedAt.toLong())
                        DATE_IN_MILLIS = viewModel.dataManager.shift.startedAt


                        startDate?.timeInMillis = DATE_IN_MILLIS!!.toLong()
                        endDate?.timeInMillis = DATE_IN_MILLIS!!.toLong()

                        horizontalCalendar?.setRange(startDate, endDate)
                        horizontalCalendar?.refresh()
                    } else {
                        setButtonReady()

                    }


                }

            } else {


                binding.startPoint.setText("")
                binding.emptyList.visibility = View.VISIBLE
                binding.startShift.setEnabled(true)
                if (viewModel.dataManager.startShift) {

                    setButtonStarted()
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

                    System.out.println(DATE_IN_MILLIS!!.toLong())

                } else {

                    setButtonReady()
                }
            }
            adapter?.updateData(it as List<PlanEntity>)


        })

        viewModel.responseLiveConfiermShift.observe(viewLifecycleOwner, Observer {

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

                viewModel.dataManager.saveStartShift(true, 3)
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

                viewModel.getAllByDateAndShift(DATE, shift, "1");

            } else {
                Toast.makeText(baseActivity, it.rerurnMessage, Toast.LENGTH_SHORT).show()


                if (it.data.startPointData != null) {
                    if (it.data.startPointData.size == 2) {


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


                        viewModel.dataManager.saveStartShift(true, 4)
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

                        viewModel.getAllByDateAndShift(DATE, shift, "1");


                    }
                }
            }

        })

        viewModel.responseLiveEndShift.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "setObservers: " + it.toString())
            if (it.isSuccesed) {


                /*val scheduler = baseActivity.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
                scheduler.cancel(123)*/


                viewModel.dataManager.saveStartShift(false, 5)
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

                System.out.println(valuesEntity.toString())
                viewModel.valuesRepository?.insert(valuesEntity)

                viewModel.getAllByDateAndShift(DATE, shift, "1");

                CommonUtilities.writeToSDFile("")
            } else {
                Toast.makeText(baseActivity, it.rerurnMessage, Toast.LENGTH_SHORT).show()
                if (it.data != null) {
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
                        }
                    }
                }


            }
            viewModel.isStarted(DATE, shift, 401)
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {

            when (it) {
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
                0 -> {


                    Observable.just(true).delay(1000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            ProgressLoading.dismiss()
                            viewModel.getAllByDateAndShift(DATE, shift, "4")
                        }, {});


                }
            }
        })

        viewModel.showText.observe(viewLifecycleOwner, Observer {

            try {
                Toast.makeText(baseActivity, it, Toast.LENGTH_SHORT).show()
            } catch (e: java.lang.Exception) {
            }


        })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.startShift -> {

                Log.d(TAG, "onClick: "+startTime)
                if (viewModel.dataManager.startShift) {


                    val builder = AlertDialog.Builder(baseActivity)
                    builder.setTitle(getString(R.string.end_shift_and_upload_report))
                    builder.setMessage(getString(R.string.are_u_sure))
                    builder.setIcon(R.drawable.ic_warning)
                    builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                        dialog.dismiss()
                        if (LocationUtils.checkPermission(baseActivity)) {
                            ProgressLoading.showWithText(baseActivity, getString(R.string.fetching_your_location))
                            val myLocation = MyLocation(baseActivity  , true)
                            myLocation.getLocation(
                                baseActivity,
                                object : MyLocation.LocationResult() {
                                    override fun gotLocation(location: Location?, type: String? , msg: String?) {

                                        ProgressLoading.dismiss()
                                        if (location != null) {

                                            if (CommonUtilities.isFake(baseActivity, location)) {
                                                viewModel.uploadReport(
                                                    DATE,
                                                    shift,
                                                    1.toDouble(),
                                                    1.toDouble(),
                                                    shiftID.toInt()
                                                )

                                            } else {
                                                viewModel.uploadReport(
                                                    DATE,
                                                    shift,
                                                    location?.latitude!!,
                                                    location?.longitude!!,
                                                    shiftID.toInt()
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
                    builder.setNegativeButton(getString(R.string.no)) { dialog, which ->

                        dialog.dismiss()
                    }
                    val alert = builder.create()
                    alert.show()

                } else {


                    val builder = AlertDialog.Builder(baseActivity)
                    builder.setTitle(getString(R.string.start_shift))
                    builder.setMessage(getString(R.string.are_u_sure))
                    builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                        dialog.dismiss()

                        if (LocationUtils.checkPermission(baseActivity)) {
                            ProgressLoading.showWithText(baseActivity, getString(R.string.fetching_location))
                            val myLocation = MyLocation(baseActivity, true)
                            myLocation.getLocation(
                                baseActivity,
                                object : MyLocation.LocationResult() {
                                    override fun gotLocation(location: Location?, type: String? , msg: String?) {

                                        ProgressLoading.dismiss()
                                        if (location != null) {


                                            if (fullList.isNullOrEmpty()) {


                                                Toast.makeText(
                                                    baseActivity,
                                                    "Cant Start empty shift , please sync plan or add extra",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                            } else {
                                                if (CommonUtilities.isFake(
                                                        baseActivity,
                                                        location
                                                    )
                                                ) {

                                                    viewModel.confirmStartPoint(
                                                        CommonUtilities.convertDateToMillis(DATE)
                                                            .toString(),
                                                        shiftID,
                                                        CommonUtilities.currentToMillis.toString(),
                                                        model?.startPointId.toString(),
                                                        "0",
                                                        "1",
                                                        "1",
                                                        true,
                                                        startTime
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
                                                        true,
                                                        startTime
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
                    Toast.makeText(baseActivity, "Choose Shift First", Toast.LENGTH_SHORT).show()
                } else {
                    chooseActivity()
                }


            }
        }

    }


    private fun setListeners() {
        binding.startShift?.setOnClickListener(this)
        binding.addToReport.setOnClickListener(this)
    }

    private fun setHorizontalCalendar() {


        if (viewModel.dataManager.startShift) {

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

            horizontalCalendar = HorizontalCalendar.Builder(binding.root, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

            viewModel.getAllByDateAndShift(DATE, shift, "2")

            setButtonStarted()
        } else {
            setButtonReady()
            DATE = fmt?.format(CommonUtilities.currentToMillis)
            DATE_IN_MILLIS = CommonUtilities.currentToMillis.toString()
            viewModel.getAllByDateAndShift(DATE, shift, "3")


            if (viewModel.dataManager.user.isOpenReportLimit) {

                startDate?.timeInMillis = viewModel.dataManager.user?.minDate?.toLong()!!
                endDate?.timeInMillis = viewModel.dataManager.user?.maxDate?.toLong()!!

                horizontalCalendar = HorizontalCalendar.Builder(binding.root, R.id.calendarView)
                    .range(startDate, endDate)
                    .datesNumberOnScreen(5)
                    .build();
            } else {

                startDate?.timeInMillis = viewModel.dataManager.cycle?.fromDateMs?.toLong()!!
                endDate?.timeInMillis = viewModel.dataManager.user?.maxDate?.toLong()!!

                horizontalCalendar = HorizontalCalendar.Builder(binding.root, R.id.calendarView)
                    .range(startDate, endDate)
                    .datesNumberOnScreen(5)
                    .build();


            }

            System.out.println(" DATE " + DATE)
        }

        horizontalCalendar?.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {

                DATE = fmt?.format(date?.timeInMillis)

                DATE_IN_MILLIS = date?.timeInMillis.toString()
                viewModel.getAllByDateAndShift(DATE, shift, "1");
                System.out.println(" DATE " + DATE)
                System.out.println(" DATE_IN_MILLIS " + DATE_IN_MILLIS)
            }
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView?.setLayoutManager(LinearLayoutManager(baseActivity))
        adapter = ReportAdapter(baseActivity, this, viewModel.dataManager, baseActivity)
        binding.recyclerView?.setAdapter(adapter)
        binding.recyclerView!!.isNestedScrollingEnabled = false

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

    override fun collect(planEntity: PlanEntity?) {


        Toast.makeText(baseActivity, planEntity?.customerid.toString(), Toast.LENGTH_SHORT).show()
        val intent = Intent(baseActivity, CustomerInvoiceCollectReportActivity::class.java)
        intent.putExtra("CUSTOMER_MODEL", planEntity)

        startActivity(intent)

    }

    override fun makeCall(model: PlanEntity) {}

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

    /**
     * start social visit without open call activity jsut mark as a visited
     */
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

    override fun order(model: PlanEntity?) {
        modelOrder = model
        chooseInvoiceType()


    }

    private fun chooseInvoiceType() {


        val factory = LayoutInflater.from(baseActivity)
        val choose_activity_type: View = factory.inflate(R.layout.choose_activity_type, null)

        if (alertDialog != null && alertDialog?.isShowing!!) {
            return
        }

        alertDialog = android.app.AlertDialog.Builder(baseActivity).create()
        alertDialog?.setCancelable(true)
        alertDialog?.setView(choose_activity_type)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()

        var activitiesRecyclerView: RecyclerView =
            choose_activity_type.findViewById(R.id.activitiesRecyclerView)
        var close: ImageView = choose_activity_type.findViewById(R.id.close)

        var adapter = InvoiceTypsAdapter(baseActivity, this)
        activitiesRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        activitiesRecyclerView.adapter = adapter

        close.setOnClickListener(View.OnClickListener { alertDialog?.dismiss() })

        viewModel.getInvoiceTyps()
        viewModel.typsLive?.observe(this, Observer {

            adapter.setMyData(it)

        })
    }

    override fun setChooseInvoiceType(model: PurchaseTypeEntity?) {

        alertDialog?.dismiss()
        Log.d(TAG, "setChooseInvoiceType: " + model.toString())

        purchaseTypeEntity = model
        val intent = Intent(baseActivity, SelectProductsActivity::class.java)
        intent.putExtra("CUSTOMER_ID", modelOrder?.customerid)
        intent.putExtra("PurchaseTypeEntity", purchaseTypeEntity)
        startActivityForResult(intent, 1)
    }


    /**
     * start  visit by open call activity and make a calls
     */
    override fun startVisit(planEntity: PlanEntity?) {
        this.planEntity = planEntity!!
        if (planEntity?.isStarted!!) {
            val intent = Intent(baseActivity, CallsActivity::class.java);

            Log.d("CallsActivity", "startVisit: " + planEntity.toString())
            intent.putExtra("PlanVisitModel", planEntity);
            baseActivity.startActivity(intent);
        } else {

            val builder = AlertDialog.Builder(baseActivity)
            builder.setTitle(getString(R.string.confirm))
            builder.setMessage(getString(R.string.are_u_sure))
            builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->

                dialog.dismiss()

                if (LocationUtils.checkPermission(baseActivity)) {
                    ProgressLoading.showWithText(baseActivity, getString(R.string.fetching_location))
                    val myLocation = MyLocation(baseActivity, false)
                    myLocation.getLocation(baseActivity, object : MyLocation.LocationResult() {
                        override fun gotLocation(location: Location?, type: String? , msg: String?) {

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * action bar spinner
     */
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var x = getResources().getStringArray(R.array.spinner_list_item_array)
        shift = x[p2]

        viewModel.getAllByDateAndShift(DATE, shift, "6");


        if (x[p2].equals("AM Shift")) {
            shiftID = "8"
        } else {
            shiftID = "9"
        }
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

        if (alertDialog != null && alertDialog?.isShowing!!) {
            return
        }

        alertDialog = android.app.AlertDialog.Builder(baseActivity).create()
        alertDialog?.setCancelable(true)
        alertDialog?.setView(choose_activity_type)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()

        var activitiesRecyclerView: RecyclerView =
            choose_activity_type.findViewById(R.id.activitiesRecyclerView)
        var close: ImageView = choose_activity_type.findViewById(R.id.close)

        var activitiesAdapter = ActivitiesAdapter(baseActivity, this)
        activitiesRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        activitiesRecyclerView.adapter = activitiesAdapter

        close.setOnClickListener(View.OnClickListener { alertDialog?.dismiss() })

        viewModel.getActivities()
        viewModel.responseActivitiesLive?.observe(this, Observer {

            activitiesAdapter.setMyData(it)

        })
    }


    /**
     * save type of activity which selected into data manager
     */
    override fun saveActivityType(activities: ActivityEntity?) {

        System.out.println(model.toString())
        if (activities?.typeId?.equals(1)!!) {


            if (fullList.isNullOrEmpty()) {
                dialog = ChooseStartPoint(
                    baseActivity,
                    this@ReportFragment,
                    viewModel.dataManager!!,
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
                        alertDialog?.dismiss()
                        return
                    }
                }

                dialog = ChooseStartPoint(
                    baseActivity,
                    this@ReportFragment,
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

        alertDialog?.dismiss()
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


        dialog.dismiss()
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

    fun setButtonStarted() {
        binding.startShift.setBackgroundResource(R.drawable.button_green)
        binding.startShift.text = getString(R.string.end_shift)
        binding.startShift.setEnabled(true)

    }

    fun setButtonReported() {
        binding.startShift.setBackgroundResource(R.drawable.button_grey)
        binding.startShift.text = getString(R.string.reported)
        binding.startShift.setEnabled(false)
    }

    fun setButtonReady() {

        binding.startShift.setBackgroundResource(R.drawable.button)
        binding.startShift.text = getString(R.string.start_shift)
        binding.startShift.setEnabled(true)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {


                if (modelOrder != null) {

                    val list = data?.getStringExtra(("PRODUCTS"))
                    val intent = Intent(baseActivity, OrderPrintActivity::class.java)
                    intent.putExtra("PRODUCTS", list)
                    intent.putExtra("CUSTOMER_MODEL", modelOrder)
                    intent.putExtra("PurchaseTypeEntity", purchaseTypeEntity)

                    baseActivity.startActivity(intent)

                }


                // replace_fragment(OrderPrintFragment(), "OrderPrintFragment", theList)

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //   airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults)


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
            viewModel.getAllByDateAndShift(DATE, spinner?.selectedItem?.toString(), "7");
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}

