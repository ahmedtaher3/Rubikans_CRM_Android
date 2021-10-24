package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncSlide {

    @SerializedName("PlanDetId")
    @Expose
    private Integer planDetId;
    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("SalesRptDate")
    @Expose
    private String salesRptDate;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("IsExtraVisit")
    @Expose
    private boolean isExtraVisit;
    @SerializedName("CallIndex")
    @Expose
    private Integer callIndex;
    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("IsCustomMessage")
    @Expose
    private Boolean isCustomMessage;
    @SerializedName("CustomMessageNotes")
    @Expose
    private String customMessageNotes;
    @SerializedName("SlideTimeinSec")
    @Expose
    private Integer slideTimeinSec;
    @SerializedName("SlideId")
    @Expose
    private Integer slideId;

    public Integer getPlanDetId() {
        return planDetId;
    }

    public void setPlanDetId(Integer planDetId) {
        this.planDetId = planDetId;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public String getSalesRptDate() {
        return salesRptDate;
    }

    public void setSalesRptDate(String salesRptDate) {
        this.salesRptDate = salesRptDate;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public boolean getIsExtraVisit() {
        return isExtraVisit;
    }

    public void setIsExtraVisit(boolean isExtraVisit) {
        this.isExtraVisit = isExtraVisit;
    }

    public Integer getCallIndex() {
        return callIndex;
    }

    public void setCallIndex(Integer callIndex) {
        this.callIndex = callIndex;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Boolean getIsCustomMessage() {
        return isCustomMessage;
    }

    public void setIsCustomMessage(Boolean isCustomMessage) {
        this.isCustomMessage = isCustomMessage;
    }

    public String getCustomMessageNotes() {
        return customMessageNotes;
    }

    public void setCustomMessageNotes(String customMessageNotes) {
        this.customMessageNotes = customMessageNotes;
    }

    public Integer getSlideTimeinSec() {
        return slideTimeinSec;
    }

    public void setSlideTimeinSec(Integer slideTimeinSec) {
        this.slideTimeinSec = slideTimeinSec;
    }

    public Integer getSlideId() {
        return slideId;
    }

    public void setSlideId(Integer slideId) {
        this.slideId = slideId;
    }

    public SyncSlide(Integer planDetId, Integer shiftId, String salesRptDate, Integer accountId, Integer customerId, Integer branchId, boolean isExtraVisit, Integer callIndex, Integer itemId, Integer messageId, Boolean isCustomMessage, String customMessageNotes, Integer slideTimeinSec, Integer slideId) {
        this.planDetId = planDetId;
        this.shiftId = shiftId;
        this.salesRptDate = salesRptDate;
        this.accountId = accountId;
        this.customerId = customerId;
        this.branchId = branchId;
        this.isExtraVisit = isExtraVisit;
        this.callIndex = callIndex;
        this.itemId = itemId;
        this.messageId = messageId;
        this.isCustomMessage = isCustomMessage;
        this.customMessageNotes = customMessageNotes;
        this.slideTimeinSec = slideTimeinSec;
        this.slideId = slideId;
    }
}
