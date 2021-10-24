package com.devartlab.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class EmployeeModel {
    @SerializedName("DLExperiences")
    @Expose
    @Nullable
    private ArrayList<DTExperience> DLExperiences = null;
    @SerializedName("DLImages")
    @Expose
    @Nullable
    private ArrayList<DTImage> DLImages = null;
    @SerializedName("DTBankAccounts")
    @Expose
    @Nullable
    private ArrayList<DTBankAccount> DTBankAccounts = null;
    @SerializedName("DTDeductions")
    @Expose
    @Nullable
    private Object DTDeductions;
    @SerializedName("DTDues")
    @Expose
    @Nullable
    private ArrayList<DTBankAccount> DTDues;
    @SerializedName("DTExperiences")
    @Expose
    @Nullable
    private ArrayList<DTExperience> DTExperiences = null;
    @SerializedName("DTImages")
    @Expose
    @Nullable
    private ArrayList<DTImage> DTImages = null;
    @SerializedName("DTMobiles")
    @Expose
    @Nullable
    private ArrayList<DTMobiles> DTMobiles = null;
    @SerializedName("AddDateTime")
    @Expose
    @Nullable
    private Object addDateTime;
    @SerializedName("AddMAc")
    @Expose
    @Nullable
    private Object addMAc;
    @SerializedName("AddUserId")
    @Expose
    @Nullable
    private int addUserId;
    @SerializedName("Address")
    @Expose
    @Nullable
    private String address;
    @SerializedName("AllowToUpdateLocationAddress")
    @Expose
    @Nullable
    private Object allowToUpdateLocationAddress;
    @SerializedName("AreaId")
    @Expose
    @Nullable
    private int areaId;
    @SerializedName("BasicSalary")
    @Expose
    @Nullable
    private Double basicSalary;
    @SerializedName("BirthDate")
    @Expose
    @Nullable
    private String birthDate;
    @SerializedName("BsValidFromDate")
    @Expose
    @Nullable
    private String bsValidFromDate;
    @SerializedName("BsValidToDate")
    @Expose
    @Nullable
    private Object bsValidToDate;
    @SerializedName("CityId")
    @Expose
    @Nullable
    private int cityId;
    @SerializedName("CustodyAccCode")
    @Expose
    @Nullable
    private Object custodyAccCode;
    @SerializedName("DTGLAccount")
    @Expose
    @Nullable
    private Object dTGLAccount;
    @SerializedName("DTMobilesPlanItem")
    @Expose
    @Nullable
    private Object dTMobilesPlanItem;
    @SerializedName("DataLoaded")
    @Expose
    @Nullable
    private Object dataLoaded;
    @SerializedName("DateOfIssue")
    @Expose
    @Nullable
    private Object dateOfIssue;
    @SerializedName("DeptId")
    @Expose
    @Nullable
    private Object deptId;
    @SerializedName("Email")
    @Expose
    @Nullable
    private String email;
    @SerializedName("EmpArName")
    @Expose
    @Nullable
    private String empArName;
    @SerializedName("EmpEnName")
    @Expose
    @Nullable
    private String empEnName;
    @SerializedName("EmpId")
    @Expose
    @Nullable
    private Integer empId;
    @SerializedName("EmpNickName")
    @Expose
    @Nullable
    private String empNickName;
    @SerializedName("EmpSerial")
    @Expose
    @Nullable
    private Object empSerial;
    @SerializedName("EmpSignature")
    @Expose
    @Nullable
    private Object empSignature;
    @SerializedName("EmployeeEffectiveDate")
    @Expose
    @Nullable
    private String employeeEffectiveDate;
    @SerializedName("ExpireDate")
    @Expose
    @Nullable
    private Object expireDate;
    @SerializedName("FacultyId")
    @Expose
    @Nullable
    private Object facultyId;
    @SerializedName("FingerPrintEnrollNumber")
    @Expose
    @Nullable
    private Integer fingerPrintEnrollNumber;
    @SerializedName("FormalEmail")
    @Expose
    @Nullable
    private String formalEmail;
    @SerializedName("GraduationYear")
    @Expose
    @Nullable
    private String graduationYear;
    @SerializedName("HiringDate")
    @Expose
    @Nullable
    private String hiringDate;
    @SerializedName("isLeaved")
    @Expose
    @Nullable
    private Boolean isLeaved;
    @SerializedName("IsMale")
    @Expose
    @Nullable
    private Boolean isMale;
    @SerializedName("IsManger")
    @Expose
    @Nullable
    private Object isManger;
    @SerializedName("IssueGovern")
    @Expose
    @Nullable
    private Object issueGovern;
    @SerializedName("IssuedBy")
    @Expose
    @Nullable
    private Object issuedBy;
    @SerializedName("JobId")
    @Expose
    @Nullable
    private int jobId;
    @SerializedName("JustificationsDescription")
    @Expose
    @Nullable
    private Object justificationsDescription;
    @SerializedName("Lang")
    @Expose
    @Nullable
    private String lang;
    @SerializedName("Lat")
    @Expose
    @Nullable
    private String lat;
    @SerializedName("leavingDate")
    @Expose
    @Nullable
    private Object leavingDate;
    @SerializedName("LeavingJustificationsId")
    @Expose
    @Nullable
    private Object leavingJustificationsId;
    @SerializedName("LoanAccCode")
    @Expose
    @Nullable
    private Object loanAccCode;
    @SerializedName("ManagerId")
    @Expose
    @Nullable
    private int managerId;
    @SerializedName("MangerialLevel")
    @Expose
    @Nullable
    private Object mangerialLevel;
    @SerializedName("MaritalStatus")
    @Expose
    @Nullable
    private Object maritalStatus;
    @SerializedName("MilitarystatusId")
    @Expose
    @Nullable
    private int militarystatusId;
    @SerializedName("ModifyDatetime")
    @Expose
    @Nullable
    private Object modifyDatetime;
    @SerializedName("ModifyMac")
    @Expose
    @Nullable
    private Object modifyMac;
    @SerializedName("ModifyUserId")
    @Expose
    @Nullable
    private Object modifyUserId;
    @SerializedName("NationalIdNum")
    @Expose
    @Nullable
    private String nationalIdNum;
    @SerializedName("PersonalTel")
    @Expose
    @Nullable
    private String personalTel;
    @SerializedName("PersonalTel2")
    @Expose
    @Nullable
    private String personalTel2;
    @SerializedName("SecId")
    @Expose
    @Nullable
    private Object secId;
    @SerializedName("SubAreaId")
    @Expose
    @Nullable
    private int subAreaId;
    @SerializedName("WorkAreaId")
    @Expose
    @Nullable
    private int workAreaId;
    @SerializedName("WorkCityId")
    @Expose
    @Nullable
    private int workCityId;
    @SerializedName("WorkSubAreaId")
    @Expose
    @Nullable
    private int workSubAreaId;

    @Nullable
    public ArrayList<DTExperience> getDLExperiences() {
        return DLExperiences;
    }

    public void setDLExperiences(@Nullable ArrayList<DTExperience> DLExperiences) {
        this.DLExperiences = DLExperiences;
    }

    @Nullable
    public ArrayList<DTImage> getDLImages() {
        return DLImages;
    }

    public void setDLImages(@Nullable ArrayList<DTImage> DLImages) {
        this.DLImages = DLImages;
    }

    @Nullable
    public ArrayList<DTBankAccount> getDTBankAccounts() {
        return DTBankAccounts;
    }

    public void setDTBankAccounts(@Nullable ArrayList<DTBankAccount> DTBankAccounts) {
        this.DTBankAccounts = DTBankAccounts;
    }

    @Nullable
    public Object getDTDeductions() {
        return DTDeductions;
    }

    public void setDTDeductions(@Nullable Object DTDeductions) {
        this.DTDeductions = DTDeductions;
    }

    @Nullable
    public ArrayList<DTBankAccount> getDTDues() {
        return DTDues;
    }

    public void setDTDues(@Nullable ArrayList<DTBankAccount> DTDues) {
        this.DTDues = DTDues;
    }

    @Nullable
    public ArrayList<DTExperience> getDTExperiences() {
        return DTExperiences;
    }

    public void setDTExperiences(@Nullable ArrayList<DTExperience> DTExperiences) {
        this.DTExperiences = DTExperiences;
    }

    @Nullable
    public ArrayList<DTImage> getDTImages() {
        return DTImages;
    }

    public void setDTImages(@Nullable ArrayList<DTImage> DTImages) {
        this.DTImages = DTImages;
    }

    @Nullable
    public ArrayList<com.devartlab.model.DTMobiles> getDTMobiles() {
        return DTMobiles;
    }

    public void setDTMobiles(@Nullable ArrayList<com.devartlab.model.DTMobiles> DTMobiles) {
        this.DTMobiles = DTMobiles;
    }

    @Nullable
    public Object getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(@Nullable Object addDateTime) {
        this.addDateTime = addDateTime;
    }

    @Nullable
    public Object getAddMAc() {
        return addMAc;
    }

    public void setAddMAc(@Nullable Object addMAc) {
        this.addMAc = addMAc;
    }

    public int getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(int addUserId) {
        this.addUserId = addUserId;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Nullable
    public Object getAllowToUpdateLocationAddress() {
        return allowToUpdateLocationAddress;
    }

    public void setAllowToUpdateLocationAddress(@Nullable Object allowToUpdateLocationAddress) {
        this.allowToUpdateLocationAddress = allowToUpdateLocationAddress;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Nullable
    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(@Nullable Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    @Nullable
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@Nullable String birthDate) {
        this.birthDate = birthDate;
    }

    @Nullable
    public String getBsValidFromDate() {
        return bsValidFromDate;
    }

    public void setBsValidFromDate(@Nullable String bsValidFromDate) {
        this.bsValidFromDate = bsValidFromDate;
    }

    @Nullable
    public Object getBsValidToDate() {
        return bsValidToDate;
    }

    public void setBsValidToDate(@Nullable Object bsValidToDate) {
        this.bsValidToDate = bsValidToDate;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Nullable
    public Object getCustodyAccCode() {
        return custodyAccCode;
    }

    public void setCustodyAccCode(@Nullable Object custodyAccCode) {
        this.custodyAccCode = custodyAccCode;
    }

    @Nullable
    public Object getdTGLAccount() {
        return dTGLAccount;
    }

    public void setdTGLAccount(@Nullable Object dTGLAccount) {
        this.dTGLAccount = dTGLAccount;
    }

    @Nullable
    public Object getdTMobilesPlanItem() {
        return dTMobilesPlanItem;
    }

    public void setdTMobilesPlanItem(@Nullable Object dTMobilesPlanItem) {
        this.dTMobilesPlanItem = dTMobilesPlanItem;
    }

    @Nullable
    public Object getDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(@Nullable Object dataLoaded) {
        this.dataLoaded = dataLoaded;
    }

    @Nullable
    public Object getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(@Nullable Object dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Nullable
    public Object getDeptId() {
        return deptId;
    }

    public void setDeptId(@Nullable Object deptId) {
        this.deptId = deptId;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getEmpArName() {
        return empArName;
    }

    public void setEmpArName(@Nullable String empArName) {
        this.empArName = empArName;
    }

    @Nullable
    public String getEmpEnName() {
        return empEnName;
    }

    public void setEmpEnName(@Nullable String empEnName) {
        this.empEnName = empEnName;
    }

    @Nullable
    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(@Nullable Integer empId) {
        this.empId = empId;
    }

    @Nullable
    public String getEmpNickName() {
        return empNickName;
    }

    public void setEmpNickName(@Nullable String empNickName) {
        this.empNickName = empNickName;
    }

    @Nullable
    public Object getEmpSerial() {
        return empSerial;
    }

    public void setEmpSerial(@Nullable Object empSerial) {
        this.empSerial = empSerial;
    }

    @Nullable
    public Object getEmpSignature() {
        return empSignature;
    }

    public void setEmpSignature(@Nullable Object empSignature) {
        this.empSignature = empSignature;
    }

    @Nullable
    public String getEmployeeEffectiveDate() {
        return employeeEffectiveDate;
    }

    public void setEmployeeEffectiveDate(@Nullable String employeeEffectiveDate) {
        this.employeeEffectiveDate = employeeEffectiveDate;
    }

    @Nullable
    public Object getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(@Nullable Object expireDate) {
        this.expireDate = expireDate;
    }

    @Nullable
    public Object getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(@Nullable Object facultyId) {
        this.facultyId = facultyId;
    }

    @Nullable
    public Integer getFingerPrintEnrollNumber() {
        return fingerPrintEnrollNumber;
    }

    public void setFingerPrintEnrollNumber(@Nullable Integer fingerPrintEnrollNumber) {
        this.fingerPrintEnrollNumber = fingerPrintEnrollNumber;
    }

    @Nullable
    public String getFormalEmail() {
        return formalEmail;
    }

    public void setFormalEmail(@Nullable String formalEmail) {
        this.formalEmail = formalEmail;
    }

    @Nullable
    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(@Nullable String graduationYear) {
        this.graduationYear = graduationYear;
    }

    @Nullable
    public String getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(@Nullable String hiringDate) {
        this.hiringDate = hiringDate;
    }

    @Nullable
    public Boolean getLeaved() {
        return isLeaved;
    }

    public void setLeaved(@Nullable Boolean leaved) {
        isLeaved = leaved;
    }

    @Nullable
    public Boolean getMale() {
        return isMale;
    }

    public void setMale(@Nullable Boolean male) {
        isMale = male;
    }

    @Nullable
    public Object getIsManger() {
        return isManger;
    }

    public void setIsManger(@Nullable Object isManger) {
        this.isManger = isManger;
    }

    @Nullable
    public Object getIssueGovern() {
        return issueGovern;
    }

    public void setIssueGovern(@Nullable Object issueGovern) {
        this.issueGovern = issueGovern;
    }

    @Nullable
    public Object getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(@Nullable Object issuedBy) {
        this.issuedBy = issuedBy;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Nullable
    public Object getJustificationsDescription() {
        return justificationsDescription;
    }

    public void setJustificationsDescription(@Nullable Object justificationsDescription) {
        this.justificationsDescription = justificationsDescription;
    }

    @Nullable
    public String getLang() {
        return lang;
    }

    public void setLang(@Nullable String lang) {
        this.lang = lang;
    }

    @Nullable
    public String getLat() {
        return lat;
    }

    public void setLat(@Nullable String lat) {
        this.lat = lat;
    }

    @Nullable
    public Object getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(@Nullable Object leavingDate) {
        this.leavingDate = leavingDate;
    }

    @Nullable
    public Object getLeavingJustificationsId() {
        return leavingJustificationsId;
    }

    public void setLeavingJustificationsId(@Nullable Object leavingJustificationsId) {
        this.leavingJustificationsId = leavingJustificationsId;
    }

    @Nullable
    public Object getLoanAccCode() {
        return loanAccCode;
    }

    public void setLoanAccCode(@Nullable Object loanAccCode) {
        this.loanAccCode = loanAccCode;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Nullable
    public Object getMangerialLevel() {
        return mangerialLevel;
    }

    public void setMangerialLevel(@Nullable Object mangerialLevel) {
        this.mangerialLevel = mangerialLevel;
    }

    @Nullable
    public Object getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(@Nullable Object maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getMilitarystatusId() {
        return militarystatusId;
    }

    public void setMilitarystatusId(int militarystatusId) {
        this.militarystatusId = militarystatusId;
    }

    @Nullable
    public Object getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(@Nullable Object modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    @Nullable
    public Object getModifyMac() {
        return modifyMac;
    }

    public void setModifyMac(@Nullable Object modifyMac) {
        this.modifyMac = modifyMac;
    }

    @Nullable
    public Object getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(@Nullable Object modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    @Nullable
    public String getNationalIdNum() {
        return nationalIdNum;
    }

    public void setNationalIdNum(@Nullable String nationalIdNum) {
        this.nationalIdNum = nationalIdNum;
    }

    @Nullable
    public String getPersonalTel() {
        return personalTel;
    }

    public void setPersonalTel(@Nullable String personalTel) {
        this.personalTel = personalTel;
    }

    @Nullable
    public String getPersonalTel2() {
        return personalTel2;
    }

    public void setPersonalTel2(@Nullable String personalTel2) {
        this.personalTel2 = personalTel2;
    }

    @Nullable
    public Object getSecId() {
        return secId;
    }

    public void setSecId(@Nullable Object secId) {
        this.secId = secId;
    }

    public int getSubAreaId() {
        return subAreaId;
    }

    public void setSubAreaId(int subAreaId) {
        this.subAreaId = subAreaId;
    }

    public int getWorkAreaId() {
        return workAreaId;
    }

    public void setWorkAreaId(int workAreaId) {
        this.workAreaId = workAreaId;
    }

    public int getWorkCityId() {
        return workCityId;
    }

    public void setWorkCityId(int workCityId) {
        this.workCityId = workCityId;
    }

    public int getWorkSubAreaId() {
        return workSubAreaId;
    }

    public void setWorkSubAreaId(int workSubAreaId) {
        this.workSubAreaId = workSubAreaId;
    }
}