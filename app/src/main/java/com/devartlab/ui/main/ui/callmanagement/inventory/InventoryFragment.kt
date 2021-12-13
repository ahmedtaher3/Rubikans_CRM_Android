package com.devartlab.ui.main.ui.callmanagement.inventory

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.InventoryTrxType
import com.devartlab.databinding.FragmentTradeReportsBinding
import com.devartlab.model.AdModel
import com.devartlab.model.CardModel
import com.devartlab.ui.main.ui.callmanagement.inventory.balance.InventoryBalanceActivity
import com.devartlab.ui.main.ui.callmanagement.inventory.details.MovmentsDetailsActivity
import com.devartlab.ui.main.ui.callmanagement.inventory.loadcar.InventoryLoadCarActivity
import com.devartlab.ui.main.ui.callmanagement.inventory.status.InventoryRequestsStatusActivity
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.ui.trade.*
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoView
import ss.com.bannerslider.Slider


class InventoryFragment : BaseFragment<FragmentTradeReportsBinding>(),
    InventoryTypesAdapter.ChooseInvoiceType, InventoryAdapter.OnHomeItemClick {
    lateinit var viewModel: InventoryViewModel
    lateinit var binding: FragmentTradeReportsBinding
    lateinit var adapter: InventoryAdapter

    lateinit var mediaSource: SimpleMediaSource
    var alertDialog: android.app.AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(baseActivity).get(InventoryViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding

        val list = ArrayList<CardModel>()
        list.add(CardModel(1, "حركات المخازن", R.drawable.report_general))
        list.add(CardModel(2, "تفاصيل الحركات", R.drawable.report_general))
        list.add(CardModel(3, "طلبات تحميل السيارة", R.drawable.report_general))
        list.add(CardModel(4, "حالة الحركات", R.drawable.report_general))
        list.add(CardModel(5, "المخزون", R.drawable.report_general))

        adapter = InventoryAdapter(baseActivity, list, this)
        val layoutManager = GridLayoutManager(baseActivity, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.recycler?.layoutManager = layoutManager
        binding.recycler.adapter = adapter

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_trade_reports
    }

    override fun setOnHomeItemClick(model: CardModel) {

        when (model.id) {
            1 -> {
                chooseType()
            }
            2 -> {
                baseActivity.startActivity(
                    Intent(
                        baseActivity,
                        MovmentsDetailsActivity::class.java
                    )
                )
            }
            3 -> {
                baseActivity.startActivity(Intent(baseActivity, InventoryLoadCarActivity::class.java))
            }
            4 -> {
                baseActivity.startActivity(
                    Intent(
                        baseActivity,
                        InventoryRequestsStatusActivity::class.java
                    )
                )
            }
            5 -> {
                baseActivity.startActivity(
                    Intent(
                        baseActivity,
                        InventoryBalanceActivity::class.java
                    )
                )
            }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title =
                getString(R.string.inventory)
        } catch (e: Exception) {

        }

        try {
            CommonUtilities.sendMessage(baseActivity, true)
        } catch (e: Exception) {
        }
    }

    private fun chooseType() {


        val factory = LayoutInflater.from(baseActivity)
        val choose_activity_type: View = factory.inflate(R.layout.choose_activity_type, null)
        var videoView: ExoVideoView = choose_activity_type.findViewById(R.id.videoView)
        var imageView:ImageView = choose_activity_type.findViewById(R.id.imageView)
        var bannerslider: Slider = choose_activity_type.findViewById(R.id.bannerSlider)
        var textView: WebView = choose_activity_type.findViewById(R.id.textView)
        var cardviewAds: CardView = choose_activity_type.findViewById(R.id.cardview_ads)
        var btnHideShowAds: ImageView= choose_activity_type.findViewById(R.id.btn_hide_show_ads)
        var constrAds: ConstraintLayout = choose_activity_type.findViewById(R.id.constr_ads)
        var moreThanAds:TextView=choose_activity_type.findViewById(R.id.tv_more_than_ads)

        if (alertDialog != null && alertDialog?.isShowing!!) {
            return
        }

        alertDialog = android.app.AlertDialog.Builder(baseActivity).create()
        alertDialog?.setCancelable(true)
        alertDialog?.setView(choose_activity_type)
        alertDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()

        var title: TextView = choose_activity_type.findViewById(R.id.title)
        title.text = getString(R.string.choose_move_type)
        var activitiesRecyclerView: RecyclerView =
            choose_activity_type.findViewById(R.id.activitiesRecyclerView)
        var close: ImageView = choose_activity_type.findViewById(R.id.close)

        var adapter =
            InventoryTypesAdapter(
                baseActivity,
                this
            )
        activitiesRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        activitiesRecyclerView.adapter = adapter

        close.setOnClickListener(View.OnClickListener { alertDialog?.dismiss() })


        var requestObject =
            ReportsFilterModel(

                _Option = 7,
                LoginUserAccountId = viewModel.dataManager.user.accId,
                EmployeeIdStr = viewModel.dataManager.user.empId.toString(),
                AccountIdStr = viewModel.dataManager.user.accId.toString(),
                PageSize = 100,
                PageNumber = 1,
                AllowToBrowesAllRecord = false

            )
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject

        viewModel.getInventoryTypes(appraisalBuildsSchema)
        viewModel.typsLive?.observe(baseActivity, Observer {

            adapter.setMyData(it.data.inventoryTrxType)

        })


        var model = AdModel()

        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.CREATE_PLAN) {
                model = m
                constrAds.setVisibility(View.VISIBLE)
                if (model.resourceLink.equals(null)
                    && model.paragraph.equals(null)
                    && model.slideImages == null) {
                    constrAds.setVisibility(View.VISIBLE)
                    imageView.visibility = View.VISIBLE
                    Glide.with(this).load(model.default_ad_image)
                        .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
                }
                break
            }
        }

        if (!model.webPageLink.equals(null)) {
            cardviewAds.setOnClickListener {
                openWebPage(model.webPageLink)
            }
        }
        when (model.type) {
            "Video" -> {
                videoView.visibility = View.VISIBLE
                mediaSource = SimpleMediaSource(model.resourceLink)
                videoView.play(mediaSource);
            }
            "Image" -> {

                imageView.visibility = View.VISIBLE
                Glide.with(this).load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
            }
            "GIF" -> {
                imageView.visibility = View.VISIBLE
                Glide.with(this).asGif().load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView);
            }
            "Paragraph" -> {
                textView.visibility = View.VISIBLE
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    textView.setText(
//                        Html.fromHtml(
//                            model.paragraph,
//                            Html.FROM_HTML_MODE_LEGACY
//                        )
//                    );
//                } else
//                    textView.setText(Html.fromHtml(model.paragraph))
                textView.loadDataWithBaseURL(null, model.paragraph!!
                    ,  "text/html", "utf-8", null)
            }
            "Slider" -> {
                bannerslider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(context))
                bannerslider?.setInterval(5000)

                val list = ArrayList<String>()
                for (i in model.slideImages!!) {
                    list.add(i?.link!!)
                }
                bannerslider?.setAdapter(MainSliderAdapter(list))
            }
        }
        if (model.show_ad == true) {
            btnHideShowAds.setVisibility(View.VISIBLE)
            btnHideShowAds.setOnClickListener {
                if (constrAds.visibility == View.VISIBLE) {
                    constrAds.setVisibility(View.GONE)
                    btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
                } else {
                    constrAds.setVisibility(View.VISIBLE)
                    btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
                }
            }
        }
        if (model.show_more == true) {
            moreThanAds.setVisibility(View.VISIBLE)
            moreThanAds.setOnClickListener {
                val  intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }
    }


    override fun setChooseInvoiceType(model: InventoryTrxType?) {

        if (alertDialog != null && alertDialog?.isShowing!!) {
            alertDialog?.dismiss()
        }


        System.out.println(model?.trxTypeID)


        val intent = Intent(baseActivity, InventoryDetails::class.java)
        intent.putExtra("TRX_TYPE_NAME", model?.trxTypeDescription)
        intent.putExtra("TRX_TYPE_ID", model?.trxTypeID.toString())
        startActivity(intent)


    }

}