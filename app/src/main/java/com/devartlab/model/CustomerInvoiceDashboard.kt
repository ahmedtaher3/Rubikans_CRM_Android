package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class CustomerInvoiceDashboard(

	@field:SerializedName("TotalValue")
	val totalValue: Any? = null,

	@field:SerializedName("PaiedInvoiceCustomer")
	val paiedInvoiceCustomer: Int? = null,

	@field:SerializedName("ReminderInvoiceCustomer")
	val reminderInvoiceCustomer: Int? = null,

	@field:SerializedName("PaiedInvoiceNumber")
	val paiedInvoiceNumber: Int? = null,

	@field:SerializedName("ReminderInvoiceNumber")
	val reminderInvoiceNumber: Int? = null,

	@field:SerializedName("NumberofCustomer")
	val numberofCustomer: Double? = null,

	@field:SerializedName("NumberofInvoice")
	val numberofInvoice: Double? = null,

	@field:SerializedName("TotalPaied")
	val totalPaied: Any? = null,

	@field:SerializedName("TotalReminder")
	val totalReminder: Double? = null
)
