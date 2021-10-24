package com.devartlab.ui.main.ui.employeeservices.expenses.add

import android.app.Dialog
import android.content.Context
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
import com.devartlab.model.Penalties
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_penalties_dialog.*
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPenaltiesDialog(private var activity: Context, var dataManager: DataManager, var date: String, var employeeId: String, var model: Penalties, var isEdit: Boolean) : Dialog(activity) {

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    lateinit var speciality: ArrayList<String>
    lateinit var specialityIds: ArrayList<Int>
    var month: String = "0"
    var _year: String = "0"
    var ReasonID: String = "0"
    var typeID: String = "0"
    var fmt: SimpleDateFormat? = null
    lateinit var PenaltyTypes: ArrayList<String>
    lateinit var PenaltyTypesIdes: ArrayList<Int>
    lateinit var ReasonTypes: ArrayList<String>
    lateinit var ReasonTypesIdes: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_penalties_dialog)
        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        PenaltyTypes = ArrayList()
        PenaltyTypesIdes = ArrayList()
        ReasonTypes = ArrayList()
        ReasonTypesIdes = ArrayList()

        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        DatePenalty.setText(fmt?.format(date.toLong()))
        val list = ArrayList<String>()


        if (isEdit) {
            System.out.println(model.toString())
            typeID = model.penaltyTypeId.toString()
            ReasonID = model.penaltyReasonId.toString()

            DatePenalty.setText(model.penaltyDate.take(10))
            TypePenalty.setText(model.penaltyArName.toString())
            ReasonTypePenalty.setText(model.penReasonArName.toString())
            QtyPenalty.setText(model.penaltyQty.toString())
            NotesPenalty.setText(model.notes.toString())

            AddPenalty.text = "Edit"
            PenaltiesDialogTitle.text = "Edit Expense"
        }

        TypePenalty.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))
        TypePenalty.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            typeID = PenaltyTypesIdes[i].toString()

        }
        ReasonTypePenalty.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))
        ReasonTypePenalty.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            ReasonID = ReasonTypesIdes[i].toString()

        }



        close.setOnClickListener(View.OnClickListener {

            dismiss()
        })

        /*  DateExpense.setOnClickListener(View.OnClickListener {


              val c: Calendar = Calendar.getInstance()
              var mYear = c.get(Calendar.YEAR)
              var mMonth = c.get(Calendar.MONTH)
              var mDay = c.get(Calendar.DAY_OF_MONTH)


              val datePickerDialog = DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                      DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                          month = (monthOfYear + 1).toString()
                          _year = year.toString()
                          DateExpense.setText(  (monthOfYear + 1).toString() + "-" +year.toString())

                      }, mYear, mMonth, mDay)
              datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()


          })*/

        AddPenalty.setOnClickListener(View.OnClickListener {

            if (isEdit) {
                Toast.makeText(context, model.penaltyId.toString(), Toast.LENGTH_SHORT).show()
                saveUpdatePenalty("false", model.penaltyId.toString(), QtyPenalty.text.toString(), NotesPenalty.text.toString(), "android")

            } else {
                saveUpdatePenalty("true", "0", QtyPenalty.text.toString(), NotesPenalty.text.toString(), "android")
            }

        })


        getPenaltiesType()
        getPenaltiesReason()

    }

    fun saveUpdatePenalty(isInsert: String, penaltyId: String, Qty: String, Notes: String, AddMac: String) {

        ProgressLoading.show(activity)



        myAPI?.saveUpdatePenalty(isInsert,
                dataManager.user.accId,
                dataManager.user.empId,
                employeeId.toInt(),
                penaltyId,
                typeID,
                ReasonID,
                CommonUtilities.currentToMillis.toString(),
                Qty,
                Notes + " ",
                AddMac)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {
                        ProgressLoading.dismiss()
                        Toast.makeText(context, data.rerurnMessage, Toast.LENGTH_SHORT).show()

                        if (data.isSuccesed) {
                            dismiss()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        ProgressLoading.dismiss()

                    }

                    override fun onComplete() {
                    }
                })

    }


    fun getPenaltiesType() {


        myAPI?.penaltiesType!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {


                        if (data.isSuccesed) {

                            for (model in data.data.penaltiesType) {
                                PenaltyTypes.add(model.penaltyArName)
                                PenaltyTypesIdes.add(model.penaltyTypeId)
                            }

                            TypePenalty.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, PenaltyTypes))

                        } else {
                            Toast.makeText(context, data.rerurnMessage, Toast.LENGTH_SHORT).show()
                        }


                    }

                    override fun onError(e: Throwable) {


                    }

                    override fun onComplete() {
                    }
                })

    }

    fun getPenaltiesReason() {


        myAPI?.penaltiesReason!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {

                        if (data.isSuccesed) {
                            for (model in data.data.penaltiesReason) {
                                ReasonTypes.add(model.penReasonArName)
                                ReasonTypesIdes.add(model.penReasonId)
                            }

                            ReasonTypePenalty.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, ReasonTypes))

                        } else {
                            Toast.makeText(context, data.rerurnMessage, Toast.LENGTH_SHORT).show()
                        }


                    }

                    override fun onError(e: Throwable) {


                    }

                    override fun onComplete() {
                    }
                })

    }

}
