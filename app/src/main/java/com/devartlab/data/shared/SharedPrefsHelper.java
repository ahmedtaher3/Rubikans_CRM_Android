package com.devartlab.data.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {
    public static final String CLevelName = "CLevelName";
    public static final String CYCLE = "CYCLE";
    public static final String ADS = "ADS";
    public static final String GoogleService = "GoogleService";
    public static final String DEVICE_TYPE = "DEVICE_TYPE";
    public static final String DisplayName = "DisplayName";
    public static final String EmpId = "EmpId";
    public static final String FIRST_TIME = "FIRST_TIME";
    public static final String IS_LOGGED = "IS_LOGGED";
    public static final String Supervisor = "Supervisor";
    public static final String MY_PREFS = "MY_PREFS";
    public static final String NewOldCycle = "NewOldCycle";
    public static final String OfflineMood = "OfflineMood";
    public static final String SERVER_DATE = "SERVER_DATE";
    public static final String SHIFT = "SHIFT";
    public static final String START_POINT = "START_POINT";
    public static final String START_SHIFT = "START_SHIFT";
    public static final String SavedData = "SavedData";
    public static final String MobileData = "MobileData";
    public static final String UpdatePlan = "UpdatePlan";
    public static final String UpdateDeletedPlan = "UpdateDeletedPlan";
    public static final String NewCycle = "SavedData";
    public static final String SyncAble = "SyncAble";
    public static final String Title = "Title";
    public static final String TitleID = "TitleID";
    public static final String USER = "USER";
    public static final String UserName = "UserName";
    public static final String UserPassword = "UserPassword";
    private static final String LANG = "LANG";
    private static final String Token = "Token";

    SharedPreferences mSharedPreferences;

    public SharedPrefsHelper(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(MY_PREFS, 0);
    }

    public void clear() {
        this.mSharedPreferences.edit().clear().apply();
    }

    public void putEmpId(int s) {
        this.mSharedPreferences.edit().putInt(TitleID, s).apply();
    }

    public int getEmpId() {
        return this.mSharedPreferences.getInt(EmpId, 0);
    }


    public void putNewCycle(boolean b) {
        this.mSharedPreferences.edit().putBoolean(NewCycle, b).apply();
    }

    public Boolean getNewCycle() {
        return this.mSharedPreferences.getBoolean(NewCycle, false);
    }


    public void putSavedData(boolean b) {
        this.mSharedPreferences.edit().putBoolean(SavedData, b).apply();
    }

    public Boolean getSavedData() {
        return this.mSharedPreferences.getBoolean(SavedData, false);
    }


    public void putToken(String b) {
        this.mSharedPreferences.edit().putString(Token, b).apply();
    }

    public String getToken() {
        return this.mSharedPreferences.getString(Token, "");
    }


    public void putMobileData(boolean b) {
        this.mSharedPreferences.edit().putBoolean(MobileData, b).apply();
    }

    public Boolean getMobileData() {
        return this.mSharedPreferences.getBoolean(MobileData, false);
    }


    public void putUpdatePlan(boolean b) {
        this.mSharedPreferences.edit().putBoolean(UpdatePlan, b).apply();
    }

    public Boolean getUpdatePlan() {
        return this.mSharedPreferences.getBoolean(UpdatePlan, false);
    }


    public void putUpdateDeletedPlan(boolean b) {
        this.mSharedPreferences.edit().putBoolean(UpdateDeletedPlan, b).apply();
    }

    public Boolean getUpdateDeletedPlan() {
        return this.mSharedPreferences.getBoolean(UpdateDeletedPlan, false);
    }

    public void putDeviceType(boolean b) {
        this.mSharedPreferences.edit().putBoolean(DEVICE_TYPE, b).apply();
    }

    public Boolean getDeviceType() {
        return this.mSharedPreferences.getBoolean(DEVICE_TYPE, false);
    }


    public void putGoogleService(boolean b) {
        this.mSharedPreferences.edit().putBoolean(GoogleService, b).apply();
    }

    public Boolean getGoogleService() {
        return this.mSharedPreferences.getBoolean(GoogleService, false);
    }


    public void putIsLogin(boolean b) {
        this.mSharedPreferences.edit().putBoolean(IS_LOGGED, b).apply();
    }

    public Boolean getIsLogin() {
        return this.mSharedPreferences.getBoolean(IS_LOGGED, false);
    }

    public void putSupervisor(boolean b) {
        this.mSharedPreferences.edit().putBoolean(Supervisor, b).apply();
    }

    public Boolean getSupervisor() {
        return this.mSharedPreferences.getBoolean(Supervisor, false);
    }

    public void putUser(String s) {
        this.mSharedPreferences.edit().putString(USER, s).apply();
    }

    public String getUser() {
        return this.mSharedPreferences.getString(USER, null);
    }

    public void putUserName(String s) {
        this.mSharedPreferences.edit().putString(UserName, s).apply();
    }

    public String getUserName() {
        return this.mSharedPreferences.getString(UserName, null);
    }

    public void putUserTitle(String s) {
        this.mSharedPreferences.edit().putString(Title, s).apply();
    }

    public String getUserTitle() {
        return this.mSharedPreferences.getString(Title, null);
    }

    public void putUserTitleID(String s) {
        this.mSharedPreferences.edit().putString(TitleID, s).apply();
    }

    public String getUserTitleID() {
        return this.mSharedPreferences.getString(TitleID, null);
    }

    public void putCLevelName(String s) {
        this.mSharedPreferences.edit().putString(CLevelName, s).apply();
    }

    public String getCLevelName() {
        return this.mSharedPreferences.getString(CLevelName, null);
    }

    public void putDisplayName(String s) {
        this.mSharedPreferences.edit().putString(DisplayName, s).apply();
    }

    public String getDisplayName() {
        return this.mSharedPreferences.getString(DisplayName, null);
    }

    public void putUserPassword(String s) {
        this.mSharedPreferences.edit().putString(UserPassword, s).apply();
    }

    public String getUserPassword() {
        return this.mSharedPreferences.getString(UserPassword, null);
    }

    public void putServerTime(String s) {
        this.mSharedPreferences.edit().putString(SERVER_DATE, s).apply();
    }

    public String getServerTime() {
        return this.mSharedPreferences.getString(SERVER_DATE, null);
    }

    public void putStartShift(boolean b) {
        this.mSharedPreferences.edit().putBoolean(START_SHIFT, b).apply();
    }

    public Boolean getStartShift() {
        return this.mSharedPreferences.getBoolean(START_SHIFT, false);
    }

    public void putFirstTime(boolean b) {
        this.mSharedPreferences.edit().putBoolean(FIRST_TIME, b).apply();
    }

    public Boolean getFirstTime() {
        return this.mSharedPreferences.getBoolean(FIRST_TIME, true);
    }

    public void putShift(String shift) {
        this.mSharedPreferences.edit().putString(SHIFT, shift).apply();
    }

    public String getShift() {
        return this.mSharedPreferences.getString(SHIFT, null);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void putCycle(String cycle) {
        this.mSharedPreferences.edit().putString(CYCLE, cycle).apply();
    }

    public String getCycle() {
        return this.mSharedPreferences.getString(CYCLE, null);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////


    public void putAds(String cycle) {
        this.mSharedPreferences.edit().putString(ADS, cycle).apply();
    }

    public String getAds() {
        return this.mSharedPreferences.getString(ADS, null);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void putStartPoint(String startPoint) {
        this.mSharedPreferences.edit().putString(START_POINT, startPoint).apply();
    }

    public String getStartPoint() {
        return this.mSharedPreferences.getString(START_POINT, null);
    }

    public void putNewOldCycle(String startPoint) {
        this.mSharedPreferences.edit().putString(NewOldCycle, startPoint).apply();
    }

    public String getNewOldCycle() {
        return this.mSharedPreferences.getString(NewOldCycle, null);
    }

    public void putSyncAble(boolean b) {
        this.mSharedPreferences.edit().putBoolean(SyncAble, b).apply();
    }

    public Boolean getSyncAble() {
        return Boolean.valueOf(this.mSharedPreferences.getBoolean(SyncAble, true));
    }

    public void putOfflineMood(boolean b) {
        this.mSharedPreferences.edit().putBoolean(OfflineMood, b).apply();
    }

    public Boolean getOfflineMood() {
        return Boolean.valueOf(this.mSharedPreferences.getBoolean(OfflineMood, false));
    }

    public void putLang(String name) {
        mSharedPreferences.edit().putString(LANG, name).apply();
    }

    public String getLang() {
        return mSharedPreferences.getString(LANG, "en");
    }
}
