package com.devartlab.ui.main.ui.eShopping.pharmacyBinding.addLocation

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devartlab.GetMyLocation
import com.devartlab.R
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.AddLocationViewModel
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress.UpdateAddressRequest
import com.devartlab.databinding.ActivityAddLocationBinding
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.LocationUtils
import com.devartlab.utils.ProgressLoading
import android.location.Geocoder
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.PharmacyBindingActivity
import java.lang.Exception
import java.util.*


class AddLocationActivity : AppCompatActivity() {
    private var idPharmacies: String? = null
    lateinit var binding: ActivityAddLocationBinding
    var viewModel: AddLocationViewModel? = null
    private var countryID: Int = 0
    private var cityID: Int = 0
    private var areaID: Int = 0
    private var districtID: Int = 0
    var lat_lng: String? = null
    private lateinit var updateAddressRequest: UpdateAddressRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_location
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.add_location)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("pharmacies_id")) {
            idPharmacies = intent.getStringExtra("pharmacies_id")
        }
        viewModel = ViewModelProvider(this)[AddLocationViewModel::class.java]
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getUserAddress(idPharmacies!!)
        binding.edCountry.setOnClickListener {
            viewModel!!.getCountry()
        }
        binding.edCity.setOnClickListener {
            viewModel!!.getCities(countryID.toString())
        }
        binding.edArea.setOnClickListener {
            viewModel!!.getAreas(cityID.toString())
        }
        binding.edDistrict.setOnClickListener {
            viewModel!!.getDistricts(areaID.toString())
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

                            if (CommonUtilities.isFake(this@AddLocationActivity, location)) {
                                lat_lng = "1" + "," + "1"
                            } else {
                                lat_lng = location?.latitude.toString() + "," + location?.longitude.toString()
                                binding.edLanLng.text = getAddressFromLatLng(this@AddLocationActivity,location?.latitude,location?.longitude)
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this@AddLocationActivity,
                                    resources.getString(R.string.error_location_try_again),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                })

            }
        }
        binding.btnUpdate.setOnClickListener {
            when {
                countryID == 0 -> {
                    binding.edCountry.error = "please enter country"
                }
                cityID == 0 -> {
                    binding.edCity.error = "please enter city"
                }
                areaID == 0 -> {
                    binding.edCity.error = "please enter area"
                }
                districtID == 0 -> {
                    binding.edCity.error = "please enter district"
                }
                TextUtils.isEmpty(binding.edAddress.text.toString()) -> {
                    binding.edCity.error = "please enter address"
                }
                lat_lng == null -> {
                    binding.edLanLng.error = "please click to get current location"
                }
                else -> {
                    updateAddressRequest = UpdateAddressRequest(
                        idPharmacies,
                        countryID,
                        cityID,
                        areaID,
                        districtID,
                        binding.edAddress.text.toString(),
                        lat_lng!!
                    )
                    viewModel!!.updateAddress(updateAddressRequest)
                }
            }
        }
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.CountryResponse.observe(this) {

            val countryBrandsPopUp = PopupMenu(this, binding.edCountry)
            for (i in 0 until it!!.size) {
                countryBrandsPopUp.menu.add(i, i, i, it[i].country_name)
            }
            countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edCountry.text = it[item.itemId].country_name
                countryID = it[item.itemId].id
                binding.edCity.setText(R.string.select)
                binding.edArea.setText(R.string.select)
                binding.edDistrict.setText(R.string.select)
                Log.e("popupCarBrands", "onMenuItemClick: $countryID")
                return@OnMenuItemClickListener false
            })
            countryBrandsPopUp.show()
        }
        viewModel!!.citiesResponse.observe(this) {

            val citiesBrandsPopUp = PopupMenu(this, binding.edCity)
            for (i in 0 until it!!.data.size) {
                citiesBrandsPopUp.menu.add(i, i, i, it.data[i].name)
            }
            citiesBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edCity.text = it.data[item.itemId].name
                cityID = it.data[item.itemId].id
                binding.edArea.setText(R.string.select)
                binding.edDistrict.setText(R.string.select)
                Log.e("popupCarBrands", "onMenuItemClick: $cityID")
                return@OnMenuItemClickListener false
            })
            citiesBrandsPopUp.show()
        }
        viewModel!!.areasResponse.observe(this) {

            val areasBrandsPopUp = PopupMenu(this, binding.edArea)
            for (i in 0 until it!!.data.size) {
                areasBrandsPopUp.menu.add(i, i, i, it.data[i].name)
            }
            areasBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edArea.text = it.data[item.itemId].name
                areaID = it.data[item.itemId].id
                binding.edDistrict.setText(R.string.select)
                Log.e("popupCarBrands", "onMenuItemClick: $areaID")
                return@OnMenuItemClickListener false
            })
            areasBrandsPopUp.show()
        }
        viewModel!!.districtsResponse.observe(this) {

            val districtsBrandsPopUp = PopupMenu(this, binding.edDistrict)
            for (i in 0 until it!!.data.size) {
                districtsBrandsPopUp.menu.add(i, i, i, it.data[i].name)
            }
            districtsBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edDistrict.text = it.data[item.itemId].name
                districtID = it.data[item.itemId].id
                Log.e("popupCarBrands", "onMenuItemClick: $districtID")
                return@OnMenuItemClickListener false
            })
            districtsBrandsPopUp.show()
        }
        viewModel!!.UpdateAddressResponse.observe(this) {
            if (it!!.message) {
                Toast.makeText(this, " تمت اضافة بنجاح", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, PharmacyBindingActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.getUserAddressResponse.observe(this) {
            binding.progressBar.visibility = View.GONE
            if (it != null) {
                binding.edCountry.text = it.country_name
                binding.edCity.text = it.city_name
                binding.edArea.text = it.area_name
                binding.edDistrict.text = it.district_name
                binding.edAddress.setText(it.fulladdress)
                if (it.lat_lng != null) {
                    val strs = it.lat_lng.split(",").toTypedArray()
                    val lat: Double = strs[0].toDouble()
                    val lng: Double = strs[1].toDouble()
                    binding.edLanLng.text = getAddressFromLatLng(
                        this@AddLocationActivity,
                        lat,
                        lng
                    )
                }
                countryID = it.country_id
                cityID = it.city_id
                areaID = it.area_id
                districtID = it.district_id
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    fun getAddressFromLatLng(context: Context?, lat: Double, lng: Double): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(context, Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(lat, lng, 1)
            if (addresses[0].subThoroughfare == null && addresses[0].thoroughfare == null) {
                Log.e("TAG", "getAddressLine: ")
                addresses[0].getAddressLine(0)
            } else if (addresses[0].subThoroughfare == null) {
                Log.e("TAG", "getSubThoroughfare: ")
                addresses[0].thoroughfare.toString() + ", " + addresses[0].adminArea
            } else {
                Log.e("TAG", "getAddressFromLatLng: ")
                addresses[0].subThoroughfare
                    .toString() + " " + addresses[0].thoroughfare + ", " + addresses[0].adminArea
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}