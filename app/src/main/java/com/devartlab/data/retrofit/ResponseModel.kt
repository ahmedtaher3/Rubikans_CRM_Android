package com.devartlab.data.retrofit

import com.devartlab.model.CorrectiveAction
import com.devartlab.model.EMPloyeeAppraisal
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class ResponseModel (
    @SerializedName("IsSuccesed")
    @Expose
    var isSuccesed: Boolean,

    @SerializedName("RerurnMessage")
    @Expose
    var rerurnMessage: String,

    @SerializedName("RerurnValAsInteger")
    @Expose
    var rerurnValAsInteger: Int,

    @SerializedName("RerurnValAsString")
    @Expose
    var rerurnValAsString: String,

    @SerializedName("data")
    @Expose
    var data: Data,

    @SerializedName("EMployeeAppraisal")
    @Expose
    var eMPloyeeAppraisal: ArrayList<EMPloyeeAppraisal>,

    @SerializedName("CorrectiveAction")
    @Expose
    var correctiveAction: ArrayList<CorrectiveAction> 

 
)