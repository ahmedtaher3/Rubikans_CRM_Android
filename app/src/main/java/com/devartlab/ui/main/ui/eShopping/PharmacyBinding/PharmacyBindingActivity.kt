package com.devartlab.ui.main.ui.eShopping.PharmacyBinding

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.a4eshopping.PharmacyBinding.PharmacyBindingViewModel
import com.devartlab.a4eshopping.PharmacyBinding.model.searchForPharmacy.DataX
import com.devartlab.a4eshopping.PharmacyBinding.model.searchForPharmacy.SearchForPharmacyRequest
import com.devartlab.a4eshopping.PharmacyBinding.model.searchForPharmacy.SearchForPharmacyResponse
import com.devartlab.a4eshopping.PharmacyBinding.uploadPharmacyFiles.UploadPharmacyFilesActivity
import com.devartlab.databinding.ActivityPharmacyBindingBinding
import com.devartlab.ui.main.ui.eShopping.PharmacyBinding.addLocation.AddLocationActivity
import com.devartlab.ui.main.ui.eShopping.PharmacyBinding.allComments.AllCommentsActivity

class PharmacyBindingActivity : AppCompatActivity() {
    lateinit var binding: ActivityPharmacyBindingBinding
    var viewModel: PharmacyBindingViewModel? = null
    private var adapter: ListConnetctedPharmaciesAdapter? = null
    lateinit var searchForPharmacy: SearchForPharmacyRequest
    val list = ArrayList<DataX>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_pharmacy_binding)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.Pharmacy_binding)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = ListConnetctedPharmaciesAdapter(null)
        viewModel = ViewModelProvider(this).get(PharmacyBindingViewModel::class.java)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getConnetctedPharmacies("")
        binding.btnSearch.setOnClickListener {
            if (TextUtils.isEmpty(binding.edSearch.getText().toString())) {
                binding.edSearch.setError("please enter code")
            } else {
                viewModel!!.getSearchForPharmacy(binding.edSearch.text.toString())
            }
        }

        binding.searchBarVideo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel!!.getConnetctedPharmacies(binding.searchBarVideo.text.toString())
            }
            override fun afterTextChanged(editable: Editable) {
            }
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            refresh()
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
        viewModel!!.ConnetctedPharmaciesResponse.observe(this, Observer {
            if (it!!.data == null) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
                binding.progressBar.setVisibility(View.GONE)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = ListConnetctedPharmaciesAdapter(it.data)
                binding.recyclerPharmacyBinging.setAdapter(adapter)
                adapter!!.setOnItemClickListener(ListConnetctedPharmaciesAdapter.OnItemClickListener { pos, dataItem ->
                    val intent = Intent(this, AllCommentsActivity::class.java)
                    intent.putExtra("pharmacies_id", dataItem.id.toString())
                    startActivity(intent)
                })
                adapter!!.setOnItemClickListener2(ListConnetctedPharmaciesAdapter.OnItemClickListener2 { pos, dataItem ->
                    val intent = Intent(this, AddLocationActivity::class.java)
                    intent.putExtra("pharmacies_id", dataItem.id.toString())
                    startActivity(intent)
                })
                adapter!!.setOnItemClickListener3(ListConnetctedPharmaciesAdapter.OnItemClickListener3 { pos, dataItem ->
                    val intent = Intent(this, UploadPharmacyFilesActivity::class.java)
                    intent.putExtra("pharmacies_id", dataItem.id.toString())
                    startActivity(intent)
                })
            }
        })
        viewModel!!.searchForPharmacyResponse.observe(this, Observer {
            if(it!!.data==null){
                Toast.makeText(this, "تم ربط الصدليه من قبل او لا توجد صيدليه بهذا الرقم", Toast.LENGTH_SHORT).show()
            }else{
                showSearchForPharmacyDialog(it!!)
            }
        })

    }

    fun showSearchForPharmacyDialog(searchForPharmacyResponse: SearchForPharmacyResponse) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_diatails_pharmacy)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val BtnConnect = dialog.findViewById<Button>(R.id.btn_connect_now)
        val BtnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val TvName = dialog.findViewById<TextView>(R.id.tv_no_pharmacy)
        val TvType_of_customer = dialog.findViewById<TextView>(R.id.tv_name_pharmacy)
        val TvPhoneNo = dialog.findViewById<TextView>(R.id.tv_start_date)
        val TvEmail = dialog.findViewById<TextView>(R.id.tv_update_date)
        TvName.text =searchForPharmacyResponse.data.id.toString()
        TvType_of_customer.text =searchForPharmacyResponse.data.type_code_ar
        TvPhoneNo.text =searchForPharmacyResponse.data.phone
        TvEmail.text =searchForPharmacyResponse.data.email
        BtnConnect.setOnClickListener {
            searchForPharmacy= SearchForPharmacyRequest(searchForPharmacyResponse.data.id)
            viewModel!!.connetctedPharmacies(searchForPharmacy)
        }
        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        viewModel!!.connectPharmacyResponse.observe(this, Observer {
                dialog.dismiss()
        })
        dialog.show()
    }

    private fun refresh() {
        synchronized(this) {
            viewModel!!.getConnetctedPharmacies("")
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.setVisibility(View.VISIBLE)
        }
    }
    private fun filter(text: String) {
        val filteredList: ArrayList<DataX> = ArrayList()

        for (item in list) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter!!.filterData(filteredList)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}