package com.devartlab.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devartlab.data.room.activity.ActivityDao
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.arranged.ArrangedDao
import com.devartlab.data.room.arranged.ArrangedEntity
import com.devartlab.data.room.arrangedslides.ArrangedSlidesDao
import com.devartlab.data.room.arrangedslides.ArrangedSlidesEntity
import com.devartlab.data.room.authority.AuthorityDao
import com.devartlab.data.room.authority.AuthorityEntity
import com.devartlab.data.room.callslide.CallSlideDao
import com.devartlab.data.room.callslide.CallSlideEntity
import com.devartlab.data.room.collect.CollectDao
import com.devartlab.data.room.collect.CollectEntity
import com.devartlab.data.room.contract.ContractDao
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.data.room.filterdata.FilterDataDao
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.invoicedetailes.CustomerInvoiceDao
import com.devartlab.data.room.invoicedetailes.CustomerInvoiceEntity
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesDao
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.data.room.locations.LocationsDao
import com.devartlab.data.room.massages.MassageEntity
import com.devartlab.data.room.massages.MassagesDao
import com.devartlab.data.room.myballance.MyBallanceDao
import com.devartlab.data.room.myballance.MyBallanceEntity
import com.devartlab.data.room.plan.PlanDao
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.poduct.ProductDao
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.room.purchasetype.PurchaseTypeDao
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import com.devartlab.data.room.random.RandomDao
import com.devartlab.data.room.random.RandomEntity
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.data.room.slidetimer.SlideTimerDao
import com.devartlab.data.room.slidetimer.SlideTimerEntity
import com.devartlab.data.room.specialty.SpecialtyDao
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import com.devartlab.data.room.startPoint.StartPointDao
import com.devartlab.data.room.startPoint.StartPointEntity
import com.devartlab.data.room.tradedetails.TradeDetailsDao
import com.devartlab.data.room.tradedetails.TradeDetailsEntity
import com.devartlab.data.room.trademaster.TradeMasterDao
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.data.room.values.ValuesDao
import com.devartlab.data.room.values.ValuesEntity
import com.devartlab.data.room.visit.VisitDao
import com.devartlab.data.room.visit.VisitEntity


@Database(entities = [ProductEntity::class
    , SlideEntity::class
    , SlideTimerEntity::class
    , PlanEntity::class
    , VisitEntity::class
    , CallSlideEntity::class
    , ArrangedEntity::class
    , ArrangedSlidesEntity::class
    , MassageEntity::class
    , ListTypesEntity::class
    , ListEntity::class
    , ValuesEntity::class
    , AuthorityEntity::class
    , StartPointEntity::class
    , RandomEntity::class
    , TradeDetailsEntity::class
    , TradeMasterEntity::class
    , ActivityEntity::class
    , PurchaseTypeEntity::class
    , ContractEntity::class
    , SpecialtyParentEntity::class
    , FilterDataEntity::class
    , MyBallanceEntity::class
    , CollectEntity::class
    , CustomerInvoiceEntity::class],
          version = 10)
abstract class AppDatabase : RoomDatabase() {


    abstract fun productDao(): ProductDao
    abstract fun slideDao(): SlideDao
    abstract fun slideTimerDao(): SlideTimerDao
    abstract fun planDao(): PlanDao
    abstract fun authorityDao(): AuthorityDao
    abstract fun visitDao(): VisitDao
    abstract fun callSlideDao(): CallSlideDao
    abstract fun arrangedDao(): ArrangedDao
    abstract fun arrangedSlidesDao(): ArrangedSlidesDao
    abstract fun massagesDao(): MassagesDao
    abstract fun listTypesDao(): ListTypesDao
    abstract fun listDao(): ListDao
    abstract fun valuesDao(): ValuesDao
    abstract fun startPointDao(): StartPointDao
    abstract fun randomDao(): RandomDao
    abstract fun tradeMasterDao(): TradeMasterDao
    abstract fun tradeDetailsDao(): TradeDetailsDao
    abstract fun activityDao(): ActivityDao
    abstract fun purchaseTypeDao(): PurchaseTypeDao
    abstract fun contractDao(): ContractDao
    abstract fun specialtyDao(): SpecialtyDao
    abstract fun filterDataDao(): FilterDataDao
    abstract fun myBallanceDao(): MyBallanceDao
    abstract fun collectDao(): CollectDao
    abstract fun customerInvoiceDao(): CustomerInvoiceDao

}

