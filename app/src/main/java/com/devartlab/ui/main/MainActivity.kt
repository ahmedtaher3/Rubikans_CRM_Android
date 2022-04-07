package com.devartlab.ui.main

import android.app.job.JobScheduler
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.AppConstants
import com.devartlab.GetDeviceToken
import com.devartlab.R
import com.devartlab.UpdatePlan
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityMainBinding
import com.devartlab.databinding.NavHeaderMainBinding
import com.devartlab.model.AdModel
import com.devartlab.model.CardModel
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.callmanagement.CallManagementActivity
import com.devartlab.ui.main.ui.callmanagement.home.MenuListAdapter
import com.devartlab.ui.main.ui.callmanagement.syncdata.SyncDataDialog
import com.devartlab.ui.main.ui.contactlist.ui.main.ContactsActivity
import com.devartlab.ui.main.ui.devartLabTeam.DevartLabTeamActivity
import com.devartlab.ui.main.ui.devartlink.DevartLinkActivity
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingActivity
import com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping.Login4EShoppingRequest
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.ui.main.ui.employeeservices.SelfServiceActivity
import com.devartlab.ui.main.ui.employeeservices.approval.ApprovalRequestsFragment
import com.devartlab.ui.main.ui.market.MarketRequestTypesActivity
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.ui.main.ui.profile.ProfileActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_customer.*
import qruz.t.qruzdriverapp.ui.main.fragments.profile.changelang.ChangeLanguage
import ss.com.bannerslider.Slider
import ss.com.bannerslider.event.OnSlideClickListener
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

private const val TAG = "MainActivity"

