package com.devartlab.model;

public class User {
    int accId;
    String additionalAccIds;
    int authorityId;
    int empId;
    int userStore;
    String image;
    String levelName;
    String nameAr;
    String nameEn;
    String password;
    String versionName;
    String title;
    int titleId;
    int managerId;
    String userName;
    long minDate;
    long maxDate;
    boolean openReportLimit;
    boolean allowToUpdatePlan;
    long serverDate;

    public String getNameAr() {
        return this.nameAr;
    }

    public void setNameAr(String nameAr2) {
        this.nameAr = nameAr2;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public void setNameEn(String nameEn2) {
        this.nameEn = nameEn2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public void setLevelName(String levelName2) {
        this.levelName = levelName2;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image2) {
        this.image = image2;
    }

    public int getEmpId() {
        return this.empId;
    }

    public void setEmpId(int empId2) {
        this.empId = empId2;
    }

    public int getAccId() {
        return this.accId;
    }

    public int getAuthorityId() {
        return this.authorityId;
    }

    public void setAuthorityId(int authorityId2) {
        this.authorityId = authorityId2;
    }

    public void setAccId(int accId2) {
        this.accId = accId2;
    }

    public String getAdditionalAccIds() {
        return this.additionalAccIds;
    }

    public void setAdditionalAccIds(String additionalAccIds2) {
        this.additionalAccIds = additionalAccIds2;
    }

    public int getTitleId() {
        return this.titleId;
    }

    public void setTitleId(int titleId2) {
        this.titleId = titleId2;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public long getMinDate() {
        return minDate;
    }

    public void setMinDate(long minDate) {
        this.minDate = minDate;
    }

    public long getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(long maxDate) {
        this.maxDate = maxDate;
    }

    public boolean isOpenReportLimit() {
        return openReportLimit;
    }

    public void setOpenReportLimit(boolean openReportLimit) {
        this.openReportLimit = openReportLimit;
    }

    public boolean isAllowToUpdatePlan() {
        return allowToUpdatePlan;
    }

    public void setAllowToUpdatePlan(boolean allowToUpdatePlan) {
        this.allowToUpdatePlan = allowToUpdatePlan;
    }

    public long getServerDate() {
        return serverDate;
    }

    public void setServerDate(long serverDate) {
        this.serverDate = serverDate;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getStoreId() {
        return userStore;
    }

    public void setStoreId(int userStore) {
        this.userStore = userStore;
    }
}
