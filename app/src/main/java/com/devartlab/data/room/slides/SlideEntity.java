package com.devartlab.data.room.slides;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SlideEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "ItemId")
    public int ItemId;

    @ColumnInfo(name = "MessageId")
    public int MessageId;

    @ColumnInfo(name = "MessageDetId")
    public int MessageDetId;

    @ColumnInfo(name = "SlideName")
    public String SlideName;

    @ColumnInfo(name = "SlideUrl")
    public String SlideUrl;

    @ColumnInfo(name = "SlideBase64")
    public String SlideBase64;

    @ColumnInfo(name = "isConverted")
    public Boolean isConverted;

    public SlideEntity(int id, int itemId, int messageId, int messageDetId, String slideName, String slideUrl, String slideBase64, Boolean isConverted) {
        this.id = id;
        ItemId = itemId;
        MessageId = messageId;
        MessageDetId = messageDetId;
        SlideName = slideName;
        SlideUrl = slideUrl;
        SlideBase64 = slideBase64;
        this.isConverted = isConverted;
    }

    public SlideEntity(int itemId, int messageId, int messageDetId, String slideName, String slideUrl, String slideBase64, Boolean isConverted) {
        ItemId = itemId;
        MessageId = messageId;
        MessageDetId = messageDetId;
        SlideName = slideName;
        SlideUrl = slideUrl;
        SlideBase64 = slideBase64;
        this.isConverted = isConverted;
    }

    public SlideEntity() {
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

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public int getMessageDetId() {
        return MessageDetId;
    }

    public void setMessageDetId(int messageDetId) {
        MessageDetId = messageDetId;
    }

    public String getSlideName() {
        return SlideName;
    }

    public void setSlideName(String slideName) {
        SlideName = slideName;
    }

    public String getSlideUrl() {
        return SlideUrl;
    }

    public void setSlideUrl(String slideUrl) {
        SlideUrl = slideUrl;
    }

    public String getSlideBase64() {
        return SlideBase64;
    }

    public void setSlideBase64(String slideBase64) {
        SlideBase64 = slideBase64;
    }

    public Boolean getConverted() {
        return isConverted;
    }

    public void setConverted(Boolean converted) {
        isConverted = converted;
    }
}
