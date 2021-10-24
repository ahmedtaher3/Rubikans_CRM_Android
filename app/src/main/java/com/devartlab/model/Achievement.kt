package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Achievement(
    @SerializedName("SalesAchivement")
    @Expose
    var salesAchivement: String? = null,

    @SerializedName("TOTalCoverage")
    @Expose
    var tOTalCoverage: String? = null,

    @SerializedName("TOtalBudget")
    @Expose
    var totalBudget: String? = null


)