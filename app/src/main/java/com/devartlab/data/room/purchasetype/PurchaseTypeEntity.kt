package com.devartlab.data.room.purchasetype

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class PurchaseTypeEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    
    @ColumnInfo(name = "paymentMethodId")
    @field:SerializedName("PaymentMethodId")
    var paymentMethodId: Int? = null,
    
    @ColumnInfo(name = "isMultiVendorLandedcostDocument")
    @field:SerializedName("IsMultiVendorLandedcostDocument")
    var isMultiVendorLandedcostDocument: Boolean? = null,

    @ColumnInfo(name = "isSales")
    @field:SerializedName("IsSales")
    var isSales: Int? = null,

    @ColumnInfo(name = "workFlowId")
    @field:SerializedName("WorkFlowId")
    var workFlowId: Int? = null,

    @ColumnInfo(name = "invoiceTypeId")
    @field:SerializedName("InvoiceTypeId")
    var invoiceTypeId: Int? = null,

    @ColumnInfo(name = "generateInventoryTransaction")
    @field:SerializedName("GenerateInventoryTransaction")
    var generateInventoryTransaction: Boolean? = null,

    @ColumnInfo(name = "defultStoreID")
    @field:SerializedName("DefultStoreID")
    var defultStoreID: Int? = null,

    @ColumnInfo(name = "allowLandedCost")
    @field:SerializedName("AllowLandedCost")
    var allowLandedCost: Boolean? = null,

    @ColumnInfo(name = "inventoryTrxTypeId")
    @field:SerializedName("InventoryTrxTypeId")
    var inventoryTrxTypeId: Int? = null,

    @ColumnInfo(name = "addDateTime")
    @field:SerializedName("AddDateTime")
    var addDateTime: String? = null,

    @ColumnInfo(name = "showStore")
    @field:SerializedName("ShowStore")
    var showStore: Boolean? = null,

    @ColumnInfo(name = "invoiceTypeDescription")
    @field:SerializedName("InvoiceTypeDescription")
    var invoiceTypeDescription: String? = null,

    @ColumnInfo(name = "searchItemGroupsIdStr")
    @field:SerializedName("SearchItemGroupsIdStr")
    var searchItemGroupsIdStr: String? = null,

    @ColumnInfo(name = "allowAddDiscAccounts")
    @field:SerializedName("AllowAddDiscAccounts")
    var allowAddDiscAccounts: Boolean? = null,

    @ColumnInfo(name = "formId")
    @field:SerializedName("FormId")
    var formId: Int? = null,

    @ColumnInfo(name = "isQuotaion")
    @field:SerializedName("IsQuotaion")
    var isQuotaion: Boolean? = null,

    @ColumnInfo(name = "modifyDatetime")
    @field:SerializedName("ModifyDatetime")
    var modifyDatetime: String? = null,

    @ColumnInfo(name = "allowToSetPrice")
    @field:SerializedName("AllowToSetPrice")
    var allowToSetPrice: Boolean? = null,

    @ColumnInfo(name = "transactionAccountId")
    @field:SerializedName("TransactionAccountId")
    var transactionAccountId: Int? = null,

    @ColumnInfo(name = "isFinanceTransaction")
    @field:SerializedName("IsFinanceTransaction")
    var isFinanceTransaction: Boolean? = null,

    @ColumnInfo(name = "modifyEmpId")
    @field:SerializedName("ModifyEmpId")
    var modifyEmpId: Int? = null,

    @ColumnInfo(name = "postingWorkFlowId")
    @field:SerializedName("PostingWorkFlowId")
    var postingWorkFlowId: Int? = null,

    @ColumnInfo(name = "allowVat")
    @field:SerializedName("AllowVat")
    var allowVat: Boolean? = null,

    @ColumnInfo(name = "allowInterMediateAccount")
    @field:SerializedName("AllowInterMediateAccount")
    var allowInterMediateAccount: Int? = null,

    @ColumnInfo(name = "affectItemBallance")
    @field:SerializedName("AffectItemBallance")
    var affectItemBallance: Boolean? = null,

    @ColumnInfo(name = "allowItemPatchNumber")
    @field:SerializedName("AllowItemPatchNumber")
    var allowItemPatchNumber: Boolean? = null,

    @ColumnInfo(name = "addUserId")
    @field:SerializedName("AddUserId")
    var addUserId: Int? = null,

    @ColumnInfo(name = "modifyUserId")
    @field:SerializedName("ModifyUserId")
    var modifyUserId: Int? = null,

    @ColumnInfo(name = "addMAc")
    @field:SerializedName("AddMAc")
    var addMAc: String? = null,

    @ColumnInfo(name = "revisionWorkFlowid")
    @field:SerializedName("RevisionWorkFlowid")
    var revisionWorkFlowid: Int? = null,

    @ColumnInfo(name = "generateCostJournal")
    @field:SerializedName("GenerateCostJournal")
    var generateCostJournal: Boolean? = null,

    @ColumnInfo(name = "allowMultiStore")
    @field:SerializedName("AllowMultiStore")
    var allowMultiStore: Boolean? = null,

    @ColumnInfo(name = "interMediateAccountId")
    @field:SerializedName("InterMediateAccountId")
    var interMediateAccountId: Int? = null,

    @ColumnInfo(name = "generateJournalAfterFirstRevision")
    @field:SerializedName("GenerateJournalAfterFirstRevision")
    var generateJournalAfterFirstRevision: Boolean? = null,

    @ColumnInfo(name = "isSystemType")
    @field:SerializedName("IsSystemType")
    var isSystemType: Boolean? = null,

    @ColumnInfo(name = "invoiceTypeSerial")
    @field:SerializedName("InvoiceTypeSerial")
    var invoiceTypeSerial: Int? = null,

    @ColumnInfo(name = "modifyMac")
    @field:SerializedName("ModifyMac")
    var modifyMac: String? = null,

    @ColumnInfo(name = "generateJournal")
    @field:SerializedName("GenerateJournal")
    var generateJournal: Boolean? = null,

    @ColumnInfo(name = "addEmpId")
    @field:SerializedName("AddEmpId")
    var addEmpId: Int? = null
){
    constructor(
        paymentMethodId: Int?,
        isMultiVendorLandedcostDocument: Boolean?,
        isSales: Int?,
        workFlowId: Int?,
        invoiceTypeId: Int?,
        generateInventoryTransaction: Boolean?,
        defultStoreID: Int?,
        allowLandedCost: Boolean?,
        inventoryTrxTypeId: Int?,
        addDateTime: String?,
        showStore: Boolean?,
        invoiceTypeDescription: String?,
        searchItemGroupsIdStr: String?,
        allowAddDiscAccounts: Boolean?,
        formId: Int?,
        isQuotaion: Boolean?,
        modifyDatetime: String?,
        allowToSetPrice: Boolean?,
        transactionAccountId: Int?,
        isFinanceTransaction: Boolean?,
        modifyEmpId: Int?,
        postingWorkFlowId: Int?,
        allowVat: Boolean?,
        allowInterMediateAccount: Int?,
        affectItemBallance: Boolean?,
        allowItemPatchNumber: Boolean?,
        addUserId: Int?,
        modifyUserId: Int?,
        addMAc: String?,
        revisionWorkFlowid: Int?,
        generateCostJournal: Boolean?,
        allowMultiStore: Boolean?,
        interMediateAccountId: Int?,
        generateJournalAfterFirstRevision: Boolean?,
        isSystemType: Boolean?,
        invoiceTypeSerial: Int?,
        modifyMac: String?,
        generateJournal: Boolean?,
        addEmpId: Int?
    ) :this(){
        this.paymentMethodId = paymentMethodId
        this.isMultiVendorLandedcostDocument = isMultiVendorLandedcostDocument
        this.isSales = isSales
        this.workFlowId = workFlowId
        this.invoiceTypeId = invoiceTypeId
        this.generateInventoryTransaction = generateInventoryTransaction
        this.defultStoreID = defultStoreID
        this.allowLandedCost = allowLandedCost
        this.inventoryTrxTypeId = inventoryTrxTypeId
        this.addDateTime = addDateTime
        this.showStore = showStore
        this.invoiceTypeDescription = invoiceTypeDescription
        this.searchItemGroupsIdStr = searchItemGroupsIdStr
        this.allowAddDiscAccounts = allowAddDiscAccounts
        this.formId = formId
        this.isQuotaion = isQuotaion
        this.modifyDatetime = modifyDatetime
        this.allowToSetPrice = allowToSetPrice
        this.transactionAccountId = transactionAccountId
        this.isFinanceTransaction = isFinanceTransaction
        this.modifyEmpId = modifyEmpId
        this.postingWorkFlowId = postingWorkFlowId
        this.allowVat = allowVat
        this.allowInterMediateAccount = allowInterMediateAccount
        this.affectItemBallance = affectItemBallance
        this.allowItemPatchNumber = allowItemPatchNumber
        this.addUserId = addUserId
        this.modifyUserId = modifyUserId
        this.addMAc = addMAc
        this.revisionWorkFlowid = revisionWorkFlowid
        this.generateCostJournal = generateCostJournal
        this.allowMultiStore = allowMultiStore
        this.interMediateAccountId = interMediateAccountId
        this.generateJournalAfterFirstRevision = generateJournalAfterFirstRevision
        this.isSystemType = isSystemType
        this.invoiceTypeSerial = invoiceTypeSerial
        this.modifyMac = modifyMac
        this.generateJournal = generateJournal
        this.addEmpId = addEmpId
    }
}
