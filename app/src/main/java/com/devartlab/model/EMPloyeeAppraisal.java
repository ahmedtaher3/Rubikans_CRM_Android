package com.devartlab.model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EMPloyeeAppraisal {

    @SerializedName("Empid")
    @Expose
    private Integer empid;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("EMPloyeeAppraisal")
    @Expose
    private ArrayList<EMPloyeeAppraisalQ> eMPloyeeAppraisal = null;

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public ArrayList<EMPloyeeAppraisalQ> getEMPloyeeAppraisal() {
        return eMPloyeeAppraisal;
    }

    public void setEMPloyeeAppraisal(ArrayList<EMPloyeeAppraisalQ> eMPloyeeAppraisal) {
        this.eMPloyeeAppraisal = eMPloyeeAppraisal;
    }

    @Override
    public String toString() {
        return "EMPloyeeAppraisal{" +
                "empid=" + empid +
                ", accountId=" + accountId +
                ", empName='" + empName + '\'' +
                ", eMPloyeeAppraisal=" + eMPloyeeAppraisal.toString() +
                '}';
    }
}