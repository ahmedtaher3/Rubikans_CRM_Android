package com.devartlab.ui.main.ui.callmanagement.inventory

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.ui.trade.InventoryTrxWarehouseTransActionDetailsModel
import com.devartlab.ui.trade.InventoryTrxWarehouseTransActionModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

private const val TAG = "InventoryViewModel"

class InventoryViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var dataManager: DataManager

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    val progress: MutableLiveData<Int>
    val typsLive: MutableLiveData<ResponseModel>
    val responseLive: MutableLiveData<ResponseModel>
    val responseLiveInsert: MutableLiveData<ResponseModel>
    lateinit var retroInstance: ApiServices

    lateinit var recyclerListData: MutableLiveData<ResponseModel>


    init {
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        dataManager = (getApplication<Application>() as BaseApplication).dataManager!!

        retroInstance = RetrofitClient(dataManager!!).instance!!.create(ApiServices::class.java)



        progress = MutableLiveData()
        typsLive = MutableLiveData()
        responseLive = MutableLiveData()
        responseLiveInsert = MutableLiveData()

        recyclerListData = MutableLiveData()

    }


    fun getStoresList() {
        progress.postValue(1)

        val call = retroInstance.getStoresDataFromAPI()
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                progress.postValue(0)
                when (response.code()) {
                    200 -> {
                        if (response.body()?.isSuccesed!!) {

                            responseLive.postValue(response.body())
                        } else {
                            Toast.makeText(
                                getApplication(),
                                response.body()?.rerurnMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    else -> {


                        Toast.makeText(
                            getApplication(),
                            "ERROR ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }


            }
        })
    }


    fun inventoryInsertAndUpdate(requestObject: InventoryTrxWarehouseTransActionModel) {
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonObject
        progress.postValue(1)

        val call = retroInstance.inventoryInsertAndUbdate(appraisalBuildsSchema)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                progress.postValue(0)

                progress.postValue(0)
                when (response.code()) {
                    200 -> {
                        if (response.body()?.isSuccesed!!) {

                            responseLiveInsert.postValue(response.body())
                        } else {
                            Toast.makeText(
                                getApplication(),
                                response.body()?.rerurnMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    else -> {


                        Toast.makeText(
                            getApplication(),
                            "ERROR ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }


            }
        })
    }


    fun approveInventoryRequest(requestObject: java.util.ArrayList<InventoryTrxWarehouseTransActionDetailsModel>) {
        val appraisalBuildsSchema = Gson().toJsonTree(requestObject).asJsonArray
        progress.postValue(1)

        val call = retroInstance.approveInventoryMovesDetailsDescription(
            dataManager.user.accId,
            true,
            appraisalBuildsSchema
        )
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                progress.postValue(0)
                progress.postValue(0)
                when (response.code()) {
                    200 -> {
                        if (response.body()?.isSuccesed!!) {

                            responseLiveInsert.postValue(response.body())
                        } else {
                            Toast.makeText(
                                getApplication(),
                                response.body()?.rerurnMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    else -> {


                        Toast.makeText(
                            getApplication(),
                            "ERROR ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }


            }
        })
    }


    fun getInventoryTypes(obj: JsonObject) {

        Log.d(TAG, "getInventoryTyps: " + obj.toString())

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


    fun getStoresDataFromAPI() {

        val call = retroInstance.getStoresDataFromAPI()
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {

                    System.out.println(" APICALL" + response.body().toString())
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }


        })
    }

    fun getInventoryStocking(requestObject: JsonObject) {

        val call = retroInstance.getInventoryInventory(requestObject)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                recyclerListData.postValue(null)

                System.out.println(" faileeeeeeeeeeeeeee")
                System.out.println(t.message)
                System.out.println(" faileeeeeeeeeeeeeee")

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {

                    System.out.println(" APICALL" + response.body().toString())
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                    System.out.println(" noooooooooooooooooooo" + response.body().toString())
                    System.out.println(" noooooooooooooooooooo" + response.body().toString())

                }
            }
        })
    }


    fun getInventoryMovesDetails(requestObject: JsonObject) {
        progress.postValue(1)
        val call = retroInstance.getInventoryMovesDetails(requestObject)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {


                progress.postValue(0)
                when (response.code()) {
                    200 -> {
                        if (response.body()?.isSuccesed!!) {

                            recyclerListData.postValue(response.body())
                        } else {
                            Toast.makeText(
                                getApplication(),
                                response.body()?.rerurnMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                    else -> {


                        Toast.makeText(
                            getApplication(),
                            "ERROR ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }


            }
        })
    }

    fun getInventoryStatues(requestObject: JsonObject) {

        progress.postValue(1)

        val call = retroInstance.getInventoryStatues(requestObject)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                progress.postValue(0)
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                progress.postValue(0)
                if (response.body()?.isSuccesed!!) {
                    recyclerListData.postValue(response.body())
                } else {

                    Toast.makeText(
                        getApplication(),
                        response.body()?.rerurnMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    fun getDataFromAPI(requestObject: JsonObject) {

        val call = retroInstance.getDataFromAPI2(requestObject)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {

                    System.out.println(" APICALL" + response.body().toString())
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }


    fun getInventoryMovesDetailsDescription(requestObject: JsonObject) {
        progress.postValue(1)

        val call = retroInstance.getInventoryMovesDetailsDescription(requestObject)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(getApplication(), t.message, Toast.LENGTH_SHORT).show()
                progress.postValue(0)

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                progress.postValue(0)
                if (response.body()?.isSuccesed!!) {

                    recyclerListData.postValue(response.body())
                } else {
                    Toast.makeText(
                        getApplication(),
                        response.body()?.rerurnMessage!!,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }
}