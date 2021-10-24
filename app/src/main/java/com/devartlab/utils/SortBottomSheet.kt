package com.devartlab.utils


import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.devartlab.R
import com.devartlab.databinding.SortBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Created by user on 27/3/18.
 */
class SortBottomSheet(var listener: DialogListener, private val flaag: Int) :
        BottomSheetDialogFragment() {
    private var binding: SortBottomSheetBinding? = null
    private var mRootView: View? = null

    var flag: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
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

        binding = DataBindingUtil.inflate(inflater, R.layout.sort_bottom_sheet, container, false)
        mRootView = binding?.getRoot()


        flag = flaag
        if (flag == 0) {
            binding?.planned?.isChecked = false
            binding?.unCover?.isChecked = false
            binding?.all?.isChecked = true
        } else if (flag == 1) {
            binding?.planned?.isChecked = true
            binding?.unCover?.isChecked = false
            binding?.all?.isChecked = false
        } else if (flag == 2) {
            binding?.planned?.isChecked = false
            binding?.unCover?.isChecked = true
            binding?.all?.isChecked = false
        }


        binding?.planned?.setOnClickListener {

            binding?.planned?.isChecked = true
            binding?.unCover?.isChecked = false
            binding?.all?.isChecked = false
            flag = 1
            listener.applyFilter(flag)
            dismiss()
        }

        binding?.unCover?.setOnClickListener {

            binding?.planned?.isChecked = false
            binding?.all?.isChecked = false
            binding?.unCover?.isChecked = true
            flag = 2
            listener.applyFilter(flag)
            dismiss()
        }

        binding?.all?.setOnClickListener {

            binding?.planned?.isChecked = false
            binding?.all?.isChecked = true
            binding?.unCover?.isChecked = false
            flag = 0
            listener.applyFilter(flag)
            dismiss()
        }

        binding?.dismiss?.setOnClickListener {

            dismiss()
        }


        return mRootView
    }

    interface DialogListener {
        fun applyFilter(flag: Int)
    }


}