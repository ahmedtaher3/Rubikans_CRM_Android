package com.devartlab.ui.auth.splash

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.AppConstants
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivitySplashBinding
import com.devartlab.model.Cycle
import com.devartlab.ui.auth.login.LoginActivity
import com.devartlab.ui.auth.login.LoginViewModel
import com.devartlab.ui.main.MainActivity
import com.devartlab.ui.main.ui.eShopping.main.model.login4EShopping.Login4EShoppingRequest
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    lateinit var binding: ActivitySplashBinding

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    lateinit var viewModel: LoginViewModel
    var versionCode: Int = 0
    var alertDialog: AlertDialog? = null


    var updateText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)


        viewModel.dataManager.saveGoogleService(CommonUtilities.isGooglePlayServicesAvailable(this))


        versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
        } else {
            packageManager.getPackageInfo(packageName, 0).versionCode
        }


        val dialogBuilder = AlertDialog.Builder(this@SplashActivity)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this@SplashActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.choose_mode, null)
        dialogBuilder.setView(dialogView)
        val offlineButton = dialogView.findViewById<View>(R.id.offlineButton) as Button
        val onlineButton = dialogView.findViewById<View>(R.id.onlineButton) as Button
        alertDialog = dialogBuilder.create()

        offlineButton.setOnClickListener(View.OnClickListener {

            viewModel.dataManager!!.saveOfflineMood(true)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()

        })

        onlineButton.setOnClickListener(View.OnClickListener {
            viewModel.dataManager!!.saveOfflineMood(false)



            viewModel.login(
                viewModel.dataManager?.user.userName!!,
                viewModel.dataManager?.user.password!!
            )

        })


        val tabletSize = resources.getBoolean(R.bool.isTablet)
        if (tabletSize) {
            viewModel.dataManager!!.saveTablet(true)
        } else {
            viewModel.dataManager!!.saveTablet(false)
        }



        if (!viewModel.dataManager?.isLogin!! || viewModel.dataManager?.user.userName ==
            null && viewModel.dataManager.user.password == null
        ) {


            Single.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Long?> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onError(e: Throwable) {}
                    override fun onSuccess(t: Long) {


                        viewModel.dataManager!!.saveOfflineMood(false)
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        finish()


                    }
                })


        } else {

            //in case user login then
            //check database if empty go to login activity to start app from beginning
            viewModel.checkDatabase()


        }



        binding.tryAgainButton.setOnClickListener {



            viewModel.login(
                viewModel.dataManager?.user.userName!!,
                viewModel.dataManager?.user.password!!
            )
        }


        setObservers()
    }

    private fun setObservers() {

        viewModel.responseLive.observe(this, Observer { t ->

            if (t.isSuccesed) {
                var user = viewModel.dataManager.user
                user.userName = t.data.loginData[0].userName
                user.versionName = t.data.loginData[0].versionName
                user.password = t.data.loginData[0].userPassword
                user.accId = t.data.loginData[0].userId
                user.empId = t.data.loginData[0].userEmpId
                user.managerId = t.data.loginData[0].managerId

                try {
                    user.storeId = t.data.userStores[0].storeId!!
                } catch (e: Exception) {

                }
                user.image = t.data.loginData[0].fileImage

                user.serverDate = t.data.loginData[0].currentDayDateMs.toLong()
                user.minDate = t.data.loginData[0].minDateMs.toLong()
                user.maxDate = t.data.loginData[0].maxDateMs.toLong()



                viewModel.dataManager.saveUser(user)
                viewModel.dataManager.saveIsLogin(true)




                if (versionCode >= t.data.loginData[0].versionCode) {


                    Completable.fromAction {

                        if (viewModel.authorityDao?.allList.isNullOrEmpty()) {
                            viewModel.loginData(
                                viewModel.dataManager.user.userName,
                                viewModel.dataManager.user.password
                            )

                        } else {
                            if (viewModel.dataManager.savedData) {

                                if (t.data.loginData[0].allowToUpdatePermission) {
                                    viewModel.loginData(
                                        viewModel.dataManager.user.userName,
                                        viewModel.dataManager.user.password
                                    )

                                } else {
                                    Single.timer(1, TimeUnit.SECONDS)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(object : SingleObserver<Long?> {
                                            override fun onSubscribe(d: Disposable) {}

                                            override fun onError(e: Throwable) {}
                                            override fun onSuccess(t: Long) {


                                                /*       val biometricManager = BiometricManager.from(this)
                                                       when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                                                           BiometricManager.BIOMETRIC_SUCCESS ->
                                                               Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                                                           BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                                                               Log.e("MY_APP_TAG", "No biometric features available on this device.")
                                                           BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                                                               Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                                                           BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                                                               // Prompts the user to create credentials that your app accepts.
                                                               val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                                                   putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                                                           BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                                                               }
                                                               startActivityForResult(enrollIntent, REQUEST_CODE)
                                                           }
                                                       }
                                                       */
                                                startActivity(
                                                    Intent(
                                                        this@SplashActivity,
                                                        MainActivity::class.java
                                                    )
                                                )
                                                finish()

                                            }
                                        })
                                }

                            } else {
                                viewModel.loginData(
                                    viewModel.dataManager.user.userName,
                                    viewModel.dataManager.user.password
                                )
                            }
                        }

                    }.subscribeOn(Schedulers.io())
                        .subscribe()


                } else {

                    if (t.data.loginData[0].versionName.contains(versionCode.toString())) {
                        if (t.data.loginData[0].lastVersionAvailableDay > 0) {
                            // open must not update dialog
                            notMustUpdate(
                                t.data.loginData[0].lastVersionAvailableDay,
                                t.data.loginData[0].allowToUpdatePermission,
                                updateText
                            )
                        } else {
                            // open must update dialog
                            mustUpdate(updateText)
                        }
                    } else {

                        mustUpdate(updateText)

                    }


                }


            } else {

                Toast.makeText(this, t.rerurnMessage, Toast.LENGTH_SHORT).show()

                binding.tryAgainLayout.visibility = View.VISIBLE

            }


        })

        viewModel.responseLiveData.observe(this, Observer { t ->


            if (t.isSuccesed) {

                var user = viewModel?.dataManager?.user
                user?.nameEn = t.data.workData[0].empArName
                user?.nameAr = t.data.workData[0].empEnName
                if (t.data.workData[0].managerId == null)
                    user?.managerId = 0
                else
                    user?.managerId = t.data.workData[0].managerId
                user?.levelName = t.data.jobDescription[0].cLevelName
                user?.additionalAccIds = t.data.jobDescription[0].additionalAccountIdStr
                user?.titleId = t.data.workData[0].jobtId
                user?.title = t.data.workData[0].empEnTitle
                for (m in t.data.additionalAuthorityData) {

                    if (m.formId == 62)
                        user?.isAllowToUpdatePlan = m.allow

                    if (m.formId == 77)
                        user?.isOpenReportLimit = m.allow

                }

                viewModel?.dataManager?.saveUser(user)

                viewModel.dataManager.saveNewOldCycle(t.data.cycleData[0])

                viewModel?.dataManager?.saveCycle(
                    Cycle(
                        t.data.cycleData[0].currentCyclePlanId,
                        t.data.cycleData[0].currentCycleId,
                        t.data.cycleData[0].currentCycleFromDate,
                        t.data.cycleData[0].currentCycleToDate,
                        0,
                        "",
                        true,
                        t.data.cycleData[0].currentFromDateMs,
                        t.data.cycleData[0].currentToDateMs

                    )
                )

                viewModel.saveAuthority(t.data.authorityData)

                viewModel.dataManager.isSavedData(true)



                viewModel.updatePermission()


            } else {

                Toast.makeText(this, t.rerurnMessage, Toast.LENGTH_SHORT).show()


            }


        })

        viewModel.responseLiveUpdatePermission.observe(this, Observer { t ->


            if (t.isSuccesed) {

                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()


            } else {

                Toast.makeText(this, t.rerurnMessage, Toast.LENGTH_SHORT).show()


            }


        })


        viewModel.progress?.observe(this, Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                10 -> {

                    ProgressLoading.dismiss()
                    binding.tryAgainLayout.visibility = View.VISIBLE
                }
                1 -> {
                    ProgressLoading.show(this@SplashActivity)

                }
            }
        })

        viewModel.checkData?.observe(this, Observer {

            when (it) {
                true -> {

                    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
                    var reference: DatabaseReference = database.getReference()
                    reference.child("Setting").get().addOnSuccessListener {

                        viewModel.dataManager.isUpdateDeletedPlan(
                            it.child("UpdateDeletedPlan").value as Boolean
                        )

                        updateText = it.child("UpdateText").value as String


                        try {
                            viewModel.dataManager.isMobileData(
                                it.child(viewModel.dataManager.user.empId.toString())
                                    .child("MobileData").value as Boolean
                            )

                        } catch (e: Exception) {

                            reference.child(viewModel.dataManager.user.empId.toString())
                                .child("MobileData")
                                .setValue(true)

                            viewModel.dataManager.isMobileData(true)

                        }


                        try {
                            viewModel.dataManager.isUpdatePlan(
                                it.child(viewModel.dataManager.user.empId.toString())
                                    .child("UpdatePlan").value as Boolean
                            )

                        } catch (e: Exception) {
                            reference.child(viewModel.dataManager.user.empId.toString())
                                .child("UpdatePlan")
                                .setValue(false)

                            viewModel.dataManager.isUpdatePlan(false)

                        }

                    }.addOnFailureListener {}

                    Single.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {

                                if (viewModel.dataManager?.startShift!!) {

                                    try {
                                        alertDialog?.show()
                                    } catch (e: Exception) {

                                    }

                                } else {


                                    viewModel.login(
                                        viewModel.dataManager?.user.userName!!,
                                        viewModel.dataManager?.user.password!!
                                    )
                                }


                            }
                        })


                }
                false -> {


                    Single.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {


                                viewModel.dataManager.clear()
                                startActivity(
                                    Intent(
                                        this@SplashActivity,
                                        LoginActivity::class.java
                                    )
                                )
                                finish()

                            }
                        })
                }

            }
        })
    }


    fun mustUpdate(text: String) {


        val dialogBuilder = android.app.AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_version, null)
        dialogBuilder.setView(dialogView)
        val updateMsg = dialogView.findViewById<View>(R.id.updateMsg) as TextView
        val updateBtn = dialogView.findViewById<View>(R.id.updateBtn) as Button
        val ignoreBtn = dialogView.findViewById<View>(R.id.ignoreBtn) as TextView
        val updateText = dialogView.findViewById<View>(R.id.updateText) as TextView

        ignoreBtn.text = "Exit"
        updateMsg.text = "This version expired! must update"
        updateText.text = text.replace("_b", "\n")

        val alertDialog = dialogBuilder.create()
        updateBtn.setOnClickListener {

            val appPackageName = packageName // getPackageName() from Context or Activity object

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
                finish()
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
                finish()
            }
        }
        ignoreBtn.setOnClickListener {
            alertDialog.dismiss()
            this@SplashActivity.finish()
        }

        alertDialog.show()


    }


    fun notMustUpdate(days: Int, allowToUpdatePermission: Boolean, text: String) {

        val dialogBuilder = android.app.AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_version, null)
        dialogBuilder.setView(dialogView)
        val updateMsg = dialogView.findViewById<View>(R.id.updateMsg) as TextView
        val updateBtn = dialogView.findViewById<View>(R.id.updateBtn) as Button
        val ignoreBtn = dialogView.findViewById<View>(R.id.ignoreBtn) as TextView
        val updateText = dialogView.findViewById<View>(R.id.updateText) as TextView

        updateMsg.text = "This version has " + days.toString() + " days to expire"
        updateText.text = text.replace("_b", "\n")

        val alertDialog = dialogBuilder.create()
        updateBtn.setOnClickListener {


            val appPackageName = packageName // getPackageName() from Context or Activity object

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
                finish()
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
                finish()
            }


        }
        ignoreBtn.setOnClickListener {
            alertDialog.dismiss()


            Completable.fromAction {

                if (viewModel.authorityDao?.allList.isNullOrEmpty()) {
                    viewModel.loginData(
                        viewModel.dataManager.user.userName,
                        viewModel.dataManager.user.password
                    )

                } else {
                    if (viewModel.dataManager.savedData) {

                        if (allowToUpdatePermission) {
                            viewModel.loginData(
                                viewModel.dataManager.user.userName,
                                viewModel.dataManager.user.password
                            )

                        } else {
                            Single.timer(1, TimeUnit.SECONDS)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : SingleObserver<Long?> {
                                    override fun onSubscribe(d: Disposable) {}

                                    override fun onError(e: Throwable) {}
                                    override fun onSuccess(t: Long) {

                                        startActivity(
                                            Intent(
                                                this@SplashActivity,
                                                MainActivity::class.java
                                            )
                                        )
                                        finish()

                                    }
                                })
                        }

                    } else {
                        viewModel.loginData(
                            viewModel.dataManager.user.userName,
                            viewModel.dataManager.user.password
                        )
                    }
                }

            }.subscribeOn(Schedulers.io())
                .subscribe()

        }

        alertDialog.show()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }
}