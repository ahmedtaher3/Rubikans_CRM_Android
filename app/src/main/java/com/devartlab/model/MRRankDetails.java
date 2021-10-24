package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MRRankDetails {

    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("VisitId")
    @Expose
    private Integer visitId;
    @SerializedName("TerrEmpId")
    @Expose
    private Integer terrEmpId;
    @SerializedName("TerrAssignId")
    @Expose
    private Integer terrAssignId;
    @SerializedName("TerrAccountId")
    @Expose
    private Integer terrAccountId;
    @SerializedName("LineEnName")
    @Expose
    private String lineEnName;
    @SerializedName("EMpName")
    @Expose
    private String eMpName;
    @SerializedName("SalTerriotryEnName")
    @Expose
    private String salTerriotryEnName;
    @SerializedName("TotalList")
    @Expose
    private Integer totalList;
    @SerializedName("TOtalPlanned")
    @Expose
    private Integer tOtalPlanned;
    @SerializedName("ListClassName")
    @Expose
    private String listClassName;
    @SerializedName("CusClassDisplayOrder")
    @Expose
    private Integer cusClassDisplayOrder;
    @SerializedName("TotalClass")
    @Expose
    private Integer totalClass;
    @SerializedName("VisitedPlanned")
    @Expose
    private Integer visitedPlanned;
    @SerializedName("VistiedTotal")
    @Expose
    private Integer vistiedTotal;
    @SerializedName("Visitedconfirmed")
    @Expose
    private Integer visitedconfirmed;
    @SerializedName("VistiedExtra")
    @Expose
    private Integer vistiedExtra;
    @SerializedName("TOtalUnCoverd")
    @Expose
    private Integer tOtalUnCoverd;
    @SerializedName("IsListClass")
    @Expose
    private Boolean isListClass;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public Integer getTerrEmpId() {
        return terrEmpId;
    }

    public void setTerrEmpId(Integer terrEmpId) {
        this.terrEmpId = terrEmpId;
    }

    public Integer getTerrAssignId() {
        return terrAssignId;
    }

    public void setTerrAssignId(Integer terrAssignId) {
        this.terrAssignId = terrAssignId;
    }

    public Integer getTerrAccountId() {
        return terrAccountId;
    }

    public void setTerrAccountId(Integer terrAccountId) {
        this.terrAccountId = terrAccountId;
    }

    public String getLineEnName() {
        return lineEnName;
    }

    public void setLineEnName(String lineEnName) {
        this.lineEnName = lineEnName;
    }

    public String getEMpName() {
        return eMpName;
    }

    public void setEMpName(String eMpName) {
        this.eMpName = eMpName;
    }

    public String getSalTerriotryEnName() {
        return salTerriotryEnName;
    }

    public void setSalTerriotryEnName(String salTerriotryEnName) {
        this.salTerriotryEnName = salTerriotryEnName;
    }

    public Integer getTotalList() {
        return totalList;
    }

    public void setTotalList(Integer totalList) {
        this.totalList = totalList;
    }

    public Integer getTOtalPlanned() {
        return tOtalPlanned;
    }

    public void setTOtalPlanned(Integer tOtalPlanned) {
        this.tOtalPlanned = tOtalPlanned;
    }

    public String getListClassName() {
        return listClassName;
    }

    public void setListClassName(String listClassName) {
        this.listClassName = listClassName;
    }

    public Integer getCusClassDisplayOrder() {
        return cusClassDisplayOrder;
    }

    public void setCusClassDisplayOrder(Integer cusClassDisplayOrder) {
        this.cusClassDisplayOrder = cusClassDisplayOrder;
    }

    public Integer getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(Integer totalClass) {
        this.totalClass = totalClass;
    }

    public Integer getVisitedPlanned() {
        return visitedPlanned;
    }

    public void setVisitedPlanned(Integer visitedPlanned) {
        this.visitedPlanned = visitedPlanned;
    }

    public Integer getVistiedTotal() {
        return vistiedTotal;
    }

    public void setVistiedTotal(Integer vistiedTotal) {
        this.vistiedTotal = vistiedTotal;
    }

    public Integer getVisitedconfirmed() {
        return visitedconfirmed;
    }

    public void setVisitedconfirmed(Integer visitedconfirmed) {
        this.visitedconfirmed = visitedconfirmed;
    }

    public Integer getVistiedExtra() {
        return vistiedExtra;
    }

    public void setVistiedExtra(Integer vistiedExtra) {
        this.vistiedExtra = vistiedExtra;
    }

    public Integer getTOtalUnCoverd() {
        return tOtalUnCoverd;
    }

    public void setTOtalUnCoverd(Integer tOtalUnCoverd) {
        this.tOtalUnCoverd = tOtalUnCoverd;
    }

    public Boolean getIsListClass() {
        return isListClass;
    }

    public void setIsListClass(Boolean isListClass) {
        this.isListClass = isListClass;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}