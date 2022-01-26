package com.devartlab.a4eshopping.addProductsToThePharmacy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.Pharmacy.CategoryPharmacyResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.searchAllPharmacy.SearchAllPharmacyResponse
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchAllPharmacyViewModel(application: Application) : AndroidViewModel(application) {
    var errorMessage: MutableLiveData<Int>
        protected set
    var SearchAllPharmacyResponse: MutableLiveData<SearchAllPharmacyResponse?>
        protected set
    var categoryPharmacyResponse: MutableLiveData<CategoryPharmacyResponse?>
        protected set
    var addToCardResponse: MutableLiveData<AddToCardResponse?>
        protected set
    var addOrderToCartResponse: MutableLiveData<AddOrderToCartResponse?>
        protected set

    fun getSearchAllPharmacy(q: String) {
        RetrofitClient.getApis4EShopping()
            .getSearchAllPharmacy("Bearer " + UserPreferenceHelper.getUser().token,q)!!
            .enqueue(object : Callback<SearchAllPharmacyResponse?> {
                override fun onResponse(
                    call: Call<SearchAllPharmacyResponse?>,
                    response: Response<SearchAllPharmacyResponse?>
                ) {
                    if (response.isSuccessful) {
                        SearchAllPharmacyResponse.postValue(response.body())
                    } else {
                        SearchAllPharmacyResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<SearchAllPharmacyResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getCategoryv2Pharmacy(type_code: String) {
        RetrofitClient.getApis4EShopping()
            .getCategoryv2Pharmacy("Bearer " + UserPreferenceHelper.getUser().token,type_code)!!
            .enqueue(object : Callback<CategoryPharmacyResponse?> {
                override fun onResponse(
                    call: Call<CategoryPharmacyResponse?>,
                    response: Response<CategoryPharmacyResponse?>
                ) {
                    if (response.isSuccessful) {
                        categoryPharmacyResponse.postValue(response.body())
                    } else {
                        categoryPharmacyResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<CategoryPharmacyResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getAddOrderToCart(request: AddOrderToCartRequest) {
        RetrofitClient.getApis4EShopping()
            .getAddOrderToCart("Bearer " + UserPreferenceHelper.getUser().token,request)!!
            .enqueue(object : Callback<AddOrderToCartResponse?> {
                override fun onResponse(
                    call: Call<AddOrderToCartResponse?>,
                    response: Response<AddOrderToCartResponse?>
                ) {
                    if (response.isSuccessful) {
                        addOrderToCartResponse.postValue(response.body())
                    } else {
                        addOrderToCartResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<AddOrderToCartResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    init {
        SearchAllPharmacyResponse = MutableLiveData()
        errorMessage = MutableLiveData()
        categoryPharmacyResponse = MutableLiveData()
        addToCardResponse = MutableLiveData()
        addOrderToCartResponse= MutableLiveData()
    }
}