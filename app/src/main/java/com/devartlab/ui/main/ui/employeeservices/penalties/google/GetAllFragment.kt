package com.devartlab.ui.main.ui.employeeservices.penalties.google

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentGetAllBinding
import com.devartlab.model.PenaltiesGoogle
import com.devartlab.ui.main.ui.employeeservices.penalties.PenaltiesViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.devartlab.utils.RecyclerTouchListener
import java.util.*


class GetAllFragment : BaseFragment<FragmentGetAllBinding>() {

    lateinit var binding: FragmentGetAllBinding
    lateinit var viewModel: PenaltiesViewModel
    lateinit var adapter: AllPenaltiesAdapter
    lateinit var fulList: ArrayList<PenaltiesGoogle>

    override fun getLayoutId(): Int {
        return R.layout.fragment_get_all
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PenaltiesViewModel::class.java)
        adapter = AllPenaltiesAdapter(baseActivity)
        fulList = ArrayList()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Penalties"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!


        binding.addNew.setOnClickListener(View.OnClickListener {

            startActivity(Intent(baseActivity, AddNewPenaltyActivity::class.java))


        })

        binding.hrRequestsSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
             viewModel.getSentPenalties()
        })




        setRecyclerViews()
        viewModel.getSentPenalties()
        setObservers()


    }

    private fun setRecyclerViews() {

        binding.recyclerView.adapter = adapter

        val touchListener = RecyclerTouchListener(baseActivity, binding.recyclerView)
        touchListener.setClickable(object : RecyclerTouchListener.OnRowClickListener {
            override fun onRowClicked(position: Int) {
            }

            override fun onIndependentViewClicked(independentViewID: Int, position: Int) {}
        })
                .setSwipeOptionViews(R.id.delete_task, R.id.edit_task)
                .setSwipeable(R.id.rowFG, R.id.rowBG, object : RecyclerTouchListener.OnSwipeOptionsClickListener {
                    override fun onSwipeOptionClicked(viewID: Int, position: Int) {
                        when (viewID) {
                            R.id.edit_task -> {

                            }

                            R.id.delete_task -> {

                                if (fulList[position].approve.equals("PENDING")) {
                                    viewModel.delete(fulList[position].code!!)
                                }
                            }
                        }
                    }
                })
        binding.recyclerView.addOnItemTouchListener(touchListener)


    }


    private fun setObservers() {


        viewModel.responseLiveInsert.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            fulList = it.penaltiesGoogle!!
            adapter.setMyData(fulList)


            if (fulList.isNullOrEmpty()) {

                binding.emptyList.visibility = View.VISIBLE

            } else {
                binding.emptyList.visibility = View.GONE

            }
        })


        viewModel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()

                    if (binding.hrRequestsSwipeRefreshLayout.isRefreshing)
                        binding.hrRequestsSwipeRefreshLayout.isRefreshing = false
                }
                1 -> {

                    ProgressLoading.show(baseActivity)
                }
            }
        })

    }


}