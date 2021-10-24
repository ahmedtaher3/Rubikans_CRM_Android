package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDefEmployee {

    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("EmpSerial")
    @Expose
    private String empSerial;
    @SerializedName("EmpArName")
    @Expose
    private String empArName;
    @SerializedName("EmpEnName")
    @Expose
    private String empEnName;
    @SerializedName("JobSerial")
    @Expose
    private String jobSerial;
    @SerializedName("EmpArTitle")
    @Expose
    private String empArTitle;
    @SerializedName("EmpEnTitle")
    @Expose
    private String empEnTitle;
    @SerializedName("MangerArName")
    @Expose
    private String mangerArName;
    @SerializedName("EmpTerriotry")
    @Expose
    private String empTerriotry;
    @SerializedName("LineSerial")
    @Expose
    private String lineSerial;
    @SerializedName("LineArName")
    @Expose
    private String lineArName;
    @SerializedName("LineEnName")
    @Expose
    private String lineEnName;
    @SerializedName("EmpLine")
    @Expose
    private Integer empLine;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("AssignId")
    @Expose
    private Integer assignId;
    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("ManagerId")
    @Expose
    private Integer managerId;
    @SerializedName("jobtId")
    @Expose
    private Integer jobtId;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpSerial() {
        return empSerial;
    }

    public void setEmpSerial(String empSerial) {
        this.empSerial = empSerial;
    }

    public String getEmpArName() {
        return empArName;
    }

    public void setEmpArName(String empArName) {
        this.empArName = empArName;
    }

    public String getEmpEnName() {
        return empEnName;
    }

    public void setEmpEnName(String empEnName) {
        this.empEnName = empEnName;
    }

    public String getJobSerial() {
        return jobSerial;
    }

    public void setJobSerial(String jobSerial) {
        this.jobSerial = jobSerial;
    }

    public String getEmpArTitle() {
        return empArTitle;
    }

    public void setEmpArTitle(String empArTitle) {
        this.empArTitle = empArTitle;
    }

    public String getEmpEnTitle() {
        return empEnTitle;
    }

    public void setEmpEnTitle(String empEnTitle) {
        this.empEnTitle = empEnTitle;
    }

    public String getMangerArName() {
        return mangerArName;
    }

    public void setMangerArName(String mangerArName) {
        this.mangerArName = mangerArName;
    }

    public String getEmpTerriotry() {
        return empTerriotry;
    }

    public void setEmpTerriotry(String empTerriotry) {
        this.empTerriotry = empTerriotry;
    }

    public String getLineSerial() {
        return lineSerial;
    }

    public void setLineSerial(String lineSerial) {
        this.lineSerial = lineSerial;
    }

    public String getLineArName() {
        return lineArName;
    }

    public void setLineArName(String lineArName) {
        this.lineArName = lineArName;
    }

    public String getLineEnName() {
        return lineEnName;
    }

    public void setLineEnName(String lineEnName) {
        this.lineEnName = lineEnName;
    }

    public Integer getEmpLine() {
        return empLine;
    }

    public void setEmpLine(Integer empLine) {
        this.empLine = empLine;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getJobtId() {
        return jobtId;
    }

    public void setJobtId(Integer jobtId) {
        this.jobtId = jobtId;
    }

}
