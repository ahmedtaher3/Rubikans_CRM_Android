package com.devartlab.data.room.poduct;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ProductEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ItemId")
    public int ItemId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image")
    public String image;

    public ProductEntity() {
    }

    public ProductEntity(int itemId, String name, String image) {
        ItemId = itemId;
        this.name = name;
        this.image = image;
    }

    public ProductEntity(int id, int itemId, String name, String image) {
        this.id = id;
        ItemId = itemId;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", ItemId=" + ItemId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}