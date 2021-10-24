package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VacationBallance {

    @SerializedName("EMpId")
    @Expose
    private Integer eMpId;
    @SerializedName("VacTypeId")
    @Expose
    private Integer vacTypeId;
    @SerializedName("VacTypeArName")
    @Expose
    private String vacTypeArName;
    @SerializedName("MaxDayLimit")
    @Expose
    private Double maxDayLimit;
    @SerializedName("TotalConsumed")
    @Expose
    private Double totalConsumed;
    @SerializedName("Reminder")
    @Expose
    private Double reminder;
    @SerializedName("EnforceVacationTypeBallance")
    @Expose
    private Integer enforceVacationTypeBallance;

    public Integer getEMpId() {
        return eMpId;
    }

    public void setEMpId(Integer eMpId) {
        this.eMpId = eMpId;
    }

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

    public Double getMaxDayLimit() {
        return maxDayLimit;
    }

    public void setMaxDayLimit(Double maxDayLimit) {
        this.maxDayLimit = maxDayLimit;
    }

    public Double getTotalConsumed() {
        return totalConsumed;
    }

    public void setTotalConsumed(Double totalConsumed) {
        this.totalConsumed = totalConsumed;
    }

    public Double getReminder() {
        return reminder;
    }

    public void setReminder(Double reminder) {
        this.reminder = reminder;
    }

    public Integer getEnforceVacationTypeBallance() {
        return enforceVacationTypeBallance;
    }

    public void setEnforceVacationTypeBallance(Integer enforceVacationTypeBallance) {
        this.enforceVacationTypeBallance = enforceVacationTypeBallance;
    }

    @Override
    public String toString() {
        return "VacationBallance{" +
                "eMpId=" + eMpId +
                ", vacTypeId=" + vacTypeId +
                ", vacTypeArName='" + vacTypeArName + '\'' +
                ", maxDayLimit=" + maxDayLimit +
                ", totalConsumed=" + totalConsumed +
                ", reminder=" + reminder +
                ", enforceVacationTypeBallance=" + enforceVacationTypeBallance +
                '}';
    }
}
