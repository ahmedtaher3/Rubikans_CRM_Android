package com.devartlab.ui.main.ui.callmanagement.sync

import android.R.attr.path
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.plan.PlanModelTest
import com.devartlab.databinding.SyncFragmentBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.sync_fragment.*
import ss.com.bannerslider.Slider
import java.io.*


class SyncFragment : BaseFragment<SyncFragmentBinding>() {
    private val TAG = "SyncFragment"
    private lateinit var viewModel: SyncViewModel
    private lateinit var binding: SyncFragmentBinding
    lateinit var mediaSource: SimpleMediaSource


    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageReference: StorageReference = storage.getReference().child("Data Base")

    companion object {
        fun newInstance() = SyncFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.sync_fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding

        viewModel = ViewModelProviders.of(this).get(SyncViewModel::class.java)

        if (viewModel!!.dataManager.user.image != null) {
            val decodedString = Base64.decode(viewModel!!.dataManager.user.image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            imageView.setImageBitmap(decodedByte)
        }


        syncProductsData?.setOnClickListener(View.OnClickListener {

            viewModel.syncProducts()
        })

        syncPlan?.setOnClickListener(View.OnClickListener {


            if (viewModel.dataManager?.syncAble) {

                if (LocationUtils.checkSyncPlanPermissions(baseActivity)) {

                    viewModel.getPlan()

                } else {
                    Toast.makeText(
                        baseActivity,
                        "Enable Permissions First!",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            } else {
                Toast.makeText(context, "You Have To Update Plan First", Toast.LENGTH_SHORT).show()
            }


        })

        syncList?.setOnClickListener(View.OnClickListener {


            viewModel.syncList()

        })

        sendDataBase?.setOnClickListener(View.OnClickListener {

            ProgressLoading.show(baseActivity)

            var file = Uri.fromFile(File("/data/data/com.devartlab/databases/MyToDos"))
            var file_shm = Uri.fromFile(File("/data/data/com.devartlab/databases/MyToDos-shm"))
            var file_wal = Uri.fromFile(File("/data/data/com.devartlab/databases/MyToDos-wal"))
            var MY_PREFS = Uri.fromFile(File("/data/data/com.devartlab/shared_prefs/MY_PREFS.xml"))
            val fileRef =
                storageReference.child(CommonUtilities.convertToDate(System.currentTimeMillis()))
                    .child("${viewModel.dataManager.user.nameEn}/${file.lastPathSegment}")
            val file_shmRef =
                storageReference.child(CommonUtilities.convertToDate(System.currentTimeMillis()))
                    .child("${viewModel.dataManager.user.nameEn}/${file_shm.lastPathSegment}")
            val file_walRef =
                storageReference.child(CommonUtilities.convertToDate(System.currentTimeMillis()))
                    .child("${viewModel.dataManager.user.nameEn}/${file_wal.lastPathSegment}")
            val MY_PREFSRef =
                storageReference.child(CommonUtilities.convertToDate(System.currentTimeMillis()))
                    .child("${viewModel.dataManager.user.nameEn}/${MY_PREFS.lastPathSegment}")



            fileRef.putFile(file).addOnFailureListener {
                ProgressLoading.dismiss()
                Toast.makeText(baseActivity, it.message, Toast.LENGTH_SHORT).show()

            }.addOnSuccessListener { taskSnapshot ->
                file_shmRef.putFile(file_shm).addOnFailureListener {
                    ProgressLoading.dismiss()
                    Toast.makeText(baseActivity, it.message, Toast.LENGTH_SHORT).show()

                }.addOnSuccessListener { taskSnapshot ->
                    file_walRef.putFile(file_wal).addOnFailureListener {
                        ProgressLoading.dismiss()
                        Toast.makeText(baseActivity, it.message, Toast.LENGTH_SHORT).show()

                    }.addOnSuccessListener { taskSnapshot ->
                        MY_PREFSRef.putFile(MY_PREFS).addOnFailureListener {
                            ProgressLoading.dismiss()
                            Toast.makeText(baseActivity, it.message, Toast.LENGTH_SHORT).show()

                        }.addOnSuccessListener { taskSnapshot ->
                            ProgressLoading.dismiss()
                            Toast.makeText(baseActivity, "done", Toast.LENGTH_SHORT).show()

                        }


                    }


                }

            }


        })


        replaceDataBase?.setOnClickListener(View.OnClickListener {


            val text = CommonUtilities.getText()


            Toast.makeText(baseActivity, text, Toast.LENGTH_SHORT).show()

            if (!text.isNullOrEmpty()) {
                val gson = GsonBuilder().create()


                val theList = gson.fromJson<ArrayList<PlanModelTest>>(
                    text,
                    object : TypeToken<ArrayList<PlanModelTest>>() {}.type
                )

                for (i in theList) {
                    Completable.fromAction {

                        var  x = try {
                            i.date + "T" + CommonUtilities.getTextAfterSlash(i.startPoint!!) + ":00"
                        } catch (e: java.lang.Exception) {
                            ""
                        }
                        val planmodel = PlanEntity(
                            i.planAccountId,
                            87,
                            i.cycleArName,
                            i.fromDate,
                            i.toDate,
                            i.shift!!,
                            i.day,
                            i.date!!,
                            i.activityEnName,
                            i.doubleVisitEmpName,
                            i.startPoint,
                            i.brick,
                            i.cusSerial,
                            i.customerName!!,
                            i.speciality,
                            i._class,
                            i.branchType,
                            i.placeName,
                            i.address,
                            i.notes,
                            i.eventsEnName,
                            i.eventDescription,
                            i.taskText,
                            i.officeDescription,
                            i.meetingMembers,
                            i.customerid,
                            i.customerBranchid,
                            i.branchPlaceId,
                            i.callObjectives,
                            i.activityId,
                            i.shiftId,
                            i.startPointId,
                            i.terriotryEmpId,
                            0,
                            i.meetingMemberId,
                            false,
                            i.terriotryAssigntId,
                            i.terriotryAccountId,
                            i.doubleVAccountId,
                            i.doubleVAccountIdStr,
                            i.brickId,
                            i.territoryId,
                            false,
                            false,
                            "",
                            "",
                            0,
                            0,
                            0,
                            0,
                            false,
                            x,
                            13514,
                            0,
                            0,
                            i.activityTypeID,
                            false,
                            false,
                            false,
                            ""

                        )

                        Log.d(TAG, "onViewCreated: " + planmodel.toString())

                        viewModel.planDao.insert(planmodel)
                    }.subscribeOn(Schedulers.io())
                        .subscribe()


                }


            }

        })

        sendReport?.setOnClickListener(View.OnClickListener {

            if (viewModel.dataManager.startShift) {
                viewModel.syncReport(
                    viewModel.dataManager.shift.date,
                    viewModel.dataManager.shift.name.toString()
                )
            } else {
                Toast.makeText(baseActivity, "you didn't start shift yet!", Toast.LENGTH_SHORT)
                    .show()

            }
        })


        clearData?.setOnClickListener(View.OnClickListener {


            val builder = AlertDialog.Builder(baseActivity)
            builder.setTitle("Clear Data")
            builder.setMessage("Are you sure?")
            builder.setPositiveButton("YES") { dialog, which ->

                viewModel.dataManager.clear()
                viewModel.removePlan()
                dialog.dismiss()
                baseActivity.finish()
            }
            builder.setNegativeButton("NO") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()

        })


        name?.setText(viewModel.dataManager?.user?.nameAr)


        viewModel.progress.observe(baseActivity, Observer {


            when (it) {
                1 -> {
                    context?.let { it1 -> ProgressLoading.show(it1) }
                }
                0 -> {
                    ProgressLoading.dismiss()
                }
            }

        })

        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.SYNC) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)
            && model.slideImages==null) {
            binding.constrAds.setVisibility(View.GONE)
        } else if (model.resourceLink.equals(null)&&model.paragraph.equals(null)
            && model.slideImages==null) {
            binding.imageView1.visibility = View.VISIBLE
            Glide.with(this).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView1)
        }
        if (!model.webPageLink.equals("")) {
            binding.cardviewAds.setOnClickListener {
                openWebPage(model.webPageLink)
            }
        }
        when (model.type) {
            "Video" -> {
                binding.videoView.visibility = View.VISIBLE
                mediaSource = SimpleMediaSource(model.resourceLink)
                binding.videoView.play(mediaSource);
            }
            "Image" -> {

                binding.imageView1.visibility = View.VISIBLE
                Glide.with(this).load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView1)
            }
            "GIF" -> {
                binding.imageView1.visibility = View.VISIBLE
                Glide.with(this).asGif().load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView1);
            }
            "Paragraph" -> {
                binding.textView.visibility = View.VISIBLE
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    binding.textView.setText(
//                        Html.fromHtml(
//                            model.paragraph,
//                            Html.FROM_HTML_MODE_LEGACY
//                        )
//                    );
//                } else
//                    binding.textView.setText(Html.fromHtml(model.paragraph))
                binding.textView.loadDataWithBaseURL(null, model.paragraph!!
                    ,  "text/html", "utf-8", null)
            }
            "Slider" -> {
                binding.bannerSlider.visibility = View.VISIBLE
                Slider.init(PicassoImageLoadingService(context))
                binding.bannerSlider?.setInterval(5000)

                val list = ArrayList<String>()
                for (i in model.slideImages!!) {
                    list.add(i?.link!!)
                }
                binding.bannerSlider?.setAdapter(MainSliderAdapter(list))
            }
        }
        if (model.show_ad == true) {
            binding.btnHideShowAds.setVisibility(View.VISIBLE)
            binding.btnHideShowAds.setOnClickListener {
                if (binding.constrAds.visibility == View.VISIBLE) {
                    binding.constrAds.setVisibility(View.GONE)
                    binding.btnHideShowAds.setImageResource(R.drawable.ic_show_hide_ads)
                } else {
                    binding.constrAds.setVisibility(View.VISIBLE)
                    binding.btnHideShowAds.setImageResource(R.drawable.ic_hide_show_ads)
                }
            }
        }
        if (model.show_more == true) {
            binding.tvMoreThanAds.setVisibility(View.VISIBLE)
            binding.tvMoreThanAds.setOnClickListener {
                val  intent = Intent(getActivity(), MoreDetailsAdsActivity::class.java)
                intent.putExtra("pageCode", model.pageCode)
                getActivity()?.startActivity(intent)
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Report"

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }
    }


}
