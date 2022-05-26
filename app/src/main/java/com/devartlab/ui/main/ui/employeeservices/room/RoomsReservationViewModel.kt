package com.devartlab.ui.main.ui.employeeservices.room

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.GoogleRequestResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class RoomsReservationViewModel(application: Application) : AndroidViewModel(application) {

    val progress: MutableLiveData<Int>
    var myAPI: ApiServices? = null
    var dataManager: DataManager? = null
    var retrofit: Retrofit? = null
    val responseLive: MutableLiveData<GoogleRequestResponse>
    val reservationLive: MutableLiveData<GoogleRequestResponse>
    val responseLiveDelete: MutableLiveData<ResponseModel>


    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!
        progress = MutableLiveData()
        retrofit = RetrofitClient(dataManager!!).instanceGoogleSheet!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<GoogleRequestResponse>()
        reservationLive = MutableLiveData<GoogleRequestResponse>()
        responseLiveDelete = MutableLiveData<ResponseModel>()

    }


    public fun rooms(sheet: String?, action: String?, id: String?, date: String?, name: String?, roomId: String?, reservationDate: String?, nine: String?, nineHalf: String?, ten: String?, tenHalf: String?, eleven: String?, elevenHalf: String?, twelve: String?, twelveHalf: String?, one: String?, oneHalf: String?, two: String?, twoHalf: String?, three: String?, threeHalf: String?, four: String?, fourHalf: String?, five: String?, fiveHalf: String?, six: String?, sixHalf: String?, code: String?) {
/*        progress.postValue(1)
        myAPI?.room(sheet, action, id, date, name, roomId, reservationDate, nine, nineHalf, ten, tenHalf, eleven, elevenHalf, twelve, twelveHalf, one, oneHalf, two, twoHalf, three, threeHalf, four, fourHalf, five, fiveHalf, six, sixHalf, code)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        responseLive.postValue(data)
                        progress.postValue(0)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {

                    }
                })*/

    }

    public fun reservations(sheet: String?, action: String?, id: String?, date: String?, name: String?, roomId: String?, reservationDate: String?, nine: String?, nineHalf: String?, ten: String?, tenHalf: String?, eleven: String?, elevenHalf: String?, twelve: String?, twelveHalf: String?, one: String?, oneHalf: String?, two: String?, twoHalf: String?, three: String?, threeHalf: String?, four: String?, fourHalf: String?, five: String?, fiveHalf: String?, six: String?, sixHalf: String?, code: String?) {
        progress.postValue(1)
   /*     myAPI?.room(sheet, action, id, date, name, roomId, reservationDate, nine, nineHalf, ten, tenHalf, eleven, elevenHalf, twelve, twelveHalf, one, oneHalf, two, twoHalf, three, threeHalf, four, fourHalf, five, fiveHalf, six, sixHalf, code)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        reservationLive.postValue(data)
                        progress.postValue(0)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {

                    }
                })*/

    }


}