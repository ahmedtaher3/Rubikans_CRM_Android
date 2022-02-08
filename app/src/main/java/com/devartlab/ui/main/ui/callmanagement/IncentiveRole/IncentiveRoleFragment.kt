package com.devartlab.ui.main.ui.callmanagement.IncentiveRole

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentIncentiveRoleBinding
import com.devartlab.utils.CommonUtilities

class IncentiveRoleFragment : BaseFragment<FragmentIncentiveRoleBinding>() {
    lateinit var binding: FragmentIncentiveRoleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_incentive_role
    }

    fun replace_fragment(fragment: Fragment?, tag: String?) {
        baseActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                fragment!!
            )
            .addToBackStack(tag)
            .commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title =
                getString(R.string.incentive_role)
        } catch (e: Exception) {

        }

        try {
            CommonUtilities.sendMessage(baseActivity, true)
        } catch (e: Exception) {
        }
    }

}