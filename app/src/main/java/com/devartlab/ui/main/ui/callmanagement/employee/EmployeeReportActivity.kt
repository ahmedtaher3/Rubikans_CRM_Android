package com.devartlab.ui.main.ui.callmanagement.employee

import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.ActivityEmployeeReportBinding
import com.devartlab.model.Cycle
import com.devartlab.model.EmployeeReport
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.AdModel
import com.devartlab.model.StartEndPoint
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.callmanagement.employee.temp.TempReportFragment
import com.devartlab.ui.main.ui.cycles.ChangeCycle
import com.devartlab.ui.main.ui.cycles.ChangeCycleInterface
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.ramijemli.percentagechartview.PercentageChartView
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import ss.com.bannerslider.Slider
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmployeeReportActivity : BaseActivity<ActivityEmployeeReportBinding>(), ChangeCycleInterface,
    ChooseEmployeeInterFace, AdapterView.OnItemSelectedListener {
    var mChart: PercentageChartView? = null
    lateinit var binding: ActivityEmployeeReportBinding
    lateinit var viewModel: EmployeeReportViewModel
    lateinit var adapter: EmployeeReportAdapter
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var mediaSource: SimpleMediaSource
    var horizontalCalendar: HorizontalCalendar? = null
    var startDate: Calendar? = null
    var endDate: Calendar? = null
    var fmt: SimpleDateFormat? = null
    lateinit var fullList: ArrayList<EmployeeReport>
    private var DATE: String? = null
    private var Shift: String = "AM Shift"
    private var ShiftID: String = "8"
    private var filterDatamodel: FilterDataEntity? = null
    var fm: FragmentManager? = null

    lateinit var queue: RequestQueue

    override fun getLayoutId(): Int {
        return R.layout.activity_employee_report
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        queue = Volley.newRequestQueue(this)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Daily Report"

        viewModel = ViewModelProviders.of(this).get(EmployeeReportViewModel::class.java)
        fullList = ArrayList()
        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)


        filterDatamodel = FilterDataEntity(
            viewModel!!.dataManager.user.empId,
            viewModel!!.dataManager.user.nameAr,
            viewModel!!.dataManager.user.title,
            "",
            viewModel!!.dataManager.user.accId,
            0,
            viewModel!!.dataManager.user.image,
            0, "", 0, ""
        )



        binding.empImage?.setImageResource(R.drawable.user_logo)
        binding.drName.setText(filterDatamodel?.empName)
        binding.drTitle.setText(filterDatamodel?.empTitle)
        if (!filterDatamodel?.fileImage.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(filterDatamodel?.fileImage, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }


        binding.cycles.setOnClickListener(View.OnClickListener {

            val changeCycle = ChangeCycle(
                this@EmployeeReportActivity,
                this@EmployeeReportActivity,
                viewModel.dataManager,
                filterDatamodel?.empAccountId!!
            )
            changeCycle.setCanceledOnTouchOutside(true)
            val window = changeCycle.window
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            changeCycle.window?.setBackgroundDrawableResource(android.R.color.transparent)
            changeCycle.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            changeCycle.show()
        })

        val adapter = ArrayAdapter.createFromResource(
            this@EmployeeReportActivity,
            R.array.spinner_list_item_array,
            R.layout.spinner_item_black
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_black)
        binding.shiftSpinner.adapter = adapter
        binding.shiftSpinner.onItemSelectedListener = this

        binding.reportMap.setOnClickListener(View.OnClickListener {

            replace_fragment(
                ReportMapFragment.newInstance(
                    filterDatamodel?.empAccountId.toString(),
                    (CommonUtilities.convertToMillis(DATE)!!.toLong() + 7200000).toString(),
                    ShiftID
                ), "ReportMapFragment"
            )
            //  replace_fragment(ReportMapFragment.newInstance(filterDatamodel!!.empAccountId, CommonUtilities.convertToMillis(DATE)!!, ShiftID), "ReportMapFragment")

        })
        binding.empImage.setOnClickListener(View.OnClickListener {

            chooseEmployee = ChooseEmployee(
                this@EmployeeReportActivity,
                this@EmployeeReportActivity,
                viewModel.dataManager
            )
            chooseEmployee.setCanceledOnTouchOutside(true)
            val window = chooseEmployee.window
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee.show()
        })
        setUpCalendar()
        setupRecyclerView()
        setObservers()
        viewModel.getCycleReport(
            filterDatamodel?.empAccountId.toString(),
            viewModel.dataManager.newOldCycle.currentCycleId.toString()
        )
        ads()
    }

    private fun setUpCalendar() {

        startDate = Calendar.getInstance();
        startDate?.timeInMillis = viewModel?.dataManager?.newOldCycle?.currentFromDateMs!!


        endDate = Calendar.getInstance();
        endDate?.timeInMillis = viewModel?.dataManager?.newOldCycle?.currentToDateMs!!



        horizontalCalendar = HorizontalCalendar.Builder(binding?.root, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(5)
            .build();

        horizontalCalendar?.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {

                DATE = fmt?.format(date?.timeInMillis)
                clearAdapter()
                viewModel.getDailyReport(
                    false,
                    filterDatamodel?.empAccountId!!,
                    CommonUtilities.convertToMillis(DATE)!! + 7200000,
                    ShiftID.toInt()
                )


            }
        }

    }


    private fun setupRecyclerView() {
        adapter = EmployeeReportAdapter(this@EmployeeReportActivity, viewModel.dataManager!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@EmployeeReportActivity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.isNestedScrollingEnabled = false

    }


    private fun setObservers() {

        viewModel.responseLiveDay.observe(this, Observer { it ->


            if (it.isSuccesed) {

                fullList.clear()
                fullList = it.data.employeeReport
                fullList.add(0, EmployeeReport())
                if (it.data.employeeReport.isNullOrEmpty()) {
                    binding.emptyList.visibility = View.VISIBLE

                } else {
                    binding.emptyList.visibility = View.GONE

                }

                System.out.println(it.data.startEndPoint.size)

                if (it.data.startEndPoint.isNullOrEmpty() || it.data.startEndPoint.size < 2) {
                    adapter.setMyData(
                        fullList, StartEndPoint(
                            0,
                            "0",
                            0,
                            "0",
                            0,
                            "0",
                            0,
                            0,
                            "0",
                            "0",
                            0
                        ), StartEndPoint(
                            0,
                            "0",
                            0,
                            "0",
                            0,
                            "0",
                            0,
                            0,
                            "0",
                            "0",
                            0
                        )
                    )

                } else {

                    val model1 = it.data.startEndPoint[0]
                    val model2 = it.data.startEndPoint[1]

                    model1.address =
                        getAddress(model1.latVal?.toDouble()!!, model1.langVal?.toDouble()!!)!!
                    model2.address =
                        getAddress(model2.latVal?.toDouble()!!, model2.langVal?.toDouble()!!)!!

                    adapter.setMyData(fullList, model1, model2)
                    System.out.println(" fullList " + model2.address)
                }

            } else {
                binding.emptyList.visibility = View.VISIBLE
                adapter.setMyData(ArrayList(), StartEndPoint(), StartEndPoint())
                Toast.makeText(this@EmployeeReportActivity, it.rerurnMessage, Toast.LENGTH_SHORT)
                    .show()
            }


        })


        viewModel.responseLiveCycle.observe(this, Observer { it ->

            if (it.isSuccesed) {

                var list = 0
                var visited = 0
                for (model in it.data.coverageReport) {
                    list += model.list
                    visited += model.visited
                }


                if (!it.data.coverageReport.isNullOrEmpty()) {


                    try {
                        binding.firstClass.setText(it.data.coverageReport[0].class_.toString())
                        binding.firstList.setText(it.data.coverageReport[0].list.toString())
                        binding.firstFrequency.setText(it.data.coverageReport[0].visited.toString())
                        binding.firstCoverage.setText(it.data.coverageReport[0].coverage.toString())


                    } catch (e: Exception) {
                    }

                    try {
                        binding.secondClass.setText(it.data.coverageReport[1].class_.toString())
                        binding.thirdList.setText(it.data.coverageReport[1].list.toString())
                        binding.secondFrequency.setText(it.data.coverageReport[1].visited.toString())
                        binding.secondCoverage.setText(it.data.coverageReport[1].coverage.toString())

                    } catch (e: Exception) {
                    }

                    try {
                        binding.thirdClass.setText(it.data.coverageReport[2].class_.toString())
                        binding.thirdList.setText(it.data.coverageReport[2].list.toString())
                        binding.thirdFrequency.setText(it.data.coverageReport[2].visited.toString())
                        binding.thirdCoverage.setText(it.data.coverageReport[2].coverage.toString())

                    } catch (e: Exception) {
                    }



                    binding.listCount.setText("list (" + list.toString() + ")")
                    binding.frequencyCount.setText("visited (" + visited.toString() + ")")


                } else {

                    Toast.makeText(this, "Some error happened", Toast.LENGTH_SHORT).show()



                    binding.firstClass.setText("0")
                    binding.secondClass.setText("0")
                    binding.thirdClass.setText("0")

                    binding.listCount.setText("list (0)")
                    binding.firstList.setText("0")
                    binding.secondList.setText("0")
                    binding.thirdList.setText("0")

                    binding.frequencyCount.setText("visited (0)")
                    binding.firstFrequency.setText("0")
                    binding.secondFrequency.setText("0")
                    binding.thirdFrequency.setText("0")

                    binding.firstCoverage.setText("0")
                    binding.secondCoverage.setText("0")
                    binding.thirdCoverage.setText("0")
                }


                binding.totalBudget.setText("Total Budget = (" + it.data.achivement[0].totalBudget.toString() + ")")

                binding.mChart?.setProgress(
                    it.data.achivement[0].salesAchivement?.take(2)?.toFloat()!!, true
                )


            } else {

                Toast.makeText(this@EmployeeReportActivity, it.rerurnMessage, Toast.LENGTH_SHORT)
                    .show()
            }


        })

        /*viewModel.progress.observe(this, Observer { progress ->

            when (progress) {
                0 -> {

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
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })*/

        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this@EmployeeReportActivity)
                }

                2 -> {

                    binding.dayProgress.visibility = View.VISIBLE
                }


                3 -> {

                    binding.dayProgress.visibility = View.GONE
                }
            }
        })


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var x = getResources().getStringArray(R.array.spinner_list_item_array)

        Shift = x[p2]
        // (DATE!!, Shift)

        if (x[p2].equals("AM Shift")) {
            ShiftID = "8"
        } else {
            ShiftID = "9"
        }

        clearAdapter()
        viewModel.getDailyReport(
            false,
            filterDatamodel?.empAccountId!!,
            CommonUtilities.convertToMillis(DATE)!! + 7200000,
            ShiftID.toInt()
        )

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }*/

    fun replace_fragment(fragment: Fragment?, tag: String?) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
            )
            .add(
                R.id.DailyReportContainer,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }


    override fun chooseEmployee(model: FilterDataEntity?) {

        binding.empImage?.setImageResource(R.drawable.user_logo)
        chooseEmployee.dismiss()
        filterDatamodel = model
        viewModel.getCycleReport(
            filterDatamodel?.empAccountId.toString(),
            viewModel?.dataManager?.cycle.cycleId.toString()
        )
        binding.drName.setText(filterDatamodel?.empName)
        binding.drTitle.setText(filterDatamodel?.empTitle)
        binding.empImage?.setImageResource(R.drawable.user_logo)

        if (model?.fileImage != null) {
            Glide.with(this)
                .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                .placeholder(binding.empImage?.drawable)
                .into(binding.empImage!!)
        } else {
            Glide.with(this)
                .load(com.devartlab.BuildConfig.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                .placeholder(binding.empImage?.drawable)
                .into(binding.empImage!!)
        }


    }

    override fun changeCycle(cycle: Cycle?) {

        binding.cycles.setText(cycle?.cycleArName)
        viewModel.getCycleReport(
            filterDatamodel?.empAccountId.toString(),
            cycle?.cycleId.toString()
        )


        startDate?.timeInMillis = cycle?.fromDateMs?.toLong()!!
        endDate?.timeInMillis = cycle?.toDateMs?.toLong()!!
        horizontalCalendar?.setRange(startDate, endDate)
        horizontalCalendar?.refresh()
        horizontalCalendar?.centerCalendarToPosition(0)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.employee_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.TempReport -> {
                replace_fragment(
                    TempReportFragment.newInstance(
                        filterDatamodel?.empAccountId.toString(),
                        (CommonUtilities.convertToMillis(DATE)!!.toLong() + 7200000).toString(),
                        ShiftID
                    ), "TempReportFragment"
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun clearAdapter() {

        binding.emptyList.visibility = View.VISIBLE
        if (adapter != null) {
            adapter.setMyData(
                ArrayList(), StartEndPoint(
                    0,
                    "0",
                    0,
                    "0",
                    0,
                    "0",
                    0,
                    0,
                    "0",
                    "0",
                    0
                ), StartEndPoint(
                    0,
                    "0",
                    0,
                    "0",
                    0,
                    "0",
                    0,
                    0,
                    "0",
                    "0",
                    0
                )
            )
        }

    }


    fun getAddress(latitude: Double, longitude: Double): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale("ar"))
        var address = ""
        try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            //  throw RuntimeException(e.message)
        }
        return address
    }


    fun ads() {

        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.DAILY_REPORT) {
                model = m
                binding.constrAds.setVisibility(View.VISIBLE)
                if (model.resourceLink.equals(null)
                    && model.paragraph.equals(null)
                    && model.slideImages == null) {
                    binding.constrAds.setVisibility(View.VISIBLE)
                    binding.imageView.visibility = View.VISIBLE
                    Glide.with(this).load(model.default_ad_image)
                        .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
                }
                break
            }
        }

        if (!model.webPageLink.equals("")) {
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
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    binding.textView.setText(
//                        Html.fromHtml(
//                            model.paragraph,
//                            Html.FROM_HTML_MODE_LEGACY
//                        )
//                    );
//                } else
//                    binding.textView.setText(Html.fromHtml(model.paragraph))
                binding.textView.loadDataWithBaseURL(
                    null, model.paragraph!!, "text/html", "utf-8", null
                )
            }
            "Slider" -> {
                binding.bannerSlider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(this))
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
                intent = Intent(this, MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                startActivity(intent)
            }
        }
    }

}