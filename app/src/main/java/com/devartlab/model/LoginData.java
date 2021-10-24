package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserEmpId")
    @Expose
    private Integer userEmpId;
    @SerializedName("EmpArName")
    @Expose
    private String empArName;
    @SerializedName("JobArName")
    @Expose
    private String jobArName;
    @SerializedName("JobEnName")
    @Expose
    private String jobEnName;
    @SerializedName("AllowToUpdatePermission")
    @Expose
    private Boolean allowToUpdatePermission;
    @SerializedName("UserPassword")
    @Expose
    private String userPassword;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("FileImage")
    @Expose
    private String fileImage;
    @SerializedName("ManagerId")
    @Expose
    private Integer managerId;
    @SerializedName("VersionName")
    @Expose
    private String versionName;
    @SerializedName("VersionCode")
    @Expose
    private Integer versionCode;
    @SerializedName("LastVersionAvailableDay")
    @Expose
    private Integer lastVersionAvailableDay;
    @SerializedName("MaxDate")
    @Expose
    private String maxDate;
    @SerializedName("MinDate")
    @Expose
    private String minDate;
    @SerializedName("CurrentDayDate")
    @Expose
    private String currentDayDate;
    @SerializedName("MaxDateMs")
    @Expose
    private long maxDateMs;
    @SerializedName("MinDateMs")
    @Expose
    private long minDateMs;
    @SerializedName("CurrentDayDateMs")
    @Expose
    private long currentDayDateMs;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(Integer userEmpId) {
        this.userEmpId = userEmpId;
    }

    public String getEmpArName() {
        return empArName;
    }

    public void setEmpArName(String empArName) {
        this.empArName = empArName;
    }

    public String getJobArName() {
        return jobArName;
    }

    public void setJobArName(String jobArName) {
        this.jobArName = jobArName;
    }

    public String getJobEnName() {
        return jobEnName;
    }

    public void setJobEnName(String jobEnName) {
        this.jobEnName = jobEnName;
    }

    public Boolean getAllowToUpdatePermission() {
        return allowToUpdatePermission;
    }

    public void setAllowToUpdatePermission(Boolean allowToUpdatePermission) {
        this.allowToUpdatePermission = allowToUpdatePermission;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getLastVersionAvailableDay() {
        return lastVersionAvailableDay;
    }

    public void setLastVersionAvailableDay(Integer lastVersionAvailableDay) {
        this.lastVersionAvailableDay = lastVersionAvailableDay;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getCurrentDayDate() {
        return currentDayDate;
    }

    public void setCurrentDayDate(String currentDayDate) {
        this.currentDayDate = currentDayDate;
    }

    public long getMaxDateMs() {
        return maxDateMs;
    }

    public void setMaxDateMs(long maxDateMs) {
        this.maxDateMs = maxDateMs;
    }

    public long getMinDateMs() {
        return minDateMs;
    }

    public void setMinDateMs(long minDateMs) {
        this.minDateMs = minDateMs;
    }

    public long getCurrentDayDateMs() {
        return currentDayDateMs;
    }

    public void setCurrentDayDateMs(long currentDayDateMs) {
        this.currentDayDateMs = currentDayDateMs;
    }

}