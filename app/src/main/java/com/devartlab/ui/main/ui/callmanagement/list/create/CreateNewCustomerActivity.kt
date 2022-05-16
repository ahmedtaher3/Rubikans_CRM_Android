package com.devartlab.ui.main.ui.callmanagement.list.create

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.data.room.list.DefCustomerAddressModel
import com.devartlab.data.room.list.DefSupplierKeyPersonModel
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.room.listtypes.ListTypesEntity
import com.devartlab.databinding.ActivityCreateNewCustomerBinding
import com.devartlab.utils.ProgressLoading
import com.google.android.material.textfield.TextInputLayout

class CreateNewCustomerActivity : BaseActivity<ActivityCreateNewCustomerBinding>() {

    lateinit var binding: ActivityCreateNewCustomerBinding
    lateinit var viewModel: CreateNewCustomerViewModel

    var typeList = ArrayList<ListTypesEntity>()
    var typeListNames = ArrayList<String>()
    var typeListModel = ListTypesEntity()
    var typeId = 0

    var customerTypeList = ArrayList<FilterDataEntity>()
    var customerTypeNames = ArrayList<String>()
    var customerModel = FilterDataEntity()
    var customerTypeId = 0


    var territoryList = ArrayList<FilterDataEntity>()
    var territoryNames = ArrayList<String>()
    var territoryModel = FilterDataEntity()
    var territoryId = 0

    var brickList = ArrayList<FilterDataEntity>()
    var brickNames = ArrayList<String>()
    var brickModel = FilterDataEntity()
    var brickId = 0

    var classList = ArrayList<FilterDataEntity>()
    var classNames = ArrayList<String>()
    var classModel = FilterDataEntity()
    var classId = 0


    var addressList = ArrayList<DefCustomerAddressModel>()
    var keyPersonList = ArrayList<DefSupplierKeyPersonModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(CreateNewCustomerViewModel::class.java)



        (binding.listType.editText as? AutoCompleteTextView)?.setAdapter(
            ArrayAdapter(
                this,
                R.layout.adapter_list_item,
                typeListNames
            )
        )
        (binding.listType.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            typeId = typeList[i].listTypeId!!
            typeListModel = typeList[i]!!
            viewModel.getFilterSpecialityChild(
                viewModel?.dataManager?.user?.accId.toString(),
                "TblDefCustomerType",
                "and CusTypeParentId=$typeId",
                "0",typeId
            )

        }


        (binding.customerType.editText as? AutoCompleteTextView)?.setAdapter(
            ArrayAdapter(
                this,
                R.layout.adapter_list_item,
                customerTypeNames
            )
        )
        (binding.customerType.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
            customerTypeId = customerTypeList[i].fieldId!!
            customerModel = customerTypeList[i]!!

        }




        viewModel.getTypes()
        viewModel.getFilterTerriotry(
            viewModel?.dataManager?.user?.accId.toString(),
            "TblAnalyDefSalesTerriotry",
            "0",
            "0"
        )
        viewModel.getFilterClass(
            viewModel?.dataManager?.user?.accId.toString(),
            "TblDefCustomerClass",
            "",
            "0"
        )




        binding.add.setOnClickListener {

            viewModel.addCustomer(
                ListEntity(
                    0
                    , customerModel.fieldId
                    , ""
                    , viewModel.dataManager.user.empId
                    , 0
                    , 0
                    , 0
                    , 0
                    , 0
                    , binding.notes.text.toString(),
                    "",
                    brickModel.fieldName,
                    brickModel.fieldId,
                    0,
                    "",
                    0,
                    binding.name.text.toString(),
                    customerModel.fieldName,
                    classModel.fieldName,
                    classModel.fieldId,
                    customerModel.fieldId,
                    binding.address.text.toString(),
                    typeListModel.listTypeId,
                    0,
                    customerModel.fieldName,
                    customerModel.fieldName,
                    customerModel.fieldId,
                    classModel.fieldId,
                    viewModel.dataManager.user.nameAr,
                    viewModel.dataManager.user.nameEn,
                    0,
                    0,
                    0,
                    0,
                    viewModel.dataManager.user.accId,
                    "",
                    0,
                    binding.phone1.text.toString(),
                    binding.phone2.text.toString(),
                    territoryId,
                    "",
                    false,
                    0,
                    0,
                    "",
                    "",
                    ""))
        }

     //   R.drawable.rounded_blue


