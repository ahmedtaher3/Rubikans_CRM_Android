package com.devartlab.ui.main.ui.profile


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.databinding.FragmentProfileBinding
import com.devartlab.model.DTExperience
import com.devartlab.model.DTImage
import com.devartlab.model.EmployeeModel
import com.devartlab.ui.main.ui.employeeservices.expenses.add.AddExperienceDialog
import com.devartlab.ui.main.ui.profile.experience.OnAddExperienceClick
import com.devartlab.utils.CommonUtilities
import com.devartlab.utils.CommonUtilities.DecodeFile
import com.devartlab.utils.CommonUtilities.getCustomImagePath
import com.devartlab.utils.ProgressLoading
import com.google.gson.Gson
 import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class ProfileActivity : BaseActivity<FragmentProfileBinding>(), View.OnClickListener, OnAddExperienceClick {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var profileBanksAdapter: ProfileBanksAdapter
    private lateinit var profileExperienceAdapter: ProfileExperienceAdapter
    private lateinit var profileNumbersAdapter: ProfileNumbersAdapter
    lateinit var dialog: AddExperienceDialog

    var mCapturedImageUrl: String? = null
    private lateinit var lastLayout: RelativeLayout
    private lateinit var lastTextView: TextView
    var file: File? = null
    private var model = EmployeeModel()
     var lat = "0.0"
    var lng = "0.0"

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        profileBanksAdapter = ProfileBanksAdapter(this@ProfileActivity, ArrayList())
        profileExperienceAdapter = ProfileExperienceAdapter(this@ProfileActivity, ArrayList())
        profileNumbersAdapter = ProfileNumbersAdapter(this@ProfileActivity, ArrayList())
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())



        viewModel.getData()
        setObservers()
        setRecyclerViews()

        binding.editImageButton?.setOnClickListener(this)
        binding.edit?.setOnClickListener(this)
        binding.personalDataTab?.setOnClickListener(this)
        binding.experienceDataTab?.setOnClickListener(this)
        binding.mobileDataTab?.setOnClickListener(this)
        binding.bankDataTab?.setOnClickListener(this)
        binding.userLocation?.setOnClickListener(this)
        binding.addExperience?.setOnClickListener(this)

        lastLayout = binding.personalDataTab!!
        lastTextView = binding.personalDataTabTextView!!

    }

    private fun setRecyclerViews() {

        binding.recyclerViewBanks?.layoutManager = LinearLayoutManager(this@ProfileActivity)
        binding.recyclerViewBanks?.adapter = profileBanksAdapter

        binding.recyclerViewNumbers?.layoutManager = LinearLayoutManager(this@ProfileActivity)
        binding.recyclerViewNumbers?.adapter = profileNumbersAdapter

        binding.recyclerViewExperience?.layoutManager = LinearLayoutManager(this@ProfileActivity)
        binding.recyclerViewExperience?.adapter = profileExperienceAdapter

    }

    private fun setObservers() {
        viewModel.responseLive.observe(this, androidx.lifecycle.Observer {


            if (it.isSuccesed) {

                binding.editImage!!.setImageResource(R.drawable.user_logo)
                if (!it.data.employees[0].dtImages.isNullOrEmpty()) {
                    val decodedString: ByteArray = Base64.decode(it.data.employees[0].dtImages?.get(0)?.fileImage, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    binding.editImage?.setImageBitmap(decodedByte)
                }



                model = it.data.employees[0]
                model.dlImages = it.data.employees[0].dtImages

                model.dlExperiences = it.data.employees[0].dtExperiences
                model.setDLImages(it.data.employees[0].getDLImages())


                binding.userNameAr?.setText(model.empArName)
                binding.userNameEn?.setText(model.empEnName)
                binding.userAddress?.setText(model.address)
                binding.userBirthDate?.setText(model.birthDate?.take(10))
                binding.userEmail?.setText(model.email)
                binding.userFormalEmail?.setText(model.formalEmail)
                binding.userPersonalNumber1?.setText(model.personalTel)
                binding.userPersonalNumber2?.setText(model.personalTel2)
                binding.userHiringDate?.setText(model.hiringDate?.take(10))
                //  binding.userGender?.setText(model.male.toString())
                binding.userNationalID?.setText(model.nationalIdNum)
                binding.userFingerPrintEnrolNum?.setText(model.fingerPrintEnrollNumber.toString())
                binding.userEmpId?.setText("Employee Serial : " + model.empId)



                profileExperienceAdapter.setMyData(model.dtExperiences!!)

                profileNumbersAdapter.setMyData(model.dtMobiles!!)

                profileBanksAdapter.setMyData(model.dtBankAccounts!!)

                lat = model.lat.toString()
                lng = model.lang.toString()


                if (lat != "0" || lat != "0.0") {
                    var geocoder: Geocoder

                    var addresses: List<Address> = ArrayList()
                    geocoder = Geocoder(this@ProfileActivity, Locale("ar"))
                    try {
                        addresses = geocoder.getFromLocation(model.lat?.toDouble()!!, model.lang?.toDouble()!!, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (addresses.size != 0) {
                        binding.userLocation?.text = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                    } else {
                        binding.userLocation?.text = "Error! , please try again"

                    }

                }


            } else {

                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })

        viewModel.responseLiveUpdate.observe(this, androidx.lifecycle.Observer {


            if (it.isSuccesed) {

                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })

        viewModel.progress.observe(this, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    ProgressLoading.dismiss()
                }
                1 -> {

                    ProgressLoading.show(this@ProfileActivity)
                }
            }
        })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.edit -> {


                var sendModel = EmployeeModel()

                val drawable = binding.editImage?.drawable

                val bitmap: Bitmap = (drawable as BitmapDrawable).getBitmap()
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0)
                sendModel.dlImages = ArrayList()

                sendModel.dlImages?.add(DTImage(0, viewModel.dataManager.user.getEmpId(), encoded))
                sendModel.setDLExperiences(profileExperienceAdapter.getMyData())
                sendModel.empId = model.empId
                sendModel.lat = lat
                sendModel.lang = lng
                sendModel.empArName = binding.userNameAr!!.text?.toString()
                sendModel.empEnName = binding.userNameEn!!.text?.toString()
                sendModel.address = binding.userAddress!!.text?.toString()
                sendModel.birthDate = binding.userBirthDate!!.text?.toString()
                sendModel.email = binding.userEmail!!.text?.toString()
                sendModel.formalEmail = binding.userFormalEmail!!.text?.toString()
                sendModel.personalTel = binding.userPersonalNumber1!!.text?.toString()
                sendModel.personalTel2 = binding.userPersonalNumber2!!.text?.toString()
                sendModel.hiringDate = binding.userHiringDate!!.text?.toString()
                sendModel.nationalIdNum = model.nationalIdNum
                sendModel.fingerPrintEnrollNumber = model.fingerPrintEnrollNumber
                sendModel.jobId = model.jobId
                sendModel.managerId = model.managerId
                sendModel.deptId = model.deptId


                val json = Gson().toJsonTree(sendModel).asJsonObject


                viewModel.updateEmployee(json)


            }
            R.id.personalDataTab -> {
                binding.profilePersonalData?.visibility = View.VISIBLE
                binding.profileNumber?.visibility = View.GONE
                binding.profileBanks?.visibility = View.GONE
                binding.profileExperience?.visibility = View.GONE

                binding.personalDataTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.personalDataTabTextView?.setTextColor(Color.parseColor("#ffffff"))


                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.personalDataTab!!
                lastTextView = binding.personalDataTabTextView!!
            }
            R.id.experienceDataTab -> {

                binding.profilePersonalData?.visibility = View.GONE
                binding.profileNumber?.visibility = View.GONE
                binding.profileBanks?.visibility = View.GONE
                binding.profileExperience?.visibility = View.VISIBLE

                binding.experienceDataTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.experienceDataTabTextView?.setTextColor(Color.parseColor("#ffffff"))


                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.experienceDataTab!!
                lastTextView = binding.experienceDataTabTextView!!


            }
            R.id.mobileDataTab -> {

                binding.profilePersonalData?.visibility = View.GONE
                binding.profileNumber?.visibility = View.VISIBLE
                binding.profileBanks?.visibility = View.GONE
                binding.profileExperience?.visibility = View.GONE

                binding.mobileDataTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.mobileDataTabTextView?.setTextColor(Color.parseColor("#ffffff"))


                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.mobileDataTab!!
                lastTextView = binding.mobileDataTabTextView!!

            }
            R.id.bankDataTab -> {

                binding.profilePersonalData?.visibility = View.GONE
                binding.profileNumber?.visibility = View.GONE
                binding.profileBanks?.visibility = View.VISIBLE
                binding.profileExperience?.visibility = View.GONE

                binding.bankDataTab?.setBackgroundResource(R.drawable.rounded_primary)
                binding.bankDataTabTextView?.setTextColor(Color.parseColor("#ffffff"))


                lastLayout.setBackgroundResource(R.drawable.rounded_grey)
                lastTextView.setTextColor(Color.parseColor("#353535"))


                lastLayout = binding.bankDataTab!!
                lastTextView = binding.bankDataTabTextView!!
            }
            R.id.editImageButton -> {

                if (ContextCompat.checkSelfPermission(this@ProfileActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@ProfileActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@ProfileActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                } else {
                    requestCameraPermission()
                }

            }

            R.id.userLocation -> {

                binding.userLocationProgress?.visibility = View.VISIBLE



            /*    airLocation = AirLocation(
                        this@ProfileActivity,
                        true,
                        true,
                        object : AirLocation.Callbacks {
                            override fun onSuccess(location: Location) {
                                binding.userLocationProgress?.visibility = View.GONE
                                lat = location.latitude.toString()
                                lng = location.longitude.toString()


                                var geocoder: Geocoder

                                var addresses: List<Address> = ArrayList()
                                geocoder = Geocoder(this@ProfileActivity, Locale("ar"))
                                try {
                                    addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                                if (addresses.size != 0) {
                                    binding.userLocation?.text = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                                } else {
                                    binding.userLocation?.text = "Error! , please try again"

                                }


                            }

                            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                                Toast.makeText(this@ProfileActivity, "Field to get Location , please try again", Toast.LENGTH_SHORT).show()
                                binding.userLocationProgress?.visibility = View.GONE

                            }
                        })*/


            }


            R.id.addExperience -> {
                dialog = AddExperienceDialog(this@ProfileActivity, this);
                dialog.setCanceledOnTouchOutside(true);
                val window = dialog.getWindow();
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();

            }


        }
    }

    fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@ProfileActivity, Manifest.permission.CAMERA)) {
            AlertDialog.Builder(this@ProfileActivity)
                    .setTitle("permission denied")
                    .setMessage("ask for permission again")
                    .setPositiveButton("ok") { dialog, which -> ActivityCompat.requestPermissions(this@ProfileActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22) }
                    .setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
                    .create().show()
        } else {
            ActivityCompat.requestPermissions(this@ProfileActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 30) {

                file = File(DecodeFile("DEVART", mCapturedImageUrl, 600, 600))
                binding.editImage?.setImageResource(android.R.color.transparent);
                binding.editImage?.setImageURI(Uri.fromFile(file))
            } else if (requestCode == 20) {

                file = File(DecodeFile("DEVART", CommonUtilities.GetPathFromUri(this@ProfileActivity, data?.data!!), 600, 600))
                binding.editImage?.setImageResource(android.R.color.transparent);
                binding.editImage?.setImageURI(Uri.fromFile(file))
            }
        }


    }

    override fun setOnAddExperienceClick(model: DTExperience) {


        profileExperienceAdapter.addItem(model)

    }


    fun pickImage() {

        val dialogBuilder = AlertDialog.Builder(this@ProfileActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.pick_image, null)
        dialogBuilder.setView(dialogView)


        val camera = dialogView.findViewById<LinearLayout>(R.id.camera)
        val gallery = dialogView.findViewById<LinearLayout>(R.id.gallery)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)

        val alertDialog = dialogBuilder.create()

        alertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);




        camera.setOnClickListener(View.OnClickListener {

            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val file: File = getCustomImagePath(this@ProfileActivity, System.currentTimeMillis().toString() + "")!!
            mCapturedImageUrl = file.absolutePath
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
            startActivityForResult(takePicture, 30)
            alertDialog?.dismiss()
        })
        gallery.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 20)
            alertDialog?.dismiss()

        }
        )

        cancel.setOnClickListener(
                View.OnClickListener { alertDialog?.dismiss() }
        )

        alertDialog?.show()
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

}
