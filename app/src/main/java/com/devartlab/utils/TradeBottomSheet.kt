package com.devartlab.utils


import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.TradeFilterBottomSheetBinding
import com.devartlab.model.DevartLabReportsFilterDTO
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by user on 27/3/18.
 */
class TradeBottomSheet(var listener: DialogListener, private val filter: DevartLabReportsFilterDTO?, private val dataManager: DataManager, private val myAPI: ApiServices) :
        BottomSheetDialogFragment(), View.OnClickListener {

    private var binding: TradeFilterBottomSheetBinding? = null
    private var mRootView: View? = null


    private var pending: Boolean? = false
    private var transefred: Boolean? = false
    private var rejected: Boolean? = false


    lateinit var terriotry: ArrayList<String>
    lateinit var terriotryIds: ArrayList<Int>

    lateinit var brick: ArrayList<String>
    lateinit var brickIds: ArrayList<Int>

    lateinit var speciality: ArrayList<String>
    lateinit var specialityIds: ArrayList<Int>

    lateinit var specialityChild: ArrayList<String>
    lateinit var specialityIdsChild: ArrayList<Int>

    lateinit var classs: ArrayList<String>
    lateinit var classIds: ArrayList<Int>


    var brikFilterId = 0
    var terriotryFilterId = 0
    var specialityFilterId = 0
    var specialityFilterIdChild = 0
    var classFilterId = 0

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            setupBottomSheet(it)
            /*  val bottomSheetDialog = it as BottomSheetDialog
              setupFullHeight(bottomSheetDialog)
  */

        }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
        )
                ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)



        binding = DataBindingUtil.inflate(inflater, R.layout.trade_filter_bottom_sheet, container, false)
        mRootView = binding?.getRoot()



        terriotry = ArrayList()
        terriotryIds = ArrayList()
        brick = ArrayList()
        brickIds = ArrayList()
        speciality = ArrayList()
        specialityChild = ArrayList()
        specialityIds = ArrayList()
        specialityIdsChild = ArrayList()
        classs = ArrayList()
        classIds = ArrayList()


        getFilterTerriotry(dataManager?.user?.accId.toString(), "TblAnalyDefSalesTerriotry", "0", "0")
        getFilterBrik(dataManager?.user?.accId.toString(), "TblDefBrick", "", "0")
        getFilterSpeciality(dataManager?.user?.accId.toString(), "TblDefCustomerType", "and IsMain=1", "0")
        getFilterClass(dataManager?.user?.accId.toString(), "TblDefCustomerClass", "", "0")


        val list = ArrayList<String>()
        binding?.terriotrySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.brickSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.specialitySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.specialitySpinnerChild?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))
        binding?.classSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, list))




        binding?.terriotrySpinner?.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->


            terriotryFilterId = terriotryIds[i]
            getFilterBrik(dataManager?.user?.accId.toString(), "TblDefBrick", terriotryFilterId.toString(), "0")


        })

        binding?.brickSpinner?.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            brikFilterId = brickIds[i]
        })


        binding?.specialitySpinner?.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            specialityFilterId = specialityIds[i]
            getFilterSpecialityChild(dataManager?.user?.accId.toString(), "TblDefCustomerType", "and CusTypeParentId=" + specialityFilterId.toString(), "0")

        })

        binding?.specialitySpinnerChild?.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            specialityFilterIdChild = specialityIdsChild[i]

            for (m in specialityIdsChild) {
                System.out.println( " specialityIdsChild " + m.toString())
            }
            for (m in specialityChild) {
                System.out.println( " specialityIdsChild " + m.toString())
            }
        })

        binding?.classSpinner?.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            classFilterId = classIds[i]
        })

        binding?.applyFilter?.setOnClickListener(this)



        return mRootView
    }

    interface DialogListener {
        fun applyFilter(filter: DevartLabReportsFilterDTO)

    }


    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay
                .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.dismiss -> {
                dismiss()
            }
            R.id.applyFilter -> {
                listener.applyFilter(

                        DevartLabReportsFilterDTO(
                                0,
                                1000,
                                1,
                                dataManager.user.accId.toString(),
                                null,
                                specialityFilterId.toString(),
                                null,
                                null,
                                null,
                                null,
                                false,
                                null,
                                null,
                                null,
                                null,
                                false,
                                null,
                                null,
                                null,
                                null,
                                null,
                                specialityFilterIdChild.toString(),
                                null,
                                brikFilterId.toString(),
                                null,
                                null,
                                null

                        )
                )
                dismiss()
            }


        }
    }


    public fun getFilterTerriotry(id: String, tableName: String, whereCondition: String, filterText: String) {


        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {


                        terriotry.clear()
                        terriotryIds.clear()
                        for (model in data) {

                            terriotry.add(model.fieldName!!)
                            terriotryIds.add(model.fieldId!!)
                        }

                        binding?.terriotrySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, terriotry))


                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {}
                })
    }

    public fun getFilterBrik(id: String, tableName: String, whereCondition: String, filterText: String) {


        myAPI?.getFilterData(id.toInt(), tableName, whereCondition, "", filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {

                        brick.clear()
                        brickIds.clear()

                        for (model in data) {

                            brick.add(model.fieldName!!)
                            brickIds.add(model.fieldId!!)
                        }


                        binding?.brickSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, brick))

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {}
                })
    }

    public fun getFilterSpeciality(id: String, tableName: String, whereCondition: String, filterText: String) {


        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {

                        speciality.clear()
                        specialityIds.clear()
                        for (model in data) {

                            speciality.add(model.fieldName!!)
                            specialityIds.add(model.fieldId!!)
                        }

                        binding?.specialitySpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, speciality))

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {}
                })
    }

    public fun getFilterSpecialityChild(id: String, tableName: String, whereCondition: String, filterText: String) {


        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {


                        specialityChild.clear()
                        specialityIdsChild.clear()
                        for (model in data) {

                            specialityChild.add(model.fieldName!!)
                            specialityIdsChild.add(model.fieldId!!)
                        }

                        binding?.specialitySpinnerChild?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, specialityChild))


                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {}
                })
    }

    public fun getFilterClass(id: String, tableName: String, whereCondition: String, filterText: String) {


        myAPI?.getFilterData(id.toInt(), tableName, "", whereCondition, filterText)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<FilterDataEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<FilterDataEntity>) {


                        classs.clear()
                        classIds.clear()
                        for (model in data) {

                            classs.add(model.fieldName!!)
                            classIds.add(model.fieldId!!)
                        }

                        binding?.classSpinner?.setAdapter(ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, classs))

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {}
                })
    }


}