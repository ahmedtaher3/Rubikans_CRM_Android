package com.devartlab.ui.main.ui.callmanagement.trade

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityTradeBinding
import com.devartlab.databinding.NavHeaderMainBinding
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.MainViewModel
import com.devartlab.ui.main.ui.callmanagement.CallManagementActivity
import com.devartlab.ui.main.ui.employeeservices.SelfServiceActivity
import com.devartlab.ui.main.ui.profile.ProfileActivity
import com.devartlab.ui.main.ui.trade.manager.ManagerFragment
import com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract.SelectProductsActivity
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.ui.main.ui.trade.report.TradeDailyReportFragment
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class TradeActivity : BaseActivity<ActivityTradeBinding>() {
    lateinit var binding: ActivityTradeBinding
    private var viewModel: MainViewModel? = null
    private var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.title = "Trade"

        binding.report.setOnClickListener {
            replace_fragment(TradeDailyReportFragment(), "TradeDailyReportFragment", ArrayList())
        }

        binding.manager.setOnClickListener {
            replace_fragment(ManagerFragment(), "ManagerFragment", ArrayList())

        }

        binding.newOrder.setOnClickListener {


            val intent = Intent(this, SelectProductsActivity::class.java)
            startActivityForResult(intent, 1)


        }


        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        toggle = object : ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding!!.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle?.syncState()


        val headerMainBinding: NavHeaderMainBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.nav_header_main, binding!!.navView, false)


        if (viewModel!!.dataManager.user.image != null) {
            val decodedString = Base64.decode(viewModel!!.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            headerMainBinding.imageView.setImageBitmap(decodedByte)
        }
        headerMainBinding.imageView.setOnClickListener {
            val intent = Intent(this@TradeActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding!!.navView.addHeaderView(headerMainBinding.root)
        headerMainBinding.viewModel = viewModel
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this@TradeActivity, CallManagementActivity::class.java)
            val intent2 = Intent(this@TradeActivity, SelfServiceActivity::class.java)
            when (menuItem.itemId) {

                R.id.report -> {
                    replace_fragment(ManagerFragment(), "ManagerFragment", ArrayList())
                }

                R.id.workDay -> {
                    replace_fragment(TradeDailyReportFragment(), "TradeDailyReportFragment", ArrayList())
                }

                R.id.exit -> {

                    finish()
                }
                R.id.LogOut -> {
                    UserPreferenceHelper.clean()
                    viewModel!!.dataManager.clear()
                    val intent = Intent(this@TradeActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

            }
            true
        }


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_trade
    }


    fun replace_fragment(fragment: Fragment?, tag: String?, data: ArrayList<ContractEntity>) {

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val bundle = Bundle()
        bundle.putParcelableArrayList("PRODUCTS", data)


        fragment?.arguments = bundle
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left
                )
                .replace(
                        R.id.main_container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {


                val list = data?.getStringExtra(("PRODUCTS"))

                val gson = GsonBuilder().create()
                val theList = gson.fromJson<ArrayList<ContractEntity>>(list, object : TypeToken<ArrayList<ContractEntity>>() {}.type)

                System.out.println(" aaa " + list)

               // replace_fragment(OrderPrintFragment(), "OrderPrintFragment", theList)

            }

        }
    }


}