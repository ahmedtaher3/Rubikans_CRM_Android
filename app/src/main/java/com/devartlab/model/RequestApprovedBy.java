
package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestApprovedBy {

    @SerializedName("Create Date")
    @Expose
    private String createDate;
    @SerializedName("Approved Date")
    @Expose
    private Object approvedDate;
    @SerializedName("Employee")
    @Expose
    private String employee;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("Notes")
    @Expose
    private Object notes;
    @SerializedName("TotalCoast")
    @Expose
    private Object totalCoast;
    @SerializedName("TotalGain")
    @Expose
    private Object totalGain;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Object getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Object approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public Object getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(Object totalCoast) {
        this.totalCoast = totalCoast;
    }

    public Object getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(Object totalGain) {
        this.totalGain = totalGain;
    }

}
