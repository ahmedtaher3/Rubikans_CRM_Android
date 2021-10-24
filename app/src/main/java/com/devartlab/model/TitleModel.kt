package com.devartlab.model



import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class TitleModel (

        var id: String? = null,

        var name: String? = null

): Parcelable

