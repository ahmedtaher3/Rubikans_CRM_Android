package com.devartlab.model;

public class OpenPlanModel {

   
        public int PlanId ;
        public int PlanAccountId ;
        public int PlanEmpId ;
        public int OpenAccountId ;
        public int OpenEmpId ;
        public String ExpireDate ;
        public String PlanDate ;

    public OpenPlanModel(int planId, int planAccountId, int planEmpId, int openAccountId, int openEmpId, String expireDate, String planDate) {
        PlanId = planId;
        PlanAccountId = planAccountId;
        PlanEmpId = planEmpId;
        OpenAccountId = openAccountId;
        OpenEmpId = openEmpId;
        ExpireDate = expireDate;
        PlanDate = planDate;
    }

    public int getPlanId() {
        return PlanId;
    }

    public void setPlanId(int planId) {
        PlanId = planId;
    }

    public int getPlanAccountId() {
        return PlanAccountId;
    }

    public void setPlanAccountId(int planAccountId) {
        PlanAccountId = planAccountId;
    }

    public int getPlanEmpId() {
        return PlanEmpId;
    }

    public void setPlanEmpId(int planEmpId) {
        PlanEmpId = planEmpId;
    }

    public int getOpenAccountId() {
        return OpenAccountId;
    }

    public void setOpenAccountId(int openAccountId) {
        OpenAccountId = openAccountId;
    }

    public int getOpenEmpId() {
        return OpenEmpId;
    }

    public void setOpenEmpId(int openEmpId) {
        OpenEmpId = openEmpId;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getPlanDate() {
        return PlanDate;
    }

    public void setPlanDate(String planDate) {
        PlanDate = planDate;
    }
}
