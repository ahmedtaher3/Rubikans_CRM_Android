package qruz.t.qruzdriverapp.ui.main.fragments.profile.changelang

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.base.BaseApplication
import com.devartlab.base.BaseFragment
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.FragmentChangeLanguageBinding
import com.devartlab.ui.auth.splash.SplashActivity
import com.devartlab.utils.LocaleUtils
import java.util.*

class ChangeLanguage : BaseActivity<FragmentChangeLanguageBinding>() {


    lateinit var binding: FragmentChangeLanguageBinding
    lateinit var dataManager: DataManager
    lateinit var languageDialog: AlertDialog

    override fun getLayoutId(): Int {
        return R.layout.fragment_change_language
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = viewDataBinding!!
        dataManager = (getApplication() as BaseApplication).dataManager!!

        val language: String = dataManager.getLang()!!

        if (language == "en") {
            binding.changeLangTextView.setText("English")
        } else {
            binding.changeLangTextView.setText("عربى")
        }

        binding.changeLang.setOnClickListener(View.OnClickListener {

            languageDialog = AlertDialog.Builder(this).create()

            val factory = LayoutInflater.from(this)
            val languageDialogview: View =
                    factory.inflate(R.layout.language_change_popup, null)
            if (languageDialog != null && languageDialog.isShowing()) {
                return@OnClickListener
            }

            languageDialog.setCancelable(false)
            languageDialog.setView(languageDialogview)
            languageDialog.show()

            languageDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val imvLogoChangeLanguage =
                    languageDialogview.findViewById<View>(R.id.imvLogoChangeLanguage) as ImageView
            val radioGroup =
                    languageDialogview.findViewById<View>(R.id.myRadioGroup) as RadioGroup
            val rdbEnglish =
                    languageDialogview.findViewById<View>(R.id.rdbEnglish) as RadioButton
            val rdbArabic =
                    languageDialogview.findViewById<View>(R.id.rdbArabic) as RadioButton
            val dialogButton =
                    languageDialogview.findViewById<View>(R.id.btn_update) as Button
            val language = dataManager.getLang()

            if (language == "en") {
                rdbEnglish.isChecked = true
            } else {
                rdbArabic.isChecked = true
            }
            dialogButton.setOnClickListener {
                if (rdbArabic.isChecked) {
                    LocaleUtils.setLocale(Locale("ar"))
                    LocaleUtils.updateConfig(
                            getApplication(),
                            getResources().getConfiguration()
                    )
                    dataManager.saveLang("ar")
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                    languageDialog.dismiss()
                } else {
                    LocaleUtils.setLocale(Locale("en"))
                    LocaleUtils.updateConfig(
                            getApplication(),
                            getResources().getConfiguration()
                    )
                    dataManager.saveLang("en")
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                    languageDialog.dismiss()
                }
            }

            languageDialogview.setOnClickListener { if (languageDialog != null) languageDialog.dismiss() }

        })

    }

}