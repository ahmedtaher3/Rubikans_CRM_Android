package com.devartlab.data.shared

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsHelper(context: Context) {
    var mSharedPreferences: SharedPreferences
    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    fun putEmpId(s: Int) {
        mSharedPreferences.edit().putInt(TitleID, s).apply()
    }

    val empId: Int
        get() = mSharedPreferences.getInt(EmpId, 0)

    fun putNewCycle(b: Boolean) {
        mSharedPreferences.edit().putBoolean(NewCycle, b).apply()
    }

    val newCycle: Boolean
        get() = mSharedPreferences.getBoolean(NewCycle, false)

    fun putSavedData(b: Boolean) {
        mSharedPreferences.edit().putBoolean(SavedData, b).apply()
    }

    val savedData: Boolean
        get() = mSharedPreferences.getBoolean(SavedData, false)

    fun putToken(b: String?) {
        mSharedPreferences.edit().putString(Token, b).apply()
    }

    val token: String?
        get() = mSharedPreferences.getString(Token, "")

    fun putMobileData(b: Boolean) {
        mSharedPreferences.edit().putBoolean(MobileData, b).apply()
    }

    val mobileData: Boolean
        get() = mSharedPreferences.getBoolean(MobileData, false)

    fun putUpdatePlan(b: Boolean) {
        mSharedPreferences.edit().putBoolean(UpdatePlan, b).apply()
    }

    val updatePlan: Boolean
        get() = mSharedPreferences.getBoolean(UpdatePlan, false)

    fun putUpdateDeletedPlan(b: Boolean) {
        mSharedPreferences.edit().putBoolean(UpdateDeletedPlan, b).apply()
    }

    val updateDeletedPlan: Boolean
        get() = mSharedPreferences.getBoolean(UpdateDeletedPlan, false)

    fun putDeviceType(b: Boolean) {
        mSharedPreferences.edit().putBoolean(DEVICE_TYPE, b).apply()
    }

    val deviceType: Boolean
        get() = mSharedPreferences.getBoolean(DEVICE_TYPE, false)

    fun putGoogleService(b: Boolean) {
        mSharedPreferences.edit().putBoolean(GoogleService, b).apply()
    }

    //////////////////////////////////////////////////////////////////////////////////

    fun putHuaweiToken(s: String) {
        mSharedPreferences!!.edit().putString(HuaweiToken, s).apply()
    }

    val huaweiToken: String?
        get() = mSharedPreferences!!.getString(HuaweiToken , "")
    //////////////////////////////////////////////////////////////////////////////////

    val googleService: Boolean
        get() = mSharedPreferences.getBoolean(GoogleService, false)

    fun putIsLogin(b: Boolean) {
        mSharedPreferences.edit().putBoolean(IS_LOGGED, b).apply()
    }

    val isLogin: Boolean
        get() = mSharedPreferences.getBoolean(IS_LOGGED, false)

    fun putSupervisor(b: Boolean) {
        mSharedPreferences.edit().putBoolean(Supervisor, b).apply()
    }

    val supervisor: Boolean
        get() = mSharedPreferences.getBoolean(Supervisor, false)

    fun putUser(s: String?) {
        mSharedPreferences.edit().putString(USER, s).apply()
    }

    val user: String?
        get() = mSharedPreferences.getString(USER, null)

    fun putUserName(s: String?) {
        mSharedPreferences.edit().putString(UserName, s).apply()
    }

    val userName: String?
        get() = mSharedPreferences.getString(UserName, null)

    fun putUserTitle(s: String?) {
        mSharedPreferences.edit().putString(Title, s).apply()
    }

    val userTitle: String?
        get() = mSharedPreferences.getString(Title, null)

    fun putUserTitleID(s: String?) {
        mSharedPreferences.edit().putString(TitleID, s).apply()
    }

    val userTitleID: String?
        get() = mSharedPreferences.getString(TitleID, null)

    fun putCLevelName(s: String?) {
        mSharedPreferences.edit().putString(CLevelName, s).apply()
    }

    val cLevelName: String?
        get() = mSharedPreferences.getString(CLevelName, null)

    fun putDisplayName(s: String?) {
        mSharedPreferences.edit().putString(DisplayName, s).apply()
    }

    val displayName: String?
        get() = mSharedPreferences.getString(DisplayName, null)

    fun putUserPassword(s: String?) {
        mSharedPreferences.edit().putString(UserPassword, s).apply()
    }

    val userPassword: String?
        get() = mSharedPreferences.getString(UserPassword, null)

    fun putServerTime(s: String?) {
        mSharedPreferences.edit().putString(SERVER_DATE, s).apply()
    }

    val serverTime: String?
        get() = mSharedPreferences.getString(SERVER_DATE, null)

    fun putStartShift(b: Boolean) {
        mSharedPreferences.edit().putBoolean(START_SHIFT, b).apply()
    }

    val startShift: Boolean
        get() = mSharedPreferences.getBoolean(START_SHIFT, false)

    fun putFirstTime(b: Boolean) {
        mSharedPreferences.edit().putBoolean(FIRST_TIME, b).apply()
    }

    val firstTime: Boolean
        get() = mSharedPreferences.getBoolean(FIRST_TIME, true)

    fun putShift(shift: String?) {
        mSharedPreferences.edit().putString(SHIFT, shift).apply()
    }

    val shift: String?
        get() = mSharedPreferences.getString(SHIFT, null)

    //////////////////////////////////////////////////////////////////////////////////////////////////
    fun putCycle(cycle: String?) {
        mSharedPreferences.edit().putString(CYCLE, cycle).apply()
    }

    val cycle: String?
        get() = mSharedPreferences.getString(CYCLE, null)

    //////////////////////////////////////////////////////////////////////////////////////////////////
    fun putAds(cycle: String?) {
        mSharedPreferences.edit().putString(ADS, cycle).apply()
    }

    val ads: String?
        get() = mSharedPreferences.getString(ADS, null)

    //////////////////////////////////////////////////////////////////////////////////////////////////
    fun putStartPoint(startPoint: String?) {
        mSharedPreferences.edit().putString(START_POINT, startPoint).apply()
    }

    val startPoint: String?
        get() = mSharedPreferences.getString(START_POINT, null)

    fun putNewOldCycle(startPoint: String?) {
        mSharedPreferences.edit().putString(NewOldCycle, startPoint).apply()
    }

    val newOldCycle: String?
        get() = mSharedPreferences.getString(NewOldCycle, null)

    fun putSyncAble(b: Boolean) {
        mSharedPreferences.edit().putBoolean(SyncAble, b).apply()
    }

    val syncAble: Boolean
        get() = java.lang.Boolean.valueOf(mSharedPreferences.getBoolean(SyncAble, true))

    fun putOfflineMood(b: Boolean) {
        mSharedPreferences.edit().putBoolean(OfflineMood, b).apply()
    }

    val offlineMood: Boolean
        get() = java.lang.Boolean.valueOf(mSharedPreferences.getBoolean(OfflineMood, false))

    fun putLang(name: String?) {
        mSharedPreferences.edit().putString(LANG, name).apply()
    }

    val lang: String?
        get() = mSharedPreferences.getString(LANG, "en")

    companion object {
        const val CLevelName = "CLevelName"
        const val CYCLE = "CYCLE"
        const val ADS = "ADS"
        const val GoogleService = "GoogleService"
        const val DEVICE_TYPE = "DEVICE_TYPE"
        const val DisplayName = "DisplayName"
        const val EmpId = "EmpId"
        const val FIRST_TIME = "FIRST_TIME"
        const val IS_LOGGED = "IS_LOGGED"
        const val Supervisor = "Supervisor"
        const val MY_PREFS = "MY_PREFS"
        const val NewOldCycle = "NewOldCycle"
        const val OfflineMood = "OfflineMood"
        const val SERVER_DATE = "SERVER_DATE"
        const val SHIFT = "SHIFT"
        const val START_POINT = "START_POINT"
        const val START_SHIFT = "START_SHIFT"
        const val SavedData = "SavedData"
        const val MobileData = "MobileData"
        const val UpdatePlan = "UpdatePlan"
        const val UpdateDeletedPlan = "UpdateDeletedPlan"
        const val NewCycle = "SavedData"
        const val SyncAble = "SyncAble"
        const val Title = "Title"
        const val TitleID = "TitleID"
        const val USER = "USER"
        const val UserName = "UserName"
        const val UserPassword = "UserPassword"
        private const val LANG = "LANG"
        private const val Token = "Token"
        private const val HuaweiToken = "HuaweiToken"
    }

    init {
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, 0)
    }
}