
package com.devartlab.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPrincipalDatum {

    @SerializedName("MarkReqId")
    @Expose
    @Nullable
    private Integer markReqId;
    @SerializedName("ParentMarkReqId")
    @Expose
    @Nullable
    private Integer parentMarkReqId;
    @SerializedName("MarkReqCode")
    @Expose
    @Nullable
    private String markReqCode;
    @SerializedName("MarkReqTypeId")
    @Expose
    @Nullable
    private Integer markReqTypeId;
    @SerializedName("MarkReqDate")
    @Expose
    @Nullable
    private String markReqDate;
    @SerializedName("MarkReqAssignId")
    @Expose
    @Nullable
    private Integer markReqAssignId;
    @SerializedName("MarkReqAccountId")
    @Expose
    @Nullable
    private Integer markReqAccountId;
    @SerializedName("MarkReqEmpId")
    @Expose
    @Nullable
    private Integer markReqEmpId;
    @SerializedName("MarkReqApplicantId")
    @Expose
    @Nullable
    private Integer markReqApplicantId;
    @SerializedName("EventSpecialityStrId")
    @Expose
    @Nullable
    private Object eventSpecialityStrId;
    @SerializedName("MarkReqNumCustomers")
    @Expose
    @Nullable
    private Integer markReqNumCustomers;
    @SerializedName("MarkReqNumEmployee")
    @Expose
    @Nullable
    private Object markReqNumEmployee;
    @SerializedName("MarkReqCustodyEmpId")
    @Expose
    @Nullable
    private Object markReqCustodyEmpId;
    @SerializedName("MarkReqTarget")
    @Expose
    @Nullable
    private Object markReqTarget;
    @SerializedName("MarkReqExchangeDate")
    @Expose
    @Nullable
    private String markReqExchangeDate;
    @SerializedName("PayMethodId")
    @Expose
    @Nullable
    private Integer payMethodId;
    @SerializedName("PayMethodDesc")
    @Expose
    @Nullable
    private Object payMethodDesc;
    @SerializedName("MarkReqExecutDate")
    @Expose
    @Nullable
    private String markReqExecutDate;
    @SerializedName("IncomePeriodFrom")
    @Expose
    @Nullable
    private String incomePeriodFrom;
    @SerializedName("IncomePeriodTo")
    @Expose
    @Nullable
    private String incomePeriodTo;
    @SerializedName("TotalGain")
    @Expose
    @Nullable
    private Double totalGain;
    @SerializedName("TotalPerPeriod")
    @Expose
    @Nullable
    private Double totalPerPeriod;
    @SerializedName("TotalCoast")
    @Expose
    @Nullable
    private Double totalCoast;
    @SerializedName("PrevMonth1")
    @Expose
    @Nullable
    private Object prevMonth1;
    @SerializedName("PrevMonth2")
    @Expose
    @Nullable
    private Object prevMonth2;
    @SerializedName("PrevMonth3")
    @Expose
    @Nullable
    private Object prevMonth3;
    @SerializedName("Forcaste")
    @Expose
    @Nullable
    private Object forcaste;
    @SerializedName("PlaceGift")
    @Expose
    @Nullable
    private String placeGift;
    @SerializedName("CashBondId")
    @Expose
    @Nullable
    private Object cashBondId;
    @SerializedName("BudgetDate")
    @Expose
    @Nullable
    private Object budgetDate;
    @SerializedName("BudgetValue")
    @Expose
    @Nullable
    private Object budgetValue;
    @SerializedName("BudgetAllow")
    @Expose
    @Nullable
    private Object budgetAllow;
    @SerializedName("ISExecuted")
    @Expose
    @Nullable
    private Object iSExecuted;
    @SerializedName("AddUserId")
    @Expose
    @Nullable
    private Integer addUserId;
    @SerializedName("AddMAc")
    @Expose
    @Nullable
    private String addMAc;
    @SerializedName("AddDateTime")
    @Expose
    @Nullable
    private String addDateTime;
    @SerializedName("ModifyUserId")
    @Expose
    @Nullable
    private Integer modifyUserId;
    @SerializedName("ModifyMac")
    @Expose
    @Nullable
    private String modifyMac;
    @SerializedName("ModifyDatetime")
    @Expose
    @Nullable
    private String modifyDatetime;
    @SerializedName("ReqDescription")
    @Expose
    @Nullable
    private String reqDescription;
    @SerializedName("FinanceDataUserId")
    @Expose
    @Nullable
    private Object financeDataUserId;
    @SerializedName("FinanceDataMAc")
    @Expose
    @Nullable
    private Object financeDataMAc;
    @SerializedName("FinanceDataDateTime")
    @Expose
    @Nullable
    private Object financeDataDateTime;
    @SerializedName("IsSettelmentClosed")
    @Expose
    @Nullable
    private Object isSettelmentClosed;
    @SerializedName("IsUnCompleteSettelment")
    @Expose
    @Nullable
    private Object isUnCompleteSettelment;
    @SerializedName("TotalSettelment")
    @Expose
    @Nullable
    private Object totalSettelment;
    @SerializedName("SettelmentNotes")
    @Expose
    @Nullable
    private Object settelmentNotes;
    @SerializedName("Approved")
    @Expose
    @Nullable
    private Object approved;
    @SerializedName("ApprovedBy")
    @Expose
    @Nullable
    private Object approvedBy;
    @SerializedName("ApprovedByEmpId")
    @Expose
    @Nullable
    private Object approvedByEmpId;
    @SerializedName("ApprovedDate")
    @Expose
    @Nullable
    private Object approvedDate;
    @SerializedName("ApprovalNotes")
    @Expose
    @Nullable
    private Object approvalNotes;
    @SerializedName("IsCanceled")
    @Expose
    @Nullable
    private Object isCanceled;
    @SerializedName("Expired")
    @Expose
    @Nullable
    private Object expired;
    @SerializedName("ExpiredDate")
    @Expose
    @Nullable
    private Object expiredDate;
    @SerializedName("IsPayed")
    @Expose
    @Nullable
    private Object isPayed;
    @SerializedName("PayedValue")
    @Expose
    @Nullable
    private Object payedValue;
    @SerializedName("CashingDate")
    @Expose
    @Nullable
    private Object cashingDate;
    @SerializedName("CashingUserId")
    @Expose
    @Nullable
    private Object cashingUserId;
    @SerializedName("CashingEMpId")
    @Expose
    @Nullable
    private Object cashingEMpId;
    @SerializedName("IsNoCustdyRequest")
    @Expose
    @Nullable
    private Object isNoCustdyRequest;
    @SerializedName("NoCustdySettelmentDate")
    @Expose
    @Nullable
    private Object noCustdySettelmentDate;
    @SerializedName("NoCustdySettelmentEMpId")
    @Expose
    @Nullable
    private Object noCustdySettelmentEMpId;
    @SerializedName("NoCustdySettelmentUserId")
    @Expose
    @Nullable
    private Object noCustdySettelmentUserId;
    @SerializedName("NoCustdySettelmentSupplierId")
    @Expose
    @Nullable
    private Object noCustdySettelmentSupplierId;
    @SerializedName("NoCustdySettelmentDocType")
    @Expose
    @Nullable
    private Object noCustdySettelmentDocType;
    @SerializedName("NoCustdySettelmentDocNumber")
    @Expose
    @Nullable
    private Object noCustdySettelmentDocNumber;
    @SerializedName("NoCustdySettelmentNotes")
    @Expose
    @Nullable
    private Object noCustdySettelmentNotes;
    @SerializedName("CustomerAttendanceReported")
    @Expose
    @Nullable
    private Object customerAttendanceReported;
    @SerializedName("ExtraRequest")
    @Expose
    @Nullable
    private Boolean extraRequest;
    @SerializedName("CashFlowDate")
    @Expose
    @Nullable
    private Object cashFlowDate;
    @SerializedName("CashFlowAddByEMpId")
    @Expose
    @Nullable
    private Object cashFlowAddByEMpId;
    @SerializedName("CashFlowAddByAccountId")
    @Expose
    @Nullable
    private Object cashFlowAddByAccountId;
    @SerializedName("CashFlowAddDateTime")
    @Expose
    @Nullable
    private Object cashFlowAddDateTime;
    @SerializedName("RequestType")
    @Expose
    @Nullable
    private String requestType;
    @SerializedName("RequestTerriotry")
    @Expose
    @Nullable
    private String requestTerriotry;
    @SerializedName("MarkReqApplicantName")
    @Expose
    @Nullable
    private String markReqApplicantName;
    @SerializedName("ParentRequestDesc")
    @Expose
    @Nullable
    private String parentRequestDesc;
    @SerializedName("RequestSpeciality")
    @Expose
    @Nullable
    private String requestSpeciality;
    @SerializedName("AllowToUpdate")
    @Expose
    @Nullable
    private Boolean allowToUpdate;

    @Nullable
    public Integer getMarkReqId() {
        return markReqId;
    }

    public void setMarkReqId(@Nullable Integer markReqId) {
        this.markReqId = markReqId;
    }

    @Nullable
    public Integer getParentMarkReqId() {
        return parentMarkReqId;
    }

    public void setParentMarkReqId(@Nullable Integer parentMarkReqId) {
        this.parentMarkReqId = parentMarkReqId;
    }

    @Nullable
    public String getMarkReqCode() {
        return markReqCode;
    }

    public void setMarkReqCode(@Nullable String markReqCode) {
        this.markReqCode = markReqCode;
    }

    @Nullable
    public Integer getMarkReqTypeId() {
        return markReqTypeId;
    }

    public void setMarkReqTypeId(@Nullable Integer markReqTypeId) {
        this.markReqTypeId = markReqTypeId;
    }

    @Nullable
    public String getMarkReqDate() {
        return markReqDate;
    }

    public void setMarkReqDate(@Nullable String markReqDate) {
        this.markReqDate = markReqDate;
    }

    @Nullable
    public Integer getMarkReqAssignId() {
        return markReqAssignId;
    }

    public void setMarkReqAssignId(@Nullable Integer markReqAssignId) {
        this.markReqAssignId = markReqAssignId;
    }

    @Nullable
    public Integer getMarkReqAccountId() {
        return markReqAccountId;
    }

    public void setMarkReqAccountId(@Nullable Integer markReqAccountId) {
        this.markReqAccountId = markReqAccountId;
    }

    @Nullable
    public Integer getMarkReqEmpId() {
        return markReqEmpId;
    }

    public void setMarkReqEmpId(@Nullable Integer markReqEmpId) {
        this.markReqEmpId = markReqEmpId;
    }

    @Nullable
    public Integer getMarkReqApplicantId() {
        return markReqApplicantId;
    }

    public void setMarkReqApplicantId(@Nullable Integer markReqApplicantId) {
        this.markReqApplicantId = markReqApplicantId;
    }

    @Nullable
    public Object getEventSpecialityStrId() {
        return eventSpecialityStrId;
    }

    public void setEventSpecialityStrId(@Nullable Object eventSpecialityStrId) {
        this.eventSpecialityStrId = eventSpecialityStrId;
    }

    @Nullable
    public Integer getMarkReqNumCustomers() {
        return markReqNumCustomers;
    }

    public void setMarkReqNumCustomers(@Nullable Integer markReqNumCustomers) {
        this.markReqNumCustomers = markReqNumCustomers;
    }

    @Nullable
    public Object getMarkReqNumEmployee() {
        return markReqNumEmployee;
    }

    public void setMarkReqNumEmployee(@Nullable Object markReqNumEmployee) {
        this.markReqNumEmployee = markReqNumEmployee;
    }

    @Nullable
    public Object getMarkReqCustodyEmpId() {
        return markReqCustodyEmpId;
    }

    public void setMarkReqCustodyEmpId(@Nullable Object markReqCustodyEmpId) {
        this.markReqCustodyEmpId = markReqCustodyEmpId;
    }

    @Nullable
    public Object getMarkReqTarget() {
        return markReqTarget;
    }

    public void setMarkReqTarget(@Nullable Object markReqTarget) {
        this.markReqTarget = markReqTarget;
    }

    @Nullable
    public String getMarkReqExchangeDate() {
        return markReqExchangeDate;
    }

    public void setMarkReqExchangeDate(@Nullable String markReqExchangeDate) {
        this.markReqExchangeDate = markReqExchangeDate;
    }

    @Nullable
    public Integer getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(@Nullable Integer payMethodId) {
        this.payMethodId = payMethodId;
    }

    @Nullable
    public Object getPayMethodDesc() {
        return payMethodDesc;
    }

    public void setPayMethodDesc(@Nullable Object payMethodDesc) {
        this.payMethodDesc = payMethodDesc;
    }

    @Nullable
    public String getMarkReqExecutDate() {
        return markReqExecutDate;
    }

    public void setMarkReqExecutDate(@Nullable String markReqExecutDate) {
        this.markReqExecutDate = markReqExecutDate;
    }

    @Nullable
    public String getIncomePeriodFrom() {
        return incomePeriodFrom;
    }

    public void setIncomePeriodFrom(@Nullable String incomePeriodFrom) {
        this.incomePeriodFrom = incomePeriodFrom;
    }

    @Nullable
    public String getIncomePeriodTo() {
        return incomePeriodTo;
    }

    public void setIncomePeriodTo(@Nullable String incomePeriodTo) {
        this.incomePeriodTo = incomePeriodTo;
    }

    @Nullable
    public Double getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(@Nullable Double totalGain) {
        this.totalGain = totalGain;
    }

    @Nullable
    public Double getTotalPerPeriod() {
        return totalPerPeriod;
    }

    public void setTotalPerPeriod(@Nullable Double totalPerPeriod) {
        this.totalPerPeriod = totalPerPeriod;
    }

    @Nullable
    public Double getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(@Nullable Double totalCoast) {
        this.totalCoast = totalCoast;
    }

    @Nullable
    public Object getPrevMonth1() {
        return prevMonth1;
    }

    public void setPrevMonth1(@Nullable Object prevMonth1) {
        this.prevMonth1 = prevMonth1;
    }

    @Nullable
    public Object getPrevMonth2() {
        return prevMonth2;
    }

    public void setPrevMonth2(@Nullable Object prevMonth2) {
        this.prevMonth2 = prevMonth2;
    }

    @Nullable
    public Object getPrevMonth3() {
        return prevMonth3;
    }

    public void setPrevMonth3(@Nullable Object prevMonth3) {
        this.prevMonth3 = prevMonth3;
    }

    @Nullable
    public Object getForcaste() {
        return forcaste;
    }

    public void setForcaste(@Nullable Object forcaste) {
        this.forcaste = forcaste;
    }

    @Nullable
    public String getPlaceGift() {
        return placeGift;
    }

    public void setPlaceGift(@Nullable String placeGift) {
        this.placeGift = placeGift;
    }

    @Nullable
    public Object getCashBondId() {
        return cashBondId;
    }

    public void setCashBondId(@Nullable Object cashBondId) {
        this.cashBondId = cashBondId;
    }

    @Nullable
    public Object getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(@Nullable Object budgetDate) {
        this.budgetDate = budgetDate;
    }

    @Nullable
    public Object getBudgetValue() {
        return budgetValue;
    }

    public void setBudgetValue(@Nullable Object budgetValue) {
        this.budgetValue = budgetValue;
    }

    @Nullable
    public Object getBudgetAllow() {
        return budgetAllow;
    }

    public void setBudgetAllow(@Nullable Object budgetAllow) {
        this.budgetAllow = budgetAllow;
    }

    @Nullable
    public Object getiSExecuted() {
        return iSExecuted;
    }

    public void setiSExecuted(@Nullable Object iSExecuted) {
        this.iSExecuted = iSExecuted;
    }

    @Nullable
    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(@Nullable Integer addUserId) {
        this.addUserId = addUserId;
    }

    @Nullable
    public String getAddMAc() {
        return addMAc;
    }

    public void setAddMAc(@Nullable String addMAc) {
        this.addMAc = addMAc;
    }

    @Nullable
    public String getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime(@Nullable String addDateTime) {
        this.addDateTime = addDateTime;
    }

    @Nullable
    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(@Nullable Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    @Nullable
    public String getModifyMac() {
        return modifyMac;
    }

    public void setModifyMac(@Nullable String modifyMac) {
        this.modifyMac = modifyMac;
    }

    @Nullable
    public String getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(@Nullable String modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    @Nullable
    public String getReqDescription() {
        return reqDescription;
    }

    public void setReqDescription(@Nullable String reqDescription) {
        this.reqDescription = reqDescription;
    }

    @Nullable
    public Object getFinanceDataUserId() {
        return financeDataUserId;
    }

    public void setFinanceDataUserId(@Nullable Object financeDataUserId) {
        this.financeDataUserId = financeDataUserId;
    }

    @Nullable
    public Object getFinanceDataMAc() {
        return financeDataMAc;
    }

    public void setFinanceDataMAc(@Nullable Object financeDataMAc) {
        this.financeDataMAc = financeDataMAc;
    }

    @Nullable
    public Object getFinanceDataDateTime() {
        return financeDataDateTime;
    }

    public void setFinanceDataDateTime(@Nullable Object financeDataDateTime) {
        this.financeDataDateTime = financeDataDateTime;
    }

    @Nullable
    public Object getIsSettelmentClosed() {
        return isSettelmentClosed;
    }

    public void setIsSettelmentClosed(@Nullable Object isSettelmentClosed) {
        this.isSettelmentClosed = isSettelmentClosed;
    }

    @Nullable
    public Object getIsUnCompleteSettelment() {
        return isUnCompleteSettelment;
    }

    public void setIsUnCompleteSettelment(@Nullable Object isUnCompleteSettelment) {
        this.isUnCompleteSettelment = isUnCompleteSettelment;
    }

    @Nullable
    public Object getTotalSettelment() {
        return totalSettelment;
    }

    public void setTotalSettelment(@Nullable Object totalSettelment) {
        this.totalSettelment = totalSettelment;
    }

    @Nullable
    public Object getSettelmentNotes() {
        return settelmentNotes;
    }

    public void setSettelmentNotes(@Nullable Object settelmentNotes) {
        this.settelmentNotes = settelmentNotes;
    }

    @Nullable
    public Object getApproved() {
        return approved;
    }

    public void setApproved(@Nullable Object approved) {
        this.approved = approved;
    }

    @Nullable
    public Object getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(@Nullable Object approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Nullable
    public Object getApprovedByEmpId() {
        return approvedByEmpId;
    }

    public void setApprovedByEmpId(@Nullable Object approvedByEmpId) {
        this.approvedByEmpId = approvedByEmpId;
    }

    @Nullable
    public Object getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(@Nullable Object approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Nullable
    public Object getApprovalNotes() {
        return approvalNotes;
    }

    public void setApprovalNotes(@Nullable Object approvalNotes) {
        this.approvalNotes = approvalNotes;
    }

    @Nullable
    public Object getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(@Nullable Object isCanceled) {
        this.isCanceled = isCanceled;
    }

    @Nullable
    public Object getExpired() {
        return expired;
    }

    public void setExpired(@Nullable Object expired) {
        this.expired = expired;
    }

    @Nullable
    public Object getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(@Nullable Object expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Nullable
    public Object getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(@Nullable Object isPayed) {
        this.isPayed = isPayed;
    }

    @Nullable
    public Object getPayedValue() {
        return payedValue;
    }

    public void setPayedValue(@Nullable Object payedValue) {
        this.payedValue = payedValue;
    }

    @Nullable
    public Object getCashingDate() {
        return cashingDate;
    }

    public void setCashingDate(@Nullable Object cashingDate) {
        this.cashingDate = cashingDate;
    }

    @Nullable
    public Object getCashingUserId() {
        return cashingUserId;
    }

    public void setCashingUserId(@Nullable Object cashingUserId) {
        this.cashingUserId = cashingUserId;
    }

    @Nullable
    public Object getCashingEMpId() {
        return cashingEMpId;
    }

    public void setCashingEMpId(@Nullable Object cashingEMpId) {
        this.cashingEMpId = cashingEMpId;
    }

    @Nullable
    public Object getIsNoCustdyRequest() {
        return isNoCustdyRequest;
    }

    public void setIsNoCustdyRequest(@Nullable Object isNoCustdyRequest) {
        this.isNoCustdyRequest = isNoCustdyRequest;
    }

    @Nullable
    public Object getNoCustdySettelmentDate() {
        return noCustdySettelmentDate;
    }

    public void setNoCustdySettelmentDate(@Nullable Object noCustdySettelmentDate) {
        this.noCustdySettelmentDate = noCustdySettelmentDate;
    }

    @Nullable
    public Object getNoCustdySettelmentEMpId() {
        return noCustdySettelmentEMpId;
    }

    public void setNoCustdySettelmentEMpId(@Nullable Object noCustdySettelmentEMpId) {
        this.noCustdySettelmentEMpId = noCustdySettelmentEMpId;
    }

    @Nullable
    public Object getNoCustdySettelmentUserId() {
        return noCustdySettelmentUserId;
    }

    public void setNoCustdySettelmentUserId(@Nullable Object noCustdySettelmentUserId) {
        this.noCustdySettelmentUserId = noCustdySettelmentUserId;
    }

    @Nullable
    public Object getNoCustdySettelmentSupplierId() {
        return noCustdySettelmentSupplierId;
    }

    public void setNoCustdySettelmentSupplierId(@Nullable Object noCustdySettelmentSupplierId) {
        this.noCustdySettelmentSupplierId = noCustdySettelmentSupplierId;
    }

    @Nullable
    public Object getNoCustdySettelmentDocType() {
        return noCustdySettelmentDocType;
    }

    public void setNoCustdySettelmentDocType(@Nullable Object noCustdySettelmentDocType) {
        this.noCustdySettelmentDocType = noCustdySettelmentDocType;
    }

    @Nullable
    public Object getNoCustdySettelmentDocNumber() {
        return noCustdySettelmentDocNumber;
    }

    public void setNoCustdySettelmentDocNumber(@Nullable Object noCustdySettelmentDocNumber) {
        this.noCustdySettelmentDocNumber = noCustdySettelmentDocNumber;
    }

    @Nullable
    public Object getNoCustdySettelmentNotes() {
        return noCustdySettelmentNotes;
    }

    public void setNoCustdySettelmentNotes(@Nullable Object noCustdySettelmentNotes) {
        this.noCustdySettelmentNotes = noCustdySettelmentNotes;
    }

    @Nullable
    public Object getCustomerAttendanceReported() {
        return customerAttendanceReported;
    }

    public void setCustomerAttendanceReported(@Nullable Object customerAttendanceReported) {
        this.customerAttendanceReported = customerAttendanceReported;
    }

    @Nullable
    public Boolean getExtraRequest() {
        return extraRequest;
    }

    public void setExtraRequest(@Nullable Boolean extraRequest) {
        this.extraRequest = extraRequest;
    }

    @Nullable
    public Object getCashFlowDate() {
        return cashFlowDate;
    }

    public void setCashFlowDate(@Nullable Object cashFlowDate) {
        this.cashFlowDate = cashFlowDate;
    }

    @Nullable
    public Object getCashFlowAddByEMpId() {
        return cashFlowAddByEMpId;
    }

    public void setCashFlowAddByEMpId(@Nullable Object cashFlowAddByEMpId) {
        this.cashFlowAddByEMpId = cashFlowAddByEMpId;
    }

    @Nullable
    public Object getCashFlowAddByAccountId() {
        return cashFlowAddByAccountId;
    }

    public void setCashFlowAddByAccountId(@Nullable Object cashFlowAddByAccountId) {
        this.cashFlowAddByAccountId = cashFlowAddByAccountId;
    }

    @Nullable
    public Object getCashFlowAddDateTime() {
        return cashFlowAddDateTime;
    }

    public void setCashFlowAddDateTime(@Nullable Object cashFlowAddDateTime) {
        this.cashFlowAddDateTime = cashFlowAddDateTime;
    }

    @Nullable
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(@Nullable String requestType) {
        this.requestType = requestType;
    }

    @Nullable
    public String getRequestTerriotry() {
        return requestTerriotry;
    }

    public void setRequestTerriotry(@Nullable String requestTerriotry) {
        this.requestTerriotry = requestTerriotry;
    }

    @Nullable
    public String getMarkReqApplicantName() {
        return markReqApplicantName;
    }

    public void setMarkReqApplicantName(@Nullable String markReqApplicantName) {
        this.markReqApplicantName = markReqApplicantName;
    }

    @Nullable
    public String getParentRequestDesc() {
        return parentRequestDesc;
    }

    public void setParentRequestDesc(@Nullable String parentRequestDesc) {
        this.parentRequestDesc = parentRequestDesc;
    }

    @Nullable
    public String getRequestSpeciality() {
        return requestSpeciality;
    }

    public void setRequestSpeciality(@Nullable String requestSpeciality) {
        this.requestSpeciality = requestSpeciality;
    }

    @Nullable
    public Boolean getAllowToUpdate() {
        return allowToUpdate;
    }

    public void setAllowToUpdate(@Nullable Boolean allowToUpdate) {
        this.allowToUpdate = allowToUpdate;
    }
}