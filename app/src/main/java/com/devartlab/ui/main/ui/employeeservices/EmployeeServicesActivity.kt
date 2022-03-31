package com.devartlab.ui.main.ui.employeeservices

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.authority.AuthorityDao
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.ActivityEmploteeServicesBinding
import com.devartlab.data.room.filterdata.FilterDataEntity
import com.devartlab.ui.main.ui.employeeservices.attendance.AttendanceFragment
import com.devartlab.ui.main.ui.employeeservices.expenses.ExpensesFragment
import com.devartlab.ui.main.ui.employeeservices.penalties.PenaltiesFragment
import com.devartlab.ui.dialogs.chooseemployee.ChooseEmployeeInterFace
import com.devartlab.ui.main.ui.employeeservices.salary.EmployeeSalaryFragment
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import com.devartlab.base.BaseApplication
import com.devartlab.ui.main.ui.employeeservices.home.SelfServiceHomeFragment
import com.devartlab.ui.main.ui.employeeservices.workfromhome.WorkFromHomeFragment

class EmployeeServicesActivity : BaseActivity<ActivityEmploteeServicesBinding>(),
        BottomNavigationView.OnNavigationItemSelectedListener, ChooseEmployeeInterFace {
    lateinit var binding: ActivityEmploteeServicesBinding
    var active: String? = null
    var pageFragment: String? = null
    lateinit var empModel: FilterDataEntity
    lateinit var authorityDao: AuthorityDao
    lateinit var dataManager: DataManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        setSupportActionBar(binding.toolbar)
        dataManager = (getApplication() as BaseApplication).dataManager!!

        if (intent.hasExtra("pageFragment")) {
            pageFragment = intent.getStringExtra("pageFragment")
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Expenses"
        authorityDao = DatabaseClient.getInstance(this)?.appDatabase?.authorityDao()!!
        empModel = FilterDataEntity(
            dataManager.user.empId,
            dataManager.user.userName,
            dataManager.user.title,
            "",
            dataManager.user.accId,
            0,
            dataManager.user.image,
            0, "",0 , ""
        )

        val menu: Menu = binding.navView.getMenu()


        val Expenses = menu.findItem(R.id.Expenses)
        val Penalties = menu.findItem(R.id.Penalties)
        val Salary = menu.findItem(R.id.Salary)
        val Attendance = menu.findItem(R.id.Attendance)
        Penalties.isVisible = false

        Completable.fromAction {

            if (!authorityDao.getById("1060").allowBrowseRecord) {
                Expenses.isVisible = false
            }


            if (!authorityDao.getById("1084").allowBrowseRecord) {
                Attendance.isVisible = false
            }

            if (!authorityDao.getById("1100").allowBrowseRecord) {
                Salary.isVisible = false
            }

        }
                .subscribeOn(Schedulers.io())
                .subscribe()

        binding.navView.setOnNavigationItemSelectedListener(this)

        if (intent.getStringExtra("PAGE_NUMBER") != null) {

            when (intent.getStringExtra("PAGE_NUMBER")) {
                "1" -> {
                    try {
                        replace_fragment(ExpensesFragment(), "")
                        active = "ExpensesFragment"
                        binding.navView.setSelectedItemId(R.id.Expenses);

                        supportActionBar!!.title = "Expenses"

                    } catch (e: Exception) {
                        replace_fragment(ExpensesFragment(), "")
                        active = "ExpensesFragment"
                    }


                }

                "2" -> {
                    try {
                        replace_fragment(PenaltiesFragment(), "")
                        active = "PenaltiesFragment"
                        binding.navView.setSelectedItemId(R.id.Penalties);

                        supportActionBar!!.title = "Penalties"

                    } catch (e: Exception) {
                        replace_fragment(ExpensesFragment(), "")
                        active = "ExpensesFragment"
                    }


                }

                "4" -> {

                    try {

                        replace_fragment(EmployeeSalaryFragment(), "")
                        active = "EmployeeSalaryFragment"
                        binding.navView.setSelectedItemId(R.id.Salary);

                        supportActionBar!!.title = "Salary"
                    } catch (e: Exception) {
                        replace_fragment(ExpensesFragment(), "")
                        active = "ExpensesFragment"
                    }


                }

                "5" -> {

                    try {

                        replace_fragment(AttendanceFragment(), "")
                        active = "AttendanceFragment"
                        binding.navView.setSelectedItemId(R.id.Attendance);

                        supportActionBar!!.title = "Attendance"

                    } catch (e: Exception) {
                        replace_fragment(ExpensesFragment(), "")
                        active = "ExpensesFragment"
                    }


                }
            }
        } else {
            replace_fragment(ExpensesFragment(), "")
            active = "ExpensesFragment"
        }

        openFragmentFromAds()
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_emplotee_services

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {


            R.id.Penalties -> {


                if (!active.equals("PenaltiesFragment")) {
                    replace_fragment(PenaltiesFragment(), "")
                    active = "PenaltiesFragment"
                    supportActionBar!!.title = "Penalties"
                }


            }

            R.id.Expenses -> {
                if (!active.equals("ExpensesFragment")) {
                    replace_fragment(ExpensesFragment(), "")
                    active = "ExpensesFragment"
                    supportActionBar!!.title = "Expenses"
                }

            }

            R.id.Salary -> {
                if (!active.equals("EmployeeSalaryFragment")) {
                    replace_fragment(EmployeeSalaryFragment(), "EmployeeSalaryFragment")
                    active = "EmployeeSalaryFragment"
                    supportActionBar!!.title = "Salary"
                }

            }
            R.id.Attendance -> {
                if (!active.equals("AttendanceFragment")) {
                    replace_fragment(AttendanceFragment(), "AttendanceFragment")
                    active = "AttendanceFragment"
                    supportActionBar!!.title = "Attendance"
                }

            }
        }
        return true
    }

    override fun onBackPressed() {


        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }

    }

    /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }*/
    fun replace_fragment(fragment: Fragment?, tag: String?) {
        val bundle = Bundle()
        bundle.putParcelable("EMP_MODEL", empModel);
        fragment?.arguments = bundle
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_left
                )
                .add(
                        R.id.main_container,
                        fragment!!
                )
                .addToBackStack(tag)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun chooseEmployee(model: FilterDataEntity?) {
        empModel = model!!
    }
    fun openFragmentFromAds(){
        when (pageFragment) {
            "AttendanceFragment" -> replace_fragment(AttendanceFragment(), "AttendanceFragment")
            "WorkFromHomeFragment" -> replace_fragment(WorkFromHomeFragment(), "WorkFromHomeFragment")
            "ShowAllWorkFromHomeFragment" -> replace_fragment(WorkFromHomeFragment(), "ShowAllWorkFromHomeFragment")
        }
    }

}


