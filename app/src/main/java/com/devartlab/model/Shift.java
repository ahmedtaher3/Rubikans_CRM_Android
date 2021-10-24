package com.devartlab.model;

public class Shift {

    int id;
    String name;
    String startedAt;
    boolean isStarted;
    String date;


    public Shift(int id, String name, String startedAt, String date, boolean isStarted) {
        this.id = id;
        this.name = name;
        this.startedAt = startedAt;
        this.date = date;
        this.isStarted = isStarted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
