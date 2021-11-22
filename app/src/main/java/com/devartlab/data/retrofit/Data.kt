package com.devartlab.data.retrofit

import android.os.Parcelable
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.data.room.contract.ContractList
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.invoicedetailes.CustomerInvoiceEntity
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.data.room.myballance.MyBallanceEntity
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import com.devartlab.model.*

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Data(


    @SerializedName("ads")
    @Expose
    var ads: ArrayList<AdModel>,

    @SerializedName("UsersStores")
    @Expose
    var userStores: ArrayList<UserStore>,


    @SerializedName("Plan")
    @Expose
    var planData: ArrayList<PlanModel>,

    @SerializedName("ActivityLIst")
    @Expose
    var activityLIst: ArrayList<ActivityEntity>,

    @SerializedName("ListType")
    @Expose
    var listTypesEntity: ArrayList<ListTypesEntity>,

    @SerializedName("CustomerList")
    @Expose
    var customerList: ArrayList<ListEntity>,

    @SerializedName("StartPointReportTitle")
    @Expose
    var startPointReportTitle: ArrayList<StartPointReportTitle>,

    @SerializedName("ListPermissionData")
    @Expose
    var listPermissionData: ArrayList<ListPermissionData>,

    @SerializedName("PlanDayPermissionData")
    @Expose
    var planDayPermissionData: ArrayList<PlanDayPermissionData>,

    @SerializedName("StartPointData")
    @Expose
    var startPointData: ArrayList<StartPointData>,

    @SerializedName("DoubleVisitReportDetails")
    @Expose
    var doubleVisitReportDetails: ArrayList<DoubleVisitReportDetails>,

    @SerializedName("ExpensesType")
    @Expose
    var expensesType: ArrayList<ExpensesType>,

    @SerializedName("MRRanks")
    @Expose
    var mrRanks: ArrayList<MRRank>,

    @SerializedName("StartPointReport")
    @Expose
    var startPointReport: ArrayList<StartPointReport>,

    @SerializedName("DoubleVisitReport")
    @Expose
    var doubleVisitReport: ArrayList<DoubleVisitReport>,

    @SerializedName("SVRanks")
    @Expose
    var svRanks: ArrayList<SVRank>,

    @SerializedName("MRRank")
    @Expose
    var mrRanksReports: ArrayList<MRRankReport>,

    @SerializedName("MOnthExpensesList")
    @Expose
    var expenses: ArrayList<Expenses>,

    @SerializedName("EmployeePenalties")
    @Expose
    var penalties: ArrayList<Penalties>,

    @SerializedName("PenaltiesType")
    @Expose
    var penaltiesType: ArrayList<PenaltyType>,

    @SerializedName("PenaltiesReason")
    @Expose
    var penaltiesReason: ArrayList<PenReason>,

    @SerializedName("EmployeeVacation")
    @Expose
    var employeeVacations: ArrayList<EmployeeVacation>,

    @SerializedName("VacationType")
    @Expose
    var vacationType: ArrayList<VacationType>,

    @SerializedName("VacationBallance")
    @Expose
    var vacationBallances: ArrayList<VacationBallance>,

    @SerializedName("SalaryItem")
    @Expose
    var salaryItem: ArrayList<SalaryItem>,

    @SerializedName("SalaryItemDetails")
    @Expose
    var salaryItemDetails: ArrayList<SalaryItemDetails>,

    @SerializedName("EmployeeData")
    @Expose
    var employeeData: ArrayList<EmployeeData_class>,

    @SerializedName("EMployeeDayDetails")
    @Expose
    var eMployeeDayDetails: ArrayList<EMployeeDayDetails_class>,

    @SerializedName("ExpensesTypeDetails")
    @Expose
    var expensesTypeDetails: ArrayList<ExpensesTypeDetails>,

    @SerializedName("StartEndPoint")
    @Expose
    var startEndPoint: ArrayList<StartEndPoint>,

    @SerializedName("EMployeeReport")
    @Expose
    var employeeReport: ArrayList<EmployeeReport>,

    @SerializedName("CoverageReport")
    @Expose
    var coverageReport: ArrayList<CoverageReport>,

    @SerializedName("Achivement")
    @Expose
    var achivement: ArrayList<Achievement>,

    @SerializedName("EMPloyeeAppraisal")
    @Expose
    var eMPloyeeAppraisal: ArrayList<EMPloyeeAppraisal>,

    @SerializedName("Employees")
    @Expose
    var employees: ArrayList<EmployeeModel>,

    @SerializedName("Table1")
    @Expose
    var cycleDataLogin: ArrayList<com.devartlab.model.Table1>,

    @SerializedName("userData")
    @Expose
    var userData: ArrayList<UserDatum>,

    @SerializedName("authorityData")
    @Expose
    var authorityData: ArrayList<AuthorityDatum>,

    @SerializedName("workData")
    @Expose
    var workData: ArrayList<WorkDatum>,

    @SerializedName("jobDescription")
    @Expose
    var jobDescription: ArrayList<JobDescription>,

    @SerializedName("areaData")
    @Expose
    var areaData: ArrayList<AreaDatum>,

    @SerializedName("additionalAuthorityData")
    @Expose
    var additionalAuthorityData: ArrayList<AdditionalAuthorityDatum>,

    @SerializedName("dateData")
    @Expose
    var dateData: ArrayList<DateDatum>,

    @SerializedName("Table10")
    @Expose
    var table10: ArrayList<Any>,

    @SerializedName("cycleData")
    @Expose
    var cycleData: ArrayList<CycleDatum>,

    @SerializedName("LoginData")
    @Expose
    var loginData: ArrayList<LoginData>,

    @SerializedName("Summary")
    @Expose
    var summary: ArrayList<Summary>,

    @SerializedName("Details")
    @Expose
    var details: ArrayList<Detail>,

    @SerializedName("RequestPrincipalData")
    @Expose
    var requestPrincipalData: ArrayList<RequestPrincipalDatum>,

    @SerializedName("RequestCustomer")
    @Expose
    var requestCustomer: ArrayList<RequestCustomer>,

    @SerializedName("RequestGainDetails")
    @Expose
    var requestGainDetails: ArrayList<RequestGainDetail>,

    @SerializedName("RequestGainPharmcy")
    @Expose
    var requestGainPharmcy: ArrayList<RequestGainPharmcy>,

    @SerializedName("RequestCostItems")
    @Expose
    var requestCostItems: ArrayList<RequestCostItem>,

    @SerializedName("RequestApprovedBy")
    @Expose
    var requestApprovedBy: ArrayList<RequestApprovedBy>,

    @SerializedName("EMployeeDayList")
    @Expose
    var eMployeeDayList_Class: ArrayList<EMployeeDayList_Class>,


    @SerializedName("PlanAndCoverCustomers")
    @Expose
    var planAndCoverCustomers: ArrayList<PlanAndCoverCustomers>,


    @SerializedName("CustomerReportDetilas")
    @Expose
    var customerReportDetilas: ArrayList<CustomerReportDetilas>,


    @SerializedName("CustomerPlanDetilas")
    @Expose
    var customerPlanDetilas: ArrayList<CustomerPlanDetilas>,

    @SerializedName("CustomeBudgetDetilas")
    @Expose
    var customeBudgetDetilas: ArrayList<CustomeBudgetDetilas>,

    @SerializedName("ContractList")
    @Expose
    var contractList: ArrayList<ContractList>,

    @SerializedName("COntractList")
    @Expose
    var contractList2: ArrayList<ContractEntity>,


    @SerializedName("StoreBallance")
    @Expose
    var storeBallance: ArrayList<MyBallanceEntity>,


    @SerializedName("ItemTable")
    @Expose
    var itemList: ArrayList<ContractList>,

    @SerializedName("InvDefSalesPurchaseType")
    @Expose
    var invDefSalesPurchaseType: ArrayList<PurchaseTypeEntity>,

    @SerializedName("SalePurchaseType")
    @Expose
    var salePurchaseType: ArrayList<PurchaseTypeEntity>,

    @SerializedName("EMPloyeeStoreInvoice")
    @Expose
    var storeInvoice: ArrayList<EMPloyeeStoreInvoice>,

    @SerializedName("CustomerInvoiceDashboard")
    @Expose
    var customerInvoiceDashboard: ArrayList<CustomerInvoiceDashboard>,

    @SerializedName("CustomerInvoiceDetails")
    @Expose
    var customerInvoiceDetails: ArrayList<CustomerInvoiceEntity>,

    @SerializedName("CustomerInvoice")
    @Expose
    var CustomerInvoice: ArrayList<CustomerInvoiceEntity>,

    @SerializedName("InventoryType")
    @Expose
    var inventoryTrxType: ArrayList<InventoryTrxType>,


    @SerializedName("ClassList")
    @Expose
    var classList: ArrayList<FilterDataEntity>,

    @SerializedName("SpecilaityLIst")
    @Expose
    var specialityLIst: ArrayList<FilterDataEntity>,

    @SerializedName("TerriotryList")
    @Expose
    var territoryList: ArrayList<FilterDataEntity>,

    @SerializedName("BrickList")
    @Expose
    var brickList: ArrayList<FilterDataEntity>,

    @field:SerializedName("VanStoctaking")
    val vanStoctaking: ArrayList<VanStoctaking>? = null,

    @field:SerializedName("StoresTBl")
    val storesTBl: ArrayList<StoresTBl>? = null,

    @field:SerializedName("INvnetoryMasterData")
    val iNvnetoryMasterData: ArrayList<INvnetoryMasterDatum>? = null,


    @field:SerializedName("INvnetoryTrxDetails")
    val iNvnetoryTrxDetails: ArrayList<INvnetoryTrxDetail>? = null,

    @field:SerializedName("INvnetoryAllrequest")
    val iNvnetoryAllrequest: ArrayList<INvnetory>? = null,
    @field:SerializedName("INvnetoryPending")
    val iNvnetoryPending: ArrayList<INvnetory>? = null,
    @field:SerializedName("INvnetoryApproved")
    val iNvnetoryApproved: ArrayList<INvnetory?>? = null


)

