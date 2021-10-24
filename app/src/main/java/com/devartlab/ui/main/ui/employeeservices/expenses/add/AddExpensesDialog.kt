package com.devartlab.ui.main.ui.employeeservices.expenses.add

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.AddExpensesDialogBinding
import com.devartlab.model.Expenses
import com.devartlab.model.ExpensesType
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.CommonUtilities.DecodeFile
import com.devartlab.utils.CommonUtilities.getCustomImagePath
import com.devartlab.utils.ProgressLoading
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddExpensesDialog(private var activity: Context,
                        var dataManager: DataManager,
                        var date: String, var model: Expenses,
                        var isEdit: Boolean) : DialogFragment() {

    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    lateinit var speciality: ArrayList<String>
    var LastKmValue: Int = 0
    var typeID: String = "0"
    var maxValueLimit: Double = 0.00
    var fmt: SimpleDateFormat? = null
    lateinit var expensesTypes: ArrayList<String>
    lateinit var expensesTypesIdes: ArrayList<Int>
    lateinit var expensesTypeList: ArrayList<ExpensesType>
    var mCapturedImageUrl: String? = null

    lateinit var binding: AddExpensesDialogBinding
    var file: File? = null
    private var mRootView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_expenses_dialog, container, false)
        mRootView = binding?.getRoot()
        val builder = StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        retrofit = RetrofitClient.getInstance()
        myAPI = retrofit!!.create(ApiServices::class.java)
        expensesTypes = ArrayList()
        expensesTypesIdes = ArrayList()

        fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        binding.DateExpense.setText(fmt?.format(CommonUtilities.currentToMillis))
        val list = ArrayList<String>()


        if (isEdit) {
            typeID = model.expTypeId.toString()


            binding.QtyExpense.setText(model.qty.toString())
            binding.ValueExpense.setText(model.value.toString())
            binding.TotalValueExpense.setText(model.totalValue.toString())
            binding.NotesExpense.setText(model.notes.toString())
            try {
                binding.PreviousKmValueExpense.setText(model.previousKiloMeterValue.toString())
                binding.CurrentKmValueExpense.setText(model.currentKiloMeterValue.toString())

            } catch (e: Exception) {
            }

            if (model.expTypeId == 9) {
                binding.GasolineLayout.visibility = View.VISIBLE
                binding.AddNewImage.visibility = View.VISIBLE
            } else {
                binding.GasolineLayout.visibility = View.GONE
                binding.AddNewImage.visibility = View.GONE
            }

            binding.DateExpense.setText(model.expDate.take(10))
            binding.ExpenseTypeExpense.setText(model.expTypeArName)
            binding.AddExpense.setText("Edit")
            binding.ExpenseDialogTitle.setText("Edit Expense")
        }

        binding.ExpenseTypeExpense.setAdapter(ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, list))
        binding.ExpenseTypeExpense.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            typeID = expensesTypesIdes[i].toString()
            maxValueLimit = expensesTypeList[i].maxValueLimit

            if (expensesTypesIdes[i] == 9) {

                binding.GasolineLayout.visibility = View.VISIBLE
                binding.AddNewImage.visibility = View.VISIBLE
                binding.PreviousKmValueExpense.setText(LastKmValue.toString())

            } else {
                binding.GasolineLayout.visibility = View.GONE
                binding.AddNewImage.visibility = View.GONE
            }

            if (expensesTypeList[i].constantValue > 0.00) {
                binding.ValueExpense.setText(expensesTypeList[i].constantValue.toString())
                binding.ValueExpense.setFocusable(false)
                binding.QtyExpense.setFocusable(false)
            } else if (expensesTypeList[i].constantValue == 0.00) {
                binding.ValueExpense.setText("")
                binding.ValueExpense.setFocusableInTouchMode(true)
                binding.QtyExpense.setFocusableInTouchMode(true)
            }

        })


        binding.QtyExpense.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


                try {
                    val value = binding.QtyExpense.text.toString().toDouble() * binding.ValueExpense.text.toString().toDouble()
                    val formatter: NumberFormat = DecimalFormat("#0.0")
                    binding.TotalValueExpense.setText(formatter.format(value))
                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // TODO Auto-generated method stub
            }
        })

        binding.CurrentKmValueExpense.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


                try {
                    binding.QtyExpense.setText((binding.CurrentKmValueExpense.text.toString().toDouble() - binding.PreviousKmValueExpense.text.toString().toDouble()).toString())

                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // TODO Auto-generated method stub
            }
        })


        binding.ValueExpense?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                try {

                    val value = binding.QtyExpense.text.toString().toDouble() * binding.ValueExpense.text.toString().toDouble()
                    val formatter: NumberFormat = DecimalFormat("#0.0")
                    binding.TotalValueExpense.setText(formatter.format(value))

                } catch (e: Exception) {

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) { // TODO Auto-generated method stub
            }
        })

        binding.AddNewImage.setOnClickListener(View.OnClickListener {


            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val file: File = getCustomImagePath(activity, CommonUtilities.currentToMillis.toString() + "")!!
                mCapturedImageUrl = file.absolutePath
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
                startActivityForResult(takePicture, 30)
            } else {
                requestCameraPermission()
            }


        })

        binding.DateExpense.setOnClickListener(View.OnClickListener {


            val c: Calendar = Calendar.getInstance()
            var mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            var mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->


                        val cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.YEAR, year);
                        fmt?.format(cal.timeInMillis)

                        if (CommonUtilities.getDifferenceDays((Date(CommonUtilities.currentToMillis)), Date(cal.timeInMillis)).toInt() > 0 || CommonUtilities.getDifferenceDays(Date(CommonUtilities.currentToMillis), Date(cal.timeInMillis)).toInt() < -2) {
                            Toast.makeText(activity, "wrong day", Toast.LENGTH_SHORT).show()

                        } else {
                            binding.DateExpense.setText(fmt?.format(cal.timeInMillis))

                        }


                    }, mYear, mMonth, mDay)
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()

        })


        binding.close.setOnClickListener(View.OnClickListener {

            dismiss()
        })

        binding.AddExpense.setOnClickListener(View.OnClickListener {
            // updateImage(file)

            if (typeID.equals("0")) {
                Toast.makeText(activity, "please choose type", Toast.LENGTH_SHORT).show()
            } else if (binding.ValueExpense.text.toString().isEmpty()) {
                Toast.makeText(activity, "please enter the value ", Toast.LENGTH_SHORT).show()
            } else if (binding.QtyExpense.text.toString().isEmpty()) {
                Toast.makeText(activity, "please enter the Quantity", Toast.LENGTH_SHORT).show()
            } else {
                if (isEdit) {
                    SaveUpdateExpense("false", model.expId.toString(), typeID,
                            date, CommonUtilities.checkIfTextEmpty(binding.PreviousKmValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.CurrentKmValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.QtyExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.ValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.TotalValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.NotesExpense.text.toString()), "android")

                } else {

                    if (maxValueLimit != 0.00) {
                        if (maxValueLimit < binding.ValueExpense.text.toString().toDouble()) {
                            binding.ValueExpense.error = "Max Value Limit :" + maxValueLimit.toString()
                            Toast.makeText(activity, "Max Value Limit :" + maxValueLimit.toString(), Toast.LENGTH_SHORT).show()

                            return@OnClickListener
                        }
                    }


                    SaveUpdateExpense("true", "0", typeID,
                            date, CommonUtilities.checkIfTextEmpty(binding.PreviousKmValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.CurrentKmValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.QtyExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.ValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.TotalValueExpense.text.toString()),
                            CommonUtilities.checkIfTextEmpty(binding.NotesExpense.text.toString()), "android")

                }
            }


        })

        GetExpensesType()


        return mRootView


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 30) {

                file = File(DecodeFile(dataManager.user.accId.toString(), mCapturedImageUrl, 1000, 1000))

                val myBitmap = BitmapFactory.decodeFile(file!!.getAbsolutePath());
                binding.expenseImage.visibility = View.VISIBLE
                binding.expenseImage.setImageBitmap(myBitmap);
                // binding.expenseImage.setImageURI(Uri.fromFile(file))

            } else if (requestCode == 20) {

            }
        }

    }


    public fun SaveUpdateExpense(isInsert: String, ExpId: String, ExpTypeId: String, ExpDateMsFormat: String, PreviousKiloMeterValue: String, CurrentKiloMeterValue: String, Qty: String, Value: String, TotalValue: String, Notes: String, AddMac: String) {


        if (typeID == "9") {

            if (file == null) {
                Toast.makeText(activity, "Please Add Value Image", Toast.LENGTH_SHORT).show()
            } else {
                ProgressLoading.show(activity)
                val body_2 = MultipartBody.Part.createFormData(
                        "file",
                        file!!.name,
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!))




                myAPI?.SaveUpdateExpense(isInsert, dataManager.user.accId!!, dataManager.user.empId!!,
                        ExpId.toInt(), ExpTypeId, ExpDateMsFormat, PreviousKiloMeterValue, CurrentKiloMeterValue, Qty, Value, TotalValue, Notes, AddMac, body_2)!!
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Observer<ResponseModel> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onNext(data: ResponseModel) {

                                Toast.makeText(context, data.rerurnMessage, Toast.LENGTH_SHORT).show()

                                if (data.isSuccesed) {
                                    dismiss()
                                }
                                ProgressLoading.dismiss()
                            }

                            override fun onError(e: Throwable) {
                                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                                ProgressLoading.dismiss()


                            }

                            override fun onComplete() {
                            }
                        })
            }

        } else {


            ProgressLoading.show(activity)

            val file = RequestBody.create(MultipartBody.FORM, "")
            val body = MultipartBody.Part.createFormData("file", "", file)

            myAPI?.SaveUpdateExpense(isInsert, dataManager.user.accId!!, dataManager.user.empId!!,
                    ExpId.toInt(), ExpTypeId, ExpDateMsFormat, PreviousKiloMeterValue, CurrentKiloMeterValue, Qty, Value, TotalValue, Notes, AddMac, body)!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<ResponseModel> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(data: ResponseModel) {

                            Toast.makeText(context, data.rerurnMessage, Toast.LENGTH_SHORT).show()

                            if (data.isSuccesed) {
                                dismiss()
                            }
                            ProgressLoading.dismiss()
                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                            ProgressLoading.dismiss()


                        }

                        override fun onComplete() {
                        }
                    })
        }


    }

    public fun GetExpensesType() {


        myAPI?.GetExpensesType(dataManager.user.accId!!, dataManager.user.empId!!, dataManager.user.titleId.toString())!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseModel> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ResponseModel) {

                        if (data.isSuccesed) {
                            expensesTypeList = data.data.expensesType
                            LastKmValue = data.data.expensesTypeDetails[0].lastKmValue

                            for (model in data.data.expensesType) {
                                expensesTypes.add(model.expTypeArName)
                                expensesTypesIdes.add(model.expTypeId)
                            }

                            binding.ExpenseTypeExpense.setAdapter(ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, expensesTypes))

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


    fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity as Activity, Manifest.permission.CAMERA)) {
            AlertDialog.Builder(activity)
                    .setTitle("permission denied")
                    .setMessage("ask for permission again")
                    .setPositiveButton("ok") { dialog, which -> ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22) }
                    .setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
                    .create().show()
        } else {
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22)
        }
    }


}
