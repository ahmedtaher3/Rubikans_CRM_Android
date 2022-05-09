package com.devartlab.ui.main.ui.eShopping.nearbyPharmacy.model

import com.google.gson.annotations.SerializedName

data class NearbyPharmacyResponse(

    @field:SerializedName("data")
    val data: PharmacyData,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("status")
    val status: Int
)

data class PharmacyItem(

    @field:SerializedName("fulladdress")
    val fulladdress: String,

    @field:SerializedName("lat_lng")
    val latLng: String,

    @field:SerializedName("lng")
    val lng: String,

    @field:SerializedName("pharmacy_id")
    val pharmacyId: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("lat")
    val lat: String,

    @field:SerializedName("orders_count")
    val orders_count: Int,

    @field:SerializedName("mpharmacy")
    val mpharmacy: Mpharmacy
)

data class Mpharmacy(

    @field:SerializedName("mandwob_id")
    val mandwobId: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("mmndob")
    val mmndob: Mmndob,

    @field: SerializedName("activated")
    val activated: Int,

    @field:SerializedName("id")
    val id: Int
)

data class PharmacyData(

    @field:SerializedName("first_page_url")
    val firstPageUrl: String,

    @field:SerializedName("path")
    val path: String,

    @field:SerializedName("per_page")
    val perPage: Int,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("data")
    val data: List<PharmacyItem>,

    @field:SerializedName("last_page")
    val lastPage: Int,

    @field:SerializedName("last_page_url")
    val lastPageUrl: String,

    @field:SerializedName("next_page_url")
    val nextPageUrl: Any,

    @field:SerializedName("from")
    val from: Int,

    @field:SerializedName("to")
    val to: Int,

    @field:SerializedName("prev_page_url")
    val prevPageUrl: Any,

    @field:SerializedName("current_page")
    val currentPage: Int
)

data class Mmndob(

    @field:SerializedName("area")
    val area: String,

    @field:SerializedName("hrms_JobEnName")
    val hrmsJobEnName: String,

    @field:SerializedName("hrms")
    val hrms: String,

    @field:SerializedName("line")
    val line: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
