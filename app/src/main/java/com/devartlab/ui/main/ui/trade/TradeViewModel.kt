package com.devartlab.ui.main.ui.trade

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.random.RandomDao
import com.devartlab.data.room.tradedetails.TradeDetailsDao
import com.devartlab.data.room.tradedetails.TradeDetailsEntity
import com.devartlab.data.room.trademaster.TradeMasterDao
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.DevartLabReportsFilterDTO
import com.devartlab.model.GoogleRequestResponse
import com.devartlab.ui.main.ui.callmanagement.inventory.ReportsFilterModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Retrofit


private const val TAG = "TradeViewModel"

class TradeViewModel(application: Application) : AndroidViewModel(application) {

    val progress: MutableLiveData<Int>
    val progressInsertPlace: MutableLiveData<Int>
    var myAPI: ApiServices? = null
    var myAPIGoogle: ApiServicesGoogle? = null
    var dataManager: DataManager? = null
    var retrofit: Retrofit? = null
    var retrofitGoogle: Retrofit? = null


    val productLive: MutableLiveData<ResponseModel>
    val myProductLive: MutableLiveData<ResponseModel>
    val typsLive: MutableLiveData<ResponseModel>


    val governmentsLive: MutableLiveData<GoogleRequestResponse>
    val areaLive: MutableLiveData<GoogleRequestResponse>
    val cityLive: MutableLiveData<GoogleRequestResponse>

    val tradeDayLive: MutableLiveData<GoogleRequestResponse>
    val customerLive: MutableLiveData<GoogleRequestResponse>
    val billLive: MutableLiveData<GoogleRequestResponse>

    var tradeMasterDao: TradeMasterDao? = null
    var tradeDetailsDao: TradeDetailsDao? = null

    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        progress = MutableLiveData()
        progressInsertPlace = MutableLiveData()
        myProductLive = MutableLiveData()
        retrofit = RetrofitClient.getInstance()
        retrofitGoogle = RetrofitClient.getInstanceGoogleSheet()
        myAPI = retrofit!!.create(ApiServices::class.java)
        myAPIGoogle = retrofitGoogle!!.create(ApiServicesGoogle::class.java)

        governmentsLive = MutableLiveData<GoogleRequestResponse>()
        areaLive = MutableLiveData<GoogleRequestResponse>()
        cityLive = MutableLiveData<GoogleRequestResponse>()
        tradeDayLive = MutableLiveData<GoogleRequestResponse>()
        productLive = MutableLiveData<ResponseModel>()
        typsLive = MutableLiveData()
        customerLive = MutableLiveData<GoogleRequestResponse>()
        billLive = MutableLiveData<GoogleRequestResponse>()

        tradeMasterDao = DatabaseClient.getInstance(application)?.appDatabase?.tradeMasterDao()
        tradeDetailsDao = DatabaseClient.getInstance(application)?.appDatabase?.tradeDetailsDao()


    }

    fun getTyps() {


        progress.postValue(1)
        myAPI?.getAllType(true)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {
                    progress.postValue(0)
                    typsLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }

    fun getInventoryTyps(obj: JsonObject) {


        progress.postValue(1)
        myAPI?.getDataFromAPI(obj)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {
                    progress.postValue(0)
                    typsLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }

    fun getCities(
        action: String?,
        governmentId: String,
        cityId: String,
        areaId: String
    ) {
    }


    fun getByEmpId() {


        progress.postValue(1)
        myAPIGoogle?.tradeGetCustomers(
            "Customer",
            "getByEmpId",
            dataManager?.user?.empId.toString(),
            ""
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GoogleRequestResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: GoogleRequestResponse) {
                    progress.postValue(0)
                    customerLive.postValue(data)
                    System.out.println("  aaaaa  " + data.toString())

                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    System.out.println(e.message)
                    System.out.println("  aaaaa  " + e.message)

                }

                override fun onComplete() {}
            })

    }


    fun getByAreaId(id: String) {


        progress.postValue(1)
        myAPIGoogle?.tradeGetCustomers("Customer", "getByAreaId", "0", id)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GoogleRequestResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: GoogleRequestResponse) {
                    progress.postValue(0)
                    customerLive.postValue(data)
                    System.out.println("  aaaaa  " + data.toString())
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    System.out.println("  aaaaa  " + e.message)
                }

                override fun onComplete() {}
            })

    }


    fun getGovernments() {
        progress.postValue(1)
        myAPIGoogle?.trade("Government", "getAll", "", "", "", "", "", "", "", "", "", "", "", "")!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GoogleRequestResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: GoogleRequestResponse) {
                    progress.postValue(0)
                    governmentsLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }


    fun getProducts(model: DevartLabReportsFilterDTO) {
        val json = Gson().toJsonTree(model).asJsonObject
        progress.postValue(1)
        myAPI?.getProducts(json)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {
                    progress.postValue(0)
                    productLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

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
                    productLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }

    fun getMyProducts(s: ReportsFilterModel) {
        progress.postValue(1)

        val appraisalBuildsSchema = Gson().toJsonTree(s).asJsonObject
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


    fun InsertAndUpdate(x: JsonObject) {

        Log.d(TAG, "InsertAndUpdate: $x")
        progress.postValue(1)
        myAPI?.insertAndUpdate(x)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseBody> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseBody) {
                    progress.postValue(10)
                    System.out.println(" InsertAndUpdate " + data.string())

                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }

    fun insertTradeOffline(x: TradeMasterEntity , list : ArrayList<TradeDetailsEntity>)  {

        Completable.fromAction {


            for (m in list!!)
            {
                tradeDetailsDao?.insert(m)
            }
            tradeMasterDao?.insert(x)

            progress.postValue(9)

        }.subscribeOn(Schedulers.io())
            .subscribe()

    }


    fun getBills() {

        Toast.makeText(getApplication(), dataManager?.user?.empId.toString(), Toast.LENGTH_LONG)
            .show()

        progress.postValue(1)
        myAPIGoogle?.trade(
            "Bill",
            "getAll",
            "",
            "",
            "",
            "",
            dataManager?.user?.empId.toString(),
            "",
            "",
            "",
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
                    progress.postValue(0)
                    billLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    println(e.message)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }


    fun insert(
        action: String?,
        cityId: String,
        areaId: String,
        customerId: String,
        customerName: String,
        array: String,
        discount: String,
        lastPrice: String,
        totalPrice: String,
        notes: String
    ) {
    }


    fun insertPlace(
        sheet: String?,
        action: String?,
        governmentId: String,
        cityId: String,
        governmentName: String,
        cityName: String,
        areaName: String
    ) {
    }


    fun checkDay() {
        progress.postValue(1)
        myAPIGoogle?.tradeReport(
            "Oman Finger print",
            "getDay",
            dataManager?.user?.empId.toString(),
            "",
            "",
            "",
            "",
            "",
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
                    progress.postValue(0)
                    tradeDayLive.postValue(data)
                    System.out.println(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    System.out.println(e.message)
                }

                override fun onComplete() {}
            })

    }


    fun getEmpDays(id: String?) {}


    fun startDay(startAt: String?, lat: String?, long: String?, distance: String?) {


    }


    fun endDay(startAt: String?, lat: String?, long: String?, code: String?, distance: String?) {}


}