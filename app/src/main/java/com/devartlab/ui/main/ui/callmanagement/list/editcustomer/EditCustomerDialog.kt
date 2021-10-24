package com.devartlab.ui.main.ui.callmanagement.list.editcustomer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.shared.DataManager
import kotlinx.android.synthetic.main.edit_customer_dialog.*
import retrofit2.Retrofit

class EditCustomerDialog(context: Context, var dataManager: DataManager, var editCustomerInterface: EditCustomerInterface, var listEntity: ListEntity) : Dialog(context) {

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    lateinit var speciality: ArrayList<String>
    lateinit var specialityIds: ArrayList<Int>

    lateinit var classs: ArrayList<String>
    lateinit var classIds: ArrayList<Int>


    var classString: String = ""
    var classID: Int = 0
    var specialityString: String = ""
    var specialityID: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_customer_dialog)
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        val list = ArrayList<String>()

        classString = listEntity.cusClassEnName!!
        classID = listEntity.cusClassId!!
        specialityString = listEntity.cusTypeEnName!!
        specialityID = listEntity.customerTypeId!!

        ClassSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))
        SpecialitySpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))



        ClassSpinner.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            classString = classs[i]
            classID = classIds[i]
        })

        SpecialitySpinner.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            specialityString = speciality[i]
            specialityID = specialityIds[i]
        })

        OK.setOnClickListener(View.OnClickListener {

            val newCustomer: ListEntity = listEntity

            newCustomer.cusClassEnName = classString
            newCustomer.cusClassId = classID
            newCustomer.cusTypeEnName = specialityString
            newCustomer.customerTypeId = specialityID

            editCustomerInterface.editCustomer(newCustomer)
        })



        speciality = ArrayList()
        specialityIds = ArrayList()
        classs = ArrayList()
        classIds = ArrayList()



     //   getClassesList()
     //   getSpecialitiesList()
    }
/*
    public fun getClassesList() {


        EditCustomerProgressBar.visibility = View.VISIBLE
        myAPI?.getFilterData(dataManager.user.accId!!, "TblDefCustomerClass", "and 1=1", "0")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterData>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterData>) {


                        classs.clear()
                        classIds.clear()
                        for (model in data) {
                            classs.add(model.empName)
                            classIds.add(model.empId)
                        }
                        ClassSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, classs))

                    }

                    override fun onError(e: Throwable) {

                        EditCustomerProgressBar.visibility = View.GONE

                    }

                    override fun onComplete() {
                        EditCustomerProgressBar.visibility = View.GONE
                    }
                })

    }*/

  /*  public fun getSpecialitiesList() {


        myAPI?.getFilterData(dataManager.user.accId!!, "TblDefCustomerType", "and 1=1", "0")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterData>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterData>) {


                        speciality.clear()
                        specialityIds.clear()
                        for (model in data) {
                            speciality.add(model.empName)
                            specialityIds.add(model.empId)
                        }
                        SpecialitySpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, speciality))


                    }

                    override fun onError(e: Throwable) {

                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

                    }

                    override fun onComplete() {
                    }
                })

    }*/
}
