package com.devartlab.ui.main.ui.moreDetailsAds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.databinding.ActivityMoreDetailsAdsBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.moreDetailsAds.model.SeeMoreRequest
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider

private const val TAG = "MoreDetailsAdsActivity"
class MoreDetailsAdsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreDetailsAdsBinding
    lateinit var viewModel: MoreDetailsAdsViewModel
    lateinit var mediaSource: SimpleMediaSource
    lateinit var request: SeeMoreRequest
    var model = AdModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_more_details_ads)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Read more"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this)[MoreDetailsAdsViewModel::class.java]
        val bundle: Bundle? = intent.extras
        val page_code = bundle?.get("pageCode")
        Log.e("page_code", page_code.toString())
        Log.d(TAG, "onCreate: " + Gson().toJson(viewModel.dataManager.ads.ads!! as Any?))
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode == page_code) {
                model = m
                break
            }
        }
        when (model.view_more_type) {
            "Video" -> {
                binding.videoView.visibility = View.VISIBLE
                mediaSource = SimpleMediaSource(model.view_more_image!!.get(0)!!.link)
                binding.videoView.play(mediaSource);
            }
            "Image" -> {

                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).load(model.view_more_image!!.get(0)!!.link)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
            }
            "GIF" -> {
                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).asGif().load(model.view_more_image!!.get(0)!!.link)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView);


            }
            "Paragraph" -> {
                binding.textView.visibility = View.VISIBLE
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    binding.textView.setText(
//                        Html.fromHtml(
//                            model.view_more_paragraph,
//                            Html.FROM_HTML_MODE_LEGACY
//                        )
//                    )
//                } else
//                    binding.textView.setText(Html.fromHtml(model.view_more_paragraph))
                binding.textView.loadDataWithBaseURL(null, model.view_more_paragraph!!
                    ,  "text/html", "utf-8", null)
            }
            "Slider" -> {
                binding.bannerSlider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(this))
                binding.bannerSlider?.setInterval(5000)

                val list = ArrayList<String>()
                for (i in model.view_more_image!!) {
                    list.add(i?.link!!)
                }
                binding.bannerSlider?.setAdapter(MainSliderAdapter(list))
            }
        }
        val str =model.createdAt!!.split("T".toRegex())[0]
        binding.tvDataCreate.setText(str)

        binding.tvTitle.setText(model.view_more_title)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            binding.tvDec.setText(Html.fromHtml(model.view_more_text, Html.FROM_HTML_MODE_COMPACT))
//        } else
//            binding.tvDec.setText(Html.fromHtml(model.view_more_text))
//        binding.tvDec.getSettings().setJavaScriptEnabled(true);
//        binding.tvDec.loadUrl(model.view_more_text!!)
        binding.tvDec.loadDataWithBaseURL(null, model.view_more_text!!
            ,  "text/html", "utf-8", null)

        handleObserver()
    }

    private fun handleObserver() {
        request = SeeMoreRequest(model.id!!)
        viewModel.getSeeMore(request)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}