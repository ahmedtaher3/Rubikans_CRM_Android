package com.devartlab.data.shared

import com.devartlab.model.*
import com.google.gson.Gson

class DataManager(var mSharedPrefsHelper: SharedPrefsHelper) {
    fun clear() {
        mSharedPrefsHelper.clear()
    }

    fun isSavedData(b: Boolean) {
        mSharedPrefsHelper.putSavedData(b)
    }

    val savedData: Boolean
        get() = mSharedPrefsHelper.savedData




    fun saveToken(b: String) {
        mSharedPrefsHelper.putToken(b)
    }

    val token: String
        get() = mSharedPrefsHelper.token!!



    ////////////////////////////////////////////////////////////////////////////////


    fun saveDeviceToken(b: String) {
        mSharedPrefsHelper.putDeviceToken(b)
    }

    val deviceToken: String?
        get() = mSharedPrefsHelper.deviceToken

    ////////////////////////////////////////////////////////////////////////////////


    fun isMobileData(b: Boolean) {
        mSharedPrefsHelper.putMobileData(b)
    }

    val mobileData: Boolean
        get() = mSharedPrefsHelper.mobileData


    fun isUpdatePlan(b: Boolean) {
        mSharedPrefsHelper.putUpdatePlan(b)
    }

    val updatePlan: Boolean
        get() = mSharedPrefsHelper.updatePlan



    fun isUpdateDeletedPlan(b: Boolean) {
        mSharedPrefsHelper.putUpdateDeletedPlan(b)
    }

    val updateDeletedPlan: Boolean
        get() = mSharedPrefsHelper.updateDeletedPlan



    fun isNewCycle(b: Boolean) {
        mSharedPrefsHelper.putNewCycle(b)
    }

    val newCycle: Boolean
        get() = mSharedPrefsHelper.newCycle




    fun saveUser(s: User?) {
        mSharedPrefsHelper.putUser(Gson().toJson(s as Any?))
    }

    val user: User
        get() {
            mSharedPrefsHelper.user
             return Gson().fromJson(mSharedPrefsHelper.user, User::class.java) as User
        }

    fun saveGoogleService(s: Boolean) {
        mSharedPrefsHelper.putGoogleService(s)
    }

    val googleService: Boolean
        get() = mSharedPrefsHelper.googleService


    fun saveTablet(s: Boolean) {
        mSharedPrefsHelper.putDeviceType(s)
    }

    val isTablet: Boolean
        get() = mSharedPrefsHelper.deviceType

    fun saveIsLogin(b: Boolean) {
        mSharedPrefsHelper.putIsLogin(b)
    }

    val isLogin: Boolean
        get() = mSharedPrefsHelper.isLogin


    fun saveIsSupervisor(b: Boolean) {
        mSharedPrefsHelper.putSupervisor(b)
    }

    val isSupervisor: Boolean
        get() = mSharedPrefsHelper.supervisor



    fun saveServerTime(s: String?) {
        mSharedPrefsHelper.putServerTime(s)
    }

    val serverTime: String
        get() = mSharedPrefsHelper.serverTime!!

    fun saveStartShift(b: Boolean , t:Int) {
        mSharedPrefsHelper.putStartShift(b)
        System.out.println(" saveStartShift "+t.toString())
    }

    val startShift: Boolean
        get() = mSharedPrefsHelper.startShift


    //////////////////////////////////////////////////////////////////////////////////////////////////

    fun saveFirstTime(b: Boolean) {
        mSharedPrefsHelper.putFirstTime(b)
    }

    val sFirstTime: Boolean
        get() = mSharedPrefsHelper.firstTime

    //////////////////////////////////////////////////////////////////////////////////////////////////

    fun saveShift(s: Shift?) {
        mSharedPrefsHelper.putShift(Gson().toJson(s as Any?))
    }

    val shift: Shift
        get() = Gson().fromJson(mSharedPrefsHelper.shift, Shift::class.java) as Shift


    //////////////////////////////////////////////////////////////////////////////////////////////////

    fun saveCycle(s: Cycle?) {
        mSharedPrefsHelper.putCycle(Gson().toJson(s as Any?))
    }

    val cycle: Cycle
        get() = Gson().fromJson(mSharedPrefsHelper.cycle, Cycle::class.java) as Cycle

    //////////////////////////////////////////////////////////////////////////////////////////////////

    fun saveAds(s: AdModelList) {
        mSharedPrefsHelper.putAds(Gson().toJson(s as Any?))
    }

    val ads: AdModelList
        get() = Gson().fromJson(mSharedPrefsHelper.ads, AdModelList::class.java) as AdModelList


    //////////////////////////////////////////////////////////////////////////////////////////////////


    fun saveStartPoint(s: StartPoint?) {
        mSharedPrefsHelper.putStartPoint(Gson().toJson(s as Any?))
    }

    val startPoint: StartPoint
        get() = Gson().fromJson(mSharedPrefsHelper.startPoint, StartPoint::class.java) as StartPoint

    fun saveNewOldCycle(s: CycleDatum?) {
        mSharedPrefsHelper.putNewOldCycle(Gson().toJson(s as Any?))
    }

    val newOldCycle: CycleDatum
        get() = Gson().fromJson(mSharedPrefsHelper.newOldCycle, CycleDatum::class.java) as CycleDatum

    fun saveSyncAble(b: Boolean) {
        mSharedPrefsHelper.putSyncAble(b)
    }

    val syncAble: Boolean
        get() = mSharedPrefsHelper.syncAble

    fun saveOfflineMood(b: Boolean) {
        mSharedPrefsHelper.putOfflineMood(b)
    }

    val offlineMood: Boolean
        get() = mSharedPrefsHelper.offlineMood


    fun saveLang(name: String?) {
        mSharedPrefsHelper.putLang(name)
    }

    fun getLang(): String? {
        return mSharedPrefsHelper.lang
    }
}