data class VanStoctaking(
    @field:SerializedName("StoreId")
    val storeID: Int? = null,
    @field:SerializedName("_ItemId")
    val itemID: Int? = null,
    @field:SerializedName("ItemPrincipalUnitId")
    val itemPrincipalUnitID: Long? = null,
    @field:SerializedName("ItemArName")
    val itemArName: String? = null,
    @field:SerializedName("UnitArName")
    val unitArName: String? = null,
    @field:SerializedName("Qty")
    var qty: Int? = null,
    @field:SerializedName("Reserved")
    val reserved: Long? = null,
    @field:SerializedName("Pending")
    val pending: Long? = null,
    @field:SerializedName("BatchNumber")
    val batchNumber: String? = null,
    @field:SerializedName("PatchExpireDate")
    val patchExpireDate: String? = null,
    var qtyy: Long = 0
)


data class StoresTBl(
    @field:SerializedName("StorId")
    val storID: Int? = null,
    @field:SerializedName("StoreSerial")
    val storeSerial: String? = null,
    @field:SerializedName("StorName")
    val storName: String? = null,
    @field:SerializedName("StorAddress")
    val storAddress: String? = null,
    @field:SerializedName("StorTel")
    val storTel: Any? = null,
    @field:SerializedName("StoreAccountId")
    val storeAccountID: Long? = null,
    @field:SerializedName("StoreNotes")
    val storeNotes: Any? = null,
    @field:SerializedName("Country")
    val country: Any? = null,
    @field:SerializedName("StoreKeeperEmpId")
    val storeKeeperEmpID: Any? = null,
    @field:SerializedName("StoreLatValue")
    val storeLatValue: Any? = null,
    @field:SerializedName("StoreLangValue")
    val storeLangValue: Any? = null,
    @field:SerializedName("AddUserId")
    val addUserID: Any? = null,
    @field:SerializedName("AddMAc")
    val addMAC: Any? = null,
    @field:SerializedName("AddDateTime")
    val addDateTime: Any? = null,
    @field:SerializedName("ModifyUserId")
    val modifyUserID: Any? = null,
    @field:SerializedName("ModifyMac")
    val modifyMAC: Any? = null,
    @field:SerializedName("ModifyDatetime")
    val modifyDatetime: Any? = null
)


