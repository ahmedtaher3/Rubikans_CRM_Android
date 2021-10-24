package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SVRank {
    @SerializedName("_EmpINdex")
    @Expose
    var empINdex: Int? = null

    @SerializedName("EmpId")
    @Expose
    var empId: Int? = null

    @SerializedName("EmpAccount")
    @Expose
    var empAccount: Int? = null

    @SerializedName("EmpNAme")
    @Expose
    var empNAme: String? = null

    @SerializedName("AccDescription")
    @Expose
    var accDescription: String? = null

    @SerializedName("OfficialVacation")
    @Expose
    var officialVacation: Int? = null

    @SerializedName("CycleWorkingDays")
    @Expose
    var cycleWorkingDays: Int? = null

    @SerializedName("EMpVacationSHift")
    @Expose
    var eMpVacationSHift: Int? = null

    @SerializedName("doubleVPlanshift")
    @Expose
    var doubleVPlanshift: Int? = null

    @SerializedName("doubleVPlanCustomer")
    @Expose
    var doubleVPlanCustomer: Int? = null

    @SerializedName("doubleVActualshift")
    @Expose
    var doubleVActualshift: Int? = null

    @SerializedName("doubleVActualVisits")
    @Expose
    var doubleVActualVisits: Int? = null

    @SerializedName("SingleVPlanshift")
    @Expose
    var singleVPlanshift: Int? = null

    @SerializedName("SingleVPlanCustomer")
    @Expose
    var singleVPlanCustomer: Int? = null

    @SerializedName("SingleVActualshift")
    @Expose
    var singleVActualshift: Int? = null

    @SerializedName("SingleVActualVisits")
    @Expose
    var singleVActualVisits: Int? = null

    @SerializedName("OtherActivitesSHift")
    @Expose
    var otherActivitesSHift: Int? = null

    @SerializedName("ActualOtherActivitesSHift")
    @Expose
    var actualOtherActivitesSHift: Int? = null

    @SerializedName("ClinicWorkingDays")
    @Expose
    var clinicWorkingDays: Int? = null

    @SerializedName("ClinicActualVisits")
    @Expose
    var clinicActualVisits: Int? = null

    @SerializedName("ClinicCallRate")
    @Expose
    var clinicCallRate: Double? = null

    @SerializedName("ImagePath")
    @Expose
    var imagePath: String? = null

    fun geteMpVacationSHift(): Int? {
        return eMpVacationSHift
    }

    fun seteMpVacationSHift(eMpVacationSHift: Int?) {
        this.eMpVacationSHift = eMpVacationSHift
    }

}