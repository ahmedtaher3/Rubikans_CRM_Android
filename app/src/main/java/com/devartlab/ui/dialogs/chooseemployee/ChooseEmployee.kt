package com.devartlab.ui.dialogs.chooseemployee

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.CustomerList
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.StartPointAdapter
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ss.com.bannerslider.Slider

class ChooseEmployee(context: Context, private var chooseEmployeeInterFace: ChooseEmployeeInterFace, private var dataManager: DataManager)
    : Dialog(context), EmployeeSearchAdapter.OnEmployeeFilterClick, EmployeeSearchSelectedAdapter.OnFilterEmployeesChange {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewSelected: RecyclerView
    lateinit var close: ImageView
    lateinit var adapter: EmployeeSearchAdapter
    lateinit var adapterSelected: EmployeeSearchSelectedAdapter
    lateinit var progressBar: ProgressBar
    lateinit var videoView: ExoVideoView
    lateinit var imageView: ImageView
    lateinit var bannerslider: Slider
    lateinit var btnHideShowAds: ImageView
    lateinit var constrAds: ConstraintLayout
    lateinit var mediaSource: SimpleMediaSource
    lateinit var cardviewAds: CardView
    lateinit var moreThanAds: TextView
    lateinit var textView: TextView
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    var editText: EditText? = null
    var list: ArrayList<CustomerList>? = null
    var lastId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_employee)


        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        progressBar = findViewById(R.id.progressBar)
        close = findViewById(R.id.close)
        editText = findViewById(R.id.editText_search)
        lastId =  dataManager.user.empId
        videoView = findViewById(R.id.videoView)
        imageView = findViewById(R.id.imageView)
        bannerslider = findViewById(R.id.bannerSlider)
        recyclerView = findViewById(R.id.startPointRecyclerView)
        btnHideShowAds = findViewById(R.id.btn_hide_show_ads)
        moreThanAds = findViewById(R.id.tv_more_than_ads)
        constrAds = findViewById(R.id.constr_ads)
        cardviewAds = findViewById(R.id.cardview_ads)
        textView = findViewById(R.id.textView)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = EmployeeSearchAdapter(context, chooseEmployeeInterFace, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        recyclerViewSelected = findViewById(R.id.recyclerViewSelected)
        adapterSelected = EmployeeSearchSelectedAdapter(context, this)
        recyclerViewSelected.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSelected.adapter = adapterSelected

        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if (TextUtils.isEmpty(charSequence.toString()))
                    getFilterEmpl(lastId, "0")
                else
                    getFilterEmpl(lastId, charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        getFilterEmpl(lastId, "0")

        var model = AdModel()
        for (m in dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.CHOOSE_EMPLOYEE) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)) {
            constrAds.setVisibility(View.GONE)
        }
        else if (model.resourceLink.equals(null)&&model.paragraph.equals(null)) {
            imageView.visibility = View.VISIBLE
            Glide.with(context).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
        }
        if (!model.webPageLink.equals("")) {
            cardviewAds.setOnClickListener {
                val uri = Uri.parse(model.webPageLink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                ContextCompat.startActivity(context, intent, null)
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
                Glide.with(context).load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
            }
            "GIF" -> {
                imageView.visibility = View.VISIBLE
                Glide.with(context).asGif().load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView);
            }
            "Paragraph" -> {
                textView.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    textView.setText(
                        Html.fromHtml(
                            model.paragraph,
                            Html.FROM_HTML_MODE_LEGACY
                        )
                    );
                } else
                    textView.setText(Html.fromHtml(model.paragraph))
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
                val  intent = Intent(context, MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                context.startActivity(intent)
            }
        }
    }

    public fun getFilterEmpl(empId: Int, filterText: String) {

        System.out.println("accountId  = " + empId + "filterText = " + filterText)
        progressBar.visibility = View.VISIBLE
        myAPI?.filterEmployees(empId, "0")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {


                        print(data)
                        progressBar.visibility = View.GONE
                        adapter.setMyData(data)


                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {}
                })
    }

    override fun setOnEmployeeFilterClick(model: FilterDataEntity?) {



                 adapterSelected.addItem(model)

    }

    override fun onChange(employeesList: MutableList<FilterDataEntity>?) {


        if (employeesList.isNullOrEmpty()) {
            lastId = dataManager.user.empId
            getFilterEmpl(lastId, "0")
            print("lastId  " + lastId)
        } else {
            lastId = employeesList.last().empId!!
            getFilterEmpl(lastId, "0")
            print("lastId  " + lastId)
        }

    }


}
