.package com.devartlab


import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

private const val TAG = "GetMyLocation"

@SuppressLint("MissingPermission")
class GetMyLocation(private val activity: AppCompatActivity) {

    var timer1: Timer? = null
    var locationResult: LocationResult? = null

    val dataManager: DataManager = (activity.application as BaseApplication).dataManager!!

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference: DatabaseReference = database.reference.child("Locations")


    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLocationCallback: LocationCallback? = null

    fun getLocation(context: Context, result: LocationResult?): Boolean {

        locationResult = result
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        val locationRequest = LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 0
        }

        var i = 0
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                Log.d(TAG, "Location data: ${location.latitude},${location.longitude}")
                Log.d(TAG, "Location accuracy: ${location.accuracy}")
                var differenceMillis = System.currentTimeMillis() - location.time;
                var minutes = (differenceMillis / 1000) / 60
                Log.d(TAG, "Location time: $minutes")

                reference.child(dataManager.user.empId.toString())
                    .child("Location")
                    .setValue("${location.latitude},${location.longitude}")

                i++

                if (i >= 2) {
                    if (checkLocation(locationResult.lastLocation)) {
                        setLocation(locationResult.lastLocation)
                    }
                }


            }


        }


        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,
            mLocationCallback!!,
            Looper.myLooper()!!
        )

        timer1 = Timer()
        timer1!!.schedule(SetNullLocation(), 30000)

        return true

    }

    internal inner class SetNullLocation : TimerTask() {
        override fun run() {

            setLocation(null)
        }
    }

    abstract class LocationResult {
        abstract fun gotLocation(location: Location?, msg: String?)
    }

    private fun checkLocation(location: Location?): Boolean {


        return if (BuildConfig.DEBUG) {
            true
        } else {
            if (location != null) {
                val differenceMillis = System.currentTimeMillis() - location.time
                val minutes = (differenceMillis / 1000) / 60
                if (minutes > 3) {
                    false
                } else location.latitude != location.longitude

            } else false
        }

    }

    private fun pushLocation(location: Location?) {

        if (checkLocation(location)) {

            locationResult!!.gotLocation(location, " google ")

            if (location != null) {
                Log.d(TAG, "Location data: ${location.latitude},${location.longitude}")
                Log.d(TAG, "Location accuracy: ${location.accuracy}")
                var differenceMillis = System.currentTimeMillis() - location.time;
                var minutes = (differenceMillis / 1000) / 60
                Log.d(TAG, "Location time: $minutes")

            }


        } else {
            locationResult!!.gotLocation(null, " huawei ")
        }

    }

    private fun setLocation(location: Location?) {

        fusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback!!)
        timer1!!.cancel()
        pushLocation(location)

    }

}