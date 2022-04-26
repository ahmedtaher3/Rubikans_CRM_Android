package com.devartlab.ui.main.ui.callmanagement.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.authority.AuthorityDao
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.databinding.FragmentHomeBinding
import com.devartlab.model.AdModel
import com.devartlab.model.CardModel
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.CallsActivity
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportActivity
import com.devartlab.ui.main.ui.callmanagement.incentiveRule.IncentiveRuleActivity
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryFragment
import com.devartlab.ui.main.ui.callmanagement.list.TypesFragment
import com.devartlab.ui.main.ui.callmanagement.plan.PlanFragment
import com.devartlab.ui.main.ui.callmanagement.planListpermission.ListPermissionActivity
import com.devartlab.ui.main.ui.callmanagement.planListpermission.PlanPermissionActivity
import com.devartlab.ui.main.ui.callmanagement.ranks.SVRankFragment
import com.devartlab.ui.main.ui.callmanagement.ranks.StartPointReportFragment
import com.devartlab.ui.main.ui.callmanagement.ranks.doublevisit.DVReportFragment
import com.devartlab.ui.main.ui.callmanagement.ranks.medicalriprank.MRRankFragment
import com.devartlab.ui.main.ui.callmanagement.ranks.planandcover.PlanAndCoverFragment
import com.devartlab.ui.main.ui.callmanagement.report.ReportFragment
import com.devartlab.ui.main.ui.callmanagement.report.superreport.ManagerReportFragment
import com.devartlab.ui.main.ui.callmanagement.sync.SyncFragment
import com.devartlab.ui.main.ui.callmanagement.syncdata.SyncDataDialog
import com.devartlab.ui.main.ui.callmanagement.trade.TradeReportsFragment
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ss.com.bannerslider.Slider
import ss.com.bannerslider.event.OnSlideClickListener

