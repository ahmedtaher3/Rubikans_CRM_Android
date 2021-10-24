package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDefConstDefinition {

    @SerializedName("DefId")
    @Expose
    private Integer defId;
    @SerializedName("ArName")
    @Expose
    private String arName;
    @SerializedName("EnName")
    @Expose
    private String enName;
    @SerializedName("DefType")
    @Expose
    private Integer defType;
    @SerializedName("DefTypeDescription")
    @Expose
    private String defTypeDescription;
    @SerializedName("IsSystemDiff")
    @Expose
    private Object isSystemDiff;

    public Integer getDefId() {
        return defId;
    }

    public void setDefId(Integer defId) {
        this.defId = defId;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Integer getDefType() {
        return defType;
    }

    public void setDefType(Integer defType) {
        this.defType = defType;
    }

    public String getDefTypeDescription() {
        return defTypeDescription;
    }

    public void setDefTypeDescription(String defTypeDescription) {
        this.defTypeDescription = defTypeDescription;
    }

    public Object getIsSystemDiff() {
        return isSystemDiff;
    }

    public void setIsSystemDiff(Object isSystemDiff) {
        this.isSystemDiff = isSystemDiff;
    }

}
