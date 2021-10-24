
package com.devartlab.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGainDetail {

    @SerializedName("ItemEnName")
    @Expose
    @Nullable
    private String itemEnName;
    @SerializedName("Qty")
    @Expose
    @Nullable
    private Integer qty;
    @SerializedName("ProdPharmacyPrice")
    @Expose
    @Nullable
    private Double prodPharmacyPrice;
    @SerializedName("ProdXfactor")
    @Expose
    @Nullable
    private Double prodXfactor;
    @SerializedName("TotalPharmacyPrice")
    @Expose
    @Nullable
    private Double totalPharmacyPrice;
    @SerializedName("TotalXfactor")
    @Expose
    @Nullable
    private Double totalXfactor;
    @SerializedName("ProdId")
    @Expose
    @Nullable
    private Integer prodId;
    @SerializedName("MarReqDetRowIndex")
    @Expose
    @Nullable
    private Integer marReqDetRowIndex;

    @Nullable
    public String getItemEnName() {
        return itemEnName;
    }

    public void setItemEnName(@Nullable String itemEnName) {
        this.itemEnName = itemEnName;
    }

    @Nullable
    public Integer getQty() {
        return qty;
    }

    public void setQty(@Nullable Integer qty) {
        this.qty = qty;
    }

    @Nullable
    public Double getProdPharmacyPrice() {
        return prodPharmacyPrice;
    }

    public void setProdPharmacyPrice(@Nullable Double prodPharmacyPrice) {
        this.prodPharmacyPrice = prodPharmacyPrice;
    }

    @Nullable
    public Double getProdXfactor() {
        return prodXfactor;
    }

    public void setProdXfactor(@Nullable Double prodXfactor) {
        this.prodXfactor = prodXfactor;
    }

    @Nullable
    public Double getTotalPharmacyPrice() {
        return totalPharmacyPrice;
    }

    public void setTotalPharmacyPrice(@Nullable Double totalPharmacyPrice) {
        this.totalPharmacyPrice = totalPharmacyPrice;
    }

    @Nullable
    public Double getTotalXfactor() {
        return totalXfactor;
    }

    public void setTotalXfactor(@Nullable Double totalXfactor) {
        this.totalXfactor = totalXfactor;
    }

    @Nullable
    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(@Nullable Integer prodId) {
        this.prodId = prodId;
    }

    @Nullable
    public Integer getMarReqDetRowIndex() {
        return marReqDetRowIndex;
    }

    public void setMarReqDetRowIndex(@Nullable Integer marReqDetRowIndex) {
        this.marReqDetRowIndex = marReqDetRowIndex;
    }
}
