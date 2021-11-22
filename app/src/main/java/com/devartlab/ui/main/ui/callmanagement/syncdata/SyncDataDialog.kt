package com.devartlab.ui.main.ui.callmanagement.syncdata

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
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
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.data.room.values.ValuesDao
import com.devartlab.data.shared.DataManager
import com.devartlab.data.source.values.ValuesRepository
import com.devartlab.model.CustomerList
import com.devartlab.model.ProductTable
import com.devartlab.utils.LocationUtils
import com.devartlab.utils.PlanUtlis
import com.devartlab.utils.ProgressLoading
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.sync_data_dialog.*
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class SyncDataDialog(
    context: Context,
    private var activity: AppCompatActivity,
    private var dataManager: DataManager
) : Dialog(context) {

    lateinit var myAPI: ApiServices
    lateinit var retrofit: Retrofit

    lateinit var massagesDao: MassagesDao
    lateinit var productDao: ProductDao
    lateinit var slideDao: SlideDao
    lateinit var planDao: PlanDao
    lateinit var valuesDao: ValuesDao
    lateinit var listTypesDao: ListTypesDao
    lateinit var listDao: ListDao
    var valuesRepository: ValuesRepository? = null

    var startPointDao: StartPointDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sync_data_dialog)
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)

        massagesDao = DatabaseClient.getInstance(context)?.appDatabase?.massagesDao()!!
        productDao = DatabaseClient.getInstance(context)?.appDatabase?.productDao()!!
        slideDao = DatabaseClient.getInstance(context)?.appDatabase?.slideDao()!!
        listTypesDao = DatabaseClient.getInstance(context)?.appDatabase?.listTypesDao()!!
        listDao = DatabaseClient.getInstance(context)?.appDatabase?.listDao()!!
        planDao = DatabaseClient.getInstance(context)?.appDatabase?.planDao()!!
        valuesDao = DatabaseClient.getInstance(context)?.appDatabase?.valuesDao()!!
        valuesRepository = ValuesRepository.getInstance(valuesDao)
        startPointDao = DatabaseClient.getInstance(context)?.appDatabase?.startPointDao()

        syncProductsData.setOnClickListener(View.OnClickListener { syncProducts() })
        syncPlanData.setOnClickListener(View.OnClickListener {


            if (dataManager?.syncAble) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Sync Plan")
                builder.setMessage("Are you sure?")
                builder.setPositiveButton("YES") { dialog, which ->


                    getPlan()

                    dialog.dismiss()
                }
                builder.setNegativeButton("NO") { dialog, which ->
                    // Do nothing
                    dialog.dismiss()
                }
                val alert = builder.create()
                alert.show()
            } else {
                1
            }


        })
        syncListData.setOnClickListener(View.OnClickListener { syncList() })
        dismiss.setOnClickListener(View.OnClickListener {
            dataManager.saveFirstTime(false)
            dismiss()
        })
    }

    fun syncProducts() {
        ProgressLoading.show(activity)
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
                    ProgressLoading.dismiss()
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

                }

                override fun onComplete() {
                    ProgressLoading.dismiss()
                    syncProductsDataImage.visibility = View.VISIBLE
                }
            })

    }


    fun getPlan() {


        if (LocationUtils.checkSyncPlanPermissions(activity)) {

            ProgressLoading.show(activity)
            PlanUtlis.syncPlan(activity.application, object : PlanUtlis.OnSyncPlan {
                override fun onSuccess() {


                    Single.timer(1, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {
                                ProgressLoading.dismiss()
                                syncPlanDataImage.visibility = View.VISIBLE

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
                                ProgressLoading.dismiss()
                                println(error)
                                System.out.println(error)
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        })


                }
            })

        } else {
            Toast.makeText(
                activity,
                "Enable Permissions First!",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    fun syncList() {

        ProgressLoading.show(activity)
        myAPI?.getCustomerListType(dataManager.user.accId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<SpecialtyParentEntity>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<SpecialtyParentEntity>) {

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
                    ProgressLoading.dismiss()
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()


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


                        val customer = ListEntity(model.listSerial,
                                                  model.customerTypeId,
                                                  model.listDescription,
                                                  model.empId,
                                                  model.salAreaId,
                                                  model.districtId,
                                                  model.customerId,
                                                  model.branchId,
                                                  model.customerState,
                                                  model.notes,
                                                  model.placeName,
                                                  model.brickEnName,
                                                  model.bricId,
                                                  model.branchtypeId,
                                                  model.branchType,
                                                  model.cusSerial,
                                                  model.customerEnName,
                                                  model.oldSpeciality,
                                                  model.oldClass,
                                                  model.oldClassId,
                                                  model.oldSpecialityId,
                                                  model.address,
                                                  model.listId,
                                                  model.listDetId,
                                                  model.cusTypeEnName,
                                                  model.cusClassEnName,
                                                  model.cusTypeId,
                                                  model.cusClassId,
                                                  model.empArName,
                                                  model.empEnName,
                                                  model.deptId,
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
                        }.subscribeOn(Schedulers.io()).subscribe()
                    }


                }

                override fun onError(e: Throwable) {
                    ProgressLoading.dismiss()

                }

                override fun onComplete() {
                    ProgressLoading.dismiss()
                    syncListDataImage.visibility = View.VISIBLE

                }
            })

    }

}
