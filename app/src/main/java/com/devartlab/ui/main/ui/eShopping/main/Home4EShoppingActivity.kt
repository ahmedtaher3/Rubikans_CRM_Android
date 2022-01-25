package com.devartlab.ui.main.ui.a4eshopping.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.a4eshopping.addProductsToThePharmacy.AddProductsPharmacyActivity
import com.devartlab.a4eshopping.main.model.CardModel
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.Activity4eshoppingBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.eShopping.PharmacyBinding.PharmacyBindingActivity
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingViewModel
import com.devartlab.ui.main.ui.eShopping.pharmacySales.PharmacySalesActivity
import com.devartlab.ui.main.ui.eShopping.ticket.TicketActivity
import com.devartlab.ui.main.ui.eshopping.main.MenuListAdapter
import com.devartlab.ui.main.ui.eshopping.orientationVideos.OrientationVideosActivity
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider

class Home4EShoppingActivity : AppCompatActivity() ,
    MenuListAdapter.OnHomeItemClick{

    lateinit var binding: Activity4eshoppingBinding
    lateinit var adapter: MenuListAdapter
    lateinit var dataManager: DataManager
    lateinit var mediaSource: SimpleMediaSource
    lateinit var viewModel: Home4EShoppingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_4eshopping)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.eshopping)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(Home4EShoppingViewModel::class.java)
        dataManager = (getApplication() as BaseApplication).dataManager!!
        setUpRecycler()
        ads()
    }

    private fun setUpRecycler() {
        val list = ArrayList<CardModel>()
        list.add(CardModel(1, resources.getString(R.string.orientation_videos), "",R.drawable.ic_video))
        list.add(CardModel(2, resources.getString(R.string.Pharmacy_binding), "", R.drawable.ic_pharmacy_binding))
        list.add(CardModel(3, resources.getString(R.string.Pharmacy_sales), "", R.drawable.pharmacy_sales))
        list.add(CardModel(4, resources.getString(R.string.Add_products_to_Pharmacy), "", R.drawable.ic_add_pharmacy))
        list.add(CardModel(5, resources.getString(R.string.Add_ticket), "", R.drawable.ic_chat))

        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter

    }

    override fun setOnHomeItemClick(model: CardModel) {
        when (model.id) {
            1 -> {
                val intent = Intent(this, OrientationVideosActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(this, PharmacyBindingActivity::class.java)
                startActivity(intent)
            }
            3 -> {
                    val intent = Intent(this, PharmacySalesActivity::class.java)
                    startActivity(intent)
            }
            4 -> {
                val intent = Intent(this, AddProductsPharmacyActivity::class.java)
                startActivity(intent)
            }
            5 -> {
                val intent = Intent(this, TicketActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun ads() {
        var model: AdModel? = null
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.HOME_4ESHOPPING) {
                model = m
                binding.constrAds.setVisibility(View.VISIBLE)
                if (model.resourceLink.equals(null) && model.paragraph.equals(null) && model.slideImages == null) {
                    binding.constrAds.setVisibility(View.VISIBLE)
                    binding.imageView.visibility = View.VISIBLE
                    Glide.with(this).load(model.default_ad_image).centerCrop()
                        .into(binding.imageView)
                }
                break
            }
        }

        if (model != null) {

            if (!model.webPageLink.isNullOrBlank()) {
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
                    Glide.with(this).load(model.resourceLink).centerCrop()
                        .placeholder(R.drawable.dr_hussain).into(binding.imageView)
                }
                "GIF" -> {
                    binding.imageView.visibility = View.VISIBLE
                    Glide.with(this).asGif().load(model.resourceLink).centerCrop()
                        .placeholder(R.drawable.dr_hussain).into(binding.imageView);
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
    fun openWebPage(url: String?) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
    override fun onStop() {
        super.onStop()
        binding.videoView.stop()
    }
}