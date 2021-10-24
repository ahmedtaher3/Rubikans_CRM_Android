package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VacationType {

    @SerializedName("VacTypeId")
    @Expose
    private Integer vacTypeId;
    @SerializedName("VacTypeArName")
    @Expose
    private String vacTypeArName;
    @SerializedName("VacTypeEnName")
    @Expose
    private String vacTypeEnName;
    @SerializedName("WithSalary")
    @Expose
    private Boolean withSalary;
    @SerializedName("VacTypeMonthLimit")
    @Expose
    private Integer vacTypeMonthLimit;
    @SerializedName("MaxDayLimit")
    @Expose
    private Double maxDayLimit;
    @SerializedName("MaxRelatedDayPerOnce")
    @Expose
    private Double maxRelatedDayPerOnce;

    public Integer getVacTypeId() {
        return vacTypeId;
    }

    public void setVacTypeId(Integer vacTypeId) {
        this.vacTypeId = vacTypeId;
    }

    public String getVacTypeArName() {
        return vacTypeArName;
    }

    public void setVacTypeArName(String vacTypeArName) {
        this.vacTypeArName = vacTypeArName;
    }

    public String getVacTypeEnName() {
        return vacTypeEnName;
    }

    public void setVacTypeEnName(String vacTypeEnName) {
        this.vacTypeEnName = vacTypeEnName;
    }

    public Boolean getWithSalary() {
        return withSalary;
    }

    public void setWithSalary(Boolean withSalary) {
        this.withSalary = withSalary;
    }

    public Integer getVacTypeMonthLimit() {
        return vacTypeMonthLimit;
    }

    public void setVacTypeMonthLimit(Integer vacTypeMonthLimit) {
        this.vacTypeMonthLimit = vacTypeMonthLimit;
    }

    public Double getMaxDayLimit() {
        return maxDayLimit;
    }

    public void setMaxDayLimit(Double maxDayLimit) {
        this.maxDayLimit = maxDayLimit;
    }

    public Double getMaxRelatedDayPerOnce() {
        return maxRelatedDayPerOnce;
    }

    public void setMaxRelatedDayPerOnce(Double maxRelatedDayPerOnce) {
        this.maxRelatedDayPerOnce = maxRelatedDayPerOnce;
    }

}