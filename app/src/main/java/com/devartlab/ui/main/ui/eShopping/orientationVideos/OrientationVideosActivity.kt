package com.devartlab.ui.main.ui.eShopping.orientationVideos

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.a4eshopping.orientationVideos.model.Item
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.ActivityOrientationVideosBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider


class OrientationVideosActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrientationVideosBinding
    var viewModel: VideosViewModel? = null
    private var adapter: VideoListAdapter? = null
    val list = ArrayList<Item>()
    lateinit var dataManager: DataManager
    lateinit var mediaSource: SimpleMediaSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_orientation_videos)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.orientation_videos)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = VideoListAdapter(null)
        viewModel = ViewModelProvider(this).get(VideosViewModel::class.java)
        dataManager = (getApplication() as BaseApplication).dataManager!!
        onClickListener()
        handleObserver()
        ads()
        binding.searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private fun onClickListener() {
        viewModel!!.getVideos()
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }

    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.responseVideos.observe(this, Observer {
            if (it!!.items == null) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = VideoListAdapter(it.items)
                list.addAll(it.items)
                binding.recyclerListVideos.setAdapter(adapter)
                adapter!!.setOnItemClickListener(VideoListAdapter.OnItemClickListener { pos, dataItem ->

                    val intent = Intent(this, videoActivity::class.java)
                    intent.putExtra("_id", dataItem.snippet.resourceId.videoId)
                    intent.putExtra("_name", dataItem.snippet.title)
                    intent.putExtra("_dec", dataItem.snippet.description)
                    intent.putExtra("_name_channel", dataItem.snippet.channelTitle)
                    startActivity(intent)
                })
            }
        })
    }


    private fun filter(text: String) {
        val filteredList: ArrayList<Item> = ArrayList()

        for (item in list) {
            if (item.snippet.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
                Log.e("xxx",item.toString())
            }
        }
        adapter!!.filterData(filteredList)
    }


    private fun refresh() {
        synchronized(this) {
            viewModel!!.getVideos()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun ads() {
        var model: AdModel? = null
        for (m in viewModel!!.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.FAQ_TUTORIAL) {
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