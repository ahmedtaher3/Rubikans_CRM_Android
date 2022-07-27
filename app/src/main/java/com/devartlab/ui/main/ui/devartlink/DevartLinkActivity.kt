package com.devartlab.ui.main.ui.devartlink

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.base.BaseApplication
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.ActivityDevartLinkBinding
import com.devartlab.model.AdModel
import com.devartlab.model.CardModel
import com.devartlab.ui.main.ui.callmanagement.home.MenuListAdapter
import com.devartlab.ui.main.ui.devartlink.calender.CalenderActivity
import com.devartlab.ui.main.ui.devartlink.devartAcademy.DevartAcademyActivity
import com.devartlab.ui.main.ui.devartlink.devartCommunity.DevartCommunityActivity
import com.devartlab.ui.main.ui.devartlink.handBook.HandBookActivity
import com.devartlab.ui.main.ui.devartlink.letsTalk.ChatThread.ChatListViewModel
import com.devartlab.ui.main.ui.devartlink.letsTalk.LetsTalkActivity
import com.devartlab.ui.main.ui.devartlink.letsTalk.db.AppDataBase
import com.devartlab.ui.main.ui.devartlink.letsTalk.db.ChatItemModel
import com.devartlab.ui.main.ui.devartlink.letsTalk.model.user.UserResponse
import com.devartlab.ui.main.ui.eShopping.utils.UserPreferenceHelper
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.ui.main.ui.profile.ProfileActivity
import com.devartlab.utils.Constants
import com.devartlab.utils.MainSliderAdapter
import com.devartlab.utils.PicassoImageLoadingService
import com.google.android.exoplayer2.source.MediaSource
import com.google.gson.Gson
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ss.com.bannerslider.Slider
import ss.com.bannerslider.event.OnSlideClickListener
import java.util.HashMap

private const val TAG = "DevartLinkActivity"

class DevartLinkActivity : BaseActivity<ActivityDevartLinkBinding>(),
    MenuListAdapter.OnHomeItemClick {
    lateinit var binding: ActivityDevartLinkBinding
    lateinit var adapter: MenuListAdapter
    lateinit var viewModel: DevartLinkViewModel
    lateinit var viewModel2: ChatListViewModel
    lateinit var mediaSource: SimpleMediaSource
    var dataManager: DataManager? = null
    var name: String? = null
    var pass: String? = null
    var token: String? = null
    var chatItemModel: ChatItemModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = "Devart Link"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this).get(DevartLinkViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(ChatListViewModel::class.java)

        dataManager = (application as BaseApplication).dataManager
        name = dataManager!!.user.userName
        pass = dataManager!!.user.password
        token = dataManager!!.token
        viewModel.getUserModel(name, pass, token)


        val list = ArrayList<CardModel>()

//        list.add(CardModel(1, resources.getString(R.string.lets_talk), R.drawable.ic_talk_icon_02))
        list.add(CardModel(2, resources.getString(R.string.faq), R.drawable.ic_faq))
        list.add(CardModel(3, resources.getString(R.string.community), R.drawable.ic_community))
//        list.add(CardModel(4, resources.getString(R.string.academy), R.drawable.ic_academy))
//        list.add(CardModel(5, resources.getString(R.string.calender), R.drawable.ic_calendar_month))

        adapter = MenuListAdapter(this, list, this)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.recycler?.layoutManager = layoutManager
        binding.recycler.adapter = adapter
        chatItemModel = AppDataBase.getInstance().classDAO().allANewForms
        handleObserver()//observer

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_devart_link
    }

    override fun setOnHomeItemClick(model: CardModel) {

        when (model.id) {
            1 -> {
                startActivity(Intent(this, LetsTalkActivity::class.java))
            }
            2 -> {
                startActivity(Intent(this, HandBookActivity::class.java))
            }
            3 -> {
                startActivity(Intent(this, DevartCommunityActivity::class.java))
            }
            4 -> {
                startActivity(Intent(this, DevartAcademyActivity::class.java))
            }
            5 -> {
                startActivity(Intent(this, CalenderActivity::class.java))
            }
        }
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


    fun handleObserver() {
        viewModel!!.errorMessage.observe(this, Observer { integer: Int ->
            if (integer == 1) {
                Log.e("xxx", "error")
                Toast.makeText(this, "error in response data", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "error in Network", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.userResponse.observe(this, Observer {
            UserPreferenceHelper.saveUserProfileChat(it!!)
            binding.recycler.setVisibility(View.VISIBLE)
            binding.ProgressBar.setVisibility(View.GONE)
        })
        if (AppDataBase.getInstance().classDAO().getAllANewForms() != null) {
            val map: MutableMap<String, RequestBody> = HashMap()
            val message: RequestBody =
                RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    chatItemModel!!.getMessage()
                )
            map["message"] = message
            val id: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                chatItemModel!!.getId()
            )
            map["user_id"] = id
            val userId: RequestBody =
                RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    chatItemModel!!.getUserId()
                )
            map["id"] = userId

            if (chatItemModel?.getVolleyFileObjs() == null) {
                val part: MultipartBody.Part? = null
                viewModel2.sendMessages(part, map)
                AppDataBase.getInstance().classDAO().deleteAllRecords()
            }
//            else {
//                RequestBody sendMGSReqBody = RequestBody.create(chatItemModel.getVolleyFileObjs().getFile()
//                        , MediaType.parse("image/*"));
//                MultipartBody.Part part = MultipartBody.Part
//                        .createFormData(chatItemModel.getVolleyFileObjs().getParamName(),
//                                chatItemModel.getVolleyFileObjs().getFile().getName()
//                                , sendMGSReqBody);
//                chatItemModel.setVolleyFileObjs(chatItemModel.getVolleyFileObjs());
//                    viewModel2.sendMessages(part, map);
//                AppDataBase.getInstance().classDAO().deleteAllRecords();
//            }
        }
    }

    //
//    override fun onResume() {
//        super.onResume()
//        binding.videoView.play(mediaSource);

}