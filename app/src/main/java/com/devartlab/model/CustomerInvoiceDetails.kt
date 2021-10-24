package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class CustomerInvoiceDetails(

	@field:SerializedName("Speciality")
	var speciality: String? = null,

	@field:SerializedName("Discount")
	var discount: Double? = null,

	@field:SerializedName("ISPayed")
	var iSPayed: Boolean? = null,

	@field:SerializedName("TotalInvoiceWithTax")
	var totalInvoiceWithTax: Any? = null,

	@field:SerializedName("InvoiceTypeId")
	var invoiceTypeId: Int? = null,

	@field:SerializedName("EmpArName")
	var empArName: String? = null,

	@field:SerializedName("TaxValue")
	var taxValue: Any? = null,

	@field:SerializedName("BrickEnName")
	var brickEnName: String? = null,

	@field:SerializedName("InvoiceCreateDate")
	var invoiceCreateDate: Any? = null,

	@field:SerializedName("LateDays")
	var lateDays: Any? = null,

	@field:SerializedName("InvoiceId")
	var invoiceId: Int? = null,

	@field:SerializedName("TotalAfterDisc")
	var totalAfterDisc: Any? = null,

	@field:SerializedName("TotalValue")
	var totalValue: Double? = null,

	@field:SerializedName("InvoiceTypeDescription")
	var invoiceTypeDescription: String? = null,

	@field:SerializedName("TotalPaid")
	var totalPaid: Double? = null,

	@field:SerializedName("Class")
	var _class: String? = null,

	@field:SerializedName("CustomerName")
	var customerName: String? = null,

	@field:SerializedName("SalTerriotryArName")
	var salTerriotryArName: String? = null,

	@field:SerializedName("TotalReminder")
	var totalReminder: Double? = null
) {


}