@Parcelize
data class INvnetoryMasterDatum(
    @field:SerializedName("TrxId")
    val trxID: Long? = null,
    @field:SerializedName("StorName")
    val storName: String? = null,
    @field:SerializedName("TrxTypeDescription")
    val trxTypeDescription: String? = null,
    @field:SerializedName("TrxSerial")
    val trxSerial: String? = null,
    @field:SerializedName("Trxdate")
    val trxdate: String? = null,
    @field:SerializedName("AddbyEMployee")
    val addbyEMployee: String? = null,
    @field:SerializedName("StoreId")
    val storeId: Int? = null,
    @field:SerializedName("ToStoreId")
    val toStoreId: Int? = null,

    var selected: Boolean = false


) : Parcelable


data class InventoryTrxType(
    @field:SerializedName("TrxTypeId")
    val trxTypeID: Long? = null,
    @field:SerializedName("TrxTypeDescription")
    val trxTypeDescription: String? = null,
    @field:SerializedName("IsOutTrxType")
    val isOutTrxType: Boolean? = null,
    @field:SerializedName("AllowStoreId")
    val allowStoreID: Boolean? = null,
    @field:SerializedName("AllowToStore")
    val allowToStore: Boolean? = null,
    @field:SerializedName("AllowProduct")
    val allowProduct: Boolean? = null,
    @field:SerializedName("AllowPatchNumber")
    val allowPatchNumber: Boolean? = null,
    @field:SerializedName("AllowCustomer")
    val allowCustomer: Boolean? = null,
    @field:SerializedName("AllowSupplier")
    val allowSupplier: Boolean? = null,
    @field:SerializedName("AllowMultiStore")
    val allowMultiStore: Boolean? = null,
    @field:SerializedName("AllowToSetPrice")
    val allowToSetPrice: Boolean? = null,
    @field:SerializedName("DefultTrxJournalAccount")
    val defultTrxJournalAccount: Long? = null,
    @field:SerializedName("DefultStoreID")
    val defultStoreID: Any? = null,
    @field:SerializedName("DefultItmGroupsIdStr")
    val defultItmGroupsIDStr: String? = null,
    @field:SerializedName("AllowToAddCustomjournalAccount")
    val allowToAddCustomjournalAccount: Any? = null,
    @field:SerializedName("AllowToGenerateCostjournal")
    val allowToGenerateCostjournal: Any? = null,
    @field:SerializedName("CostjournalDefultAccountId")
    val costjournalDefultAccountID: Any? = null,
    @field:SerializedName("AllowDealersAccounts")
    val allowDealersAccounts: Any? = null,
    @field:SerializedName("AllowMediatorAccount")
    val allowMediatorAccount: Boolean? = null,
    @field:SerializedName("AutoGenerateJournal")
    val autoGenerateJournal: Boolean? = null,
    @field:SerializedName("AllowTax")
    val allowTax: Boolean? = null,
    @field:SerializedName("NeedQualityChek")
    val needQualityChek: Boolean? = null,
    @field:SerializedName("AddUserId")
    val addUserID: Any? = null,
    @field:SerializedName("AddMAc")
    val addMAC: Any? = null,
    @field:SerializedName("AddDateTime")
    val addDateTime: Any? = null,
    @field:SerializedName("ModifyUserId")
    val modifyUserID: Any? = null,
    @field:SerializedName("ModifyMac")
    val modifyMAC: Any? = null,
    @field:SerializedName("ModifyDatetime")
    val modifyDatetime: Any? = null,
    @field:SerializedName("IsTransferTrx")
    val isTransferTrx: Boolean? = null,
    @field:SerializedName("MasterProductGroupIdStr")
    val masterProductGroupIDStr: Any? = null,
    @field:SerializedName("IsOpeningBallance")
    val isOpeningBallance: Any? = null,
    @field:SerializedName("ISProductionIndirectTrx")
    val isProductionIndirectTrx: Any? = null,
    @field:SerializedName("AllowToCalculateProductionCost")
    val allowToCalculateProductionCost: Any? = null,
    @field:SerializedName("AllowToAddCostCenter")
    val allowToAddCostCenter: Any? = null,
    @field:SerializedName("FormId")
    val formID: Long? = null,
    @field:SerializedName("GenerateJournalAfterFirstRevision")
    val generateJournalAfterFirstRevision: Any? = null,
    @field:SerializedName("PostingWorkFlowId")
    val postingWorkFlowID: Any? = null,
    @field:SerializedName("RevisionWorkFlowid")
    val revisionWorkFlowid: Any? = null,
    @field:SerializedName("EnforceToAddCostCenter")
    val enforceToAddCostCenter: Any? = null,
    @field:SerializedName("DataLinkRefrence")
    val dataLinkRefrence: Any? = null,
    @field:SerializedName("IsTradeTrx")
    val isTradeTrx: Boolean? = null,
    @field:SerializedName("AffectItemBalance")
    val affectItemBalance: Boolean? = null
)


