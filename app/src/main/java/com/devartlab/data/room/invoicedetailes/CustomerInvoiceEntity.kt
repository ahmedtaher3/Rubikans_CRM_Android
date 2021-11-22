package com.devartlab.data.room.invoicedetailes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CustomerInvoiceEntity(


	@PrimaryKey(autoGenerate = true) var id: Int? = null,

	@ColumnInfo(name = "CustomerId") @field:SerializedName("CustomerId") var customerId: Int? = null,

	@ColumnInfo(name = "speciality") @field:SerializedName("Speciality") var speciality: String? = null,

	@ColumnInfo(name = "discount") @field:SerializedName("Discount") var discount: Double? = null,

	@ColumnInfo(name = "iSPayed") @field:SerializedName("ISPayed") var iSPayed: Boolean? = null,

	@ColumnInfo(name = "totalInvoiceWithTax") @field:SerializedName("TotalInvoiceWithTax") var totalInvoiceWithTax: Double? = null,

	@ColumnInfo(name = "invoiceTypeId") @field:SerializedName("InvoiceTypeId") var invoiceTypeId: Int? = null,

	@ColumnInfo(name = "empArName") @field:SerializedName("EmpArName") var empArName: String? = null,

	@ColumnInfo(name = "taxValue") @field:SerializedName("TaxValue") var taxValue: Double? = null,

	@ColumnInfo(name = "brickEnName") @field:SerializedName("BrickEnName") var brickEnName: String? = null,

	@ColumnInfo(name = "invoiceCreateDate") @field:SerializedName("InvoiceCreateDate") var invoiceCreateDate: String? = null,

	@ColumnInfo(name = "lateDays") @field:SerializedName("LateDays") var lateDays: String? = null,

	@ColumnInfo(name = "invoiceId") @field:SerializedName("InvoiceId") var invoiceId: Int? = null,

	@ColumnInfo(name = "totalAfterDisc") @field:SerializedName("TotalAfterDisc") var totalAfterDisc: Double? = null,

	@ColumnInfo(name = "totalValue") @field:SerializedName("TotalValue") var totalValue: Double? = null,

	@ColumnInfo(name = "invoiceTypeDescription") @field:SerializedName("InvoiceTypeDescription") var invoiceTypeDescription: String? = null,

	@ColumnInfo(name = "totalPaid") @field:SerializedName("TotalPaid") var totalPaid: Double? = null,

	@ColumnInfo(name = "_class") @field:SerializedName("Class") var _class: String? = null,

	@ColumnInfo(name = "customerName") @field:SerializedName("CustomerName") var customerName: String? = null,

	@ColumnInfo(name = "salTerriotryArName") @field:SerializedName("SalTerriotryArName") var salTerriotryArName: String? = null,

	@ColumnInfo(name = "totalReminder") @field:SerializedName("TotalReminder") var totalReminder: Double? = null) {

	constructor(customerId: Int?,
				speciality: String?,
				discount: Double?,
				iSPayed: Boolean?,
				totalInvoiceWithTax: Double?,
				invoiceTypeId: Int?,
				empArName: String?,
				taxValue: Double?,
				brickEnName: String?,
				invoiceCreateDate: String?,
				lateDays: String?,
				invoiceId: Int?,
				totalAfterDisc: Double?,
				totalValue: Double?,
				invoiceTypeDescription: String?,
				totalPaid: Double?,
				_class: String?,
				customerName: String?,
				salTerriotryArName: String?,
				totalReminder: Double?) : this() {
		this.customerId = customerId
		this.speciality = speciality
		this.discount = discount
		this.iSPayed = iSPayed
		this.totalInvoiceWithTax = totalInvoiceWithTax
		this.invoiceTypeId = invoiceTypeId
		this.empArName = empArName
		this.taxValue = taxValue
		this.brickEnName = brickEnName
		this.invoiceCreateDate = invoiceCreateDate
		this.lateDays = lateDays
		this.invoiceId = invoiceId
		this.totalAfterDisc = totalAfterDisc
		this.totalValue = totalValue
		this.invoiceTypeDescription = invoiceTypeDescription
		this.totalPaid = totalPaid
		this._class = _class
		this.customerName = customerName
		this.salTerriotryArName = salTerriotryArName
		this.totalReminder = totalReminder
	}


}
