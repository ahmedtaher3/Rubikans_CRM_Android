package com.devartlab.ui.main.ui.employeeservices.penalties

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.GoogleRequestResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class PenaltiesViewModel(application: Application) : AndroidViewModel(application) {

    val progress: MutableLiveData<Int>
    var myAPI: ApiServices? = null
    var myAPI2: ApiServicesGoogle? = null
    var dataManager: DataManager? = null
    var retrofit: Retrofit? = null
    var retrofit2: Retrofit? = null
    val responseLive: MutableLiveData<ResponseModel>
    val responseLiveInsert: MutableLiveData<GoogleRequestResponse>
    val responseLiveDelete: MutableLiveData<ResponseModel>
    val responseLivePenaltiesType: MutableLiveData<ResponseModel>
    val responseLivePenaltiesReason: MutableLiveData<ResponseModel>


    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        progress = MutableLiveData()
        retrofit = RetrofitClient(dataManager!!).instance!!
        retrofit2 = RetrofitClient(dataManager!!).instanceGoogleSheet!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        myAPI2 = retrofit2!!.create(ApiServicesGoogle::class.java)
        responseLive = MutableLiveData<ResponseModel>()
        responseLiveInsert = MutableLiveData<GoogleRequestResponse>()
        responseLiveDelete = MutableLiveData<ResponseModel>()
        responseLivePenaltiesType = MutableLiveData<ResponseModel>()
        responseLivePenaltiesReason = MutableLiveData<ResponseModel>()


    }


    fun getPenalties(EmpId: String, Month: String, Year: String) {
        progress.postValue(1)
        myAPI?.getEmployeePenalties(EmpId.toInt(), Month, Year)!!
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
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {

                    }
                })

    }

    fun deletePenalty(id: String) {
        progress.postValue(1)
        myAPI?.deletePenalty(dataManager?.user?.accId!!, id.toInt())!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        responseLiveDelete.postValue(data)
                        progress.postValue(0)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {

                    }
                })

    }

    fun getPenaltiesType() {


    }

    fun getPenaltiesReason() {

        progress.postValue(1)
        myAPI?.penaltiesReason!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {

                        responseLivePenaltiesReason.postValue(data)
                        myAPI?.penaltiesType!!
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Observer<ResponseModel> {
                                    override fun onSubscribe(d: Disposable) {}
                                    override fun onNext(data: ResponseModel) {
                                        responseLivePenaltiesType.postValue(data)

                                        progress.postValue(0)


                                    }

                                    override fun onError(e: Throwable) {


                                    }

                                    override fun onComplete() {
                                    }
                                })

                        /*  if (data.isSuccesed) {
                              for (model in data.data.penaltiesReason) {
                                  ReasonTypes.add(model.penReasonArName)
                                  ReasonTypesIdes.add(model.penReasonId)
                              }

                              ReasonTypePenalty.setAdapter(ArrayAdapter<String>(context, R.layout.simple_dropdown_item_1line, ReasonTypes))

                          } else {
                              Toast.makeText(context, data.rerurnMessage, Toast.LENGTH_SHORT).show()
                          }*/


                    }

                    override fun onError(e: Throwable) {


                    }

                    override fun onComplete() {
                    }
                })

    }

    fun insert(empId: String, empName: String, reason: String, type: String, notes: String) {

        progress.postValue(1)
        myAPI2?.penalties(
                "Penalties",
                "insert",
                dataManager?.user?.empId.toString(),
                dataManager?.user?.nameAr!!,
                empId,
                empName,
                dataManager?.user?.managerId.toString(),
                reason,
                type,
                notes,
                "PENDING",
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
                        progress.postValue(2)
                        responseLiveInsert.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)


                    }

                    override fun onComplete() {
                    }
                })

    }

    fun delete(code: String) {

        progress.postValue(1)
        myAPI2?.penalties(
                "Penalties",
                "delete",
                dataManager?.user?.empId.toString(),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "PENDING",
                "",
                "",
                "",
                code
        )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        progress.postValue(0)
                        responseLiveInsert.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)


                    }

                    override fun onComplete() {
                    }
                })

    }


    fun getSentPenalties() {

        progress.postValue(1)
        myAPI2?.penalties(
                "Penalties",
                "getAll",
                dataManager?.user?.empId.toString(),
                dataManager?.user?.nameAr!!,
                "",
                "",
                dataManager?.user?.managerId.toString(),
                "",
                "",
                "",
                "PENDING",
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
                        responseLiveInsert.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)


                    }

                    override fun onComplete() {
                    }
                })

    }


    fun getMyPenalties() {

        progress.postValue(1)
        myAPI2?.penalties(
                "Penalties",
                "getAllMyPenalties",
                "",
                dataManager?.user?.nameAr!!,
                dataManager?.user?.empId.toString(),
                "",
                dataManager?.user?.managerId.toString(),
                "",
                "",
                "",
                "PENDING",
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
                        responseLiveInsert.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)


                    }

                    override fun onComplete() {
                    }
                })

    }


}