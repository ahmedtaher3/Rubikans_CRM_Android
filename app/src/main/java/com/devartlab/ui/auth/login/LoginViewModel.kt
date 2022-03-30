package com.devartlab.ui.auth.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.GetDeviceToken
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.authority.AuthorityDao
import com.devartlab.data.room.authority.AuthorityEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.AdModelList
import com.devartlab.model.AuthorityDatum
import com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping.Login4EShoppingRequest
import com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping.Login4EShoppingResponse
import com.devartlab.utils.CommonUtilities
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
 import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val dataManager: DataManager
    val responseLive: MutableLiveData<ResponseModel>
    val responseLiveData: MutableLiveData<ResponseModel>
    val responseLiveUpdatePermission: MutableLiveData<ResponseModel>
    val progress: MutableLiveData<Int>
    val checkData: MutableLiveData<Boolean>
    var errorMessage: MutableLiveData<Int>
        protected set
    var login4EShoppingResponse: MutableLiveData<Login4EShoppingResponse?>
        protected set
    var authorityDao: AuthorityDao? = null
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    var myAPIDevartLink: ApiServices? = null
    var retrofitDevartLink: Retrofit? = null

    var databaseReference: DatabaseReference? = null

    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!


        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)


        retrofitDevartLink = RetrofitClient.getInstanceDevartLink()
        myAPIDevartLink = retrofitDevartLink!!.create(ApiServices::class.java)

        responseLive = MutableLiveData<ResponseModel>()
        responseLiveData = MutableLiveData<ResponseModel>()
        responseLiveUpdatePermission = MutableLiveData<ResponseModel>()
        progress = MutableLiveData()
        checkData = MutableLiveData()
        databaseReference = FirebaseDatabase.getInstance().reference
        authorityDao = DatabaseClient.getInstance(application)?.appDatabase?.authorityDao()
        errorMessage = MutableLiveData()//error message
        login4EShoppingResponse = MutableLiveData()//login 4eshopping

    }


    fun checkDatabase() {
        Completable.fromAction {
            val list = authorityDao?.allList
            checkData.postValue(list?.size!! > 0)
        }.subscribeOn(Schedulers.io())
            .subscribe()

    }

    public fun login(userEmail: String, userPass: String) {

        progress.postValue(1)


        myAPIDevartLink?.getAds()!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {

                    val ads = AdModelList(data.data.ads)
                    dataManager.saveAds(ads)


                    myAPI?.login(userEmail, userPass)!!
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Observer<ResponseModel> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onNext(data: ResponseModel) {

                                progress.postValue(0)
                                responseLive.postValue(data)

                                if (data.isSuccesed) {


                                    dataManager.saveToken("token")
                                }

                            }

                            override fun onError(e: Throwable) {

                                Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                                progress.postValue(10)

                            }

                            override fun onComplete() {


                            }
                        })

                }

                override fun onError(e: Throwable) {

                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                    progress.postValue(0)

                }

                override fun onComplete() {


                }
            })






    }

    public fun loginData(userEmail: String, userPass: String) {

        progress.postValue(1)



        myAPI?.loginData(userEmail, userPass)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {

                    progress.postValue(0)
                    responseLiveData.postValue(data)

                }

                override fun onError(e: Throwable) {

                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                    progress.postValue(0)

                }

                override fun onComplete() {


                }
            })


    }

    public fun updatePermission() {


        myAPI?.updatePermission(dataManager.user.accId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseModel> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ResponseModel) {


                    progress.postValue(0)
                    responseLiveUpdatePermission.postValue(data)

                }

                override fun onError(e: Throwable) {

                    progress.postValue(0)

                }

                override fun onComplete() {


                }
            })


    }

    fun saveAuthority(list: ArrayList<AuthorityDatum>) {
        Completable.fromAction {

            authorityDao?.deleteTable()

            var newList = ArrayList<AuthorityEntity>()
            var i = 0
            for (model in list) {

                if (
                    model.formId == 61 ||
                    model.formId == 1125 ||
                    model.formId == 1126 ||
                    model.formId == 1060 ||
                    model.formId == 1061 ||
                    model.formId == 1084 ||
                    model.formId == 1100 ||
                    model.formId == 1026 ||
                    model.formId == 1157 ||
                    model.formId == 1003 ||
                    model.formId == 1159 ||
                    model.formId == 1138
                ) {

                    i++
                    var authorityEntity = AuthorityEntity(
                        model.authorityDetId,
                        model.authorityId,
                        model.formId,
                        model.allowBrowseRecord,
                        model.allowEdit,
                        model.allowSave,
                        model.allowDelete,
                        model.allowExport,
                        model.allowPreviewReport,
                        model.allowPrintReport
                    )
                    newList.add(authorityEntity)

                }


            }

            print("auth counter" + i.toString())
            authorityDao?.insertAllEntities(newList)


        }.subscribeOn(Schedulers.io())
            .subscribe()

    }

    //function login 4EShopping
    fun getUserModel(activity: AppCompatActivity, login4EShoppingRequest: Login4EShoppingRequest) {
        val getToken = GetDeviceToken(activity)
        getToken.getToken(object : GetDeviceToken.TokenResult() {
            override fun success(token: String?) {
                var myToken = ""
                myToken = if (token.isNullOrBlank()) {
                    dataManager.deviceToken!!
                } else {
                    token
                }

                login4EShoppingRequest.fcm = myToken
                RetrofitClient.getApis4EShopping().LOGIN4ESHOPPING(login4EShoppingRequest)!!
                    .enqueue(object : Callback<Login4EShoppingResponse?> {
                        override fun onResponse(
                            call: Call<Login4EShoppingResponse?>,
                            response: Response<Login4EShoppingResponse?>
                        ) {
                            if (response.isSuccessful) {
                                login4EShoppingResponse.postValue(response.body())
                            } else {
                                login4EShoppingResponse.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<Login4EShoppingResponse?>, t: Throwable) {
                            errorMessage.postValue(1)
                        }
                    })
            }

            override fun failure(msg: String?) {


            }

        })

    }
}