package com.devartlab.ui.main.ui.employeeservices.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
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
import com.devartlab.databinding.FragmentSelfServiceHomeBinding
import com.devartlab.model.CardModel
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.AdModel
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.dialogs.placeholder
import com.devartlab.ui.main.ui.callmanagement.syncdata.SyncDataDialog
import com.devartlab.ui.main.ui.employeeservices.EmployeeServicesActivity
import com.devartlab.ui.main.ui.employeeservices.SubmitFingerprintActivity
import com.devartlab.ui.main.ui.employeeservices.approval.AllRequestsFragment
import com.devartlab.ui.main.ui.employeeservices.hrrequest.EmployeeRequestsFragment
import com.devartlab.ui.main.ui.employeeservices.meals.MealsFragment
import com.devartlab.ui.main.ui.employeeservices.penalties.google.GetAllFragment
import com.devartlab.ui.main.ui.employeeservices.penalties.google.GetMyPenaltiesFragment
import com.devartlab.ui.main.ui.employeeservices.leavework.LeaveWorkActivity
import com.devartlab.ui.main.ui.employeeservices.businessCard.BusinessCardActivity
import com.devartlab.ui.main.ui.employeeservices.workfromhome.WorkFromHomeFragment
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_devart_link.*
import ss.com.bannerslider.Slider
import kotlin.math.log
import com.google.android.exoplayer2.ui.PlaybackControlView


private const val TAG = "SelfServiceHomeFragment"

class SelfServiceHomeFragment : BaseFragment<FragmentSelfServiceHomeBinding>(),
    ChooseEmployeeInterFace,
    EmployeeServicesAdapter.OnHomeItemClick {
    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentSelfServiceHomeBinding
    lateinit var dialog: SyncDataDialog
    lateinit var authorityDao: AuthorityDao
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var mediaSource: SimpleMediaSource

    lateinit var adapter: EmployeeServicesAdapter


    var isBack: Boolean = true
    var notifCount: Button? = null
    var mNotifCount = 0


    var plan: Boolean = false
    var report: Boolean = false
    var list: Boolean = false

    var salary: Boolean = false
    var attendance: Boolean = false
    var officeboy: Boolean = false
    var expenses: Boolean = false
    var MealsApprovePermission: Boolean = true
    var AttendancePermission: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        authorityDao = DatabaseClient.getInstance(baseActivity)?.appDatabase?.authorityDao()!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        setHasOptionsMenu(true);

        Completable.fromAction(object : Action {
            @Throws(java.lang.Exception::class)
            override fun run() {
                run {

                    if (!viewModel.authorityDao.getById("1138").allowBrowseRecord) {

                        MealsApprovePermission = false
                    }

                    if (!viewModel.authorityDao.getById("1084").allowBrowseRecord) {

                        AttendancePermission = false
                    }
                }
            }
        })
            .subscribeOn(Schedulers.io())
            .subscribe()


        val list = ArrayList<CardModel>()
        list.add(CardModel(1, "Expenses", R.drawable.home_expenses))
        list.add(CardModel(2, "Salary", R.drawable.home_salary))
        list.add(CardModel(3, "Attendance", R.drawable.home_attendance))
        list.add(CardModel(4, "HR Requests", R.drawable.request_home))
        list.add(CardModel(5, "وجبات", R.drawable.meal_home))
        list.add(CardModel(6, "My Penalties", R.drawable.penalty))
        list.add(CardModel(7, "Add New Penalty", R.drawable.penalty))

        list.add(CardModel(8, "Work From Home", R.drawable.work_from_home))
        list.add(CardModel(9, "Approved Report", R.drawable.approved))
        list.add(CardModel(10, "Confirm Fingerprint", R.drawable.home_attendance))

        list.add(CardModel(11, "Request Business Card", R.drawable.creditcard))
        list.add(CardModel(12, "Leave Work", R.drawable.exit))


        adapter = EmployeeServicesAdapter(baseActivity, list, this)
        val layoutManager = GridLayoutManager(baseActivity, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.recycler?.layoutManager = layoutManager
        binding.recycler.adapter = adapter



        viewModel!!.getRequests(
            "getAllPending",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            viewModel!!.dataManager!!.user.empId.toString(),
            "",
            "",
            "",
            ""
        )
        ads()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_self_service_home
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title =
                getString(R.string.menu_home)
        } catch (e: Exception) {

        }

        try {
            CommonUtilities.sendMessage(baseActivity, true)
        } catch (e: Exception) {
        }
    }


    fun replace_fragment(fragment: Fragment?, tag: String?) {


        baseActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }

    fun replace_fragment_model(fragment: Fragment?, tag: String?, model: FilterDataEntity?) {

        val bundle = Bundle()
        bundle.putParcelable("EMP_MODEL", model)
        fragment?.arguments = bundle
        baseActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
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
    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee.dismiss()
        Log.d(TAG, "chooseEmployee: " + model.toString())
        replace_fragment_model(AllRequestsFragment(), "AllRequestsFragment", model)
    }

    override fun setOnHomeItemClick(model: CardModel) {
        when (model.id) {


            1 -> {

                val intent = Intent(baseActivity, EmployeeServicesActivity::class.java)
                intent.putExtra("PAGE_NUMBER", "1")
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(baseActivity, EmployeeServicesActivity::class.java)
                intent.putExtra("PAGE_NUMBER", "4")
                startActivity(intent)
            }
            3 -> {

                if (AttendancePermission) {
                    val intent = Intent(baseActivity, EmployeeServicesActivity::class.java)
                    intent.putExtra("PAGE_NUMBER", "5")
                    startActivity(intent)
                } else
                    Toast.makeText(baseActivity, "You haven't permission", Toast.LENGTH_SHORT)
                        .show()


            }
            4 -> {

                replace_fragment(EmployeeRequestsFragment(), "EmployeeRequestsFragment")

            }
            5 -> {
                replace_fragment(MealsFragment(), "MealsFragment")
            }
            6 -> {
                replace_fragment(GetMyPenaltiesFragment(), "GetMyPenaltiesFragment")
            }
            7 -> {
                replace_fragment(GetAllFragment(), "GetAllFragment")
            }
            8 -> {
                replace_fragment(WorkFromHomeFragment(), "WorkFromHomeFragment")

            }
            9 -> {


                chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
                chooseEmployee.setCanceledOnTouchOutside(true)
                val window = chooseEmployee.window
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
                chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                chooseEmployee.show()


            }
            10 -> {

                val intent = Intent(baseActivity, SubmitFingerprintActivity::class.java)
                startActivity(intent)
            }
            11 -> {

                startActivity(Intent(baseActivity, BusinessCardActivity::class.java))

            }
            12 -> {
                startActivity(Intent(baseActivity, LeaveWorkActivity::class.java))

            }


        }

    }

    override fun onStop() {
        super.onStop()
        binding.videoView.stop()
    }

    fun ads() {
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.SELF_SERVICES_PAGE) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            && model.paragraph.equals(null)
            && model.slideImages == null
        ) {
            binding.constrAds.setVisibility(View.GONE)
        } else if (model.resourceLink.equals(null) && model.paragraph.equals(null)
            && model.slideImages == null
        ) {
            binding.imageView.visibility = View.VISIBLE
            Glide.with(this).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
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
                val intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }
    }
}