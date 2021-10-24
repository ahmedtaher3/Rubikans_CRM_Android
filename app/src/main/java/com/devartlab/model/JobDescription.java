
package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobDescription {

    @SerializedName("CLevelId")
    @Expose
    private Integer cLevelId;
    @SerializedName("CLevelSerial")
    @Expose
    private Integer cLevelSerial;
    @SerializedName("CLevelName")
    @Expose
    private String cLevelName;
    @SerializedName("ShowAllArea")
    @Expose
    private Boolean showAllArea;
    @SerializedName("CLevelAreaTypeId")
    @Expose
    private Integer cLevelAreaTypeId;
    @SerializedName("DoCusList")
    @Expose
    private Boolean doCusList;
    @SerializedName("IsMultiLine")
    @Expose
    private Boolean isMultiLine;
    @SerializedName("AdditionalAccountIdStr")
    @Expose
    private String additionalAccountIdStr;
    @SerializedName("ShowAditionalRoot")
    @Expose
    private Boolean showAditionalRoot;
    @SerializedName("WeeklyVacationDaysStr")
    @Expose
    private String weeklyVacationDaysStr;
    @SerializedName("AddUserId")
    @Expose
    private Object addUserId;
    @SerializedName("AddMAc")
    @Expose
    private Object addMAc;
    @SerializedName("AddDateTime")
    @Expose
    private Object addDateTime;
    @SerializedName("ModifyUserId")
    @Expose
    private Integer modifyUserId;
    @SerializedName("ModifyMac")
    @Expose
    private String modifyMac;
    @SerializedName("ModifyDatetime")
    @Expose
    private String modifyDatetime;

    public Integer getCLevelId() {
        return cLevelId;
    }

    public void setCLevelId(Integer cLevelId) {
        this.cLevelId = cLevelId;
    }

    public Integer getCLevelSerial() {
        return cLevelSerial;
    }

    public void setCLevelSerial(Integer cLevelSerial) {
        this.cLevelSerial = cLevelSerial;
    }

    public String getCLevelName() {
        return cLevelName;
    }

    public void setCLevelName(String cLevelName) {
        this.cLevelName = cLevelName;
    }

    public Boolean getShowAllArea() {
        return showAllArea;
    }

    public void setShowAllArea(Boolean showAllArea) {
        this.showAllArea = showAllArea;
    }

    public Integer getCLevelAreaTypeId() {
        return cLevelAreaTypeId;
    }

    public void setCLevelAreaTypeId(Integer cLevelAreaTypeId) {
        this.cLevelAreaTypeId = cLevelAreaTypeId;
    }

    public Boolean getDoCusList() {
        return doCusList;
    }

    public void setDoCusList(Boolean doCusList) {
        this.doCusList = doCusList;
    }

    public Boolean getIsMultiLine() {
        return isMultiLine;
    }

    public void setIsMultiLine(Boolean isMultiLine) {
        this.isMultiLine = isMultiLine;
    }

    public String getAdditionalAccountIdStr() {
        return additionalAccountIdStr;
    }

    public void setAdditionalAccountIdStr(String additionalAccountIdStr) {
        this.additionalAccountIdStr = additionalAccountIdStr;
    }

    public Boolean getShowAditionalRoot() {
        return showAditionalRoot;
    }

    public void setShowAditionalRoot(Boolean showAditionalRoot) {
        this.showAditionalRoot = showAditionalRoot;
    }

    public String getWeeklyVacationDaysStr() {
        return weeklyVacationDaysStr;
    }

    public void setWeeklyVacationDaysStr(String weeklyVacationDaysStr) {
        this.weeklyVacationDaysStr = weeklyVacationDaysStr;
    }

    public Object getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Object addUserId) {
        this.addUserId = addUserId;
    }

    public Object getAddMAc() {
        return addMAc;
    }

    public void setAddMAc(Object addMAc) {
        this.addMAc = addMAc;
    }

    public Object getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(Object addDateTime) {
        this.addDateTime = addDateTime;
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyMac() {
        return modifyMac;
    }

    public void setModifyMac(String modifyMac) {
        this.modifyMac = modifyMac;
    }

    public String getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(String modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

}
