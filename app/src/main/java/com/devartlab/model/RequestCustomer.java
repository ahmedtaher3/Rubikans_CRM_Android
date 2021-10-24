
package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCustomer {

    @SerializedName("CustomerEnName")
    @Expose
    private String customerEnName;
    @SerializedName("CusTypeEnName")
    @Expose
    private String cusTypeEnName;
    @SerializedName("CusClassEnName")
    @Expose
    private String cusClassEnName;
    @SerializedName("PlaceName")
    @Expose
    private Object placeName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("BranchTel1")
    @Expose
    private String branchTel1;
    @SerializedName("BranchTel2")
    @Expose
    private String branchTel2;
    @SerializedName("ProductBtn")
    @Expose
    private String productBtn;
    @SerializedName("ProductBtn1")
    @Expose
    private String productBtn1;
    @SerializedName("TotalGain")
    @Expose
    private Double totalGain;
    @SerializedName("ProductStr")
    @Expose
    private String productStr;
    @SerializedName("UnitsStr")
    @Expose
    private String unitsStr;
    @SerializedName("PharmaciesIdStr")
    @Expose
    private String pharmaciesIdStr;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("AssignId")
    @Expose
    private Integer assignId;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("MarReqDetRowIndex")
    @Expose
    private Integer marReqDetRowIndex;

    public String getCustomerEnName() {
        return customerEnName;
    }

    public void setCustomerEnName(String customerEnName) {
        this.customerEnName = customerEnName;
    }

    public String getCusTypeEnName() {
        return cusTypeEnName;
    }

    public void setCusTypeEnName(String cusTypeEnName) {
        this.cusTypeEnName = cusTypeEnName;
    }

    public String getCusClassEnName() {
        return cusClassEnName;
    }

    public void setCusClassEnName(String cusClassEnName) {
        this.cusClassEnName = cusClassEnName;
    }

    public Object getPlaceName() {
        return placeName;
    }

    public void setPlaceName(Object placeName) {
        this.placeName = placeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranchTel1() {
        return branchTel1;
    }

    public void setBranchTel1(String branchTel1) {
        this.branchTel1 = branchTel1;
    }

    public String getBranchTel2() {
        return branchTel2;
    }

    public void setBranchTel2(String branchTel2) {
        this.branchTel2 = branchTel2;
    }

    public String getProductBtn() {
        return productBtn;
    }

    public void setProductBtn(String productBtn) {
        this.productBtn = productBtn;
    }

    public String getProductBtn1() {
        return productBtn1;
    }

    public void setProductBtn1(String productBtn1) {
        this.productBtn1 = productBtn1;
    }

    public Double getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(Double totalGain) {
        this.totalGain = totalGain;
    }

    public String getProductStr() {
        return productStr;
    }

    public void setProductStr(String productStr) {
        this.productStr = productStr;
    }

    public String getUnitsStr() {
        return unitsStr;
    }

    public void setUnitsStr(String unitsStr) {
        this.unitsStr = unitsStr;
    }

    public String getPharmaciesIdStr() {
        return pharmaciesIdStr;
    }

    public void setPharmaciesIdStr(String pharmaciesIdStr) {
        this.pharmaciesIdStr = pharmaciesIdStr;
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

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getMarReqDetRowIndex() {
        return marReqDetRowIndex;
    }

    public void setMarReqDetRowIndex(Integer marReqDetRowIndex) {
        this.marReqDetRowIndex = marReqDetRowIndex;
    }

}
