package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserData {
    @SerializedName("Table")
    @Expose
    private List<TblAuthUsers> tblAuthUsers = null;
    @SerializedName("Table1")
    @Expose
    private List<TblAuthAuthorityDetails> tblAuthAuthorityDetails = null;
    @SerializedName("Table2")
    @Expose
    private List<TblDefEmployee> tblDefEmployee = null;
    @SerializedName("Table3")
    @Expose
    private List<TblCRMControlLevels> tblCRMControlLevels = null;
    @SerializedName("Table4")
    @Expose
    private List<TblCRMControlLevelsActivity> tblCRMControlLevelsActivity = null;
    @SerializedName("Table5")
    @Expose
    private List<TblDefConstDefinition> tblDefConstDefinition = null;
    @SerializedName("Table6")
    @Expose
    private List<TblCrmTrxEmployeeAreaAssignment> tblCrmTrxEmployeeAreaAssignment = null;
    @SerializedName("Table7")
    @Expose
    private List<TblAuthAuthoritySpPermitionDetails> tblAuthAuthoritySpPermitionDetails = null;
    @SerializedName("Table8")
    @Expose
    private List<Table8> table8 = null;
    @SerializedName("Table9")
    @Expose
    private List<ServerDate> serverDate = null;
    @SerializedName("Table10")
    @Expose
    private List<Table10> table10 = null;
    @SerializedName("Table11")
    @Expose
    private List<NewOldCycle> table11 = null;


    public List<TblAuthUsers> getTblAuthUsers() {
        return tblAuthUsers;
    }

    public void setTblAuthUsers(List<TblAuthUsers> tblAuthUsers) {
        this.tblAuthUsers = tblAuthUsers;
    }

    public List<TblAuthAuthorityDetails> getTblAuthAuthorityDetails() {
        return tblAuthAuthorityDetails;
    }

    public void setTblAuthAuthorityDetails(List<TblAuthAuthorityDetails> tblAuthAuthorityDetails) {
        this.tblAuthAuthorityDetails = tblAuthAuthorityDetails;
    }

    public List<TblDefEmployee> getTblDefEmployee() {
        return tblDefEmployee;
    }

    public void setTblDefEmployee(List<TblDefEmployee> tblDefEmployee) {
        this.tblDefEmployee = tblDefEmployee;
    }

    public List<TblCRMControlLevels> getTblCRMControlLevels() {
        return tblCRMControlLevels;
    }

    public void setTblCRMControlLevels(List<TblCRMControlLevels> tblCRMControlLevels) {
        this.tblCRMControlLevels = tblCRMControlLevels;
    }

    public List<TblCRMControlLevelsActivity> getTblCRMControlLevelsActivity() {
        return tblCRMControlLevelsActivity;
    }

    public void setTblCRMControlLevelsActivity(List<TblCRMControlLevelsActivity> tblCRMControlLevelsActivity) {
        this.tblCRMControlLevelsActivity = tblCRMControlLevelsActivity;
    }

    public List<TblDefConstDefinition> getTblDefConstDefinition() {
        return tblDefConstDefinition;
    }

    public void setTblDefConstDefinition(List<TblDefConstDefinition> tblDefConstDefinition) {
        this.tblDefConstDefinition = tblDefConstDefinition;
    }

    public List<TblCrmTrxEmployeeAreaAssignment> getTblCrmTrxEmployeeAreaAssignment() {
        return tblCrmTrxEmployeeAreaAssignment;
    }

    public void setTblCrmTrxEmployeeAreaAssignment(List<TblCrmTrxEmployeeAreaAssignment> tblCrmTrxEmployeeAreaAssignment) {
        this.tblCrmTrxEmployeeAreaAssignment = tblCrmTrxEmployeeAreaAssignment;
    }

    public List<TblAuthAuthoritySpPermitionDetails> getTblAuthAuthoritySpPermitionDetails() {
        return tblAuthAuthoritySpPermitionDetails;
    }

    public void setTblAuthAuthoritySpPermitionDetails(List<TblAuthAuthoritySpPermitionDetails> tblAuthAuthoritySpPermitionDetails) {
        this.tblAuthAuthoritySpPermitionDetails = tblAuthAuthoritySpPermitionDetails;
    }

    public List<Table8> getTable8() {
        return table8;
    }

    public void setTable8(List<Table8> table8) {
        this.table8 = table8;
    }

    public List<ServerDate> getServerDate() {
        return serverDate;
    }

    public void setServerDate(List<ServerDate> serverDate) {
        this.serverDate = serverDate;
    }

    public List<Table10> getTable10() {
        return table10;
    }

    public void setTable10(List<Table10> table10) {
        this.table10 = table10;
    }

    public List<NewOldCycle> getTable11() {
        return table11;
    }

    public void setTable11(List<NewOldCycle> table11) {
        this.table11 = table11;
    }
}
