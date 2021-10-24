package com.devartlab.ui.main.ui.employeeservices

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentEmployeeServicesBinding

/**
 * A simple [Fragment] subclass.
 */
class EmployeeServicesFragment : BaseFragment<FragmentEmployeeServicesBinding>() {

    lateinit var fragmentEmployeeServicesBinding: FragmentEmployeeServicesBinding
    lateinit var employeeAdapter: EmployeeAdapter
    lateinit var list: ArrayList<EmployeeServices>

    override fun getLayoutId(): Int {
        return R.layout.fragment_employee_services
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentEmployeeServicesBinding = viewDataBinding
        employeeAdapter = EmployeeAdapter(baseActivity)
        list = ArrayList<EmployeeServices>()

        fragmentEmployeeServicesBinding.employeesRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        fragmentEmployeeServicesBinding.employeesRecyclerView.adapter = employeeAdapter

        if (getArguments()?.getBoolean("Requests")!!)
        {
            fragmentEmployeeServicesBinding.title.text = "Employee Requests"
            list.add(EmployeeServices("HR form (Request)" , "https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589960052/money_zzmerw.png" , "https://docs.google.com/forms/d/e/1FAIpQLSc0-8iWuvGFjdSMVy_EJT0LqHy8rPa7Y1NbQxm2HZgDRLP-bA/viewform" ,""))
            list.add(EmployeeServices("HR Penalty Form" , "https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589960052/halloween_whnan8.png" , "https://docs.google.com/forms/d/e/1FAIpQLSdeO9Pas4oZ4swRIX2Q306ev3sSVUGqESB2tPoivrWXJ2jb9Q/viewform" ,""))
            list.add(EmployeeServices("Work from home attending" , "https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589961093/world_xvbsvv.png" , "https://docs.google.com/forms/d/e/1FAIpQLSeDpxLCZXpSwDjWCBV6sytEN_uDc0NWUHa8vfRmh6VZGGsmWA/viewform" ,""))
        }
        else
        {
            fragmentEmployeeServicesBinding.title.text = "Employee Services"
            list.add(EmployeeServices("Products Requests" , "https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589960053/money_1_ddyttb.png" , "https://docs.google.com/forms/d/e/1FAIpQLSeDpxLCZXpSwDjWCBV6sytEN_uDc0NWUHa8vfRmh6VZGGsmWA/viewform" ,""))
            list.add(EmployeeServices("Breakfast request form" , "https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589960052/knife_h0wmfs.png" , "https://docs.google.com/forms/d/1t2CWg6xgc2c0m7QWBlcAgqmGuCiVJ68IeXcK7k6_k_g/viewform?edit_requested=true" ,""))
            list.add(EmployeeServices("Lunch request form" , "https://res.cloudinary.com/dn2hcmcqn/image/upload/v1589960053/eating_yg1ovq.png" , "https://docs.google.com/forms/d/1Fnr2xYDrCOHAQ4Q0xOmxNbL9wxEWDQL3nFp1a_O9kWc/viewform?edit_requested=true" ,""))
        }


        employeeAdapter.setMyData(list)
    }

}
