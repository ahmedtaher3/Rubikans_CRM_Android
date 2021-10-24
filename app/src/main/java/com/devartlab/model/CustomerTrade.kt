package com.devartlab.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class CustomerTrade (
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("legalName")
    @Expose
    var legalName: String? = null,

    @SerializedName("credit")
    @Expose
    var credit: String? = null,

    @SerializedName("lastDate")
    @Expose
    var lastDate: String? = null,

    @SerializedName("phone")
    @Expose
    var phone: String? = null,

    @SerializedName("governmentId")
    @Expose
    var governmentId: String? = null,

    @SerializedName("cityId")
    @Expose
    var cityId: String? = null,

    @SerializedName("areaId")
    @Expose
    var areaId: String? = null,

    @SerializedName("address")
    @Expose
    var address: String? = null,

    @SerializedName("bulidDate")
    @Expose
    var bulidDate: String? = null,

    @SerializedName("salesId")
    @Expose
    var salesId: String? = null,

    @SerializedName("notes")
    @Expose
    var notes: String? = null




): Parcelable
{
    override fun toString(): String {
        return "CustomerTrade(id=$id, name=$name, legalName=$legalName, credit=$credit, lastDate=$lastDate, phone=$phone, governmentId=$governmentId, cityId=$cityId, areaId=$areaId, address=$address, bulidDate=$bulidDate, salesId=$salesId, notes=$notes)"
    }
}

