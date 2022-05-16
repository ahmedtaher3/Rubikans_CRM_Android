package com.devartlab.ui.main.ui.employeeservices.meals.previousmeals

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentPreviousMealsBinding
import com.devartlab.model.MealRequest
import com.devartlab.ui.main.ui.employeeservices.meals.MealsViewModel
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketRequestTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PreviousMealsFragment : BaseFragment<FragmentPreviousMealsBinding>() {

    lateinit var binding: FragmentPreviousMealsBinding
    lateinit var viewModel: MealsViewModel
    lateinit var adapter: PreviousMealsAdapter

    private lateinit var fullList: java.util.ArrayList<MealRequest>


    override fun getLayoutId(): Int {
        return R.layout.fragment_previous_meals
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Requests"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MealsViewModel::class.java)
        adapter = PreviousMealsAdapter(baseActivity, ArrayList())
        fullList = ArrayList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!

        setEmpData()
        setObservers()
        setRecyclerViews()

        binding.previousMealsSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {

            viewModel.getMealRequests()

        })

        viewModel.getMealRequests()

    }


    private fun setEmpData() {
        binding.empImage.setImageResource(R.drawable.user_logo);
        if (!viewModel.dataManager.user.image.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(viewModel.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.empImage?.setImageBitmap(decodedByte)
        }

        binding.empTitle.text = viewModel.dataManager.user.title
        binding.empName.text = viewModel.dataManager.user.nameAr
        binding.empId.text = viewModel.dataManager.user.empId.toString()
    }

    private fun setRecyclerViews() {

        binding.recyclerView?.adapter = adapter

        /*
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

                                if (!fullList[position].received) {
                                     viewModel.deleteMealRequest(fullList[position].code)
                                }
                            }
                        }
                    }
                })
        binding.recyclerView.addOnItemTouchListener(touchListener)
         */

    }

    private fun setObservers() {


        viewModel.responseLiveMealRequest.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            fullList = it.mealRequests!!
            adapter.setMyData(fullList)

            if (binding.previousMealsSwipeRefreshLayout.isRefreshing)
                binding.previousMealsSwipeRefreshLayout.isRefreshing = false
        })


        viewModel.progressMealRequest.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

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


}