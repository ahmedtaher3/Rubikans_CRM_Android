package com.devartlab.data.room.arranged;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArrangedEntity {


    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "customerId")
    private Integer customerId;

    @ColumnInfo(name = "productId")
    private Integer productId;
    @ColumnInfo(name = "productName")
    private String productName;


    @ColumnInfo(name = "massageId")
    private Integer massageId;
    @ColumnInfo(name = "massageName")
    private String massageName;

    @ColumnInfo(name = "image")
    private String image;

    public ArrangedEntity(Integer id, Integer customerId, Integer productId, String productName, Integer massageId, String massageName, String image) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.massageId = massageId;
        this.massageName = massageName;
        this.image = image;
    }

    public ArrangedEntity(Integer customerId, Integer productId, String productName, Integer massageId, String massageName, String image) {
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.massageId = massageId;
        this.massageName = massageName;
        this.image = image;
    }

    public ArrangedEntity() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getMassageId() {
        return massageId;
    }

    public void setMassageId(Integer massageId) {
        this.massageId = massageId;
    }

    public String getMassageName() {
        return massageName;
    }

    public void setMassageName(String massageName) {
        this.massageName = massageName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}