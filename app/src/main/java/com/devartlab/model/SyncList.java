package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncList {
    @SerializedName("ListDetId")
    @Expose
    private Integer listDetId;
    @SerializedName("ListId")
    @Expose
    private Integer listId;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("CusTypeId")
    @Expose
    private Integer cusTypeId;
    @SerializedName("CusClassId")
    @Expose
    private Integer cusClassId;
    @SerializedName("CustomerState")
    @Expose
    private Integer customerState;
    @SerializedName("AddDate")
    @Expose
    private String addDate;
    @SerializedName("DeleteDate")
    @Expose
    private Object deleteDate;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("DayID")
    @Expose
    private Object dayID;
    @SerializedName("TimeFrom")
    @Expose
    private Object timeFrom;
    @SerializedName("TimeTo")
    @Expose
    private Object timeTo;
    @SerializedName("AssigntId")
    @Expose
    private Integer assigntId;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("DataLoaded")
    @Expose
    private Boolean dataLoaded;

    public SyncList(Integer listDetId, Integer listId, Integer customerId, Integer branchId, Integer cusTypeId, Integer cusClassId, Integer customerState, String addDate, Object deleteDate, String notes, Object dayID, Object timeFrom, Object timeTo, Integer assigntId, Integer accountId, Boolean dataLoaded) {
        this.listDetId = listDetId;
        this.listId = listId;
        this.customerId = customerId;
        this.branchId = branchId;
        this.cusTypeId = cusTypeId;
        this.cusClassId = cusClassId;
        this.customerState = customerState;
        this.addDate = addDate;
        this.deleteDate = deleteDate;
        this.notes = notes;
        this.dayID = dayID;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.assigntId = assigntId;
        this.accountId = accountId;
        this.dataLoaded = dataLoaded;
    }

    public Integer getListDetId() {
        return listDetId;
    }

    public void setListDetId(Integer listDetId) {
        this.listDetId = listDetId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getCusTypeId() {
        return cusTypeId;
    }

    public void setCusTypeId(Integer cusTypeId) {
        this.cusTypeId = cusTypeId;
    }

    public Integer getCusClassId() {
        return cusClassId;
    }

    public void setCusClassId(Integer cusClassId) {
        this.cusClassId = cusClassId;
    }

    public Integer getCustomerState() {
        return customerState;
    }

    public void setCustomerState(Integer customerState) {
        this.customerState = customerState;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public Object getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Object deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Object getDayID() {
        return dayID;
    }

    public void setDayID(Object dayID) {
        this.dayID = dayID;
    }

    public Object getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Object timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Object getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Object timeTo) {
        this.timeTo = timeTo;
    }

    public Integer getAssigntId() {
        return assigntId;
    }

    public void setAssigntId(Integer assigntId) {
        this.assigntId = assigntId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(Boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }

}
