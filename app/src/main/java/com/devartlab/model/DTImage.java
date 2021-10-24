package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DTImage {

    @SerializedName("ImageId")
    @Expose
    private Integer imageId;
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("FileImage")
    @Expose
    private String fileImage;

    public DTImage(Integer imageId, Integer empId, String fileImage) {
        this.imageId = imageId;
        this.empId = empId;
        this.fileImage = fileImage;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage;
    }

}