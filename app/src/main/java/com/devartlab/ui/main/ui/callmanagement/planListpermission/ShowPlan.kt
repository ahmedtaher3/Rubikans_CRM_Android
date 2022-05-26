package com.devartlab.ui.main.ui.callmanagement.planListpermission

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.DoubleVisitEmp
import com.devartlab.utils.CommonUtilities
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class ShowPlan(context: Context , private val empId:Int, private val date:String , private val dataManager: DataManager)
    : Dialog(context) {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var adapter : ShowPlanAdapter
    lateinit var close : ImageView
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_plan)


        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        progressBar = findViewById(R.id.progressBar)

        recyclerView = findViewById(R.id.recyclerView)
        close = findViewById(R.id.close)
        adapter = ShowPlanAdapter(context )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter



        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })

        getDoubleListList(empId.toString() , CommonUtilities.convertDateToMillis(date).toString() , "0")

    }

    public fun getDoubleListList(selectedEmpAccountId: String, DayDateInMsFormat: String, ShiftId: String) {

        System.out.println("selectedEmpAccountId : " + selectedEmpAccountId + "DayDateInMsFormat : " + DayDateInMsFormat + "ShiftId : " + ShiftId)

        progressBar.visibility = View.VISIBLE
        myAPI?.getEmployeePlan(selectedEmpAccountId, DayDateInMsFormat, ShiftId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<java.util.ArrayList<DoubleVisitEmp>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: java.util.ArrayList<DoubleVisitEmp>) {

                        progressBar.visibility = View.GONE

                        adapter.setMyData(data)

                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {}
                })

    }


}
