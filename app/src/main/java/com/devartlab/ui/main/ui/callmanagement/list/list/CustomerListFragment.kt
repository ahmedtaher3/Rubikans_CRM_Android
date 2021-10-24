package com.devartlab.ui.main.ui.callmanagement.list.list

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.databinding.ListkFragmentBinding
import com.devartlab.ui.main.ui.callmanagement.list.TypesViewModel
import com.devartlab.ui.main.ui.callmanagement.list.editcustomer.EditCustomerDialog
import com.devartlab.ui.main.ui.callmanagement.list.editcustomer.EditCustomerInterface
import com.devartlab.utils.ProgressLoading
import com.devartlab.utils.SwipeHelper
import com.devartlab.utils.SwipeHelper.UnderlayButtonClickListener

class CustomerListFragment : BaseFragment<ListkFragmentBinding>(), EditCustomerInterface {


    private lateinit var viewModel: TypesViewModel
    private lateinit var binding: ListkFragmentBinding
    private lateinit var adapter: CustomerListAdapter
    private var BUTTON_WIDTH: Int = 100
    lateinit var dialog: EditCustomerDialog
    lateinit var fullList: List<ListEntity>
    lateinit var listTypesEntity: ListTypesEntity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(TypesViewModel::class.java)
        binding = viewDataBinding
        adapter = CustomerListAdapter(baseActivity, viewModel.dataManager)
        listTypesEntity = arguments?.getSerializable("LIST_TYPE_MODEL") as ListTypesEntity

        binding.listRecyclerView.layoutManager = LinearLayoutManager(baseActivity)
        binding.listRecyclerView.adapter = adapter


        viewModel.getAllList(listTypesEntity.listTypeId.toString())


        if (viewModel?.dataManager?.isTablet!!)
            BUTTON_WIDTH = 100
        else
            BUTTON_WIDTH = 200
        val swipeHelper: SwipeHelper = object : SwipeHelper(baseActivity, BUTTON_WIDTH) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder, underlayButtons: MutableList<UnderlayButton>) {

                underlayButtons.add(UnderlayButton(
                        "Edit",
                        0,
                        Color.parseColor("#23780a"),
                        UnderlayButtonClickListener { pos ->


                            dialog = EditCustomerDialog(baseActivity, viewModel.dataManager, this@CustomerListFragment, fullList[pos]);
                            dialog.setCanceledOnTouchOutside(true);
                            val window = dialog.getWindow();
                            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            dialog.show();


                        }
                ))


                underlayButtons.add(UnderlayButton(
                        "Delete",
                        R.drawable.ic_account_voice_black_36dp,
                        Color.parseColor("#fc1c03"),
                        UnderlayButtonClickListener { pos ->


                            val model = fullList[pos]
                            model.customerState = 0
                            viewModel.updateCustomer(model, listTypesEntity.listTypeId.toString())

                        }
                ))


            }
        }
        swipeHelper.attachToRecyclerView(binding.listRecyclerView);



        binding?.editTextSearch?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (TextUtils.isEmpty(s)) {
                    adapter.setMyData(fullList)
                } else {

                    val filtered :  ArrayList<ListEntity> = ArrayList()
                    for (model in fullList)
                    {
                        if (model.customerEnName?.contains(s.toString(), ignoreCase = true)!!)
                        {
                            filtered.add(model)
                        }
                    }
                    adapter.setMyData(filtered)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // TODO Auto-generated method stub
            }
        })




/*
        binding.addToList.setOnClickListener(View.OnClickListener {

            var intent = Intent(baseActivity, AddNewCustomer::class.java)
            intent.putExtra("LIST_TYPE_MODEL", listTypesEntity)
            startActivity(intent)

        })
*/

        setObservers()
    }

    private fun setObservers() {
        viewModel.responseLiveList.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty())
            {
                binding.emptyList.visibility = View.VISIBLE
            }
            else
            {
                binding.emptyList.visibility = View.GONE
            }
            fullList = it
            adapter.setMyData(it)

        })


        viewModel.progress.observe(viewLifecycleOwner, Observer { progress ->

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

    override fun getLayoutId(): Int {
        return R.layout.listk_fragment
    }

    override fun editCustomer(model: ListEntity) {
        dialog.dismiss()
        viewModel.updateCustomer(model, listTypesEntity.listTypeId.toString())

        Toast.makeText(baseActivity, "customer has been updated", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.list_menu, menu)
        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.updateList -> {
                viewModel.updateList(listTypesEntity.listTypeId.toString())
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
