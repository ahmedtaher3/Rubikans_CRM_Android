package com.devartlab.ui.main.ui.eShopping.nearbyPharmacy

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityNearbyPharmacyBinding
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class NearbyPharmacyActivity  : BaseActivity<ActivityNearbyPharmacyBinding?>(),
    MapboxMap.OnMarkerClickListener  {
    lateinit var binding:ActivityNearbyPharmacyBinding
    lateinit var queue: RequestQueue

    override fun getLayoutId(): Int {
        return R.layout.activity_nearby_pharmacy
    }

    // private var mapView: MapView? = null
    private var map_boxMap: MapboxMap? = null

    val bounds= LatLngBounds.Builder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        binding = viewDataBinding!!

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.nearby_pharmacy)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        queue = Volley.newRequestQueue(this)

        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView?.getMapAsync { mapboxMap ->


            map_boxMap = mapboxMap
            map_boxMap!!.setPadding(100, 100, 100, 100)

            map_boxMap!!.getUiSettings().setZoomGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setScrollGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setAllGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setRotateGesturesEnabled(true)
            map_boxMap!!.getUiSettings().getFocalPoint()
            map_boxMap!!.getUiSettings().setRotateVelocityAnimationEnabled(true)
            map_boxMap!!.getUiSettings().setTiltGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setScaleVelocityAnimationEnabled(true)
            map_boxMap!!.getUiSettings().setCompassEnabled(false)
            map_boxMap!!.getUiSettings().setDeselectMarkersOnTap(true)
            map_boxMap!!.setOnMarkerClickListener(this)
     //       System.out.println(" getDailyReport " + id)

 //           viewModel.getDailyReport(false, id?.toInt()!!, time?.toLong()!!, shift_id?.toInt()!!)
            setObservers()


            mapboxMap.setStyle(Style.MAPBOX_STREETS) {

            }

        }
    }

    private fun setObservers() {
    }


    fun getAddress(strLatitude: String, strLongitude: String): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this@NearbyPharmacyActivity, Locale("ar"))
        val latitude = strLatitude.toDouble()
        val longitude = strLongitude.toDouble()
        var address = ""
        try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            //  throw RuntimeException(e.message)
        }
        return address
    }

    override fun onStart() {
        super.onStart()
        binding.mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView?.onDestroy()
    }

    override fun onMarkerClick(marker: com.mapbox.mapboxsdk.annotations.Marker): Boolean {

        getLocationFormGoogle(
            marker.position.latitude.toString() + "," + marker.position.longitude.toString(),
            marker.title
        )



        return true
    }


    fun getLocationFormGoogle(latLng: String, title: String): String {

        var address = ""
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
            "https://maps.googleapis.com/maps/api/geocode/json?latlng=$latLng&key=AIzaSyA0a4_5jHzBStit_c4_ZM7TPTCO-uNLfoM&language=ar-EG",
            null,
            Response.Listener<JSONObject>() {
                System.out.println(" jsonObjectRequest " + it.toString())

                if (it.optJSONArray("results").length() > 0) {

                    val results: JSONArray = it.getJSONArray("results")
                    val result = results.getJSONObject(0)

                    System.out.println(" getLocationFormGoogle " + result.getString("formatted_address"))


             //       binding.address.text = (title + "\n" + result.getString("formatted_address"))

                } else {
                    System.out.println(" getLocationFormGoogle " + latLng)

                }

            }
            , Response.ErrorListener() {})

        queue.add(jsonObjectRequest)


        return address

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}