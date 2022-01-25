package com.devartlab.ui.main.ui.eShopping.ticket

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.a4eshopping.ticket.model.addRate.AddRateRequest
import com.devartlab.databinding.ActivityChatEShoppingThreadBinding
import com.devartlab.ui.main.ui.eShopping.utils.filesUpload.VolleyFileObj
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class ChatThread4EShoppingActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatEShoppingThreadBinding
    private var viewModel: TicketViewModel? = null
    var ticket_id: String? = null
    var status: String? = null
    var subject: String? = null
    var adapter: ChatListAdapter? = null
    var volleyFileObjs: List<VolleyFileObj> = ArrayList<VolleyFileObj>()
    var pusher: Pusher? = null
    var channel: Channel? = null
    lateinit var request: AddRateRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_e_shopping_thread)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
        if (intent.hasExtra("ticket_id")) {
            ticket_id = intent.getStringExtra("ticket_id")
            viewModel!!.getChatList(ticket_id!!)
        }
        if (intent.hasExtra("status")) {
            status = intent.getStringExtra("status")
        }
        if (intent.hasExtra("subject")) {
            subject = intent.getStringExtra("subject")
            supportActionBar!!.title = subject
        }

        pusher()
        onClickListener()
        handleObserver()
    }

    private fun onClickListener() {
        val animation = AnimationUtils
            .loadLayoutAnimation(this, R.anim.layout_animation)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = false
        layoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.layoutAnimation = animation
        adapter = ChatListAdapter(null)
        binding.recyclerView.adapter = adapter
        binding.sendIMG.setOnClickListener {
            selectImage()
            if (ContextCompat.checkSelfPermission(this@ChatThread4EShoppingActivity, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@ChatThread4EShoppingActivity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this@ChatThread4EShoppingActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            } else {
                requestCameraPermission()
            }
        }
        binding.send.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(binding.message.text.toString())) {
                binding.message.error = "please enter message"
            } else {
                binding.send.isEnabled = false
                submit()
            }
        })
        binding.btnRate.setOnClickListener(View.OnClickListener {
            addRateDialog()
        })
    }

    fun handleObserver() {
        viewModel!!.errorMessage.observe(this) { integer ->
            if (integer == 1) {
                Toast.makeText(
                    this@ChatThread4EShoppingActivity,
                    "error in response data",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this@ChatThread4EShoppingActivity, "error in Network", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel!!.fetchMessagesResponse.observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            adapter = ChatListAdapter(it!!.data)
            binding.recyclerView.adapter = adapter
            adapter!!.setOnItemClickListener(ChatListAdapter.OnItemLongClickListener { pos, dataItem ->
                if (status == "1"||status=="2") {
                    deleteMessagesDialog(dataItem.id.toString())
                }
            })
            if (it!!.rate != null) {
                binding.tvRateRating.visibility = View.VISIBLE
                binding.tvRate.visibility = View.VISIBLE
                binding.send.visibility = View.GONE
                binding.sendIMG.visibility = View.GONE
                binding.message.visibility = View.GONE
                binding.tvRateRating.rating = it!!.rate.rate.toFloat()
            } else if (it!!.rate == null) {
                if (status != "1" && status != "2") {
                    binding.btnRate.visibility = View.VISIBLE
                    binding.send.visibility = View.GONE
                    binding.sendIMG.visibility = View.GONE
                    binding.message.visibility = View.GONE
                }
            }
        })
        viewModel!!.sendMessagesResponse.observe(this, Observer {
            binding.message.setText("")
            binding.sendIMG.setImageResource(R.drawable.ic_attach_file)
            (volleyFileObjs as ArrayList<VolleyFileObj>).clear()
            binding.send.isEnabled = true
        })
        viewModel!!.addRateResponse.observe(this, Observer {
            finish()
            startActivity(getIntent())
            Toast.makeText(this@ChatThread4EShoppingActivity, "تم التقييم بنجاح", Toast.LENGTH_SHORT)
                .show()
        })
        viewModel!!.deleteMessagesResponse.observe(this, Observer {
            finish()
            startActivity(getIntent())
        })
    }

    private fun pusher() {
        val options = PusherOptions()
            .setCluster("ap4")
        pusher = Pusher("c2cd55b18d0d95d534e4", options)

        pusher!!.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i(
                    "Pusher", "State changed from ${change.previousState} to ${change.currentState}"
                )
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception
            ) {
                Log.i(
                    "Pusher",
                    "There was a problem connecting! code ($code), " + "message ($message), exception($e)"
                )
            }
        }, ConnectionState.ALL)
        channel = pusher!!.subscribe("chatify")
        channel!!.bind("messaging") { event ->
            Log.i("Pusher", "Received event with data: $event")
            if (intent.hasExtra("ticket_id")) {
                ticket_id = intent.getStringExtra("ticket_id")
                viewModel!!.getChatList(ticket_id!!)
            }
        }
        channel!!.bind("statuschanged") { event ->
            Log.i("Pusher", "Received event with data: $event")
            val intent = Intent(this, TicketActivity::class.java)
            startActivity(intent)
        }
        channel!!.bind("client-seen") { event ->
            Log.i("Pusher", "client-seen: $event")
            viewModel!!.getChatList(ticket_id!!)
        }
        channel!!.bind("client-delete-message") { event ->
            android.util.Log.i("Pusher", "Received event with data: $event")
            if (intent.hasExtra("ticket_id")) {
                ticket_id = intent.getStringExtra("ticket_id")
                viewModel!!.getChatList(ticket_id!!)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //camera
        if (requestCode == 0) {
            val photo = data!!.extras!!["data"] as Bitmap?
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            val tempUri = getImageUri(this, photo!!)
            binding.sendIMG.setImageBitmap(photo)
            (volleyFileObjs as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "file",
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
            binding.sendIMG.setImageBitmap(bitmap)
            (volleyFileObjs as ArrayList<VolleyFileObj>).add(
                VolleyFileObj(
                    "file",
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
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), ticket_id!!)
        map["id"] = id
        Log.e("id", " $ticket_id")

        val message: RequestBody =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.message.text.toString()
            )
        map["message"] = message
        Log.e("message", binding.message.text.toString())

        if (volleyFileObjs.size == 0) {
            viewModel!!.sendMessages(map)
        } else {
            val sendMGSReqBody: RequestBody = RequestBody.create(
                "image/*".toMediaTypeOrNull(),
                volleyFileObjs.get(0).file
            )
            val part: MultipartBody.Part = MultipartBody.Part.createFormData(
                volleyFileObjs[0].paramName,
                volleyFileObjs[0].file.name, sendMGSReqBody
            )
            viewModel!!.sendMessages(part, map)
        }
    }

    fun addRateDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_rate)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val BtnCancel = dialog.findViewById<ImageView>(R.id.iv_cancel_dialog)
        val RateBar = dialog.findViewById<RatingBar>(R.id.tv_rate_rating)
        val MSGRate = dialog.findViewById<EditText>(R.id.et_rate_msg)
        val BtnSubmit = dialog.findViewById<Button>(R.id.btn_rate_submit)

        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        BtnSubmit.setOnClickListener {
            if (TextUtils.isEmpty(MSGRate.getText().toString())) {
                MSGRate.setError("please enter message")
            } else if (TextUtils.isEmpty(RateBar.rating.toString())) {
                Toast.makeText(this@ChatThread4EShoppingActivity, "please enter rate", Toast.LENGTH_SHORT)
                    .show()
            } else {
                request = AddRateRequest(ticket_id!!, MSGRate.text.toString(), RateBar.rating)
                viewModel!!.addRate(request)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun deleteMessagesDialog(id_messages: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_delete_message)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val BtnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val BtnSubmit = dialog.findViewById<Button>(R.id.btn_delete)

        BtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        BtnSubmit.setOnClickListener {
            viewModel!!.deleteMessages(id_messages)
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@ChatThread4EShoppingActivity, Manifest.permission.CAMERA)) {
            android.app.AlertDialog.Builder(this@ChatThread4EShoppingActivity)
                .setTitle("permission denied")
                .setMessage("ask for permission again")
                .setPositiveButton("ok") { dialog, which -> ActivityCompat.requestPermissions(this@ChatThread4EShoppingActivity, arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22) }
                .setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(this@ChatThread4EShoppingActivity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 22)
        }
    }
}