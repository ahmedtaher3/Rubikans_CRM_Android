package com.devartlab.ui.main.ui.cycles


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.Cycle
import com.devartlab.ui.main.ui.callmanagement.plan.cycles.CyclesAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_cycles_dialog.*
import retrofit2.Retrofit
import java.util.*

class ChangeCycle(context: Context, private var changeCycleInterface: ChangeCycleInterface, private var dataManager: DataManager, private var accId: Int) : Dialog(context), CyclesAdapter.CycleInterface {

    private var param1: String? = null
    private var param2: String? = null
    var adapter: CyclesAdapter? = null
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_cycles_dialog)


        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        adapter = CyclesAdapter(context, this, dataManager)


        val layoutManager: GridLayoutManager
        if (dataManager!!.isTablet)
            layoutManager = GridLayoutManager(context, 3)
        else
            layoutManager = GridLayoutManager(context, 2)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        cyclesRecyclerView?.layoutManager = layoutManager


        getCycles()


        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })


    }

    override fun setCycle(cycle: Cycle?) {

        dataManager?.saveCycle(cycle)
        changeCycleInterface.changeCycle(cycle)
        dismiss()

    }

    fun getCycles() {

        cyclesProgressBar?.visibility = View.VISIBLE
        myAPI?.getYtdCyclePlan(accId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<Cycle>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<Cycle>) {
                        cyclesProgressBar?.visibility = View.GONE
                        cyclesRecyclerView?.adapter = adapter
                        adapter?.setMyData(data)


                    }

                    override fun onError(e: Throwable) {
                        cyclesProgressBar?.visibility = View.GONE
                        Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {}
                })
    }

}
