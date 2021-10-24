package com.devartlab.data.room.invoice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class InvoiceEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "InvoiceDetId")
    var InvoiceDetId: Int? = 0

    @ColumnInfo(name = "InvoiceId")
    var InvoiceId: Int? = 0
     
    @ColumnInfo(name = "RowIndex")
    var RowIndex: Int? = 0
     
    @ColumnInfo(name = "ItemId")
    var ItemId: Int? = 0
     
    @ColumnInfo(name = "UnitId")
    var UnitId: Int? = 0
     
    @ColumnInfo(name = "Qty")
    var Qty: Double? = null
     
    @ColumnInfo(name = "BonusQty")
    var BonusQty: Double? = null
     
    @ColumnInfo(name = "Price")
    var Price: Double? = null
     
    @ColumnInfo(name = "PublicPrice")
    var PublicPrice: Double? = null
     
    @ColumnInfo(name = "TotalValue")
    var TotalValue: Double? = null
     
    @ColumnInfo(name = "PharmacyDisc")
    var PharmacyDisc: Double? = null
     
    @ColumnInfo(name = "CashDisc")
    var CashDisc: Double? = null
     
    @ColumnInfo(name = "CashDiscValue")
    var CashDiscValue: Double? = null
     
    @ColumnInfo(name = "DistributionDisc")
    var DistributionDisc: Double? = null
     
    @ColumnInfo(name = "DistributionDiscValue")
    var DistributionDiscValue: Double? = null
     
    @ColumnInfo(name = "Disc1")
    var Disc1: Double? = null
     
    @ColumnInfo(name = "Disc1Value")
    var Disc1Value: Double? = null
     
    @ColumnInfo(name = "Disc2")
    var Disc2: Double? = null
     
    @ColumnInfo(name = "Disc2Value")
    var Disc2Value: Double? = null
     
    @ColumnInfo(name = "TotalAfterDisc")
    var TotalAfterDisc: Double? = null
     
    @ColumnInfo(name = "TaxPercent")
    var TaxPercent: Double? = null
     
    @ColumnInfo(name = "BounsTax")
    var BounsTax: Double? = null
     
    @ColumnInfo(name = "TaxValue")
    var TaxValue: Double? = null
     
    @ColumnInfo(name = "TotalInvoice")
    var TotalInvoice: Double? = null
     
    @ColumnInfo(name = "PatchNumber")
    var PatchNumber: String? = null
     
    @ColumnInfo(name = "ExpireDate")
    var ExpireDate: String? = null
     
    @ColumnInfo(name = "Notes")
    var Notes: String? = null
     
    @ColumnInfo(name = "CopyFromModuleId")
    var CopyFromModuleId: Int? = 0
     
    @ColumnInfo(name = "CopyFromTrxTypeId")
    var CopyFromTrxTypeId: Int? = 0
     
    @ColumnInfo(name = "CopyFromTrxId")
    var CopyFromTrxId: Int? = 0
     
    @ColumnInfo(name = "CopyFromLineId")
    var CopyFromLineId: Int? = 0
     
    @ColumnInfo(name = "CopyFromQty")
    var CopyFromQty: Double? = null
     
    @ColumnInfo(name = "StoreId")
    var StoreId: Int? = 0
     
    @ColumnInfo(name = "IsBonus")
    var IsBonus: Double? = null
     
    @ColumnInfo(name = "PharmacyDiscValue")
    var PharmacyDiscValue: Double? = null
     
    @ColumnInfo(name = "Disc3")
    var Disc3: Double? = null
     
    @ColumnInfo(name = "DiscValue")
    var DiscValue: Double? = null
     
    @ColumnInfo(name = "CustomValue")
    var CustomValue: Double? = null
     
    @ColumnInfo(name = "expenditureCost")
    var expenditureCost: Double? = null
     
    @ColumnInfo(name = "TotalItemAllocationCost")
    var TotalItemAllocationCost: Double? = null
     
    @ColumnInfo(name = "whseUnitprice")
    var whseUnitprice: Double? = null
     
    @ColumnInfo(name = "TotalAfterexpenditure")
    var TotalAfterexpenditure: Double? = null
     
    @ColumnInfo(name = "ProductionDate")
    var ProductionDate: String? = null




    constructor() {}

}