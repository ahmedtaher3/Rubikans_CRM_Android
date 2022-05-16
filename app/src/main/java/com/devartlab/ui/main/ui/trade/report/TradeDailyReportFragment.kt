package com.devartlab.ui.main.ui.trade.report

import android.content.Context
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentReportDailyBinding
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
 import java.text.SimpleDateFormat
import java.util.*


class TradeDailyReportFragment : BaseFragment<FragmentReportDailyBinding>(), TradeInterface {
    lateinit var binding: FragmentReportDailyBinding
    lateinit var viewModel: TradeViewModel
    private var DATE: String? = null
    private var CODE = ""
    var fmt: SimpleDateFormat? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)
        fmt = SimpleDateFormat("yyyy-MM-dd' // 'hh:mm a", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
        viewModel.checkDay()
        binding.date.text = DATE?.take(10)
        binding.startDay.setOnClickListener {


            if (isMockSettingsON(baseActivity)) {
                Toast.makeText(baseActivity, "Please Close Mock Location First", Toast.LENGTH_SHORT).show()

            } else {


            /*    val startDayDialog = StartDayDialog(baseActivity, viewModel?.myAPI!!, this);
                startDayDialog.setCanceledOnTouchOutside(true);
                val window = startDayDialog.getWindow();
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                startDayDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                startDayDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                startDayDialog.show();*/
            }


        }

        binding.startBreak.setOnClickListener {

/*            val airLocation = AirLocation(
                    baseActivity,
                    true,
                    true,
                    object : AirLocation.Callbacks {
                        override fun onSuccess(location: Location) {

                            if (CommonUtilities.isFake(baseActivity, location)) {
                                viewModel.startBreak(DATE, "1", "1", CODE)

                            } else {
                                viewModel.startBreak(DATE, location.latitude.toString(), location.longitude.toString(), CODE)

                            }

                        }

                        override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                            Toast.makeText(baseActivity, "Field to get Location , please try again", Toast.LENGTH_SHORT).show()

                        }
                    })*/


        }

        binding.endBreak.setOnClickListener {

/*
            val airLocation = AirLocation(
                    baseActivity,
                    true,
                    true,
                    object : AirLocation.Callbacks {
                        override fun onSuccess(location: Location) {
                            if (CommonUtilities.isFake(baseActivity, location)) {
                                 viewModel.endBreak(DATE, "1", "1", CODE)

                            } else {
                                viewModel.endBreak(DATE, location.latitude.toString(), location.longitude.toString(), CODE)

                            }
                        }

                        override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                            Toast.makeText(baseActivity, "Field to get Location , please try again", Toast.LENGTH_SHORT).show()

                        }
                    })
*/


        }

        binding.endDay.setOnClickListener {


          /*  val endDayDialog = EndDayDialog(baseActivity, viewModel?.myAPI!!, this);
            endDayDialog.setCanceledOnTouchOutside(true);
            val window = endDayDialog.getWindow();
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            endDayDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            endDayDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            endDayDialog.show();*/


        }

        setObservers()
    }


    private fun setObservers() {

        viewModel.progress.observe(baseActivity, androidx.lifecycle.Observer {
            when (it) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })

     /*   viewModel.tradeDayLive.observe(baseActivity, androidx.lifecycle.Observer {

            if (it.isSuccessful) {

                CODE = it.tradeDay.code!!


                binding.startDay.text = "Start Day At " + it.tradeDay.startDayAt
                binding.startBreak.text = "Start Break At " + it.tradeDay.startBreakAt
                binding.endBreak.text = "End Break At " + it.tradeDay.endBreakAt
                binding.endDay.text = "End Day At " + it.tradeDay.endDayAt


                if (!it.tradeDay.startDayAt.isNullOrEmpty() && it.tradeDay.startBreakAt.isNullOrEmpty()) {
                    // in case start day

                    binding.startDay.isEnabled = false

                    binding.startDay.visibility = View.VISIBLE
                    binding.startBreak.visibility = View.VISIBLE
                    binding.endBreak.visibility = View.GONE
                    binding.endDay.visibility = View.GONE

                    binding.startBreak.text = "Start Break"

                }


                if (!it.tradeDay.startBreakAt.isNullOrEmpty() && it.tradeDay.endBreakAt.isNullOrEmpty()) {
                    // in case start break

                    binding.startDay.isEnabled = false
                    binding.startBreak.isEnabled = false

                    binding.startDay.visibility = View.VISIBLE
                    binding.startBreak.visibility = View.VISIBLE
                    binding.endBreak.visibility = View.VISIBLE
                    binding.endDay.visibility = View.GONE

                    binding.endBreak.text = "End Break"

                }


                if (!it.tradeDay.endBreakAt.isNullOrEmpty() && it.tradeDay.endDayAt.isNullOrEmpty()) {
                    // in case end break

                    binding.startDay.isEnabled = false
                    binding.startBreak.isEnabled = false
                    binding.endBreak.isEnabled = false

                    binding.startDay.visibility = View.VISIBLE
                    binding.startBreak.visibility = View.VISIBLE
                    binding.endBreak.visibility = View.VISIBLE
                    binding.endDay.visibility = View.VISIBLE

                    binding.endDay.text = "End Day"

                }


                if (!it.tradeDay.endDayAt.isNullOrEmpty()) {
                    // in case end day

                    binding.startDay.isEnabled = false
                    binding.startBreak.isEnabled = false
                    binding.endBreak.isEnabled = false
                    binding.endDay.isEnabled = false

                    binding.startDay.visibility = View.VISIBLE
                    binding.startBreak.visibility = View.VISIBLE
                    binding.endBreak.visibility = View.VISIBLE
                    binding.endDay.visibility = View.VISIBLE
                }

            } else {
                // day not started yet

                binding.startDay.visibility = View.VISIBLE
                binding.startBreak.visibility = View.GONE
                binding.endBreak.visibility = View.GONE
                binding.endDay.visibility = View.GONE
            }

        })
*/
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_report_daily
    }

    override fun onTradeStartDay(id: String, name: String) {

/*
        val airLocation = AirLocation(
                baseActivity,
                true,
                true,
                object : AirLocation.Callbacks {
                    override fun onSuccess(location: Location) {


                        if ( CommonUtilities.isFake(baseActivity, location)) {
                            viewModel.startDay(id, name, DATE, "1", "1")

                        } else {
                            viewModel.startDay(id, name, DATE, location.latitude.toString(), location.longitude.toString())

                        }
                    }

                    override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                        Toast.makeText(baseActivity, "Field to get Location , please try again", Toast.LENGTH_SHORT).show()

                    }
                })*/
    }

    override fun onTradeEndDay(firstAnswer: String, secondAnswer: String, thirdAnswer: String) {
/*        val airLocation = AirLocation(
                baseActivity,
                true,
                true,
                object : AirLocation.Callbacks {
                    override fun onSuccess(location: Location) {

                        if (CommonUtilities.isFake(baseActivity, location)) {
                            viewModel.endDay(DATE, "1", "1", CODE, firstAnswer, secondAnswer, thirdAnswer)

                        } else {
                            viewModel.endDay(DATE, location.latitude.toString(), location.longitude.toString(), CODE, firstAnswer, secondAnswer, thirdAnswer)

                        }


                    }

                    override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                        Toast.makeText(baseActivity, "Field to get Location , please try again", Toast.LENGTH_SHORT).show()

                    }
                })*/
    }


    fun isMockSettingsON(context: Context): Boolean {
        // returns true if mock location enabled, false if not enabled.
        return if (Settings.Secure.getString(context.contentResolver,
                        Settings.Secure.ALLOW_MOCK_LOCATION) == "0") false else true
    }



}