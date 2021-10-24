package com.devartlab.data.room.massages;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class MassageEntity implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "ItemId")
    private Integer ItemId;

    @ColumnInfo(name = "MessageId")
    private Integer MessageId;

    @ColumnInfo(name = "MessageDescription")
    private String MessageDescription;

    @ColumnInfo(name = "messageLogoUrl")
    private String messageLogoUrl;

    public MassageEntity(Integer id, Integer itemId, Integer messageId, String messageDescription, String messageLogoUrl) {
        this.id = id;
        ItemId = itemId;
        MessageId = messageId;
        MessageDescription = messageDescription;
        this.messageLogoUrl = messageLogoUrl;
    }

    public MassageEntity(Integer itemId, Integer messageId, String messageDescription, String messageLogoUrl) {
        ItemId = itemId;
        MessageId = messageId;
        MessageDescription = messageDescription;
        this.messageLogoUrl = messageLogoUrl;
    }

    public MassageEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return ItemId;
    }

    public void setItemId(Integer itemId) {
        ItemId = itemId;
    }

    public Integer getMessageId() {
        return MessageId;
    }

    public void setMessageId(Integer messageId) {
        MessageId = messageId;
    }

    public String getMessageDescription() {
        return MessageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        MessageDescription = messageDescription;
    }

    public String getMessageLogoUrl() {
        return messageLogoUrl;
    }

    public void setMessageLogoUrl(String messageLogoUrl) {
        this.messageLogoUrl = messageLogoUrl;
    }

    @Override
    public String toString() {
        return "MassageEntity{" +
                "id=" + id +
                ", ItemId=" + ItemId +
                ", MessageId=" + MessageId +
                ", MessageDescription='" + MessageDescription + '\'' +
                ", messageLogoUrl='" + messageLogoUrl + '\'' +
                '}';
    }
}