package com.devartlab.base

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.devartlab.R
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.DialogWelcomePostBinding
import com.devartlab.ui.main.MainActivity
import com.devartlab.ui.main.ui.callmanagement.CallManagementActivity
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportActivity
import com.devartlab.ui.main.ui.contactlist.ui.main.ContactsActivity
import com.devartlab.ui.main.ui.devartlink.DevartLinkActivity
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingActivity
import com.devartlab.ui.main.ui.eShopping.orientationVideos.OrientationVideosActivity
import com.devartlab.ui.main.ui.employeeservices.EmployeeServicesActivity
import com.devartlab.ui.main.ui.employeeservices.SelfServiceActivity
import com.devartlab.ui.main.ui.market.MarketRequestTypesActivity
import com.devartlab.utils.InternetConnectionDetector
import com.devartlab.utils.LocaleUtils
import com.devartlab.utils.LocationUtils
import com.squareup.picasso.Picasso
import java.util.*


abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity(), BaseFragment.Callback {
    var viewDataBinding: T? = null
        private set


    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String) {}
    private var dataManager: DataManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        dataManager = (application as BaseApplication).dataManager
        if (dataManager!!.getLang() != null) {
            if (dataManager!!.getLang() == "ar") {
                LocaleUtils.setLocale(Locale("ar"))
                LocaleUtils.updateConfig(application, baseContext.resources.configuration)
            } else {
                LocaleUtils.setLocale(Locale("en"))
                LocaleUtils.updateConfig(application, baseContext.resources.configuration)
            }
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    val isNetworkConnected: Boolean
        get() = InternetConnectionDetector.IsInternetAvailable(applicationContext)

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String?>?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding?.executePendingBindings()
    }

    fun openWebPage(url: String?) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun showWelcomePostDialog(image: String?, webLink: String?) {
        val dialog = Dialog(this)
        val binding: DialogWelcomePostBinding = DataBindingUtil.inflate(
            dialog.layoutInflater,
            R.layout.dialog_welcome_post,
            null,
            false
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setContentView(binding.root)
        Picasso.get()
            .load(image)
            .centerCrop()
            .resize(800, 800)
            .error(android.R.drawable.stat_notify_error)
            .into(binding.ivPost)
        binding.ivCancelDialog.setOnClickListener { dialog.dismiss() }
        binding.ivPost.setOnClickListener { openWebPage(webLink) }
        dialog.show()
    }

    fun meuNav(id: Int, context: Context) {
        when (id) {
            1 -> context.startActivity(Intent(context, MainActivity::class.java))
            2, 8 -> {
                intent = Intent(context, CallManagementActivity::class.java)
                intent!!.putExtra("pageFragment", "PlanFragment")
                startActivity(intent)
            }
            3 -> {
                intent = Intent(context, SelfServiceActivity::class.java)
                intent!!.putExtra("pageFragment", "SelfServiceHomeFragment")
                startActivity(intent)
            }
            4 -> context.startActivity(Intent(context, DevartLinkActivity::class.java))
            5 -> {
                intent = Intent(context, CallManagementActivity::class.java)
                intent!!.putExtra("pageFragment", "HomeFragment")
                startActivity(intent)
            }
            6 -> {
                intent = Intent(context, CallManagementActivity::class.java)
                intent!!.putExtra("pageFragment", "ReportFragment")
                startActivity(intent)
            }
            7 -> context.startActivity(Intent(context, EmployeeReportActivity::class.java))
            10 -> context.startActivity(Intent(context, MarketRequestTypesActivity::class.java))
            12 -> {
                intent = Intent(context, EmployeeServicesActivity::class.java)
                intent!!.putExtra("pageFragment", "AttendanceFragment")
                startActivity(intent)
            }
            14 -> context.startActivity(Intent(context, ContactsActivity::class.java))
            15 -> {
                intent = Intent(context, CallManagementActivity::class.java)
                intent!!.putExtra("pageFragment", "DVReportFragment")
                startActivity(intent)
            }
            16 -> {
                intent = Intent(context, SelfServiceActivity::class.java)
                intent!!.putExtra("pageFragment", "MealsFragment")
                startActivity(intent)
            }
            17 -> {
                intent = Intent(context, CallManagementActivity::class.java)
                intent!!.putExtra("pageFragment", "ListFragment")
                startActivity(intent)
            }
            19 -> {
                intent = Intent(context, EmployeeServicesActivity::class.java)
                intent!!.putExtra("PAGE_NUMBER", "4")
                startActivity(intent)
            }
            20 -> {
                intent = Intent(context, EmployeeServicesActivity::class.java)
                intent!!.putExtra("pageFragment", "ShowAllWorkFromHomeFragment")
                startActivity(intent)
            }
            21 -> {
                intent = Intent(context, CallManagementActivity::class.java)
                intent!!.putExtra("pageFragment", "SyncFragment")
                startActivity(intent)
            }
            22 -> {
                intent = Intent(context, EmployeeServicesActivity::class.java)
                intent!!.putExtra("pageFragment", "WorkFromHomeFragment")
                startActivity(intent)
            }
            23 -> context.startActivity(Intent(context, Home4EShoppingActivity::class.java))
            26 -> context.startActivity(Intent(context, OrientationVideosActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2296) {
            LocationUtils.checkLocationPermission(this)
        }
    }

    init {
        LocaleUtils.updateConfig(this)
    }
}