package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorkFromHomeModel(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("end")
    @Expose
    var end: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("managerId")
    @Expose
    var managerId: Int? = null,

    @SerializedName("notes")
    @Expose
    var notes: String? = null,

    @SerializedName("approve")
    @Expose
    var approve: String? = null,

    @SerializedName("approveDate")
    @Expose
    var approveDate: String? = null,

    @SerializedName("code")
    @Expose
    var code: String? = null,

    var checked: Boolean? = false

)