package com.devartlab.ui.main.ui.contactlist.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.callmanagement.plan.addplan.doubleextra.FilterListExtraFragment
import com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint.StartPointAdapter
import com.devartlab.ui.main.ui.contactlist.pojo.Contactlist
import com.devartlab.ui.main.ui.contactlist.pojo.Request
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.devartlab.utils.ProgressLoading.dismiss
import com.devartlab.utils.ProgressLoading.show
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoView
import ss.com.bannerslider.Slider
import java.util.*

class ContactsActivity : AppCompatActivity() {
    private var contactsViewModel: GetContactsViewModel? = null
    private var contactlistArrayList: ArrayList<Contactlist>? = null
    private var contactsRecyclerAdapter: GetContactsRecyclerAdapter? = null
    private var contactrecycler: RecyclerView? = null
    lateinit var videoView: ExoVideoView
    lateinit var imageView: ImageView
    lateinit var bannerslider: Slider
    lateinit var btnHideShowAds: ImageView
    lateinit var constrAds: ConstraintLayout
    lateinit var mediaSource: SimpleMediaSource
    lateinit var cardviewAds: CardView
    lateinit var moreThanAds: TextView
    lateinit var textView: WebView
    var dataManager:DataManager? = null
    private var inputsearch: EditText? = null
    private var toolbar: Toolbar? = null
    var swiperefresh: SwipeRefreshLayout? = null
    private var spinnerfilteroftyperequest: Spinner? = null
    private var choosedspinner_typerequesttextFILTER: String? = null
    var filterdlistsearch: ArrayList<Contactlist>? = null
    var lastCompletelyVisibleItemPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_contacts)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Contacts List"
        contactrecycler = findViewById(R.id.contact_recycler)
        videoView = findViewById(R.id.videoView)
        imageView = findViewById(R.id.imageView)
        bannerslider = findViewById(R.id.bannerSlider)
        btnHideShowAds = findViewById(R.id.btn_hide_show_ads)
        moreThanAds = findViewById(R.id.tv_more_than_ads)
        constrAds = findViewById(R.id.constr_ads)
        cardviewAds = findViewById(R.id.cardview_ads)
        textView = findViewById(R.id.textView)
        dataManager = (getApplication() as BaseApplication).dataManager!!
        spinnerfilteroftyperequest = findViewById(R.id.spinerr_of_filterdep)
        contactsRecyclerAdapter = GetContactsRecyclerAdapter(this@ContactsActivity)
        contactrecycler?.setLayoutManager(LinearLayoutManager(this))
        contactrecycler?.setHasFixedSize(true)
        contactrecycler?.setAdapter(contactsRecyclerAdapter)
        contactsViewModel =
            ViewModelProviders.of(this@ContactsActivity).get(GetContactsViewModel::class.java)
        contactlistArrayList = ArrayList()
        getmyrequest("")
        //        selecttheTypeRequestfilter();
        swiperefresh = findViewById(R.id.swiperefresh)
        swiperefresh?.setOnRefreshListener(OnRefreshListener {
            swiperefresh?.setRefreshing(true)
            getmyrequest("")
            swiperefresh?.setRefreshing(false)
        })
        inputsearch = findViewById(R.id.placeofsearchwidget)
        inputsearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })

//
//        lastCompletelyVisibleItemPosition = ((LinearLayoutManager) contactrecycler.getLayoutManager()).findLastVisibleItemPosition();
//
    ads()
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<Contactlist>()
        for (item in filterdlistsearch!!) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        //        this.contactlistArrayList = filteredList;
        contactsRecyclerAdapter!!.setArrayList(filteredList)
        //
    }

    fun selecttheTypeRequestfilter(filterd: ArrayList<String?>?) {
        val adapter = ArrayAdapter(applicationContext, R.layout.textspinner, filterd!!)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerfilteroftyperequest!!.adapter = adapter
        spinnerfilteroftyperequest!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                l: Long
            ) {
                choosedspinner_typerequesttextFILTER = parent.getItemAtPosition(position).toString()
                val newList = ArrayList<Contactlist>()
                for (model in contactlistArrayList!!) {
                    Log.d("getTypeRequest =   ", model.dep)
                    if (model.dep == choosedspinner_typerequesttextFILTER) {
                        newList.add(model)
                    } else if (choosedspinner_typerequesttextFILTER == "All") {
                        newList.add(model)
                    }
                }
                filterdlistsearch = newList
                contactsRecyclerAdapter!!.setArrayList(newList)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    fun getmyrequest(text: String) {
        show(this)
        contactsViewModel!!.getRequest()
        contactsViewModel!!.googlesheetMutableLiveData.observe(
            this@ContactsActivity,
            Observer<Request?> { request ->
                if (request != null && request.contactlist != null) {
                    contactlistArrayList = request.contactlist as ArrayList<Contactlist>
                    val all: ArrayList<String?> = ArrayList()
                    val filterd: ArrayList<String?> = ArrayList()
                    val newList = ArrayList<Contactlist>()
                    for (filtered in contactlistArrayList!!) {
                        all.add(filtered.dep)
                        if (filtered.name.toLowerCase().contains(text.toLowerCase())) {
                            newList.add(filtered)
                        } else {
                            newList.add(filtered)
                        }
                        //                        newList.add(model);
                        Log.d("getTypeRequest =   ", filtered.name)
                    }
                    //                    call();
                    filterd.add("All")
                    for (filter in all) {
                        if (!filterd.contains(filter)) filterd.add(filter)
                    }
                    selecttheTypeRequestfilter(filterd)
                    contactsRecyclerAdapter!!.setArrayList(newList)
                    dismiss()
                }
            })
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
    fun ads(){
        var model = AdModel()
        for (m in dataManager!!.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.CONTACTS_LIST) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)
            && model.slideImages==null) {
            constrAds.setVisibility(View.GONE)
        }
        else if (model.resourceLink.equals(null)&&model.paragraph.equals(null)
            && model.slideImages==null) {
            imageView.visibility = View.VISIBLE
            Glide.with(this).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(imageView)
        }
        if (!model.webPageLink.equals("")) {
            cardviewAds.setOnClickListener {
                val uri = Uri.parse(model.webPageLink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
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
                Slider.init(PicassoImageLoadingService(this))
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
                intent = Intent(this, MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                startActivity(intent)
            }
        }
    }
}