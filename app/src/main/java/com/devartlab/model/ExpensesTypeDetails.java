package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpensesTypeDetails {
    @SerializedName("CurrDate")
    @Expose
    private String currDate;
    @SerializedName("AllowToAddExpenses")
    @Expose
    private Boolean allowToAddExpenses;
    @SerializedName("LastKmValue")
    @Expose
    private Integer lastKmValue;

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public Boolean getAllowToAddExpenses() {
        return allowToAddExpenses;
    }

    public void setAllowToAddExpenses(Boolean allowToAddExpenses) {
        this.allowToAddExpenses = allowToAddExpenses;
    }

    public Integer getLastKmValue() {
        return lastKmValue;
    }

    public void setLastKmValue(Integer lastKmValue) {
        this.lastKmValue = lastKmValue;
    }

}
