package com.devartlab.utils

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.devartlab.R
import com.devartlab.databinding.MrRankBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by user on 27/3/18.
 */
class RankBottomSheet(private val list: ArrayList<FilterDataEntity>?, var listener: DialogListener) : BottomSheetDialogFragment() {

    lateinit var binding: MrRankBinding
    private var mRootView: View? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet)
                ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

        binding = DataBindingUtil.inflate(inflater, R.layout.mr_rank, container, false)
        mRootView = binding?.getRoot()


        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())


        binding.visitUp!!.setOnClickListener {
            listener.visits(true)
            dismiss()
        }
        binding.visitDown!!.setOnClickListener {
            listener.visits(false)
            dismiss()
        }


        binding.listUp!!.setOnClickListener {
            listener.list(true)
            dismiss()
        }
        binding.listDown!!.setOnClickListener {
            listener.list(false)
            dismiss()
        }


        binding.unCoverUp!!.setOnClickListener {
            listener.unCover(true)
            dismiss()
        }
        binding.unCoverDown!!.setOnClickListener {
            listener.unCover(false)
            dismiss()
        }


        val listStrings = ArrayList<String>()
        val listIds = ArrayList<Int>()

        for (m in list!!) {
            listStrings.add(m.fieldName!!)
            listIds.add(m.fieldId!!)
        }
        binding?.specialist?.setAdapter(ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, listStrings))
        binding?.specialist?.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

            listener.specialist(listIds[i].toString())
            dismiss()
        })



        return mRootView
    }

    interface DialogListener {
        fun visits(isDescending: Boolean)
        fun list(isDescending: Boolean)
        fun unCover(isDescending: Boolean)
        fun specialist(id: String)

    }

}