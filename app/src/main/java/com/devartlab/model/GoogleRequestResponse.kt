package com.devartlab.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class GoogleRequestResponse {
    @SerializedName("successful")
    @Expose
    var isSuccessful = false

    @SerializedName("workFromHome")
    @Expose
    var workFromHome: WorkFromHome? = null

    @SerializedName("meals")
    @Expose
    var meal: ArrayList<Meal>? = null

    @SerializedName("mealRequests")
    @Expose
    var mealRequests: ArrayList<MealRequest>? = null

    @SerializedName("user")
    @Expose
    var user: ArrayList<GoogleSheetUser>? = null

    @SerializedName("hrRequests")
    @Expose
    var hrRequests: ArrayList<GoogleSheetUser>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("penalties")
    @Expose
    var penaltiesGoogle: ArrayList<PenaltiesGoogle>? = null

    @SerializedName("workFromHomelist")
    @Expose
    var workFromHomelist: ArrayList<WorkFromHomeModel>? = null

    @SerializedName("rooms")
    @Expose
    var rooms: ArrayList<RoomModel>? = null

    @SerializedName("reservations")
    @Expose
    var reservations: ArrayList<RoomReservation>? = null

    @SerializedName("bills")
    @Expose
    var bills: ArrayList<Bill>? = null

    @SerializedName("tradeReport")
    @Expose
    var tradeDay: TradeDay? = null

    @SerializedName("tradeReports")
    @Expose
    var tradeReports: ArrayList<TradeDay>? = null

    @SerializedName("products")
    @Expose
    var products: ArrayList<ProductTrade>? = null

    @SerializedName("areas")
    @Expose
    var areas: ArrayList<AreaTrade>? = null

    @SerializedName("cities")
    @Expose
    var cities: ArrayList<CityTrade>? = null

    @SerializedName("governments")
    @Expose
    var governments: ArrayList<GovernmentTrade>? = null

    @SerializedName("customers")
    @Expose
    var customers: ArrayList<CustomerTrade>? = null

    @SerializedName("customersAcher")
    @Expose
    var customersAchers: ArrayList<CustomerAcher>? = null

    @SerializedName("lat")
    @Expose
    var lat: String? = null

    @SerializedName("lng")
    @Expose
    var lng: String? = null
    override fun toString(): String {
        return "GoogleRequestResponse(isSuccessful=$isSuccessful, workFromHome=$workFromHome, meal=$meal, mealRequests=$mealRequests, user=$user, hrRequests=$hrRequests, message=$message, penaltiesGoogle=$penaltiesGoogle, workFromHomelist=$workFromHomelist, rooms=$rooms, reservations=$reservations, bills=$bills, tradeDay=$tradeDay, tradeReports=$tradeReports, products=$products, areas=$areas, cities=$cities, governments=$governments, customers=$customers, customersAchers=$customersAchers, lat=$lat, lng=$lng)"
    }


}