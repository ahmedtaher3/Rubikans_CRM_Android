package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SyncPlanResponse{

    @SerializedName("Table1")
    @Expose
    private List<Response> table1 = null;
    @SerializedName("Table")
    @Expose
    private List<PlanModel> table = null;

    public List<Response> getTable1() {
        return table1;
    }

    public void setTable1(List<Response> table1) {
        this.table1 = table1;
    }

    public List<PlanModel> getTable() {
        return table;
    }

    public void setTable(List<PlanModel> table) {
        this.table = table;
    }

}