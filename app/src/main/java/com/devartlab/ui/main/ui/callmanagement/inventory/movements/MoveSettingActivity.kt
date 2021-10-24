package com.devartlab.ui.trade

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.retrofit.ResponseModel
import com.devartlab.data.retrofit.StoresTBl
import com.devartlab.databinding.ActivityInventoryDetailsBinding
import com.devartlab.data.room.contract.ContractEntity
import com.devartlab.ui.main.ui.callmanagement.inventory.InventoryViewModel
import com.devartlab.ui.main.ui.callmanagement.inventory.movements.StockingActivity
import com.devartlab.utils.ProgressLoading
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "InventoryDetailsLayout"

class InventoryDetails : BaseActivity<ActivityInventoryDetailsBinding>() {

    lateinit var binding: ActivityInventoryDetailsBinding
    lateinit var viewModel: InventoryViewModel

    var listNames = ArrayList<String>()
    var storesTBl = ArrayList<StoresTBl>()


    var empId = "0"
    var accId = "0"
    var storeName = ""
    var storePosetion = 0
    var toStoreName = ""
    var toStorePosetion = 0
    var sessionId = ""
    var totalValue = 0.0
    var date = ""
    var currentDate = ""
    var noteee = ""
    var storeIdd = ""


    val calendar = Calendar.getInstance()
    val uploadDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
    lateinit var InventoryStoreList: ArrayList<String>
    lateinit var da: ResponseModel


