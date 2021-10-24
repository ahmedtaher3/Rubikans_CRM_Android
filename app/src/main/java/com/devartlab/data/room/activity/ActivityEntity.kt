package com.devartlab.data.room.activity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ActivityEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "activityId")
    @SerializedName("ActivityId")
    @Expose
    var activityId: Int? = null,

    @ColumnInfo(name = "activityEnName")
    @SerializedName("ActivityEnName")
    @Expose
    var activityEnName: String? = null,

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    @Expose
    var notes: String? = null,

    @ColumnInfo(name = "typeId")
    @SerializedName("TypeId")
    @Expose
    var typeId: Int? = null,

    @ColumnInfo(name = "typeNotes")
    @SerializedName("TypeNotes")
    @Expose
    var typeNotes: String? = null

) : Parcelable {

    constructor(
        activityId: Int?,
        activityEnName: String?,
        notes: String?,
        typeId: Int?,
        typeNotes: String?
    ) : this() {
        this.activityId = activityId
        this.activityEnName = activityEnName
        this.notes = notes
        this.typeId = typeId
        this.typeNotes = typeNotes
    }
}