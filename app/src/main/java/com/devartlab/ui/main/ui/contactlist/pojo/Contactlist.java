package com.devartlab.ui.main.ui.contactlist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contactlist {

    @SerializedName("HRMS_ID")
    @Expose
    private String HRMS_ID;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Titel")
    @Expose
    private String Titel;

    @SerializedName("Dep")
    @Expose
    private String Dep;

    @SerializedName("Phone_1")
    @Expose
    private String Phone_1;

    @SerializedName("Phone_2")
    @Expose
    private String Phone_2;

    @SerializedName("Email")
    @Expose
    private String Email;

    public String getHRMS_ID() {
        return HRMS_ID;
    }

    public String getName() {
        return Name;
    }


    public String getTitel() {
        return Titel;
    }

    public String getDep() {
        return Dep;
    }

    public String getPhone_1() {
        return Phone_1;
    }

    public String getPhone_2() {
        return Phone_2;
    }

    public String getEmail() {
        return Email;
    }
}
