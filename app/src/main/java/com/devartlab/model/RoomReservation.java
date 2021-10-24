package com.devartlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomReservation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("room id")
    @Expose
    private Integer roomId;
    @SerializedName("Reservation date")
    @Expose
    private String reservationDate;
    @SerializedName("9:00")
    @Expose
    private Boolean _900;
    @SerializedName("9:30")
    @Expose
    private Boolean _930;
    @SerializedName("10:00")
    @Expose
    private Boolean _1000;
    @SerializedName("10:30")
    @Expose
    private Boolean _1030;
    @SerializedName("11:00")
    @Expose
    private Boolean _1100;
    @SerializedName("11:30")
    @Expose
    private Boolean _1130;
    @SerializedName("12:00")
    @Expose
    private Boolean _1200;
    @SerializedName("12:30")
    @Expose
    private Boolean _1230;
    @SerializedName("1:00")
    @Expose
    private Boolean _100;
    @SerializedName("1:30")
    @Expose
    private Boolean _130;
    @SerializedName("2:00")
    @Expose
    private Boolean _200;
    @SerializedName("2:30")
    @Expose
    private Boolean _230;
    @SerializedName("3:00")
    @Expose
    private Boolean _300;
    @SerializedName("3:30")
    @Expose
    private Boolean _330;
    @SerializedName("4:00")
    @Expose
    private Boolean _400;
    @SerializedName("4:30")
    @Expose
    private Boolean _430;
    @SerializedName("5:00")
    @Expose
    private Boolean _500;
    @SerializedName("5:30")
    @Expose
    private Boolean _530;
    @SerializedName("6:00")
    @Expose
    private Boolean _600;
    @SerializedName("6:30")
    @Expose
    private Boolean _630;
    @SerializedName("code")
    @Expose
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Boolean get900() {
        return _900;
    }

    public void set900(Boolean _900) {
        this._900 = _900;
    }

    public Boolean get930() {
        return _930;
    }

    public void set930(Boolean _930) {
        this._930 = _930;
    }

    public Boolean get1000() {
        return _1000;
    }

    public void set1000(Boolean _1000) {
        this._1000 = _1000;
    }

    public Boolean get1030() {
        return _1030;
    }

    public void set1030(Boolean _1030) {
        this._1030 = _1030;
    }

    public Boolean get1100() {
        return _1100;
    }

    public void set1100(Boolean _1100) {
        this._1100 = _1100;
    }

    public Boolean get1130() {
        return _1130;
    }

    public void set1130(Boolean _1130) {
        this._1130 = _1130;
    }

    public Boolean get1200() {
        return _1200;
    }

    public void set1200(Boolean _1200) {
        this._1200 = _1200;
    }

    public Boolean get1230() {
        return _1230;
    }

    public void set1230(Boolean _1230) {
        this._1230 = _1230;
    }

    public Boolean get100() {
        return _100;
    }

    public void set100(Boolean _100) {
        this._100 = _100;
    }

    public Boolean get130() {
        return _130;
    }

    public void set130(Boolean _130) {
        this._130 = _130;
    }

    public Boolean get200() {
        return _200;
    }

    public void set200(Boolean _200) {
        this._200 = _200;
    }

    public Boolean get230() {
        return _230;
    }

    public void set230(Boolean _230) {
        this._230 = _230;
    }

    public Boolean get300() {
        return _300;
    }

    public void set300(Boolean _300) {
        this._300 = _300;
    }

    public Boolean get330() {
        return _330;
    }

    public void set330(Boolean _330) {
        this._330 = _330;
    }

    public Boolean get400() {
        return _400;
    }

    public void set400(Boolean _400) {
        this._400 = _400;
    }

    public Boolean get430() {
        return _430;
    }

    public void set430(Boolean _430) {
        this._430 = _430;
    }

    public Boolean get500() {
        return _500;
    }

    public void set500(Boolean _500) {
        this._500 = _500;
    }

    public Boolean get530() {
        return _530;
    }

    public void set530(Boolean _530) {
        this._530 = _530;
    }

    public Boolean get600() {
        return _600;
    }

    public void set600(Boolean _600) {
        this._600 = _600;
    }

    public Boolean get630() {
        return _630;
    }

    public void set630(Boolean _630) {
        this._630 = _630;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}