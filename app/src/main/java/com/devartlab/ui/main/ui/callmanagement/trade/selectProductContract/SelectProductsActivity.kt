package com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import com.devartlab.databinding.ActivitySelectProductsBinding

private const val TAG = "SelectProductsActivity"
class SelectProductsActivity : BaseActivity<ActivitySelectProductsBinding>() {
    lateinit var binding: ActivitySelectProductsBinding

    var model : PurchaseTypeEntity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.select_products)
        Log.d(TAG, "onCreate: " + intent.getIntExtra("CUSTOMER_ID" ,0))
        model = intent.getParcelableExtra("PurchaseTypeEntity")

        replace_fragment(SelectProductsFragment(), "SelectProductsFragment" , intent.getIntExtra("CUSTOMER_ID" ,0))

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_select_products
    }

    fun replace_fragment(fragment: Fragment?, tag: String? , id:Int) {

        val bundel = Bundle()
        bundel.putInt("CUSTOMER_ID" , id)
        fragment?.arguments = bundel
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left
                )
                .add(
                        R.id.container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}