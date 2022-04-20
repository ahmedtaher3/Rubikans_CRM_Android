package com.devartlab.ui.main.ui.eShopping.pharmacyBinding.allComments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.devartlab.R
import com.devartlab.databinding.ActivityAllCommentsBinding

class AllCommentsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllCommentsBinding
    var viewModel: AllCommentsViewModel? = null
    private var idPharmacies: String? = null
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
        viewModel = ViewModelProvider(this)[AllCommentsViewModel::class.java]
        onClickListener()
        handleObserver()
    }
    private fun onClickListener() {
        viewModel!!.getSearchForPharmacy(idPharmacies!!)
    }
    private fun handleObserver(){
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.allCommentsResponse.observe(this) {
            if (it!!.data.isEmpty()) {
                //errorMessage if data coming is null;
                binding.tvEmptyList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else {
                //show data in recyclerView
                binding.progressBar.visibility = View.GONE
                adapter = AllCommentsAdapter(it.data)
                binding.recyclerAllComments.adapter = adapter
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}