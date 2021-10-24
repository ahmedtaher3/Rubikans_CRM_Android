package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PenaltiesGoogle (
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("empId")
    @Expose
    var empId: Int? = null,

    @SerializedName("empName")
    @Expose
    var empName: String? = null,

    @SerializedName("managerId")
    @Expose
    var managerId: Int? = null,

    @SerializedName("reason")
    @Expose
    var reason: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("notes")
    @Expose
    var notes: String? = null,

    @SerializedName("status")
    @Expose
    var approve: String? = null,

    @SerializedName("approveComment")
    @Expose
    var approveComment: String? = null,

    @SerializedName("approveDate")
    @Expose
    var approveDate: String? = null,

    @SerializedName("code")
    @Expose
    var code: String? = null ,

    var checked: Boolean? = false

)