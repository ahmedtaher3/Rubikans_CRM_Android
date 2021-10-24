package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class CustomerReportDetilas(

	@field:SerializedName("Shift")
	val shift: String? = null,

	@field:SerializedName("Plan/Report")
	val planReport: String? = null,

	@field:SerializedName("ReportView")
	val reportView: Int? = null,

	@field:SerializedName("CustomerId")
	val customerId: Int? = null,

	@field:SerializedName("Day")
	val day: String? = null,

	@field:SerializedName("Date")
	val date: String? = null
)
