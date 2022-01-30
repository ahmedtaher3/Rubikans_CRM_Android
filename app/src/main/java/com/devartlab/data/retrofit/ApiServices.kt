package com.devartlab.data.retrofit

import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.areas.AreasResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.cities.CitiesResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.country.CountryResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.districts.DistrictsResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.getUserAddress.GetUserAddressResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress.UpdateAddressRequest
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress.UpdateAddressResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.Pharmacy.CategoryPharmacyResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.searchAllPharmacy.SearchAllPharmacyResponse
import com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping.Login4EShoppingRequest
import com.devartlab.ui.main.ui.eShopping.ticket.model.sendMessages.SendMessagesResponse
import com.devartlab.a4eshopping.orientationVideos.model.ResponseVideos
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import com.devartlab.model.*
import com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model.ChatListResponse
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.ImageModel.ImageProfileResponse
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.mareSeen.MarkSeenRequest
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.mareSeen.MarkSeenResponse
import com.devartlab.ui.main.ui.devartlink.letsTalk.home.model.peopleList.PeopleListResponse
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.searchPeople.SearchPeapleResponse
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.user.UserResponse
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.userID.UserIDResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.uploadPharmacyFiles.model.pharmacydata.GetInfoPharmacyResponse
import com.devartlab.ui.main.ui.eShopping.addProductsToThePharmacy.model.showCart.ShowCartResponse
import com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping.Login4EShoppingResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.allComments.model.AllCommentsResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.connectPharmacy.ConnectPharmacyResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.searchForPharmacy.ConnetctedPharmaciesResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.searchForPharmacy.SearchForPharmacyRequest
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.model.searchForPharmacy.SearchForPharmacyResponse
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.uploadPharmacyFiles.model.updatePharmacyDetails
import com.devartlab.ui.main.ui.eShopping.pharmacySales.model.PharmacySalesResponse
import com.devartlab.ui.main.ui.eShopping.pharmacySales.model.detailsPharmacySales.DetailsPharmacySalesResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.addRate.AddRateRequest
import com.devartlab.ui.main.ui.eShopping.ticket.model.addRate.AddRateResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.addTicket.AddTicketRequest
import com.devartlab.ui.main.ui.eShopping.ticket.model.addTicket.AddTicketRsponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.deleteMessages.DeleteMessagesResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.deleteTickets.DeleteTicketsResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.fetchMessages.FetchMessagesResponse
import com.devartlab.ui.main.ui.eShopping.ticket.model.getContacts.GetContactsResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiServices {
    @POST("DailyReport/SubmitDailyReport")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun uploadReport(
        @Query("AllowToCloseShift") AllowToCloseShift: Boolean,
        @Query("ShiftId") ShiftId: Int,
        @Query("SalesRptDateInMsFormat") SalesRptDateInMsFormat: String,
        @Query("EMpId") EMpId: Int,
        @Query("AccountId") AccountId: Int,
        @Query("DeviceMac") DeviceMac: String,
        @Query("LatVal") LatVal: Double,
        @Query("LangVal") LangVal: Double,
        @Body array: JsonObject
    ): Observable<ResponseModel>

    @POST("AuthUsersApi/UpdatePermission")
    fun updatePermission(@Query("pUserId") pUserId: Int): Observable<ResponseModel>

    @GET("CyclePlan/OpenPlanDayPlan")
    fun updatePlanPermission(@Query("AccountId") AccountId: Int): Observable<ResponseModel>

    @GET("CyclePlan/OpenPlanDayList")
    fun updateListPermission(@Query("AccountId") AccountId: Int): Observable<ResponseModel>

    @GET("CyclePlan/GetOpenCyclePlan")
    fun getPlan(@Query("AccountId") id: Int): Observable<ResponseModel>

    @GET("CustomerList/GetCustomerList")
    fun getCustomerList(@Query("AccountId") AccountId: Int): Observable<ArrayList<CustomerList>>

    @GET("AuthUsersApi/GetUserActivity")
    fun getUserActivity(@Query("AccountId") AccountId: Int): Observable<ArrayList<ActivityEntity>>

    @GET("CustomerList/GetCustomer")
    fun getFilterCustomerList(
        @Query("AccountId") AccountId: Int,
        @Query("TerrAssignIdStr") TerrAssignIdStr: String,
        @Query("BrickIdStr") BrickIdStr: String,
        @Query("SpecialityIdStr") SpecialityIdStr: String,
        @Query("ClassIdStr") ClassIdStr: String,
        @Query("FilterText") FilterText: String,
        @Query("cusIdes") cusIdes: String,
        @Query("cusBranchIds") cusBranchIds: String
    ): Observable<ArrayList<CustomerList>>

    @GET("CustomerList/SearchCustomer")
    fun SearchCustomer(
        @Query("AccountId") AccountId: Int,
        @Query("TerrAssignIdStr") TerrAssignIdStr: String,
        @Query("BrickIdStr") BrickIdStr: String,
        @Query("SpecialityIdStr") SpecialityIdStr: String,
        @Query("ClassIdStr") ClassIdStr: String,
        @Query("FilterText") FilterText: String,
        @Query("cusIdes") cusIdes: String,
        @Query("cusBranchIds") cusBranchIds: String
    ): Observable<ArrayList<ListEntity>>

    @GET("CustomerList/GetCustomerListType")
    fun getCustomerListType(@Query("AccountId") id: Int): Observable<ArrayList<SpecialtyParentEntity>>

    @POST("CyclePlan/UpdatePlan")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun updatePlan(
        @Query("AccountId") id: Int,
        @Query("DeviceMac") DeviceMac: String,
        @Body array: JsonArray
    ): Observable<SyncPlanResponse>

    @POST("CustomerList/UpdateCustomerList")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun updateList(
        @Query("AccountId") id: Int,
        @Query("DeviceMac") DeviceMac: String,
        @Body array: JsonArray
    ): Observable<ArrayList<Response>>

    @GET("CyclePlan/GetYtdCyclePlan")
    fun getYtdCyclePlan(@Query("AccountId") id: Int): Observable<ArrayList<Cycle>>

    @get:GET("CyclePlan/GetYtdCyclePlan")
    val ytdCyclePlanAll: Observable<ArrayList<Cycle>>

    @GET("LookupUtility/GetbyTableName")
    fun getFilterData(
        @Query("AccountId") id: Int,
        @Query("TableName") tableName: String,
        @Query("ParentIdStr") ParentIdStr: String,
        @Query("WhereCondtion") whereCondition: String,
        @Query("FilterText") filterText: String
    ): Observable<ArrayList<FilterDataEntity>>

    @GET("LookupUtility/GetbyTableName")
    fun getJobs(
        @Query("AccountId") id: Int,
        @Query("TableName") tableName: String,
        @Query("ParentIdStr") whereCondition: String,
        @Query("FilterText") filterTSyncAllProductext: String
    ): Observable<ArrayList<JobsModel>>

    @GET("Product/MasterFile/SyncAllProduct")
    fun syncProducts(@Query("AccountId") id: Int): Observable<ProductTable>

    @POST("DailyReport/SyncReportCustomers")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun syncReportCustomer(
        @Query("AccountId") id: Int,
        @Query("DeviceMac") DeviceMac: String,
        @Query("SalesRptDateInMsFormat") SalesRptDateInMsFormat: Long,
        @Query("LatVal") LatVal: Double,
        @Query("LangVal") LangVal: Double,
        @Query("pEmpId") pEmpId: Int,
        @Query("pShiftId") pShiftId: Int,
        @Body array: JsonArray
    ): Observable<ArrayList<SyncReportMassage>>

    @GET("CallManagmentReport/MRRankDashboard")
    fun getMRRankReport(
        @Query("pAccountsIdStr") pAccountsIdStr: String,
        @Query("pFromDate") pFromDate: String,
        @Query("pToDate") pToDate: String,
        @Query("pParentSpecialityIdStr") pParentSpecialityIdStr: String,
        @Query("pGroupLevel") pGroupLevel: Int,
        @Query("pCycleId") pCycleId: Int,
        @Query("pActivityIdStr") pActivityIdStr: String,
        @Query("pPageNumber") pPageNumber: Int,
        @Query("pSearchText") pSearchText: String,
        @Query("pPageSize") pPageSize: Int
    ): Observable<ResponseModel>

    @GET("CallManagmentReport/GetPLanAndCover")
    fun getPLanAndCover(
        @Query("pCustomerTypeIdStr") pAccountsIdStr: String,
        @Query("pEmployeeIdStr") pEmployeeIdStr: String,
        @Query("pFromDate") pFromDate: String,
        @Query("pToDate") pToDate: String,
        @Query("pCycleId") pCycleId: Int
    ): Observable<ResponseModel>

    @GET("DailyReport/GetDailyReport")
    fun getStartPointReport(
        @Query("AccountId") AccountId: String,
        @Query("pDate") pDate: String,
        @Query("pShiftId") pShiftId: String
    ): Observable<ResponseModel>

    @GET("CallManagmentReport/DoubleVisitReport")
    fun getDVReport(
        @Query("pFromDate") pFromDate: String,
        @Query("pToDate") pToDate: String,
        @Query("pSvIdStr") pSvIdStr: String
    ): Observable<ResponseModel>

    @GET("CallManagmentReport/DoubleVisitDetails")
    fun getDVDetails(
        @Query("pFromDate") pFromDate: String,
        @Query("pToDate") pToDate: String,
        @Query("pEmployeeId") pEmployeeId: String
    ): Observable<ResponseModel>

    @GET("CallManagmentReport/SVRank")
    fun getSvRankReport(
        @Query("pAccountsIdStr") pAccountsIdStr: String,
        @Query("pCycleId") pCycleId: Int
    ): Observable<ResponseModel>

    @GET("CallManagmentReport/MRRankDashboardDetails")
    fun getMRRankReportDetails(
        @Query("pAccountsIdStr") pAccountsIdStr: String,
        @Query("pFromDate") pFromDate: String,
        @Query("pToDate") pToDate: String,
        @Query("pGroupLevel") pGroupLevel: Int,
        @Query("pCycleId") pCycleId: Int,
        @Query("pActivityIdStr") pActivityIdStr: String,
        @Query("pPageNumber") pPageNumber: Int,
        @Query("pSearchText") pSearchText: String,
        @Query("pPageSize") pPageSize: Int,
        @Query("pEmpId") pEmpId: Int
    ): Observable<ResponseModel>

    @POST("CyclePlan/OpenPlanList")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun openPlanList(@Body array: JsonArray): Observable<ResponseModel>

    @POST("CyclePlan/OpenPlanDay")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun openPlanDay(@Body array: JsonArray): Observable<ResponseModel>

    @POST("DailyReport/SyncDoubleVisitReportCustomer")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun SyncDoubleVisitReportCustomer(
        @Query("AccountId") id: Int,
        @Query("DeviceMac") DeviceMac: String,
        @Body array: JsonArray
    ): Observable<ArrayList<SyncReportMassage>>

    @POST("DailyReport/Test/SyncDoubleVisitReportCustomer")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun testSyncDoubleVisitReportCustomer(
        @Query("AccountId") id: Int,
        @Query("DeviceMac") DeviceMac: String,
        @Body array: JsonObject
    ): Observable<ArrayList<DailyReportModel>>

    @POST("DailyReport/SyncReportSlids")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun syncReportSlids(
        @Query("AccountId") id: Int,
        @Query("ReportId") ReportId: Int,
        @Body array: JsonArray
    ): Observable<ArrayList<SyncReportMassage>>

    @POST("DailyReport/SyncUpdateEvaluationReport")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun SyncUpdateEvaluationReport(
        @Query("AccountId") id: Int,
        @Query("ReportId") ReportId: String,
        @Body array: JsonArray
    ): Observable<ArrayList<SyncReportMassage>>

    @GET("CustomerList/GetStartpoint")
    fun getStartPointList(
        @Query("AccountId") AccountId: Int,
        @Query("TerrAssignIdStr") TerrAssignIdStr: String,
        @Query("BrickIdStr") BrickIdStr: String,
        @Query("SpecialityIdStr") SpecialityIdStr: String,
        @Query("ClassIdStr") ClassIdStr: String,
        @Query("FilterText") FilterText: String
    ): Observable<ArrayList<ListEntity>>

    @GET("CyclePlan/GetMeetingMember")
    fun getMeetingMember(@Query("EMpId") eMpId: Int): Observable<ArrayList<FilterDataEntity>>

    @GET("CyclePlan/GetEMployeePlan")
    fun getEmployeePlan(
        @Query("selectedEmpAccountId") selectedEmpAccountId: String,
        @Query("DayDateInMsFormat") dayDateInMsFormat: String,
        @Query("ShiftId") shiftId: String
    ): Observable<ArrayList<DoubleVisitEmp>>

    @POST("DailyReport/ConfirmStartPoint")
    fun confirmStartPoint(
        @Query("AccountId") AccountId: Int,
        @Query("SalesRptDateInMsFormat") SalesRptDateInMsFormat: String,
        @Query("ShiftId") ShiftId: String,
        @Query("StartPointTimeInMsFormat") StartPointTimeInMsFormat: String,
        @Query("StartPointId") StartPointId: String,
        @Query("StartPointBranchId") StartPointBranchId: String,
        @Query("LatVal") LatVal: String,
        @Query("LangVal") LangVal: String,
        @Query("IsStart") IsStart: Boolean,
        @Query("StartTime") StartTime: String
    ): Observable<ResponseModel>

    @GET("CustomerDataBase/GetTerriotryCustomer")
    fun customerDataBase(
        @Query("AccountId") AccountId: Int,
        @Query("TerrAssignId") TerrAssignId: String,
        @Query("CustomerTypeid") CustomerTypeid: String,
        @Query("BrickIdStr") BrickIdStr: String,
        @Query("SpecialityIdStr") SpecialityIdStr: String,
        @Query("ClassIdStr") ClassIdStr: String,
        @Query("ExecludeBranchIdStr") ExecludeBranchIdStr: String,
        @Query("FilterText") FilterText: String
    ): Observable<ArrayList<CustomerList>>

    //////////////////////////////////////////////////////////////////////////////////////////
    @Multipart
    @POST("EmployeeSelfService/SaveUpdateExpense")
    fun SaveUpdateExpense(
        @Query("IsInsert") IsInsert: String,
        @Query("AccountId") AccountId: Int,
        @Query("EmpId") EmpId: Int,
        @Query("ExpId") ExpId: Int,
        @Query("ExpTypeId") ExpTypeId: String,
        @Query("ExpDateMsFormat") ExpDateMsFormat: String,
        @Query("PreviousKiloMeterValue") PreviousKiloMeterValue: String,
        @Query("CurrentKiloMeterValue") CurrentKiloMeterValue: String,
        @Query("Qty") Qty: String,
        @Query("Value") Value: String,
        @Query("TotalValue") TotalValue: String,
        @Query("Notes") Notes: String,
        @Query("AddMac") AddMac: String,
        @Part IMAGE: MultipartBody.Part
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/GetExpensesType")
    fun GetExpensesType(
        @Query("AccountId") AccountId: Int,
        @Query("EMpid") EMpid: Int,
        @Query("EmpTitleId") EmpTitleId: String
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/DeleteExpense")
    fun DeleteExpense(
        @Query("AccountId") AccountId: Int,
        @Query("ExpId") ExpId: Int
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/GetMOnthExpensesList")
    fun getMonthExpensesList(
        @Query("AccountId") id: Int,
        @Query("EmpId") EmpId: Int,
        @Query("Month") Month: String,
        @Query("Year") Year: String
    ): Observable<ResponseModel>

    //////////////////////////////////////////////////////////////////////////////////////
    @get:GET("EmployeeSelfService/GetPenaltiesType")
    val penaltiesType: Observable<ResponseModel>

    @get:GET("EmployeeSelfService/GetPenaltiesReason")
    val penaltiesReason: Observable<ResponseModel>

    @GET("EmployeeSelfService/GetEmployeePenalties")
    fun getEmployeePenalties(
        @Query("EmpId") EmpId: Int,
        @Query("Month") Month: String,
        @Query("Year") Year: String
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/SaveUpdatePenalty")
    fun saveUpdatePenalty(
        @Query("IsInsert") IsInsert: String,
        @Query("AccountId") id: Int,
        @Query("ManagerId") ManagerId: Int,
        @Query("EmpId") EmpId: Int,
        @Query("PenaltyId") PenaltyId: String,
        @Query("PenTypeId") PenTypeId: String,
        @Query("PenReasonId") PenReasonId: String,
        @Query("PenDateMsFormat") PenDateMsFormat: String,
        @Query("Qty") Qty: String,
        @Query("Notes") Notes: String,
        @Query("AddMac") AddMac: String
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/DeletePenalty")
    fun deletePenalty(
        @Query("AccountId") id: Int,
        @Query("PenaltyId") PenaltyId: Int
    ): Observable<ResponseModel>

    //////////////////////////////////////////////////////////////////////////////////////
    @get:GET("EmployeeSelfService/GetVacationType")
    val vacationType: Observable<ResponseModel>

    @GET("EmployeeSelfService/GetVacationBallance")
    fun getVacationBallance(
        @Query("AccountId") AccountId: Int,
        @Query("EmpId") EmpId: String
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/GetEmployeeVacation")
    fun getEmployeeVacation(
        @Query("EmpId") EmpId: String,
        @Query("FromDateInMsFormat") FromDateInMsFormat: String,
        @Query("ToDateInMsFormat") ToDateInMsFormat: String
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/DeleteVacation")
    fun deleteVacation(
        @Query("AccountId") AccountId: Int,
        @Query("VacationId") VacationId: Int
    ): Observable<ResponseModel>

    @GET("EmployeeSelfService/SaveUpdateVacation")
    fun saveUpdateVacation(
        @Query("IsInsert") IsInsert: String,
        @Query("AccountId") id: Int,
        @Query("EmpId") EmpId: Int,
        @Query("VacationId") VacationId: Int,
        @Query("VacationTypeId") VacationTypeId: Int,
        @Query("Qty") Qty: String,
        @Query("FromDateMsFormat") FromDateMsFormat: String,
        @Query("ToDateMsFormat") ToDateMsFormat: String,
        @Query("AddWithSalaryDeduct") AddWithSalaryDeduct: String,
        @Query("SalaryDeductDaysQty") SalaryDeductDaysQty: String,
        @Query("Notes") Notes: String,
        @Query("AddMac") AddMac: String
    ): Observable<ResponseModel>

    //////////////////////////////////////////////////////////////////////////////////////
    @POST("HumanResource/GetEmployeeMonthlyPaySlip")
    fun getEmployeeMonthlyPaySlip(
        @Query("AccountId") AccountId: Int,
        @Query("EMpid") EMpid: Int,
        @Query("ChoosenMonth") ChoosenMonth: String,
        @Query("ChoosenYear") ChoosenYear: String
    ): Observable<ResponseModel>

    //////////////////////////////////////////////////////////////////////////////////////
    @POST("TimeAttendance/GetEmployeeFingerPrint")
    fun getFingerprintList(
        @Query("AccountId") AccountId: Int,
        @Query("EMpid") EmpId: Int,
        @Query("Month") currentMonth: Int,
        @Query("Year") currentyear: Int
    ): Observable<ResponseModel>

    //////////////////////////////////////////////////////////////////////////////////////
    @Multipart
    @POST("EmployeeSelfService/UPloadFile")
    fun update_image(@Part IMAGE: MultipartBody.Part): Observable<ResponseBody>

    //////////////////////////////////////////////////////////////////////////////////////
    @POST("DailyReport/GetEMployeeDailyReport")
    fun GetEmployeeDailyReport(
        @Query("IsTempView") isTemp: Boolean,
        @Query("AccountId") AccountId: Int,
        @Query("EmployeeAccountId") EmployeeAccountId: Int,
        @Query("DayDateInMSFormat") DayDateInMSFormat: Long,
        @Query("ShiftId") ShiftId: Int
    ): Observable<ResponseModel>

    @POST("DailyReport/GetEMployeeDailyReportDashBoard")
    fun GetEMployeeDailyReportDashBoard(
        @Query("AccountId") AccountId: Int,
        @Query("EmployeeAccountId") EmployeeAccountId: String,
        @Query("CycleId") CycleId: String
    ): Observable<ResponseModel>

    @POST("DailyReport/TempSyncReportCustomer")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun sendTempSyncReportCustomer(
        @Query("AccountId") id: Int,
        @Query("DeviceMac") DeviceMac: String,
        @Body array: JsonArray
    ): Observable<ResponseModel>

    ///////////////////////////////////////////////////////////
    @POST("DailyReport/GetEMployeeDailyReportAppraisalItem")
    fun GetEMployeeDailyReportAppraisalItem(
        @Query("AccountId") AccountId: Int,
        @Query("EmployeeAccountIdStr") EmployeeAccountIdStr: String
    ): Observable<ResponseModel>

    /////////////////////////////////////////////////
    @POST("CyclePlan/GetPLanEditPermition")
    fun GetPLanEditPermition(
        @Query("AccountId") AccountId: Int,
        @Query("PlanId") PlanId: Int,
        @Query("DayHasPlan") DayHasPlan: String,
        @Query("DayDateInMsFormat") DayDateInMsFormat: String
    ): Observable<ResponseModel>

    /////////////////////////////////////////////////////////
    @GET("LookupUtility/SearchEmployee")
    fun filterEmployees(
        @Query("EmpId") EmpId: Int,
        @Query("FilterText") FilterText: String
    ): Observable<ArrayList<FilterDataEntity>>

    /////////////////////////////////////////////////////
    @GET("Employees/GetById")
    fun GetEmployeeById(
        @Query("pEmpId") user_id: Int
    ): Observable<ResponseModel>

    @POST("Employees/InsertAndUpdate")
    @Headers(
        "Contpreliveent-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun InsertAndUpdateEmployee(@Body array: JsonObject): Observable<ResponseModel>

    @GET("AuthUsersApi/Login")
    fun login(
        @Query("UserName") UserName: String,
        @Query("Password") Password: String
    ): Observable<ResponseModel>

    @GET("AuthUsersApi/LoginData")
    fun loginData(
        @Query("UserName") UserName: String,
        @Query("Password") Password: String
    ): Observable<ResponseModel>

    @GET("AuthUsersApi/cycleData")
    fun cycleData(
        @Query("UserId") UserId: Int
    ): Observable<ResponseModel>

    //////////////////////////////////////////////////////////////////////////////////////////
    @GET("MarketRequest/GetPendingRequests")
    fun getPendingRequests(
        @Query("pAccountId") pAccountId: String,
        @Query("pMyAccountId") pMyAccountId: Int,
        @Query("pMarkReqTypeId") pMarkReqTypeId: Int
    ): Observable<ResponseModel>

    @GET("MarketRequest/ShowRequestDetails")
    fun showRequestDetails(
        @Query("pRequestId") pRequestId: Int,
        @Query("pRequestApprovedId") pRequestApprovedId: Int
    ): Observable<ResponseModel>

    @GET("MarketRequest/RequestsApproval")
    fun sendRequestsApproval(
        @Query("pCurrentFlowdetIdStr") pCurrentFlowdetIdStr: String,
        @Query("pApproved") pApproved: Boolean,
        @Query("pDeny") pDeny: Boolean,
        @Query("pArchiving") pArchiving: Boolean,
        @Query("pNotes") pNotes: String,
        @Query("AccountId") AccountId: Int,
        @Query("pMacData") pMacData: String
    ): Observable<ResponseModel>

    /////////////////////////////////////////////////////////
    @POST("Items/GetAllItemReportByOption")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun getProducts(@Body `object`: JsonObject): Observable<ResponseModel>

    @GET("Items/GetAllFinishedProduct")
    fun getAllProducts(
        @Query("pPageSize") pPageSize: Int,
        @Query("pPageNumber") pPageNumber: Int,
        @Query("pSearchText") pSearchText: String
    ): Observable<ResponseModel>


    @GET("InvDefSalesPurchaseType/GetAllType")
    fun getAllType(
        @Query("AllowToTrade") allowToTrade: Boolean
    ): Observable<ResponseModel>


    @get:GET("InvDefSalesPurchaseType/GetAllType")
    val allType: Observable<ResponseModel>

    @POST("SalesPurchase/InsertAndUpdate")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun insertAndUpdate(@Body array: JsonObject): Observable<ResponseBody>

    @POST("SalesPurchase/InvoiceCashCollection")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun cashCollection(@Body array: JsonArray): Observable<ResponseModel>

    @POST("SalesPurchase/GetAllSalesPurchaseReport")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun GetAllSalesPurchaseReport(@Body `object`: JsonObject): Observable<ResponseModel>


    @POST("InventoryDefTrxType/GetAllInventoryTrxTypeByOption")
    fun getDataFromAPI(@Body `object`: JsonObject): Observable<ResponseModel>

    @POST("InventoryTransaction/GetAllInvnetoryTrxByOption")
    fun getAllInvnetoryTrxByOption(@Body `object`: JsonObject): Observable<ResponseModel>


    @POST("InventoryDefTrxType/GetAllInventoryTrxTypeByOption")
    fun getDataFromAPI2(@Body objects: JsonObject): Call<ResponseModel>


    @GET("DefStores/GetAll")
    fun getStoresDataFromAPI(): Call<ResponseModel>


    @POST("InventoryTransaction/InsertAndUpdate")
    fun inventoryInsertAndUbdate(@Body objects: JsonObject): Call<ResponseModel>


    @POST("InventoryTransaction/GetAllInvnetoryTrxByOption")
    fun getInventoryMovesDetails(@Body objects: JsonObject): Call<ResponseModel>


    @POST("InventoryTransaction/GetAllInvnetoryTrxByOption")
    fun getInventoryMovesDetailsDescription(@Body objects: JsonObject): Call<ResponseModel>


    @POST("InventoryTransaction/INventoryTrxApprove")
    fun approveInventoryMovesDetailsDescription(
        @Query("LoginUserAccountId") loginUserAccId: Int,
        @Query("IsApproved") isApprove: Boolean,
        @Body array: JsonArray
    ): Call<ResponseModel>


    @POST("InventoryTransaction/GetAllInvnetoryTrxByOption")
    fun getInventoryInventory(@Body objects: JsonObject): Call<ResponseModel>


    @POST("InventoryTransaction/GetAllInvnetoryTrxByOption")
    fun getInventoryStatues(@Body objects: JsonObject): Call<ResponseModel>


    @GET("DevartLabUtilities/GetAccountOffLineData")
    fun syncOfflineData(
        @Query("AccountId") AccountId: Int,
        @Query("StoreId") StoreId: Int
    ): Observable<ResponseModel>


    @GET("api/ads")
    fun getAds(): Observable<ResponseModel>

    ///////////////////chat let's talk//////////////
    @GET("search-users")
    open fun getSearchPeaple(): Call<SearchPeapleResponse?>?

    @GET("crm-chat")
    fun getModelUser(
        @Query("u") userName: String?,
        @Query("p") password: String?,
        @Query("fcm") fcm: String?
    ): Call<UserResponse?>?

    @GET("people_list")
    fun getPeopleList(@Query("id") id: String?): Call<PeopleListResponse?>?

    @GET("fetchmessages")
    fun getChatList(@Query("id") id: String?): Call<ChatListResponse?>?

    @GET("get-chat")
    fun getUserID(
        @Query("id") id: String?,
        @Query("user_id") user_id: String?
    ): Call<UserIDResponse?>?

    @GET("image")
    fun getImageProfile(@Query("id") user_id: String?): Call<ImageProfileResponse?>?

    @Multipart
    @POST("sendmessage")
    fun SEND_MESSAGES(
        @Part file: MultipartBody.Part,
        @PartMap send: MutableMap<String, RequestBody>
    ): Call<com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.model.sendMessages.SendMessagesResponse?>?

    @POST("makeSeen")
    fun MARK_SEEN(@Body markSeenRequest: MarkSeenRequest?): Call<MarkSeenResponse?>?

    ///////////////////4eshopping//////////////
    @POST("login")
    fun LOGIN4ESHOPPING(@Body login4EShoppingRequest: Login4EShoppingRequest?): Call<Login4EShoppingResponse?>?

    @GET("connetctedPharmacies")
    fun getConnetctedPharmacies(
        @Header("Authorization") token: String?,
        @Query("q") q: String?
    ): Call<ConnetctedPharmaciesResponse?>?

    @GET("searchForPharmacy")
    fun getSearchForPharmacy(
        @Header("Authorization") token: String?,
        @Query("q") q: String?
    ): Call<SearchForPharmacyResponse?>?

    @POST("connectPharmacy")
    fun connetctedPharmacies(
        @Header("Authorization") token: String?,
        @Body request: SearchForPharmacyRequest?
    ): Call<ConnectPharmacyResponse?>?

    @GET("getAllComments")
    fun getAllComments(
        @Header("Authorization") token: String?,
        @Query("id") id: String?
    ): Call<AllCommentsResponse?>?

    @GET("countries")
    fun getCountry(): Call<CountryResponse?>?

    @GET("get_cities")
    fun getCities(@Query("country_id") country_id: String?): Call<CitiesResponse?>?

    @GET("get_areas")
    fun getAreas(@Query("city_id") city_id: String?): Call<AreasResponse?>?

    @GET("get_districts")
    fun getDistricts(@Query("area_id") area_id: String?): Call<DistrictsResponse?>?

    @POST("updateAddress")
    fun updateAddress(
        @Header("Authorization") token: String?,
        @Body request: UpdateAddressRequest?
    ): Call<UpdateAddressResponse?>?

    @GET("getUserAddress")
    fun getUserAddress(
        @Header("Authorization") token: String?,
        @Query("id") id: String?
    ): Call<GetUserAddressResponse?>?

    @Multipart
    @POST("updatePharmacyDetails")
    fun updatePharmacyDetails(
        @Header("Authorization") token: String?,
        @Part file: MultipartBody.Part?,
        @Part file2: MultipartBody.Part?,
        @Part file3: MultipartBody.Part?,
        @PartMap send: MutableMap<String, RequestBody>
    ): Call<updatePharmacyDetails?>?

    @GET("pharmacySales")
    fun getPharmacySales(
        @Header("Authorization") token: String?,
        @Query("q") q: String?
    ): Call<PharmacySalesResponse?>?

    @GET("order-detailsv2")
    fun getOrderDetails(
        @Header("Authorization") token: String?,
        @Query("order_number") order_number: String?
    ): Call<DetailsPharmacySalesResponse?>?

    @GET("searchAllPharmacy")
    fun getSearchAllPharmacy(
        @Header("Authorization") token: String?,
        @Query("q") q: String?
    ): Call<SearchAllPharmacyResponse?>?


    @GET("categoryType")
    fun getCategoryv2Pharmacy(
        @Header("Authorization") token: String?,
        @Query("type_code") type_code: String?
    ): Call<CategoryPharmacyResponse?>?


    @POST("add-to-cart-MR/{id}/{amount}")
    fun getAddToCard(
        @Header("Authorization") token: String?, @Path("id") id: Int,
        @Path("amount") amount: Int,
        @Body request: AddToCardRequest?
    ): Call<AddToCardResponse?>?

    @POST("removefrom-cart-MR/{id}")
    fun getRemoveToCard(
        @Header("Authorization") token: String?, @Path("id") id: Int,
        @Body request: AddToCardRequest?
    ): Call<AddToCardResponse?>?


    @GET("show-cart-MR")
    fun getShowCard(
        @Header("Authorization") token: String?, @Query("user_id") user_id: Int?
    ): Call<ShowCartResponse?>?

    @POST("add-order-to-cart")
    fun getAddOrderToCart(
        @Header("Authorization") token: String?,
        @Body request: AddOrderToCartRequest?
    ): Call<AddOrderToCartResponse?>?

    @GET("getContacts")
    fun getGetContacts(
        @Header("Authorization") token: String?,
        @Query("status") status: String?,
        @Query("q") q: String?
    ): Call<GetContactsResponse?>?

    @GET("fetchmessagesapi")
    fun getChatList(
        @Header("Authorization") token: String?,
        @Query("id") id: String?
    ): Call<FetchMessagesResponse?>?

    @POST("addTickets")
    fun addTicket(
        @Header("Authorization") token: String?,
        @Body request: AddTicketRequest?
    ): Call<AddTicketRsponse?>?

    @Multipart
    @POST("sendmesage")
    fun SEND_MESSAGES(
        @Header("Authorization") token: String?,
        @Part file: MultipartBody.Part,
        @PartMap send: MutableMap<String, RequestBody>
    ): Call<SendMessagesResponse?>?

    @Multipart
    @POST("sendmesage")
    fun SEND_MESSAGES(
        @Header("Authorization") token: String?,
        @PartMap send: MutableMap<String, RequestBody>
    ): Call<SendMessagesResponse?>?

    @POST("ratetickets")
    fun addRate(
        @Header("Authorization") token: String?,
        @Body request: AddRateRequest?
    ): Call<AddRateResponse?>?

    @GET("deleteTickets")
    fun deleteTicket(
        @Header("Authorization") token: String?,
        @Query("id") id: String?
    ): Call<DeleteTicketsResponse?>?

    @GET("deleteMessages")
    fun deleteMessages(
        @Header("Authorization") token: String?,
        @Query("message_id") message_id: String?
    ): Call<DeleteMessagesResponse?>?

    @GET("videos")
    fun getVideos(
        @Header("Authorization") token: String?
    ): Call<ResponseVideos?>?

    @GET("get-user-details")
    fun getInfoPharmacy(
        @Header("Authorization") token: String?,
        @Query("id") id: String?
    ): Call<GetInfoPharmacyResponse?>?

}