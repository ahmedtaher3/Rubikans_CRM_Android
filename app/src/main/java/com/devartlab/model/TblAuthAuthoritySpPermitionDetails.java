package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAuthAuthoritySpPermitionDetails {

    @SerializedName("SpPermId")
    @Expose
    private Integer spPermId;
    @SerializedName("AuthorityId")
    @Expose
    private Integer authorityId;
    @SerializedName("ButtonRef")
    @Expose
    private Integer buttonRef;
    @SerializedName("FormId")
    @Expose
    private Integer formId;
    @SerializedName("Allow")
    @Expose
    private Boolean allow;

    public Integer getSpPermId() {
        return spPermId;
    }

    public void setSpPermId(Integer spPermId) {
        this.spPermId = spPermId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public Integer getButtonRef() {
        return buttonRef;
    }

    public void setButtonRef(Integer buttonRef) {
        this.buttonRef = buttonRef;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Boolean getAllow() {
        return allow;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

}
