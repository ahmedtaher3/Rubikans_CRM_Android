package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalaryItem {
    @SerializedName("DuesDeductionItemId")
    @Expose
    private Integer duesDeductionItemId;
    @SerializedName("DuesDeductionEnName")
    @Expose
    private String duesDeductionEnName;
    @SerializedName("TotalValue")
    @Expose
    private Double totalValue;
    @SerializedName("DisplayOrder")
    @Expose
    private Integer displayOrder;

    public Integer getDuesDeductionItemId() {
        return duesDeductionItemId;
    }

    public void setDuesDeductionItemId(Integer duesDeductionItemId) {
        this.duesDeductionItemId = duesDeductionItemId;
    }

    public String getDuesDeductionEnName() {
        return duesDeductionEnName;
    }

    public void setDuesDeductionEnName(String duesDeductionEnName) {
        this.duesDeductionEnName = duesDeductionEnName;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}
