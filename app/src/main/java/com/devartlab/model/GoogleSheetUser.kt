package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GoogleSheetUser (
    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("typeRequest")
    @Expose
    var typeRequest: String? = null,

    @SerializedName("requestStartIn")
    @Expose
    var requestStartIn: String? = null,

    @SerializedName("requestEndIn")
    @Expose
    var requestEndIn: String? = null,

    @SerializedName("notes")
    @Expose
    var notes: String? = null,

    @SerializedName("managerId")
    @Expose
    var managerId: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("comment")
    @Expose
    var comment: String? = null,

    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("approvedDate")
    @Expose
    var approvedDate: String? = null,

    var checked: Boolean? = true

)