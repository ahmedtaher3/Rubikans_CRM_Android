package com.devartlab.ui.main.ui.callmanagement.list.list

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.DoubleVisitEmp
import com.devartlab.data.room.filterdata.FilterDataEntity
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


private const val TAG = "AddPlanViewModel"

class AddPlanViewModel(application: Application) : AndroidViewModel(application) {


    val dataManager: DataManager
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    lateinit var listDao: ListDao
    val responseLive: MutableLiveData<ArrayList<ListEntity>>
    val responseDoubleVisitEmpLive: MutableLiveData<ArrayList<DoubleVisitEmp>>
    val filterLinesResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterTerriotryResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterEmployeeResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterBrikResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterSpecialityResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterSpecialityResponseLiveChild: MutableLiveData<ArrayList<FilterDataEntity>>
    val filterClassResponseLive: MutableLiveData<ArrayList<FilterDataEntity>>
    val progress: MutableLiveData<Int>


    init {

        dataManager = (getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        responseLive = MutableLiveData<ArrayList<ListEntity>>()
        responseDoubleVisitEmpLive = MutableLiveData<ArrayList<DoubleVisitEmp>>()
        filterLinesResponseLive = MutableLiveData<ArrayList<FilterDataEntity>>()
        filterEmployeeResponseLive = MutableLiveData<ArrayList<FilterDataEntity>>()
        filterTerriotryResponseLive = MutableLiveData<ArrayList<FilterDataEntity>>()
        filterBrikResponseLive = MutableLiveData<ArrayList<FilterDataEntity>>()
        filterSpecialityResponseLive = MutableLiveData<ArrayList<FilterDataEntity>>()
        filterSpecialityResponseLiveChild = MutableLiveData<ArrayList<FilterDataEntity>>()
        filterClassResponseLive = MutableLiveData<ArrayList<FilterDataEntity>>()
        progress = MutableLiveData<Int>()
        listDao = DatabaseClient.getInstance(application)?.appDatabase?.listDao()!!


    }


    public fun getCustomerList(
        id: Int,
        rettitory: String,
        brick: String,
        specialty: String,
        classs: String,
        filterText: String,
        cusIdes: String,
        cusBranchIds: String
    ) {


        if (dataManager.offlineMood) {

            Completable.fromAction {

                if (filterText.isNullOrBlank() || filterText == "0")
                    responseLive.postValue(listDao.all as ArrayList<ListEntity>)
                else
                    responseLive.postValue(listDao.getAllByText(filterText) as ArrayList<ListEntity>)
            }
                .subscribeOn(Schedulers.io())
                .subscribe()
        } else {

            progress.postValue(1)
            myAPI?.SearchCustomer(
                id,
                rettitory,
                brick,
                specialty,
                classs,
                filterText,
                cusIdes,
                cusBranchIds
            )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<ListEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<ListEntity>) {

                        progress.postValue(0)
                        Completable.fromAction {

                            val list = listDao.getByName("filterText") as ArrayList<ListEntity>
                            listDao.insertAll(data)
                            data.addAll(list)
                            Log.d(TAG, "onNext: " + list.size)
                            responseLive.postValue(data)
                        }
                            .subscribeOn(Schedulers.io())
                            .subscribe()


                    }

                    override fun onError(e: Throwable) {
                        progress.postValue(0)
                        Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {}
                })
        }

    }

    public fun getCustomerListWithoutLoading(
        id: Int,
        rettitory: String,
        brick: String,
        specialty: String,
        classs: String,
        filterText: String,
        cusIdes: String,
        cusBranchIds: String
    ) {

        if (dataManager.offlineMood) {

            Completable.fromAction {

                if (filterText.isNullOrBlank() || filterText == "0")
                    responseLive.postValue(listDao.all as ArrayList<ListEntity>)
                else
                    responseLive.postValue(listDao.getAllByText(filterText) as ArrayList<ListEntity>)
            }
                .subscribeOn(Schedulers.io())
                .subscribe()
        } else {
            myAPI?.SearchCustomer(
                id,
                rettitory,
                brick,
                specialty,
                classs,
                filterText,
                cusIdes,
                cusBranchIds
            )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<ListEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<ListEntity>) {
                        Completable.fromAction {


                            val list = listDao.getByName(filterText)

                            for (m in list) {
                                data.add(m)
                            }

                            listDao.insertAll(data)
                            Log.d(TAG, "onNext: " + list.size)
                            responseLive.postValue(data)
                        }
                            .subscribeOn(Schedulers.io())
                            .subscribe()

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {}
                })
        }


    }


    public fun getFilterLines(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String
    ) {


        System.out.println(" getFilterLines " + id + tableName + whereCondition + filterText)

        progress.postValue(1)
        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<FilterDataEntity>) {

                    progress.postValue(0)
                    filterLinesResponseLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                }

                override fun onComplete() {}
            })
    }


    public fun getFilterEmpl(
        id: String,
        tableName: String,
        whereCondition: String,
        filterText: String
    ) {

        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<FilterDataEntity>) {


                    filterEmployeeResponseLive.postValue(data)
                }

                override fun onError(e: Throwable) {

                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {}
            })
    }


    public fun getMeetingMembers() {


        progress.postValue(1)
        myAPI?.getMeetingMember(dataManager.user.empId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<FilterDataEntity>) {

                    progress.postValue(0)
                    filterEmployeeResponseLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)

                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {}
            })
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

    public fun getFilterSpeciality(
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
                    filterSpecialityResponseLive.postValue(data)
                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                }

                override fun onComplete() {}
            })
    }

    public fun getFilterSpecialityChild(
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
                    filterSpecialityResponseLiveChild.postValue(data)
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


    public fun getDoubleListList(
        selectedEmpAccountId: String,
        DayDateInMsFormat: String,
        ShiftId: String
    ) {

        System.out.println("selectedEmpAccountId : " + selectedEmpAccountId + "DayDateInMsFormat : " + DayDateInMsFormat + "ShiftId : " + ShiftId)

        progress.postValue(1)
        myAPI?.getEmployeePlan(selectedEmpAccountId, DayDateInMsFormat, ShiftId)!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<java.util.ArrayList<DoubleVisitEmp>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: java.util.ArrayList<DoubleVisitEmp>) {

                    progress.postValue(0)
                    responseDoubleVisitEmpLive.postValue(data)

                }

                override fun onError(e: Throwable) {
                    progress.postValue(0)
                    Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {}
            })

    }


}