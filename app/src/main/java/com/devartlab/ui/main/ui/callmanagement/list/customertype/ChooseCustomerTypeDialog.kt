package com.devartlab.ui.main.ui.callmanagement.list.customertype

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.data.room.filterdata.FilterDataEntity
import kotlinx.android.synthetic.main.activity_choose_customer_type.*
import retrofit2.Retrofit

class ChooseCustomerTypeDialog (context: Context, private var chooseCustomerTypeInterFace: ChooseCustomerTypeInterFace, private var dataManager: DataManager) : Dialog(context) {

     lateinit var dapter: ChooseCustomerTypeAdapter
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    var editText: EditText? = null
    var list: ArrayList<FilterDataEntity>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_customer_type)
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        dapter = ChooseCustomerTypeAdapter(context, chooseCustomerTypeInterFace , dataManager)

        customerTypeRecyclerView.layoutManager = LinearLayoutManager(context)
        customerTypeRecyclerView.adapter = dapter

        //getStartPointList()
    }

/*
    public fun getStartPointList() {


        customerTypeProgressBar.visibility = View.VISIBLE
        myAPI?.getFilterData(dataManager.user.accId!!, "AddnewCustomerListType", "0", "0")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterData>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterData>) {

                        list = data
                        dapter.setMyData(data)
                    }

                    override fun onError(e: Throwable) {

                        customerTypeProgressBar.visibility = View.GONE
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

                    }

                    override fun onComplete() {
                        customerTypeProgressBar.visibility = View.GONE
                    }
                })

    }*/
}
