package com.devartlab.ui.main.ui.employeeservices.workfromhome

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentWorkFromHomeBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider
import java.text.SimpleDateFormat
import java.util.*


class WorkFromHomeFragment : BaseFragment<FragmentWorkFromHomeBinding>() {

    lateinit var binding: FragmentWorkFromHomeBinding
    lateinit var viewModel: WorkFromHomeViewModel

    var started: Boolean = false
    var ended: Boolean = false
    var code: String = ""
    private var DATE: String? = null
    var fmt: SimpleDateFormat? = null
    lateinit var mediaSource: SimpleMediaSource

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_from_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WorkFromHomeViewModel::class.java)
        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding

        setObservers()

        binding.date.setText(DATE)
        binding.startWorkFromHome.setOnClickListener {

            if (!started)
                viewModel.startWorkFromHome("insert", viewModel.dataManager.user.empId.toString(), "", viewModel.dataManager.user.nameAr.toString(), viewModel.dataManager.user.managerId.toString(), "", "", "", "")
        }

        binding.startWorkFromHomeEnded.setOnClickListener {

            if (!ended)
                viewModel.endWorkToday(code)
        }


        binding.getAll.setOnClickListener {

            replace_fragment(ShowAllWorkFromHomeFragment(), "ShowAllWorkFromHomeFragment")

        }

        viewModel.getAll()
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.WORK_FROM_HOME) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)
            && model.slideImages==null) {
            binding.constrAds.setVisibility(View.GONE)
        } else if (model.resourceLink.equals(null)
            &&model.paragraph.equals(null)
            && model.slideImages==null) {
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
                binding.textView.loadDataWithBaseURL(null, model.paragraph!!
                    ,  "text/html", "utf-8", null)
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
                val  intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }
    }

    private fun setObservers() {

        viewModel.responseLiveCheckWorkFromHome.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it.isSuccessful) {
                binding.startWorkFromHomeEnded.visibility = View.VISIBLE
                started = true
                code = it.workFromHome?.code!!
                binding.startWorkFromHome.setText("Started At \n " + it.workFromHome?.startedAt?.takeLast(8))


                if (it.workFromHome?.ended!!) {
                    ended = true
                    binding.startWorkFromHomeEnded.setText("Ended At \n " + it.workFromHome?.endedAt?.takeLast(8))


                } else {
                    ended = false

                }

            } else {
                binding.startWorkFromHomeEnded.visibility = View.GONE

                started = false

            }
        })


        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })


    }

    private fun replace_fragment(fragment: Fragment, s: String) {

        baseActivity.supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
        )
                .add(
                        R.id.Container,
                        fragment
                )
                .addToBackStack("s")
                .commit()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Work From Home"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

}