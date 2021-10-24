package com.devartlab.data.room.callslide;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CallSlideEntity {


    @PrimaryKey(autoGenerate = true)
    private Integer id;


    @ColumnInfo(name = "planID")
    private Integer planID;

    @ColumnInfo(name = "productID")
    private Integer productID;

    @ColumnInfo(name = "slideID")
    private Integer slideID;

    @ColumnInfo(name = "userID")
    private Integer userID;

    @ColumnInfo(name = "time")
    private String time;

    public CallSlideEntity(Integer id, Integer planID, Integer productID, Integer slideID, Integer userID, String time) {
        this.id = id;
        this.planID = planID;
        this.productID = productID;
        this.slideID = slideID;
        this.userID = userID;
        this.time = time;
    }

    public CallSlideEntity(Integer planID, Integer productID, Integer slideID, Integer userID, String time) {
        this.planID = planID;
        this.productID = productID;
        this.slideID = slideID;
        this.userID = userID;
        this.time = time;
    }

    public CallSlideEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanID() {
        return planID;
    }

    public void setPlanID(Integer planID) {
        this.planID = planID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getSlideID() {
        return slideID;
    }

    public void setSlideID(Integer slideID) {
        this.slideID = slideID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}