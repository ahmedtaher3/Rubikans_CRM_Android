package com.devartlab.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.devartlab.R
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.random.RandomDao
import com.devartlab.data.room.random.RandomEntity
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.ui.auth.splash.SplashActivity
import com.devartlab.ui.main.CallsActivity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class ExampleJobService : JobService(), LocationListener {
    private var jobCancelled = false
    lateinit var locationManager: LocationManager
    var randomDao: RandomDao? = null

    override fun onStartJob(params: JobParameters): Boolean {
        System.out.println(TAG + "Job onStartJob")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        randomDao = DatabaseClient.getInstance(this)?.appDatabase?.randomDao()



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0F, this);



        return true;
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job cancelled before completion")
        System.out.println(TAG + "Job cancelled before completion")
        jobCancelled = true
        return true
    }

    companion object {
        private const val TAG = "ExampleJobService"
    }

    private fun sendNotification(messageTitle: String, messageBody: String) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
                this, System.currentTimeMillis().toInt()/* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT
        )
        val DEFAULT_VIBRATE_PATTERN = longArrayOf(0, 250, 250, 250)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val mBuilder =
                NotificationCompat.Builder(this, "1")
                        .setSmallIcon(R.drawable.devart_logo)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setDefaults(Notification.DEFAULT_VIBRATE) //Important for heads-up notification
                        .setPriority(Notification.PRIORITY_MAX)

        val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val description = "channel_description"
            val importance =
                    NotificationManager.IMPORTANCE_HIGH //Important for heads-up notification

            val channel = NotificationChannel("1", name, importance)
            channel.description = description
            channel.setShowBadge(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val notificationManager = getSystemService(
                    NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }


        val buildNotification = mBuilder.build()
        val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.notify(System.currentTimeMillis().toInt(), buildNotification)
    }

    override fun onLocationChanged(p0: Location) {

        System.out.println(TAG + "onLocationChanged")
        Completable.fromAction {
            randomDao?.insert(RandomEntity(
                    p0?.latitude.toString()
                    , p0?.longitude.toString()
                    , System.currentTimeMillis().toString()
            ))

            locationManager.removeUpdates(this)

        }.subscribeOn(Schedulers.io())
                .subscribe()

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {


    }



}