package com.devartlab.model

import com.google.gson.annotations.SerializedName

data class DevartLabReportsFilterDTO(


        @field:SerializedName("_Option")
        var option: Int? = null,

        @field:SerializedName("PageSize")
        var pageSize: Int? = null,

        @field:SerializedName("PageNumber")
        var pageNumber: Int? = null,

        @field:SerializedName("AccountIdStr")
        var accountIdStr: String? = null,

        @field:SerializedName("PrItemIdStr")
        var prItemIdStr: Any? = null,

        @field:SerializedName("_CustomerTypeIdStr")
        var customerTypeIdStr: Any? = null,

        @field:SerializedName("ToDateInMsFormat")
        var toDateInMsFormat: Any? = null,

        @field:SerializedName("StoreIdStr")
        var storeIdStr: Any? = null,

        @field:SerializedName("EmployeeIdStr")
        var employeeIdStr: Any? = null,


        @field:SerializedName("GroupLevel")
        var groupLevel: Any? = null,

        @field:SerializedName("IsApprovedTrx")
        var isApprovedTrx: Boolean? = null,

        @field:SerializedName("_ClassIdStr")
        var classIdStr: Any? = null,

        @field:SerializedName("TODate")
        var tODate: Any? = null,

        @field:SerializedName("UnitIdStr")
        var unitIdStr: Any? = null,

        @field:SerializedName("_SalesLineIdStr")
        var salesLineIdStr: Any? = null,

        @field:SerializedName("AllowToBrowesAllRecord")
        var allowToBrowesAllRecord: Boolean? = null,

        @field:SerializedName("CycleIdStr")
        var cycleIdStr: Any? = null,

        @field:SerializedName("CustomerIdStr")
        var customerIdStr: Any? = null,


        @field:SerializedName("SupplierIdStr")
        var supplierIdStr: Any? = null,

        @field:SerializedName("SearchText")
        var searchText: Any? = null,

        @field:SerializedName("LoginUserAccountId")
        var loginUserAccountId: Int? = null,

        @field:SerializedName("_SpecialityIdStr")
        var specialityIdStr: Any? = null,

        @field:SerializedName("InvoiceTypeIdStr")
        var invoiceTypeIdStr: Any? = null,

        @field:SerializedName("_SalesBricksIdStr")
        var salesBricksIdStr: Any? = null,

        @field:SerializedName("AssignIdStr")
        var assignIdStr: Any? = null,

        @field:SerializedName("FromDate")
        var fromDate: Any? = null,

        @field:SerializedName("FromDateInMsFormat")
        var fromDateInMsFormat: Any? = null
)
