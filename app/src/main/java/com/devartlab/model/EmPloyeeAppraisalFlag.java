package com.devartlab.model;

import java.util.ArrayList;

public class EmPloyeeAppraisalFlag {


    private Integer empid;

    private Integer accountId;

    private Integer correctiveActionsID;

    private String empName;

    private String correctiveActions;
    private String correctiveActionsComment;

    private ArrayList<EMPloyeeAppraisalQ> eMPloyeeAppraisal = null;

    private Boolean flag = false;


    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public ArrayList<EMPloyeeAppraisalQ> geteMPloyeeAppraisal() {
        return eMPloyeeAppraisal;
    }

    public void seteMPloyeeAppraisal(ArrayList<EMPloyeeAppraisalQ> eMPloyeeAppraisal) {
        this.eMPloyeeAppraisal = eMPloyeeAppraisal;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getCorrectiveActionsID() {
        return correctiveActionsID;
    }

    public void setCorrectiveActionsID(Integer correctiveActionsID) {
        this.correctiveActionsID = correctiveActionsID;
    }

    public String getCorrectiveActions() {
        return correctiveActions;
    }

    public void setCorrectiveActions(String correctiveActions) {
        this.correctiveActions = correctiveActions;
    }

    public String getCorrectiveActionsComment() {
        return correctiveActionsComment;
    }

    public void setCorrectiveActionsComment(String correctiveActionsComment) {
        this.correctiveActionsComment = correctiveActionsComment;
    }
}