package com.devartlab.data.room.authority;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AuthorityEntity {


    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "AuthorityDetId")
    private Integer authorityDetId;

    @ColumnInfo(name = "authorityId")
    private Integer authorityId;

    @ColumnInfo(name = "formId")
    private Integer formId;

    @ColumnInfo(name = "allowBrowseRecord")
    private Boolean allowBrowseRecord;

    @ColumnInfo(name = "allowEdit")
    private Boolean allowEdit;

    @ColumnInfo(name = "allowSave")
    private Boolean allowSave;

    @ColumnInfo(name = "allowDelete")
    private Boolean allowDelete;

    @ColumnInfo(name = "allowExport")
    private Boolean allowExport;

    @ColumnInfo(name = "allowPreviewReport")
    private Boolean allowPreviewReport;

    @ColumnInfo(name = "allowPrintReport")
    private Boolean allowPrintReport;

    public AuthorityEntity(Integer id, Integer authorityDetId, Integer authorityId, Integer formId, Boolean allowBrowseRecord, Boolean allowEdit, Boolean allowSave, Boolean allowDelete, Boolean allowExport, Boolean allowPreviewReport, Boolean allowPrintReport) {
        this.id = id;
        this.authorityDetId = authorityDetId;
        this.authorityId = authorityId;
        this.formId = formId;
        this.allowBrowseRecord = allowBrowseRecord;
        this.allowEdit = allowEdit;
        this.allowSave = allowSave;
        this.allowDelete = allowDelete;
        this.allowExport = allowExport;
        this.allowPreviewReport = allowPreviewReport;
        this.allowPrintReport = allowPrintReport;
    }

    public AuthorityEntity(Integer authorityDetId, Integer authorityId, Integer formId, Boolean allowBrowseRecord, Boolean allowEdit, Boolean allowSave, Boolean allowDelete, Boolean allowExport, Boolean allowPreviewReport, Boolean allowPrintReport) {
        this.authorityDetId = authorityDetId;
        this.authorityId = authorityId;
        this.formId = formId;
        this.allowBrowseRecord = allowBrowseRecord;
        this.allowEdit = allowEdit;
        this.allowSave = allowSave;
        this.allowDelete = allowDelete;
        this.allowExport = allowExport;
        this.allowPreviewReport = allowPreviewReport;
        this.allowPrintReport = allowPrintReport;
    }

    public AuthorityEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "AuthorityEntity{" +
                "id=" + id +
                ", authorityDetId=" + authorityDetId +
                ", authorityId=" + authorityId +
                ", formId=" + formId +
                ", allowBrowseRecord=" + allowBrowseRecord +
                ", allowEdit=" + allowEdit +
                ", allowSave=" + allowSave +
                ", allowDelete=" + allowDelete +
                ", allowExport=" + allowExport +
                ", allowPreviewReport=" + allowPreviewReport +
                ", allowPrintReport=" + allowPrintReport +
                '}';
    }
}