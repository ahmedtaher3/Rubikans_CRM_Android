package com.devartlab.data.room.locations;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class LocationsEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "empId")
    public Integer empId;

    @ColumnInfo(name = "accId")
    public Integer accId;

    @ColumnInfo(name = "lat")
    public Double lat;

    @ColumnInfo(name = "lng")
    public Double lng;

    @ColumnInfo(name = "shiftId")
    public int shiftId;

    @ColumnInfo(name = "date")
    public String date;

    public LocationsEntity(Integer id, Integer empId, Integer accId, Double lat, Double lng, int shiftId, String date) {
        this.id = id;
        this.empId = empId;
        this.accId = accId;
        this.lat = lat;
        this.lng = lng;
        this.shiftId = shiftId;
        this.date = date;
    }

    public LocationsEntity(Integer empId, Integer accId, Double lat, Double lng, int shiftId, String date) {
        this.empId = empId;
        this.accId = accId;
        this.lat = lat;
        this.lng = lng;
        this.shiftId = shiftId;
        this.date = date;
    }

    public LocationsEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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
}