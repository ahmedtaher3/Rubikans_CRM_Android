package com.devartlab.data.retrofit

import com.devartlab.AppConstants
import com.devartlab.model.GoogleRequestResponse
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServicesGoogle {
 
    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun googleSheetRequest(@Query("sheet") sheet: String,
                           @Query("action") action: String,
                           @Query("date") date: String,
                           @Query("id") id: String,
                           @Query("name") name: String,
                           @Query("typeRequest") typeRequest: String,
                           @Query("requestStartIn") requestStartIn: String,
                           @Query("requestEndIn") requestEndIn: String,
                           @Query("notes") notes: String,
                           @Query("managerId") managerId: String,
                           @Query("status") status: String,
                           @Query("comment") comment: String,
                           @Query("approvalDate") approvalDate: String,
                           @Query("code") code: String): Observable<GoogleRequestResponse>


    @POST("${AppConstants.GoogleSheetApiKey}/exec")
    fun approveAll(@Body objects: JsonObject): Observable<GoogleRequestResponse>


    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun meals(@Query("sheet") sheet: String,
              @Query("action") action: String,
              @Query("id") id: String,
              @Query("date") date: String,
              @Query("name") name: String,
              @Query("mealDate") mealDate: String,
              @Query("meal") meal: String,
              @Query("quantity") quantity: String,
              @Query("type") type: String,
              @Query("notes") notes: String,
              @Query("code") code: String,
              @Query("Price") Price: String,
              @Query("receivedAt") receivedAt: String,
              @Query("receivedID") receivedID: String): Observable<GoogleRequestResponse>

    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun workFromHome(@Query("sheet") sheet: String,
                     @Query("action") action: String,
                     @Query("id") id: String,
                     @Query("date") date: String,
                     @Query("name") name: String,
                     @Query("managerId") managerId: String,
                     @Query("notes") notes: String,
                     @Query("status") status: String,
                     @Query("approveDate") approveDate: String,
                     @Query("code") code: String,
                     @Query("endedAt") endedAt: String): Observable<GoogleRequestResponse>

    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun penalties(@Query("sheet") sheet: String,
                  @Query("action") action: String,
                  @Query("id") id: String,
                  @Query("name") name: String,
                  @Query("empId") empId: String,
                  @Query("empName") empName: String,
                  @Query("managerId") managerId: String,
                  @Query("reason") reason: String,
                  @Query("type") type: String,
                  @Query("notes") notes: String,
                  @Query("status") status: String,
                  @Query("approve") approve: String,
                  @Query("approveComment") approveComment: String,
                  @Query("approveDate") approveDate: String,
                  @Query("code") code: String): Observable<GoogleRequestResponse>

    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun room(@Query("sheet") sheet: String,
             @Query("action") action: String,
             @Query("id") id: String,
             @Query("date") date: String,
             @Query("name") name: String,
             @Query("roomId") roomId: String,
             @Query("reservationDate") reservationDate: String,
             @Query("nine") nine: String,
             @Query("nineHalf") nineHalf: String,
             @Query("ten") ten: String,
             @Query("tenHalf") tenHalf: String,
             @Query("eleven") eleven: String,
             @Query("elevenHalf") elevenHalf: String,
             @Query("twelve") twelve: String,
             @Query("twelveHalf") twelveHalf: String,
             @Query("one") one: String,
             @Query("oneHalf") oneHalf: String,
             @Query("two") two: String,
             @Query("twoHalf") twoHalf: String,
             @Query("three") three: String,
             @Query("threeHalf") threeHalf: String,
             @Query("four") four: String,
             @Query("fourHalf") fourHalf: String,
             @Query("five") five: String,
             @Query("fiveHalf") fiveHalf: String,
             @Query("six") six: String,
             @Query("sixHalf") sixHalf: String,
             @Query("code") code: String): Observable<GoogleRequestResponse>

    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun trade(@Query("sheet") sheet: String,
              @Query("action") action: String,
              @Query("governmentId") governmentId: String,
              @Query("cityId") cityId: String,
              @Query("areaId") areaId: String,
              @Query("userName") userName: String,
              @Query("userId") userId: String,
              @Query("array") array: String,
              @Query("customerName") customerName: String,
              @Query("customerId") customerId: String,
              @Query("discount") discount: String,
              @Query("lastPrice") lastPrice: String,
              @Query("totalPrice") totalPrice: String,
              @Query("notes") notes: String): Observable<GoogleRequestResponse>

    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun tradeGetCustomers(@Query("sheet") sheet: String,
                          @Query("action") action: String,
                          @Query("empId") empId: String,
                          @Query("areaId") areaId: String): Observable<GoogleRequestResponse>

    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun tradeAddNewPlace(@Query("sheet") sheet: String,
                         @Query("action") action: String,
                         @Query("governmentId") governmentId: String,
                         @Query("cityId") cityId: String,
                         @Query("governmentName") governmentName: String,
                         @Query("cityName") cityName: String,
                         @Query("areaName") areaName: String): Observable<GoogleRequestResponse>

    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun tradeReport(@Query("sheet") sheet: String,
                    @Query("action") action: String,
                    @Query("empId") empId: String,
                    @Query("startDistance") startDistance: String,
                    @Query("startDayAt") startDayAt: String,
                    @Query("startDayLat") startDayLat: String,
                    @Query("startDayLong") startDayLong: String,
                    @Query("endDistance") endDistance: String,
                    @Query("endDayAt") endDayAt: String,
                    @Query("endDayLat") endDayLat: String,
                    @Query("endDayLong") endDayLong: String,
                    @Query("code") code: String): Observable<GoogleRequestResponse>


    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun leaveWork(@Query("sheet") sheet: String,
                  @Query("action") action: String,
                  @Query("id") id: String,
                  @Query("name") name: String,
                  @Query("leaveDate") leaveDate: String,
                  @Query("managerId") managerId: String,
                  @Query("notes") notes: String,
                  @Query("addedBy") addedBy: String

    ): Observable<GoogleRequestResponse>


    @GET("${AppConstants.GoogleSheetApiKey}/exec")
    fun businessCard(@Query("sheet") sheet: String,
                     @Query("action") action: String,
                     @Query("id") id: String,
                     @Query("name") name: String,
                     @Query("title") title: String,
                     @Query("managerId") managerId: String,
                     @Query("mobile") mobile: String,
                     @Query("email") email: String,
                     @Query("notes") notes: String

    ): Observable<GoogleRequestResponse>


}