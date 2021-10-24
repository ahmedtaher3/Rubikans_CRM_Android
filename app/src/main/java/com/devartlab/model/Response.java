package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response  {
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
    private Integer retVaLasString;

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

    public Integer getRetVaLasString() {
        return retVaLasString;
    }

    public void setRetVaLasString(Integer retVaLasString) {
        this.retVaLasString = retVaLasString;
    }

}
