package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("projector")
    @Expose
    private Boolean projector;
    @SerializedName("floar")
    @Expose
    private Integer floar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Boolean getProjector() {
        return projector;
    }

    public void setProjector(Boolean projector) {
        this.projector = projector;
    }

    public Integer getFloar() {
        return floar;
    }

    public void setFloar(Integer floar) {
        this.floar = floar;
    }

}