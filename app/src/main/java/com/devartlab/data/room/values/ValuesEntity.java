package com.devartlab.data.room.values;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ValuesEntity {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "isStarted")
    private Boolean isStarted;

    @ColumnInfo(name = "isSyncAble")
    private Boolean isSyncAble;

    @ColumnInfo(name = "startPoint")
    private String startPoint;

    @ColumnInfo(name = "startTime")
    private Long startTime;

    @ColumnInfo(name = "shift")
    private String shift;

    @ColumnInfo(name = "shiftID")
    private int shiftID;

    @ColumnInfo(name = "date")
    private String date;

    public ValuesEntity(int id, Boolean isStarted, Boolean isSyncAble, String startPoint, Long startTime, String shift, int shiftID, String date) {
        this.id = id;
        this.isStarted = isStarted;
        this.isSyncAble = isSyncAble;
        this.startPoint = startPoint;
        this.startTime = startTime;
        this.shift = shift;
        this.shiftID = shiftID;
        this.date = date;
    }

    public ValuesEntity(Boolean isStarted, Boolean isSyncAble, String startPoint, Long startTime, String shift, int shiftID, String date) {
        this.isStarted = isStarted;
        this.isSyncAble = isSyncAble;
        this.startPoint = startPoint;
        this.startTime = startTime;
        this.shift = shift;
        this.shiftID = shiftID;
        this.date = date;
    }

    public ValuesEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getStarted() {
        return isStarted;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }

    public Boolean getSyncAble() {
        return isSyncAble;
    }

    public void setSyncAble(Boolean syncAble) {
        isSyncAble = syncAble;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ValuesEntity{" +
                "id=" + id +
                ", isStarted=" + isStarted +
                ", isSyncAble=" + isSyncAble +
                ", startPoint='" + startPoint + '\'' +
                ", startTime=" + startTime +
                ", shift='" + shift + '\'' +
                ", shiftID=" + shiftID +
                ", date='" + date + '\'' +
                '}';
    }
}
