package com.devartlab.ui.main.ui.employeeservices.workfromhome

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentWorkFromHomeBinding
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import java.text.SimpleDateFormat
import java.util.*


class WorkFromHomeFragment : BaseFragment<FragmentWorkFromHomeBinding>() {

    lateinit var binding: FragmentWorkFromHomeBinding
    lateinit var viewModel: WorkFromHomeViewModel

    var started: Boolean = false
    var ended: Boolean = false
    var code: String = ""
    private var DATE: String? = null
    var fmt: SimpleDateFormat? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_from_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WorkFromHomeViewModel::class.java)
        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DATE = fmt?.format(CommonUtilities.currentToMillis)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding

        setObservers()

        binding.date.setText(DATE)
        binding.startWorkFromHome.setOnClickListener {

            if (!started)
                viewModel.startWorkFromHome("insert", viewModel.dataManager.user.empId.toString(), "", viewModel.dataManager.user.nameAr.toString(), viewModel.dataManager.user.managerId.toString(), "", "", "", "")
        }

        binding.startWorkFromHomeEnded.setOnClickListener {

            if (!ended)
                viewModel.endWorkToday(code)
        }


        binding.getAll.setOnClickListener {

            replace_fragment(ShowAllWorkFromHomeFragment(), "ShowAllWorkFromHomeFragment")

        }


    }

    private fun setObservers() {

        viewModel.responseLiveCheckWorkFromHome.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it.isSuccessful) {
                binding.startWorkFromHomeEnded.visibility = View.VISIBLE
                started = true
                code = it.workFromHome?.code!!
                binding.startWorkFromHome.setText("Started At \n " + it.workFromHome?.startedAt?.takeLast(8))


                if (it.workFromHome?.ended!!) {
                    ended = true
                    binding.startWorkFromHomeEnded.setText("Ended At \n " + it.workFromHome?.endedAt?.takeLast(8))


                } else {
                    ended = false

                }

            } else {
                binding.startWorkFromHomeEnded.visibility = View.GONE

                started = false

            }
        })


        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })


    }

    private fun replace_fragment(fragment: Fragment, s: String) {

        baseActivity.supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
        )
                .add(
                        R.id.Container,
                        fragment
                )
                .addToBackStack("s")
                .commit()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Work From Home"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

}