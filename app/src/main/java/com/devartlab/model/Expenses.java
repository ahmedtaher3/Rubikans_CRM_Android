package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Expenses implements Serializable {
    @SerializedName("ExpId")
    @Expose
    private Integer expId;
    @SerializedName("ExpTypeId")
    @Expose
    private Integer expTypeId;
    @SerializedName("ExpSerial")
    @Expose
    private Object expSerial;
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("PreviousKiloMeterValue")
    @Expose
    private Integer previousKiloMeterValue;
    @SerializedName("CurrentKiloMeterValue")
    @Expose
    private Integer currentKiloMeterValue;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("Qty")
    @Expose
    private Double qty;
    @SerializedName("Value")
    @Expose
    private Double value;
    @SerializedName("TotalValue")
    @Expose
    private Double totalValue;
    @SerializedName("StorageLocation")
    @Expose
    private Object storageLocation;
    @SerializedName("Month")
    @Expose
    private Integer month;
    @SerializedName("Year")
    @Expose
    private Integer year;
    @SerializedName("Closed")
    @Expose
    private Boolean closed;
    @SerializedName("Imported")
    @Expose
    private Boolean imported;
    @SerializedName("CloudRef")
    @Expose
    private String cloudRef;
    @SerializedName("Deleted")
    @Expose
    private Object deleted;
    @SerializedName("CreatedMonth")
    @Expose
    private Integer createdMonth;
    @SerializedName("CreatedYear")
    @Expose
    private Integer createdYear;
    @SerializedName("AllowUpdate")
    @Expose
    private Boolean allowUpdate;
    @SerializedName("ExpTypeArName")
    @Expose
    private String expTypeArName;
    @SerializedName("ExpTypeIconUrl")
    @Expose
    private String expTypeIconUrl;
    @SerializedName("ExpStatus")
    @Expose
    private Integer expStatus;


    @SerializedName("PendingUser")
    @Expose
    private String pendingUser;


    public Expenses(Integer expId, Integer expTypeId, Object expSerial, Integer empId, String expDate, Integer previousKiloMeterValue, Integer currentKiloMeterValue, String notes, Double qty, Double value, Double totalValue, Object storageLocation, Integer month, Integer year, Boolean closed, Boolean imported, String cloudRef, Object deleted, Integer createdMonth, Integer createdYear, Boolean allowUpdate, String expTypeArName, String expTypeIconUrl, Integer expStatus, String pendingUser) {
        this.expId = expId;
        this.expTypeId = expTypeId;
        this.expSerial = expSerial;
        this.empId = empId;
        this.expDate = expDate;
        this.previousKiloMeterValue = previousKiloMeterValue;
        this.currentKiloMeterValue = currentKiloMeterValue;
        this.notes = notes;
        this.qty = qty;
        this.value = value;
        this.totalValue = totalValue;
        this.storageLocation = storageLocation;
        this.month = month;
        this.year = year;
        this.closed = closed;
        this.imported = imported;
        this.cloudRef = cloudRef;
        this.deleted = deleted;
        this.createdMonth = createdMonth;
        this.createdYear = createdYear;
        this.allowUpdate = allowUpdate;
        this.expTypeArName = expTypeArName;
        this.expTypeIconUrl = expTypeIconUrl;
        this.expStatus = expStatus;
        this.pendingUser = pendingUser;
    }

    public Expenses() {
    }

    public String getPendingUser() {
        return pendingUser;
    }

    public void setPendingUser(String pendingUser) {
        this.pendingUser = pendingUser;
    }


    public Integer getExpId() {
        return expId;
    }

    public void setExpId(Integer expId) {
        this.expId = expId;
    }

    public Integer getExpTypeId() {
        return expTypeId;
    }

    public void setExpTypeId(Integer expTypeId) {
        this.expTypeId = expTypeId;
    }

    public Object getExpSerial() {
        return expSerial;
    }

    public void setExpSerial(Object expSerial) {
        this.expSerial = expSerial;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public Integer getPreviousKiloMeterValue() {
        return previousKiloMeterValue;
    }

    public void setPreviousKiloMeterValue(Integer previousKiloMeterValue) {
        this.previousKiloMeterValue = previousKiloMeterValue;
    }

    public Integer getCurrentKiloMeterValue() {
        return currentKiloMeterValue;
    }

    public void setCurrentKiloMeterValue(Integer currentKiloMeterValue) {
        this.currentKiloMeterValue = currentKiloMeterValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Object getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(Object storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean getImported() {
        return imported;
    }

    public void setImported(Boolean imported) {
        this.imported = imported;
    }

    public String getCloudRef() {
        return cloudRef;
    }

    public void setCloudRef(String cloudRef) {
        this.cloudRef = cloudRef;
    }

    public Object getDeleted() {
        return deleted;
    }

    public void setDeleted(Object deleted) {
        this.deleted = deleted;
    }

    public Integer getCreatedMonth() {
        return createdMonth;
    }

    public void setCreatedMonth(Integer createdMonth) {
        this.createdMonth = createdMonth;
    }

    public Integer getCreatedYear() {
        return createdYear;
    }

    public void setCreatedYear(Integer createdYear) {
        this.createdYear = createdYear;
    }

    public Boolean getAllowUpdate() {
        return allowUpdate;
    }

    public void setAllowUpdate(Boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    public String getExpTypeArName() {
        return expTypeArName;
    }

    public void setExpTypeArName(String expTypeArName) {
        this.expTypeArName = expTypeArName;
    }

    public String getExpTypeIconUrl() {
        return expTypeIconUrl;
    }

    public void setExpTypeIconUrl(String expTypeIconUrl) {
        this.expTypeIconUrl = expTypeIconUrl;
    }

    public Integer getExpStatus() {
        return expStatus;
    }

    public void setExpStatus(Integer expStatus) {
        this.expStatus = expStatus;
    }

}