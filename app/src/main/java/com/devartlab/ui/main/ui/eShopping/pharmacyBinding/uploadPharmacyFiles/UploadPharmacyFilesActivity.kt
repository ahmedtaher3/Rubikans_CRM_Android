package com.devartlab.ui.main.ui.eShopping.pharmacyBinding.uploadPharmacyFiles

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.util.ArrayList
import java.util.HashMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.databinding.ActivityUploadPharmacyFilesBinding
import com.devartlab.ui.main.ui.eShopping.pharmacyBinding.PharmacyBindingActivity
import com.devartlab.ui.main.ui.eShopping.utils.filesUpload.VolleyFileObj
import okhttp3.MultipartBody

class UploadPharmacyFilesActivity : AppCompatActivity() {
    private var idPharmacies: String? = null
    private var payGetWay: String? = null
    private var hours: String? = null
    private var yesOrNo: String? = null
    var viewModel: UploadPharmacyViewModel? = null
    private var volleyFileObjs: List<VolleyFileObj> = ArrayList<VolleyFileObj>()
    private var volleyFileObjs2: List<VolleyFileObj> = ArrayList<VolleyFileObj>()
    private var volleyFileObjs3: List<VolleyFileObj> = ArrayList<VolleyFileObj>()
    lateinit var binding: ActivityUploadPharmacyFilesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_upload_pharmacy_files
        )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.upload_pharmacy_files)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("pharmacies_id")) {
            idPharmacies = intent.getStringExtra("pharmacies_id")
        }
        viewModel = ViewModelProvider(this)[UploadPharmacyViewModel::class.java]
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        viewModel!!.getInfoPharmacy(idPharmacies!!)
        binding.ivDelCommercialRegister.setOnClickListener {
            (volleyFileObjs as ArrayList<VolleyFileObj>).clear()
            binding.ivCommercialRegister.setImageResource(R.drawable.licence_personal)
            binding.ivDelCommercialRegister.visibility = View.GONE
        }
        binding.ivDelCommercialRegisterTwo.setOnClickListener {
            (volleyFileObjs as ArrayList<VolleyFileObj>).clear()
            binding.ivCommercialRegisterTwo.setImageResource(R.drawable.licence_personal)
            binding.ivDelCommercialRegisterTwo.visibility = View.GONE
        }
        binding.ivDelTheTaxCard.setOnClickListener {
            (volleyFileObjs as ArrayList<VolleyFileObj>).clear()
            binding.ivTheTaxCard.setImageResource(R.drawable.licence_personal)
            binding.ivDelTheTaxCard.visibility = View.GONE
        }
        binding.ivCommercialRegister.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.CAMERA
                ) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                selectImage()
            } else {
                requestCameraPermission()
            }
        }
        binding.ivCommercialRegisterTwo.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.CAMERA
                ) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                selectImageCommercialRegisterTwo()
            } else {
                requestCameraPermission()
            }
        }
        binding.ivTheTaxCard.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.CAMERA
                ) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this@UploadPharmacyFilesActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                selectImageTheTaxCard()
            } else {
                requestCameraPermission()
            }
        }
        binding.radGroPayment.setOnCheckedChangeListener { _, _ ->
            when {
                binding.PayMob.isChecked -> {
                    payGetWay = "PayMob"
                    binding.edOther.visibility = View.GONE
                    binding.edOther.setText("")
                }
                binding.Fawry.isChecked -> {
                    payGetWay = "Fawry"
                    binding.edOther.visibility = View.GONE
                    binding.edOther.setText("")
                }
                binding.Aman.isChecked -> {
                    payGetWay = "Aman"
                    binding.edOther.visibility = View.GONE
                    binding.edOther.setText("")
                }
                binding.other.isChecked -> {
                    payGetWay = "others"
                    if (TextUtils.isEmpty(binding.edOther.text.toString())) {
                        binding.edOther.error = "enter other payment"
                    }
                    binding.edOther.visibility = View.VISIBLE
                }
            }
        }
        binding.radGroWorkingHours.setOnCheckedChangeListener { _, _ ->
            when {
                binding.h12.isChecked -> {
                    hours = "12"
                }
                binding.h18.isChecked -> {
                    hours = "18"
                }
                binding.h24.isChecked -> {
                    hours = "24"
                }
            }
        }
        binding.radGroDelivery.setOnCheckedChangeListener { _, _ ->
            if (binding.yes.isChecked) {
                yesOrNo = "delivery"
            } else if (binding.no.isChecked) {
                yesOrNo = "not_delivery"
            }
        }
        binding.btnUpdate.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.edCommercialRegisterNumber.text.toString()) -> {
                    binding.edCommercialRegisterNumber.error = "valid"
                }
                TextUtils.isEmpty(binding.edPharmacyLegalName.text.toString()) -> {
                    binding.edPharmacyLegalName.error = "valid"
                }
                TextUtils.isEmpty(binding.edPhone.text.toString()) -> {
                    binding.edPhone.error = "valid"
                }
                TextUtils.isEmpty(binding.edTheTaxCardNumber.text.toString()) -> {
                    binding.edTheTaxCardNumber.error = "valid"
                }
                else -> {
                    submit()
                }
            }
        }
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this) { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel!!.updatePharmacyDetails.observe(this) {
            if (it!!.message) {
                Toast.makeText(this, " تمت رفع بيانات الصيدلية بنجاح", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, PharmacyBindingActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel!!.getInfoPharmacyResponse.observe(this) {
            binding.progressBar.visibility = View.GONE
            if (it?.data != null) {
                if (!it.data.id.equals(null)) {
                    binding.tvNoPharmacy.text = it.data.id.toString()
                }
                if (it.data.name != null) {
                    binding.tvNamePharmacy.text = it.data.name
                }
                if (it.data.phone != null) {
                    binding.tvStartDate.text = it.data.phone
                    binding.edPhone.setText(it.data.phone)
                }
                if (it.data.email != null) {
                    binding.tvUpdateDate.text = it.data.email
                }
                if (it.data.the_tax_card_number != null) {
                    binding.edTheTaxCardNumber.setText(it.data.the_tax_card_number)
                }
                if (it.data.pharmacy_legal_name != null) {
                    binding.edPharmacyLegalName.setText(it.data.pharmacy_legal_name)
                }
                if (it.data.the_tax_card != null) {
                    Glide.with(this)
                        .load("https://4eshopping.com/assets/images/pharmacy/" + it!!.data.the_tax_card)
                        .centerCrop()
                        .into(binding.ivTheTaxCard)
                    binding.ivDelTheTaxCard.visibility = View.VISIBLE
                }
                if (it.data.commercial_register != null) {
                    Glide.with(this)
                        .load("https://4eshopping.com/assets/images/pharmacy/" + it!!.data.commercial_register)
                        .centerCrop()
                        .into(binding.ivCommercialRegister)
                    binding.ivDelCommercialRegister.visibility = View.VISIBLE
                }
                if (it.data.commercial_register_two != null) {
                    Glide.with(this)
                        .load("https://4eshopping.com/assets/images/pharmacy/" + it!!.data.commercial_register_two)
                        .centerCrop()
                        .into(binding.ivCommercialRegisterTwo)
                    binding.ivDelCommercialRegisterTwo.visibility = View.VISIBLE
                }
                if (it.data.commercial_register_number != null) {
                    binding.edCommercialRegisterNumber.setText(it.data.commercial_register_number)
                }
                if (it.data.working_hours != null) {
                    hours = it.data.working_hours
                    when (it.data.working_hours) {
                        "12" -> {
                            binding.h12.isChecked = true
                        }
                        "18" -> {
                            binding.h18.isChecked = true
                        }
                        "24" -> {
                            binding.h24.isChecked = true
                        }
                    }
                }
                if (it!!.data.payment != null) {
                    payGetWay = it.data.payment
                    when (it.data.payment) {
                        "PayMob" -> {
                            binding.PayMob.isChecked = true
                        }
                        "Fawry" -> {
                            binding.Fawry.isChecked = true
                        }
                        "Aman" -> {
                            binding.Aman.isChecked = true
                        }
                        else -> {
                            binding.other.isChecked = true
                            binding.edOther.visibility = View.VISIBLE
                            binding.edOther.setText(it.data.payment)
                        }
                    }
                    if (it!!.data.delivery != null) {
                        yesOrNo = it.data.delivery
                        if (it.data.delivery == "delivery") {
                            binding.yes.isChecked = true
                        } else if (it.data.delivery == "not_delivery") {
                            binding.no.isChecked = true
                        }
                    }
                }
            }
        }
    }

    fun selectImage() {
        val options = arrayOf<CharSequence>(
            getString(R.string.labal_from_camera),
            getString(R.string.labal_from_library_imgs),
            getString(R.string.labal_cancel)
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.labal_choose_method_img))
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == getString(R.string.labal_from_camera) -> {
                    val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(i, 0)
                }
                options[item] == getString(R.string.labal_from_library_imgs) -> {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, 2)
                }
                options[item] == getString(R.string.labal_cancel) -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun selectImageCommercialRegisterTwo() {
        val options = arrayOf<CharSequence>(
            getString(R.string.labal_from_camera),
            getString(R.string.labal_from_library_imgs),
            getString(R.string.labal_cancel)
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.labal_choose_method_img))
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == getString(R.string.labal_from_camera) -> {
                    val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(i, 1)
                }
                options[item] == getString(R.string.labal_from_library_imgs) -> {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, 3)
                }
                options[item] == getString(R.string.labal_cancel) -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun selectImageTheTaxCard() {
        val options = arrayOf<CharSequence>(
            getString(R.string.labal_from_camera),
            getString(R.string.labal_from_library_imgs),
            getString(R.string.labal_cancel)
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.labal_choose_method_img))
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == getString(R.string.labal_from_camera) -> {
                    val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(i, 5)
                }
                options[item] == getString(R.string.labal_from_library_imgs) -> {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, 7)
                }
                options[item] == getString(R.string.labal_cancel) -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //camera
        when (requestCode) {
            0 -> {
                val photo = data!!.extras!!["data"] as Bitmap?
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                val tempUri = getImageUri(this, photo!!)
                binding.ivCommercialRegister.setImageBitmap(photo)
                binding.ivDelCommercialRegister.visibility = View.VISIBLE
                (volleyFileObjs as ArrayList<VolleyFileObj>).add(
                    VolleyFileObj(
                        "commercial_register",
                        getRealPathFromURICamera(tempUri!!),
                        1001
                    )
                )
                //gallery
            }
            2 -> {
                //The array list has the image paths of the selected images
                val selectedImage = data!!.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(
                    selectedImage!!,
                    filePathColumn, null, null, null
                )
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                cursor.close()
                val bitmap = BitmapFactory.decodeFile(picturePath)
                binding.ivCommercialRegister.setImageBitmap(bitmap)
                binding.ivDelCommercialRegister.visibility = View.VISIBLE
                (volleyFileObjs as ArrayList<VolleyFileObj>).add(
                    VolleyFileObj(
                        "commercial_register",
                        picturePath,
                        1001
                    )
                )

            }
            1 -> {
                val photo = data!!.extras!!["data"] as Bitmap?
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                val tempUri = getImageUri(this, photo!!)
                binding.ivCommercialRegisterTwo.setImageBitmap(photo)
                binding.ivDelCommercialRegisterTwo.visibility = View.VISIBLE
                (volleyFileObjs2 as ArrayList<VolleyFileObj>).add(
                    VolleyFileObj(
                        "commercial_register_two",
                        getRealPathFromURICamera(tempUri!!),
                        1001
                    )
                )
                //gallery
            }
            3 -> {
                //The array list has the image paths of the selected images
                val selectedImage = data!!.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(
                    selectedImage!!,
                    filePathColumn, null, null, null
                )
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                cursor.close()
                val bitmap = BitmapFactory.decodeFile(picturePath)
                binding.ivDelCommercialRegisterTwo.visibility = View.VISIBLE
                binding.ivCommercialRegisterTwo.setImageBitmap(bitmap)
                (volleyFileObjs2 as ArrayList<VolleyFileObj>).add(
                    VolleyFileObj(
                        "commercial_register_two",
                        picturePath,
                        1001
                    )
                )
            }
            5 -> {
                val photo = data!!.extras!!["data"] as Bitmap?
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                val tempUri = getImageUri(this, photo!!)
                binding.ivTheTaxCard.setImageBitmap(photo)
                binding.ivDelTheTaxCard.visibility = View.VISIBLE
                (volleyFileObjs3 as ArrayList<VolleyFileObj>).add(
                    VolleyFileObj(
                        "the_tax_card",
                        getRealPathFromURICamera(tempUri!!),
                        1001
                    )
                )
                //gallery
            }
            7 -> {
                //The array list has the image paths of the selected images
                val selectedImage = data!!.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(
                    selectedImage!!,
                    filePathColumn, null, null, null
                )
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                cursor.close()
                val bitmap = BitmapFactory.decodeFile(picturePath)
                binding.ivTheTaxCard.setImageBitmap(bitmap)
                binding.ivDelTheTaxCard.visibility = View.VISIBLE
                (volleyFileObjs3 as ArrayList<VolleyFileObj>).add(
                    VolleyFileObj(
                        "the_tax_card",
                        picturePath,
                        1001
                    )
                )
            }
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext
                .contentResolver, inImage, "Title", null
        )
        return Uri.parse(path)
    }

    private fun getRealPathFromURICamera(contentURI: Uri): String? {
        val result: String?
        val cursor = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    private fun submit() {
        val map: MutableMap<String, RequestBody> = HashMap()
        val id: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), idPharmacies!!)
        map["id"] = id
        Log.e("id", " $idPharmacies")

        val phone: RequestBody =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edPhone.text.toString()
            )
        map["phone"] = phone
        Log.e("phone", binding.edPhone.text.toString())

        val commercialregisternumber: RequestBody =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edCommercialRegisterNumber.text.toString()
            )
        map["commercial_register_number"] = commercialregisternumber
        Log.e("registernumber", binding.edCommercialRegisterNumber.text.toString())

        val payment: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), payGetWay!!)
        map["payment"] = payment
        Log.e("payment", payGetWay!!)

        val workingHours: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), hours!!)
        map["working_hours"] = workingHours
        Log.e("workingHours", hours!!)

        val delivery: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), yesOrNo!!)
        map["delivery"] = delivery
        Log.e("delivery", yesOrNo!!)

        val pharmacyLegalName: RequestBody =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edPharmacyLegalName.text.toString()
            )
        map["pharmacy_legal_name"] = pharmacyLegalName
        Log.e("pharmacy_legal_name", binding.edPharmacyLegalName.text.toString())

        val theTaxCardNumber: RequestBody =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edTheTaxCardNumber.text.toString()
            )
        map["the_tax_card_number"] = theTaxCardNumber
        Log.e("the_tax_card_number", binding.edTheTaxCardNumber.text.toString())


        val paymentName: RequestBody =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edOther.text.toString()
            )
        map["payment_name"] = paymentName
        Log.e("payment_name", binding.edOther.text.toString())
        when {
            volleyFileObjs.isEmpty() && volleyFileObjs2.isEmpty() && volleyFileObjs3.isEmpty() -> {
                val part: MultipartBody.Part? = null
                val part2: MultipartBody.Part? = null
                val part3: MultipartBody.Part? = null
                viewModel!!.getUpdatePharmacyDetails(part, part2, part3, map)
            }
            volleyFileObjs.isEmpty() && volleyFileObjs2.isEmpty() -> {
                val part: MultipartBody.Part? = null
                val part2: MultipartBody.Part? = null

                val sendMGSReqBody3: RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    volleyFileObjs3[0].file
                )
                val part3: MultipartBody.Part = MultipartBody.Part.createFormData(
                    volleyFileObjs3[0].paramName,
                    volleyFileObjs3[0].file.name, sendMGSReqBody3
                )
                viewModel!!.getUpdatePharmacyDetails(part, part2, part3, map)
            }
            volleyFileObjs.isEmpty() && volleyFileObjs3.isEmpty() -> {
                val part: MultipartBody.Part? = null
                val part3: MultipartBody.Part? = null

                val sendMGSReqBody2: RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    volleyFileObjs2[0].file
                )
                val part2: MultipartBody.Part = MultipartBody.Part.createFormData(
                    volleyFileObjs2[0].paramName,
                    volleyFileObjs2[0].file.name, sendMGSReqBody2
                )
                viewModel!!.getUpdatePharmacyDetails(part, part2, part3, map)
            }
            volleyFileObjs2.isEmpty() && volleyFileObjs3.isEmpty() -> {
                val part3: MultipartBody.Part? = null
                val part2: MultipartBody.Part? = null
                val sendMGSReqBody: RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    volleyFileObjs[0].file
                )
                val part: MultipartBody.Part = MultipartBody.Part.createFormData(
                    volleyFileObjs[0].paramName,
                    volleyFileObjs[0].file.name, sendMGSReqBody
                )
                viewModel!!.getUpdatePharmacyDetails(part, part2, part3, map)
            }
            else -> {
                val sendMGSReqBody: RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    volleyFileObjs[0].file
                )
                val part: MultipartBody.Part = MultipartBody.Part.createFormData(
                    volleyFileObjs[0].paramName,
                    volleyFileObjs[0].file.name, sendMGSReqBody
                )

                val sendMGSReqBody2: RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    volleyFileObjs2[0].file
                )
                val part2: MultipartBody.Part = MultipartBody.Part.createFormData(
                    volleyFileObjs2[0].paramName,
                    volleyFileObjs2[0].file.name, sendMGSReqBody2
                )

                val sendMGSReqBody3: RequestBody = RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    volleyFileObjs3[0].file
                )
                val part3: MultipartBody.Part = MultipartBody.Part.createFormData(
                    volleyFileObjs3[0].paramName,
                    volleyFileObjs3[0].file.name, sendMGSReqBody3
                )
                viewModel!!.getUpdatePharmacyDetails(part, part2, part3, map)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@UploadPharmacyFilesActivity,
                Manifest.permission.CAMERA
            )
        ) {
            android.app.AlertDialog.Builder(this@UploadPharmacyFilesActivity)
                .setTitle("permission denied")
                .setMessage("ask for permission again")
                .setPositiveButton("ok") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this@UploadPharmacyFilesActivity, arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), 22
                    )
                }
                .setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(
                this@UploadPharmacyFilesActivity,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                22
            )
        }
    }
}
