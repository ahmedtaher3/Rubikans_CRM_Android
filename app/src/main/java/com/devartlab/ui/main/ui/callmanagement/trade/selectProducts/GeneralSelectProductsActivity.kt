package com.devartlab.ui.main.ui.callmanagement.trade.selectProducts

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivitySelectProductsBinding

class GeneralSelectProductsActivity : BaseActivity<ActivitySelectProductsBinding>() {
    lateinit var binding: ActivitySelectProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding

        replace_fragment(GeneralSelectProductsFragment(), "SelectProductsFragment")
//        replace_fragment(ConfirmProductsFragment(), "ConfirmProductsFragment")

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_select_products
    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {
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