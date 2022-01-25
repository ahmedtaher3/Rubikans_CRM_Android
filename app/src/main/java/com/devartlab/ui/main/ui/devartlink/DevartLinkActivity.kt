package com.devartlab.ui.main.ui.devartlink

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.ActivityDevartLinkBinding
import com.devartlab.model.AdModel
import com.devartlab.model.CardModel
import com.devartlab.ui.main.ui.callmanagement.home.MenuListAdapter
import com.devartlab.ui.main.ui.devartlink.letsTalk.LetsTalkActivity
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.user.UserResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.ui.main.ui.profile.ProfileActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.google.android.exoplayer2.source.MediaSource
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider

private const val TAG = "DevartLinkActivity"

class DevartLinkActivity : BaseActivity<ActivityDevartLinkBinding>(),
    MenuListAdapter.OnHomeItemClick {
    lateinit var binding: ActivityDevartLinkBinding
    lateinit var adapter: MenuListAdapter
    lateinit var viewModel: DevartLinkViewModel
    lateinit var mediaSource: SimpleMediaSource
    var dataManager: DataManager? = null
    var name: String? = null
    var pass: String? = null
    var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Devart Link"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(DevartLinkViewModel::class.java)

        dataManager = (application as BaseApplication).dataManager
        name = dataManager!!.user.userName
        pass = dataManager!!.user.password
        token = dataManager!!.token
        viewModel.getUserModel(name, pass, token)


        val list = ArrayList<CardModel>()

        list.add(CardModel(1, resources.getString(R.string.lets_talk), R.drawable.ic_talk_icon_02))


        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.recycler?.layoutManager = layoutManager
        binding.recycler.adapter = adapter
        ads()//fun to show ads
        handleObserver()//observer
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_devart_link
    }

    override fun setOnHomeItemClick(model: CardModel) {

        when (model.id) {
            1 -> {
                startActivity(Intent(this, LetsTalkActivity::class.java))
            }
        }
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

    override fun onStop() {
        super.onStop()
        binding.videoView.stop()
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
        viewModel.userResponse.observe(this, Observer {
            UserPreferenceHelper.saveUserProfileChat(it!!)
            binding.recycler.setVisibility(View.VISIBLE)
        })
    }
    //
//    override fun onResume() {
//        super.onResume()
//        binding.videoView.play(mediaSource);
//    }
    fun ads() {
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.DEVART_LINK) {
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
                    .centerCrop().into(binding.imageView)
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