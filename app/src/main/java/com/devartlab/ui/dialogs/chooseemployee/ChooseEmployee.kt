package com.devartlab.ui.dialogs.chooseemployee

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.AdModel
import com.devartlab.model.CustomerList
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import com.jarvanmo.exoplayerview.ui.ExoVideoView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ss.com.bannerslider.Slider

class ChooseEmployee(
    context: Context,
    private var chooseEmployeeInterFace: ChooseEmployeeInterFace,
    private var dataManager: DataManager
) : Dialog(context), EmployeeSearchAdapter.OnEmployeeFilterClick,
    EmployeeSearchSelectedAdapter.OnFilterEmployeesChange {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewSelected: RecyclerView
    lateinit var close: ImageView
    lateinit var adapter: EmployeeSearchAdapter
    lateinit var adapterSelected: EmployeeSearchSelectedAdapter
    lateinit var progressBar: ProgressBar


    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    var editText: EditText? = null
    var list: ArrayList<CustomerList>? = null
    var lastId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_employee)


        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        progressBar = findViewById(R.id.progressBar)
        close = findViewById(R.id.close)
        editText = findViewById(R.id.editText_search)
        lastId = dataManager.user.empId

        recyclerView = findViewById(R.id.recyclerView)
        adapter = EmployeeSearchAdapter(context, chooseEmployeeInterFace, this , dataManager)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        recyclerViewSelected = findViewById(R.id.recyclerViewSelected)
        adapterSelected = EmployeeSearchSelectedAdapter(context, this)
        recyclerViewSelected.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSelected.adapter = adapterSelected

        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if (TextUtils.isEmpty(charSequence.toString()))
                    getFilterEmpl(lastId, "0")
                else
                    getFilterEmpl(lastId, charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        getFilterEmpl(lastId, "0")

    }

    public fun getFilterEmpl(empId: Int, filterText: String) {

        System.out.println("accountId  = " + empId + "filterText = " + filterText)
        progressBar.visibility = View.VISIBLE
        myAPI?.filterEmployees(empId, "0")!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(data: ArrayList<FilterDataEntity>) {


                    print(data)
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

    override fun setOnEmployeeFilterClick(model: FilterDataEntity?) {


        adapterSelected.addItem(model)

    }

    override fun onChange(employeesList: MutableList<FilterDataEntity>?) {


        if (employeesList.isNullOrEmpty()) {
            lastId = dataManager.user.empId
            getFilterEmpl(lastId, "0")
            print("lastId  " + lastId)
        } else {
            lastId = employeesList.last().empId!!
            getFilterEmpl(lastId, "0")
            print("lastId  " + lastId)
        }

    }


}
