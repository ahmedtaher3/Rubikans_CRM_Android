package com.devartlab.a4eshopping.PharmacyBinding.uploadPharmacyFiles

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
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.devartlab.R
import com.devartlab.databinding.ActivityUploadPharmacyFilesBinding
import com.devartlab.ui.main.ui.a4eshopping.main.Home4EShoppingActivity
import com.devartlab.ui.main.ui.eShopping.utils.filesUpload.VolleyFileObj
import okhttp3.MultipartBody


class UploadPharmacyFilesActivity : AppCompatActivity() {
    var idPharmacies: String? = null
    var payGetWay: String? = null
    var hours: String? = null
    var yesOrNo: String? = null
    var viewModel: UploadPharmacyViewModel? = null
    lateinit var binding: ActivityUploadPharmacyFilesBinding
    var volleyFileObjs: List<VolleyFileObj> = ArrayList<VolleyFileObj>()
    var volleyFileObjs2: List<VolleyFileObj> = ArrayList<VolleyFileObj>()
    var volleyFileObjs3: List<VolleyFileObj> = ArrayList<VolleyFileObj>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_upload_pharmacy_files)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = getString(R.string.upload_pharmacy_files)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("pharmacies_id")) {
            idPharmacies = intent.getStringExtra("pharmacies_id")
        }
        viewModel = ViewModelProvider(this).get(UploadPharmacyViewModel::class.java)
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        binding.ivDelCommercialRegister.setOnClickListener {
            (volleyFileObjs as ArrayList<VolleyFileObj>).clear()
            binding.ivCommercialRegister.setImageResource(R.drawable.licence_personal)
            binding.ivDelCommercialRegister.setVisibility(View.GONE)
        }
        binding.ivDelCommercialRegisterTwo.setOnClickListener {
            (volleyFileObjs as ArrayList<VolleyFileObj>).clear()
            binding.ivCommercialRegister.setImageResource(R.drawable.licence_personal)
            binding.ivDelCommercialRegisterTwo.setVisibility(View.GONE)
        }
        binding.ivDelTheTaxCard.setOnClickListener {
            (volleyFileObjs as ArrayList<VolleyFileObj>).clear()
            binding.ivCommercialRegister.setImageResource(R.drawable.licence_personal)
            binding.ivDelTheTaxCard.setVisibility(View.GONE)
        }
        binding.ivCommercialRegister.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            } else {
                requestCameraPermission()
            }
        }
        binding.ivCommercialRegisterTwo.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectImageCommercialRegisterTwo()
            } else {
                requestCameraPermission()
            }
        }
        binding.ivTheTaxCard.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@UploadPharmacyFilesActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectImageTheTaxCard()
            } else {
                requestCameraPermission()
            }
        }
        binding.radGroPayment.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            if (binding.PayMob.isChecked()) {
                payGetWay = "PayMob"
                binding.edOther.setVisibility(View.GONE)
                binding.edOther.setText("")
            } else if (binding.Fawry.isChecked()) {
                payGetWay = "Fawry"
                binding.edOther.setVisibility(View.GONE)
                binding.edOther.setText("")
            }else if (binding.Aman.isChecked()) {
                payGetWay = "Aman"
                binding.edOther.setVisibility(View.GONE)
                binding.edOther.setText("")
            }else if (binding.other.isChecked()) {
                payGetWay = "others"
                if (TextUtils.isEmpty(binding.edOther.getText().toString())) {
                    binding.edOther.setError("enter other payment")
                }
                binding.edOther.setVisibility(View.VISIBLE)
            }
        })
        binding.radGroWorkingHours.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            if (binding.h12.isChecked()) {
                hours = "12"
            } else if (binding.h18.isChecked()) {
                hours = "18"
            }else if (binding.h24.isChecked()) {
                hours = "24"
            }
        })
        binding.radGroDelivery.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            if (binding.yes.isChecked()) {
                yesOrNo = "delivery"
            } else if (binding.no.isChecked()) {
                yesOrNo = "not_delivery"
            }
        })
        binding.btnUpdate.setOnClickListener {
            if (TextUtils.isEmpty(binding.edCommercialRegisterNumber.getText().toString())) {
                binding.edCommercialRegisterNumber.setError("valid")
            }else if (TextUtils.isEmpty(binding.edPharmacyLegalName.getText().toString())) {
                binding.edPharmacyLegalName.setError("valid")
            }else if (TextUtils.isEmpty(binding.edPhone.getText().toString())) {
                binding.edPhone.setError("valid")
            }else if (TextUtils.isEmpty(binding.edTheTaxCardNumber.getText().toString())) {
                binding.edTheTaxCardNumber.setError("valid")
            }else if (volleyFileObjs.size == 0) {
                Toast.makeText(this, "please upload image", Toast.LENGTH_SHORT).show()
            }else if (volleyFileObjs2.size == 0) {
                Toast.makeText(this, "please upload image", Toast.LENGTH_SHORT).show()
            }else if (volleyFileObjs3.size == 0) {
                Toast.makeText(this, "please upload image", Toast.LENGTH_SHORT).show()
            }else{
                submit()
            }
        }
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel!!.updatePharmacyDetails.observe(this, Observer {
            if(it!!.message){
                Toast.makeText(this, " تمت رفع بيانات الصيدلية بنجاح", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, Home4EShoppingActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
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
            if (options[item] == getString(R.string.labal_from_camera)) {
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, 0)
            } else if (options[item] == getString(R.string.labal_from_library_imgs)) {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, 2)
            } else if (options[item] == getString(R.string.labal_cancel)) {
                dialog.dismiss()
            }
        }
        builder.show()
    }
    fun selectImageCommercialRegisterTwo() {
        val options = arrayOf<CharSequence>(
            getString(R.string.labal_from_camera),
            getString(R.string.labal_from_library_imgs),
            getString(R.string.labal_cancel)
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.labal_choose_method_img))
        builder.setItems(options) { dialog, item ->
            if (options[item] == getString(R.string.labal_from_camera)) {
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, 1)
            } else if (options[item] == getString(R.string.labal_from_library_imgs)) {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, 3)
            } else if (options[item] == getString(R.string.labal_cancel)) {
                dialog.dismiss()
            }
        }
        builder.show()
    }
    fun selectImageTheTaxCard() {
        val options = arrayOf<CharSequence>(
            getString(R.string.labal_from_camera),
            getString(R.string.labal_from_library_imgs),
            getString(R.string.labal_cancel)
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.labal_choose_method_img))
        builder.setItems(options) { dialog, item ->
            if (options[item] == getString(R.string.labal_from_camera)) {
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i, 5)
            } else if (options[item] == getString(R.string.labal_from_library_imgs)) {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, 7)
            } else if (options[item] == getString(R.string.labal_cancel)) {
                dialog.dismiss()
            }
        }
        builder.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //camera
        if (requestCode == 0) {
            val photo = data!!.extras!!["data"] as Bitmap?
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            val tempUri = getImageUri(this, photo!!)
            binding.ivCommercialRegister.setImageBitmap(photo)
            binding.ivDelCommercialRegister.setVisibility(View.VISIBLE)
            (volleyFileObjs as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "commercial_register",
                    getRealPathFromURICamera(tempUri!!),
                    1001
                )
            )
            //gallery
        } else if (requestCode == 2) {
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
            binding.ivDelCommercialRegister.setVisibility(View.VISIBLE)
            (volleyFileObjs as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "commercial_register",
                    picturePath,
                    1001
                )
            )

        } else if (requestCode == 1) {
            val photo = data!!.extras!!["data"] as Bitmap?
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            val tempUri = getImageUri(this, photo!!)
            binding.ivCommercialRegisterTwo.setImageBitmap(photo)
            binding.ivDelCommercialRegisterTwo.setVisibility(View.VISIBLE)
            (volleyFileObjs2 as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "commercial_register_two",
                    getRealPathFromURICamera(tempUri!!),
                    1001
                )
            )
            //gallery
        } else if (requestCode == 3) {
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
            binding.ivDelCommercialRegisterTwo.setVisibility(View.VISIBLE)
            binding.ivCommercialRegisterTwo.setImageBitmap(bitmap)
            (volleyFileObjs2 as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "commercial_register_two",
                    picturePath,
                    1001
                )
            )
        } else if (requestCode == 5) {
            val photo = data!!.extras!!["data"] as Bitmap?
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            val tempUri = getImageUri(this, photo!!)
            binding.ivTheTaxCard.setImageBitmap(photo)
            binding.ivDelTheTaxCard.setVisibility(View.VISIBLE)
            (volleyFileObjs3 as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "commercial_register_two",
                    getRealPathFromURICamera(tempUri!!),
                    1001
                )
            )
            //gallery
        } else if (requestCode == 7) {
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
            binding.ivDelTheTaxCard.setVisibility(View.VISIBLE)
            (volleyFileObjs3 as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "commercial_register_two",
                    picturePath,
                    1001
                )
            )
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
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
        Log.e("id"," $idPharmacies")

        val phone: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.edPhone.text.toString())
        map["phone"] = phone
        Log.e("phone",binding.edPhone.text.toString())

        val commercialregisternumber: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull()
                , binding.edCommercialRegisterNumber.text.toString())
        map["commercial_register_number"] = commercialregisternumber
        Log.e("registernumber",binding.edCommercialRegisterNumber.text.toString())

        val payment: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), payGetWay!!)
        map["payment"] = payment
        Log.e("payment",payGetWay!!)

        val workingHours: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), hours!!)
        map["working_hours"] = workingHours
        Log.e("workingHours",hours!!)

        val delivery: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), yesOrNo!!)
        map["delivery"] = delivery
        Log.e("delivery",yesOrNo!!)

        val pharmacyLegalName: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.edPharmacyLegalName.text.toString())
        map["pharmacy_legal_name"] = pharmacyLegalName
        Log.e("pharmacy_legal_name",binding.edPharmacyLegalName.text.toString())

        val theTaxCardNumber: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.edTheTaxCardNumber.text.toString())
        map["the_tax_card_number"] = theTaxCardNumber
        Log.e("the_tax_card_number",binding.edTheTaxCardNumber.text.toString())


        val paymentName: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), binding.edOther.text.toString())
        map["payment_name"] = paymentName
        Log.e("payment_name",binding.edOther.text.toString())

        val sendMGSReqBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),
            volleyFileObjs.get(0).file )
        val part: MultipartBody.Part = MultipartBody.Part.createFormData(
            volleyFileObjs[0].paramName,
            volleyFileObjs[0].file.name, sendMGSReqBody
        )

        val sendMGSReqBody2: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),
            volleyFileObjs2.get(0).file )
        val part2: MultipartBody.Part = MultipartBody.Part.createFormData(
            volleyFileObjs2[0].paramName,
            volleyFileObjs2[0].file.name, sendMGSReqBody2
        )

        val sendMGSReqBody3: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),
            volleyFileObjs3.get(0).file )
        val part3: MultipartBody.Part = MultipartBody.Part.createFormData(
            volleyFileObjs3[0].paramName,
            volleyFileObjs3[0].file.name, sendMGSReqBody3
        )


        viewModel!!.getUpdatePharmacyDetails(part,part2,part3, map)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@UploadPharmacyFilesActivity, Manifest.permission.CAMERA)) {
            android.app.AlertDialog.Builder(this@UploadPharmacyFilesActivity)
                .setTitle("permission denied")
                .setMessage("ask for permission again")
                .setPositiveButton("ok") { dialog, which -> ActivityCompat.requestPermissions(this@UploadPharmacyFilesActivity, arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22) }
                .setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(this@UploadPharmacyFilesActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22)
        }
    }
}