package com.devartlab.ui.auth.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.ActivityLoginBinding
import com.devartlab.model.Cycle
import com.devartlab.model.CycleDatum
import com.devartlab.model.User
import com.devartlab.ui.main.MainActivity
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.ProgressLoading
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class LoginActivity : BaseActivity<ActivityLoginBinding?>(), View.OnClickListener {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    lateinit var database: FirebaseDatabase
    var token: String? = null
    var versionCode: Int = 0

    var updateText = ""

    var allowToUpdatePermission = false
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding = viewDataBinding!!
        binding?.login?.setOnClickListener(this)
        setObservers()

        database = FirebaseDatabase.getInstance()
        var reference: DatabaseReference = database.getReference().child("Setting")
        reference.get().addOnSuccessListener {
            updateText = it.child("UpdateText").value as String
        }.addOnFailureListener {}


        versionCode =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
            } else {
                packageManager.getPackageInfo(packageName, 0).versionCode
            }
    }


    private fun setObservers() {


        viewModel?.responseLive?.observe(this, Observer { t ->
            if (t.isSuccesed) {


                var referenceAccountData: DatabaseReference = database.getReference().child("Users")
                    .child(t.data.loginData[0].userId.toString())
                referenceAccountData.child("UserEmpId").setValue(t.data.loginData[0].userEmpId)
                referenceAccountData.child("UserName").setValue(t.data.loginData[0].userName)
                referenceAccountData.child("UserPassword")
                    .setValue(t.data.loginData[0].userPassword)
                referenceAccountData.child("Name").setValue(t.data.loginData[0].empArName)


                var user = User()
                user.userName = t.data.loginData[0].userName
                user.versionName = t.data.loginData[0].versionName
                user.password = t.data.loginData[0].userPassword
                user.accId = t.data.loginData[0].userId
                user.empId = t.data.loginData[0].userEmpId
                user.image = t.data.loginData[0].fileImage
                user.managerId = t.data.loginData[0].managerId
                try {
                    user.storeId = t.data.userStores[0].storeId!!
                } catch (e: Exception) {

                }
                user.serverDate = t.data.loginData[0].currentDayDateMs.toLong()
                user.minDate = t.data.loginData[0].minDateMs.toLong()
                user.maxDate = t.data.loginData[0].maxDateMs.toLong()


                viewModel?.dataManager?.saveUser(user)



                viewModel.dataManager.saveNewOldCycle(
                    CycleDatum(
                        t.data.cycleDataLogin[0].currentCycleId,
                        t.data.cycleDataLogin[0].currentCyclePlanId,
                        t.data.cycleDataLogin[0].currentCycleFromDate,
                        t.data.cycleDataLogin[0].currentCycleToDate,
                        t.data.cycleDataLogin[0].openCycleId,
                        t.data.cycleDataLogin[0].openPLanCycleId,
                        t.data.cycleDataLogin[0].openCycleFromDate,
                        t.data.cycleDataLogin[0].openCycleToDate,
                        CommonUtilities.convertDateToMillis(
                            t.data.cycleDataLogin[0].currentCycleFromDate.take(
                                10
                            )
                        ),
                        CommonUtilities.convertDateToMillis(
                            t.data.cycleDataLogin[0].currentCycleToDate.take(
                                10
                            )
                        ),
                        CommonUtilities.convertDateToMillis(
                            t.data.cycleDataLogin[0].openCycleFromDate.take(
                                10
                            )
                        ),
                        CommonUtilities.convertDateToMillis(
                            t.data.cycleDataLogin[0].openCycleToDate.take(
                                10
                            )
                        )
                    )
                )



                if (versionCode >= t.data.loginData[0].versionCode) {
                    viewModel?.loginData(
                        binding?.userName?.text.toString(),
                        binding?.password?.text.toString()
                    )
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
                allowToUpdatePermission = t.data.loginData[0].allowToUpdatePermission


            } else {
                Toast.makeText(this, t.rerurnMessage, Toast.LENGTH_SHORT).show()
            }

        })

        viewModel?.responseLiveData?.observe(this, Observer { t ->


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

                viewModel?.dataManager?.saveNewOldCycle(t.data.cycleData[0])

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


                viewModel?.saveAuthority(t.data.authorityData)

                viewModel?.dataManager?.isSavedData(true)

                viewModel.updatePermission()

                for (m in t.data.additionalAuthorityData) {


                    if (m.formId == 77 && m.buttonRef == 2) {
                        viewModel.dataManager.saveIsSupervisor(m.allow)
                    }
                }

            } else {

                Toast.makeText(this, t.rerurnMessage, Toast.LENGTH_SHORT).show()


            }


        })


        viewModel.responseLiveUpdatePermission.observe(this, Observer { t ->


            if (t.isSuccesed) {


                database = FirebaseDatabase.getInstance()
                var reference: DatabaseReference = database.getReference().child("Setting")
                reference.get().addOnSuccessListener {

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


                viewModel?.dataManager?.saveIsLogin(true)
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()

            } else {

                Toast.makeText(this, t.rerurnMessage, Toast.LENGTH_SHORT).show()


            }


        })

        viewModel?.progress?.observe(this, Observer { progress ->

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


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.login -> {
                viewModel?.login(
                    binding?.userName?.text.toString(),
                    binding?.password?.text.toString()
                )
            }
        }
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
        updateText.text = text.replace("_b", "\n")
        ignoreBtn.text = "Exit"
        updateMsg.text = "This version expired! must update"

        val alertDialog = dialogBuilder.create()
        updateBtn.setOnClickListener {
            alertDialog.dismiss()

        }
        ignoreBtn.setOnClickListener {
            alertDialog.dismiss()
            finish()
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
        updateText.text = text.replace("_b", "\n")
        updateMsg.text = "This version has " + days.toString() + " days to expire"

        val alertDialog = dialogBuilder.create()
        updateBtn.setOnClickListener {
            alertDialog.dismiss()

        }
        ignoreBtn.setOnClickListener {
            alertDialog.dismiss()

            if (viewModel?.dataManager?.savedData!!) {

                if (allowToUpdatePermission) {

                    viewModel?.loginData(
                        binding?.userName?.text.toString(),
                        binding?.password?.text.toString()
                    )

                } else {


                    Single.timer(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<Long?> {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onError(e: Throwable) {}
                            override fun onSuccess(t: Long) {

                                viewModel?.dataManager?.saveIsLogin(true)
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()

                            }
                        })
                }

            } else {
                viewModel?.loginData(
                    binding?.userName?.text.toString(),
                    binding?.password?.text.toString()
                )

            }


        }

        alertDialog.show()
    }

}