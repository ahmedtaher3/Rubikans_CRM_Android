package com.devartlab.ui.main.ui.devartlink.devartCommunity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.devartlab.R
import com.devartlab.databinding.ActivityCatDevartCommunityBinding

class CatDevartCommunityActivity : AppCompatActivity() {
    lateinit var binding:ActivityCatDevartCommunityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_cat_devart_community)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.community)
    }
}