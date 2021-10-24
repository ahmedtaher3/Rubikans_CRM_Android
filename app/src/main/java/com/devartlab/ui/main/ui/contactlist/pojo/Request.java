package com.devartlab.ui.main.ui.contactlist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Request {

    public Request() {
    }

    @SerializedName("Contactlist")
    @Expose
    private List<Contactlist> Contactlist = null;

    public List<Contactlist> getContactlist() {
        return Contactlist;
    }

    public void setgooglesheetdata(List<Contactlist> Contactlist) {
        this.Contactlist = Contactlist;
    }


}
