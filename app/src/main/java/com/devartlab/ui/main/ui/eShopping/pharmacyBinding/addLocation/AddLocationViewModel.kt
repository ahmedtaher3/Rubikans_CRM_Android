package com.devartlab.a4eshopping.PharmacyBinding.addLocation

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.areas.AreasResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.cities.CitiesResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.country.CountryResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.districts.DistrictsResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.getUserAddress.GetUserAddressResponse
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress.UpdateAddressRequest
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.model.updateAddress.UpdateAddressResponse
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddLocationViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var CountryResponse: MutableLiveData<CountryResponse?>
        protected set
    var citiesResponse: MutableLiveData<CitiesResponse?>
        protected set
    var areasResponse: MutableLiveData<AreasResponse?>
        protected set
    var districtsResponse: MutableLiveData<DistrictsResponse?>
        protected set
    var UpdateAddressResponse: MutableLiveData<UpdateAddressResponse?>
        protected set
    var getUserAddressResponse: MutableLiveData<GetUserAddressResponse?>
        protected set

    fun getCountry() {
        RetrofitClient.getApis4EShopping().getCountry()!!
            .enqueue(object : Callback<CountryResponse?> {
                override fun onResponse(
                    call: Call<CountryResponse?>,
                    response: Response<CountryResponse?>
                ) {
                    if (response.isSuccessful) {
                        CountryResponse.postValue(response.body())
                    } else {
                        CountryResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<CountryResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getCities(country_id: String) {
        RetrofitClient.getApis4EShopping().getCities(country_id)!!
            .enqueue(object : Callback<CitiesResponse?> {
                override fun onResponse(
                    call: Call<CitiesResponse?>,
                    response: Response<CitiesResponse?>
                ) {
                    if (response.isSuccessful) {
                        citiesResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CitiesResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getAreas(areas: String) {
        RetrofitClient.getApis4EShopping().getAreas(areas)!!
            .enqueue(object : Callback<AreasResponse?> {
                override fun onResponse(
                    call: Call<AreasResponse?>,
                    response: Response<AreasResponse?>
                ) {
                    if (response.isSuccessful) {
                        areasResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AreasResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getDistricts(districts: String) {
        RetrofitClient.getApis4EShopping().getDistricts(districts)!!
            .enqueue(object : Callback<DistrictsResponse?> {
                override fun onResponse(
                    call: Call<DistrictsResponse?>,
                    response: Response<DistrictsResponse?>
                ) {
                    if (response.isSuccessful) {
                        districtsResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DistrictsResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun updateAddress(request: UpdateAddressRequest) {
        RetrofitClient.getApis4EShopping().updateAddress("Bearer "+ UserPreferenceHelper.getUser().token, request)!!
            .enqueue(object : Callback<UpdateAddressResponse?> {
                override fun onResponse(
                    call: Call<UpdateAddressResponse?>,
                    response: Response<UpdateAddressResponse?>
                ) {
                    if (response.isSuccessful) {
                        UpdateAddressResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UpdateAddressResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getUserAddress(id: String) {
        RetrofitClient.getApis4EShopping().getUserAddress("Bearer "+UserPreferenceHelper.getUser().token, id)!!
            .enqueue(object : Callback<GetUserAddressResponse?> {
                override fun onResponse(
                    call: Call<GetUserAddressResponse?>,
                    response: Response<GetUserAddressResponse?>
                ) {
                    if (response.isSuccessful) {
                        getUserAddressResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetUserAddressResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        errorMessage = MutableLiveData()
        CountryResponse = MutableLiveData()
        citiesResponse = MutableLiveData()
        areasResponse = MutableLiveData()
        districtsResponse = MutableLiveData()
        UpdateAddressResponse = MutableLiveData()
        getUserAddressResponse = MutableLiveData()
    }
}