package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meal {
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("dayAr")
    @Expose
    private String dayAr;
   @SerializedName("meal")
    @Expose
    private String meal;

    @SerializedName("free")
    @Expose
    private String free;

    @SerializedName("diet")
    @Expose
    private String diet;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayAr() {
        return dayAr;
    }

    public void setDayAr(String dayAr) {
        this.dayAr = dayAr;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }
}