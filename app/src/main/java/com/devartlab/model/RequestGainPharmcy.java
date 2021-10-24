
package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGainPharmcy {

    @SerializedName("CustomerArName")
    @Expose
    private String customerArName;
    @SerializedName("Quantity")
    @Expose
    private Object quantity;
    @SerializedName("TotalPharmacyPrice")
    @Expose
    private Object totalPharmacyPrice;
    @SerializedName("TotalXFactor")
    @Expose
    private Object totalXFactor;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("MarReqDetRowIndex")
    @Expose
    private Integer marReqDetRowIndex;
    @SerializedName("IsLead")
    @Expose
    private Integer isLead;

    public String getCustomerArName() {
        return customerArName;
    }

    public void setCustomerArName(String customerArName) {
        this.customerArName = customerArName;
    }

    public Object getQuantity() {
        return quantity;
    }

    public void setQuantity(Object quantity) {
        this.quantity = quantity;
    }

    public Object getTotalPharmacyPrice() {
        return totalPharmacyPrice;
    }

    public void setTotalPharmacyPrice(Object totalPharmacyPrice) {
        this.totalPharmacyPrice = totalPharmacyPrice;
    }

    public Object getTotalXFactor() {
        return totalXFactor;
    }

    public void setTotalXFactor(Object totalXFactor) {
        this.totalXFactor = totalXFactor;
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

    public Integer getMarReqDetRowIndex() {
        return marReqDetRowIndex;
    }

    public void setMarReqDetRowIndex(Integer marReqDetRowIndex) {
        this.marReqDetRowIndex = marReqDetRowIndex;
    }

    public Integer getIsLead() {
        return isLead;
    }

    public void setIsLead(Integer isLead) {
        this.isLead = isLead;
    }

}
