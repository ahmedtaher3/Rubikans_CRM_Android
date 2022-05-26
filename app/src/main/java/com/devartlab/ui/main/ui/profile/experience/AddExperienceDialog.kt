package com.devartlab.ui.main.ui.employeeservices.expenses.add

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.DTExperience
import com.devartlab.model.JobsModel
import com.devartlab.ui.main.ui.profile.experience.OnAddExperienceClick
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_experirnce_dialog.*
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class AddExperienceDialog(private var activity: Context, var onAddExperienceClick: OnAddExperienceClick , private val dataManager: DataManager) : Dialog(activity) {

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null

    var jobsList: ArrayList<String>? = null
    var jobsListIds: ArrayList<Int>? = null

    var jobID = 0
    var jobName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_experirnce_dialog)
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)


        jobsList = ArrayList()
        jobsListIds = ArrayList()
        fromExperience.setOnClickListener(View.OnClickListener {


            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        fromExperience.setText((monthOfYear + 1).toString() + "-" + year.toString())

                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        })



        toExperience.setOnClickListener(View.OnClickListener {


            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        toExperience.setText((monthOfYear + 1).toString() + "-" + year.toString())

                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


        })

        closeImageView.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        add.setOnClickListener(View.OnClickListener {
            onAddExperienceClick.setOnAddExperienceClick(DTExperience(fromExperience.text.toString(),
                    toExperience.text.toString(),
                    companyExperience.text.toString(),
                    jobsSpinner.text.toString(),
                    descExperience.text.toString(),
                    trainingCheckBox.isChecked,
                    jobID,
                    0
            ))

            dismiss()
        })


        jobsSpinner.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, jobsList!!))

        jobsSpinner.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

            jobID = jobsListIds!![i]
            jobName = jobsList!![i]
        })

        getFilterLines(19, "TblDefJobs", "", "0")


    }

    public fun getFilterLines(id: Int, tableName: String, whereCondition: String, filterText: String) {


        myAPI?.getJobs(id, tableName, whereCondition, filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<JobsModel>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<JobsModel>) {


                        for (model in data) {
                            jobsList?.add(model.jobArName)
                            jobsListIds?.add(model.jobtId)
                        }
                        jobsSpinner.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, jobsList!!))

                    }

                    override fun onError(e: Throwable) {


                    }

                    override fun onComplete() {}
                })
    }


}
