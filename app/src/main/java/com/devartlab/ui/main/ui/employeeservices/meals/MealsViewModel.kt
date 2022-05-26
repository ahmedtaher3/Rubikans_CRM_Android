package com.devartlab.ui.main.ui.employeeservices.meals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.GoogleRequestResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

class MealsViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServicesGoogle? = null
    var retrofit: Retrofit? = null

    val responseLive: MutableLiveData<GoogleRequestResponse>
    val responseLiveMeals: MutableLiveData<GoogleRequestResponse>
    val responseLiveMealRequest: MutableLiveData<GoogleRequestResponse>
    val responseLiveMealApproveRequest: MutableLiveData<GoogleRequestResponse>
    val responseLiveMealRequestToday: MutableLiveData<GoogleRequestResponse>


    val progress: MutableLiveData<Int>
    val progressWeek: MutableLiveData<Int>
    val progressMealRequest: MutableLiveData<Int>
    val progressMealApproveRequest: MutableLiveData<Int>
    val progressMealRequesttoday: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instanceGoogleSheet!!
        myAPI = retrofit!!.create(ApiServicesGoogle::class.java)
        responseLive = MutableLiveData<GoogleRequestResponse>()
        responseLiveMeals = MutableLiveData<GoogleRequestResponse>()
        responseLiveMealRequest = MutableLiveData<GoogleRequestResponse>()
        responseLiveMealRequestToday = MutableLiveData<GoogleRequestResponse>()
        responseLiveMealApproveRequest = MutableLiveData<GoogleRequestResponse>()


        progress = MutableLiveData<Int>()
        progressWeek = MutableLiveData<Int>()
        progressMealRequest = MutableLiveData<Int>()
        progressMealRequesttoday = MutableLiveData<Int>()
        progressMealApproveRequest = MutableLiveData<Int>()


    }

    /////////////////////////////////////////////////////////


    fun getMeals(sheet: String?) {


        progressWeek.postValue(1)
        myAPI?.meals(sheet!!, "getMeals", "", "", "", "", "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        progressWeek.postValue(0)
                        responseLiveMeals.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progressWeek.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }

    fun insertMeal(sheet: String?, date: String?, mealDate: String?, meal: String?, mealType: String?, quantity: String?, notes: String?, price: String?) {


        progress.postValue(1)
        myAPI?.meals(sheet!!, "insert", dataManager.user.empId.toString(), date!!, dataManager.user.nameAr, mealDate!!, meal!!, quantity!!, mealType!!, notes!!, "", price!!, "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        progress.postValue(0)
                        responseLive.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }

    fun getMealRequests() {


        progressMealRequest.postValue(1)
        myAPI?.meals("Meals Requests", "getAll", dataManager.user.empId.toString(), "", "", "", "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        progressMealRequest.postValue(0)
                        responseLiveMealRequest.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progressMealRequest.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }

    fun getMealRequestsToday() {


        progressMealRequesttoday.postValue(1)
        myAPI?.meals("Meals Requests", "getAllToDay", "", "", "", "", "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        progressMealRequesttoday.postValue(0)
                        responseLiveMealRequestToday.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progressMealRequesttoday.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }

    fun approveRequests(code: String) {


        progressMealRequesttoday.postValue(1)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' // 'hh:mm a", Locale.US)
        myAPI?.meals("Meals Requests", "update", "", "", "", "", "", "", "", "", code, "", simpleDateFormat?.format(System.currentTimeMillis()), dataManager.user.empId.toString())!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        progressMealRequesttoday.postValue(0)
                        responseLiveMealRequestToday.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progressMealRequesttoday.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }

    fun deleteMealRequest(code: String) {


        progressMealRequest.postValue(1)
        myAPI?.meals("Meals Requests", "delete", dataManager.user.empId.toString(), "", "", "", "", "", "", "", code, "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {

                        progressMealRequest.postValue(0)
                        responseLiveMealRequest.postValue(data)

                    }

                    override fun onError(e: Throwable) {
                        progressMealRequest.postValue(0)
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }


}