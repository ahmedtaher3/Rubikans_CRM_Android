package com.devartlab.ui.main.ui.employeeservices.meals.confirmation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.devartlab.R
import com.devartlab
.base.BaseFragment
import com.devartlab.databinding.FragmentCofirmMealsBinding
import com.devartlab
.model.MealRequest
import com.devartlab.ui.main.ui.employeeservices.meals.Meals
import com.devartlab
.ui.main.ui.employeeservices.meals.MealsViewModel
import com.devartlab
.utils.CommonUtilities
import com.devartlab
.utils.ProgressLoading
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketRequestTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmMealsFragment : BaseFragment<FragmentCofirmMealsBinding>(), OnMealRequestClick {

    lateinit var binding: FragmentCofirmMealsBinding
    lateinit var viewModel: MealsViewModel
    lateinit var adapter: ConfirmMealsAdapter
    lateinit var fullList: java.util.ArrayList<MealRequest>


    override fun getLayoutId(): Int {
        return R.layout.fragment_cofirm_meals
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Meals"

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
        adapter = ConfirmMealsAdapter(baseActivity, ArrayList(), this)
        fullList = ArrayList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding
        setHasOptionsMenu(true);


        setObservers()
        setRecyclerViews()
        setSpinnerMeals()

        binding.previousMealsSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.getMealRequestsToday()
        })

        viewModel.getMealRequestsToday()


    }

    private fun setListeners() {

    }


    private fun setRecyclerViews() {

        binding.recyclerView?.adapter = adapter

    }

    private fun setObservers() {


        viewModel.responseLiveMealRequestToday.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            fullList = it.mealRequests!!


            var newList = ArrayList<MealRequest>()
            for (model in fullList) {
                if (CommonUtilities.isSameDayMeals(Date(CommonUtilities.currentToMillis), model.date.take(10)))
                    newList.add(model)
            }

            adapter.setMyData(newList)


            if (binding.previousMealsSwipeRefreshLayout.isRefreshing)
                binding.previousMealsSwipeRefreshLayout.isRefreshing = false
        })


        viewModel.progressMealRequesttoday.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

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

    override fun setOnMealRequestClick(isReceived: Boolean, code: String) {

        viewModel.approveRequests(code)

    }

    private fun setSpinnerMeals() {

        var list = ArrayList<String>()
        list.add(Meals.All.name)
        list.add(Meals.Breakfast.name)
        list.add(Meals.Lunch.name)

        val spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, list)

        binding.spinnerMeals.setAdapter(spinnerAdapter)
        binding.spinnerMeals.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {


                when (list[position]) {
                    Meals.Breakfast.name -> {


                        var newList = ArrayList<MealRequest>()
                        for (model in fullList) {
                            if (model.meal == Meals.Breakfast.name)
                                newList.add(model)
                        }

                        adapter.setMyData(newList)


                    }
                    Meals.Lunch.name -> {


                        var newList = ArrayList<MealRequest>()
                        for (model in fullList) {
                            if (model.meal == Meals.Lunch.name)
                                newList.add(model)
                        }

                        adapter.setMyData(newList)

                    }

                    Meals.All.name -> {

                        adapter.setMyData(fullList)

                    }

                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }


}