data class INvnetoryTrxDetail(
    @field:SerializedName("StorName")
    val storName: String? = null,
    @field:SerializedName("StoreStorageLocationId")
    val storeStorageLocationID: Any? = null,
    @field:SerializedName("ItemSerial")
    val itemSerial: String? = null,
    @field:SerializedName("ItemArName")
    val itemArName: String? = null,
    @field:SerializedName("UnitArName")
    val unitArName: String? = null,
    @field:SerializedName("PatchNumber")
    val patchNumber: Any? = null,
    @field:SerializedName("PatchProductionDate")
    val patchProductionDate: Any? = null,
    @field:SerializedName("PatchExpireDate")
    val patchExpireDate: Any? = null,
    @field:SerializedName("AvaliableBallance")
    val avaliableBallance: Long? = null,
    @field:SerializedName("BookedupBallance")
    val bookedupBallance: Long? = null,
    @field:SerializedName("AllQty")
    val allQty: Any? = null,
    @field:SerializedName("Qty")
    var qty: Long? = null,
    @field:SerializedName("RefuesQty")
    val refuesQty: Any? = null,
    @field:SerializedName("RefuesQtyPercent")
    val refuesQtyPercent: Any? = null,
    @field:SerializedName("BounsQty")
    val bounsQty: Any? = null,
    @field:SerializedName("Price")
    val price: Long? = null,
    @field:SerializedName("TotalValue")
    val totalValue: Long? = null,
    @field:SerializedName("Notes")
    val notes: String? = null,
    @field:SerializedName("CopyFromModuleId")
    val copyFromModuleID: Any? = null,
    @field:SerializedName("CopyFromTrxTypeId")
    val copyFromTrxTypeID: Any? = null,
    @field:SerializedName("CopyFromTrxId")
    val copyFromTrxID: Any? = null,
    @field:SerializedName("CopyFromLineId")
    val copyFromLineID: Any? = null,
    @field:SerializedName("StoreID")
    val storeID: Int? = null,
    @field:SerializedName("ItemId")
    val itemID: Long? = null,
    @field:SerializedName("UnitId")
    val unitID: Long? = null,
    @field:SerializedName("CopyFromQty")
    val copyFromQty: Any? = null,
    @field:SerializedName("Column1")
    val column1: Any? = null,
    @field:SerializedName("OriginalCopyfromId")
    val originalCopyfromID: Any? = null,
    @field:SerializedName("IsBouns")
    val isBouns: Any? = null,
    @field:SerializedName("TOFromStoreId")
    val toFromStoreID: String? = null

//        var count: Long? = qty
)

@Parcelize
data class INvnetory(
    @field:SerializedName("TrxId")
    val trxID: Long? = null,
    @field:SerializedName("StorName")
    val storName: String? = null,
    @field:SerializedName("TrxTypeDescription")
    val trxTypeDescription: String? = null,
    @field:SerializedName("TrxSerial")
    val trxSerial: String? = null,
    @field:SerializedName("Trxdate")
    val trxdate: String? = null,
    @field:SerializedName("AddbyEMployee")
    val addbyEMployee: String? = null,
    @field:SerializedName("Approved")
    val approved: Boolean? = null
) : Parcelable

