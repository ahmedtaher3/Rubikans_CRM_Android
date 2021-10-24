package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpensesType {
    @SerializedName("ExpTypeArName")
    @Expose
    private String expTypeArName;
    @SerializedName("ExpTypeId")
    @Expose
    private Integer expTypeId;
    @SerializedName("ExpTypeIconUrl")
    @Expose
    private String expTypeIconUrl;
    @SerializedName("MaxValueLimit")
    @Expose
    private Double maxValueLimit;
    @SerializedName("ConstantValue")
    @Expose
    private Double constantValue;

    public String getExpTypeArName() {
        return expTypeArName;
    }

    public void setExpTypeArName(String expTypeArName) {
        this.expTypeArName = expTypeArName;
    }

    public Integer getExpTypeId() {
        return expTypeId;
    }

    public void setExpTypeId(Integer expTypeId) {
        this.expTypeId = expTypeId;
    }

    public String getExpTypeIconUrl() {
        return expTypeIconUrl;
    }

    public void setExpTypeIconUrl(String expTypeIconUrl) {
        this.expTypeIconUrl = expTypeIconUrl;
    }

    public Double getMaxValueLimit() {
        return maxValueLimit;
    }

    public void setMaxValueLimit(Double maxValueLimit) {
        this.maxValueLimit = maxValueLimit;
    }

    public Double getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(Double constantValue) {
        this.constantValue = constantValue;
    }

}
