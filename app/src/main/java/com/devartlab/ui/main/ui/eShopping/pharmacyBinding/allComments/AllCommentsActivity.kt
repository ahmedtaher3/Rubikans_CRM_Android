package com.devartlab.ui.main.ui.eShopping.pharmacyBinding.allComments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityAllCommentsBinding

class AllCommentsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllCommentsBinding
    var viewModel: AllCommentsViewModel? = null
    var idPharmacies: String? = null
    private var adapter: AllCommentsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_all_comments)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.comment)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("pharmacies_id")) {
            idPharmacies = intent.getStringExtra("pharmacies_id")
        }
        adapter = AllCommentsAdapter(null)
        viewModel = ViewModelProvider(this).get(AllCommentsViewModel::class.java)
        onClickListener()
        handleObserver()
    }
    private fun onClickListener() {
        viewModel!!.getSearchForPharmacy(idPharmacies!!)
    }
    private fun handleObserver(){
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.allCommentsResponse.observe(this, Observer {
            if (it!!.data.size==0) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.setVisibility(View.VISIBLE)
                binding.progressBar.setVisibility(View.GONE)
            } else {
                //show data in recyclerView
                binding.progressBar.setVisibility(View.GONE)
                adapter = AllCommentsAdapter(it.data)
                binding.recyclerAllComments.setAdapter(adapter)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}