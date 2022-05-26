package com.devartlab.ui.main.ui.eShopping.addProductsToThePharmacy

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.Pharmacy.Prod
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.AddOrderToCartRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addOrderToCart.Cart
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardRequest
import com.devartlab.a4eshopping.addProductsToThePharmacy.model.addProduct.AddToCardResponse
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.databinding.ActivityAddProductsPharmacyBinding
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingActivity
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AddProductsPharmacyActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddProductsPharmacyBinding
    private var adapter: AddProductsPharmaciesAdapter? = null
    var viewModel: SearchAllPharmacyViewModel? = null
    var cart: List<Cart> = ArrayList<Cart>()
    val list = ArrayList<Prod>()
    var no_product: Int = 0
    var id_pharmacies: Int = 0
    lateinit var request: AddOrderToCartRequest
    var addToCardRequest: AddToCardRequest? = null
    private var pharmacyID: Int = 0
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
        viewModel = ViewModelProvider(this)[SearchAllPharmacyViewModel::class.java]
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getSearchAllPharmacy("")
        binding.searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
        binding.edPharmacySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                viewModel!!.getSearchAllPharmacy(binding.edPharmacySearch.text.toString())
                binding.ivRemoveSearch.visibility = View.VISIBLE
            }
        })

        binding.ivRemoveSearch.setOnClickListener {
            binding.edPharmacyNo.hint = "..............."
            binding.edPharmacyName.hint = "..............."
            binding.edPharmacyNo.text = null
            binding.edPharmacyName.text = null
            binding.tvTotalMoney.text = "0"
            binding.tvTotalCoinssss.text = "0"
            binding.tvTotalRoi.text = "0%"
            binding.tvAddToCard.text = "0"
            binding.edPharmacySearch.setHint(R.string.name_no_pharmacy_search)
            binding.edPharmacySearch.text = null
        }
        binding.btnAddToPharmacy.setOnClickListener {
            when {
                cart.isEmpty() -> {
                    Toast.makeText(this, "please add product", Toast.LENGTH_SHORT)
                        .show()
                }
                pharmacyID == 0 -> {
                    Toast.makeText(this, "please choose pharmacy", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    request = AddOrderToCartRequest(cart, pharmacyID)
                    viewModel!!.getAddOrderToCart(request)
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
        viewModel!!.SearchAllPharmacyResponse.observe(this) {
            when {
                it!!.code == 401 -> {
                    viewModel!!.dataManager.clear()
                    UserPreferenceHelper.clean()
                    Toast.makeText(this, "please login again", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                it.data.isNullOrEmpty() -> {
                    //errorMessage if data coming is null;
                    binding.tvEmptyList.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    val countryBrandsPopUp = PopupMenu(this, binding.edPharmacySearch)
                    for (i in 0 until it.data.size) {
                        countryBrandsPopUp.menu
                            .add(i, i, i, it.data[i].id.toString() + it.data[i].name)
                    }
                    countryBrandsPopUp.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                        binding.edPharmacyName.text = it.data[item.itemId].name
                        binding.edPharmacyNo.text = it.data[item.itemId].id.toString()
                        viewModel!!.getCategoryv2Pharmacy(it.data[item.itemId].type_code)
                        id_pharmacies = it.data[item.itemId].id
                        binding.tvTotalMoney.text = "0"
                        binding.tvTotalCoinssss.text = "0"
                        binding.tvTotalRoi.text = "0%"
                        binding.tvAddToCard.text = "0"
                        no_product = 0
                        pharmacyID = it.data[item.itemId].id
                        return@OnMenuItemClickListener false
                    })
                    countryBrandsPopUp.show()
                }
            }
        }
        viewModel!!.categoryPharmacyResponse.observe(this) {
            if (it!!.data.prods.isEmpty()) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else {
                //show data in recyclerView
                addToCardRequest = AddToCardRequest("mr", no_product, id_pharmacies)
                binding.progressBar.visibility = View.GONE
                adapter = AddProductsPharmaciesAdapter(it.data.prods)
                binding.recyclerDetailsPharmacies.adapter = adapter
                list.addAll(it.data.prods)
                adapter!!.setOnItemClickListener { _, id, amount ->
                    RetrofitClient(viewModel?.dataManager!!).apis4EShopping.getAddToCard(
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
                                    binding.tvTotalMoney.text = response.body()!!.totalPrice.toString()
                                    binding.tvTotalCoinssss.text = response.body()!!.totalCoins.toString()
                                    binding.tvTotalRoi.text = response.body()!!.roi.toString() + "%"
                                    no_product++
                                    binding.tvAddToCard.text = no_product.toString()
                                    addToCardRequest =
                                        AddToCardRequest("mr", no_product, id_pharmacies)
                                    Log.e("no_product++", no_product.toString())
                                    (cart as ArrayList<Cart>).add(Cart(id, amount))
                                }
                            }

                            override fun onFailure(call: Call<AddToCardResponse?>, t: Throwable) {
                            }
                        })
                }
                adapter!!.setOnItemClickListener2 { _, id, amount ->
                    RetrofitClient(viewModel?.dataManager!!).apis4EShopping.getRemoveToCard(
                        "Bearer " + UserPreferenceHelper.getUser().token,
                        id,
                        addToCardRequest
                    )!!.enqueue(object : Callback<AddToCardResponse?> {
                        override fun onResponse(
                            call: Call<AddToCardResponse?>,
                            response: Response<AddToCardResponse?>
                        ) {
                            if (response.isSuccessful) {
                                binding.tvTotalMoney.text = response.body()!!.totalPrice.toString()
                                binding.tvTotalCoinssss.text =
                                    response.body()!!.totalCoins.toString()
                                binding.tvTotalRoi.text = response.body()!!.roi.toString() + "%"
                                no_product--
                                binding.tvAddToCard.text = no_product.toString()
                                Log.e("no_product--", no_product.toString())
                                (cart as ArrayList<Cart>).remove(Cart(id, amount))
                            }
                        }

                        override fun onFailure(call: Call<AddToCardResponse?>, t: Throwable) {
                            //   errorMessage.postValue(1);
                        }
                    })
                }
            }
        }
        viewModel!!.addOrderToCartResponse.observe(this) {
            if (it!!.success == 200) {
                Toast.makeText(this, " تمت اضافة المنتجات للصيدليه بنجاح", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, Home4EShoppingActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Prod> = ArrayList()

        for (item in list) {
            if (item.name.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }
        adapter!!.filterData(filteredList)
    }

}