
package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorityDatum {

    @SerializedName("AuthorityDetId")
    @Expose
    private Integer authorityDetId;
    @SerializedName("AuthorityId")
    @Expose
    private Integer authorityId;
    @SerializedName("FormId")
    @Expose
    private Integer formId;
    @SerializedName("AllowBrowseRecord")
    @Expose
    private Boolean allowBrowseRecord;
    @SerializedName("AllowEdit")
    @Expose
    private Boolean allowEdit;
    @SerializedName("AllowSave")
    @Expose
    private Boolean allowSave;
    @SerializedName("AllowDelete")
    @Expose
    private Boolean allowDelete;
    @SerializedName("AllowExport")
    @Expose
    private Boolean allowExport;
    @SerializedName("AllowPreviewReport")
    @Expose
    private Boolean allowPreviewReport;
    @SerializedName("AllowPrintReport")
    @Expose
    private Boolean allowPrintReport;

    public Integer getAuthorityDetId() {
        return authorityDetId;
    }

    public void setAuthorityDetId(Integer authorityDetId) {
        this.authorityDetId = authorityDetId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Boolean getAllowBrowseRecord() {
        return allowBrowseRecord;
    }

    public void setAllowBrowseRecord(Boolean allowBrowseRecord) {
        this.allowBrowseRecord = allowBrowseRecord;
    }

    public Boolean getAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(Boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public Boolean getAllowSave() {
        return allowSave;
    }

    public void setAllowSave(Boolean allowSave) {
        this.allowSave = allowSave;
    }

    public Boolean getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    public Boolean getAllowExport() {
        return allowExport;
    }

    public void setAllowExport(Boolean allowExport) {
        this.allowExport = allowExport;
    }

    public Boolean getAllowPreviewReport() {
        return allowPreviewReport;
    }

    public void setAllowPreviewReport(Boolean allowPreviewReport) {
        this.allowPreviewReport = allowPreviewReport;
    }

    public Boolean getAllowPrintReport() {
        return allowPrintReport;
    }

    public void setAllowPrintReport(Boolean allowPrintReport) {
        this.allowPrintReport = allowPrintReport;
    }

}
