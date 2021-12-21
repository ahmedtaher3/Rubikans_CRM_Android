package com.devartlab.ui.main.ui.employeeservices

import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.devartlab.GetMyLocation
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityReportDailyBinding
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.LocationUtils
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*


class SubmitFingerprintActivity : BaseActivity<ActivityReportDailyBinding>() {
    lateinit var binding: ActivityReportDailyBinding
    lateinit var viewModel: TradeViewModel
    private var DATE: String? = null
    private var CODE = ""
    var fmt: SimpleDateFormat? = null


    lateinit var companyLocation: Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Confirm Fingerprint"


        fmt = SimpleDateFormat("yyyy-MM-dd' // 'hh:mm a", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)

        companyLocation = Location("")

        viewModel.checkDay()
        binding.date.text = DATE?.take(10)
        binding.startDay.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Start Day")
            builder.setMessage("Are you sure?")
            builder.setPositiveButton("YES") { dialog, which ->

                dialog.dismiss()

                if (LocationUtils.checkPermission(this@SubmitFingerprintActivity)) {
                    ProgressLoading.showWithText(this@SubmitFingerprintActivity, resources.getString(R.string.fetching_your_location))
                    val getMyLocation = GetMyLocation(this@SubmitFingerprintActivity)
                    getMyLocation.getLocation(this@SubmitFingerprintActivity, object : GetMyLocation.LocationResult() {
                        override fun gotLocation(location: Location?, msg: String?) {

                                ProgressLoading.dismiss()
                                if (location != null) {


                                    val distance = location.distanceTo(companyLocation).toInt()


                                    viewModel.startDay(
                                        DATE,
                                        location.latitude.toString(),
                                        location.longitude.toString(),
                                        distance.toString()
                                    )


                                }
                                else {

                               runOnUiThread {
                                        Toast.makeText(this@SubmitFingerprintActivity, getString(R.string.error_location_try_again), Toast.LENGTH_LONG).show()
                                    }


                                }

                        }

                    })

                }


            }
            builder.setNegativeButton("NO") { dialog, which ->

                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()


        }


        binding.endDay.setOnClickListener {


            val builder = AlertDialog.Builder(this)
            builder.setTitle("End Day")
            builder.setMessage("Are you sure?")
            builder.setPositiveButton("YES") { dialog, which ->

                dialog.dismiss()
                if (LocationUtils.checkPermission(this@SubmitFingerprintActivity)) {
                    ProgressLoading.showWithText(this@SubmitFingerprintActivity, resources.getString(R.string.fetching_your_location))
                    val getMyLocation = GetMyLocation(this@SubmitFingerprintActivity)
                    getMyLocation.getLocation(this@SubmitFingerprintActivity, object : GetMyLocation.LocationResult() {
                        override fun gotLocation(location: Location?, msg: String?) {

                            ProgressLoading.dismiss()
                            if (location != null){

                                val distance = location.distanceTo(companyLocation).toInt()


                                System.out.println(
                                    " location " + location.latitude.toString() + "," + location.longitude.toString() + "\n"
                                            + " companyLocation " + companyLocation.latitude.toString() + "," + companyLocation.longitude.toString() + "\n"
                                )

                                viewModel.endDay(
                                    DATE,
                                    location.latitude.toString(),
                                    location.longitude.toString(),
                                    CODE,
                                    distance.toString()
                                )


                            }
                            else {

                                runOnUiThread {
                                    Toast.makeText(this@SubmitFingerprintActivity, getString(R.string.error_location_try_again), Toast.LENGTH_LONG).show()
                                }


                            }

                        }

                    })

                }


            }
            builder.setNegativeButton("NO") { dialog, which ->

                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()


        }

        setObservers()


    }


    private fun setObservers() {

        viewModel.progress.observe(this, androidx.lifecycle.Observer {
            when (it) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })

        viewModel.tradeDayLive.observe(this, androidx.lifecycle.Observer {

            System.out.println(" tradeDayLive " + it.tradeDay.toString())
            System.out.println(" tradeDayLive " + it.toString())

            if (!it.lat.isNullOrEmpty() && !it.lng.isNullOrEmpty()) {
                companyLocation.latitude = it.lat?.toDouble()!!
                companyLocation.longitude = it.lng?.toDouble()!!


            }

            if (it.isSuccessful) {

                CODE = it.tradeDay?.code!!


                binding.startDay.text = "Start Day At " + it.tradeDay?.startDayAt
                binding.endDay.text = "End Day At " + it.tradeDay?.endDayAt


                if (!it.tradeDay?.startDayAt.isNullOrEmpty()) {
                    // in case start day

                    binding.startDay.isEnabled = false

                    binding.startDay.visibility = View.VISIBLE
                    binding.endDay.visibility = View.VISIBLE


                }


                if (!it.tradeDay?.endDayAt.isNullOrEmpty()) {
                    // in case end day

                    binding.startDay.isEnabled = false
                    binding.endDay.isEnabled = false

                    binding.startDay.visibility = View.VISIBLE
                    binding.endDay.visibility = View.VISIBLE
                }

            } else {
                // day not started yet

                binding.startDay.visibility = View.VISIBLE
                binding.endDay.visibility = View.GONE
            }

        })

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_report_daily
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

}