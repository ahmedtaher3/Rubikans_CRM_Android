package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkFromHome {
    @SerializedName("started")
    @Expose
    private Boolean started;
    @SerializedName("startedAt")
    @Expose
    private String startedAt;
    @SerializedName("ended")
    @Expose
    private Boolean ended;
    @SerializedName("endedAt")
    @Expose
    private String endedAt;
    @SerializedName("code")
    @Expose
    private String code;

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public Boolean getEnded() {
        return ended;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WorkFromHome{" +
                "started=" + started +
                ", startedAt='" + startedAt + '\'' +
                ", ended=" + ended +
                ", endedAt='" + endedAt + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
