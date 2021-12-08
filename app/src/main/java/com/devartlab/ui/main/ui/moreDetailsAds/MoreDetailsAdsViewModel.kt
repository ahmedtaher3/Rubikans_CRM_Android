package com.devartlab.ui.main.ui.moreDetailsAds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager

class MoreDetailsAdsViewModel (application: Application) : AndroidViewModel(application) {
    var dataManager: DataManager
    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}