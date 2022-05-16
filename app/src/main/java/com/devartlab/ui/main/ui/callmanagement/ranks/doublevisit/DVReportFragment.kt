package com.devartlab.ui.main.ui.callmanagement.ranks.doublevisit

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.databinding.DvReportFragmentBinding
import com.devartlab.model.Cycle
import com.devartlab.model.DoubleVisitReport
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.AdModel
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployee
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.callmanagement.ranks.RanksViewModel
import com.devartlab.ui.main.ui.cycles.ChangeCycleAll
import com.devartlab.ui.main.ui.cycles.ChangeCycleInterface
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider
import ss.com.bannerslider.event.OnSlideClickListener


class DVReportFragment : BaseFragment<DvReportFragmentBinding>(), DVReportAdapter.OnItemSelect, ChooseEmployeeInterFace, ChangeCycleInterface {
    private lateinit var viewModel: RanksViewModel
    private lateinit var binding: DvReportFragmentBinding
    private lateinit var adapter: DVReportAdapter
    lateinit var mediaSource: SimpleMediaSource

    var chooseEmployee: ChooseEmployee? = null
    var changeCycle: ChangeCycleAll? = null
    var fromDate = ""
    var toDate = ""
    var cycleId = 0
    var empId = "0"
    var accId = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RanksViewModel::class.java)
        adapter = DVReportAdapter(baseActivity, ArrayList(), this)


    }

    override fun getLayoutId(): Int {
        return R.layout.dv_report_fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!

        fromDate = viewModel.dataManager.newOldCycle.currentCycleFromDate?.take(10)!!
        toDate = viewModel.dataManager.newOldCycle.currentCycleToDate?.take(10)!!
        cycleId = viewModel.dataManager.newOldCycle.currentCycleId
        empId = viewModel.dataManager.user.empId.toString()
        accId = viewModel.dataManager.user.accId.toString()

        setEmpData()
        viewModel.getDVReport(  fromDate, toDate,  empId)
        binding.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                adapter.filter(charSequence.toString())

            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.empImage.setOnClickListener {

            chooseEmployee = ChooseEmployee(baseActivity, this, viewModel.dataManager)
            chooseEmployee?.setCanceledOnTouchOutside(true)
            val window = chooseEmployee?.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            chooseEmployee?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            chooseEmployee?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            chooseEmployee?.show()

        }

        binding.cycles.setOnClickListener {

            changeCycle = ChangeCycleAll(baseActivity, this, viewModel.dataManager, accId.toInt())
            changeCycle?.setCanceledOnTouchOutside(true)
            val window = changeCycle?.window
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            changeCycle?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            changeCycle?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            changeCycle?.show()

        }



        binding.recyclerView.adapter = adapter


        setObservers()
        ads()

    }

    private fun setObservers() {
        viewModel.responseLive.observe(viewLifecycleOwner, Observer {


            adapter.setMyData(it.data.doubleVisitReport)

        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {


            when (it) {
                1 -> {
                    context?.let { it1 -> ProgressLoading.show(it1) }
                }
                0 -> {
                    ProgressLoading.dismiss()
                }
            }

        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Double Visit Report"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }



    override fun setOnItemSelect(model: DoubleVisitReport) {
        val intent = Intent(baseActivity , DVDetailsActivity::class.java)
        intent.putExtra("DoubleVisitReport" ,model)
        intent.putExtra("FromDate" ,fromDate)
        intent.putExtra("ToDate" ,toDate)
        startActivity(intent)
     }

    override fun chooseEmployee(model: FilterDataEntity?) {
        chooseEmployee?.dismiss()
        binding.empImage.setImageResource(R.drawable.user_logo);
        if (model?.fileImage != null)
        {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/" + model.fileImage)
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }
        else

        {
            Glide.with(baseActivity)
                    .load(com.devartlab.AppConstants.ImageBaseURL + "ImageUpload/Employee/DefaultEmpImage.jpg")
                    .placeholder(binding.empImage?.drawable)
                    .into(binding.empImage!!)
        }
        binding.empName.text = model?.empName
        binding.empId.text = model?.empId.toString()
        binding.empTitle.text = model?.empTitle

        empId = model?.empId.toString()
        accId = model?.empAccountId.toString()

        viewModel.getDVReport(  fromDate, toDate,  empId)


    }

    override fun changeCycle(cycle: Cycle?) {
        binding.cycles.setText(cycle?.cycleArName)

        fromDate = cycle?.fromDate?.take(10)!!
        toDate = cycle?.toDate?.take(10)!!
        cycleId = cycle?.cycleId


        viewModel.getDVReport(  fromDate, toDate,  empId)
    }


    private fun setEmpData() {
        binding.empImage?.setImageResource(R.drawable.user_logo)
        if (!viewModel.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(viewModel.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }

        binding.empTitle.text = viewModel.dataManager.user.title
        binding.empName.text = viewModel.dataManager.user.nameAr
        binding.empId.text = viewModel.dataManager.user.empId.toString()
    }
    fun ads(){
        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.DOUBLE_VISIT_REPORT) {
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

        if (!model.webPageLink.isNullOrBlank()) {
            if (model.is_external!!) {
                binding.cardviewAds.setOnClickListener {
                    openWebPage(model.webPageLink)
                }
            } else {
                binding.cardviewAds.setOnClickListener {
                    meuNav(model.webPageLink!!.toInt(), context)
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
                Slider.init(PicassoImageLoadingService(baseActivity.applicationContext))
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
                                    meuNav(model.webPageLink!!.toInt(),context)
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
                val  intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }
    }
}
