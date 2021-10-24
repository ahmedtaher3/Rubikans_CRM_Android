package com.devartlab.base;
import android.content.res.Configuration
import androidx.multidex.MultiDexApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.data.shared.SharedPrefsHelper
import com.devartlab.utils.LocaleUtils
import com.mazenrashed.printooth.Printooth


class BaseApplication : MultiDexApplication() {
    var dataManager: DataManager? = null
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleUtils.updateConfig(this, newConfig)
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPrefsHelper = SharedPrefsHelper(applicationContext)
        dataManager = DataManager(sharedPrefsHelper)
        Printooth.init(this)
    }
}