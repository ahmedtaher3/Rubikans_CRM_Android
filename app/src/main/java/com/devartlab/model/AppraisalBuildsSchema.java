package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppraisalBuildsSchema {

    @SerializedName("DisplayOrder")
    @Expose
    private Integer displayOrder;
    @SerializedName("SalesReportId")
    @Expose
    private Integer salesReportId;
    @SerializedName("EMpId")
    @Expose
    private Integer eMpId;
    @SerializedName("Managerid")
    @Expose
    private Integer managerid;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
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
    @SerializedName("CorrectiveActionId")
    @Expose
    private Integer correctiveActionId;
    @SerializedName("CorrectiveActionComment")
    @Expose
    private String correctiveActionComment;
    @SerializedName("ISCorrectiveAction")
    @Expose
    private boolean iSCorrectiveAction;

    public AppraisalBuildsSchema(Integer displayOrder, Integer salesReportId, Integer eMpId, Integer managerid, Integer accountId, String category, String item, Integer rate, Integer itemId, Integer correctiveActionId, String correctiveActionComment, boolean iSCorrectiveAction) {
        this.displayOrder = displayOrder;
        this.salesReportId = salesReportId;
        this.eMpId = eMpId;
        this.managerid = managerid;
        this.accountId = accountId;
        this.category = category;
        this.item = item;
        this.rate = rate;
        this.itemId = itemId;
        this.correctiveActionId = correctiveActionId;
        this.correctiveActionComment = correctiveActionComment;
        this.iSCorrectiveAction = iSCorrectiveAction;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getSalesReportId() {
        return salesReportId;
    }

    public void setSalesReportId(Integer salesReportId) {
        this.salesReportId = salesReportId;
    }

    public Integer getEMpId() {
        return eMpId;
    }

    public void setEMpId(Integer eMpId) {
        this.eMpId = eMpId;
    }

    public Integer getManagerid() {
        return managerid;
    }

    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

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

    public Integer getCorrectiveActionId() {
        return correctiveActionId;
    }

    public void setCorrectiveActionId(Integer correctiveActionId) {
        this.correctiveActionId = correctiveActionId;
    }

    public String getCorrectiveActionComment() {
        return correctiveActionComment;
    }

    public void setCorrectiveActionComment(String correctiveActionComment) {
        this.correctiveActionComment = correctiveActionComment;
    }

    public boolean getISCorrectiveAction() {
        return iSCorrectiveAction;
    }

    public void setISCorrectiveAction(boolean iSCorrectiveAction) {
        this.iSCorrectiveAction = iSCorrectiveAction;
    }

}