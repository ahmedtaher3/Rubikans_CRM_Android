package com.devartlab.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize class DoubleVisitReport : Parcelable {
        @SerializedName("Manger")
        @Expose
        var manger: String? = null

        @SerializedName("EmployeeEMpName")
        @Expose
        var employeeEMpName: String? = null

        @SerializedName("PlanAmShift")
        @Expose
        var planAmShift: Int? = null

        @SerializedName("PlanVisitAmShift")
        @Expose
        var planVisitAmShift: Int? = null

        @SerializedName("CoverAmShift")
        @Expose
        var coverAmShift: Int? = null

        @SerializedName("VisitNumAmShift")
        @Expose
        var visitNumAmShift: Int? = null

        @SerializedName("PlanPmShift")
        @Expose
        var planPmShift: Int? = null

        @SerializedName("CoverPmShift")
        @Expose
        var coverPmShift: Int? = null

        @SerializedName("PlanVisitPmShift")
        @Expose
        var planVisitPmShift: Int? = null

        @SerializedName("VisitNumPmShift")
        @Expose
        var visitNumPmShift: Int? = null

        @SerializedName("PlanFullDay")
        @Expose
        var planFullDay: Int? = null

        @SerializedName("CoverFullDay")
        @Expose
        var coverFullDay: Int? = null

        @SerializedName("PlanVisitFullDay")
        @Expose
        var planVisitFullDay: Int? = null

        @SerializedName("VisitNumFullDay")
        @Expose
        var visitNumFullDay: Int? = null

        @SerializedName("MangerId")
        @Expose
        var mangerId: Int? = null

        @SerializedName("EmployeeId")
        @Expose
        var employeeId: Int? = null

        @SerializedName("IsVacant")
        @Expose
        var isVacant: Boolean? = null

        @SerializedName("TotalPlanAmShift")
        @Expose
        var totalPlanAmShift: Int? = null

        @SerializedName("TotalCoverAmShift")
        @Expose
        var totalCoverAmShift: Int? = null

        @SerializedName("TotalPlanVisitPmShift")
        @Expose
        var totalPlanVisitPmShift: Int? = null

        @SerializedName("TotalPlanVisitAmShift")
        @Expose
        var totalPlanVisitAmShift: Int? = null

        @SerializedName("TotalVisitNumAmShift")
        @Expose
        var totalVisitNumAmShift: Int? = null

        @SerializedName("TotalPlanPmShift")
        @Expose
        var totalPlanPmShift: Int? = null

        @SerializedName("TotalCoverPmShift")
        @Expose
        var totalCoverPmShift: Int? = null

        @SerializedName("TotalCoverPmShift1")
        @Expose
        var totalCoverPmShift1: Int? = null

        @SerializedName("TotalVisitNumPmShift")
        @Expose
        var totalVisitNumPmShift: Int? = null

        @SerializedName("PrcentagePlanAmShift")
        @Expose
        var prcentagePlanAmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }
        @SerializedName("PrcentageCoverAmShift")
        @Expose
        var prcentageCoverAmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }
        @SerializedName("PrcentagePlanVisitAmShift")
        @Expose
        var prcentagePlanVisitAmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }

        @SerializedName("PrcentageVisitNumAmShift")
        @Expose
        var prcentageVisitNumAmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }


        @SerializedName("PrcentagePlanPmShift")
        @Expose
        var prcentagePlanPmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }

        @SerializedName("PrcentageCoverPmShift")
        @Expose
        var prcentageCoverPmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }

        @SerializedName("PrcentagePlanVisitPmShift")
        @Expose
        var prcentagePlanVisitPmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }

        @SerializedName("PrcentageVisitNumPmShift")
        @Expose
        var prcentageVisitNumPmShift: Int? = null
                get() = field ?: 0
                set(value) {
                        if (value != null) {
                                field = value
                        }
                }

        @SerializedName("EmployeeImage")
        @Expose
        var employeeImage: String? = null

        @SerializedName("ManagerImage")
        @Expose
        var managerImage: String? = null

}