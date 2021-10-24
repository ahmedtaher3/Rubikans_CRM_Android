package com.devartlab.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class CustomerAcher (
        @SerializedName("id")
        @Expose
        var id: String? = null,

        @SerializedName("name")
        @Expose
        var name: String? = null,

        @SerializedName("lat")
        @Expose
        var lat: String? = null,

        @SerializedName("long")
        @Expose
        var long: String? = null



): Parcelable

