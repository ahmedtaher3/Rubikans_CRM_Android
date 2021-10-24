package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalaryItemDetails {
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ValueDescription")
    @Expose
    private String valueDescription;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("DuesDeductionId")
    @Expose
    private Integer duesDeductionId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValueDescription() {
        return valueDescription;
    }

    public void setValueDescription(String valueDescription) {
        this.valueDescription = valueDescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDuesDeductionId() {
        return duesDeductionId;
    }

    public void setDuesDeductionId(Integer duesDeductionId) {
        this.duesDeductionId = duesDeductionId;
    }

}
