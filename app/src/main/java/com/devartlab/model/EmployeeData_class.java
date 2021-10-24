package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeData_class {

    @SerializedName("EMpId")
    @Expose
    private int EMpId;

    @SerializedName("DeptArName")
    @Expose
    private String DeptArName;

    @SerializedName("JobArName")
    @Expose
    private String JobArName;

    @SerializedName("EmpNickName")
    @Expose
    private String EmpNickName;

    @SerializedName("RegulationDesc")
    @Expose
    private String RegulationDesc;

    @SerializedName("TotalDeduction")
    @Expose
    private String TotalDeduction;

    public EmployeeData_class(int EMpId, String deptArName, String jobArName, String empNickName, String regulationDesc, String totalDeduction, int empRegularityId) {
        this.EMpId = EMpId;
        DeptArName = deptArName;
        JobArName = jobArName;
        EmpNickName = empNickName;
        RegulationDesc = regulationDesc;
        TotalDeduction = totalDeduction;
        EmpRegularityId = empRegularityId;
    }

    public EmployeeData_class() {
    }

    public int getEMpId() {
        return EMpId;
    }

    public void setEMpId(int EMpId) {
        this.EMpId = EMpId;
    }

    public String getDeptArName() {
        return DeptArName;
    }

    public void setDeptArName(String deptArName) {
        DeptArName = deptArName;
    }

    public String getJobArName() {
        return JobArName;
    }

    public void setJobArName(String jobArName) {
        JobArName = jobArName;
    }

    public String getEmpNickName() {
        return EmpNickName;
    }

    public void setEmpNickName(String empNickName) {
        EmpNickName = empNickName;
    }

    public String getRegulationDesc() {
        return RegulationDesc;
    }

    public void setRegulationDesc(String regulationDesc) {
        RegulationDesc = regulationDesc;
    }

    public String getTotalDeduction() {
        return TotalDeduction;
    }

    public void setTotalDeduction(String totalDeduction) {
        TotalDeduction = totalDeduction;
    }

    public int getEmpRegularityId() {
        return EmpRegularityId;
    }

    public void setEmpRegularityId(int empRegularityId) {
        EmpRegularityId = empRegularityId;
    }

    @SerializedName("EmpRegularityId")
    @Expose
    private int EmpRegularityId;

}
