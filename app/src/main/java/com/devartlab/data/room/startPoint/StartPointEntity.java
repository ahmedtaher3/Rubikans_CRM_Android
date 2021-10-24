package com.devartlab.data.room.startPoint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"date", "shift"}, unique = true)})
public class StartPointEntity {


    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "shift")
    private String shift;

    @ColumnInfo(name = "isEnded")
    private boolean isEnded;

    @ColumnInfo(name = "isUploaded")
    private boolean isUploaded;


    public StartPointEntity(Integer id, String date, String shift, boolean isEnded, boolean isUploaded) {
        this.id = id;
        this.date = date;
        this.shift = shift;
        this.isEnded = isEnded;
        this.isUploaded = isUploaded;
    }

    public StartPointEntity(String date, String shift, boolean isEnded, boolean isUploaded) {
        this.date = date;
        this.shift = shift;
        this.isEnded = isEnded;
        this.isUploaded = isUploaded;

    }

    public StartPointEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }
}