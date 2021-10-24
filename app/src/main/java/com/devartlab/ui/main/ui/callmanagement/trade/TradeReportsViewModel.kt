package com.devartlab.ui.main.ui.callmanagement.trade

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.authority.AuthorityDao
import com.devartlab.data.room.authority.AuthorityEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.GoogleRequestResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.room.contract.ContractDao
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.trademaster.TradeMasterDao
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.data.room.values.ValuesDao
import com.devartlab.model.DevartLabReportsFilterDTO
import com.devartlab.model.InvTrxSalesPurchasePaymentDetailsModel
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Completable
import okhttp3.ResponseBody
import retrofit2.Retrofit


private const val TAG = "TradeReportsViewModel"
class TradeReportsViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var dataManager: DataManager

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    val progress: MutableLiveData<Int>
    val responseLiveRoom: MutableLiveData<List<TradeMasterEntity>>
    val responseLive: MutableLiveData<ResponseModel>
    val responseCollect: MutableLiveData<ResponseModel>
    val productLive: MutableLiveData<java.util.ArrayList<ContractEntity>>
    val myProductLive: MutableLiveData<ResponseModel>
    var tradeMasterDao: TradeMasterDao? = null
    var contractDao: ContractDao? = null


    init {
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        dataManager = (getApplication<Application>() as BaseApplication).dataManager!!
        progress = MutableLiveData()
        responseLive = MutableLiveData()
        responseLiveRoom = MutableLiveData()
        responseCollect = MutableLiveData()
        productLive = MutableLiveData()
        myProductLive = MutableLiveData()
        tradeMasterDao = DatabaseClient.getInstance(application)?.appDatabase?.tradeMasterDao()
        contractDao = DatabaseClient.getInstance(application)?.appDatabase?.contractDao()


    }


    fun getOfflineInvoices() {
        Completable.fromAction {
            responseLiveRoom.postValue(tradeMasterDao?.all)

        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getSalesPurchaseReport(model: DevartLabReportsFilterDTO) {
        val json = Gson().toJsonTree(model).asJsonObject

        System.out.println(" getSalesPurchaseReport " + json)
        progress.postValue(1)

        myAPI?.GetAllSalesPurchaseReport(json)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {


                    progress.postValue(0)
                    responseLive.postValue(data)


                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {}
            })

    }

    fun collectMoney(list: ArrayList<InvTrxSalesPurchasePaymentDetailsModel>) {
        val newlist = ArrayList<InvTrxSalesPurchasePaymentDetailsModel>()

        for (m in list) {
            if (m.PaymentValue!! > 0.0) {
                newlist.add(m)
            }
        }

        val json = Gson().toJsonTree(newlist).asJsonArray

        System.out.println(" getSalesPurchaseReport " + json)
        progress.postValue(1)

        myAPI?.cashCollection(json)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {


                    progress.postValue(0)
                    responseCollect.postValue(data)


                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {}
            })

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
                            productLive.postValue(data.data.contractList)


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
                        productLive.postValue(data.data.itemList)
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
        progress.postValue(1)

        val appraisalBuildsSchema = Gson().toJsonTree(s).asJsonObject
        Log.d(TAG, "getMyProducts: $appraisalBuildsSchema")
        myAPI?.getAllInvnetoryTrxByOption(appraisalBuildsSchema)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {
                    progress.postValue(0)
                    myProductLive.postValue(data)
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