package com.devartlab.ui.main.ui.eShopping.PharmacyBinding.addLocation

import android.content.Intent
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
import com.devartlab.R
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.AddLocationViewModel
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress.UpdateAddressRequest
import com.devartlab.databinding.ActivityAddLocationBinding
import com.devartlab.ui.main.ui.a4eshopping.main.Home4EShoppingActivity

class AddLocationActivity : AppCompatActivity() {
    var idPharmacies: String? = null
    lateinit var binding: ActivityAddLocationBinding
    var viewModel: AddLocationViewModel? = null
    var countryID: Int = 0
    var cityID: Int = 0
    var areaID: Int = 0
    var districtID: Int = 0
    lateinit var updateAddressRequest: UpdateAddressRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_location)
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
            binding.edCity.setText("")
            binding.edArea.setText("")
            binding.edDistrict.setText("")
            viewModel!!.getCountry()
        }
        binding.edCity.setOnClickListener {
            binding.edArea.setText("")
            binding.edDistrict.setText("")
            viewModel!!.getCities(countryID.toString())
        }
        binding.edArea.setOnClickListener {
            binding.edDistrict.setText("")
            viewModel!!.getAreas(cityID.toString())
        }
        binding.edDistrict.setOnClickListener {
            viewModel!!.getDistricts(areaID.toString())
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
            } else {
                updateAddressRequest = UpdateAddressRequest(
                    idPharmacies,
                    countryID,
                    cityID,
                    areaID,
                    districtID,
                    binding.edAddress.getText().toString()
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
            val intent = Intent(this, Home4EShoppingActivity::class.java)
            startActivity(intent)
        })
        viewModel!!.getUserAddressResponse.observe(this, Observer {
            binding.progressBar.setVisibility(View.GONE)
            if (it != null) {
                binding.edCountry.setText(it.country_name)
                binding.edCity.setText(it.city_name)
                binding.edArea.setText(it.area_name)
                binding.edDistrict.setText(it.district_name)
                binding.edAddress.setText(it.fulladdress)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}