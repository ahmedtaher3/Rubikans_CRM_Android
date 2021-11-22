package com.devartlab.ui.main.ui.callmanagement.sync

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesDao
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.data.room.massages.MassageEntity
import com.devartlab.data.room.massages.MassagesDao
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.poduct.ProductDao
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.room.poduct.ProductRepository
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.data.room.slides.SlideRepository
import com.devartlab.data.room.slidetimer.SlideTimerRepository
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.data.room.values.ValuesDao
import com.devartlab.data.shared.DataManager
import com.devartlab.data.source.values.ValuesRepository
import com.devartlab.model.CustomerList
import com.devartlab.model.ProductTable
import com.devartlab.model.SyncReport
import com.devartlab.utils.PlanUtlis
import com.google.gson.Gson
import com.google.gson.JsonArray
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


private const val TAG = "SyncViewModel"

class SyncViewModel(application: Application) : AndroidViewModel(application) {
    lateinit private var productRepository: ProductRepository
    lateinit private var slideRepository: SlideRepository
    lateinit private var slideTimerRepository: SlideTimerRepository
    lateinit var myAPI: ApiServices
    lateinit var dataManager: DataManager
    lateinit var retrofit: Retrofit
    lateinit var allSlides: LiveData<List<SlideEntity>>
    lateinit var massagesDao: MassagesDao
    lateinit var productDao: ProductDao
    lateinit var slideDao: SlideDao
    lateinit var planDao: PlanDao
    lateinit var listTypesDao: ListTypesDao
    lateinit var listDao: ListDao
    lateinit var progress: MutableLiveData<Int>
    lateinit var responseLive: MutableLiveData<ProductTable>

    var valuesRepository: ValuesRepository? = null
    var startPointDao: StartPointDao? = null
    lateinit var valuesDao: ValuesDao


    init {

        planDao = DatabaseClient.getInstance(application)?.appDatabase?.planDao()!!


        allSlides = MediatorLiveData()
        responseLive = MediatorLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        progress = MutableLiveData()
        massagesDao = DatabaseClient.getInstance(application)?.appDatabase?.massagesDao()!!
        productDao = DatabaseClient.getInstance(application)?.appDatabase?.productDao()!!
        slideDao = DatabaseClient.getInstance(application)?.appDatabase?.slideDao()!!
        listTypesDao = DatabaseClient.getInstance(application)?.appDatabase?.listTypesDao()!!
        listDao = DatabaseClient.getInstance(application)?.appDatabase?.listDao()!!
        valuesDao = DatabaseClient.getInstance(application)?.appDatabase?.valuesDao()!!

        valuesRepository = ValuesRepository.getInstance(valuesDao)
        startPointDao = DatabaseClient.getInstance(application)?.appDatabase?.startPointDao()

    }


    fun syncProducts() {
        progress.postValue(1)
        myAPI?.syncProducts(dataManager?.user?.accId!!)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProductTable> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(body: ProductTable) {


                    Completable.fromAction {
                        productDao?.deleteTable()
                        for (product in body.table) {
                            val productEntity = ProductEntity(
                                product.itemId,
                                product.itemName,
                                product.itemImageUrl
                            )

                            productDao?.insert(productEntity)
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()



                    Completable.fromAction {
                        massagesDao?.deleteTable()
                        for (massage in body.table1) {
                            val massageEntity = MassageEntity(
                                massage.itemId,
                                massage.messageId,
                                massage.messageDescription,
                                massage.messageLogoUrl
                            )

                            massagesDao?.insert(massageEntity)
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()




                    Completable.fromAction {
                        slideDao?.deleteTable()
                        for (massageSlide in body.table2) {

                            val massageSlideEntity = SlideEntity(
                                massageSlide.itemId,
                                massageSlide.messageId,
                                massageSlide.messageDetId,
                                massageSlide.slideName,
                                massageSlide.slideUrl,
                                "",
                                false
                            )


                            slideDao?.insert(massageSlideEntity)
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()

                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                }

                override fun onComplete() {
                    progress.postValue(0)
                }
            })

    }

    fun getPlan() {


        progress.postValue(1)
        PlanUtlis.syncPlan(getApplication(), object : PlanUtlis.OnSyncPlan {
            override fun onSuccess() {


                Single.timer(1, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Long?> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onError(e: Throwable) {}
                        override fun onSuccess(t: Long) {

                            progress.postValue(0)
                            Toast.makeText(getApplication(), "Success", Toast.LENGTH_LONG).show()
                        }
                    })


            }

            override fun onError() {
                TODO("Not yet implemented")
            }

            override fun onFailure(error: String) {

                Single.timer(1, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Long?> {
                        override fun onSubscribe(d: Disposable) {}

                        override fun onError(e: Throwable) {}
                        override fun onSuccess(t: Long) {

                            progress.postValue(0)
                            Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show()
                        }
                    })


            }
        })

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
                            model.lIstId,
                            model.accountId
                            , model.assigntId
                            , model.iSReadOnly!!
                            , model.listTypeId
                            , model.listType
                            , model.listDescription
                            , model.totalCustomer
                            , model.iconImageUrl
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


        myAPI?.getCustomerList(dataManager.user.accId!!)!!
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
                            , model.deptId,
                            model.secId,
                            model.jobId,
                            model.assigntId,
                            model.accountId,
                            model.addressNotes,
                            model.branchPlaceId,
                            model.branchTel1,
                            model.branchTel2,
                            model.terriotryId,
                            model.branchDesc,
                            true,
                            0,
                            0,
                            "",
                            "",
                            "")


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


    public fun syncReport(date: String?, shift: String?) {
        println("date = " + date.toString() + "shift = " + shift)

        progress.postValue(1)

        Completable.fromAction {

            var list = ArrayList<SyncReport>();

            for (model in planDao?.getAllByDateAndShift(date, shift)!!) {


                list.add(
                    SyncReport(
                        model.planDetID,
                        dataManager.user.empId,
                        dataManager.user.accId,
                        model.date,
                        model.shiftId,
                        model.customerid,
                        model.customerBranchid,
                        0,
                        model.visit,
                        model.extraVisit,
                        model.activityId,
                        model.terriotryEmpId,
                        model.terriotryAssigntId,
                        model.terriotryAccountId,
                        model.doubleVAccountId,//
                        model.doubleVAccountIdStr,
                        0,
                        false,
                        model.cusLat,
                        model.cusLang,
                        model.call1,
                        model.call2,
                        model.call3,
                        model.call4,
                        0,
                        model.meetingMemberId,
                        "",
                        model.taskText
                    )
                )

            }
            val jsonArray1 = Gson().toJsonTree(list).asJsonArray
            println("jsonArray1" + jsonArray1.toString())
            sendReport(jsonArray1)

        }.subscribeOn(Schedulers.io())
            .subscribe()


    }

    public fun removePlan() {

        Completable.fromAction {
            planDao.deleteTable()
        }
            .subscribeOn(Schedulers.io())
            .subscribe()


    }

    public fun sendReport(array: JsonArray?) {


        myAPI?.sendTempSyncReportCustomer(dataManager.user.accId!!, "android", array!!)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(body: ResponseModel) {
                        System.out.println("ResponseModel" + body)


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        println(e.message)
                        System.out.println("onError" + e.message)


                    }

                    override fun onComplete() {
                        progress.postValue(0)

                    }
                })

    }


}