package com.devartlab.ui.main.ui.callmanagement.list.create

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.filterdata.FilterDataDao
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesDao
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.data.room.specialty.SpecialtyDao
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import com.devartlab.data.shared.DataManager
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class CreateNewCustomerViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    lateinit var listTypesDao: ListTypesDao
    lateinit var listDao: ListDao
    lateinit var specialtyDao: SpecialtyDao
    lateinit var filterDataDao: FilterDataDao

    val responseLiveTypes: MutableLiveData<List<ListTypesEntity>>
    val filterSpecialityResponseLiveChild: MutableLiveData<List<FilterDataEntity>>
    val filterTerriotryResponseLive: MutableLiveData<List<FilterDataEntity>>
    val filterBrikResponseLive: MutableLiveData<List<FilterDataEntity>>
    val filterClassResponseLive: MutableLiveData<List<FilterDataEntity>>
    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLiveTypes = MutableLiveData()
        filterSpecialityResponseLiveChild = MutableLiveData()
        filterTerriotryResponseLive = MutableLiveData()
        filterBrikResponseLive = MutableLiveData()
        filterClassResponseLive = MutableLiveData()
        progress = MutableLiveData<Int>()

        listDao = DatabaseClient.getInstance(application)?.appDatabase?.listDao()!!
        listTypesDao = DatabaseClient.getInstance(application)?.appDatabase?.listTypesDao()!!
        specialtyDao = DatabaseClient.getInstance(application)?.appDatabase?.specialtyDao()!!
        filterDataDao = DatabaseClient.getInstance(application)?.appDatabase?.filterDataDao()!!

    }


    fun getTypes() {

        if (dataManager.offlineMood) {
            Completable.fromAction {
                responseLiveTypes.postValue(listTypesDao.all)
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {
            progress.postValue(1)
            myAPI?.getCustomerListType(dataManager.user.accId!!)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<SpecialtyParentEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<SpecialtyParentEntity>) {
                        progress.postValue(0)
                        val list = ArrayList<ListTypesEntity>()
                        for (m in data) {
                            list.add(ListTypesEntity(m.lIstId, 0, 0, 0, m.listTypeId, "", m.listDescription, m.totalCustomer, ""))
                        }
                        responseLiveTypes.postValue(list)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message.toString(), Toast.LENGTH_SHORT)
                            .show()


                    }

                    override fun onComplete() {

                    }
                })
        }


    }

    public fun getFilterSpecialityChild(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String,
        typeId: Int
    ) {

        if (dataManager.offlineMood) {
            Completable.fromAction {
                filterSpecialityResponseLiveChild.postValue(filterDataDao.getAll("speciality"))
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {

            progress.postValue(1)
            myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {

                        progress.postValue(0)
                        filterSpecialityResponseLiveChild.postValue(data)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                    }

                    override fun onComplete() {}
                })
        }


    }


    public fun getFilterTerriotry(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String
    ) {

        if (dataManager.offlineMood) {
            Completable.fromAction {
                filterTerriotryResponseLive.postValue(filterDataDao.getAll("territory"))
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {
            progress.postValue(1)
            myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {

                        progress.postValue(0)
                        filterTerriotryResponseLive.postValue(data)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                    }

                    override fun onComplete() {}
                })
        }
    }

    public fun getFilterBrik(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String
    ) {


        if (dataManager.offlineMood) {
            Completable.fromAction {
                //filterBrikResponseLive.postValue(filterDataDao.getAll("brick" , whereCondition.toInt()))
                filterBrikResponseLive.postValue(filterDataDao.getAll("brick"))
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {

            progress.postValue(1)
            myAPI?.getFilterData(id.toInt(), tableName, whereCondition, "", filterText)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {

                        progress.postValue(0)
                        filterBrikResponseLive.postValue(data)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                    }

                    override fun onComplete() {}
                })
        }
    }

    public fun getFilterClass(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String
    ) {

        if (dataManager.offlineMood) {
            Completable.fromAction {
                filterClassResponseLive.postValue(filterDataDao.getAll("class"))
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {

            progress.postValue(1)
            myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {

                        progress.postValue(0)
                        filterClassResponseLive.postValue(data)
                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                    }

                    override fun onComplete() {}
                })
        }
    }


    fun addCustomer(model: ListEntity) {
        Completable.fromAction {

            listDao.insert(model)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}