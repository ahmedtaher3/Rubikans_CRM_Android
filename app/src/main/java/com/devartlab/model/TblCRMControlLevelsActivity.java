package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblCRMControlLevelsActivity {

    @SerializedName("ActivityId")
    @Expose
    private Integer activityId;
    @SerializedName("ActivitySerial")
    @Expose
    private Integer activitySerial;
    @SerializedName("ActivityArName")
    @Expose
    private String activityArName;
    @SerializedName("ActivityEnName")
    @Expose
    private String activityEnName;
    @SerializedName("Notes")
    @Expose
    private Object notes;
    @SerializedName("ActivityType")
    @Expose
    private Integer activityType;
    @SerializedName("ActivityTypeName")
    @Expose
    private String activityTypeName;
    @SerializedName("ActivityTypeNotes")
    @Expose
    private Object activityTypeNotes;
    @SerializedName("ViewId")
    @Expose
    private Integer viewId;
    @SerializedName("ViewNotes")
    @Expose
    private Object viewNotes;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivitySerial() {
        return activitySerial;
    }

    public void setActivitySerial(Integer activitySerial) {
        this.activitySerial = activitySerial;
    }

    public String getActivityArName() {
        return activityArName;
    }

    public void setActivityArName(String activityArName) {
        this.activityArName = activityArName;
    }

    public String getActivityEnName() {
        return activityEnName;
    }

    public void setActivityEnName(String activityEnName) {
        this.activityEnName = activityEnName;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public Object getActivityTypeNotes() {
        return activityTypeNotes;
    }

    public void setActivityTypeNotes(Object activityTypeNotes) {
        this.activityTypeNotes = activityTypeNotes;
    }

    public Integer getViewId() {
        return viewId;
    }

    public void setViewId(Integer viewId) {
        this.viewId = viewId;
    }

    public Object getViewNotes() {
        return viewNotes;
    }

    public void setViewNotes(Object viewNotes) {
        this.viewNotes = viewNotes;
    }

}
