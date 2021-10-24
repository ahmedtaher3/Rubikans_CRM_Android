package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MRRank {
    @SerializedName("TerrEmpId")
    @Expose
    var terrEmpId: Int? = null

    @SerializedName("TerrAssignId")
    @Expose
    var terrAssignId: Int? = null

    @SerializedName("TerrAccountId")
    @Expose
    var terrAccountId: Int? = null

    @SerializedName("LineEnName")
    @Expose
    var lineEnName: String? = null

    @SerializedName("EMpName")
    @Expose
    private var eMpName: String? = null

    @SerializedName("SalTerriotryEnName")
    @Expose
    var salTerriotryEnName: String? = null

    @SerializedName("VisitRatio")
    @Expose
    var visitRatio: String? = null

    @SerializedName("VisitPercentage")
    @Expose
    var visitPercentage: Double? = null

    @SerializedName("ListRatio")
    @Expose
    var listRatio: String? = null

    @SerializedName("ListPercentage")
    @Expose
    var listPercentage: Double? = null
        get() = field ?: 0.0
        set(value) {
            if (value != null) {
                field = value
            }
        }

    @SerializedName("UnCoverRatio")
    @Expose
    var unCoverRatio: String? = null

    @SerializedName("UnCoverPercentage")
    @Expose
    var unCoverPercentage: Double? = null

    @SerializedName("ImagePath")
    @Expose
    var imagePath: String? = null

    fun geteMpName(): String? {
        return eMpName
    }

    fun seteMpName(eMpName: String?) {
        this.eMpName = eMpName
    }

}