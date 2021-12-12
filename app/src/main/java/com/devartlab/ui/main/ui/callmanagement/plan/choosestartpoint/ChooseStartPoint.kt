package com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.devartlink.DevartLinkViewModel
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoView
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_choose_start_point.*
import retrofit2.Retrofit
import ss.com.bannerslider.Slider

private const val TAG = "ChooseStartPoint"

class ChooseStartPoint(
    private var activity: AppCompatActivity,
    private var chooseStartPointInterFace: ChooseStartPointInterFace,
    private var dataManager: DataManager,
    private var activityType: Int,
    private val activities: ActivityEntity?,
    private val DATE: String?,
    private val Type: Int?
) : Dialog(activity) {

    lateinit var recyclerView: RecyclerView
    lateinit var videoView: ExoVideoView
    lateinit var imageView: ImageView
    lateinit var bannerslider: Slider
    lateinit var btnHideShowAds: ImageView
    lateinit var constrAds: ConstraintLayout
    lateinit var startPointAdapter: StartPointAdapter
    lateinit var mediaSource: SimpleMediaSource
    lateinit var cardviewAds: CardView
    lateinit var moreThanAds: TextView
    lateinit var textView: WebView
    lateinit var close: ImageView
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    var editText: EditText? = null
    var list: ArrayList<ListEntity>? = null
    var listDao: ListDao? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_start_point)
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        startPointAdapter = StartPointAdapter(
            activity,
            chooseStartPointInterFace,
            dataManager,
            activityType,
            activities!!,
            DATE!!,
            Type!!
        )
        videoView = findViewById(R.id.videoView)
        imageView = findViewById(R.id.imageView)
        bannerslider = findViewById(R.id.bannerSlider)
        recyclerView = findViewById(R.id.startPointRecyclerView)
        btnHideShowAds = findViewById(R.id.btn_hide_show_ads)
        moreThanAds = findViewById(R.id.tv_more_than_ads)
        constrAds = findViewById(R.id.constr_ads)
        cardviewAds = findViewById(R.id.cardview_ads)
        textView = findViewById(R.id.textView)
        close = findViewById(R.id.close)
        editText = findViewById(R.id.editText_search)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = startPointAdapter
        listDao = DatabaseClient.getInstance(context)?.appDatabase?.listDao()
        var model = AdModel()

        for (m in dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.CHOOSE_START_POINT) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)
            && model.slideImages!!.equals(null)) {
            constrAds.setVisibility(View.GONE)
        }
        else if (model.resourceLink.equals(null)&&model.paragraph.equals(null)
            && model.slideImages!!.equals(null)) {
            imageView.visibility = View.VISIBLE
            Glide.with(context).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
        }
        if (!model.webPageLink.equals("")) {
            cardviewAds.setOnClickListener {
                val uri = Uri.parse(model.webPageLink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(context,intent,null)
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
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    textView.setText(
//                        Html.fromHtml(
//                            model.resourceLink,
//                            Html.FROM_HTML_MODE_LEGACY
//                        )
//                    );
//                } else
//                    textView.setText(Html.fromHtml(model.resourceLink))
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
                val  intent = Intent(activity, MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                activity?.startActivity(intent)
            }
        }
        close?.setOnClickListener(View.OnClickListener { dismiss() })

        getStartPointList("0", "0", "0", "0", "0")
    }


    public fun getStartPointList(
        TerrAssignIdStr: String,
        BrickIdStr: String,
        SpecialityIdStr: String,
        ClassIdStr: String,
        FilterText: String
    ) {


        if (dataManager.offlineMood) {

            Log.d(TAG, "getStartPointList: $FilterText")
            Completable.fromAction {
                if (FilterText.isNullOrEmpty() || FilterText == "0") {
                    startPointAdapter.setMyData(listDao?.pharmacies as ArrayList<ListEntity>)
                } else {
                    startPointAdapter.setMyData(listDao?.getPharmacies(FilterText) as ArrayList<ListEntity>)
                }
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {

            ProgressBar.visibility = View.VISIBLE
            myAPI?.getStartPointList(
                dataManager.user.accId,
                TerrAssignIdStr,
                BrickIdStr,
                SpecialityIdStr,
                ClassIdStr,
                FilterText
            )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<ListEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<ListEntity>) {

                        list = data
                        startPointAdapter.setMyData(data)

                        Completable.fromAction {
                            listDao?.insertAll(data)
                        }.subscribeOn(Schedulers.io())
                            .subscribe()
                    }

                    override fun onError(e: Throwable) {

                        ProgressBar.visibility = View.GONE
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

                    }

                    override fun onComplete() {
                        ProgressBar.visibility = View.GONE
                    }
                })
        }
    }

}
