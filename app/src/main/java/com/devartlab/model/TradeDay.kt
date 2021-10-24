package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


class TradeDay(
        @SerializedName("empId")
        @Expose
        var empId: Int? = null,

        @SerializedName("startDistance")
        @Expose
        var startDistance: String? = null,

        @SerializedName("startDayAt")
        @Expose
        var startDayAt: String? = null,

        @SerializedName("startDayLat")
        @Expose
        var startDayLat: String? = null,

        @SerializedName("startDayLong")
        @Expose
        var startDayLong: String? = null,


        @SerializedName("endDistance")
        @Expose
        var endDistance: String? = null,

        @SerializedName("endDayAt")
        @Expose
        var endDayAt: String? = null,

        @SerializedName("endDayLat")
        @Expose
        var endDayLat: String? = null,

        @SerializedName("endDayLong")
        @Expose
        var endDayLong: String? = null,





        @SerializedName("code")
        @Expose
        var code: String? = null

)  : Serializable
{
        override fun toString(): String {
                return "TradeDay(empId=$empId, startDistance=$startDistance, startDayAt=$startDayAt, startDayLat=$startDayLat, startDayLong=$startDayLong, endDistance=$endDistance, endDayAt=$endDayAt, endDayLat=$endDayLat, endDayLong=$endDayLong, code=$code)"
        }
}