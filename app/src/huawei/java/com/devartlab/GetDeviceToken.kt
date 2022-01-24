package com.devartlab

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException

private const val TAG = "GetDeviceToken"

class GetDeviceToken(private val activity: AppCompatActivity) {
    lateinit var dataManager: DataManager

    fun getToken(result: TokenResult?) {

        dataManager = (activity.application as BaseApplication).dataManager!!

        object : Thread() {
            override fun run() {
                try {
                    // read from agconnect-services.json
                    val appId = "105326629"
                    val token = HmsInstanceId.getInstance(activity).getToken(appId, "HCM")
                    Log.i(TAG, "get token:$token")
                    if (token.isNullOrBlank()) {
                        if (dataManager.huaweiToken.isNullOrBlank()) {
                            result?.success("")
                        } else {
                            result?.success(dataManager.huaweiToken)
                        }
                    } else {
                        result?.success(token)
                    }


                } catch (e: ApiException) {
                    Log.d(TAG, "get token failed, $e")
                    result?.failure("An error occurred, please try again later")
                }
            }
        }.start()

    }


    abstract class TokenResult {
        abstract fun success(token: String?)
        abstract fun failure(msg: String?)
    }

}