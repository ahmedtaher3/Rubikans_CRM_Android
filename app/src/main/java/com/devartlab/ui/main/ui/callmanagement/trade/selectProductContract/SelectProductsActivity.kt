package com.devartlab.ui.main.ui.callmanagement.trade.selectProductContract

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivitySelectProductsBinding

private const val TAG = "SelectProductsActivity"
class SelectProductsActivity : BaseActivity<ActivitySelectProductsBinding>() {
    lateinit var binding: ActivitySelectProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        Log.d(TAG, "onCreate: " + intent.getIntExtra("CUSTOMER_ID" ,0))
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

}