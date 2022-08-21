package com.devartlab

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

private const val TAG = "GetDeviceToken"

class GetDeviceToken(private val activity: AppCompatActivity) {

    fun getToken(result: TokenResult?) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {

                try {
                    val token = it.result.toString()
                    Log.d(TAG, "getToken: $token")
                    result?.success(token)
                } catch (e: Exception) {
                    result?.success("")
                }

            } else {
                result?.failure("An error occurred, please try again later")
            }
        }

    }


    abstract class TokenResult {
        abstract fun success(token: String?)
        abstract fun failure(msg: String?)
    }

}