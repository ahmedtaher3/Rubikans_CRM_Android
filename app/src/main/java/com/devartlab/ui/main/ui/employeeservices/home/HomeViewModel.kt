package com.devartlab.ui.main.ui.employeeservices.home

import android.app.Application
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
import com.devartlab.data.retrofit.ApiServicesGoogle
import retrofit2.Retrofit

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var dataManager: DataManager

    var myAPI2: ApiServicesGoogle? = null
    var retrofit2: Retrofit? = null
    val progressGoogle: MutableLiveData<Int>
    val responseLiveRequests: MutableLiveData<GoogleRequestResponse>

    var authList :LiveData<List<AuthorityEntity>>?=null
    lateinit var authorityDao: AuthorityDao

    init {
        dataManager = (getApplication<Application>() as BaseApplication).dataManager!!
        progressGoogle = MutableLiveData()
        responseLiveRequests = MutableLiveData()
        retrofit2 = RetrofitClient(dataManager!!).instanceGoogleSheet!!
        authorityDao = DatabaseClient.getInstance(getApplication())?.appDatabase?.authorityDao()!!

        myAPI2 = retrofit2!!.create(ApiServicesGoogle::class.java)
        getAuthList ()
    }


    fun getRequests(action: String?,
                    date: String,
                    id: String,
                    name: String,
                    typeRequest: String?,
                    requestStartIn: String,
                    requestEndIn: String?,
                    notes: String,
                    managerId: String,
                    status: String,
                    comment: String,
                    approvalDate: String,
                    code: String) {


        progressGoogle.postValue(1)

         myAPI2?.googleSheetRequest("HR Requests" ,action!!, date, id, name, typeRequest!!, requestStartIn, requestEndIn!!, notes, managerId, status, comment, approvalDate, code)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {


                        progressGoogle.postValue(0)
                        responseLiveRequests.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progressGoogle.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {}
                })

    }




    fun getAuthList ()  {

         authList = authorityDao.all
    }
}