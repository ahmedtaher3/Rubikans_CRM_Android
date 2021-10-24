package com.devartlab.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.util.*

@SuppressLint("MissingPermission")
class MyLocation(private val activity: AppCompatActivity, private val gpsOnly: Boolean) {


    var timer1: Timer? = null
    var lm: LocationManager? = null
    var locationResult: LocationResult? = null
    var gps_enabled = false
    var network_enabled = false
    var locationListenerGps: LocationListener? = null
    var locationListenerNetwork: LocationListener? = null


    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var mLocationRequest: LocationRequest
    private var mLocationCallback: LocationCallback? = null


    private var fusedLocationProviderClientHuawei: com.huawei.hms.location.FusedLocationProviderClient? =
        null
    lateinit var mLocationRequestHuawei: com.huawei.hms.location.LocationRequest
    private var mLocationCallbackHuawei: com.huawei.hms.location.LocationCallback? = null


    var gotLocation = false


    fun getLocation(context: Context, result: LocationResult?): Boolean {


        if (!gpsOnly) {
            //I use LocationResult callback class to pass location value from MyLocation to user code.
            locationResult = result
            if (lm == null) lm =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (locationListenerGps == null) locationListenerGps = object : LocationListener {
                override fun onLocationChanged(location: Location) {


                    setLocation(location, "Gps")


                }

                override fun onProviderDisabled(provider: String) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            }
            if (locationListenerNetwork == null) locationListenerNetwork =
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {

                        setLocation(location, "Network")

                    }

                    override fun onProviderDisabled(provider: String) {}
                    override fun onProviderEnabled(provider: String) {}
                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                }

            //exceptions will be thrown if provider is not permitted.
            try {
                gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (ex: Exception) {
            }
            try {
                network_enabled = lm!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (ex: Exception) {
            }

            //don't start listeners if no provider is enabled
            if (!gps_enabled && !network_enabled) return false
            if (gps_enabled) lm!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListenerGps!!
            )
            if (network_enabled) lm!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0f,
                locationListenerNetwork!!
            )
            timer1 = Timer()
            timer1!!.schedule(GetLastLocation(), 30000)


            if (CommonUtilities.isGooglePlayServicesAvailable(activity)) {

                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(activity)
                mLocationRequest = LocationRequest()
                mLocationRequest.interval = 0
                mLocationRequest.fastestInterval = 0
                mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                mLocationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                        super.onLocationResult(locationResult)


                        setLocation(locationResult.lastLocation, "Google")


                    }


                }


                fusedLocationProviderClient!!.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, Looper.myLooper()
                )

            }

            if (CommonUtilities.isHuaweiServicesAvailable(activity)) {
                fusedLocationProviderClientHuawei =
                    com.huawei.hms.location.LocationServices.getFusedLocationProviderClient(activity);

                mLocationRequestHuawei = com.huawei.hms.location.LocationRequest()
                mLocationRequestHuawei.setInterval(0)
                mLocationRequestHuawei.setFastestInterval(0)
                mLocationRequestHuawei.setPriority(com.huawei.hms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)

                mLocationCallbackHuawei = object : com.huawei.hms.location.LocationCallback() {
                    override fun onLocationResult(locationResult: com.huawei.hms.location.LocationResult?) {
                        if (locationResult != null) {

                            setLocation(locationResult.lastLocation, "Huawei")

                        }
                    }
                }

                fusedLocationProviderClientHuawei!!.requestLocationUpdates(
                    mLocationRequestHuawei,
                    mLocationCallbackHuawei, Looper.myLooper()
                )

            }


            return true
        } else {
            //I use LocationResult callback class to pass location value from MyLocation to user code.
            locationResult = result
            if (lm == null) lm =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (locationListenerGps == null) locationListenerGps = object : LocationListener {
                override fun onLocationChanged(location: Location) {


                    setLocation(location, "Gps")


                }

                override fun onProviderDisabled(provider: String) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            }

            //exceptions will be thrown if provider is not permitted.
            try {
                gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (ex: Exception) {
            }


            //don't start listeners if no provider is enabled
            if (!gps_enabled) return false
            if (gps_enabled) lm!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListenerGps!!
            )

            timer1 = Timer()
            timer1!!.schedule(SetNullLocation(), 30000)


            return true
        }


    }

    internal inner class SetNullLocation : TimerTask() {
        override fun run() {


            setLocation(null, "")

        }
    }

    internal inner class GetLastLocation : TimerTask() {
        override fun run() {


            var net_loc: Location? = null
            var gps_loc: Location? = null


            if (gps_enabled) gps_loc = lm!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (network_enabled) net_loc =
                lm!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            //if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.time > net_loc.time) {
                    if ((System.currentTimeMillis() - gps_loc.time) < 300000) {
                        setLocation(gps_loc, "Last GPS")
                    } else {
                        setLocation(null, "False GPS")
                    }
                } else {
                    if ((System.currentTimeMillis() - net_loc.time) < 300000) {
                        setLocation(gps_loc, "Last Network")
                    } else {
                        setLocation(null, "False Network")
                    }
                }
                return
            }
            if (gps_loc != null) {
                if ((System.currentTimeMillis() - net_loc?.time!!) < 300000) {
                    setLocation(gps_loc, "Last GPS")
                } else {
                    setLocation(null, "False GPS")
                }
                return
            }
            if (net_loc != null) {
                if ((System.currentTimeMillis() - net_loc?.time!!) < 300000) {
                    setLocation(net_loc, "Last Network")
                } else {
                    setLocation(null, "False Network")
                }
                return
            }

            setLocation(null, "")
        }
    }

    abstract class LocationResult {
        abstract fun gotLocation(location: Location?, type: String?, msg: String?)
    }

    private fun checkLocation(location: Location?, type: String): Boolean {
        if (
            location != null
        ) {
            return if (location.latitude < 2 || location.longitude < 2) {
                false
            } else location.latitude != location.longitude
        } else return true

    }

    private fun pushLocation(location: Location?, type: String) {
        if (checkLocation(location, type)) {
            locationResult!!.gotLocation(
                location,
                type,
                location?.accuracy.toString() + "  " + type
            )

            if (location != null) {

                println(
                    " location data  accuracy =  " + location.accuracy + type + "\n" +
                            location!!.latitude.toString() + "," + location!!.longitude.toString() + "\n" +
                            (System.currentTimeMillis() - location!!.time).toString() + "\n" +
                            location!!.accuracy.toString()
                )
            }
        }
    }

    private fun setLocation(location: Location?, type: String) {
        if (!gotLocation) {


            timer1!!.cancel()
            pushLocation(location, type)
            try {
                lm!!.removeUpdates(locationListenerGps!!)

            } catch (e: java.lang.Exception) {
            }
            try {
                lm!!.removeUpdates(locationListenerNetwork!!)

            } catch (e: java.lang.Exception) {
            }

            locationListenerGps = null
            locationListenerNetwork = null
            if (fusedLocationProviderClient != null && mLocationCallback != null) {
                try {
                    fusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)

                } catch (e: java.lang.Exception) {
                }
            }
            if (fusedLocationProviderClientHuawei != null && mLocationCallbackHuawei != null) {
                try {
                    fusedLocationProviderClientHuawei!!.removeLocationUpdates(
                        mLocationCallbackHuawei
                    )

                } catch (e: java.lang.Exception) {
                }
            }



            gotLocation = true
        }

    }

}