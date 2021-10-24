package com.devartlab.data.room.trademaster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devartlab.data.room.tradedetails.TradeDetailsEntity

@Entity
data class TradeMasterEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "InvoiceId")
    var InvoiceId  : Int? = null,
    @ColumnInfo(name = "InvoiceSerial")
    var InvoiceSerial: String? = null,
    @ColumnInfo(name = "InvoiceTypeId")
    var InvoiceTypeId  : Int? = null,
    @ColumnInfo(name = "InvoiceNatural")
    var InvoiceNatural  : Int? = null,
    @ColumnInfo(name = "InvoiceCreateDate")
    var InvoiceCreateDate: String? = null,
    @ColumnInfo(name = "InvoiceDueDate")
    var InvoiceDueDate: String? = null,
    @ColumnInfo(name = "InvoiceDueDays")
    var InvoiceDueDays  : Int? = null,
    @ColumnInfo(name = "InvoiceDescription")
    var InvoiceDescription: String? = null,
    @ColumnInfo(name = "CustomerId")
    var CustomerId  : Int? = null,
    @ColumnInfo(name = "StoreId")
    var StoreId  : Int? = null,
    @ColumnInfo(name = "SuppId")
    var SuppId  : Int? = null,
    @ColumnInfo(name = "SuppInvoiceSerial")
    var SuppInvoiceSerial: String? = null,
    @ColumnInfo(name = "AddUserId")
    var AddUserId  : Int? = null,
    @ColumnInfo(name = "AddEmpId")
    var AddEmpId  : Int? = null,
    @ColumnInfo(name = "AddMAc")
    var AddMAc: String? = null,
    @ColumnInfo(name = "AddDateTime")
    var AddDateTime: String? = null,
    @ColumnInfo(name = "ModifyUserId")
    var ModifyUserId  : Int? = null,
    @ColumnInfo(name = "ModifyEmpId")
    var ModifyEmpId  : Int? = null,
    @ColumnInfo(name = "ModifyMac")
    var ModifyMac: String? = null,
    @ColumnInfo(name = "ModifyDatetime")
    var ModifyDatetime: String? = null,
    @ColumnInfo(name = "TotalInvoiceBeforDiscount")
    var TotalInvoiceBeforDiscount: Double? = null,
    @ColumnInfo(name = "TotalInvoiceDiscount")
    var TotalInvoiceDiscount: Double? = null,
    @ColumnInfo(name = "TotalInvoiceWithoutTax")
    var TotalInvoiceWithoutTax: Double? = null,
    @ColumnInfo(name = "TotalInvoiceTax")
    var TotalInvoiceTax: Double? = null,
    @ColumnInfo(name = "TotalInvoiceWithTax")
    var TotalInvoiceWithTax: Double? = null,
    @ColumnInfo(name = "TotalAddDiscAccounts")
    var TotalAddDiscAccounts: Double? = null,
    @ColumnInfo(name = "TotalInvoiceNet")
    var TotalInvoiceNet: Double? = null,
    @ColumnInfo(name = "JournalId")
    var JournalId  : Int? = null,
    @ColumnInfo(name = "StoreTrxTypeId")
    var StoreTrxTypeId  : Int? = null,
    @ColumnInfo(name = "InventoryTrxId")
    var InventoryTrxId  : Int? = null,
    @ColumnInfo(name = "VOucherId")
    var VOucherId  : Int? = null,
    @ColumnInfo(name = "ISPayed")
    var ISPayed: Boolean? = null,
    @ColumnInfo(name = "TotalPaid")
    var TotalPaid: Double? = null,
    @ColumnInfo(name = "TotalReminder")
    var TotalReminder: Double? = null,
    @ColumnInfo(name = "Empid")
    var Empid  : Int? = null,
    @ColumnInfo(name = "ContractId")
    var ContractId  : Int? = null,
    @ColumnInfo(name = "TrxAutoSerial")
    var TrxAutoSerial  : Int? = null,
    @ColumnInfo(name = "Approved")
    var Approved: Double? = null,
    @ColumnInfo(name = "ApprovedByUserId")
    var ApprovedByUserId  : Int? = null,
    @ColumnInfo(name = "ApprovedByEmpId")
    var ApprovedByEmpId  : Int? = null,
    @ColumnInfo(name = "ApprovedByMAc")
    var ApprovedByMAc: String? = null,
    @ColumnInfo(name = "ApprovedByDateTime")
    var ApprovedByDateTime: String? = null,
    @ColumnInfo(name = "ApprovedNotes")
    var ApprovedNotes: String? = null,
    @ColumnInfo(name = "CopyFromTrxSerialStr")
    var CopyFromTrxSerialStr: String? = null,
    @ColumnInfo(name = "AdditionalDisc1Value")
    var AdditionalDisc1Value: Double? = null,
    @ColumnInfo(name = "AdditionalDisc2Value")
    var AdditionalDisc2Value: Double? = null,
    @ColumnInfo(name = "AdditionalDisc3Value")
    var AdditionalDisc3Value: Double? = null,
    @ColumnInfo(name = "CashDiscValue")
    var CashDiscValue: Double? = null,
    @ColumnInfo(name = "TotalAllocationCost")
    var TotalAllocationCost: Double? = null,
    @ColumnInfo(name = "AllocationCostJournal")
    var AllocationCostJournal  : Int? = null,
    @ColumnInfo(name = "LandedCostSupplierIdStr")
    var LandedCostSupplierIdStr: String? = null,
    @ColumnInfo(name = "SuppIdStr")
    var SuppIdStr: String? = null,
    @ColumnInfo(name = "Closed")
    var Closed: Double? = null,
    @ColumnInfo(name = "ClosedByUserId")
    var ClosedByUserId  : Int? = null,
    @ColumnInfo(name = "ClosedByEmpId")
    var ClosedByEmpId  : Int? = null,
    @ColumnInfo(name = "ClosedByMAc")
    var ClosedByMAc: String? = null,
    @ColumnInfo(name = "ClosedByDateTime")
    var ClosedByDateTime: String? = null,
    @ColumnInfo(name = "ClosedNotes")
    var ClosedNotes: String? = null,
    @ColumnInfo(name = "IsAccumulative")
    var IsAccumulative: Double? = null,
    @ColumnInfo(name = "ExpireDate")
    var ExpireDate: String? = null,
    @ColumnInfo(name = "DLInvTrxSalesPurchaseDetails")
    var DLInvTrxSalesPurchaseDetails: String? = null,
    @ColumnInfo(name = "DLInvTrxSalesPurchaseAddDiscDetails")
    var DLInvTrxSalesPurchaseAddDiscDetails: String? = null,
    @ColumnInfo(name = "DLInvTrxSalesPurchaseServiceFeesDetails")
    var DLInvTrxSalesPurchaseServiceFeesDetails: String? = null,
    @ColumnInfo(name = "DLInvTrxSalesPurchaseCostCenter")
    var DLInvTrxSalesPurchaseCostCenter: String? = null,
    @ColumnInfo(name = "IsDraft")
    var IsDraft: Boolean = false

 

 

){

    constructor(
        InvoiceId: Int?,
        InvoiceSerial: String?,
        InvoiceTypeId: Int?,
        InvoiceNatural: Int?,
        InvoiceCreateDate: String?,
        InvoiceDueDate: String?,
        InvoiceDueDays: Int?,
        InvoiceDescription: String?,
        CustomerId: Int?,
        StoreId: Int?,
        SuppId: Int?,
        SuppInvoiceSerial: String?,
        AddUserId: Int?,
        AddEmpId: Int?,
        AddMAc: String?,
        AddDateTime: String?,
        ModifyUserId: Int?,
        ModifyEmpId: Int?,
        ModifyMac: String?,
        ModifyDatetime: String?,
        TotalInvoiceBeforDiscount: Double?,
        TotalInvoiceDiscount: Double?,
        TotalInvoiceWithoutTax: Double?,
        TotalInvoiceTax: Double?,
        TotalInvoiceWithTax: Double?,
        TotalAddDiscAccounts: Double?,
        TotalInvoiceNet: Double?,
        JournalId: Int?,
        StoreTrxTypeId: Int?,
        InventoryTrxId: Int?,
        VOucherId: Int?,
        ISPayed: Boolean?,
        TotalPaid: Double?,
        TotalReminder: Double?,
        Empid: Int?,
        ContractId: Int?,
        TrxAutoSerial: Int?,
        Approved: Double?,
        ApprovedByUserId: Int?,
        ApprovedByEmpId: Int?,
        ApprovedByMAc: String?,
        ApprovedByDateTime: String?,
        ApprovedNotes: String?,
        CopyFromTrxSerialStr: String?,
        AdditionalDisc1Value: Double?,
        AdditionalDisc2Value: Double?,
        AdditionalDisc3Value: Double?,
        CashDiscValue: Double?,
        TotalAllocationCost: Double?,
        AllocationCostJournal: Int?,
        LandedCostSupplierIdStr: String?,
        SuppIdStr: String?,
        Closed: Double?,
        ClosedByUserId: Int?,
        ClosedByEmpId: Int?,
        ClosedByMAc: String?,
        ClosedByDateTime: String?,
        ClosedNotes: String?,
        IsAccumulative: Double?,
        ExpireDate: String?,
        DLInvTrxSalesPurchaseDetails: String?,
        DLInvTrxSalesPurchaseAddDiscDetails: String?,
        DLInvTrxSalesPurchaseServiceFeesDetails: String?,
        DLInvTrxSalesPurchaseCostCenter: String?,
        IsDraft: Boolean
    ) :this() {
        this.InvoiceId = InvoiceId
        this.InvoiceSerial = InvoiceSerial
        this.InvoiceTypeId = InvoiceTypeId
        this.InvoiceNatural = InvoiceNatural
        this.InvoiceCreateDate = InvoiceCreateDate
        this.InvoiceDueDate = InvoiceDueDate
        this.InvoiceDueDays = InvoiceDueDays
        this.InvoiceDescription = InvoiceDescription
        this.CustomerId = CustomerId
        this.StoreId = StoreId
        this.SuppId = SuppId
        this.SuppInvoiceSerial = SuppInvoiceSerial
        this.AddUserId = AddUserId
        this.AddEmpId = AddEmpId
        this.AddMAc = AddMAc
        this.AddDateTime = AddDateTime
        this.ModifyUserId = ModifyUserId
        this.ModifyEmpId = ModifyEmpId
        this.ModifyMac = ModifyMac
        this.ModifyDatetime = ModifyDatetime
        this.TotalInvoiceBeforDiscount = TotalInvoiceBeforDiscount
        this.TotalInvoiceDiscount = TotalInvoiceDiscount
        this.TotalInvoiceWithoutTax = TotalInvoiceWithoutTax
        this.TotalInvoiceTax = TotalInvoiceTax
        this.TotalInvoiceWithTax = TotalInvoiceWithTax
        this.TotalAddDiscAccounts = TotalAddDiscAccounts
        this.TotalInvoiceNet = TotalInvoiceNet
        this.JournalId = JournalId
        this.StoreTrxTypeId = StoreTrxTypeId
        this.InventoryTrxId = InventoryTrxId
        this.VOucherId = VOucherId
        this.ISPayed = ISPayed
        this.TotalPaid = TotalPaid
        this.TotalReminder = TotalReminder
        this.Empid = Empid
        this.ContractId = ContractId
        this.TrxAutoSerial = TrxAutoSerial
        this.Approved = Approved
        this.ApprovedByUserId = ApprovedByUserId
        this.ApprovedByEmpId = ApprovedByEmpId
        this.ApprovedByMAc = ApprovedByMAc
        this.ApprovedByDateTime = ApprovedByDateTime
        this.ApprovedNotes = ApprovedNotes
        this.CopyFromTrxSerialStr = CopyFromTrxSerialStr
        this.AdditionalDisc1Value = AdditionalDisc1Value
        this.AdditionalDisc2Value = AdditionalDisc2Value
        this.AdditionalDisc3Value = AdditionalDisc3Value
        this.CashDiscValue = CashDiscValue
        this.TotalAllocationCost = TotalAllocationCost
        this.AllocationCostJournal = AllocationCostJournal
        this.LandedCostSupplierIdStr = LandedCostSupplierIdStr
        this.SuppIdStr = SuppIdStr
        this.Closed = Closed
        this.ClosedByUserId = ClosedByUserId
        this.ClosedByEmpId = ClosedByEmpId
        this.ClosedByMAc = ClosedByMAc
        this.ClosedByDateTime = ClosedByDateTime
        this.ClosedNotes = ClosedNotes
        this.IsAccumulative = IsAccumulative
        this.ExpireDate = ExpireDate
        this.DLInvTrxSalesPurchaseDetails = DLInvTrxSalesPurchaseDetails
        this.DLInvTrxSalesPurchaseAddDiscDetails = DLInvTrxSalesPurchaseAddDiscDetails
        this.DLInvTrxSalesPurchaseServiceFeesDetails = DLInvTrxSalesPurchaseServiceFeesDetails
        this.DLInvTrxSalesPurchaseCostCenter = DLInvTrxSalesPurchaseCostCenter
        this.IsDraft = IsDraft
    }
}