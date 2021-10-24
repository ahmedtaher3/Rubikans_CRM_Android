package com.devartlab.ui.dialogs.addplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.data.room.specialty.SpecialtyParentEntity
import com.devartlab.ui.dialogs.addplan.fragments.types.AddPlanTypesAdapter
import com.devartlab.ui.main.ui.callmanagement.list.list.AddPlanListFragment
import com.devartlab.utils.ProgressLoading
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass.
 */
class AddPlanFragment : DialogFragment(), AddPlanTypesAdapter.AddPlanGetList {

    private lateinit var adapter: AddPlanTypesAdapter
    private lateinit var typesRecyclerView: RecyclerView
    var dataManager: DataManager? = null
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataManager = ((context as AppCompatActivity).getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_plan, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        typesRecyclerView = view.findViewById(R.id.typesRecyclerView)
        adapter = AddPlanTypesAdapter(requireContext(), this)

        typesRecyclerView.layoutManager = GridLayoutManager(context, 3)
        typesRecyclerView.adapter = adapter

        getCustomerListType()

        dialog?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    companion object {
        fun newInstance(title: String?): AddPlanFragment {
            val frag = AddPlanFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }
    }


    fun replace_fragment(fragment: Fragment?, tag: String?) {


        val bundle = Bundle()
        bundle.putString("TYPE_ID","2")
        fragment?.arguments = bundle


        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left
                )
                .add(
                        R.id.addPlanContainer,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }

    override fun get_list() {
        replace_fragment(AddPlanListFragment(), "TypesFragment")
    }

    fun getCustomerListType() {

        context?.let { ProgressLoading.show(it) }
        myAPI?.getCustomerListType(dataManager?.user?.accId!!)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<SpecialtyParentEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<SpecialtyParentEntity>) {

                        adapter.setMyData(data)
                        ProgressLoading.dismiss()

                    }

                    override fun onError(e: Throwable) {

                        ProgressLoading.dismiss()
                    }

                    override fun onComplete() {}
                })

    }
}