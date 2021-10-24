
package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCostItem {

    @SerializedName("CoastItemArName")
    @Expose
    private String coastItemArName;
    @SerializedName("Qty")
    @Expose
    private Double qty;
    @SerializedName("Value")
    @Expose
    private Double value;
    @SerializedName("TotalValue")
    @Expose
    private Double totalValue;
    @SerializedName("ExchangDate")
    @Expose
    private String exchangDate;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("Column1")
    @Expose
    private String column1;
    @SerializedName("CustodyEMpId")
    @Expose
    private Integer custodyEMpId;
    @SerializedName("PayMethodId")
    @Expose
    private Integer payMethodId;
    @SerializedName("PayMethodDesc")
    @Expose
    private String payMethodDesc;
    @SerializedName("Payed")
    @Expose
    private Boolean payed;
    @SerializedName("PayedValue")
    @Expose
    private Object payedValue;
    @SerializedName("IsPurchasingItem")
    @Expose
    private Object isPurchasingItem;
    @SerializedName("MaxValue")
    @Expose
    private Object maxValue;

    public String getCoastItemArName() {
        return coastItemArName;
    }

    public void setCoastItemArName(String coastItemArName) {
        this.coastItemArName = coastItemArName;
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

    public String getExchangDate() {
        return exchangDate;
    }

    public void setExchangDate(String exchangDate) {
        this.exchangDate = exchangDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public Integer getCustodyEMpId() {
        return custodyEMpId;
    }

    public void setCustodyEMpId(Integer custodyEMpId) {
        this.custodyEMpId = custodyEMpId;
    }

    public Integer getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(Integer payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getPayMethodDesc() {
        return payMethodDesc;
    }

    public void setPayMethodDesc(String payMethodDesc) {
        this.payMethodDesc = payMethodDesc;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public Object getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(Object payedValue) {
        this.payedValue = payedValue;
    }

    public Object getIsPurchasingItem() {
        return isPurchasingItem;
    }

    public void setIsPurchasingItem(Object isPurchasingItem) {
        this.isPurchasingItem = isPurchasingItem;
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

}
