package com.devartlab.ui.main.ui.callmanagement.list

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.TypesFragmentBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.callmanagement.list.customertype.ChooseCustomerTypeDialog
import com.devartlab.ui.main.ui.callmanagement.list.customertype.ChooseCustomerTypeInterFace
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ss.com.bannerslider.Slider
import java.util.concurrent.TimeUnit

class TypesFragment : BaseFragment<TypesFragmentBinding>(), ChooseCustomerTypeInterFace {

    private lateinit var binding: TypesFragmentBinding
    private lateinit var viewModel: TypesViewModel
    private lateinit var adapter: TypesAdapter
    lateinit var mediaSource: SimpleMediaSource
    lateinit var chooseCustomerTypeDialog: ChooseCustomerTypeDialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(TypesViewModel::class.java)
        adapter =
            TypesAdapter(baseActivity, baseActivity.getSupportFragmentManager().beginTransaction())

        binding.typesRecyclerView.layoutManager = GridLayoutManager(baseActivity, 2)
        binding.typesRecyclerView.adapter = adapter

        binding.addToList.setOnClickListener(View.OnClickListener {

            chooseType()

        })


        viewModel.getAllTypes()
        setObservers()
        ads()
    }

    private fun chooseType() {


        chooseCustomerTypeDialog =
            ChooseCustomerTypeDialog(baseActivity, this@TypesFragment, viewModel?.dataManager!!);
        chooseCustomerTypeDialog.setCanceledOnTouchOutside(false);
        val window = chooseCustomerTypeDialog.getWindow();
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        );
        chooseCustomerTypeDialog.getWindow()
            ?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseCustomerTypeDialog.getWindow()
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseCustomerTypeDialog.show();

    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, Observer {

            if (it.isNullOrEmpty()) {
                binding.emptyList.visibility = View.VISIBLE
            } else {
                binding.emptyList.visibility = View.GONE
            }

            adapter.setMyData(it)

        })


        viewModel.progress.observe(this, Observer { progress ->

            when (progress) {
                0 -> {

                    Single.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {
                                ProgressLoading.dismiss()
                                viewModel.getAllTypes()
                            }
                        })


                }
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })


    }

    override fun getLayoutId(): Int {
        return R.layout.types_fragment
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "List"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.list_type_menu, menu)
        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {

            R.id.syncList -> {
                viewModel.syncList()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun getCustomerType() {

    }

    fun ads() {
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.MEDICAL_REP) {
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

        if (!model.webPageLink.equals(null)) {
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
