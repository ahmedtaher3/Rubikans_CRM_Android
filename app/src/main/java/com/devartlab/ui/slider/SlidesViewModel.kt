package com.devartlab.ui.slider
import com.devartlab.data.room.plan.PlanEntity

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.room.DatabaseClient
 import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.room.poduct.ProductRepository
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.data.room.slides.SlideRepository
import com.devartlab.data.room.slidetimer.SlideTimerRepository
import com.devartlab.data.shared.DataManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.room.arrangedslides.ArrangedSlidesDao
import com.devartlab.data.room.arrangedslides.ArrangedSlidesEntity
import com.devartlab.data.room.slidetimer.SlideTimerDao

class SlidesViewModel(application: Application) : AndroidViewModel(application) {

    val dataManager: DataManager





    var slideDao: SlideDao? = null
    var planDao: PlanDao? = null
    var slideTimerDao: SlideTimerDao? = null
    var arrangedSlidesDao: ArrangedSlidesDao? = null


    var allSlides: MutableLiveData<List<SlideEntity>>
    var allCustomeSlides: MutableLiveData<List<ArrangedSlidesEntity>>

    var planModel: MutableLiveData<PlanEntity>


    public val userName = ObservableField<String>()
    public val modifyMac = ObservableField<String>()


    init {
        dataManager = (getApplication() as BaseApplication).dataManager!!



        slideDao = DatabaseClient.getInstance(application)?.appDatabase?.slideDao()
        planDao = DatabaseClient.getInstance(application)?.appDatabase?.planDao()
        slideTimerDao = DatabaseClient.getInstance(application)?.appDatabase?.slideTimerDao()
        arrangedSlidesDao = DatabaseClient.getInstance(application)?.appDatabase?.arrangedSlidesDao()


         allSlides = MediatorLiveData()
        planModel = MediatorLiveData()
        allCustomeSlides = MediatorLiveData()


        userName.set(dataManager.user.userName)
        modifyMac.set(dataManager.user.nameAr)
    }


    fun getByID(id: String?) {
        Completable.fromAction {
            planModel.postValue(planDao?.getAllById(id))
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }


    fun updatePlan(plan: PlanEntity?) {


        Completable.fromAction {

            planDao?.update(plan)
            dataManager?.saveSyncAble(false)

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

    }


    fun getSlieds(id: Int) {

        Completable.fromAction {

            allSlides.postValue(slideDao?.getAllByID(id))

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun getCustomSlieds(arrangedID: Int) {

        Completable.fromAction {

            allCustomeSlides.postValue(arrangedSlidesDao?.getAll(arrangedID))

        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }


}