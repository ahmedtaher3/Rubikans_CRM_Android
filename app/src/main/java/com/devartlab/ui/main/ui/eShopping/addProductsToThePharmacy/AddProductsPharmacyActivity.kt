package com.devartlab.a4eshopping.addProductsToThePharmacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.a4eshopping.PharmacyBinding.addLocation.AddLocationViewModel
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.Cart
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import android.os.Looper
import com.devartlab.R
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.Pharmacy.Prod
import com.devartlab.a4eshopping.orientationVideos.model.Item
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.databinding.ActivityAddProductsPharmacyBinding
import com.devartlab.ui.main.ui.a4eshopping.main.Home4EShoppingActivity
import com.devartlab.ui.main.ui.eShopping.addProductsToThePharmacy.AddProductsPharmaciesAdapter
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper


class AddProductsPharmacyActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddProductsPharmacyBinding
    private var adapter: AddProductsPharmaciesAdapter? = null
    var viewModel: SearchAllPharmacyViewModel? = null
    var cart: List<Cart> = ArrayList<Cart>()
    val list = ArrayList<Prod>()
    lateinit var request: AddOrderToCartRequest
    var addToCardRequest: AddToCardRequest? = null
    var pharmacyID: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_products_pharmacy
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.Add_products_to_Pharmacy)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = AddProductsPharmaciesAdapter(null)
        binding.recyclerDetailsPharmacies.setNestedScrollingEnabled(false)
        binding.recyclerDetailsPharmacies.setHasFixedSize(false);
        viewModel = ViewModelProvider(this).get(SearchAllPharmacyViewModel::class.java)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getCategoryv2Pharmacy()
        viewModel!!.getSearchAllPharmacy("")
        binding.searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
                binding.ivRemoveSearch.setVisibility(View.VISIBLE)
            }
        })
        binding.ivRemoveSearch.setOnClickListener {
            binding.edPharmacyNo.setHint("...............")
            binding.edPharmacyName.setHint("...............")
            binding.tvTotalMoney.setText("0")
            binding.tvTotalCoinssss.setText("0")
            viewModel!!.getSearchAllPharmacy("")
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
        binding.edPharmacySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel!!.getSearchAllPharmacy(binding.edPharmacySearch.text.toString())
            }
            override fun afterTextChanged(editable: Editable) {
            }
        })
        binding.btnAddToPharmacy.setOnClickListener {
            if (cart.size == 0) {
                Toast.makeText(this, "please add product", Toast.LENGTH_SHORT)
                    .show()
            } else {
                request = AddOrderToCartRequest(cart, pharmacyID)
                viewModel!!.getAddOrderToCart(request)
            }
        }
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.SearchAllPharmacyResponse.observe(this, Observer {
            val countryBrandsPopUp = PopupMenu(this, binding.edPharmacySearch)
            for (i in 0 until it!!.data.size) {
                countryBrandsPopUp.getMenu()
                    .add(i, i, i, it.data.get(i).id.toString() + it.data.get(i).name)
            }
            countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                binding.edPharmacySearch
                    .setText(it.data.get(item.getItemId()).id.toString() + it.data.get(item.getItemId()).name)
                binding.edPharmacyName.setText(it.data.get(item.getItemId()).name)
                binding.edPharmacyNo.setText(it.data.get(item.getItemId()).id.toString())
                pharmacyID = it.data.get(item.getItemId()).id.toInt()
                binding.btnAddToPharmacy.setVisibility(View.VISIBLE)
                binding.searchBarVideo.setVisibility(View.VISIBLE)
                binding.edPharmacyNo.setHint("...............")
                binding.edPharmacyName.setHint("...............")
                binding.tvTotalMoney.setText("0")
                binding.tvTotalCoinssss.setText("0")
                viewModel!!.getSearchAllPharmacy("")
                binding.recyclerDetailsPharmacies.setVisibility(View.VISIBLE)
                countryBrandsPopUp.dismiss()
                Log.e("popupCarBrands", "onMenuItemClick: $pharmacyID")
                return@OnMenuItemClickListener false
            })
            countryBrandsPopUp.show()
        })
        viewModel!!.categoryPharmacyResponse.observe(this, Observer {
            if (it!!.data == null) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
                binding.progressBar.setVisibility(View.GONE)
            } else {
                //show data in recyclerView
                addToCardRequest = AddToCardRequest("mr")
                binding.progressBar.setVisibility(View.GONE)
                adapter = AddProductsPharmaciesAdapter(it.data.prods)
                binding.recyclerDetailsPharmacies.setAdapter(adapter)
                list.addAll(it.data.prods)
                adapter!!.setOnItemClickListener(AddProductsPharmaciesAdapter.OnItemClickListener { pos, id, amount ->
                    RetrofitClient.getApis4EShopping().getAddToCard(
                        "Bearer " + UserPreferenceHelper.getUser().token,
                        id, amount,
                        addToCardRequest
                    )!!
                        .enqueue(object : Callback<AddToCardResponse?> {
                            override fun onResponse(
                                call: Call<AddToCardResponse?>,
                                response: Response<AddToCardResponse?>
                            ) {
                                if (response.isSuccessful) {
                                    binding.tvTotalMoney.setText(response.body()!!.totalPrice.toString())
                                    binding.tvTotalCoinssss.setText(response.body()!!.totalCoins.toString())
                                    (cart as ArrayList<Cart>).add(Cart(id, amount))
                                }
                            }

                            override fun onFailure(call: Call<AddToCardResponse?>, t: Throwable) {
                                Log.e("xssxx", t.message.toString())
                            }
                        })
                })
                adapter!!.setOnItemClickListener2(AddProductsPharmaciesAdapter.OnItemClickListener2 { pos, id, amount ->
                    RetrofitClient.getApis4EShopping().getRemoveToCard(
                        "Bearer " + UserPreferenceHelper.getUser().token,
                        id,
                        addToCardRequest
                    )!!.enqueue(object : Callback<AddToCardResponse?> {
                        override fun onResponse(
                            call: Call<AddToCardResponse?>,
                            response: Response<AddToCardResponse?>
                        ) {
                            if (response.isSuccessful) {
                                binding.tvTotalMoney.setText(response.body()!!.totalPrice.toString())
                                binding.tvTotalCoinssss.setText(response.body()!!.totalCoins.toString())
                                (cart as ArrayList<Cart>).remove(Cart(id, amount))
                            }
                        }

                        override fun onFailure(call: Call<AddToCardResponse?>, t: Throwable) {
                            //   errorMessage.postValue(1);
                        }
                    })
                })
            }
        })
        viewModel!!.addOrderToCartResponse.observe(this, Observer {
            if (it!!.success == 200) {
                Toast.makeText(this, " تمت اضافة المنتجات للصيدليه بنجاح", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, Home4EShoppingActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    private fun refresh() {
        synchronized(this) {
            viewModel!!.getCategoryv2Pharmacy()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Prod> = ArrayList()

        for (item in list) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
                Log.e("xxx", item.toString())
            }
        }
        adapter!!.filterData(filteredList)
    }

}