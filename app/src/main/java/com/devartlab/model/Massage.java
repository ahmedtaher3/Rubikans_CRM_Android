package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Massage {

    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("MessageDescription")
    @Expose
    private String messageDescription;
    @SerializedName("MessageLogoUrl")
    @Expose
    private String messageLogoUrl;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public String getMessageLogoUrl() {
        return messageLogoUrl;
    }

    public void setMessageLogoUrl(String messageLogoUrl) {
        this.messageLogoUrl = messageLogoUrl;
    }

}