        binding.addNewAddress.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this@CreateNewCustomerActivity) // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater

            val dialogView = inflater.inflate(R.layout.add_new_address, null)
            dialogBuilder.setView(dialogView)
            val addButton = dialogView.findViewById<View>(R.id.add) as Button
            val skipButton = dialogView.findViewById<View>(R.id.close) as ImageView


            val territory = dialogView.findViewById<View>(R.id.territory) as TextInputLayout
            val brick = dialogView.findViewById<View>(R.id.brick) as TextInputLayout
            val classs = dialogView.findViewById<View>(R.id.classs) as TextInputLayout


            (territory.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.adapter_list_item,
                    territoryNames
                )
            )
            (territory.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
                territoryId = territoryList[i].fieldId!!
                territoryModel = territoryList[i]!!
                viewModel.getFilterBrik(
                    viewModel?.dataManager?.user?.accId.toString(),
                    "TblDefBrick",
                    territoryId.toString(),
                    "0"
                )


            }


            (brick.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.adapter_list_item,
                    brickNames
                )
            )
            (brick.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
                brickId = brickList[i].fieldId!!
                brickModel = brickList[i]!!

            }

            (classs.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.adapter_list_item,
                    classNames
                )
            )
            (classs.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->
                classId = classList[i].fieldId!!
                classModel = classList[i]!!

            }


            viewModel.filterTerriotryResponseLive.observe(this, Observer {
                territoryList.clear()
                territoryList = it as ArrayList<FilterDataEntity>

                for (model in it) {
                    territoryNames.add(model.fieldName!!)
                }
                (territory.editText as? AutoCompleteTextView)?.setAdapter(null)
                (territory.editText as? AutoCompleteTextView)?.setAdapter(
                    ArrayAdapter(
                        this,
                        R.layout.adapter_list_item,
                        territoryNames
                    )
                )


            })


            viewModel.filterBrikResponseLive.observe(this, Observer {
                brickList.clear()
                brickList = it as ArrayList<FilterDataEntity>

                for (model in it) {
                    brickNames.add(model.fieldName!!)
                }
                (brick.editText as? AutoCompleteTextView)?.setAdapter(null)
                (brick.editText as? AutoCompleteTextView)?.setAdapter(
                    ArrayAdapter(
                        this,
                        R.layout.adapter_list_item,
                        brickNames
                    )
                )


            })

            viewModel.filterClassResponseLive.observe(this, Observer {
                classList.clear()
                classList = it as ArrayList<FilterDataEntity>

                for (model in it) {
                    classNames.add(model.fieldName!!)
                }

                (classs.editText as? AutoCompleteTextView)?.setAdapter(null)

                (classs.editText as? AutoCompleteTextView)?.setAdapter(
                    ArrayAdapter(
                        this,
                        R.layout.adapter_list_item,
                        classNames
                    )
                )

            })





            val phone1 = dialogView.findViewById<View>(R.id.phone1) as EditText
            val phone2 = dialogView.findViewById<View>(R.id.phone2) as EditText
            val address = dialogView.findViewById<View>(R.id.address) as EditText
            val notes = dialogView.findViewById<View>(R.id.notes) as EditText

            val alertDialog = dialogBuilder.create()
            addButton.setOnClickListener {


            }
            skipButton.setOnClickListener {
                alertDialog.dismiss()

            }

            alertDialog.show()


        }


        binding.addNewKeyPerson.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this@CreateNewCustomerActivity) // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater

            val dialogView = inflater.inflate(R.layout.add_new_key_person, null)
            dialogBuilder.setView(dialogView)
            val addButton = dialogView.findViewById<View>(R.id.add) as Button
            val skipButton = dialogView.findViewById<View>(R.id.close) as ImageView

            val name = dialogView.findViewById<View>(R.id.name) as EditText
            val department = dialogView.findViewById<View>(R.id.department) as EditText
            val job = dialogView.findViewById<View>(R.id.job) as EditText
            val phone1 = dialogView.findViewById<View>(R.id.phone1) as EditText
            val phone2 = dialogView.findViewById<View>(R.id.phone2) as EditText
            val branch = dialogView.findViewById<View>(R.id.branch) as EditText
            val notes = dialogView.findViewById<View>(R.id.notes) as EditText


            val alertDialog = dialogBuilder.create()
            addButton.setOnClickListener {

                keyPersonList.add(DefSupplierKeyPersonModel(0,
                                                            0,
                                                            name.text.toString(),
                                                            department.text.toString(),
                                                            job.text.toString(),
                                                            phone1.text.toString(),
                                                            phone2.text.toString(),
                                                            branch.text.toString(),
                                                            notes.text.toString()))
                alertDialog.dismiss()
            }
            skipButton.setOnClickListener {
                alertDialog.dismiss()

            }

            alertDialog.show()


        }

        setObservers()
    }


    private fun setObservers() {

        viewModel.responseLiveTypes.observe(this, Observer {
            typeList.clear()
            typeList = it as ArrayList<ListTypesEntity>

            for (model in it) {
                typeListNames.add(model.listType!!)
            }
            (binding.listType.editText as? AutoCompleteTextView)?.setAdapter(null)
            (binding.listType.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.adapter_list_item,
                    typeListNames
                )
            )

        })
        viewModel.filterSpecialityResponseLiveChild.observe(this, Observer {
            customerTypeList.clear()
            customerTypeList = it as ArrayList<FilterDataEntity>

            for (model in it) {
                customerTypeNames.add(model.fieldName!!)
            }

            (binding.customerType.editText as? AutoCompleteTextView)?.setAdapter(null)
            (binding.customerType.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.adapter_list_item,
                    customerTypeNames
                )
            )

        })



 /*
        viewModel.filterClassResponseLive.observe(this, Observer {
            classList.clear()
            classList = it as ArrayList<FilterDataEntity>

            for (model in it) {
                classNames.add(model.fieldName!!)
            }

            (binding.classs.editText as? AutoCompleteTextView)?.setAdapter(null)

            (binding.classs.editText as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.adapter_list_item,
                    classNames
                )
            )

        })*/


        viewModel.progress.observe(this, Observer { progress ->

            when (progress) {
                0 -> {
                    ProgressLoading.dismiss()
                }
                1 -> {
                    ProgressLoading.show(this)
                }
            }
        })


    }


    override fun getLayoutId(): Int {
        return R.layout.activity_create_new_customer
    }
}