package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DTMobiles {
    @SerializedName("Serial")
    @Expose
    private String serial;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("PlanEnName")
    @Expose
    private String planEnName;
    @SerializedName("PlanId")
    @Expose
    private Integer planId;
    @SerializedName("Mobid")
    @Expose
    private Integer mobid;
    @SerializedName("ItemsId")
    @Expose
    private String itemsId;
    @SerializedName("PayedByComp")
    @Expose
    private String payedByComp;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlanEnName() {
        return planEnName;
    }

    public void setPlanEnName(String planEnName) {
        this.planEnName = planEnName;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getMobid() {
        return mobid;
    }

    public void setMobid(Integer mobid) {
        this.mobid = mobid;
    }

    public String getItemsId() {
        return itemsId;
    }

    public void setItemsId(String itemsId) {
        this.itemsId = itemsId;
    }

    public String getPayedByComp() {
        return payedByComp;
    }

    public void setPayedByComp(String payedByComp) {
        this.payedByComp = payedByComp;
    }

}