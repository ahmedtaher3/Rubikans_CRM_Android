package com.devartlab.ui.main.ui.eShopping.PharmacyBinding.addLocation

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.GetMyLocation
import com.devartlab.R
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.AddLocationViewModel
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress.UpdateAddressRequest
import com.devartlab.databinding.ActivityAddLocationBinding
import com.devartlab.ui.main.ui.a4eshopping.main.Home4EShoppingActivity
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.LocationUtils
import com.devartlab.utils.ProgressLoading
import kotlinx.android.synthetic.main.plan_item.*
import android.location.Geocoder
import com.devartlab.ui.main.ui.eShopping.PharmacyBinding.PharmacyBindingActivity
import java.lang.Exception
import java.util.*


class AddLocationActivity : AppCompatActivity() {
    var idPharmacies: String? = null
    lateinit var binding: ActivityAddLocationBinding
    var viewModel: AddLocationViewModel? = null
    var countryID: Int = 0
    var cityID: Int = 0
    var areaID: Int = 0
    var districtID: Int = 0
    var lat_lng: String? = null
    lateinit var updateAddressRequest: UpdateAddressRequest
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
        viewModel = ViewModelProvider(this).get(AddLocationViewModel::class.java)
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
                                binding.edLanLng.setText(getAddressFromLatLng(this@AddLocationActivity,location?.latitude,location?.longitude))
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
            if (countryID == 0) {
                binding.edCountry.setError("please enter country")
            } else if (cityID == 0) {
                binding.edCity.setError("please enter city")
            } else if (areaID == 0) {
                binding.edCity.setError("please enter area")
            } else if (districtID == 0) {
                binding.edCity.setError("please enter district")
            } else if (TextUtils.isEmpty(binding.edAddress.getText().toString())) {
                binding.edCity.setError("please enter address")
            } else if (lat_lng == null) {
                binding.edLanLng.setError("please click to get current location")
            } else {
                updateAddressRequest = UpdateAddressRequest(
                    idPharmacies,
                    countryID,
                    cityID,
                    areaID,
                    districtID,
                    binding.edAddress.getText().toString(),
                    lat_lng!!
                )
                viewModel!!.updateAddress(updateAddressRequest)
            }
        }
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.CountryResponse.observe(this, Observer {

            val countryBrandsPopUp = PopupMenu(this, binding.edCountry)
            for (i in 0 until it!!.size) {
                countryBrandsPopUp.getMenu().add(i, i, i, it.get(i).country_name)
            }
            countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edCountry
                    .setText(it.get(item.getItemId()).country_name)
                countryID = it.get(item.getItemId()).id.toInt()
                binding.edCity.setText(R.string.select)
                binding.edArea.setText(R.string.select)
                binding.edDistrict.setText(R.string.select)
                Log.e("popupCarBrands", "onMenuItemClick: $countryID")
                return@OnMenuItemClickListener false
            })
            countryBrandsPopUp.show()
        })
        viewModel!!.citiesResponse.observe(this, Observer {

            val citiesBrandsPopUp = PopupMenu(this, binding.edCity)
            for (i in 0 until it!!.data.size) {
                citiesBrandsPopUp.getMenu().add(i, i, i, it.data.get(i).name)
            }
            citiesBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edCity
                    .setText(it.data.get(item.getItemId()).name)
                cityID = it.data.get(item.getItemId()).id.toInt()
                binding.edArea.setText(R.string.select)
                binding.edDistrict.setText(R.string.select)
                Log.e("popupCarBrands", "onMenuItemClick: $cityID")
                return@OnMenuItemClickListener false
            })
            citiesBrandsPopUp.show()
        })
        viewModel!!.areasResponse.observe(this, Observer {

            val areasBrandsPopUp = PopupMenu(this, binding.edArea)
            for (i in 0 until it!!.data.size) {
                areasBrandsPopUp.getMenu().add(i, i, i, it.data.get(i).name)
            }
            areasBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edArea
                    .setText(it.data.get(item.getItemId()).name)
                areaID = it.data.get(item.getItemId()).id.toInt()
                binding.edDistrict.setText(R.string.select)
                Log.e("popupCarBrands", "onMenuItemClick: $areaID")
                return@OnMenuItemClickListener false
            })
            areasBrandsPopUp.show()
        })
        viewModel!!.districtsResponse.observe(this, Observer {

            val districtsBrandsPopUp = PopupMenu(this, binding.edDistrict)
            for (i in 0 until it!!.data.size) {
                districtsBrandsPopUp.getMenu().add(i, i, i, it.data.get(i).name)
            }
            districtsBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edDistrict
                    .setText(it.data.get(item.getItemId()).name)
                districtID = it.data.get(item.getItemId()).id.toInt()
                Log.e("popupCarBrands", "onMenuItemClick: $districtID")
                return@OnMenuItemClickListener false
            })
            districtsBrandsPopUp.show()
        })
        viewModel!!.UpdateAddressResponse.observe(this, Observer {
            if (it!!.message) {
                Toast.makeText(this, " تمت اضافة بنجاح", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, PharmacyBindingActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.getUserAddressResponse.observe(this, Observer {
            binding.progressBar.setVisibility(View.GONE)
            if (it != null) {
                binding.edCountry.setText(it.country_name)
                binding.edCity.setText(it.city_name)
                binding.edArea.setText(it.area_name)
                binding.edDistrict.setText(it.district_name)
                binding.edAddress.setText(it.fulladdress)
                if (it.lat_lng!=null){
                    val strs = it.lat_lng.split(",").toTypedArray()
                    val lat:Double=strs[0].toDouble()
                    val lng:Double=strs[1].toDouble()
                    binding.edLanLng.setText(getAddressFromLatLng(this@AddLocationActivity,lat,lng))
                }
                countryID = it.country_id
                cityID = it.city_id
                areaID = it.area_id
                districtID = it.district_id
            }
        })
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
}