package com.devartlab.model;

public class StartPoint {


    private int startPointId;

    private String startPointTime;

    private String startPointName;


    public StartPoint(int startPointId, String startPointTime, String startPointName) {
        this.startPointId = startPointId;
        this.startPointTime = startPointTime;
        this.startPointName = startPointName;
    }

    public int getStartPointId() {
        return startPointId;
    }

    public void setStartPointId(int startPointId) {
        this.startPointId = startPointId;
    }

    public String getStartPointTime() {
        return startPointTime;
    }

    public void setStartPointTime(String startPointTime) {
        this.startPointTime = startPointTime;
    }

    public String getStartPointName() {
        return startPointName;
    }

    public void setStartPointName(String startPointName) {
        this.startPointName = startPointName;
    }

    @Override
    public String toString() {
        return "StartPoint{" +
                "startPointId=" + startPointId +
                ", startPointTime='" + startPointTime + '\'' +
                ", startPointName='" + startPointName + '\'' +
                '}';
    }
}
