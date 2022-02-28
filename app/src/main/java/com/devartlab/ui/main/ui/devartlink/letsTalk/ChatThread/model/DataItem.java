package com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model;

import com.google.gson.annotations.SerializedName;

public class DataItem {
    public DataItem(String userId, String updatedAt, String id, String message, String seen) {
        this.userId = userId;
        this.updatedAt = updatedAt;
        this.id = id;
        this.message = message;
        this.seen = seen;
    }

    @SerializedName("attachment")
    private String attachment;

    @SerializedName("reply_id")
    private String replyId;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("userapi")
    private Userapi userapi;

    @SerializedName("conversation_id")
    private String conversationId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("_id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("message")
    private String message;

    @SerializedName("seen")
    private String seen;

    @SerializedName("is_deleted")
    private boolean is_deleted;

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserapi(Userapi userapi) {
        this.userapi = userapi;
    }

    public Userapi getUserapi() {
        return userapi;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getSeen() {
        return seen;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "attachment = '" + attachment + '\'' +
                        ",reply_id = '" + replyId + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",userapi = '" + userapi + '\'' +
                        ",conversation_id = '" + conversationId + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",_id = '" + id + '\'' +
                        ",type = '" + type + '\'' +
                        ",message = '" + message + '\'' +
                        ",seen = '" + seen + '\'' +
                        ",is_deleted = '" + is_deleted + '\'' +
                        "}";
    }
}