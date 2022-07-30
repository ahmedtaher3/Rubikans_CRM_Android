package com.devartlab.ui.main.ui.contactlist.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
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
import com.devartlab.ui.main.MainActivity
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportActivity
import com.devartlab.ui.main.ui.contactlist.pojo.Contactlist
import com.devartlab.ui.main.ui.contactlist.pojo.Request
import com.devartlab.ui.main.ui.devartlink.DevartLinkActivity
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingActivity
import com.devartlab.ui.main.ui.eShopping.orientationVideos.OrientationVideosActivity
import com.devartlab.ui.main.ui.market.MarketRequestTypesActivity
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.devartlab.utils.ProgressLoading.dismiss
import com.devartlab.utils.ProgressLoading.show
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoView
import ss.com.bannerslider.Slider
import ss.com.bannerslider.event.OnSlideClickListener
import java.util.*

class ContactsActivity : AppCompatActivity() {
    private var contactsViewModel: GetContactsViewModel? = null
    private var contactlistArrayList: ArrayList<Contactlist>? = null
    private var contactsRecyclerAdapter: GetContactsRecyclerAdapter? = null
    private var contactrecycler: RecyclerView? = null

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


    fun meuNav(id: Int, context: Context) {
        when (id) {
            1 -> context.startActivity(Intent(context, MainActivity::class.java))
            4 -> context.startActivity(Intent(context, DevartLinkActivity::class.java))
            7 -> context.startActivity(Intent(context, EmployeeReportActivity::class.java))
            10 -> context.startActivity(Intent(context, MarketRequestTypesActivity::class.java))
            14 -> context.startActivity(Intent(context, ContactsActivity::class.java))
            23 -> context.startActivity(Intent(context, Home4EShoppingActivity::class.java))
            26 -> context.startActivity(Intent(context, OrientationVideosActivity::class.java))
        }
    }
}