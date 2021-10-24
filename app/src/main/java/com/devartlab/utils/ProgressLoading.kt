package com.devartlab.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.devartlab.R

object ProgressLoading {
    private var progress: Dialog? = null
    private var action: TextView? = null

    private fun init(context: Context) {
        progress = Dialog(context)
        progress?.setContentView(R.layout.progress_layout)
        progress?.setCancelable(true)
        progress?.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progress?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        action = progress?.findViewById(R.id.action)
    } // init

    fun show(context: Context) {
        init(context)

        if (!(context as Activity).isFinishing) {
            //show dialog
            try {
                progress?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    } // show

    fun showWithText(context: Context, text: String) {
        init(context)

        if (!(context as Activity).isFinishing) {
            //show dialog
            try {
                progress?.setCancelable(false)
                progress?.show()
                action?.text = text
                action?.visibility = View.VISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    } // show

    fun isVisible(): Boolean {
        try {
            if (progress!!.isShowing)
                return true
            else
                return false
        } catch (e: Exception) {
            return false
        }
    } // fun of isVisible

    fun dismiss() {
        try {
            progress?.dismiss()

        } catch (e: Exception) {
        }


    } // dissmiss
} // class of ProgressLoading