package com.devartlab.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductTrade(
        @SerializedName("id")
        @Expose
        var id: Int? = null,

        @SerializedName("name")
        @Expose
        var name: String? = null,

        @SerializedName("image")
        @Expose
        var image: String? = null,

        @SerializedName("price")
        @Expose
        var price: Int? = null,

        @SerializedName("incoming")
        @Expose
        var incoming: String? = null,

        @SerializedName("outgoing")
        @Expose
        var outgoing: String? = null,

        @SerializedName("credit")
        @Expose
        var credit: String? = null,
        var count: Int = 0
) : Parcelable