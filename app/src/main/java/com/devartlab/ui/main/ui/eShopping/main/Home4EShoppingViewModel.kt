package com.devartlab.ui.main.ui.eShopping.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager

class Home4EShoppingViewModel (application: Application) : AndroidViewModel(application) {

    var dataManager: DataManager
    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}