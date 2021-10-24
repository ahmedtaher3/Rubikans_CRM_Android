package com.devartlab.ui.main.ui.callmanagement.report.arrange

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.arranged.ArrangedDao
import com.devartlab.data.room.arranged.ArrangedEntity
import com.devartlab.data.room.poduct.ProductDao
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.shared.DataManager
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.room.arrangedslides.ArrangedSlidesDao
import com.devartlab.data.room.arrangedslides.ArrangedSlidesEntity
import com.devartlab.data.room.massages.MassagesDao
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.model.SlideModel

class ArrangeViewModel(application: Application) : AndroidViewModel(application) {


    var allProducts: LiveData<List<ArrangedEntity>>
    var productDao: ProductDao
    var massagesDao: MassagesDao
    var slideDao: SlideDao
    var arrangedSlidesDao: ArrangedSlidesDao
    var arrangedDao: ArrangedDao? = null


    val dataManager: DataManager
    val responseLive: MutableLiveData<List<ArrangedEntity>>
    val progress: MutableLiveData<Int>


    init {

        slideDao = DatabaseClient.getInstance(application)?.appDatabase?.slideDao()!!
        arrangedSlidesDao = DatabaseClient.getInstance(application)?.appDatabase?.arrangedSlidesDao()!!
        arrangedDao = DatabaseClient.getInstance(application)?.appDatabase?.arrangedDao()!!
        massagesDao = DatabaseClient.getInstance(application)?.appDatabase?.massagesDao()!!
        dataManager = (getApplication() as BaseApplication).dataManager!!
        responseLive = MutableLiveData<List<ArrangedEntity>>()
        progress = MutableLiveData<Int>()
        productDao = DatabaseClient.getInstance(application)?.appDatabase?.productDao()!!

        allProducts = MutableLiveData()


    }


    fun getAllProducts(id: Int) {


        Completable.fromAction {

            responseLive.postValue(arrangedDao?.getAll(id))

        }.subscribeOn(Schedulers.io())
                .subscribe()


    }


    fun insertCustomMassage(arrangedEntity: ArrangedEntity, id: Int, slides: List<SlideModel>) {
        Completable.fromAction {

            val arrangedID = arrangedDao?.insert(arrangedEntity)

            for (model in slides) {

                if (model.checked) {
                    arrangedSlidesDao?.insert(ArrangedSlidesEntity(
                            arrangedID?.toInt(),
                            model.id,
                            model.slideName,
                            model.slideUrl,
                            model.converted,
                            model.base64
                    ))
                }

            }


            responseLive.postValue(arrangedDao?.getAll(id))
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }


    fun editCustomMassage(id: Int, arrangedId: Int, slides: List<SlideModel>) {
        Completable.fromAction {

            arrangedSlidesDao.deleteById(arrangedId)

            for (model in slides) {

                arrangedSlidesDao?.insert(ArrangedSlidesEntity(
                        arrangedId,
                        model.id,
                        model.slideName,
                        model.slideUrl,
                        model.converted,
                        model.base64))

            }


            responseLive.postValue(arrangedDao?.getAll(id))
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }


    fun deleteCustomMassage(arrangedEntity: ArrangedEntity, id: Int) {
        Completable.fromAction {
            arrangedDao?.delete(arrangedEntity)
            responseLive.postValue(arrangedDao?.getAll(id))
        }.subscribeOn(Schedulers.io())
                .subscribe()
    }


}