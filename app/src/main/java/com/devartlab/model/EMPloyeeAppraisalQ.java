package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EMPloyeeAppraisalQ {

    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Item")
    @Expose
    private String item;
    @SerializedName("Rate")
    @Expose
    private Integer rate;
    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("DisplayOrder")
    @Expose
    private Integer displayOrder;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("SalesReportId")
    @Expose
    private Object salesReportId;
    @SerializedName("EMpId")
    @Expose
    private Object eMpId;
    @SerializedName("Managerid")
    @Expose
    private Object managerid;
    @SerializedName("CorrectiveActionId")
    @Expose
    private Integer correctiveActionId;
    @SerializedName("CorrectiveActionComment")
    @Expose
    private Object correctiveActionComment;
    @SerializedName("IsCorrectiveAction")
    @Expose
    private Boolean isCorrectiveAction;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Object getSalesReportId() {
        return salesReportId;
    }

    public void setSalesReportId(Object salesReportId) {
        this.salesReportId = salesReportId;
    }

    public Object getEMpId() {
        return eMpId;
    }

    public void setEMpId(Object eMpId) {
        this.eMpId = eMpId;
    }

    public Object getManagerid() {
        return managerid;
    }

    public void setManagerid(Object managerid) {
        this.managerid = managerid;
    }

    public Integer getCorrectiveActionId() {
        return correctiveActionId;
    }

    public void setCorrectiveActionId(Integer correctiveActionId) {
        this.correctiveActionId = correctiveActionId;
    }

    public Object getCorrectiveActionComment() {
        return correctiveActionComment;
    }

    public void setCorrectiveActionComment(Object correctiveActionComment) {
        this.correctiveActionComment = correctiveActionComment;
    }

    public Boolean getIsCorrectiveAction() {
        return isCorrectiveAction;
    }

    public void setIsCorrectiveAction(Boolean isCorrectiveAction) {
        this.isCorrectiveAction = isCorrectiveAction;
    }

    @Override
    public String toString() {
        return "EMPloyeeAppraisalQ{" +
                "category='" + category + '\'' +
                ", item='" + item + '\'' +
                ", rate=" + rate +
                ", itemId=" + itemId +
                ", displayOrder=" + displayOrder +
                ", accountId=" + accountId +
                ", salesReportId=" + salesReportId +
                ", eMpId=" + eMpId +
                ", managerid=" + managerid +
                ", correctiveActionId=" + correctiveActionId +
                ", correctiveActionComment=" + correctiveActionComment +
                ", isCorrectiveAction=" + isCorrectiveAction +
                '}';
    }
}