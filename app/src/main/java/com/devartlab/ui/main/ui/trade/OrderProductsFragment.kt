package com.devartlab.ui.main.ui.trade

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentOrderProductsBinding
import com.devartlab.model.DevartLabReportsFilterDTO
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
class OrderProductsFragment : BaseFragment<FragmentOrderProductsBinding>() {

    lateinit var binding: FragmentOrderProductsBinding
    lateinit var viewModel: TradeViewModel
    lateinit var adapter: OrderProductsAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_order_products
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
        viewModel = ViewModelProviders.of(this).get(TradeViewModel::class.java)
        adapter = OrderProductsAdapter(baseActivity, ArrayList())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding!!

      val  filterModel = DevartLabReportsFilterDTO(
                9,
                1000,
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )


        viewModel.getProducts(filterModel)
        binding.order.setOnClickListener {
           // replace_fragment(OrderPrintActivity(), "OrderPrintFragment")
        }

        binding.recyclerView?.adapter = adapter


        setObservers()


    }

    private fun setObservers() {

        viewModel.productLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


           // adapter.setMyData(it.products)
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

    fun replace_fragment(fragment: Fragment?, tag: String?) {

        val bundle = Bundle()
        bundle.putParcelableArrayList("PRODUCTS", adapter.getMyData())
         bundle.putParcelable("CustomerTrade", arguments?.getParcelable("CustomerTrade"))

        fragment?.arguments = bundle
        baseActivity.supportFragmentManager
                .beginTransaction()
                .replace(
                        R.id.Container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }


}