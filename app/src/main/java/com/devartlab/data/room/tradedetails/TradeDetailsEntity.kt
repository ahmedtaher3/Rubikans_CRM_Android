package com.devartlab.data.room.tradedetails

import androidx.room.*

@Entity
data class TradeDetailsEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "InvoiceDetId")
    var InvoiceDetId: Int? = null,
    @ColumnInfo(name = "InvoiceId")
    var InvoiceId: Int? = null,
    @ColumnInfo(name = "RowIndex")
    var RowIndex: Int? = null,
    @ColumnInfo(name = "ItemId")
    var ItemId: Int? = null,
    @ColumnInfo(name = "UnitId")
    var UnitId: Int? = null,
    @ColumnInfo(name = "Qty")
    var Qty: Double? = null,
    @ColumnInfo(name = "BonusQty")
    var BonusQty: Double? = null,
    @ColumnInfo(name = "Price")
    var Price: Double? = null,
    @ColumnInfo(name = "PublicPrice")
    var PublicPrice: Double? = null,
    @ColumnInfo(name = "TotalValue")
    var TotalValue: Double? = null,
    @ColumnInfo(name = "PharmacyDisc")
    var PharmacyDisc: String? = null,
    @ColumnInfo(name = "CashDisc")
    var CashDisc: String? = null,
    @ColumnInfo(name = "CashDiscValue")
    var CashDiscValue: Double? = null,
    @ColumnInfo(name = "DistributionDisc")
    var DistributionDisc: String? = null,
    @ColumnInfo(name = "DistributionDiscValue")
    var DistributionDiscValue: Double? = null,
    @ColumnInfo(name = "Disc1")
    var Disc1: String? = null,
    @ColumnInfo(name = "Disc1Value")
    var Disc1Value: Double? = null,
    @ColumnInfo(name = "Disc2")
    var Disc2: String? = null,
    @ColumnInfo(name = "Disc2Value")
    var Disc2Value: Double? = null,
    @ColumnInfo(name = "TotalAfterDisc")
    var TotalAfterDisc: Double? = null,
    @ColumnInfo(name = "TaxPercent")
    var TaxPercent: Double? = null,
    @ColumnInfo(name = "BounsTax")
    var BounsTax: Double? = null,
    @ColumnInfo(name = "TaxValue")
    var TaxValue: Double? = null,
    @ColumnInfo(name = "TotalInvoice")
    var TotalInvoice: Double? = null,
    @ColumnInfo(name = "PatchNumber")
    var PatchNumber: String? = null,
    @ColumnInfo(name = "ExpireDate")
    var ExpireDate: String? = null,
    @ColumnInfo(name = "Notes")
    var Notes: String? = null,
    @ColumnInfo(name = "CopyFromModuleId")
    var CopyFromModuleId: Int? = null,
    @ColumnInfo(name = "CopyFromTrxTypeId")
    var CopyFromTrxTypeId: Int? = null,
    @ColumnInfo(name = "CopyFromTrxId")
    var CopyFromTrxId: Int? = null,
    @ColumnInfo(name = "CopyFromLineId")
    var CopyFromLineId: Int? = null,
    @ColumnInfo(name = "CopyFromQty")
    var CopyFromQty: Double? = null,
    @ColumnInfo(name = "StoreId")
    var StoreId: Int? = null,
    @ColumnInfo(name = "IsBonus")
    var IsBonus: Boolean? = null,
    @ColumnInfo(name = "PharmacyDiscValue")
    var PharmacyDiscValue: String? = null,
    @ColumnInfo(name = "Disc3")
    var Disc3: String? = null,
    @ColumnInfo(name = "DiscValue")
    var DiscValue: Double? = null,
    @ColumnInfo(name = "CustomValue")
    var CustomValue: Double? = null,
    @ColumnInfo(name = "expenditureCost")
    var expenditureCost: Double? = null,
    @ColumnInfo(name = "TotalItemAllocationCost")
    var TotalItemAllocationCost: Double? = null,
    @ColumnInfo(name = "whseUnitprice")
    var whseUnitprice: Double? = null,
    @ColumnInfo(name = "TotalAfterexpenditure")
    var TotalAfterexpenditure: Double? = null,
    @ColumnInfo(name = "ProductionDate")
    var ProductionDate: String? = null,
    @ColumnInfo(name = "ReceivingDestinationTypeId")
    var ReceivingDestinationTypeId: Int? = null,
    @ColumnInfo(name = "AdditionQty")
    var AdditionQty: Double? = null,
    @ColumnInfo(name = "AdditionPrice")
    var AdditionPrice: Double? = null,
    @ColumnInfo(name = "AdditionTotal")
    var AdditionTotal: Double? = null,
    @ColumnInfo(name = "AdditionQtyApproved")
    var AdditionQtyApproved: String? = null,
    @ColumnInfo(name = "AdditionQtyApprovedByUserId")
    var AdditionQtyApprovedByUserId: Int? = null,
    @ColumnInfo(name = "AdditionQtyApprovedByEmpId")
    var AdditionQtyApprovedByEmpId: Int? = null,
    @ColumnInfo(name = "AdditionQtyApprovedByDateTime")
    var AdditionQtyApprovedByDateTime: String? = null,
    @ColumnInfo(name = "AdditionQtyApprovedByMac")
    var AdditionQtyApprovedByMac: String? = null,
    @ColumnInfo(name = "AdditionQtyApprovedNotes")
    var AdditionQtyApprovedNotes: String? = null,
    @ColumnInfo(name = "PaymentTermId")
    var PaymentTermId: Int? = null,
    @ColumnInfo(name = "RecivingDate")
    var RecivingDate: String? = null


){

    constructor(
        InvoiceDetId: Int?,
        InvoiceId: Int?,
        RowIndex: Int?,
        ItemId: Int?,
        UnitId: Int?,
        Qty: Double?,
        BonusQty: Double?,
        Price: Double?,
        PublicPrice: Double?,
        TotalValue: Double?,
        PharmacyDisc: String?,
        CashDisc: String?,
        CashDiscValue: Double?,
        DistributionDisc: String?,
        DistributionDiscValue: Double?,
        Disc1: String?,
        Disc1Value: Double?,
        Disc2: String?,
        Disc2Value: Double?,
        TotalAfterDisc: Double?,
        TaxPercent: Double?,
        BounsTax: Double?,
        TaxValue: Double?,
        TotalInvoice: Double?,
        PatchNumber: String?,
        ExpireDate: String?,
        Notes: String?,
        CopyFromModuleId: Int?,
        CopyFromTrxTypeId: Int?,
        CopyFromTrxId: Int?,
        CopyFromLineId: Int?,
        CopyFromQty: Double?,
        StoreId: Int?,
        IsBonus: Boolean?,
        PharmacyDiscValue: String?,
        Disc3: String?,
        DiscValue: Double?,
        CustomValue: Double?,
        expenditureCost: Double?,
        TotalItemAllocationCost: Double?,
        whseUnitprice: Double?,
        TotalAfterexpenditure: Double?,
        ProductionDate: String?,
        ReceivingDestinationTypeId: Int?,
        AdditionQty: Double?,
        AdditionPrice: Double?,
        AdditionTotal: Double?,
        AdditionQtyApproved: String?,
        AdditionQtyApprovedByUserId: Int?,
        AdditionQtyApprovedByEmpId: Int?,
        AdditionQtyApprovedByDateTime: String?,
        AdditionQtyApprovedByMac: String?,
        AdditionQtyApprovedNotes: String?,
        PaymentTermId: Int?,
        RecivingDate: String?
    ):this (){
        this.InvoiceDetId = InvoiceDetId
        this.InvoiceId = InvoiceId
        this.RowIndex = RowIndex
        this.ItemId = ItemId
        this.UnitId = UnitId
        this.Qty = Qty
        this.BonusQty = BonusQty
        this.Price = Price
        this.PublicPrice = PublicPrice
        this.TotalValue = TotalValue
        this.PharmacyDisc = PharmacyDisc
        this.CashDisc = CashDisc
        this.CashDiscValue = CashDiscValue
        this.DistributionDisc = DistributionDisc
        this.DistributionDiscValue = DistributionDiscValue
        this.Disc1 = Disc1
        this.Disc1Value = Disc1Value
        this.Disc2 = Disc2
        this.Disc2Value = Disc2Value
        this.TotalAfterDisc = TotalAfterDisc
        this.TaxPercent = TaxPercent
        this.BounsTax = BounsTax
        this.TaxValue = TaxValue
        this.TotalInvoice = TotalInvoice
        this.PatchNumber = PatchNumber
        this.ExpireDate = ExpireDate
        this.Notes = Notes
        this.CopyFromModuleId = CopyFromModuleId
        this.CopyFromTrxTypeId = CopyFromTrxTypeId
        this.CopyFromTrxId = CopyFromTrxId
        this.CopyFromLineId = CopyFromLineId
        this.CopyFromQty = CopyFromQty
        this.StoreId = StoreId
        this.IsBonus = IsBonus
        this.PharmacyDiscValue = PharmacyDiscValue
        this.Disc3 = Disc3
        this.DiscValue = DiscValue
        this.CustomValue = CustomValue
        this.expenditureCost = expenditureCost
        this.TotalItemAllocationCost = TotalItemAllocationCost
        this.whseUnitprice = whseUnitprice
        this.TotalAfterexpenditure = TotalAfterexpenditure
        this.ProductionDate = ProductionDate
        this.ReceivingDestinationTypeId = ReceivingDestinationTypeId
        this.AdditionQty = AdditionQty
        this.AdditionPrice = AdditionPrice
        this.AdditionTotal = AdditionTotal
        this.AdditionQtyApproved = AdditionQtyApproved
        this.AdditionQtyApprovedByUserId = AdditionQtyApprovedByUserId
        this.AdditionQtyApprovedByEmpId = AdditionQtyApprovedByEmpId
        this.AdditionQtyApprovedByDateTime = AdditionQtyApprovedByDateTime
        this.AdditionQtyApprovedByMac = AdditionQtyApprovedByMac
        this.AdditionQtyApprovedNotes = AdditionQtyApprovedNotes
        this.PaymentTermId = PaymentTermId
        this.RecivingDate = RecivingDate
    }
}