package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StartEndPoint (
        
    @SerializedName("EmpId")
    @Expose
    var empId: Int? = null,

    @SerializedName("AssignId")
    @Expose
    var assignId: Any? = null,

    @SerializedName("AccountId")
    @Expose
    var accountId: Int? = null,

    @SerializedName("SalesRptDate")
    @Expose
    var salesRptDate: String? = null,

    @SerializedName("ShiftId")
    @Expose
    var shiftId: Int? = null,

    @SerializedName("StartPointDateTime")
    @Expose
    var startPointDateTime: String? = null,

    @SerializedName("StartPointId")
    @Expose
    var startPointId: Int? = null,

    @SerializedName("StartPointBranchId")
    @Expose
    var startPointBranchId: Int? = null,

    @SerializedName("LatVal")
    @Expose
    var latVal: String? = null,

    @SerializedName("LangVal")
    @Expose
    var langVal: String? = null,

    @SerializedName("IsStartPoint")
    @Expose
    var isStartPoint: Int? = null,


    var address: String =""

   

)