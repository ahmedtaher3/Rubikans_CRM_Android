package com.devartlab.ui.main.ui.devartlink.calender

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.devartlab.GetMyLocation
import com.devartlab.R
import com.devartlab.databinding.ActivityAddEventBinding
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.LocationUtils
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*


class AddEventActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddEventBinding
    var lat_lng: String? = null
    var labelID: Int = 0
    var createAtID: Int = 0
    var endAtID: Int = 0
    var departmentID: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_event)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.Add_event)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.edArea.setOnClickListener {
            val languageToLoad = "en" // your language
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            openCalendar(binding.edArea)
        }
        binding.edDistrict.setOnClickListener {
            val languageToLoad = "en" // your language
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            openCalendar(binding.edDistrict)
        }

        binding.edTimeCreate.setOnClickListener {
            openTimeDialog(binding.edTimeCreate)
        }

        binding.edTimeEnd.setOnClickListener {
            openTimeDialog(binding.edTimeEnd)
        }

        binding.tvLanLng.setOnClickListener {
            if (LocationUtils.checkPermission(this)) {
                ProgressLoading.showWithText(
                    this,
                    resources.getString(R.string.fetching_your_location)
                )
                val getMyLocation = GetMyLocation(this)
                getMyLocation.getLocation(this, object : GetMyLocation.LocationResult() {
                    override fun gotLocation(location: Location?, msg: String?) {
                        ProgressLoading.dismiss()
                        if (location != null) {

                            if (CommonUtilities.isFake(this@AddEventActivity, location)) {
                                lat_lng = "1" + "," + "1"
                            } else {
                                lat_lng =
                                    location?.latitude.toString() + "," + location?.longitude.toString()
                                binding.edLanLng.setText(
                                    getAddressFromLatLng(
                                        this@AddEventActivity,
                                        location?.latitude,
                                        location?.longitude
                                    )
                                )
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this@AddEventActivity,
                                    resources.getString(R.string.error_location_try_again),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                })

            }
        }
        binding.edCity.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, binding.edCity)
            popupMenu.menuInflater.inflate(R.menu.label_popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_task -> {
                        binding.edCity.setText(R.string.action_task)
                        labelID=1
                    }
                    R.id.action_request -> {
                        binding.edCity.setText(R.string.action_request)
                        labelID=2
                    }
                    R.id.action_event -> {
                        binding.edCity.setText(R.string.action_event)
                        labelID=3
                    }
                    R.id.action_holiday -> {
                        binding.edCity.setText(R.string.action_holiday)
                        labelID=4
                    }
                    R.id.action_meeting_room -> {
                        binding.edCity.setText(R.string.action_meeting_room)
                        labelID=5
                    }
                }
                true
            })
            popupMenu.show()
        }

        binding.btnAdd.setOnClickListener {
            if (TextUtils.isEmpty(binding.edCountry.getText().toString())) {
                binding.edCountry.setError("please enter title")
            } else if (labelID == 0) {
                binding.edCity.setError("please enter label")
            } else if (TextUtils.isEmpty(binding.edArea.getText().toString())) {
                binding.edArea.setError("please enter date")
            } else if (TextUtils.isEmpty(binding.edDistrict.getText().toString())) {
                binding.edDistrict.setError("please enter date")
            }else if (TextUtils.isEmpty(binding.edTimeCreate.getText().toString())) {
                binding.edTimeCreate.setError("please enter time")
            } else if (TextUtils.isEmpty(binding.edTimeEnd.getText().toString())) {
                binding.edTimeEnd.setError("please enter time")
            } else if (TextUtils.isEmpty(binding.edAddress.getText().toString())) {
                binding.edAddress.setError("please enter link event")
            } else if (departmentID == 0) {
                binding.edDepartment.setError("please enter department")
            } else if (TextUtils.isEmpty(binding.edLanLng.getText().toString())) {
                binding.edLanLng.setError("please click to get current location")
            } else if (TextUtils.isEmpty(binding.edDec.getText().toString())) {
                binding.edDec.setError("please enter description")
            } else {

            }
        }
    }

    private fun handleObserver() {
        TODO("Not yet implemented")
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun openCalendar(tv_date: TextView) {
        // Get Current Date
        val c: Calendar = Calendar.getInstance()
        val mYear: Int = c.get(Calendar.YEAR)
        val mMonth: Int = c.get(Calendar.MONTH)
        val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, monthOfYear, dayOfMonth ->
                c.set(year, monthOfYear, dayOfMonth)
                val formatter = SimpleDateFormat("yyyy/MM/dd")
                val strDate: String = formatter.format(c.getTime())
                tv_date.text = strDate
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    fun getAddressFromLatLng(context: Context?, lat: Double, lng: Double): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(context, Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(lat, lng, 1)
            if (addresses[0].getSubThoroughfare() == null && addresses[0].getThoroughfare() == null) {
                Log.e("TAG", "getAddressLine: ")
                addresses[0].getAddressLine(0)
            } else if (addresses[0].getSubThoroughfare() == null) {
                Log.e("TAG", "getSubThoroughfare: ")
                addresses[0].getThoroughfare().toString() + ", " + addresses[0].getAdminArea()
            } else {
                Log.e("TAG", "getAddressFromLatLng: ")
                addresses[0].getSubThoroughfare()
                    .toString() + " " + addresses[0].getThoroughfare() + ", " + addresses[0].getAdminArea()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
    fun openTimeDialog(view: TextView) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this@AddEventActivity,
            { timePicker, selectedHour, selectedMinute ->
                var selectedTime = ""
                var hour = selectedHour.toString()
                var minutes = selectedMinute.toString()
                if (selectedHour < 10) {
                    hour = "0$selectedHour"
                }
                if (selectedMinute < 10) {
                    minutes = "0$selectedMinute"
                }
                selectedTime = if (selectedHour < 13) {
                    if (selectedHour < 1) {
                        "12" + ":" + minutes + " " + "am"
                    } else if (selectedHour > 11) {
                        "12" + ":" + minutes + " " + "pm"
                    } else {
                        hour + ":" + minutes + " " + "am"
                    }
                } else {
                    if (selectedHour > 23) {
                        "12" + ":" + minutes + " " + "am"
                    } else {
                        if (selectedHour - 12 < 10) {
                            "0" + (selectedHour - 12) + ":" + minutes + " " + "pm"
                        } else (selectedHour - 12).toString() + ":" + minutes + " " + "pm"
                    }
                }
                view.text = selectedTime
            }, hour, minute, true
        ) //Yes 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

}