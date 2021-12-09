package com.devartlab.ui.main.ui.market

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.FragmentMarketRequestTypesBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.AdModel
import com.devartlab.model.Summary
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.market.requests.MarketRequestsFragment
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.devartlab.utils.ProgressLoading
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketRequestTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarketRequestTypesActivity : BaseActivity<FragmentMarketRequestTypesBinding>(), ChooseEmployeeInterFace, MarketRequestTypesAdapter.OnRequestTypeClick {

    lateinit var binding: FragmentMarketRequestTypesBinding
    lateinit var viewModel: MarketRequestViewModel
    lateinit var adapter: MarketRequestTypesAdapter
    lateinit var chooseEmployee: ChooseEmployee
    lateinit var mediaSource: SimpleMediaSource


    var mainAcc = true
    var accId = 0
    var accAddId = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_market_request_types
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(MarketRequestViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Market Request"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        accId = viewModel.dataManager.user.accId
        if (!viewModel.dataManager.user.additionalAccIds.isNullOrEmpty())
            accAddId = viewModel.dataManager.user.additionalAccIds

        adapter = MarketRequestTypesAdapter(this, ArrayList(), this)


        binding.empImage?.setImageResource(R.drawable.user_logo)
        binding.drName.setText(viewModel.dataManager.user?.nameAr)
        binding.drTitle.setText(viewModel.dataManager.user?.title)
        if (!viewModel.dataManager.user?.image.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(viewModel.dataManager.user?.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }
        binding.empImage.setOnClickListener(View.OnClickListener {

            chooseEmployee = ChooseEmployee(this@MarketRequestTypesActivity, this@MarketRequestTypesActivity, viewModel.dataManager)
            chooseEmployee.setCanceledOnTouchOutside(true)
            val window = chooseEmployee.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee.show()
        })

        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.MARKET_REQUEST) {
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



        viewModel.getData(0, accId, accAddId)
        setObservers()
        setRecyclerViews()
    }


    private fun setRecyclerViews() {

        binding.recyclerView?.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView?.adapter = adapter

    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, androidx.lifecycle.Observer {


            if (it.isSuccesed) {


                if (it.data.summary.isNullOrEmpty()) {
                    binding.emptyList.visibility = View.VISIBLE

                } else {
                    binding.emptyList.visibility = View.GONE

                    adapter.setMyData(it.data.summary)
                }

            } else {
                binding.emptyList.visibility = View.VISIBLE
                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })

        viewModel.responseLiveUpdate.observe(this, androidx.lifecycle.Observer {


            if (it.isSuccesed) {

                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })

        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })

    }

    override fun onRequestClick(model: Summary) {

        var fragment = MarketRequestsFragment()
        val bundle = Bundle()
        bundle.putInt("TYPE_INT", model.markReqTypeId!!)
        bundle.putString("TOTAL_COST", model.totalCoast.toString())
        bundle.putString("TOTAL_GAIN", model.totalGain.toString())
        bundle.putString("REQUEST_TPE", model.activity.toString())


        bundle.putInt("ACC_ID", accId)
        bundle.putString("ACC_ADD_ID", accAddId)
        bundle.putBoolean("MAIN_ACC", mainAcc)


        fragment.setArguments(bundle)

        supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.Container,
                        fragment!!
                )
                .addToBackStack("")
                .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun chooseEmployee(model: FilterDataEntity?) {

        binding.empImage?.setImageResource(R.drawable.user_logo)
        chooseEmployee.dismiss()
        binding.drName.setText(model?.empName)
        binding.drTitle.setText(model?.empTitle)
        binding.empImage?.setImageResource(R.drawable.user_logo)
        if (model?.fileImage != null) {
            Glide.with(this)
                    .load(ApiServices.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        } else {
            Glide.with(this)
                    .load(ApiServices.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }

        mainAcc = false
        accId = model?.empAccountId!!
        accAddId = ""

        viewModel.getData(0, accId, accAddId)


    }

}