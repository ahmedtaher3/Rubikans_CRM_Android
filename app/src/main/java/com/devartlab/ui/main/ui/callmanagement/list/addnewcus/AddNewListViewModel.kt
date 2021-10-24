package com.devartlab.ui.main.ui.callmanagement.list.addnewcus

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.listtypes.ListTypesDao
import com.devartlab.data.shared.DataManager
import com.devartlab.model.CustomerList
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesEntity
import retrofit2.Retrofit

class AddNewListViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    lateinit var listTypesDao: ListTypesDao
    lateinit var listDao: ListDao

    val responseLive: MutableLiveData<List<CustomerList>>
    val responseLiveList: MutableLiveData<List<ListEntity>>
    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<List<CustomerList>>()
        responseLiveList = MutableLiveData<List<ListEntity>>()
        progress = MutableLiveData<Int>()

        listTypesDao = DatabaseClient.getInstance(application)?.appDatabase?.listTypesDao()!!
        listDao = DatabaseClient.getInstance(application)?.appDatabase?.listDao()!!


    }


    public fun getData(
        TerrAssignId: String,
        CustomerTypeid: String,
        BrickIdStr: String,
        SpecialityIdStr: String,
        ClassIdStr: String,
        ExecludeBranchIdStr: String,
        FilterText: String
    ) {

        progress.postValue(1)
        myAPI?.customerDataBase(
            dataManager.user.accId!!, TerrAssignId, CustomerTypeid, BrickIdStr
            , SpecialityIdStr, ClassIdStr, ExecludeBranchIdStr, FilterText
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<CustomerList>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<CustomerList>) {

                    responseLive.postValue(data)
                    progress.postValue(0)
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


    public fun getDataWithoutLoading(
        TerrAssignId: String,
        CustomerTypeid: String,
        BrickIdStr: String,
        SpecialityIdStr: String,
        ClassIdStr: String,
        ExecludeBranchIdStr: String,
        FilterText: String
    ) {

        myAPI?.customerDataBase(
            dataManager.user.accId!!, TerrAssignId, CustomerTypeid, BrickIdStr
            , SpecialityIdStr, ClassIdStr, ExecludeBranchIdStr, FilterText
        )!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<CustomerList>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<CustomerList>) {

                    responseLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(getApplication(), e.message.toString(), Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onComplete() {

                }
            })

    }


    fun insert(model: CustomerList, listTypesEntity: ListTypesEntity) {

        var listEntity = ListEntity(
            model.listSerial,
            listTypesEntity.listTypeId,
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
            false

        )

        Completable.fromAction {

            listDao.insert(listEntity)

        }.subscribeOn(Schedulers.io())
            .subscribe()
    }


}