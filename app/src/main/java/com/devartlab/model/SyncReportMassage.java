package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncReportMassage {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("RetVaLasInteger")
    @Expose
    private Integer retVaLasInteger;
    @SerializedName("RetVaLasString")
    @Expose
    private String retVaLasString;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getRetVaLasInteger() {
        return retVaLasInteger;
    }

    public void setRetVaLasInteger(Integer retVaLasInteger) {
        this.retVaLasInteger = retVaLasInteger;
    }

    public String getRetVaLasString() {
        return retVaLasString;
    }

    public void setRetVaLasString(String retVaLasString) {
        this.retVaLasString = retVaLasString;
    }

}