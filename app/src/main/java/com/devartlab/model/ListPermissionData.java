package com.devartlab.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPermissionData {

    @SerializedName("ListId")
    @Expose
    private Integer listId;
    @SerializedName("Column1")
    @Expose
    private Integer column1;
    @SerializedName("AssigntId")
    @Expose
    private Integer assigntId;
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("Allow To EMployee")
    @Expose
    private String allowToEMployee;
    @SerializedName("List Terriotry")
    @Expose
    private String listTerriotry;
    @SerializedName("Serial")
    @Expose
    private Object serial;
    @SerializedName("List Type")
    @Expose
    private String listType;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("Allow to Edit")
    @Expose
    private Boolean allowToEdit;
    @SerializedName("Allow to AddCustomer")
    @Expose
    private Boolean allowToAddCustomer;
    @SerializedName("Allow to Delete Customer")
    @Expose
    private Boolean allowToDeleteCustomer;
    @SerializedName("ExpireDate")
    @Expose
    private String expireDate;
    @SerializedName("assigntId1")
    @Expose
    private Integer assigntId1;
    @SerializedName("CustomerTypeId")
    @Expose
    private Integer customerTypeId;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getColumn1() {
        return column1;
    }

    public void setColumn1(Integer column1) {
        this.column1 = column1;
    }

    public Integer getAssigntId() {
        return assigntId;
    }

    public void setAssigntId(Integer assigntId) {
        this.assigntId = assigntId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getAllowToEMployee() {
        return allowToEMployee;
    }

    public void setAllowToEMployee(String allowToEMployee) {
        this.allowToEMployee = allowToEMployee;
    }

    public String getListTerriotry() {
        return listTerriotry;
    }

    public void setListTerriotry(String listTerriotry) {
        this.listTerriotry = listTerriotry;
    }

    public Object getSerial() {
        return serial;
    }

    public void setSerial(Object serial) {
        this.serial = serial;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Boolean getAllowToEdit() {
        return allowToEdit;
    }

    public void setAllowToEdit(Boolean allowToEdit) {
        this.allowToEdit = allowToEdit;
    }

    public Boolean getAllowToAddCustomer() {
        return allowToAddCustomer;
    }

    public void setAllowToAddCustomer(Boolean allowToAddCustomer) {
        this.allowToAddCustomer = allowToAddCustomer;
    }

    public Boolean getAllowToDeleteCustomer() {
        return allowToDeleteCustomer;
    }

    public void setAllowToDeleteCustomer(Boolean allowToDeleteCustomer) {
        this.allowToDeleteCustomer = allowToDeleteCustomer;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getAssigntId1() {
        return assigntId1;
    }

    public void setAssigntId1(Integer assigntId1) {
        this.assigntId1 = assigntId1;
    }

    public Integer getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

}
