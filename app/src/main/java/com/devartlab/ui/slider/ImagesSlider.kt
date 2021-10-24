package com.devartlab.ui.slider

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.data.room.slidetimer.SlideTimerEntity
import com.devartlab.databinding.ActivityImagesSliderBinding
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ImagesSlider : BaseActivity<ActivityImagesSliderBinding>(), View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_images_slider
    }

    lateinit var binding: ActivityImagesSliderBinding

    var callIndex: Int = 0

    lateinit var viewModel: SlidesViewModel

    var adapter: SliderAdapter? = null

    var PlanVisitModel: PlanEntity? = null
    var counter = 0
    var TYPE = 0
    var totalCount: Long? = null
    var disposable: Disposable? = null

    var visitID = 0
    var massageID = 0
    var productID = 0
    var isCustomMessage = false

    var fullList = ArrayList<SlideEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY


        viewModel = ViewModelProviders.of(this).get(SlidesViewModel::class.java)



        visitID = intent.getIntExtra("VisitID", 0)
        massageID = intent.getIntExtra("MassageID", 0)
        productID = intent.getIntExtra("ProductID", 0)
        TYPE = intent.getIntExtra("MassageType", 0)


        if (visitID == 0)
        {
            binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    binding.currentImage!!.text = (position + 1).toString()
                }

                override fun onPageSelected(position: Int) {
                    Log.d(TAG, "onPageSelected: $position")
                    if (counter > 3) {

                        if (PlanVisitModel != null) {
                            val slideTimerEntity = SlideTimerEntity(
                                    PlanVisitModel?.planDetID, PlanVisitModel?.shiftId, PlanVisitModel?.date, viewModel?.dataManager?.user?.accId, PlanVisitModel?.customerid, PlanVisitModel?.customerBranchid, PlanVisitModel?.extraVisit!!, callIndex, productID, massageID, isCustomMessage, "0", counter, fullList[position].id
                            )

                            Completable.fromAction {
                                viewModel.slideTimerDao?.insert(slideTimerEntity)
                            }.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe()
                        }
                    }
                    counter = 0
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

        }
        else
        {
            viewModel.getByID(visitID.toString())

        }


        if (TYPE.equals(2)) {

            isCustomMessage = false
            viewModel?.getSlieds(massageID)
        } else {
            isCustomMessage = true
            viewModel?.getCustomSlieds(massageID!!)

        }




        binding.exit!!.setOnClickListener(this)
        binding.next!!.setOnClickListener(this)


        Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(aLong: Long) {

                        Log.d(TAG, "onNext: $aLong")
                        totalCount = aLong
                        counter = counter + 1

                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {}
                })





        adapter = SliderAdapter(this , viewModel.slideDao!!, viewModel.arrangedSlidesDao!!)
        binding.viewPager.adapter = adapter


        setObservers()
    }

    private fun setObservers() {
        viewModel?.allSlides?.observe(this, androidx.lifecycle.Observer {

            adapter?.setMyData(it)
            fullList.addAll(it)
            System.out.println(it.size)
            binding.totalImages?.text = it.size.toString()
        })
        viewModel?.allCustomeSlides?.observe(this, androidx.lifecycle.Observer {


            val list = ArrayList<SlideEntity>()
            for (model in it) {
                list.add(SlideEntity(
                        model.slideId,
                        0,
                        0,
                        0,
                        model.name,
                        model.image, model.base64 ,  model.converted
                ))
            }
            adapter?.setMyData(list)
            fullList = list
            System.out.println(it.size)
            binding.totalImages?.text = it.size.toString()
        })
        viewModel?.planModel?.observe(this, androidx.lifecycle.Observer {

            PlanVisitModel = it
            if (PlanVisitModel != null) {
                if (!PlanVisitModel?.call1!!.equals(0)) {
                    if (!PlanVisitModel?.call2!!.equals(0)) {
                        if (!PlanVisitModel?.call3!!.equals(0)) {
                            if (!PlanVisitModel?.call4!!.equals(0)) {
                            } else {
                                callIndex = 4
                            }
                        } else {
                            callIndex = 3
                        }
                    } else {
                        callIndex = 2
                    }
                } else {
                    callIndex = 1
                }


                binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                        binding.currentImage!!.text = (position + 1).toString()
                    }

                    override fun onPageSelected(position: Int) {
                        Log.d(TAG, "onPageSelected: $position")
                        if (counter > 3) {

                            if (PlanVisitModel != null) {
                                val slideTimerEntity = SlideTimerEntity(
                                        PlanVisitModel?.planDetID, PlanVisitModel?.shiftId, PlanVisitModel?.date, viewModel?.dataManager?.user?.accId, PlanVisitModel?.customerid, PlanVisitModel?.customerBranchid, PlanVisitModel?.extraVisit!!, callIndex, productID, massageID, isCustomMessage, "0", counter, fullList[position].id
                                )

                                Completable.fromAction {
                                    viewModel.slideTimerDao?.insert(slideTimerEntity)
                                }.subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe()
                            }
                        }
                        counter = 0
                    }

                    override fun onPageScrollStateChanged(state: Int) {}
                })

            }
        })

    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.exit -> finish()
            R.id.next -> {
                if (PlanVisitModel != null) {


                    if (!PlanVisitModel?.call1?.equals(0)!!) {
                        if (!PlanVisitModel?.call2?.equals(0)!!) {
                            if (!PlanVisitModel?.call3?.equals(0)!!) {
                                if (!PlanVisitModel?.call4?.equals(0)!!) {
                                } else {
                                    PlanVisitModel?.call4 = productID
                                    viewModel!!.updatePlan(PlanVisitModel)
                                }
                            } else {
                                PlanVisitModel?.call3 = productID
                                viewModel!!.updatePlan(PlanVisitModel)
                            }
                        } else {
                            PlanVisitModel?.call2 = productID
                            viewModel!!.updatePlan(PlanVisitModel)
                        }
                    } else {
                        PlanVisitModel?.call1 = productID
                        viewModel!!.updatePlan(PlanVisitModel)
                    }

                }

                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable!!.dispose()
    }

    companion object {
        val TAG = ImagesSlider::class.java.simpleName
    }


}