class MainActivity : BaseActivity<ActivityMainBinding?>(), View.OnClickListener,
    MenuListAdapter.OnHomeItemClick {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: MenuListAdapter
    lateinit var mediaSource: SimpleMediaSource

    private var toggle: ActionBarDrawerToggle? = null
    var textCartItemCount: RelativeLayout? = null
    var callManagementPermission = true
    var marketRequestPermission = true
    var tradePermission = true
    lateinit var dialog: SyncDataDialog


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.drawerLayout
        setSupportActionBar(binding!!.toolbar)



        Log.d(TAG, "onCreate: " + Gson().toJson(viewModel.dataManager.ads.ads!! as Any?))


        Completable.fromAction(object : Action {
            @Throws(Exception::class)
            override fun run() {
                run {
                    if (!viewModel!!.authorityDao.getById("61").allowBrowseRecord && !viewModel!!.authorityDao.getById(
                            "1125"
                        ).allowBrowseRecord && !viewModel!!.authorityDao.getById(
                            "1126"
                        ).allowBrowseRecord
                    ) {
                        callManagementPermission = false
                    } else {

                        if (viewModel!!.dataManager.sFirstTime) {

                            Single.timer(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : SingleObserver<Long?> {
                                    override fun onSubscribe(d: Disposable) {}
                                    override fun onSuccess(aLong: Long) {
                                        if (!viewModel!!.dataManager.startShift) {
                                            dialog = SyncDataDialog(
                                                this@MainActivity,
                                                this@MainActivity,
                                                viewModel?.dataManager!!
                                            );
                                            dialog.setCanceledOnTouchOutside(false);
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

                                    override fun onError(e: Throwable) {}
                                })

                        }

                    }


                    if (!viewModel!!.authorityDao.getById("1026").allowBrowseRecord) {
                        marketRequestPermission = false
                    }


                    try {
                        if (!viewModel!!.authorityDao.getById("1157").allowBrowseRecord) {
                            tradePermission = false
                        }
                    } catch (e: java.lang.Exception) {
                        tradePermission = false
                    }


                }
            }
        }).subscribeOn(Schedulers.io()).subscribe()

        setLiseners()
        setObservers()
        setUpNavHeader()
        setUpNavDrawer()
        setUpRecycler()
        ads()

        //welcome post dialog
        viewModel.getWelcomePost()
        //login 4EShopping
        //change device_type in request
        val getToken = GetDeviceToken(this)
        getToken.getToken(object : GetDeviceToken.TokenResult() {
            override fun success(token: String?) {
                viewModel.dataManager.saveDeviceToken(token!!)
            }

            override fun failure(msg: String?) {

            }

        })

        viewModel!!.getAllPending("allPending", "")
    }

    private fun setObservers() {
        //welcome post dialog
        viewModel.welcomePostResponse.observe(this, Observer {
            if (UserPreferenceHelper.getWelcomeDialog()!=null){
                if (UserPreferenceHelper.getWelcomeDialog().image!=it!!.data.image
                    ||UserPreferenceHelper.getWelcomeDialog().link!=it!!.data.link){
                    showWelcomePostDialog(it!!.data.image,it.data.link)
                    UserPreferenceHelper.saveWelcomeDialog(it.data)
                }
            }else{
                showWelcomePostDialog(it!!.data.image,it.data.link)
                UserPreferenceHelper.saveWelcomeDialog(it.data)
            }
        })
        viewModel.progress.observe(this, Observer { integer ->
            when (integer) {
                0 -> {
                    try {
                        ProgressLoading.dismiss()
                    } catch (e: Exception) {

                    }

                }
                1 -> {
                    ProgressLoading.show(this)


                }
            }
        })

        viewModel!!.responseLiveRequests.observe(this,
            Observer { googleRequestResponse -> setupBadge(googleRequestResponse.hrRequests?.size!! + googleRequestResponse.penaltiesGoogle?.size!! + googleRequestResponse.workFromHomelist?.size!!) })

        viewModel!!.randomLive.observe(this, Observer {
            Toast.makeText(this@MainActivity, it.size.toString(), Toast.LENGTH_SHORT).show()

        })


        viewModel!!.syncOfflineData.observe(this, Observer {

            if (it.isSuccesed) {

                viewModel.dataManager.saveOfflineMood(true)
                viewModel.onlineBoolean.set(false)
                viewModel.onlineText.set("Offline")

                try {
                    ProgressLoading.dismiss()
                } catch (e: Exception) {

                }

            } else {
                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setUpRecycler() {
        val list = ArrayList<CardModel>()
        list.add(CardModel(1, resources.getString(R.string.call_management), R.drawable.call_managment_icon))
        list.add(CardModel(2, resources.getString(R.string.self_service), R.drawable.self_service))
        list.add(CardModel(3, resources.getString(R.string.my_profile), R.drawable.employee))
        list.add(CardModel(4, resources.getString(R.string.market_request), R.drawable.money))
//        list.add(CardModel(5, "DevartLink", R.drawable.devartlink))
        list.add(CardModel(6, "4eShopping", R.drawable.e_shopping))
       // list.add(CardModel(7, "DevartLab Team", R.drawable.ic_team))

        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter

    }

    private fun setUpNavDrawer() {

        toggle = object :
            ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding!!.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle?.syncState()
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this@MainActivity, CallManagementActivity::class.java)
            val intent2 = Intent(this@MainActivity, SelfServiceActivity::class.java)
            when (menuItem.itemId) {
                R.id.List -> if (callManagementPermission) {
                    intent.putExtra("CALL_MANAGEMENT_FLAG", 1)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "You haven't permission", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.Plan -> if (callManagementPermission) {
                    intent.putExtra("CALL_MANAGEMENT_FLAG", 2)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "You haven't permission", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.Report -> if (callManagementPermission) {
                    if (viewModel?.dataManager?.isSupervisor!!) {
                        intent.putExtra("CALL_MANAGEMENT_FLAG", 4)
                        startActivity(intent)
                    } else {
                        intent.putExtra("CALL_MANAGEMENT_FLAG", 3)
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(this@MainActivity, "You haven't permission", Toast.LENGTH_SHORT)
                        .show()
                }

                R.id.DailyReport -> if (callManagementPermission) {
                    intent.putExtra("CALL_MANAGEMENT_FLAG", 5)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "You haven't permission", Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.selfService -> {
                    startActivity(intent2)

                    /*  airLocation = AirLocation(
                              this@MainActivity,
                              true,
                              true,
                              object : AirLocation.Callbacks {
                                  override fun onSuccess(location: Location) {
                                      Toast.makeText(this@MainActivity, "done", Toast.LENGTH_SHORT).show()

                                  }

                                  override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                                      Toast.makeText(this@MainActivity, "Field to get Location , please try again", Toast.LENGTH_SHORT).show()

                                  }
                              })*/


                }


                R.id.changeLang -> {


                    val intent = Intent(this, ChangeLanguage::class.java)
                    startActivity(intent)

                }


                R.id.updatePlan -> {


                    val intent = Intent(this, UpdatePlan::class.java)
                    startActivity(intent)

                }

                R.id.contactsList -> {


                    val intent = Intent(this, ContactsActivity::class.java)
                    startActivity(intent)

                }


                R.id.Logout -> {


                    val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                    scheduler.cancel(123)

                    viewModel?.deleteAllRoom(this)


                }
                R.id.rateUs -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }


                }
                R.id.searchForUpdate -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                }
                R.id.shareApp -> {

                    // startActivity(Intent(this@MainActivity , UpdatePlan::class.java))
                    val intent = Intent(Intent.ACTION_SEND)
                    val shareBody =
                        "Download Devartlab CRM" + "\n" + "\n" + "https://play.google.com/store/apps/details?id=$packageName"
                    intent.type = "text/plain"

                    intent.putExtra(Intent.EXTRA_TEXT, shareBody)

                    startActivity(Intent.createChooser(intent, getString(R.string.share_using)))

                }

            }
            true
        }

    }

    private fun setUpNavHeader() {
        val headerMainBinding: NavHeaderMainBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.nav_header_main,
            binding!!.navView,
            false
        )
        if (viewModel!!.dataManager.user.image != null) {
            val decodedString = Base64.decode(viewModel!!.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            headerMainBinding.imageView.setImageBitmap(decodedByte)
        }
        headerMainBinding.imageView.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        headerMainBinding.onlineButton.setOnClickListener {

            if (viewModel.dataManager.offlineMood) {


                viewModel.dataManager.saveOfflineMood(false)
                viewModel.onlineBoolean.set(true)
                viewModel.onlineText.set("Online")

            } else {
                viewModel.syncOfflineData()

            }

        }
        binding!!.navView.addHeaderView(headerMainBinding.root)
        headerMainBinding.viewModel = viewModel

    }

    private fun setLiseners() {
        binding!!.facebook.setOnClickListener(this)
        binding!!.twitter.setOnClickListener(this)
        binding!!.youtube.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.facebook -> {
                try {
                    packageManager.getPackageInfo("com.facebook.katana", 0)
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + "112487374437269"))
                    startActivity(intent)
                } catch (e: Exception) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/devartlabofficial")
                    )
                    startActivity(intent)
                }
            }
            R.id.twitter -> { /*        Intent intent = null;
                try {
                    // get the Twitter app if possible
                    getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + "449323664"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/devartlabs"));
                }
                startActivity(intent);*/
            }
            R.id.youtube -> {
                var intent: Intent? = null
                try {
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.setPackage("com.google.android.youtube")
                    intent.data =
                        Uri.parse("https://www.youtube.com/channel/UC7bij5Sn6vGCtxC3vPkSuwA")
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        Uri.parse("https://www.youtube.com/channel/UC7bij5Sn6vGCtxC3vPkSuwA")
                    startActivity(intent)
                }
            }
        }
    }


    fun replace_fragment(fragment: Fragment?, tag: String?) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
            )
            .add(R.id.main_container, fragment!!).addToBackStack(tag).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.action_cart)
        val actionView = MenuItemCompat.getActionView(menuItem)
        actionView.setOnClickListener {
            replace_fragment(ApprovalRequestsFragment(), "ApprovalRequestsAdapter")
        }
        textCartItemCount = actionView.findViewById<View>(R.id.cart_badge) as RelativeLayout
        return true
    }

    private fun setupBadge(size: Int) {
        if (textCartItemCount != null) {
            if (size > 0) {
                if (textCartItemCount != null) {
                    textCartItemCount!!.visibility = View.VISIBLE
                }
            } else {
                textCartItemCount!!.visibility = View.GONE
            }
        }
    }

    override fun setOnHomeItemClick(model: CardModel) {
        when (model.id) {
            1 -> {

                if (callManagementPermission) {
                    val intent = Intent(this@MainActivity, CallManagementActivity::class.java)
                    intent.putExtra("CALL_MANAGEMENT_FLAG", 0)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "You haven't permission", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            2 -> {
                val intent = Intent(this@MainActivity, SelfServiceActivity::class.java)
                startActivity(intent)
            }
            3 -> {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
            4 -> {
                if (marketRequestPermission) {
                    val intent = Intent(this@MainActivity, MarketRequestTypesActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "You haven't permission", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            5 -> {

                val intent = Intent(this@MainActivity, DevartLinkActivity::class.java)
                startActivity(intent)

            }
            6 -> {
                if (AppConstants.ShoppingPermission) {
                    if (UserPreferenceHelper.getUser().type_code == "pv#s7#w?x") {
                        val intent = Intent(this@MainActivity, Home4EShoppingActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "You haven't permission", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val intent = Intent(this@MainActivity, Home4EShoppingActivity::class.java)
                    startActivity(intent)
                }
            }
            7 -> {
                val intent = Intent(this@MainActivity, DevartLabTeamActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun ads() {
        var model: AdModel? = null
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.HOME_PAGE) {
                model = m
                binding.constrAds.setVisibility(View.VISIBLE)
                if (model.resourceLink.equals(null) && model.paragraph.equals(null) && model.slideImages == null) {
                    binding.constrAds.setVisibility(View.VISIBLE)
                    binding.imageView.visibility = View.VISIBLE
                    Glide.with(this).load(model.default_ad_image).centerCrop()
                        .placeholder(R.drawable.dr_hussain).into(binding.imageView)
                }
                break
            }
        }

        if (model != null) {

            if (!model.webPageLink.isNullOrBlank()) {
                if(model.is_external!!){
                    binding.cardviewAds.setOnClickListener {
                        openWebPage(model.webPageLink)
                    }
                }else{
                    binding.cardviewAds.setOnClickListener {
                        meuNav(model.webPageLink!!.toInt(),this@MainActivity)
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
                    Glide.with(this).load(model.resourceLink).centerCrop()
                        .placeholder(R.drawable.dr_hussain).into(binding.imageView)
                }
                "GIF" -> {
                    binding.imageView.visibility = View.VISIBLE
                    Glide.with(this).asGif().load(model.resourceLink).centerCrop()
                        .placeholder(R.drawable.dr_hussain).into(binding.imageView)
                }
                "Paragraph" -> {
                    binding.textView.visibility = View.VISIBLE
                    binding.textView.loadDataWithBaseURL(
                        null,
                        model.paragraph!!,
                        "text/html",
                        "utf-8",
                        null
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
                    binding.bannerSlider.setOnSlideClickListener {
                        binding.bannerSlider.setOnSlideClickListener(OnSlideClickListener {
                            if (!model.webPageLink.isNullOrBlank()) {
                                when {
                                    model.is_external!! -> {
                                        openWebPage(model.webPageLink)
                                    }
                                    else -> {
                                        meuNav(model.webPageLink!!.toInt(),this@MainActivity)
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
                    intent = Intent(this, MoreDetailsAdsActivity::class.java)
                    intent.putExtra("pageCode", model.pageCode)
                    startActivity(intent)
                }
            }
        }
    }

}