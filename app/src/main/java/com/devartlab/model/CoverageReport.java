package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverageReport {

    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("CusClassId")
    @Expose
    private Integer cusClassId;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("List")
    @Expose
    private Integer list;
    @SerializedName("Visited")
    @Expose
    private Integer visited;
    @SerializedName("Coverage")
    @Expose
    private String coverage;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCusClassId() {
        return cusClassId;
    }

    public void setCusClassId(Integer cusClassId) {
        this.cusClassId = cusClassId;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Integer getList() {
        return list;
    }

    public void setList(Integer list) {
        this.list = list;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

}
