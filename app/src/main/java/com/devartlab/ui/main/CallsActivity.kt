package com.devartlab.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.arranged.ArrangedDao
import com.devartlab.data.room.arranged.ArrangedEntity
import com.devartlab.data.room.massages.MassageEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.databinding.ActivityCallsBinding
import com.devartlab.model.AdModel
import com.devartlab.model.SlideModel
import com.devartlab.ui.dialogs.massages.MassagesActivity
import com.devartlab.ui.dialogs.massages.OnMassageSelect
import com.devartlab.ui.main.ui.MainArrangedAdapter
import com.devartlab.ui.main.ui.callmanagement.trade.printer.printerControl.BixolonPrinter
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.ui.slider.ImagesSlider
import com.devartlab.utils.*
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ss.com.bannerslider.Slider
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

private const val TAG = "CallsActivity"

class CallsActivity : BaseActivity<ActivityCallsBinding?>(), MainAdapter.OpenMassages,
    OnMassageSelect, MainArrangedAdapter.OpenArrangedMassages {
    var isTablet = false
    lateinit var activityCallsBinding: ActivityCallsBinding
    var mainAdapter: MainAdapter? = null
    var mainArrangedAdapter: MainArrangedAdapter? = null
    var mainViewModel: MainViewModel? = null
    var slides: ArrayList<SlideEntity>? = null
    var Plan_Visit_ID: String? = null
    var arrangedDao: ArrangedDao? = null
    lateinit var mediaSource: SimpleMediaSource

    private val bxlPrinter: BixolonPrinter? = null


    private var surveyDialog: MassagesActivity? = null


    public var model: PlanEntity? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_calls
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCallsBinding = viewDataBinding!!
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        arrangedDao = DatabaseClient.getInstance(application)?.appDatabase?.arrangedDao()






        setSupportActionBar(activityCallsBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Call"

        model = intent.extras?.getParcelable("PlanVisitModel")
        Log.d(TAG, "onCreate: " + intent.extras?.getParcelable("PlanVisitModel"))



        slides = ArrayList()
        isTablet = CommonUtilities.checkIsTablet(this)
        mainAdapter = MainAdapter(this, ArrayList(), Plan_Visit_ID, this)
        mainArrangedAdapter = MainArrangedAdapter(this, ArrayList(), Plan_Visit_ID, this)


        if (isTablet) {
            val layoutManager = GridLayoutManager(this, 3)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            activityCallsBinding!!.myRecyclerView.layoutManager = layoutManager


        } else {
            activityCallsBinding!!.myRecyclerView.layoutManager = LinearLayoutManager(this)

        }


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activityCallsBinding!!.arrangedRecyclerView.layoutManager = layoutManager
        activityCallsBinding!!.arrangedRecyclerView?.adapter = mainArrangedAdapter

        activityCallsBinding!!.myRecyclerView.adapter = mainAdapter
        mainViewModel!!.getAll()
        if (model != null) {
            Log.d(TAG, "onCreate: " + model.toString())
            mainViewModel!!.getAllArranged(model?.customerid!!)
        }
        mainViewModel!!.arrangedResponseLive.observe(
            this,
            Observer<List<ArrangedEntity?>?> { mainArrangedAdapter!!.setMyData(it) })
        mainViewModel!!.allProducts.observe(
            this,
            Observer<List<ProductEntity?>?> { productEntities ->
                mainAdapter!!.setMyData(productEntities)
            })
        mainViewModel!!.planModels.observe(this, Observer<List<PlanEntity?>?> {


        })
        activityCallsBinding!!.finishVisit.setOnClickListener {

            finishVisit()

        }


        mainViewModel?.progress?.observe(this, Observer {

            when (it) {
                1 -> {
                    ProgressLoading.show(this)
                }
                0 -> {
                    ProgressLoading.dismiss()

                }
                2 -> {


                    Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            ProgressLoading.dismiss()
                            mainViewModel!!.getAll()
                        }, {});

                }
            }

        })

  

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.call_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sync -> {
                mainViewModel?.syncProducts();


                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun openMassages(productEntity: ProductEntity?) {


        surveyDialog = MassagesActivity(
            this@CallsActivity,
            productEntity?.itemId.toString(),
            this@CallsActivity,
            this@CallsActivity
        );

        surveyDialog?.setCanceledOnTouchOutside(true);
        val window = surveyDialog?.getWindow();
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        );
        surveyDialog?.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);

        surveyDialog?.getWindow()
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        surveyDialog?.show();


    }

    override fun addEditMassage(
        massageEntity: MassageEntity?,
        productEntity: ProductEntity?,
        slides: MutableList<SlideModel>?,
        arrangedId: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun setOnMassageSelect(massageEntity: MassageEntity?) {
        if (surveyDialog != null)
            surveyDialog?.dismiss()
        val intent = Intent(this@CallsActivity, ImagesSlider::class.java)
        intent.putExtra("VisitID", model?.planId)
        intent.putExtra("MassageID", massageEntity?.messageId)
        intent.putExtra("ProductID", massageEntity?.itemId)
        intent.putExtra("MassageType", 2)
        startActivity(intent)
    }

    override fun addCustomMassage(
        massageEntity: MassageEntity?,
        productEntity: ProductEntity?,
        list: List<SlideModel>
    ) {


    }

    /**
     * check if open from role play or from plan
     * if role play just finish activity else mark as visited and finish activity
     */
    private fun finishVisit() {

        if (model != null) {

            val dialogBuilder = AlertDialog.Builder(this@CallsActivity)
            // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.finish_visit, null)
            dialogBuilder.setView(dialogView)
            val noteEditText = dialogView.findViewById<View>(R.id.noteEditText) as EditText
            val addButton = dialogView.findViewById<View>(R.id.addButton) as Button
            val skipButton = dialogView.findViewById<View>(R.id.skipButton) as TextView

            val alertDialog = dialogBuilder.create()
            addButton.setOnClickListener {
                var planEntity: PlanEntity?
                planEntity = model
                planEntity?.visit = true
                planEntity?.notes = noteEditText.text.toString()




                Completable.fromAction {

                    mainViewModel?.planDao?.update(planEntity)
                    mainViewModel?.dataManager?.saveSyncAble(false)

                    val allConfirmed = mainViewModel?.planDao?.allConfirmed
                    val json = Gson().toJson(allConfirmed)
                    CommonUtilities.writeToSDFile(json)

                    runOnUiThread { finish() }
                }
                    .subscribeOn(Schedulers.io())
                    .subscribe()


            }
            skipButton.setOnClickListener {
                alertDialog.dismiss()

                var planEntity: PlanEntity?
                planEntity = model
                planEntity?.visit = true
                mainViewModel?.updatePlan(planEntity)
                finish()
            }

            alertDialog.show()


        } else {
            finish()
        }
    }

    override fun openArrangedMassages(arrangedEntity: ArrangedEntity?) {

        if (surveyDialog != null)
            surveyDialog?.dismiss()

        val intent = Intent(this@CallsActivity, ImagesSlider::class.java)
        intent.putExtra("VisitID", model?.planId)
        intent.putExtra("MassageID", arrangedEntity?.id)
        intent.putExtra("ProductID", arrangedEntity?.productId)
        intent.putExtra("MassageType", 1)
        startActivity(intent)


    }
}