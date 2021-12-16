package com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.purchasetype.PurchaseTypeEntity
import com.devartlab.databinding.ActivitySelectProductsBinding
import com.devartlab.ui.main.ui.callmanagement.trade.printer.printerControl.BixolonPrinter

private const val TAG = "SelectProductsActivity"
class SelectProductsActivity : BaseActivity<ActivitySelectProductsBinding>() {
    lateinit var binding: ActivitySelectProductsBinding

    var purchaseTypeEntity: PurchaseTypeEntity? = null
    var planEntity: PlanEntity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.select_products)


        purchaseTypeEntity = intent.getParcelableExtra("PurchaseTypeEntity")
        planEntity = intent.getParcelableExtra("PLAN_MODEL")

        replace_fragment(SelectProductsFragment(), "SelectProductsFragment")

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_select_products
    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {

        val bundel = Bundle()
        bundel.putParcelable("PurchaseTypeEntity", purchaseTypeEntity)
        bundel.putParcelable("PLAN_MODEL", planEntity)
        fragment?.arguments = bundel
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_left)
            .add(R.id.container, fragment!!).addToBackStack(tag).commit()
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
        }
        else {
            super.onBackPressed()
        }
    }


    init {
          bxlPrinter = BixolonPrinter(this)
    }

    companion object {
        var bxlPrinter: BixolonPrinter? = null

        fun showMsg(text: String) {
        }

    }

}