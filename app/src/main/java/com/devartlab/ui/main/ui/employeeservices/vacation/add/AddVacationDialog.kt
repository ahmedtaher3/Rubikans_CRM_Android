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
import android.widget.Toast
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.model.EmployeeVacation
import com.devartlab.model.VacationBallance
import com.devartlab.utils.ProgressLoading
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_vacations_dialog.*
import retrofit2.Retrofit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddVacationDialog(private var activity: Context, var dataManager: DataManager, var employeeVacation: EmployeeVacation, var isEdit: Boolean) : Dialog(activity) {

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    lateinit var speciality: ArrayList<String>
    lateinit var specialityIds: ArrayList<Int>
    var month: String = "0"
    var fromDate: String = "0"
    var toDate: String = "0"
    var typeID: String = "0"
    var vacationsBallance: String = "0"
    var fmt: SimpleDateFormat? = null
    lateinit var VacationTypes: ArrayList<String>
    lateinit var VacationTypesIdes: ArrayList<Int>
    lateinit var ballanceList: java.util.ArrayList<VacationBallance>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_vacations_dialog)
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        VacationTypes = ArrayList()
        VacationTypesIdes = ArrayList()


        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val list = ArrayList<String>()


        if (isEdit) {
            val from = employeeVacation.fromDate.take(10)
            val to = employeeVacation.toDate.take(10)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            try {
                val mFromDate = sdf.parse(from)
                val mToDate = sdf.parse(to)
                fromDate = mFromDate.time.toString()
                toDate = mToDate.time.toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            typeID = employeeVacation.vacTypeId.toString()
            fromVacation.setText(employeeVacation.fromDate.take(10))
            toVacation.setText(employeeVacation.fromDate.take(10))
            NotesVacation.setText(employeeVacation.notes)
            typeVacation.setText(employeeVacation.vacTypeArName)
            QtyVacation.setText(employeeVacation.dayNum.toString())
        }


        fromVacation.setOnFocusChangeListener { view, b ->

            if (b) {

                val c: Calendar = Calendar.getInstance()
                var mYear = c.get(Calendar.YEAR)
                var mMonth = c.get(Calendar.MONTH)
                var mDay = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            fromVacation.setText(year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString())


                            val cal = Calendar.getInstance();
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            cal.set(Calendar.MONTH, monthOfYear);
                            cal.set(Calendar.YEAR, year);

                            fromDate = cal.timeInMillis.toString()


                        }, mYear, mMonth, mDay)
                datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                datePickerDialog.show()
            }
        }

        toVacation.setOnFocusChangeListener { view, b ->

            if (b) {

                val c: Calendar = Calendar.getInstance()
                var mYear = c.get(Calendar.YEAR)
                var mMonth = c.get(Calendar.MONTH)
                var mDay = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            toVacation.setText(year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString())


                            val cal = Calendar.getInstance();
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            cal.set(Calendar.MONTH, monthOfYear);
                            cal.set(Calendar.YEAR, year);

                            toDate = cal.timeInMillis.toString()


                        }, mYear, mMonth, mDay)
                datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                datePickerDialog.show()
            }
        }

        typeVacation.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, list))
        typeVacation.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            typeID = VacationTypesIdes[i].toString()

        })



        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })

        /*  DateExpense.setOnClickListener(View.OnClickListener {


              val c: Calendar = Calendar.getInstance()
              var mYear = c.get(Calendar.YEAR)
              var mMonth = c.get(Calendar.MONTH)
              var mDay = c.get(Calendar.DAY_OF_MONTH)


              val datePickerDialog = DatePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                      DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                          month = (monthOfYear + 1).toString()
                          _year = year.toString()
                          DateExpense.setText(  (monthOfYear + 1).toString() + "-" +year.toString())

                      }, mYear, mMonth, mDay)
              datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


          })*/

        AddVacation.setOnClickListener(View.OnClickListener {


            if (ballanceList.get(0).enforceVacationTypeBallance == 0) {


                System.out.println(ballanceList.get(0).reminder)
                System.out.println(QtyVacation.text.toString().toDouble())
                val x = ballanceList.get(0).reminder - QtyVacation.text.toString().toDouble()

                if (x > 0) {

                    if (isEdit) {
                        saveUpdateVacation("false", employeeVacation.vacId.toString(), QtyVacation.text.toString(), "false", "0", NotesVacation.text.toString(), "android")

                    } else {
                        saveUpdateVacation("true", "0", QtyVacation.text.toString(), "false", "0", NotesVacation.text.toString(), "android")

                    }


                } else {

                    if (isEdit) {
                        saveUpdateVacation("false", employeeVacation.vacId.toString(), QtyVacation.text.toString(), "true", x.toString(), NotesVacation.text.toString(), "android")

                    } else {
                        saveUpdateVacation("true", "0", QtyVacation.text.toString(), "true", x.toString(), NotesVacation.text.toString(), "android")

                    }


                }
            } else {

                for (model in ballanceList) {
                    if (model.vacTypeId == typeID.toInt()) {

                        val x: Double = ballanceList.get(0).reminder - QtyVacation.text.toString().toDouble()

                        if (x > 0) {
                            if (isEdit) {
                                saveUpdateVacation("false", employeeVacation.vacId.toString(), QtyVacation.text.toString(), "false", "0", NotesVacation.text.toString(), "android")

                            } else {
                                saveUpdateVacation("true", "0", QtyVacation.text.toString(), "false", "0", NotesVacation.text.toString(), "android")

                            }
                        } else {

                            if (isEdit) {
                                saveUpdateVacation("false", employeeVacation.vacId.toString(), QtyVacation.text.toString(), "true", x.toString(), NotesVacation.text.toString(), "android")

                            } else {
                                saveUpdateVacation("true", "0", QtyVacation.text.toString(), "true", x.toString(), NotesVacation.text.toString(), "android")

                            }


                        }

                        return@OnClickListener
                    }
                }

            }


        })

        getVacationsType();
        getVacationBallance();
    }


    public fun saveUpdateVacation(isInsert: String, vacationId: String, Qty: String, deduct: String, salaryDeductDaysQty: String, Notes: String, AddMac: String) {


        myAPI?.saveUpdateVacation(isInsert,
                dataManager.user.accId ,
                dataManager.user.empId ,
                vacationId.toInt(),
                typeID.toInt(),
                Qty,
                fromDate,
                toDate,
                deduct,
                salaryDeductDaysQty,
                Notes,
                AddMac)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {


                        Toast.makeText(activity, data.rerurnMessage, Toast.LENGTH_SHORT).show()

                        if (data.isSuccesed) {
                            dismiss()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()


                    }

                    override fun onComplete() {
                    }
                })

    }


    public fun getVacationsType() {


        myAPI?.vacationType!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {

                        if (data.isSuccesed) {


                            for (model in data.data.vacationType) {
                                VacationTypes.add(model.vacTypeArName)
                                VacationTypesIdes.add(model.vacTypeId)
                            }

                            typeVacation.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, VacationTypes))

                        } else {
                            Toast.makeText(activity, data.rerurnMessage, Toast.LENGTH_SHORT).show()
                        }


                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }

    public fun getVacationBallance() {

        ProgressLoading.show(activity)
        myAPI?.getVacationBallance(dataManager.user.accId ,
                dataManager.user.empId.toString())!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {

                        System.out.println(data.data.vacationBallances.get(0).toString())

                        ballanceList = data.data.vacationBallances
                        ProgressLoading.dismiss()
                    }

                    override fun onError(e: Throwable) {
                        ProgressLoading.dismiss()
                        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {

                    }
                })
    }


}
