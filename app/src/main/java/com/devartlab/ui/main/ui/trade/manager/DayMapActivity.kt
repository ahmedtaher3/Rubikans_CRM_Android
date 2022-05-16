package com.devartlab.ui.main.ui.trade.manager

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.FragmentDayMapBinding
import com.devartlab.model.TradeDay
import com.devartlab.ui.main.ui.callmanagement.list.addnewcus.filterlist.FilterNewListFragment
import com.devartlab.ui.main.ui.trade.TradeViewModel
import com.devartlab.utils.ProgressLoading
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback

import java.util.*

private const val MODEL = "TradeDay"

class DayMapActivity : BaseActivity<FragmentDayMapBinding>() {
    private var model: TradeDay? = null
    lateinit var binding: FragmentDayMapBinding
    lateinit var viewModel: TradeViewModel
   // var mapFragment: SupportMapFragment? = null
 //   lateinit var mMap: GoogleMap

    override fun getLayoutId(): Int {
        return R.layout.fragment_day_map
    }

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            model = it.getSerializableExtra(MODEL) as TradeDay
        }
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)

        binding = viewDataBinding!!

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.title = "Trade Report"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
        viewModel.checkDay()

    }


    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        mMap.setOnMarkerClickListener(onMarkerClickedListener);



        binding.startDay.text = model?.startDayAt
        binding.startBreak.text = model?.startBreakAt
        binding.endBreak.text = model?.endBreakAt
        binding.endDay.text = model?.endDayAt

        val points = ArrayList<LatLng>()

        points.add(LatLng(model?.startDayLat?.toDouble()!!, model?.startDayLong?.toDouble()!!))
        points.add(LatLng(model?.startBreakLat?.toDouble()!!, model?.startBreakLong?.toDouble()!!))
        points.add(LatLng(model?.endBreakLat?.toDouble()!!, model?.endBreakLong?.toDouble()!!))
        points.add(LatLng(model?.endDayLat?.toDouble()!!, model?.endDayLong?.toDouble()!!))


        val optionsStartDay = MarkerOptions().position(LatLng(model?.startDayLat?.toDouble()!!, model?.startDayLong?.toDouble()!!)).anchor(0.5f, 0.5f)
        optionsStartDay.title("Start At : " + model?.startDayAt)
        mMap.addMarker(optionsStartDay)

        val optionsStartBreak = MarkerOptions().position(LatLng(model?.startBreakLat?.toDouble()!!, model?.startBreakLong?.toDouble()!!!!)).anchor(0.5f, 0.5f)
        optionsStartBreak.title("Start Break At : " + model?.startBreakAt)
        mMap.addMarker(optionsStartBreak)

        val optionsEndBreak = MarkerOptions().position(LatLng(model?.endBreakLat?.toDouble()!!!!, model?.endBreakLong?.toDouble()!!)).anchor(0.5f, 0.5f)
        optionsEndBreak.title("End Break At : " + model?.endBreakAt)
        mMap.addMarker(optionsEndBreak)

        val optionsEndDay = MarkerOptions().position(LatLng(model?.endDayLat?.toDouble()!!, model?.endDayLong?.toDouble()!!)).anchor(0.5f, 0.5f)
        optionsEndDay.title("End At : " + model?.endDayAt)
        mMap.addMarker(optionsEndDay)


        val builder = LatLngBounds.Builder()
        for (point in points) {
            builder.include(point)
        }

        try {
            val bounds = builder.build()

            val padding = 100 // offset from edges of the map in pixels

            val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)

            mMap.moveCamera(cu)
        } catch (e: java.lang.Exception) {
            Toast.makeText(this@DayMapActivity, "no locations found", Toast.LENGTH_SHORT).show()

        }


    }

    private fun setObservers() {

        viewModel.tradeDayLive.observe(this, Observer { it ->
            System.out.println("responseLive")

            if (it.isSuccessful) {


            }

        })

        viewModel.progress.observe(this, Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {
                    ProgressLoading.show(this@DayMapActivity)
                }
            }
        })

    }

    private
    val onMarkerClickedListener = OnMarkerClickListener { marker ->


        binding.address.text = getAddress(marker.position.latitude.toString(), marker.position.longitude.toString())
        if (marker.isInfoWindowShown) {
            marker.hideInfoWindow()
        } else {
            marker.showInfoWindow()
        }
        true
    }

    fun getAddress(strLatitude: String, strLongitude: String): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this@DayMapActivity, Locale("ar"))
        val latitude = strLatitude.toDouble()
        val longitude = strLongitude.toDouble()
        var address = ""
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            //  throw RuntimeException(e.message)
        }
        return address
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}