private const val TAG = "HomeFragment"
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ChooseEmployeeInterFace, View.OnClickListener, MenuListAdapter.OnHomeItemClick {
    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    lateinit var dialog: SyncDataDialog
    lateinit var authorityDao: AuthorityDao
    lateinit var adapter: MenuListAdapter
    var isBack: Boolean = true
    var notifCount: Button? = null
    var mNotifCount = 0
    lateinit var mediaSource: SimpleMediaSource


    var salary: Boolean = false
    var attendance: Boolean = false
    var officeboy: Boolean = false
    var expenses: Boolean = false
    var penalty: Boolean = false
    var chooseEmployee: ChooseEmployee? = null
    var FLAG = 1


    var plan = true
    var list = true
    var report = true
    var planPermission = true
    var listPermission = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        authorityDao = DatabaseClient.getInstance(baseActivity)?.appDatabase?.authorityDao()!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        setHasOptionsMenu(true)

        Completable.fromAction(object : io.reactivex.functions.Action {
            @Throws(Exception::class)
            override fun run() {
                run {

                    try {
                        if (!viewModel.authorityDao.getById("61").allowBrowseRecord) {
                            list = false
                        }
                    }
                    catch (e: java.lang.Exception) {
                        list = false
                    }

                    try {
                        if (!viewModel.authorityDao.getById("1125").allowBrowseRecord) {
                            plan = false
                        }
                    }
                    catch (e: java.lang.Exception) {
                        plan = false
                    }

                    try {
                        if (!viewModel.authorityDao.getById("1126").allowBrowseRecord) {
                            report = false
                        }
                    }
                    catch (e: java.lang.Exception) {
                        report = false
                    }

                    try {
                        if (!viewModel.authorityDao.getById("1003").allowBrowseRecord) {
                            planPermission = false
                        }
                    }
                    catch (e: java.lang.Exception) {
                        planPermission = false
                    }

                    try {
                        if (!viewModel.authorityDao.getById("1159").allowBrowseRecord) {
                            listPermission = false
                        }
                    }
                    catch (e: java.lang.Exception) {
                        listPermission = false
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe()


        val list = ArrayList<CardModel>()
        list.add(CardModel(1, baseActivity.resources.getString(R.string.list), R.drawable.list))
        list.add(CardModel(2, baseActivity.resources.getString(R.string.plan), R.drawable.plan))
        list.add(CardModel(3, baseActivity.resources.getString(R.string.report), R.drawable.report))
        list.add(CardModel(4, baseActivity.resources.getString(R.string.daily_report), R.drawable.daily_report))
        list.add(CardModel(5, baseActivity.resources.getString(R.string.role_play), R.drawable.rolplay))
        list.add(CardModel(6, baseActivity.resources.getString(R.string.sync_data), R.drawable.sync))
        list.add(CardModel(7, baseActivity.resources.getString(R.string.plan_and_cover_report), R.drawable.report_general))
        list.add(CardModel(8, baseActivity.resources.getString(R.string.plan_permission), R.drawable.permission))
        list.add(CardModel(9, baseActivity.resources.getString(R.string.list_permission), R.drawable.permission))
        list.add(CardModel(10, baseActivity.resources.getString(R.string.sales_force), R.drawable.report_general))
        list.add(CardModel(11, baseActivity.resources.getString(R.string.super_visor_report), R.drawable.report_general))
        list.add(CardModel(12, baseActivity.resources.getString(R.string.double_visit_report), R.drawable.report_general))
        list.add(CardModel(13, baseActivity.resources.getString(R.string.start_point_report), R.drawable.report_general))
        //list.add(CardModel(14, baseActivity.resources.getString(R.string.trade_reports), R.drawable.report_general))
        //list.add(CardModel(15, baseActivity.resources.getString(R.string.inventory), R.drawable.report_general))
//        list.add(CardModel(16, baseActivity.resources.getString(R.string.incentive_role), R.drawable.ic_incentive_role))// add Activity incentive role

        adapter = MenuListAdapter(baseActivity, list, this)
        val layoutManager = GridLayoutManager(baseActivity, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
        ads()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.menu_home)
        }
        catch (e: Exception) {

        }

        try {
            CommonUtilities.sendMessage(baseActivity, true)
        }
        catch (e: Exception) {
        }
    }

    override fun onClick(view: View) {}

    fun replace_fragment(fragment: Fragment?, tag: String?) {
        baseActivity.supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment!!).addToBackStack(tag).commit()
    }


    /*    fun setObservers() {
            viewModel!!.responseLiveRequests.observe(this, Observer { googleRequestResponse ->

                *//*   binding.notificationText.setText(String.valueOf(googleRequestResponse.getUser().size()));
                      binding.notification.setVisibility(View.VISIBLE);
                      binding.notificationText.setVisibility(View.VISIBLE);*//*

            setNotifCount(googleRequestResponse.getUser().size)
        })

        viewModel!!.progressGoogle.observe(this, Observer { integer ->
            when (integer) {
                0 -> {

                    ProgressLoading.dismiss()

                }
                1 -> {
                    ProgressLoading.show(baseActivity)


                }
            }
        })

    }*/

    var button: Button? = null
    override fun setOnHomeItemClick(model: CardModel) {

        when (model.id) {
            1 -> {
                if (viewModel.dataManager.offlineMood) {
                    Toast.makeText(baseActivity, "you in Offline mode!", Toast.LENGTH_SHORT).show()
                }
                else {

                    if (list) {
                        replace_fragment(TypesFragment(), "ListFragment")
                    }
                    else {
                        Toast.makeText(baseActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                    }


                }
            }
            2 -> {
                if (viewModel.dataManager.offlineMood) {
                    Toast.makeText(baseActivity, "you in Offline mode!", Toast.LENGTH_SHORT).show()
                }
                else {

                    if (list) {
                        replace_fragment(PlanFragment(), "PlanFragment")
                    }
                    else {
                        Toast.makeText(baseActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                    }


                }
            }
            3 -> {

                if (report) {
                    if (viewModel.dataManager.isSupervisor) {

                        replace_fragment(ManagerReportFragment(), "ManagerReportFragment")

                    }
                    else {
                        replace_fragment(ReportFragment(), "ReportFragment")

                    }
                }
                else {
                    Toast.makeText(baseActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                }


            }
            4 -> {

                val intent = Intent(baseActivity, EmployeeReportActivity::class.java)
                startActivity(intent)
            }
            5 -> {
                val intent = Intent(baseActivity, CallsActivity::class.java)

                startActivity(intent)
            }
            6 -> {

                replace_fragment(SyncFragment(), "SyncFragment")

            }
            7 -> {
                replace_fragment(PlanAndCoverFragment(), "PlanAndCoverFragment")
            }
            8 -> {
                if (planPermission) {
                    FLAG = 2
                    chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
                    chooseEmployee?.setCanceledOnTouchOutside(true)
                    val window = chooseEmployee?.window
                    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                    chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    chooseEmployee?.show()
                }
                else {
                    Toast.makeText(baseActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                }


            }
            9 -> {

                if (listPermission) {
                    FLAG = 1
                    chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
                    chooseEmployee?.setCanceledOnTouchOutside(true)
                    val window = chooseEmployee?.window
                    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                    chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    chooseEmployee?.show()
                }
                else {
                    Toast.makeText(baseActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                }


            }
            10 -> {


                replace_fragment(MRRankFragment(), "MRRankFragment")

            }
            11 -> {
                replace_fragment(SVRankFragment(), "SVRankFragment")

            }
            12 -> {
                replace_fragment(DVReportFragment(), "DVReportFragment")

            }
            13 -> {
                replace_fragment(StartPointReportFragment(), "StartPointReportFragment")

            }
            14 -> {
                replace_fragment(TradeReportsFragment(), "TradeReportsFragment")

            }
            15 -> {
                replace_fragment(InventoryFragment(), "InventoryFragment")
            }
            16 -> {// add Activity incentive role
                val intent = Intent(baseActivity, IncentiveRuleActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee?.dismiss()

        when (FLAG) {
            1 -> {

                val intent = Intent(baseActivity, ListPermissionActivity::class.java)
                intent.putExtra("MODEL", model)
                startActivity(intent)

            }
            2 -> {

                val intent = Intent(baseActivity, PlanPermissionActivity::class.java)
                intent.putExtra("MODEL", model)
                startActivity(intent)

            }
        }
    }/* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
         inflater?.inflate(R.menu.main_menu, menu)


         val item = menu?.findItem(R.id.badge)
         button = item?.actionView as Button

         button?.height = 25
         button?.width = 25
         button?.setBackgroundResource(R.drawable.shape_notification)
         button?.setOnClickListener(View.OnClickListener {
             Toast.makeText(baseActivity, "You HasFirst", Toast.LENGTH_SHORT).show()

         })

     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {

         when (item?.itemId) {
             R.id.badge -> {


             }
         }
         return super.onOptionsItemSelected(item)
     }


     private fun setNotifCount(count: Int) {
         button?.text = count.toString()

     }*/


    override fun onStop() {
        super.onStop()
        binding.videoView.stop()
    }
    fun ads(){
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.CALL_MANAGEMENT_PAGE) {
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

        if (!model.webPageLink.isNullOrBlank()) {
            if (model.is_external!!) {
                binding.cardviewAds.setOnClickListener {
                    openWebPage(model.webPageLink)
                }
            } else {
                binding.cardviewAds.setOnClickListener {
                    meuNav(model.webPageLink!!.toInt(), context)
                }
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
                binding.textView.loadDataWithBaseURL(null, model.paragraph!!
                    ,  "text/html", "utf-8", null)
            }
            "Slider" -> {
                binding.bannerSlider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(baseActivity.applicationContext))
                binding.bannerSlider?.setInterval(5000)

                val list = ArrayList<String>()
                for (i in model.slideImages!!) {
                    list.add(i?.link!!)
                }
                binding.bannerSlider?.setAdapter(MainSliderAdapter(list))
                binding.bannerSlider.setOnSlideClickListener {
                    binding.bannerSlider.setOnSlideClickListener(OnSlideClickListener {
                        if (!model.webPageLink.isNullOrBlank()) {
                            when {
                                model.is_external!! -> {
                                    openWebPage(model.webPageLink)
                                }
                                else -> {
                                    meuNav(model.webPageLink!!.toInt(),context)
                                }
                            }
                        }
                    })
                }
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
    }
}