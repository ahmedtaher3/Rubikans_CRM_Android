package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DTBankAccount {

    @SerializedName("Serial")
    @Expose
    private Integer serial;
    @SerializedName("BankSerial")
    @Expose
    private String bankSerial;
    @SerializedName("BankArName")
    @Expose
    private String bankArName;
    @SerializedName("BankAccount")
    @Expose
    private String bankAccount;
    @SerializedName("IsConstant")
    @Expose
    private Boolean isConstant;
    @SerializedName("SalaryQuota")
    @Expose
    private Double salaryQuota;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("BankId")
    @Expose
    private Integer bankId;
    @SerializedName("Mobid")
    @Expose
    private Integer mobid;

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getBankSerial() {
        return bankSerial;
    }

    public void setBankSerial(String bankSerial) {
        this.bankSerial = bankSerial;
    }

    public String getBankArName() {
        return bankArName;
    }

    public void setBankArName(String bankArName) {
        this.bankArName = bankArName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Boolean getIsConstant() {
        return isConstant;
    }

    public void setIsConstant(Boolean isConstant) {
        this.isConstant = isConstant;
    }

    public Double getSalaryQuota() {
        return salaryQuota;
    }

    public void setSalaryQuota(Double salaryQuota) {
        this.salaryQuota = salaryQuota;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getMobid() {
        return mobid;
    }

    public void setMobid(Integer mobid) {
        this.mobid = mobid;
    }

}
