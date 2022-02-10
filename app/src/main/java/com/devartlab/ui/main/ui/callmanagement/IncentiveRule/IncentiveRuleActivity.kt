package com.devartlab.ui.main.ui.callmanagement.IncentiveRule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.a4eshopping.pharmacySales.DevartLabTeamViewModel
import com.devartlab.databinding.ActivityIncentiveRuleBinding
import com.devartlab.ui.main.ui.devartLabTeam.DevartLabSubAdapter
import com.devartlab.ui.main.ui.devartLabTeam.DevartLabTeamActivity
import com.devartlab.ui.main.ui.devartLabTeam.DevartLabTeamAdapter

class IncentiveRuleActivity : AppCompatActivity() {
    lateinit var binding:ActivityIncentiveRuleBinding
    var viewModel: DevartLabTeamViewModel? = null
    private var adapter: DevartLabSubAdapter? = null
    private var adapter2: DevartLabTeamAdapter? = null
    var _id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_incentive_rule
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        adapter = DevartLabSubAdapter(null)
        adapter2 = DevartLabTeamAdapter(null)
        viewModel = ViewModelProvider(this).get(DevartLabTeamViewModel::class.java)
        if (intent.hasExtra("_id")) {
            _id = intent.getStringExtra("_id")
            viewModel!!.getIncentive(_id!!)
        } else {
            viewModel!!.getIncentive("0")
        }
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.btnHideShowAds.setOnClickListener {
            if (binding.constrAds.visibility == View.VISIBLE) {
                binding.constrAds.setVisibility(View.GONE)
                binding.btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
            } else {
                binding.constrAds.setVisibility(View.VISIBLE)
                binding.btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
            }
        }
    }

    private fun handleObserver() {
        viewModel!!.errorMessage.observe(this, Observer { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel!!.devartLabTeamResponse.observe(this, Observer {
            binding.progressBar.setVisibility(View.GONE)

            Glide.with(this)
                .load("https://devartlink.devartlab.com/assets/images/" + it!!.image)
                .centerCrop().into(binding.imageView)
            supportActionBar!!.title = it.name
            binding.decTeam.loadDataWithBaseURL(
                null, it.description, "text/html", "utf-8", null)
            when {
                it.sub.size != 0 -> {
                    //show data in recyclerView
                    binding.progressBar.setVisibility(View.GONE)
                    adapter = DevartLabSubAdapter(it.sub)
                    binding.recyclerListTeams.setAdapter(adapter)
                    adapter!!.setOnItemClickListener(DevartLabSubAdapter.OnItemClickListener { pos, dataItem ->
                        val intent = Intent(this, IncentiveRuleActivity::class.java)
                        intent.putExtra("_id", dataItem._id)
                        startActivity(intent)
                    })
                }
                it.team.size != 0 -> {
                    //show data in recyclerView
                    binding.progressBar.setVisibility(View.GONE)
                    adapter2 = DevartLabTeamAdapter(it.team)
                    binding.recyclerListTeams.setAdapter(adapter2)
                        adapter2!!.setOnItemClickListener(DevartLabTeamAdapter.OnItemClickListener { pos, dataItem ->
                            val intent = Intent(this, IncentiveRuleActivity::class.java)
                            intent.putExtra("_id", dataItem._id)
                            startActivity(intent)
                        })
                }
                else -> {
                    //errorMessage if data coming is null;
                    binding.recyclerListTeams.setVisibility(View.GONE)
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}