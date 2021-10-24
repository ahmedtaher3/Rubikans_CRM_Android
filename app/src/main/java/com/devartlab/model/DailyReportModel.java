package com.devartlab.model;

import java.util.ArrayList;

public class DailyReportModel {

    private ArrayList<SyncReport> EmployeeDailyActivityList;
    private ArrayList<AppraisalBuildsSchema> EvaluationList;
    private ArrayList<SyncSlide> CallEdetailingList;

    public ArrayList<SyncReport> getEmployeeDailyActivityList() {
        return EmployeeDailyActivityList;
    }

    public void setEmployeeDailyActivityList(ArrayList<SyncReport> employeeDailyActivityList) {
        EmployeeDailyActivityList = employeeDailyActivityList;
    }

    public ArrayList<AppraisalBuildsSchema> getEvaluationList() {
        return EvaluationList;
    }

    public void setEvaluationList(ArrayList<AppraisalBuildsSchema> evaluationList) {
        EvaluationList = evaluationList;
    }

    public ArrayList<SyncSlide> getCallEdetailingList() {
        return CallEdetailingList;
    }

    public void setCallEdetailingList(ArrayList<SyncSlide> callEdetailingList) {
        CallEdetailingList = callEdetailingList;
    }
}
