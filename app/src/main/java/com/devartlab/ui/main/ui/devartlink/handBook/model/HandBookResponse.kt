package com.devartlab.ui.main.ui.devartlink.handBook.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.iamkamrul.expandablerecyclerviewlist.model.ParentListItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HandBookResponse(

    @field:SerializedName("first_page_url")
    val firstPageUrl: String? = null,

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("per_page")
    val perPage: Int? = null,

    @field:SerializedName("data")
    val data: List<HandBookItem>? = null,

    @field:SerializedName("next_page_url")
    val nextPageUrl: String? = null,

    @field:SerializedName("from")
    val from: Int? = null,

    @field:SerializedName("to")
    val to: Int? = null,

    @field:SerializedName("prev_page_url")
    val prevPageUrl: String? = null,

    @field:SerializedName("current_page")
    val currentPage: Int? = null
) : Parcelable

@Parcelize
data class HandBookItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("arrange")
    val arrange: Int? = null,

    @field:SerializedName("subs")
    val subs: ArrayList<HandBookSubs> = ArrayList(),

    @field:SerializedName("parent_id")
    val parentId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null
) : Parcelable, ParentListItem {
    override fun getChildItemList(): List<HandBookSubs> = subs
    override fun isInitiallyExpanded(): Boolean = false

}

@Parcelize
data class HandBookSubs(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("arrange")
    val arrange: Int? = null,

    @field:SerializedName("parent_id")
    val parentId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("sections")
    val sections: List<SectionsItem?>? = null
) : Parcelable

@Parcelize
data class SectionsItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("arrange")
    val arrange: Int? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("faq_id")
    val faqId: String? = null
) : Parcelable



