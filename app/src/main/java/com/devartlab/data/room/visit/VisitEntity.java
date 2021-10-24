package com.devartlab.data.room.visit;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VisitEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "shiftId")
    public int shiftId;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "customerId")
    public int customerId;

    @ColumnInfo(name = "userId")
    public int userId;


    @ColumnInfo(name = "isVisit")
    public boolean isVisit;


    @ColumnInfo(name = "call1")
    public boolean call1;


    @ColumnInfo(name = "call2")
    public boolean call2;


    @ColumnInfo(name = "call3")
    public boolean call3;


    @ColumnInfo(name = "call4")
    public boolean call4;


    public VisitEntity() {
    }

    public VisitEntity(int shiftId, String date, int customerId, int userId, boolean isVisit, boolean call1, boolean call2, boolean call3, boolean call4) {
        this.shiftId = shiftId;
        this.date = date;
        this.customerId = customerId;
        this.userId = userId;
        this.isVisit = isVisit;
        this.call1 = call1;
        this.call2 = call2;
        this.call3 = call3;
        this.call4 = call4;
    }

    public VisitEntity(int id, int shiftId, String date, int customerId, int userId, boolean isVisit, boolean call1, boolean call2, boolean call3, boolean call4) {
        this.id = id;
        this.shiftId = shiftId;
        this.date = date;
        this.customerId = customerId;
        this.userId = userId;
        this.isVisit = isVisit;
        this.call1 = call1;
        this.call2 = call2;
        this.call3 = call3;
        this.call4 = call4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isVisit() {
        return isVisit;
    }

    public void setVisit(boolean visit) {
        isVisit = visit;
    }

    public boolean isCall1() {
        return call1;
    }

    public void setCall1(boolean call1) {
        this.call1 = call1;
    }

    public boolean isCall2() {
        return call2;
    }

    public void setCall2(boolean call2) {
        this.call2 = call2;
    }

    public boolean isCall3() {
        return call3;
    }

    public void setCall3(boolean call3) {
        this.call3 = call3;
    }

    public boolean isCall4() {
        return call4;
    }

    public void setCall4(boolean call4) {
        this.call4 = call4;
    }
}