    override fun getLayoutId(): Int {
        return R.layout.activity_inventory_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(InventoryViewModel::class.java)

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        InventoryStoreList = ArrayList()
        accId = viewModel.dataManager.user.accId.toString()
        empId = viewModel.dataManager.user.empId.toString()


        val adapter1 = ArrayAdapter(this, R.layout.adapter_list_item, listNames)
        (binding.inventoryDetailsStoreSpinner.editText as? AutoCompleteTextView)?.setAdapter(
            adapter1
        )
        (binding.inventoryDetailsStoreSpinner.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->

            storeName = binding.inventoryDetailsStoreSpinner.editText?.text.toString();
            storePosetion = i

            storeIdd = storesTBl[i].storID.toString()
        }


        val adapter2 = ArrayAdapter(this, R.layout.adapter_list_item, listNames)
        (binding.inventoryDetailsToStoreSpinner.editText as? AutoCompleteTextView)?.setAdapter(
            adapter2
        )
        (binding.inventoryDetailsToStoreSpinner.editText as? AutoCompleteTextView)?.setOnItemClickListener { adapterView, view, i, l ->

            toStoreName = binding.inventoryDetailsToStoreSpinner.editText?.text.toString();
            toStorePosetion = i
        }


        viewModel.getStoresList()

        val sessionName = intent.getStringExtra("TRX_TYPE_NAME")
        sessionId = intent.getStringExtra("TRX_TYPE_ID")!!
        System.out.println(sessionName)
        System.out.println(sessionId)
        binding.inventoryDetalisAppBar.setText(sessionName)


        binding.inventoryFab.setOnClickListener() { view ->
            Snackbar.make(view, "Replace with your own action$sessionId", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            noteee = binding.inventoryDetailsNotes.text.toString()


            //need to edit
            if (sessionId == "45") {

                val intent = Intent(this@InventoryDetails, ReceiveTransferActivity::class.java)
                intent.putExtra("TRX_Option", "18")
                intent.putExtra("TRX_TYPE_ID", sessionId)

                startActivity(intent)


            } else if (sessionId == "46") {

                val intent = Intent(this@InventoryDetails, StockingActivity::class.java)
                intent.putExtra("STORE_ID", storeIdd)
                startActivity(intent)


            } else {
                val intent = Intent(
                    this,
                    com.devartlab.ui.main.ui.callmanagement.trade.selectProducts.GeneralSelectProductsActivity::class.java
                )
                startActivityForResult(intent, 1)
            }


        }


        binding.inventoryDetailsDate.setOnClickListener(View.OnClickListener {

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calendar[Calendar.YEAR] = year
                    calendar[Calendar.MONTH] = month
                    calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

                    binding.inventoryDetailsDate.setText(simpleDateFormat.format(calendar.time))
                    date = simpleDateFormat.format(calendar.time)

                }
            DatePickerDialog(
                this@InventoryDetails,
                dateSetListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        })

        currentDate = uploadDateFormat.format(calendar.time)

        setObservers()
    }


    private fun setObservers() {


        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this)
                }
            }
        })


        viewModel.responseLive.observe(this, Observer {

            da = it!!
            storesTBl = ArrayList<StoresTBl>(da?.data?.storesTBl!!)
            for (item in da?.data?.storesTBl!!) {
                InventoryStoreList.add(item.storName!!)


                val adapter = ArrayAdapter(
                    this@InventoryDetails,
                    R.layout.adapter_list_item,
                    InventoryStoreList
                )
                (binding.inventoryDetailsStoreSpinner.editText as? AutoCompleteTextView)?.setAdapter(
                    adapter
                )


                val adapter2 = ArrayAdapter(
                    this@InventoryDetails,
                    R.layout.adapter_list_item,
                    InventoryStoreList
                )
                (binding.inventoryDetailsToStoreSpinner.editText as? AutoCompleteTextView)?.setAdapter(
                    adapter2
                )

            }
        })

        viewModel.responseLiveInsert.observe(this, Observer {


            Toast.makeText(
                this@InventoryDetails,
                it.rerurnMessage.toString(),
                Toast.LENGTH_SHORT
            ).show()

            finish()

        })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        when (resultCode) {
            Activity.RESULT_CANCELED -> {
            }
            Activity.RESULT_OK -> {
                if (requestCode == 1) {
                    val list = data?.getStringExtra(("PRODUCTS"))
                    val gson = GsonBuilder().create()
                    val theList = gson.fromJson<ArrayList<ContractEntity>>(
                        list,
                        object : TypeToken<ArrayList<ContractEntity>>() {}.type
                    )
                    var iteamList = ArrayList<InventoryTrxWarehouseTransActionDetailsModel>()

                    for (i in theList) {

                        var value = i.price!!.toDouble() * i.count!!.toDouble()
                        totalValue += value

                        var iteam = InventoryTrxWarehouseTransActionDetailsModel(

                            TrxDetId = 0,
                            TrxId = i.itemSerial!!.toInt(),
                            StoreID = da.data!!.storesTBl!![storePosetion].storID,
                            RowIndex = theList.indexOf(i),
                            ItemId = i.itemId,
                            UnitId = i.itemPrincipalUnitId,
                            Qty = i.count!!.toDouble(),
                            RefuesQty = null,
                            AllQty = null,
                            RefuesQtyPercent = null,
                            Price = i.price!!.toDouble(),
                            TotalValue = value,
                            Notes = binding.inventoryDetailsNotes.text.toString(),
                            CopyFromModuleId = null,
                            CopyFromTrxTypeId = null,
                            CopyFromTrxId = null,
                            CopyFromLineId = null,
                            RecivedQty = null,
                            GlAccountId = null,
                            PatchNumber = null,
                            PatchExpireDate = null,
                            CopyFromQty = null,
                            StoreAccountId = null,
                            IsBouns = null,
                            LandedCostUnitPrice = null,
                            LandedCostTotalValue = null,
                            BounsQty = null,
                            OriginalCopyfromId = null,
                            TOFromStoreId = null,
                            StoreStorageLocationId = null,
                            PatchProductionDate = null
                        )
                        iteamList.add(iteam)
                    }

                    var requestObject = InventoryTrxWarehouseTransActionModel(
                        TrxId = 0,
                        TrxSerial = 0.toString(),
                        TrxTypeId = sessionId.toInt(),
                        SupplierId = null,
                        CustomerId = null,
                        Trxdate = uploadDateFormat.format(calendar.time),
                        ProductId = null,
                        PatchSerial = null,
                        StoreId = da.data!!.storesTBl!![storePosetion].storID,
                        ToStoreId = da.data!!.storesTBl!![toStorePosetion].storID,
                        TrxNote = binding.inventoryDetailsNotes.text.toString(),
                        TotalValue = totalValue,
                        AddUserId = accId.toInt(),
                        AddMAc = "Android",
                        AddDateTime = null,
                        ModifyUserId = accId.toInt(),
                        ModifyMac = null,
                        ModifyDatetime = null,
                        ApprovedUserId = null,
                        ApprovedEmpId = null,
                        ApprovedMac = null,
                        ApprovedDatetime = null,
                        TransferIsRecived = null,
                        QualityChekUserId = null,
                        QualityChekMac = null,
                        QualityChekDateTime = null,
                        QualityChekEmpid = null,
                        Approved = null,
                        PurchaseOrderSerial = null,
                        PurchaseOrderId = null,
                        JournalId = null,
                        ProductionStageIndex = null,
                        AddEmpId = empId.toInt(),
                        ContractId = null,
                        IndirectCostHasBeenDistributed = null,
                        TrxAutoSerial = null,
                        QualityCheckPass = null,
                        ApprovalNotes = null,
                        ManualTrxSerial = null,
                        INventoryDetails = iteamList
                    )

                    viewModel.inventoryInsertAndUpdate(requestObject)
                }
            }
        }

    }


}










