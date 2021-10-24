package com.devartlab.data.room.filterdata

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FilterDataEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "empId")
    @SerializedName("EmpId")
    @Expose
    var empId: Int = 0,

    @ColumnInfo(name = "empName")
    @SerializedName("EmpName")
    @Expose
    var empName: String = "",

    @ColumnInfo(name = "empTitle")
    @SerializedName("EmpTitle")
    @Expose
    var empTitle: String = "",

    @ColumnInfo(name = "salTerriotryArName")
    @SerializedName("SalTerriotryArName")
    @Expose
    var salTerriotryArName: String = "",

    @ColumnInfo(name = "empAccountId")
    @SerializedName("EmpAccountId")
    @Expose
    var empAccountId: Int = 0,

    @ColumnInfo(name = "level")
    @SerializedName("_Level")
    @Expose
    var level: Int = 0,

    @ColumnInfo(name = "fileImage")
    @SerializedName("FileImage")
    @Expose
    var fileImage: String? = null,

    @ColumnInfo(name = "fieldId")
    @SerializedName("FieldId")
    @Expose
    var fieldId: Int = 0,

    @ColumnInfo(name = "fieldName")
    @SerializedName("FieldName")
    @Expose
    var fieldName: String = "",

    @ColumnInfo(name = "parentId")
    @SerializedName("parentId")
    @Expose
    var parentId: Int? = null,

    @ColumnInfo(name = "parentName")
    @SerializedName("parentName")
    @Expose
    var parentName: String? = null

) : Parcelable {

    constructor(
        empId: Int,
        empName: String,
        empTitle: String,
        salTerriotryArName: String,
        empAccountId: Int,
        level: Int,
        fileImage: String?,
        fieldId: Int,
        fieldName: String,
        parentId: Int?,
        parentName: String?
    ) : this() {
        this.empId = empId
        this.empName = empName
        this.empTitle = empTitle
        this.salTerriotryArName = salTerriotryArName
        this.empAccountId = empAccountId
        this.level = level
        this.fileImage = fileImage
        this.fieldId = fieldId
        this.fieldName = fieldName
        this.parentId = parentId
        this.parentName = parentName
    }


}