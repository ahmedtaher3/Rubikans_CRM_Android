package com.devartlab.ui.main.ui.callmanagement.list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.TypesFragmentBinding
import com.devartlab.ui.main.ui.callmanagement.list.customertype.ChooseCustomerTypeDialog
import com.devartlab.ui.main.ui.callmanagement.list.customertype.ChooseCustomerTypeInterFace
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TypesFragment : BaseFragment<TypesFragmentBinding>() , ChooseCustomerTypeInterFace {

    private lateinit var binding: TypesFragmentBinding
    private lateinit var viewModel: TypesViewModel
    private lateinit var adapter: TypesAdapter
    lateinit var chooseCustomerTypeDialog: ChooseCustomerTypeDialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(TypesViewModel::class.java)
        adapter = TypesAdapter(baseActivity, baseActivity.getSupportFragmentManager().beginTransaction())

        binding.typesRecyclerView.layoutManager = GridLayoutManager(baseActivity, 2)
        binding.typesRecyclerView.adapter = adapter

        binding.addToList.setOnClickListener(View.OnClickListener {

             chooseType()

        })


        viewModel.getAllTypes()
        setObservers()

    }

    private fun chooseType() {


        chooseCustomerTypeDialog = ChooseCustomerTypeDialog(baseActivity, this@TypesFragment, viewModel?.dataManager!!);
        chooseCustomerTypeDialog.setCanceledOnTouchOutside(false);
        val window = chooseCustomerTypeDialog.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        chooseCustomerTypeDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseCustomerTypeDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseCustomerTypeDialog.show();

    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, Observer {

            if (it.isNullOrEmpty())
            {
                binding.emptyList.visibility = View.VISIBLE
            }
            else
            {
                binding.emptyList.visibility = View.GONE
            }

            adapter.setMyData(it)

        })


        viewModel.progress.observe(this, Observer { progress ->

            when (progress) {
                0 -> {

                    Single.timer(2, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : SingleObserver<Long?> {
                                override fun onSubscribe(d: Disposable) {}
                                override fun onError(e: Throwable) {}
                                override fun onSuccess(t: Long) {
                                    ProgressLoading.dismiss()
                                    viewModel.getAllTypes()
                                }
                            })


                }
                1 -> {
                    ProgressLoading.show(baseActivity)
                }
            }
        })


    }

    override fun getLayoutId(): Int {
        return R.layout.types_fragment
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "List"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.list_type_menu, menu)
        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {

            R.id.syncList -> {
              viewModel.syncList()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun getCustomerType() {

    }

}
