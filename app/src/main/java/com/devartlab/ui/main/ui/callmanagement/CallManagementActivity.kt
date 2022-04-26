package com.devartlab.ui.main.ui.callmanagement

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.databinding.ActivityCallManagementBinding
import com.devartlab.databinding.NavHeaderMainBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.CallsActivity
import com.devartlab.ui.main.MainViewModel
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportActivity
import com.devartlab.ui.main.ui.callmanagement.home.HomeFragment
import com.devartlab.ui.main.ui.callmanagement.list.TypesFragment
import com.devartlab.ui.main.ui.callmanagement.plan.PlanFragment
import com.devartlab.ui.main.ui.callmanagement.planListpermission.ListPermissionActivity
import com.devartlab.ui.main.ui.callmanagement.planListpermission.PlanPermissionActivity
import com.devartlab.ui.main.ui.callmanagement.ranks.*
import com.devartlab.ui.main.ui.callmanagement.ranks.doublevisit.DVReportFragment
import com.devartlab.ui.main.ui.callmanagement.ranks.medicalriprank.MRRankFragment
import com.devartlab.ui.main.ui.callmanagement.ranks.planandcover.PlanAndCoverFragment
import com.devartlab.ui.main.ui.callmanagement.report.ReportFragment
import com.devartlab.ui.main.ui.callmanagement.report.superreport.ManagerReportFragment
import com.devartlab.ui.main.ui.callmanagement.sync.SyncFragment
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.ui.main.ui.employeeservices.home.SelfServiceHomeFragment
import com.devartlab.ui.main.ui.profile.ProfileActivity
import com.devartlab.utils.ProgressLoading
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class CallManagementActivity : BaseActivity<ActivityCallManagementBinding?>(), ChooseEmployeeInterFace {
    lateinit var binding: ActivityCallManagementBinding
    private var toggle: ActionBarDrawerToggle? = null
    var pageFragment: String? = null
    private var activeFragment: String? = null
    lateinit var viewModel: MainViewModel
    var chooseEmployee: ChooseEmployee? = null
    var FLAG = 1


    var plan = true
    var list = true
    var report = true
    var planPermission = true
    var listPermission = true

    override fun getLayoutId(): Int {
        return R.layout.activity_call_management
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (intent.hasExtra("pageFragment")) {
            pageFragment = intent.getStringExtra("pageFragment")
        }



        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                run {

                    try {
                        if (!viewModel!!.authorityDao.getById("61").allowBrowseRecord) {
                            list = false
                        }
                    } catch (e: java.lang.Exception) {
                        list = false
                    }

                    try {
                        if (!viewModel!!.authorityDao.getById("1125").allowBrowseRecord) {
                            plan = false
                        }
                    } catch (e: java.lang.Exception) {
                        plan = false
                    }

                    try {
                        if (!viewModel!!.authorityDao.getById("1126").allowBrowseRecord) {
                            report = false
                        }
                    } catch (e: java.lang.Exception) {
                        report = false
                    }
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe()


        setSupportActionBar(binding!!.toolbar)
        replace_fragment(HomeFragment(), "HomeFragment")



        when (intent.getIntExtra("CALL_MANAGEMENT_FLAG", 0)) {
            0 -> {

            }
            1 -> {

                if (list) {
                    replace_fragment(TypesFragment(), "ListFragment")

                } else {
                    Toast.makeText(this@CallManagementActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                }
            }


            2 -> {

                if (plan) {
                    replace_fragment(PlanFragment(), "PlanFragment")

                } else {
                    Toast.makeText(this@CallManagementActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                }
            }

            3 -> {

                if (report) {
                    replace_fragment(ReportFragment(), "ReportFragment")

                } else {
                    Toast.makeText(this@CallManagementActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                }
            }
            4 -> {

                if (report) {
                    replace_fragment(ManagerReportFragment(), "ManagerReportFragment")

                } else {
                    Toast.makeText(this@CallManagementActivity, "You haven't permission", Toast.LENGTH_SHORT).show()

                }
            }

            5 -> {
                val intent = Intent(this@CallManagementActivity, EmployeeReportActivity::class.java)
                startActivity(intent)
            }

        }



        setUpNavDrawer()
        setUpNavHeader()
        setObservers()
        openFragmentFromAds()
    }

    private fun setObservers() {
        viewModel.progress.observe(this, Observer { integer ->
            when (integer) {
                0 -> {
                    try {
                        ProgressLoading.dismiss()
                    }
                    catch (e: Exception) {

                    }

                }
                1 -> {
                    ProgressLoading.show(this)


                }
            }
        })



        viewModel!!.syncOfflineData.observe(this, Observer {

            if (it.isSuccesed) {

                viewModel.dataManager.saveOfflineMood(true)
                viewModel.onlineBoolean.set(false)
                viewModel.onlineText.set("Offline")

                try {
                    ProgressLoading.dismiss()
                }
                catch (e: Exception) {

                }

            }
            else {
                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }

        })

    }


    private fun setUpNavHeader() {
        val headerMainBinding: NavHeaderMainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.nav_header_main, binding!!.navView, false)

        if (!viewModel!!.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(viewModel!!.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            headerMainBinding.imageView?.setImageBitmap(decodedByte)
        }

        headerMainBinding.imageView?.setOnClickListener {

            val intent = Intent(this@CallManagementActivity, ProfileActivity::class.java)
            startActivity(intent)

        }

        headerMainBinding.onlineButton.setOnClickListener {
            viewModel.dataManager.saveOfflineMood(!viewModel.dataManager.offlineMood)
            if (viewModel.dataManager.offlineMood) {
                viewModel.onlineBoolean.set(false)
                viewModel.onlineText.set("Offline")
            }
            else {
                viewModel.onlineBoolean.set(true)
                viewModel.onlineText.set("Online")
            }

        }


        headerMainBinding.onlineButton.setOnClickListener {

            if (viewModel.dataManager.offlineMood) {


                viewModel.dataManager.saveOfflineMood(false)
                viewModel.onlineBoolean.set(true)
                viewModel.onlineText.set("Online")

            }
            else {
                viewModel.syncOfflineData()

            }

        }
        binding!!.navView.addHeaderView(headerMainBinding.getRoot())
        headerMainBinding.setViewModel(viewModel)
    }

    private fun setUpNavDrawer() { // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        toggle = object :
            ActionBarDrawerToggle(this, binding.drawerLayout, binding!!.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.RolePlay -> {
                    val intent = Intent(this, CallsActivity::class.java);

                    startActivity(intent)
                }
                R.id.Home -> if (activeFragment != "HomeFragment") {
                    replace_fragment(HomeFragment(), "HomeFragment")
                    activeFragment = "HomeFragment"
                }
                else {
                    val test = supportFragmentManager.findFragmentByTag("HomeFragment") as HomeFragment?
                    if (test != null && test.isVisible) {
                    } else {
                        replace_fragment(HomeFragment(), "HomeFragment")
                        activeFragment = "HomeFragment"
                    }
                }
                R.id.List -> if (activeFragment != "ListFragment") {
                    replace_fragment(TypesFragment(), "ListFragment")
                    activeFragment = "ListFragment"
                }
                R.id.Plan -> if (activeFragment != "PlanFragment") {
                    replace_fragment(PlanFragment(), "PlanFragment")
                    activeFragment = "PlanFragment"
                }
                R.id.Report -> if (activeFragment != "SlideshowFragment") {

                    if (viewModel?.dataManager?.isSupervisor!!) {

                        replace_fragment(ManagerReportFragment(), "ManagerReportFragment")
                        activeFragment = "ManagerReportFragment"
                    } else {
                        replace_fragment(ReportFragment(), "ReportFragment")
                        activeFragment = "ReportFragment"
                    }

                }
                R.id.Sync -> if (activeFragment != "SyncFragment") {
                    replace_fragment(SyncFragment(), "SyncFragment")
                    activeFragment = "SyncFragment"
                }
                R.id.exit -> {

                    finish()
                }
                R.id.MRReport -> {

                    if (activeFragment != "MRRankFragment") {
                        replace_fragment(MRRankFragment(), "MRRankFragment")
                        activeFragment = "MRRankFragment"
                    }
                }

                R.id.PCReport -> {

                    if (activeFragment != "PlanAndCoverFragment") {
                        replace_fragment(PlanAndCoverFragment(), "PlanAndCoverFragment")
                        activeFragment = "PlanAndCoverFragment"
                    }
                }

                R.id.listPermission -> {

                    FLAG = 1
                    chooseEmployee = ChooseEmployee(this, this, viewModel?.dataManager!!)
                    chooseEmployee?.setCanceledOnTouchOutside(true)
                    val window = chooseEmployee?.window
                    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                    chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    chooseEmployee?.show()

                }
                R.id.planPermission -> {

                    FLAG = 2
                    chooseEmployee = ChooseEmployee(this, this, viewModel?.dataManager!!)
                    chooseEmployee?.setCanceledOnTouchOutside(true)
                    val window = chooseEmployee?.window
                    window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                    chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    chooseEmployee?.show()

                }

                R.id.DVReport -> {

                    if (activeFragment != "DVReportFragment") {
                        replace_fragment(DVReportFragment(), "DVReportFragment")
                        activeFragment = "DVReportFragment"
                    }
                }
                R.id.SPReport -> {

                    if (activeFragment != "StartPointReportFragment") {
                        replace_fragment(StartPointReportFragment(), "StartPointReportFragment")
                        activeFragment = "StartPointReportFragment"
                    }
                }
                R.id.SVReport -> {

                    if (activeFragment != "SVRankFragment") {
                        replace_fragment(SVRankFragment(), "SVRankFragment")
                        activeFragment = "SVRankFragment"
                    }
                }
                R.id.LogOut -> {
                    UserPreferenceHelper.clean()
                    viewModel!!.dataManager.clear()
                    val intent = Intent(this@CallManagementActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                R.id.DailyReport -> {

                    val intent = Intent(this@CallManagementActivity, EmployeeReportActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {
        activeFragment = tag
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
                        R.id.main_container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (activeFragment == "HomeFragment") {
                finish()
            } else {
                replace_fragment(HomeFragment(), "HomeFragment")
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun chooseEmployee(model: FilterDataEntity?) {

        when (FLAG) {
            1 -> {

                val intent = Intent(this@CallManagementActivity, ListPermissionActivity::class.java)
                intent.putExtra("MODEL", model)
                startActivity(intent)

            }
            2 -> {

                val intent = Intent(this@CallManagementActivity, PlanPermissionActivity::class.java)
                intent.putExtra("MODEL", model)
                startActivity(intent)

            }
        }

    }

    fun openFragmentFromAds(){
        when (pageFragment) {
            "PlanFragment" -> replace_fragment(PlanFragment(), "PlanFragment")
            "HomeFragment" ->replace_fragment(HomeFragment(), "HomeFragment")
            "ReportFragment" ->replace_fragment(ReportFragment(), "ReportFragment")
            "DVReportFragment"->replace_fragment(DVReportFragment(), "DVReportFragment")
            "ListFragment"->replace_fragment(TypesFragment(), "ListFragment")
            "SyncFragment"->replace_fragment(SyncFragment(), "SyncFragment")
        }
    }

}