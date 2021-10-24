package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class CustomeBudgetDetilas(

        @field:SerializedName("MarkReqExecutDate")
        val markReqExecutDate: String? = null,

        @field:SerializedName("TotalCost")
        val totalCost: Double? = null,

        @field:SerializedName("CustomerCounter")
        val customerCounter: Int? = null,

        @field:SerializedName("ReqTypeDescription")
        val reqTypeDescription: String? = null,

        @field:SerializedName("TotalPaied")
        val totalPaied: Int? = null,

        @field:SerializedName("CustomerId")
        val customerId: Int? = null,

        @field:SerializedName("MarkReqCode")
        val markReqCode: String? = null,

        @field:SerializedName("ReqDescription")
        val reqDescription: String? = null,

        @field:SerializedName("CustomerSHarePaied")
        val customerSHarePaied: Int? = null
)
