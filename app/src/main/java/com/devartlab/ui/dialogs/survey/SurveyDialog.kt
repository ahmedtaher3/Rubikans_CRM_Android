package com.devartlab.ui.dialogs.survey

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.devartlab.R

class SurveyDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_dialog)
    }
}