package com.devartlab.utils

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.devartlab.base.BaseApplication
import java.lang.reflect.Method


private const val TAG = "LocationUtils"

object LocationUtils {

    private fun showGPSDisabledAlertToUser(context: AppCompatActivity) {

        try {
            ProgressLoading.dismiss()
        } catch (e: java.lang.Exception) {
        }

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton("Goto Settings Page To Enable GPS",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, id: Int) {
                        val callGPSSettingIntent = Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        )
                        context.startActivity(callGPSSettingIntent)
                    }
                })
        alertDialogBuilder.setNegativeButton("Cancel",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    dialog.cancel()
                }
            })
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    private fun showDataDisabledAlertToUser(context: AppCompatActivity) {

        try {
            ProgressLoading.dismiss()
        } catch (e: java.lang.Exception) {
        }

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage("Mobile data is disabled in your device. Would you like to enable it?")
            .setCancelable(false)

        alertDialogBuilder.setNegativeButton("Ok",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    dialog.cancel()
                }
            })
        val alert = alertDialogBuilder.create()
        alert.show()
    }


/*    fun checkStoragePermission(context: AppCompatActivity ): Boolean {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            return if (Environment.isExternalStorageManager()) {
                checkLocationPermission(context)
            } else {
                requestPermission(context )
                false
            }

        } else {
            val result: Int = ActivityCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE)
            val result1: Int = ActivityCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE)
            val bool =
                result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED

            return if (bool) {
                checkLocationPermission(context)
            } else {
                requestPermission(context )
                false
            }
        }
    }

    private fun requestPermission(context: AppCompatActivity) {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", context.packageName))


                startActivityForResult(context, intent, 2296, null)
            } catch (e: java.lang.Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(context, intent, 2296, null)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                context,
                arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
                2296
            )
        }
    }*/


    fun checkLocationPermission(context: AppCompatActivity): Boolean {


        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                android.app.AlertDialog.Builder(context)
                    .setTitle("permission denied")
                    .setMessage("ask for permission again")
                    .setPositiveButton("ok") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            context, arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ), 22
                        )
                    }
                    .setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
                    .create().show()
            } else {
                ActivityCompat.requestPermissions(
                    context, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 22
                )
            }

            return false

        } else {

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                if (checkMobileData(context)) {


                    return true
                } else {
                    showDataDisabledAlertToUser(context);
                    return false
                }

            } else {
                showGPSDisabledAlertToUser(context);
                return false
            }


        }


    }


    fun checkMobileData(context: AppCompatActivity): Boolean {
        var dataManager = (context.application as BaseApplication).dataManager!!

        if (dataManager.mobileData) {
            var mobileDataEnabled = false // Assume disabled

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            try {
                val cmClass = Class.forName(cm.javaClass.name)
                val method: Method = cmClass.getDeclaredMethod("getMobileDataEnabled")
                method.setAccessible(true) // Make the method callable
                // get the setting for "mobile data"
                mobileDataEnabled = method.invoke(cm) as Boolean
            } catch (e: Exception) {
                // Some problem accessible private API
                // TODO do whatever error handling you want here
            }


            return mobileDataEnabled
        } else
            return true


    }


}