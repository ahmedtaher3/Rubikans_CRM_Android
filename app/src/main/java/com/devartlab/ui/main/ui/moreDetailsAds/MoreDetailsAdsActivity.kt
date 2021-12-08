package com.devartlab.ui.main.ui.moreDetailsAds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.databinding.ActivityMoreDetailsAdsBinding
import com.devartlab.model.AdModel
import com.devartlab.utils.Constants

class MoreDetailsAdsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreDetailsAdsBinding
    lateinit var viewModel: MoreDetailsAdsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_more_details_ads)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "More than"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(MoreDetailsAdsViewModel::class.java)
        val bundle: Bundle? = intent.extras
        val page_code = bundle?.get("pageCode")

        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == page_code) {
                model = m
                break
            }
        }
        binding.tvTitle.setText(model.view_more_title)


    }
}