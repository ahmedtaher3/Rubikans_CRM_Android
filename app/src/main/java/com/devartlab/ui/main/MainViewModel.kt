package com.devartlab.ui.main

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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // axasasasas

    val arrangedResponseLive: MutableLiveData<List<ArrangedEntity>>
    val randomLive: MutableLiveData<List<RandomEntity>>
    val progress: MutableLiveData<Int>
    val progressGoogle: MutableLiveData<Int>
    var planModels: LiveData<List<PlanEntity>>
    var allSlides: LiveData<List<SlideEntity>>
    val responseLiveRequests: MutableLiveData<GoogleRequestResponse>
    val syncOfflineData: MutableLiveData<ResponseModel>
    val allProducts: MutableLiveData<List<ProductEntity>>


    var myAPI: ApiServices? = null
    var myAPI2: ApiServicesGoogle? = null
    var dataManager: DataManager
    var retrofit: Retrofit? = null
    var retrofit2: Retrofit? = null


    var arrangedDao: ArrangedDao
    var massagesDao: MassagesDao
    var productDao: ProductDao
    var valuesDao: ValuesDao
    var slideDao: SlideDao
    var randomDao: RandomDao
    var startPointDao: StartPointDao
    var authorityDao: AuthorityDao
    var planDao: PlanDao
    var purchaseTypeDao: PurchaseTypeDao
    var activityDao: ActivityDao
    var listDao: ListDao
    var listTypeDao: ListTypesDao
    var contractDao: ContractDao
    var myBallanceDao: MyBallanceDao
    var customerInvoiceDao: CustomerInvoiceDao
    var filterDataDao: FilterDataDao


    var valuesRepository: ValuesRepository? = null


    var version: String = ""
    public val userName = ObservableField<String>()
    public val versionName = ObservableField<String>()
    public val onlineText = ObservableField<String>()
    public val onlineBoolean = ObservableField<Boolean>()
    public val modifyMac = ObservableField<String>()


    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        retrofit2 = RetrofitClient.getInstanceGoogleSheet()
        myAPI = retrofit!!.create(ApiServices::class.java)
        myAPI2 = retrofit2!!.create(ApiServicesGoogle::class.java)



        progress = MutableLiveData()
        progressGoogle = MutableLiveData()
        responseLiveRequests = MutableLiveData()
        syncOfflineData = MutableLiveData()
        allProducts = MediatorLiveData()
        allSlides = MediatorLiveData()
        planModels = MediatorLiveData()
        randomLive = MediatorLiveData()
        arrangedResponseLive = MutableLiveData()



        planDao = DatabaseClient.getInstance(application)?.appDatabase?.planDao()!!
        authorityDao = DatabaseClient.getInstance(application)?.appDatabase?.authorityDao()!!
        arrangedDao = DatabaseClient.getInstance(application)?.appDatabase?.arrangedDao()!!
        startPointDao = DatabaseClient.getInstance(application)?.appDatabase?.startPointDao()!!
        massagesDao = DatabaseClient.getInstance(application)?.appDatabase?.massagesDao()!!
        productDao = DatabaseClient.getInstance(application)?.appDatabase?.productDao()!!
        slideDao = DatabaseClient.getInstance(application)?.appDatabase?.slideDao()!!
        valuesDao = DatabaseClient.getInstance(application)?.appDatabase?.valuesDao()!!
        listDao = DatabaseClient.getInstance(application)?.appDatabase?.listDao()!!
        purchaseTypeDao = DatabaseClient.getInstance(application)?.appDatabase?.purchaseTypeDao()!!
        activityDao = DatabaseClient.getInstance(application)?.appDatabase?.activityDao()!!
        listTypeDao = DatabaseClient.getInstance(application)?.appDatabase?.listTypesDao()!!
        contractDao = DatabaseClient.getInstance(application)?.appDatabase?.contractDao()!!
        myBallanceDao = DatabaseClient.getInstance(application)?.appDatabase?.myBallanceDao()!!
        customerInvoiceDao = DatabaseClient.getInstance(application)?.appDatabase?.customerInvoiceDao()!!
        filterDataDao = DatabaseClient.getInstance(application)?.appDatabase?.filterDataDao()!!



        randomDao = DatabaseClient.getInstance(application)?.appDatabase?.randomDao()!!


        if (dataManager.offlineMood) {
            onlineText.set("Offline")
            onlineBoolean.set(false)
        }
        else {
            onlineText.set("Online")
            onlineBoolean.set(true)
        }

        try {
            val pInfo: PackageInfo =
                application.getPackageManager().getPackageInfo(application.getPackageName(), 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        userName.set(dataManager?.user?.nameAr)
        modifyMac.set(dataManager?.user?.title)
        versionName.set(version)

        if (valuesRepository == null) {
            valuesRepository = ValuesRepository.getInstance(valuesDao)
        }


    }


    fun updatePlan(plan: PlanEntity?) {


        Completable.fromAction {

            planDao?.update(plan)
            dataManager?.saveSyncAble(false)

            val allConfirmed = planDao?.allConfirmed
            val json = Gson().toJson(allConfirmed)
            CommonUtilities.writeToSDFile(json)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()


    }

    fun getAllPending(action: String, empId: String) {


        myAPI2?.googleSheetRequest(
            "All",
            action,
            "",
            empId,
            "",
            "",
            "",
            "",
            "",
            dataManager.user.empId.toString(),
            "",
            "",
            "",
            ""
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GoogleRequestResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: GoogleRequestResponse) {
                    responseLiveRequests.postValue(data)

                }

                override fun onError(e: Throwable) {
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }

    fun syncProducts() {

        progress.postValue(1)
        myAPI?.syncProducts(dataManager?.user?.accId!!)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProductTable> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(body: ProductTable) {


                    Completable.fromAction {
                        productDao?.deleteTable()
                        for (product in body.table) {
                            val productEntity = ProductEntity(
                                product.itemId,
                                product.itemName,
                                product.itemImageUrl
                            )

                            productDao?.insert(productEntity)
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()



                    Completable.fromAction {
                        massagesDao?.deleteTable()
                        for (massage in body.table1) {
                            val massageEntity = MassageEntity(
                                massage.itemId,
                                massage.messageId,
                                massage.messageDescription,
                                massage.messageLogoUrl
                            )

                            massagesDao?.insert(massageEntity)
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()




                    Completable.fromAction {
                        slideDao?.deleteTable()
                        for (massageSlide in body.table2) {

                            val massageSlideEntity = SlideEntity(
                                massageSlide.itemId,
                                massageSlide.messageId,
                                massageSlide.messageDetId,
                                massageSlide.slideName,
                                massageSlide.slideUrl,
                                "",
                                false
                            )


                            slideDao?.insert(massageSlideEntity)
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()

                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                }

                override fun onComplete() {
                    progress.postValue(2)
                }
            })

    }

    fun syncOfflineData() {

        progress.postValue(1)
        myAPI?.syncOfflineData(dataManager.user.accId, dataManager.user.storeId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(body: ResponseModel) {
                    progress.postValue(0)
                    syncOfflineData.postValue(body)

                    if (body.isSuccesed) {

                        Completable.fromAction {
                            listDao.deleteTable()
                            listDao?.insertAll(body.data.customerList)
                            activityDao.deleteTable()
                            activityDao?.insertAll(body.data.activityLIst)
                            purchaseTypeDao.deleteTable()
                            purchaseTypeDao?.insertAll(body.data.salePurchaseType)
                            listTypeDao.deleteTable()
                            listTypeDao?.insertAll(body.data.listTypesEntity)
                            contractDao.deleteTable()
                            contractDao?.insertAll(body.data.contractList2)
                            myBallanceDao.deleteTable()
                            myBallanceDao?.insertAll(body.data.storeBallance)
                            customerInvoiceDao.deleteTable()
                            customerInvoiceDao?.insertAll(body.data.CustomerInvoice)

                            filterDataDao.deleteTable()
                            for (i in body.data.classList) {
                                i.parentName = "class"
                                filterDataDao.insert(i)
                            }
                            for (i in body.data.specialityLIst) {
                                i.parentName = "speciality"
                                filterDataDao.insert(i)
                            }
                            for (i in body.data.brickList) {
                                i.parentName = "brick"
                                filterDataDao.insert(i)
                            }
                            for (i in body.data.territoryList) {
                                i.parentName = "territory"
                                filterDataDao.insert(i)
                            }

                        }.subscribeOn(Schedulers.io()).subscribe()

                               } else {
                                   Toast.makeText(getApplication(), body.rerurnMessage, Toast.LENGTH_SHORT)
                                       .show()
                    }


                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                }

                override fun onComplete() {
                    progress.postValue(2)
                }
            })

    }

    public fun getAll() {

        Completable.fromAction {

            allProducts.postValue(productDao?.getAll2())

        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getAllArranged(id: Int) {


        Completable.fromAction {

            arrangedResponseLive.postValue(arrangedDao?.getAll(id))

        }.subscribeOn(Schedulers.io())
            .subscribe()


    }

    fun insertRandom(lat: String, lng: String, time: String) {


        Completable.fromAction {

            randomDao?.insert(RandomEntity(lat, lng, time))

        }.subscribeOn(Schedulers.io())
            .subscribe()


    }

    fun deleteAllRoom(activity: MainActivity) {


        Completable.fromAction {

            planDao?.deleteTable()
            randomDao?.deleteTable()
            startPointDao?.deleteTable()
            valuesDao?.deleteAll()
            startPointDao?.deleteTable()

            dataManager.clear()
            val intentLogout = Intent(activity, LoginActivity::class.java)
            intentLogout.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intentLogout)
            activity.finish()


        }.subscribeOn(Schedulers.io())
            .subscribe()


    }

}