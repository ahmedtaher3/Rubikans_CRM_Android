package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class MassageSlide {

    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("MessageId")
    @Expose
    private Integer messageId;
    @SerializedName("MessageDetId")
    @Expose
    private Integer messageDetId;
    @SerializedName("SlideName")
    @Expose
    private String slideName;
    @SerializedName("SlideUrl")
    @Expose
    private String slideUrl;

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

    public Integer getMessageDetId() {
        return messageDetId;
    }

    public void setMessageDetId(Integer messageDetId) {
        this.messageDetId = messageDetId;
    }

    public String getSlideName() {
        return slideName;
    }

    public void setSlideName(String slideName) {
        this.slideName = slideName;
    }

    public String getSlideUrl() {
        return slideUrl;
    }

    public void setSlideUrl(String slideUrl) {
        this.slideUrl = slideUrl;
    }

}
