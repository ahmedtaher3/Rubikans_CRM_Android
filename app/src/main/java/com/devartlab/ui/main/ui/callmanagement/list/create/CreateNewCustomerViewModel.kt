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

    val responseLiveTypes: MutableLiveData<List<SpecialtyParentEntity>>
    val filterSpecialityResponseLiveChild: MutableLiveData<List<FilterDataEntity>>
    val filterTerriotryResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterBrikResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterClassResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
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
        specialtyDao = DatabaseClient.getInstance(application)?.appDatabase?.specialtyDao()!!
        filterDataDao = DatabaseClient.getInstance(application)?.appDatabase?.filterDataDao()!!

    }


    fun getTypes() {

        if (dataManager.offlineMood) {
            Completable.fromAction {
                responseLiveTypes.postValue(specialtyDao.all)
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
                        responseLiveTypes.postValue(data)

                        Completable.fromAction {
                            specialtyDao.insertAll(data)
                        }.subscribeOn(Schedulers.io())
                            .subscribe()

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
                filterSpecialityResponseLiveChild.postValue(filterDataDao.getAll("Speciality",typeId))
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


                        Completable.fromAction {

                            for (m in data)
                            {
                                m.parentName = "Speciality"
                                m.parentId = typeId
                                filterDataDao.insert(m)
                            }

                        }.subscribeOn(Schedulers.io())
                            .subscribe()


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

        progress.postValue(1)
        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
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

    public fun getFilterBrik(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String
    ) {

        progress.postValue(1)
        myAPI?.getFilterData(id.toInt(), tableName, whereCondition, "", filterText)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
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

    public fun getFilterClass(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String
    ) {

        progress.postValue(1)
        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
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


    fun addCustomer(model: ListEntity) {
        Completable.fromAction {

            listDao.insert(model)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

}