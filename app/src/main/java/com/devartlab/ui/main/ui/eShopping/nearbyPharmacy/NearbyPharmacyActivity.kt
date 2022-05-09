package com.devartlab.ui.main.ui.eShopping.nearbyPharmacy

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityNearbyPharmacyBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.mapbox.android.core.location.*
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class NearbyPharmacyActivity : BaseActivity<ActivityNearbyPharmacyBinding?>(),
    MapboxMap.OnMarkerClickListener, OnMapReadyCallback, PermissionsListener {
    lateinit var binding: ActivityNearbyPharmacyBinding
    var viewModel: NearbyPharmacyViewModel? = null
    lateinit var queue: RequestQueue
    private lateinit var permissionsManager: PermissionsManager
    private lateinit var locationEngine: LocationEngine
    private lateinit var callback: LocationChangeListeningCallback

    override fun getLayoutId(): Int {
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        return R.layout.activity_nearby_pharmacy
    }


    // private var mapView: MapView? = null
    private lateinit var map_boxMap: MapboxMap

    val bounds = LatLngBounds.Builder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.nearby_pharmacy)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this)[NearbyPharmacyViewModel::class.java]
        queue = Volley.newRequestQueue(this)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        binding.btnHideShowAds.setOnClickListener {
            if (binding.linlayPens.visibility == View.VISIBLE) {
                binding.linlayPens.visibility = View.GONE
                binding.btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
            } else {
                binding.linlayPens.visibility = View.VISIBLE
                binding.btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
            }
        }
        setObservers()
    }

    private fun setObservers() {
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.responseNearbyPharmacy.observe(this) {
            when {
                it!!.code == 401 -> {
                    viewModel!!.dataManager.clear()
                    UserPreferenceHelper.clean()
                    Toast.makeText(this, "please login again", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                it.data.data.isNullOrEmpty() -> {
                    //errorMessage if data coming is null;
                    Toast.makeText(this, "not found nearby pharmacies", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    for (model in it.data.data) {
                        if (model.pharmacyId != model.mpharmacy.id) {//Active but connect with other sale
                            try {
                                val marker = map_boxMap?.addMarker(
                                    MarkerOptions().position(
                                        LatLng(
                                            model.lat?.toDouble()!!,
                                            model.lng?.toDouble()!!
                                        )
                                    )
                                        .title( resources.getString(R.string.unaction)+"\n"+
                                                resources.getString(R.string.pharmacy)+"  " + model.mpharmacy.name+("- ")
                                                +resources.getString(R.string.id)+" :"+model.mpharmacy.id+"\n"+
                                                resources.getString(R.string.mr)+"  "+model.mpharmacy.mmndob.name+("- ")
                                                +resources.getString(R.string.id)+" :"+model.mpharmacy.mmndob.id+("- ")
                                                +model.mpharmacy.mmndob.line)
                                        .icon(
                                            IconFactory.getInstance(this)
                                                .fromResource(R.drawable.mapbox_marker_icon_default)
                                        )
                                )
                            } catch (e: Exception) {
                            }
                        } else {
                            if (model.orders_count == 0) {//Zero Order
                                try {
                                    val marker = map_boxMap?.addMarker(
                                        MarkerOptions().position(
                                            LatLng(
                                                model.lat?.toDouble()!!,
                                                model.lng?.toDouble()!!
                                            )
                                        )
                                                   .title( resources.getString(R.string.zero_order)+"\n"+
                                                       resources.getString(R.string.pharmacy)+"  " + model.mpharmacy.name+("- ")
                                                           +resources.getString(R.string.id)+" :"+model.mpharmacy.id+"\n"+
                                                           resources.getString(R.string.mr)+"  "+model.mpharmacy.mmndob.name+("- ")
                                                           +resources.getString(R.string.id)+" :"+model.mpharmacy.mmndob.id+("- ")
                                                   +model.mpharmacy.mmndob.line)
                                            .icon(
                                                IconFactory.getInstance(this)
                                                    .fromResource(R.drawable.mapbox_marker_yellow)
                                            )
                                    )
                                } catch (e: Exception) {
                                }
                            } else {
                                when (model.mpharmacy.activated) {
                                    3 -> {//coded but inactive
                                        try {
                                            val marker = map_boxMap?.addMarker(
                                                MarkerOptions().position(
                                                    LatLng(
                                                        model.lat?.toDouble()!!,
                                                        model.lng?.toDouble()!!
                                                    )
                                                )
                                                    .title( resources.getString(R.string.coding_inactive)+"\n"+
                                                        resources.getString(R.string.pharmacy)+"  " + model.mpharmacy.name+("- ")
                                                            +resources.getString(R.string.id)+" :"+model.mpharmacy.id+"\n"+
                                                            resources.getString(R.string.mr)+"  "+model.mpharmacy.mmndob.name+("- ")
                                                            +resources.getString(R.string.id)+" :"+model.mpharmacy.mmndob.id+("- ")
                                                            +model.mpharmacy.mmndob.line)
                                                    .icon(
                                                        IconFactory.getInstance(this)
                                                            .fromResource(R.drawable.mapbox_marker_blue)
                                                    )
                                            )
                                        } catch (e: Exception) {
                                        }
                                    }
                                    4 -> {//active
                                        try {
                                            val marker = map_boxMap?.addMarker(
                                                MarkerOptions().position(
                                                    LatLng(
                                                        model.lat?.toDouble()!!,
                                                        model.lng?.toDouble()!!
                                                    )
                                                )
                                                    .title( resources.getString(R.string.action)+"\n"+
                                                        resources.getString(R.string.pharmacy)+"  " + model.mpharmacy.name+("- ")
                                                            +resources.getString(R.string.id)+" :"+model.mpharmacy.id+"\n"+
                                                            resources.getString(R.string.mr)+"  "+model.mpharmacy.mmndob.name+("- ")
                                                            +resources.getString(R.string.id)+" :"+model.mpharmacy.mmndob.id+("- ")
                                                            +model.mpharmacy.mmndob.line)
                                                    .icon(
                                                        IconFactory.getInstance(this)
                                                            .fromResource(R.drawable.mapbox_marker_green)
                                                    )
                                            )
                                        } catch (e: Exception) {
                                        }
                                    }
                                    5 -> {//under review
                                        try {
                                            val marker = map_boxMap?.addMarker(
                                                MarkerOptions().position(
                                                    LatLng(
                                                        model.lat?.toDouble()!!,
                                                        model.lng?.toDouble()!!
                                                    )
                                                )
                                                    .title( resources.getString(R.string.under_review)+"\n"+
                                                        resources.getString(R.string.pharmacy)+"  " + model.mpharmacy.name+("- ")
                                                            +resources.getString(R.string.id)+" :"+model.mpharmacy.id+"\n"+
                                                            resources.getString(R.string.mr)+"  "+model.mpharmacy.mmndob.name+("- ")
                                                            +resources.getString(R.string.id)+" :"+model.mpharmacy.mmndob.id+("- ")
                                                            +model.mpharmacy.mmndob.line)
                                                    .icon(
                                                        IconFactory.getInstance(this)
                                                            .fromResource(R.drawable.mapbox_marker_gray)
                                                    )
                                            )
                                        } catch (e: Exception) {
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
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


//                    binding.inactiveLocation.text = (title + "\n" + result.getString("formatted_address"))

            } else {
                System.out.println(" getLocationFormGoogle " + latLng)

            }

        }, Response.ErrorListener() {})

    queue.add(jsonObjectRequest)


    return address

}

override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
}

override fun onMapReady(mapboxMap: MapboxMap) {
    map_boxMap = mapboxMap
    callback = LocationChangeListeningCallback()
    mapboxMap.setStyle(Style.MAPBOX_STREETS) {
        enableLocationComponent(it)
    }
}

@SuppressLint("MissingPermission")
private fun enableLocationComponent(loadedMapStyle: Style) {
    if (PermissionsManager.areLocationPermissionsGranted(this)) {
        val locationComponentActivationOptions =
            LocationComponentActivationOptions.builder(this, loadedMapStyle)
                .useDefaultLocationEngine(false)
                .build()
        map_boxMap.locationComponent.apply {
            activateLocationComponent(locationComponentActivationOptions)
            isLocationComponentEnabled = true                       // Enable to make component visible
            cameraMode = CameraMode.TRACKING                        // Set the component's camera mode
            renderMode = RenderMode.COMPASS                         // Set the component's render mode
        }
        initLocationEngine()
    } else {
        permissionsManager = PermissionsManager(this)
        permissionsManager.requestLocationPermissions(this)
    }
}

@SuppressLint("MissingPermission")
private fun initLocationEngine() {
    locationEngine = LocationEngineProvider.getBestLocationEngine(this)
    val request = LocationEngineRequest
        .Builder(10000L)
        .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
        .setMaxWaitTime(10000L * 15)
        .build()
    locationEngine.requestLocationUpdates(request, callback, mainLooper)
    locationEngine.getLastLocation(callback)
}

override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
}

private inner class LocationChangeListeningCallback :
    LocationEngineCallback<LocationEngineResult> {

    override fun onSuccess(result: LocationEngineResult?) {
        result?.lastLocation
            ?: return //BECAREFULL HERE, IF NAME LOCATION UPDATE DONT USER -> val resLoc = result.lastLocation ?: return
        if (result.lastLocation != null) {
            val lat = result.lastLocation?.latitude!!
            val lng = result.lastLocation?.longitude!!
            val latLng = LatLng(lat, lng)

            if (result.lastLocation != null) {
                map_boxMap.locationComponent.forceLocationUpdate(result.lastLocation)
                val position = CameraPosition.Builder()
                    .target(latLng)
                    .zoom(13.0) //disable this for not follow zoom
                    .tilt(10.0)
                    .build()
                map_boxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
                viewModel!!.getNearbyPharmacy("$lat,$lng")
//                Toast.makeText(
//                    this@NearbyPharmacyActivity,
//                    "Location update : $latLng",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }

    }

    override fun onFailure(exception: Exception) {}
}

override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
    Toast.makeText(this, "Permission not granted!!", Toast.LENGTH_SHORT).show()
}

override fun onPermissionResult(granted: Boolean) {
    if (granted) {
        map_boxMap.getStyle {
            enableLocationComponent(it)
        }
    } else {
        Toast.makeText(this, "Permission not granted!! app will be EXIT", Toast.LENGTH_LONG)
            .show()
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 3000)
    }
}

}