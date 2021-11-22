package com.devartlab.ui.main.ui.callmanagement.trade

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.collect.CollectDao
import com.devartlab.data.room.collect.CollectEntity
import com.devartlab.data.room.contract.ContractDao
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.data.room.invoicedetailes.CustomerInvoiceDao
import com.devartlab.data.room.invoicedetailes.CustomerInvoiceEntity
import com.devartlab.data.room.myballance.MyBallanceDao
import com.devartlab.data.room.myballance.MyBallanceEntity
import com.devartlab.data.room.trademaster.TradeMasterDao
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.CustomerInvoiceDashboard
import com.devartlab.model.DevartLabReportsFilterDTO
import com.devartlab.model.EMPloyeeStoreInvoice
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


private const val TAG = "TradeReportsViewModel"
class TradeReportsViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var dataManager: DataManager

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    val progress: MutableLiveData<Int>
    val responseLiveRoom: MutableLiveData<List<TradeMasterEntity>>
    val responseLive: MutableLiveData<java.util.ArrayList<CustomerInvoiceEntity>>
    val responseLiveDashboard: MutableLiveData<java.util.ArrayList<CustomerInvoiceDashboard>>
    val responseLiveStoreInvoice: MutableLiveData<java.util.ArrayList<EMPloyeeStoreInvoice>>
    val responseCollect: MutableLiveData<ResponseModel>
    val productLive: MutableLiveData<java.util.ArrayList<ContractEntity>>
    val myProductLive: MutableLiveData<java.util.ArrayList<MyBallanceEntity>>
    var tradeMasterDao: TradeMasterDao? = null
    var contractDao: ContractDao? = null
    var collectDao: CollectDao? = null
    var myBallanceDao: MyBallanceDao? = null
    var customerInvoiceDao: CustomerInvoiceDao? = null


    init {
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        dataManager = (getApplication<Application>() as BaseApplication).dataManager!!
        progress = MutableLiveData()
        responseLiveStoreInvoice = MutableLiveData()
        responseLiveDashboard = MutableLiveData()
        responseLive = MutableLiveData()
        responseLiveRoom = MutableLiveData()
        responseCollect = MutableLiveData()
        productLive = MutableLiveData()
        myProductLive = MutableLiveData()
        tradeMasterDao = DatabaseClient.getInstance(application)?.appDatabase?.tradeMasterDao()
        contractDao = DatabaseClient.getInstance(application)?.appDatabase?.contractDao()
        collectDao = DatabaseClient.getInstance(application)?.appDatabase?.collectDao()
        myBallanceDao = DatabaseClient.getInstance(application)?.appDatabase?.myBallanceDao()
        customerInvoiceDao = DatabaseClient.getInstance(application)?.appDatabase?.customerInvoiceDao()


    }


    fun getOfflineInvoices() {
        Completable.fromAction {
            responseLiveRoom.postValue(tradeMasterDao?.all)

        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getSalesPurchaseReport(model: DevartLabReportsFilterDTO) {


        if (dataManager.offlineMood) {


            Completable.fromAction { //  responseLive.postValue(collectDao.all)


                responseLive.postValue(customerInvoiceDao?.all as java.util.ArrayList<CustomerInvoiceEntity>?)


            }.subscribeOn(Schedulers.io()).subscribe()
        }
        else {
            val json = Gson().toJsonTree(model).asJsonObject

            System.out.println(" getSalesPurchaseReport " + json)
            progress.postValue(1)

            myAPI?.GetAllSalesPurchaseReport(json)!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {

                        progress.postValue(0)

                        if (data.isSuccesed) {

                            responseLive.postValue(data.data.customerInvoiceDetails)
                            responseLiveDashboard.postValue(data.data.customerInvoiceDashboard)
                            responseLiveStoreInvoice.postValue(data.data.storeInvoice)
                        }
                        else {
                            Toast.makeText(getApplication(), data.rerurnMessage, Toast.LENGTH_SHORT).show()
                        }


                    }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {}
            })

        }
    }

    fun collectMoney(list: ArrayList<CollectEntity>) {


        if (dataManager.offlineMood) {


            Completable.fromAction { //  responseLive.postValue(collectDao.all)

                collectDao?.insertAll(list)

                for (i in list) {

                    for (m in customerInvoiceDao?.all!!) {
                        if (m.invoiceId == i.InvoiceId) {

                            m.totalReminder = m.totalReminder?.minus(i.PaymentValue!!)
                            customerInvoiceDao?.update(m)
                        }
                    }
                }

                progress.postValue(10)

            }.subscribeOn(Schedulers.io()).subscribe()

        }
        else {
            val newlist = ArrayList<CollectEntity>()

            for (m in list) {
                if (m.PaymentValue!! > 0.0) {
                    newlist.add(m)
                }
            }

            val json = Gson().toJsonTree(newlist).asJsonArray
            System.out.println(" getSalesPurchaseReport " + json)
            progress.postValue(1)
            myAPI?.cashCollection(json)!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {


                        progress.postValue(10)
                        responseCollect.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })
        }


    }


    fun getProducts(model: DevartLabReportsFilterDTO) {

        if (dataManager.offlineMood) {
            Completable.fromAction {
                productLive.postValue(contractDao?.all as java.util.ArrayList<ContractEntity>)
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {
            val json = Gson().toJsonTree(model).asJsonObject

            Log.d(TAG, "getProducts: $json")
            progress.postValue(1)
            myAPI?.getProducts(json)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        progress.postValue(0)

                        if (data.isSuccesed) {
                            progress.postValue(0)

                            var list = ArrayList<ContractEntity>()
                            for (m in data.data.itemList) {
                                list.add(ContractEntity(0,
                                                        m.contractId,
                                                        m.cashDisc?.toInt(),
                                                        "",
                                                        0,
                                                        m.itemArName,
                                                        "",
                                                        m.itemPrincipalUnitId,
                                                        m.itemId,
                                                        m.unitArName,
                                                        0,
                                                        m.price,
                                                        0))
                            }
                            productLive.postValue(list)


                        } else {
                            Toast.makeText(getApplication(), data.rerurnMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {}
                })
        }
    }

    fun getAllProducts() {

        progress.postValue(1)
        myAPI?.getAllProducts(1000, 1, "")!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {
                    progress.postValue(0)

                    if (data.isSuccesed) {
                        progress.postValue(0)
                        var list = ArrayList<ContractEntity>()

                        for (m in data.data.itemList) {
                            list.add(ContractEntity(0,
                                                    m.contractId,
                                                    m.cashDisc?.toInt(),
                                                    "",
                                                    0,
                                                    m.itemArName,
                                                    "",
                                                    m.itemPrincipalUnitId,
                                                    m.itemId,
                                                    m.unitArName,
                                                    0,
                                                    m.price,
                                                    0))
                        }
                        productLive.postValue(list)
                    } else {
                        Toast.makeText(getApplication(), data.rerurnMessage, Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                }

                override fun onComplete() {}
            })

    }

    fun getMyProducts(s: ReportsFilterModel) {


        if (dataManager.offlineMood) {
            Completable.fromAction {
                myProductLive.postValue(myBallanceDao?.all as java.util.ArrayList<MyBallanceEntity>)
            }.subscribeOn(Schedulers.io()).subscribe()
        }
        else {

            progress.postValue(1)
            val appraisalBuildsSchema = Gson().toJsonTree(s).asJsonObject
            Log.d(TAG, "getMyProducts: $appraisalBuildsSchema")
            myAPI?.getAllInvnetoryTrxByOption(appraisalBuildsSchema)!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        progress.postValue(0)
                        var list = ArrayList<MyBallanceEntity>()

                        for (m in data.data.vanStoctaking!!) {
                            list.add(MyBallanceEntity(0, 0, 0, "", m.qty, m.itemArName, "", 0, m.itemID, m.unitArName, 0, 0.0, 0))
                        }
                        myProductLive.postValue(list)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

        }

    }
}