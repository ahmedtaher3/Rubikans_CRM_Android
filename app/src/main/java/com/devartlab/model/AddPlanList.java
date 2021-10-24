package com.devartlab.model;

public class AddPlanList {

    boolean isChecked;
    CustomerList customerList;

    public AddPlanList(boolean isChecked, CustomerList customerList) {
        this.isChecked = isChecked;
        this.customerList = customerList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public CustomerList getCustomerList() {
        return customerList;
    }

    public void setCustomerList(CustomerList customerList) {
        this.customerList = customerList;
    }
}
