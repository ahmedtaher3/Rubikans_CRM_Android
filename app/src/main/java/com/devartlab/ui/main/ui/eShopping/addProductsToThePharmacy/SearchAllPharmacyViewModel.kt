package com.devartlab.ui.main.ui.eShopping.addProductsToThePharmacy

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.Pharmacy.CategoryPharmacyResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardResponse
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.searchAllPharmacy.SearchAllPharmacyResponse
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.ui.main.ui.eShopping.addProductsToThePharmacy.model.showCart.ShowCartResponse
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
    var showCartResponse: MutableLiveData<ShowCartResponse?>
        protected set
    var dataManager: DataManager

    fun getSearchAllPharmacy(q: String) {
        RetrofitClient(dataManager).apis4EShopping
            .getSearchAllPharmacy("Bearer " + UserPreferenceHelper.getUser().token,q)!!
            .enqueue(object : Callback<SearchAllPharmacyResponse?> {
                override fun onResponse(
                    call: Call<SearchAllPharmacyResponse?>,
                    response: Response<SearchAllPharmacyResponse?>
                ) {
                    if (response.isSuccessful) {
                        SearchAllPharmacyResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SearchAllPharmacyResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getCategoryv2Pharmacy(type_code: String) {
        RetrofitClient(dataManager).apis4EShopping
            .getCategoryv2Pharmacy("Bearer " + UserPreferenceHelper.getUser().token,type_code)!!
            .enqueue(object : Callback<CategoryPharmacyResponse?> {
                override fun onResponse(
                    call: Call<CategoryPharmacyResponse?>,
                    response: Response<CategoryPharmacyResponse?>
                ) {
                    if (response.isSuccessful) {
                        categoryPharmacyResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CategoryPharmacyResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getAddOrderToCart(request: AddOrderToCartRequest) {
        RetrofitClient(dataManager).apis4EShopping
            .getAddOrderToCart("Bearer " + UserPreferenceHelper.getUser().token,request)!!
            .enqueue(object : Callback<AddOrderToCartResponse?> {
                override fun onResponse(
                    call: Call<AddOrderToCartResponse?>,
                    response: Response<AddOrderToCartResponse?>
                ) {
                    if (response.isSuccessful) {
                        addOrderToCartResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AddOrderToCartResponse?>, t: Throwable) {
                    errorMessage.postValue(1)
                }
            })
    }

    fun getShowCard(user_id: Int) {
        RetrofitClient(dataManager).apis4EShopping
            .getShowCard("Bearer " + UserPreferenceHelper.getUser().token,user_id)!!
            .enqueue(object : Callback<ShowCartResponse?> {
                override fun onResponse(
                    call: Call<ShowCartResponse?>,
                    response: Response<ShowCartResponse?>
                ) {
                    if (response.isSuccessful) {
                        showCartResponse.postValue(response.body())
                    } else {
                        Toast.makeText(getApplication(), "error in response data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ShowCartResponse?>, t: Throwable) {
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
        showCartResponse= MutableLiveData()
        dataManager = (getApplication() as BaseApplication).dataManager!!
    }
}