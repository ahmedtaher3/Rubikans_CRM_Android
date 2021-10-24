package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductTable  {

    @SerializedName("Table")
    @Expose
    private List<Product> table = null;
    @SerializedName("Table1")
    @Expose
    private List<Massage> table1 = null;
    @SerializedName("Table2")
    @Expose
    private List<MassageSlide> table2 = null;

    public List<Product> getTable() {
        return table;
    }

    public void setTable(List<Product> table) {
        this.table = table;
    }

    public List<Massage> getTable1() {
        return table1;
    }

    public void setTable1(List<Massage> table1) {
        this.table1 = table1;
    }

    public List<MassageSlide> getTable2() {
        return table2;
    }

    public void setTable2(List<MassageSlide> table2) {
        this.table2 = table2;
    }

}