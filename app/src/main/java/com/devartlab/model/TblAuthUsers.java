package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TblAuthUsers {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserEmpId")
    @Expose
    private Integer userEmpId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserPassword")
    @Expose
    private String userPassword;
    @SerializedName("AuthorityId")
    @Expose
    private Integer authorityId;
    @SerializedName("AllowDelete")
    @Expose
    private Boolean allowDelete;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("PassWordExpireDate")
    @Expose
    private Object passWordExpireDate;
    @SerializedName("LoginFromTime")
    @Expose
    private Object loginFromTime;
    @SerializedName("LoginToTime")
    @Expose
    private Object loginToTime;
    @SerializedName("UsernameVersion")
    @Expose
    private Integer usernameVersion;
    @SerializedName("AddUserId")
    @Expose
    private Object addUserId;
    @SerializedName("AddMAc")
    @Expose
    private Object addMAc;
    @SerializedName("AddDateTime")
    @Expose
    private Object addDateTime;
    @SerializedName("ModifyUserId")
    @Expose
    private Integer modifyUserId;
    @SerializedName("ModifyMac")
    @Expose
    private String modifyMac;
    @SerializedName("ModifyDatetime")
    @Expose
    private String modifyDatetime;
    @SerializedName("RoleId")
    @Expose
    private Integer roleId;
    @SerializedName("ReportPolicy")
    @Expose
    private Integer reportPolicy;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public Boolean getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getPassWordExpireDate() {
        return passWordExpireDate;
    }

    public void setPassWordExpireDate(Object passWordExpireDate) {
        this.passWordExpireDate = passWordExpireDate;
    }

    public Object getLoginFromTime() {
        return loginFromTime;
    }

    public void setLoginFromTime(Object loginFromTime) {
        this.loginFromTime = loginFromTime;
    }

    public Object getLoginToTime() {
        return loginToTime;
    }

    public void setLoginToTime(Object loginToTime) {
        this.loginToTime = loginToTime;
    }

    public Integer getUsernameVersion() {
        return usernameVersion;
    }

    public void setUsernameVersion(Integer usernameVersion) {
        this.usernameVersion = usernameVersion;
    }

    public Object getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Object addUserId) {
        this.addUserId = addUserId;
    }

    public Object getAddMAc() {
        return addMAc;
    }

    public void setAddMAc(Object addMAc) {
        this.addMAc = addMAc;
    }

    public Object getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(Object addDateTime) {
        this.addDateTime = addDateTime;
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyMac() {
        return modifyMac;
    }

    public void setModifyMac(String modifyMac) {
        this.modifyMac = modifyMac;
    }

    public String getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(String modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getReportPolicy() {
        return reportPolicy;
    }

    public void setReportPolicy(Integer reportPolicy) {
        this.reportPolicy = reportPolicy;
    }

}
