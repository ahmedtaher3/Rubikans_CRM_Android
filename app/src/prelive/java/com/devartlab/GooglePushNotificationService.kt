package com.devartlab


import android.app.Notification
import android.app.Notification.DEFAULT_VIBRATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.auth.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Retrofit


class GooglePushNotificationService : FirebaseMessagingService() {
    lateinit var dataManager: DataManager
    lateinit var myAPI: ApiServices
    var retrofit: Retrofit = RetrofitClient.getInstance()

    override fun onCreate() {
        super.onCreate()
        dataManager = (application as BaseApplication).dataManager!!
        myAPI = retrofit.create(ApiServices::class.java)
    }




    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        dataManager = (getApplication() as BaseApplication).dataManager!!

        Log.d(TAG, "From: ${remoteMessage.from}")
        Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        if (dataManager?.isLogin!!) {
            sendNotification(remoteMessage.data["title"]!!, remoteMessage.data["body"]!!)
        }

    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        if (!token.isNullOrEmpty()) {
            refreshedTokenToServer(token)
        }

    }

    private fun refreshedTokenToServer(token: String) {


        try {
            dataManager.saveHuaweiToken(token)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

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
                .setDefaults(DEFAULT_VIBRATE) //Important for heads-up notification
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

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

    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }


}