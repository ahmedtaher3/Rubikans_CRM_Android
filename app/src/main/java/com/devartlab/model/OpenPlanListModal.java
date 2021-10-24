package com.devartlab.model;

public class OpenPlanListModal {

    public int ListIdId ;
    public int AssignId ;
    public int AccountId ;
    public int ListEmpId ;
    public boolean AllowToEdit ;
    public boolean AllowToAddCustomer ;
    public boolean AllowToDeleteCustomer ;
    public String PermExpireDate ;
    public int AddUserId ;
    public int AddEmpId ;
    public String AddMac ;

    public OpenPlanListModal(int listIdId, int assignId, int accountId, int listEmpId, boolean allowToEdit, boolean allowToAddCustomer, boolean allowToDeleteCustomer, String permExpireDate, int addUserId, int addEmpId, String addMac) {
        ListIdId = listIdId;
        AssignId = assignId;
        AccountId = accountId;
        ListEmpId = listEmpId;
        AllowToEdit = allowToEdit;
        AllowToAddCustomer = allowToAddCustomer;
        AllowToDeleteCustomer = allowToDeleteCustomer;
        PermExpireDate = permExpireDate;
        AddUserId = addUserId;
        AddEmpId = addEmpId;
        AddMac = addMac;
    }

    public int getListIdId() {
        return ListIdId;
    }

    public void setListIdId(int listIdId) {
        ListIdId = listIdId;
    }

    public int getAssignId() {
        return AssignId;
    }

    public void setAssignId(int assignId) {
        AssignId = assignId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public int getListEmpId() {
        return ListEmpId;
    }

    public void setListEmpId(int listEmpId) {
        ListEmpId = listEmpId;
    }

    public boolean isAllowToEdit() {
        return AllowToEdit;
    }

    public void setAllowToEdit(boolean allowToEdit) {
        AllowToEdit = allowToEdit;
    }

    public boolean isAllowToAddCustomer() {
        return AllowToAddCustomer;
    }

    public void setAllowToAddCustomer(boolean allowToAddCustomer) {
        AllowToAddCustomer = allowToAddCustomer;
    }

    public boolean isAllowToDeleteCustomer() {
        return AllowToDeleteCustomer;
    }

    public void setAllowToDeleteCustomer(boolean allowToDeleteCustomer) {
        AllowToDeleteCustomer = allowToDeleteCustomer;
    }

    public String getPermExpireDate() {
        return PermExpireDate;
    }

    public void setPermExpireDate(String permExpireDate) {
        PermExpireDate = permExpireDate;
    }

    public int getAddUserId() {
        return AddUserId;
    }

    public void setAddUserId(int addUserId) {
        AddUserId = addUserId;
    }

    public int getAddEmpId() {
        return AddEmpId;
    }

    public void setAddEmpId(int addEmpId) {
        AddEmpId = addEmpId;
    }

    public String getAddMac() {
        return AddMac;
    }

    public void setAddMac(String addMac) {
        AddMac = addMac;
    }
}
