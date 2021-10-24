
package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaDatum {

    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("ManagerId")
    @Expose
    private Integer managerId;
    @SerializedName("EmpArName")
    @Expose
    private String empArName;
    @SerializedName("AccDescription")
    @Expose
    private String accDescription;
    @SerializedName("LineId")
    @Expose
    private Integer lineId;
    @SerializedName("AssignId")
    @Expose
    private Integer assignId;
    @SerializedName("ParentAccountId")
    @Expose
    private Integer parentAccountId;
    @SerializedName("TerriotryId")
    @Expose
    private Integer terriotryId;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("IsVacant")
    @Expose
    private Boolean isVacant;
    @SerializedName("AccSerial")
    @Expose
    private Integer accSerial;
    @SerializedName("PersonalTel")
    @Expose
    private String personalTel;
    @SerializedName("PersonalTel2")
    @Expose
    private String personalTel2;
    @SerializedName("TerritoryType")
    @Expose
    private Integer territoryType;
    @SerializedName("SalTerriotryArName")
    @Expose
    private String salTerriotryArName;
    @SerializedName("TerritoryParentId")
    @Expose
    private Integer territoryParentId;
    @SerializedName("IsRepTerritory")
    @Expose
    private Boolean isRepTerritory;
    @SerializedName("Princibals")
    @Expose
    private Boolean princibals;
    @SerializedName("IsCrossLine")
    @Expose
    private Boolean isCrossLine;
    @SerializedName("TerritoryTypeName")
    @Expose
    private String territoryTypeName;
    @SerializedName("JobId")
    @Expose
    private Integer jobId;
    @SerializedName("LineArName")
    @Expose
    private String lineArName;
    @SerializedName("LineEnName")
    @Expose
    private String lineEnName;
    @SerializedName("EmpNickName")
    @Expose
    private String empNickName;
    @SerializedName("IsAssistantAssign")
    @Expose
    private Boolean isAssistantAssign;
    @SerializedName("AdditionalAccountIdStr")
    @Expose
    private String additionalAccountIdStr;
    @SerializedName("PromoPlanId")
    @Expose
    private Integer promoPlanId;
    @SerializedName("PromoPlanDescription")
    @Expose
    private String promoPlanDescription;
    @SerializedName("PromoPlanNotes")
    @Expose
    private Object promoPlanNotes;
    @SerializedName("CycleDays")
    @Expose
    private Integer cycleDays;
    @SerializedName("WorkingDays")
    @Expose
    private Integer workingDays;
    @SerializedName("TotalList")
    @Expose
    private Integer totalList;
    @SerializedName("TotalVisits")
    @Expose
    private Integer totalVisits;
    @SerializedName("TotalDayVisits")
    @Expose
    private Integer totalDayVisits;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getEmpArName() {
        return empArName;
    }

    public void setEmpArName(String empArName) {
        this.empArName = empArName;
    }

    public String getAccDescription() {
        return accDescription;
    }

    public void setAccDescription(String accDescription) {
        this.accDescription = accDescription;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public Integer getParentAccountId() {
        return parentAccountId;
    }

    public void setParentAccountId(Integer parentAccountId) {
        this.parentAccountId = parentAccountId;
    }

    public Integer getTerriotryId() {
        return terriotryId;
    }

    public void setTerriotryId(Integer terriotryId) {
        this.terriotryId = terriotryId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }

    public Integer getAccSerial() {
        return accSerial;
    }

    public void setAccSerial(Integer accSerial) {
        this.accSerial = accSerial;
    }

    public String getPersonalTel() {
        return personalTel;
    }

    public void setPersonalTel(String personalTel) {
        this.personalTel = personalTel;
    }

    public String getPersonalTel2() {
        return personalTel2;
    }

    public void setPersonalTel2(String personalTel2) {
        this.personalTel2 = personalTel2;
    }

    public Integer getTerritoryType() {
        return territoryType;
    }

    public void setTerritoryType(Integer territoryType) {
        this.territoryType = territoryType;
    }

    public String getSalTerriotryArName() {
        return salTerriotryArName;
    }

    public void setSalTerriotryArName(String salTerriotryArName) {
        this.salTerriotryArName = salTerriotryArName;
    }

    public Integer getTerritoryParentId() {
        return territoryParentId;
    }

    public void setTerritoryParentId(Integer territoryParentId) {
        this.territoryParentId = territoryParentId;
    }

    public Boolean getIsRepTerritory() {
        return isRepTerritory;
    }

    public void setIsRepTerritory(Boolean isRepTerritory) {
        this.isRepTerritory = isRepTerritory;
    }

    public Boolean getPrincibals() {
        return princibals;
    }

    public void setPrincibals(Boolean princibals) {
        this.princibals = princibals;
    }

    public Boolean getIsCrossLine() {
        return isCrossLine;
    }

    public void setIsCrossLine(Boolean isCrossLine) {
        this.isCrossLine = isCrossLine;
    }

    public String getTerritoryTypeName() {
        return territoryTypeName;
    }

    public void setTerritoryTypeName(String territoryTypeName) {
        this.territoryTypeName = territoryTypeName;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getLineArName() {
        return lineArName;
    }

    public void setLineArName(String lineArName) {
        this.lineArName = lineArName;
    }

    public String getLineEnName() {
        return lineEnName;
    }

    public void setLineEnName(String lineEnName) {
        this.lineEnName = lineEnName;
    }

    public String getEmpNickName() {
        return empNickName;
    }

    public void setEmpNickName(String empNickName) {
        this.empNickName = empNickName;
    }

    public Boolean getIsAssistantAssign() {
        return isAssistantAssign;
    }

    public void setIsAssistantAssign(Boolean isAssistantAssign) {
        this.isAssistantAssign = isAssistantAssign;
    }

    public String getAdditionalAccountIdStr() {
        return additionalAccountIdStr;
    }

    public void setAdditionalAccountIdStr(String additionalAccountIdStr) {
        this.additionalAccountIdStr = additionalAccountIdStr;
    }

    public Integer getPromoPlanId() {
        return promoPlanId;
    }

    public void setPromoPlanId(Integer promoPlanId) {
        this.promoPlanId = promoPlanId;
    }

    public String getPromoPlanDescription() {
        return promoPlanDescription;
    }

    public void setPromoPlanDescription(String promoPlanDescription) {
        this.promoPlanDescription = promoPlanDescription;
    }

    public Object getPromoPlanNotes() {
        return promoPlanNotes;
    }

    public void setPromoPlanNotes(Object promoPlanNotes) {
        this.promoPlanNotes = promoPlanNotes;
    }

    public Integer getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(Integer cycleDays) {
        this.cycleDays = cycleDays;
    }

    public Integer getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Integer workingDays) {
        this.workingDays = workingDays;
    }

    public Integer getTotalList() {
        return totalList;
    }

    public void setTotalList(Integer totalList) {
        this.totalList = totalList;
    }

    public Integer getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(Integer totalVisits) {
        this.totalVisits = totalVisits;
    }

    public Integer getTotalDayVisits() {
        return totalDayVisits;
    }

    public void setTotalDayVisits(Integer totalDayVisits) {
        this.totalDayVisits = totalDayVisits;
    }

}
