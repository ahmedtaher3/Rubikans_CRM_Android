package com.devartlab.ui.main.ui.callmanagement.list

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.listtypes.ListTypesDao
import com.devartlab.data.shared.DataManager
import com.devartlab.model.CustomerList
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import com.devartlab.model.SyncList
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.model.Response
import retrofit2.Retrofit

class TypesViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    lateinit var listTypesDao: ListTypesDao
    lateinit var listDao: ListDao

    val responseLive: MutableLiveData<List<ListTypesEntity>>
    val responseLiveList: MutableLiveData<List<ListEntity>>
    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<List<ListTypesEntity>>()
        responseLiveList = MutableLiveData<List<ListEntity>>()
        progress = MutableLiveData<Int>()

        listTypesDao = DatabaseClient.getInstance(application)?.appDatabase?.listTypesDao()!!
        listDao = DatabaseClient.getInstance(application)?.appDatabase?.listDao()!!


    }


    fun syncList() {

        progress.postValue(1)
        myAPI?.getCustomerListType(dataManager.user.accId!!)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<SpecialtyParentEntity>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<SpecialtyParentEntity>) {

                    Toast.makeText(getApplication(), data.size.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Completable.fromAction {

                        listTypesDao.deleteTable()
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()

                    for (model in data) {


                        val type = ListTypesEntity(
                            model.lIstId!! + 0,
                            model.accountId!! + 0
                            , model.assigntId!! + 0
                            , model.iSReadOnly!!
                            , model.listTypeId!! + 0
                            , model.listType + 0
                            , model.listDescription + 0
                            , model.totalCustomer!! + 0
                            , model.iconImageUrl + 0
                        )


                        Completable.fromAction {

                            listTypesDao.insert(type)
                        }
                            .subscribeOn(Schedulers.io())
                            .subscribe()
                    }
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message.toString(), Toast.LENGTH_SHORT)
                        .show()


                }

                override fun onComplete() {
                    getCustomerList()
                }
            })

    }

    fun getCustomerList() {


        myAPI?.getCustomerList(dataManager.user.accId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<CustomerList>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<CustomerList>) {

                    Completable.fromAction {

                        listDao.deleteTable()
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()


                    for (model in data) {


                        val customer = ListEntity(
                            model.listSerial,
                            model.customerTypeId,
                            model.listDescription
                            , model.empId
                            , model.salAreaId
                            , model.districtId
                            , model.customerId
                            , model.branchId
                            , model.customerState
                            , model.notes
                            , model.placeName
                            , model.brickEnName
                            , model.bricId
                            , model.branchtypeId
                            , model.branchType
                            , model.cusSerial
                            , model.customerEnName
                            , model.oldSpeciality
                            , model.oldClass
                            , model.oldClassId
                            , model.oldSpecialityId
                            , model.address
                            , model.listId
                            , model.listDetId
                            , model.cusTypeEnName
                            , model.cusClassEnName
                            , model.cusTypeId
                            , model.cusClassId
                            , model.empArName
                            , model.empEnName
                            , model.deptId
                            , model.secId
                            , model.jobId
                            , model.assigntId
                            , model.accountId
                            , model.addressNotes
                            , model.branchPlaceId
                            , model.branchTel1
                            , model.branchTel2
                            , model.terriotryId
                            , model.branchDesc
                            , true
                        ,0
                        ,0
                        ,""
                        ,""
                        ,""
                        )


                        Completable.fromAction {

                            listDao.insert(customer)
                        }
                            .subscribeOn(Schedulers.io())
                            .subscribe()
                    }


                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)

                }

                override fun onComplete() {
                    progress.postValue(0)
                }
            })

    }

    fun getAllTypes() {
        Completable.fromAction {
            responseLive.postValue(listTypesDao.all)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()


    }


    fun updateCustomer(customer: ListEntity, id: String) {
        Completable.fromAction {
            listDao.update(customer)
        }
            .subscribeOn(Schedulers.io())
            .subscribe()

        getAllList(id)
    }

    fun getAllList(id: String) {
        Completable.fromAction {

            responseLiveList.postValue(listDao.getAll(id))
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }


    fun updateList(id: String) {


        Completable.fromAction {

            var list = ArrayList<SyncList>()

            for (model in listDao.getAll(id)) {
                com.orhanobut.logger.Logger.d(model)
                System.out.println(model.toString())


                list.add(
                    SyncList(
                        model.listDetId,
                        model.listId,
                        model.customerId,
                        model.branchId,
                        model.cusTypeId,
                        model.cusClassId,
                        model.customerState,
                        "",
                        "",
                        model.notes,
                        "",
                        "",
                        "",
                        model.assigntId,
                        model.accountId,
                        false
                    )
                )
            }

            val jsonArray1 = Gson().toJsonTree(list).asJsonArray

            progress.postValue(1)
            myAPI?.updateList(dataManager.user?.accId!!, "Android", jsonArray1)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<java.util.ArrayList<Response>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: java.util.ArrayList<Response>) {


                        if (body.get(0).status) {
                            Toast.makeText(
                                getApplication(),
                                body.get(0).message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                getApplication(),
                                body.get(0).message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        progress.postValue(0)

                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {

                    }
                })


        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()


    }
}