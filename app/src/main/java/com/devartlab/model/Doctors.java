package com.devartlab.model;

public class Doctors {

    String name , specialist , degree;

    public Doctors(String name, String specialist, String degree) {
        this.name = name;
        this.specialist = specialist;
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
