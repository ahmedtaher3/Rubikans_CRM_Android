package com.devartlab.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoubleVisitReportDetails {

    @SerializedName("Manger")
    @Expose
    private String manger;
    @SerializedName("EmployeeEMpName")
    @Expose
    private String employeeEMpName;
    @SerializedName("BrickEnName")
    @Expose
    private String brickEnName;
    @SerializedName("shiftName")
    @Expose
    private String shiftName;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("ActivityId")
    @Expose
    private Integer activityId;
    @SerializedName("DayDate")
    @Expose
    private String dayDate;
    @SerializedName("MangerId")
    @Expose
    private Integer mangerId;
    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("BrickId")
    @Expose
    private Integer brickId;
    @SerializedName("CustomerEnName")
    @Expose
    private String customerEnName;
    @SerializedName("CusTypeEnName")
    @Expose
    private String cusTypeEnName;
    @SerializedName("CusClassEnName")
    @Expose
    private String cusClassEnName;
    @SerializedName("Visited")
    @Expose
    private Boolean visited;
    @SerializedName("IsExtraVisit")
    @Expose
    private Boolean isExtraVisit;

    public String getManger() {
        return manger;
    }

    public void setManger(String manger) {
        this.manger = manger;
    }

    public String getEmployeeEMpName() {
        return employeeEMpName;
    }

    public void setEmployeeEMpName(String employeeEMpName) {
        this.employeeEMpName = employeeEMpName;
    }

    public String getBrickEnName() {
        return brickEnName;
    }

    public void setBrickEnName(String brickEnName) {
        this.brickEnName = brickEnName;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public Integer getMangerId() {
        return mangerId;
    }

    public void setMangerId(Integer mangerId) {
        this.mangerId = mangerId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getBrickId() {
        return brickId;
    }

    public void setBrickId(Integer brickId) {
        this.brickId = brickId;
    }

    public String getCustomerEnName() {
        return customerEnName;
    }

    public void setCustomerEnName(String customerEnName) {
        this.customerEnName = customerEnName;
    }

    public String getCusTypeEnName() {
        return cusTypeEnName;
    }

    public void setCusTypeEnName(String cusTypeEnName) {
        this.cusTypeEnName = cusTypeEnName;
    }

    public String getCusClassEnName() {
        return cusClassEnName;
    }

    public void setCusClassEnName(String cusClassEnName) {
        this.cusClassEnName = cusClassEnName;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Boolean getIsExtraVisit() {
        return isExtraVisit;
    }

    public void setIsExtraVisit(Boolean isExtraVisit) {
        this.isExtraVisit = isExtraVisit;
    }

}
