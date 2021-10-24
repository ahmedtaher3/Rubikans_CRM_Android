 package com.devartlab.ui.main.ui.callmanagement.plan.cycles

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.FragmentCyclesDialogBinding
import com.devartlab.model.Cycle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.data.retrofit.ResponseModel
import retrofit2.Retrofit
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [CyclesDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class CyclesDialog : DialogFragment(), CyclesAdapter.CycleInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var adapter: CyclesAdapter? = null
    var binding: FragmentCyclesDialogBinding? = null
    private var mRootView: View? = null
    var dataManager: DataManager? = null
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        dataManager = (activity?.getApplication() as BaseApplication).dataManager!!
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        adapter = CyclesAdapter(requireActivity(), this, dataManager!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cycles_dialog, container, false)
        mRootView = binding?.getRoot()
        return mRootView


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager: GridLayoutManager
        if (dataManager!!.isTablet)
            layoutManager = GridLayoutManager(context, 3)
        else
            layoutManager = GridLayoutManager(context, 2)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.cyclesRecyclerView?.layoutManager = layoutManager


        getCycles()

        binding?.close?.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        dialog?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CyclesDialog().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    fun getCycles() {

        binding?.cyclesProgressBar?.visibility = View.VISIBLE
        myAPI?.getYtdCyclePlan(dataManager?.user?.accId!!)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<Cycle>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<Cycle>) {

                        myAPI?.cycleData(dataManager?.user?.accId!!)!!
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Observer<ResponseModel> {
                                    override fun onSubscribe(d: Disposable) {}
                                    override fun onNext(cycleData: ResponseModel) {

                                        dataManager?.saveNewOldCycle(cycleData.data.cycleData[0])

                                        binding?.cyclesProgressBar?.visibility = View.GONE
                                        binding?.cyclesRecyclerView?.adapter = adapter
                                        adapter?.setMyData(data)

                                    }

                                    override fun onError(e: Throwable) {
                                        binding?.cyclesProgressBar?.visibility = View.GONE
                                        Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onComplete() {}
                                })


                    }

                    override fun onError(e: Throwable) {
                        binding?.cyclesProgressBar?.visibility = View.GONE
                        Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {}
                })
    }

    override fun setCycle(cycle: Cycle?) {
        dataManager?.saveCycle(cycle)
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        val parentFragment = targetFragment;

        (parentFragment as DialogInterface.OnDismissListener).onDismiss(dialog);


    }
}
