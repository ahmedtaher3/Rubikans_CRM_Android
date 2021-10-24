package com.devartlab.ui.main.ui.employeeservices;

public class EmployeeServices {

    String name , icon , link , desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EmployeeServices(String name, String icon, String link, String desc) {
        this.name = name;
        this.icon = icon;
        this.link = link;
        this.desc = desc;
    }
}
