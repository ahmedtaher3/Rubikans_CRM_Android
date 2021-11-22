package com.devartlab.ui.main.ui.devartlink

import android.app.Application
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.activity.ActivityDao
import com.devartlab.data.room.arranged.ArrangedDao
import com.devartlab.data.room.arranged.ArrangedEntity
import com.devartlab.data.room.authority.AuthorityDao
import com.devartlab.data.room.contract.ContractDao
import com.devartlab.data.room.filterdata.FilterDataDao
import com.devartlab.data.room.invoicedetailes.CustomerInvoiceDao
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.listtypes.ListTypesDao
import com.devartlab.data.room.massages.MassageEntity
import com.devartlab.data.room.massages.MassagesDao
import com.devartlab.data.room.myballance.MyBallanceDao
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.poduct.ProductDao
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.room.purchasetype.PurchaseTypeDao
import com.devartlab.data.room.random.RandomDao
import com.devartlab.data.room.random.RandomEntity
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.data.room.values.ValuesDao
import com.devartlab.data.shared.DataManager
import com.devartlab.data.source.values.ValuesRepository
import com.devartlab.model.GoogleRequestResponse
import com.devartlab.model.ProductTable
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.utils.CommonUtilities
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class DevartLinkViewModel(application: Application) : AndroidViewModel(application) {




    var myAPI: ApiServices? = null
    var myAPI2: ApiServicesGoogle? = null
    var dataManager: DataManager
    var retrofit: Retrofit? = null
    var retrofit2: Retrofit? = null



    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        retrofit2 = RetrofitClient.getInstanceGoogleSheet()
        myAPI = retrofit!!.create(ApiServices::class.java)
        myAPI2 = retrofit2!!.create(ApiServicesGoogle::class.java)




    }



}