package com.devartlab.ui.main.ui.employeeservices

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivitySelfServiceBinding
import com.devartlab.databinding.NavHeaderMainBinding
import com.devartlab.ui.auth.devartsite.DevartActivity
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.MainActivity
import com.devartlab.ui.main.MainViewModel
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportActivity
import com.devartlab.ui.main.ui.contactlist.ui.main.ContactsActivity
import com.devartlab.ui.main.ui.devartlink.DevartLinkActivity
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingActivity
import com.devartlab.ui.main.ui.eShopping.orientationVideos.OrientationVideosActivity
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.ui.main.ui.employeeservices.home.SelfServiceHomeFragment
import com.devartlab.ui.main.ui.employeeservices.hrrequest.EmployeeRequestsFragment
import com.devartlab.ui.main.ui.employeeservices.meals.MealsFragment
import com.devartlab.ui.main.ui.market.MarketRequestTypesActivity
import com.devartlab.ui.main.ui.profile.ProfileActivity
import com.google.android.material.navigation.NavigationView


class SelfServiceActivity : BaseActivity<ActivitySelfServiceBinding?>() {
    private var binding: ActivitySelfServiceBinding? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var activeFragment: String = ""
    var pageFragment: String? = null
    lateinit var viewModel: MainViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_self_service
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setSupportActionBar(binding!!.toolbar)

        if (intent.hasExtra("pageFragment")) {
            pageFragment = intent.getStringExtra("pageFragment")
        }
        replace_fragment(SelfServiceHomeFragment(), "SelfServiceHomeFragment")
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        toggle = object : ActionBarDrawerToggle(
                this,
                drawer,
                binding!!.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawer.addDrawerListener(toggle!!)
        toggle!!.syncState()

        val headerMainBinding: NavHeaderMainBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.nav_header_main, binding!!.navView, false)

        if (!viewModel!!.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(viewModel!!.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            headerMainBinding.imageView?.setImageBitmap(decodedByte)
        }

        headerMainBinding.imageView?.setOnClickListener {

            val intent = Intent(this@SelfServiceActivity, ProfileActivity::class.java)
            startActivity(intent)

        }

        headerMainBinding.onlineButton.setOnClickListener {
            viewModel.dataManager.saveOfflineMood(!viewModel.dataManager.offlineMood)
            if (viewModel.dataManager.offlineMood)
            {
                viewModel.onlineBoolean.set(false)
                viewModel.onlineText.set("Offline")
            }
            else{
                viewModel.onlineBoolean.set(true)
                viewModel.onlineText.set("Online")
            }

        }


        binding!!.navView.addHeaderView(headerMainBinding.getRoot())
        headerMainBinding.setViewModel(viewModel)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawer.closeDrawer(GravityCompat.START)
            when (menuItem.itemId) {
                R.id.Home -> {

                    if (activeFragment != "HomeFragment") {
                        replace_fragment(SelfServiceHomeFragment(), "HomeFragment")
                        activeFragment = "HomeFragment"
                    }
                }
                R.id.expenses -> {

                    val intent = Intent(this, EmployeeServicesActivity::class.java)
                    intent.putExtra("PAGE_NUMBER", "1")
                    startActivity(intent)
                }

                R.id.salary -> {
                    val intent = Intent(this, EmployeeServicesActivity::class.java)
                    intent.putExtra("PAGE_NUMBER", "4")
                    startActivity(intent)

                }


                R.id.attendance -> {
                    val intent = Intent(this, EmployeeServicesActivity::class.java)
                    intent.putExtra("PAGE_NUMBER", "5")
                    startActivity(intent)


                }

                R.id.hrRequests -> {

                    if (activeFragment != "EmployeeRequestsFragment") {
                        replace_fragment(EmployeeRequestsFragment(), "EmployeeRequestsFragment")
                        activeFragment = "EmployeeRequestsFragment"
                    }
                }

                R.id.meals -> {
                    if (activeFragment != "MealsFragment") {
                        replace_fragment(MealsFragment(), "MealsFragment")
                        activeFragment = "MealsFragment"
                    }

                }

                R.id.exit -> {

                    finish()

                }

                R.id.LogOut -> {
                    UserPreferenceHelper.clean()
                    viewModel!!.dataManager.clear()
                    val intent = Intent(this@SelfServiceActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                }
            }
            true
        }
        openFragmentFromAds()
    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {
        activeFragment = tag!!
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
            if (activeFragment == "SelfServiceHomeFragment") {
                finish()
            } else {
                replace_fragment(SelfServiceHomeFragment(), "SelfServiceHomeFragment")
            }
        } else {
            super.onBackPressed()
        }
    }
    fun openFragmentFromAds(){
        when (pageFragment) {
            "SelfServiceHomeFragment" -> replace_fragment(SelfServiceHomeFragment(), "SelfServiceHomeFragment")
            "MealsFragment" ->replace_fragment(MealsFragment(), "MealsFragment")
            "SelfServiceHomeFragment" -> replace_fragment(SelfServiceHomeFragment(), "SelfServiceHomeFragment")
        }
    }
}