package com.devartlab.data.room.arrangedslides;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArrangedSlidesEntity {


    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "arrangedID")
    private Integer arrangedID;


    @ColumnInfo(name = "slideId")
    private Integer slideId;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "image")
    private String Image;
    @ColumnInfo(name = "isConverted")
    private Boolean isConverted;
    @ColumnInfo(name = "base64")
    private String base64;

    public ArrangedSlidesEntity() {
    }


    public ArrangedSlidesEntity(Integer id, Integer arrangedID, Integer slideId, String name, String image, Boolean isConverted, String base64) {
        this.id = id;
        this.arrangedID = arrangedID;
        this.slideId = slideId;
        this.name = name;
        Image = image;
        this.isConverted = isConverted;
        this.base64 = base64;
    }

    public ArrangedSlidesEntity(Integer arrangedID, Integer slideId, String name, String image, Boolean isConverted, String base64) {
        this.arrangedID = arrangedID;
        this.slideId = slideId;
        this.name = name;
        Image = image;
        this.isConverted = isConverted;
        this.base64 = base64;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getArrangedID() {
        return arrangedID;
    }

    public void setArrangedID(Integer arrangedID) {
        this.arrangedID = arrangedID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Integer getSlideId() {
        return slideId;
    }

    public void setSlideId(Integer slideId) {
        this.slideId = slideId;
    }

    public Boolean getConverted() {
        return isConverted;
    }

    public void setConverted(Boolean converted) {
        isConverted = converted;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}