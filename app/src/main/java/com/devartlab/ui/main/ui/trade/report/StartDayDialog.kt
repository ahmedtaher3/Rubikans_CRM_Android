package com.devartlab.ui.main.ui.trade.report

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class StartDayDialog(context: Context, private val myAPI: ApiServicesGoogle, private val tradeInterface: TradeInterface) : Dialog(context) , ReportCustomersAdapter.OnCustomerReportSelect {


    lateinit var close: ImageView
    lateinit var adapter: ReportCustomersAdapter
    lateinit var customers: RecyclerView
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_customer)

        close = findViewById(R.id.close)
        customers = findViewById(R.id.customers)
        progressBar = findViewById(R.id.progressBar)
        adapter = ReportCustomersAdapter(context , ArrayList() , this)
        customers.adapter = adapter

        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })

        getCustomers()
    }

    override fun setOnCustomerSelect(model: CustomerAcher) {

        tradeInterface.onTradeStartDay(model.id.toString() , model.name.toString())
        dismiss()


    }


    fun getCustomers() {
        progressBar.visibility = View.VISIBLE
        myAPI?.trade("Acher Customers", "getAll", "", "", "", "", "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        progressBar.visibility = View.GONE
                        adapter.setMyData(data.customersAchers!!)
                        System.out.println( "  taag " + data.customers)

                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                        println(e.message)
                        System.out.println( " taag " + e.message)
                    }

                    override fun onComplete() {}
                })

    }

}
