package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class EMPloyeeStoreInvoice(

	@field:SerializedName("Speciality")
	val speciality: String? = null,

	@field:SerializedName("Discount")
	val discount: Any? = null,

	@field:SerializedName("TotalInvoiceWithTax")
	val totalInvoiceWithTax: Any? = null,

	@field:SerializedName("InvoiceTypeId")
	val invoiceTypeId: Int? = null,

	@field:SerializedName("EmpArName")
	val empArName: String? = null,

	@field:SerializedName("TaxValue")
	val taxValue: Any? = null,

	@field:SerializedName("BrickEnName")
	val brickEnName: String? = null,

	@field:SerializedName("InvoiceCreateDate")
	val invoiceCreateDate: Any? = null,

	@field:SerializedName("InvoiceId")
	val invoiceId: Int? = null,

	@field:SerializedName("TotalAfterDisc")
	val totalAfterDisc: Any? = null,

	@field:SerializedName("TotalValue")
	val totalValue: Double? = null,

	@field:SerializedName("InvoiceTypeDescription")
	val invoiceTypeDescription: String? = null,

	@field:SerializedName("Class")
	val _class: String? = null,

	@field:SerializedName("CustomerName")
	val customerName: String? = null,

	@field:SerializedName("SalTerriotryArName")
	val salTerriotryArName: String? = null
)
