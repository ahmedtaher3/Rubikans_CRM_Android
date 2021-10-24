package com.devartlab.ui.main.ui.callmanagement.report.arrange.dailogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.room.arrangedslides.ArrangedSlidesDao
import com.devartlab.data.room.massages.MassageEntity
import com.devartlab.data.room.massages.MassagesDao
import com.devartlab.data.room.poduct.ProductDao
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.model.SlideModel
import com.devartlab.ui.dialogs.massages.MassagesAdapter
import com.devartlab.ui.dialogs.massages.OnMassageSelect
import com.devartlab.ui.dialogs.showimage.ShowImage
import com.devartlab.ui.main.ui.callmanagement.report.arrange.ArrangeSlidesAdapter
import com.devartlab.ui.main.ui.callmanagement.report.arrange.ArrangedSelectedAdapter
import com.devartlab.ui.main.ui.callmanagement.report.arrange.dailogs.ProductsAdapter
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList


class ChooseSlides(context: Context, private val activity:
AppCompatActivity, private val slideDao: SlideDao, private val arrangedSlidesDao: ArrangedSlidesDao, private val onMassageSelect: OnMassageSelect, private val customerId: Int, private val id: Int, private val massageEntity: MassageEntity, private val productEntity: ProductEntity) : Dialog(context),
        ProductsAdapter.OpenSlides, ArrangedSelectedAdapter.OnSelectedDelete, OnMassageSelect, ArrangeSlidesAdapter.OnCustomSlideClick, View.OnClickListener {


    var arrangeSlidesAdapter: ArrangeSlidesAdapter? = null
    var arrangedSelectedAdapter: ArrangedSelectedAdapter? = null

    var massagesSlidesSelected: RecyclerView? = null
    var massagesSlides: RecyclerView? = null

    var close: ImageView? = null

    var done: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_arrange_slides)

        initViews()
        initRecyclerViews()
        initListeners()


        if (id == 0) {
            Completable.fromAction {
                val list = ArrayList<SlideModel>()
                for (model in slideDao?.getAllByProductID(massageEntity?.itemId!!)) {
                    list.add(SlideModel(
                            model.id,
                            model.itemId,
                            model.messageId,
                            model.MessageDetId,
                            model.SlideName,
                            model.SlideUrl,
                            false, 0, model.slideBase64, model.converted
                    ))
                }

                arrangeSlidesAdapter?.setMyData(list)


            }.subscribeOn(Schedulers.io())
                    .subscribe()
        } else {
            Completable.fromAction {
                val list = ArrayList<SlideModel>()
                for (model in slideDao?.getAllByID(massageEntity?.messageId!!)) {
                    list.add(SlideModel(
                            model.id,
                            model.itemId,
                            model.messageId,
                            model.MessageDetId,
                            model.SlideName,
                            model.SlideUrl,
                            false, 0, model.slideBase64, model.converted
                    ))
                }

                arrangeSlidesAdapter?.setMyData(list)


            }.subscribeOn(Schedulers.io())
                    .subscribe()
        }


    }

    private fun initListeners() {
        done?.setOnClickListener(this)
        close?.setOnClickListener(this)
    }

    private fun initRecyclerViews() {
        arrangeSlidesAdapter = ArrangeSlidesAdapter(context, activity, this, slideDao)


        val layoutManager3 = GridLayoutManager(context, 3)
        layoutManager3.orientation = LinearLayoutManager.VERTICAL
        massagesSlides?.layoutManager = layoutManager3
        massagesSlides?.adapter = arrangeSlidesAdapter

        arrangedSelectedAdapter = ArrangedSelectedAdapter(context, this, slideDao)
        massagesSlidesSelected?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        massagesSlidesSelected?.adapter = arrangedSelectedAdapter


        val _ithCallback: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
            //and in your imlpementaion of
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // get the viewHolder's and target's positions in your adapter data, swap them
                Collections.swap(arrangedSelectedAdapter?.getMyData(), viewHolder.adapterPosition, target.adapterPosition)
                // and notify the adapter that its dataset has changed
                arrangedSelectedAdapter!!.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)


                val arrangedData = arrangeSlidesAdapter?.getArrangedData()
                val myData = arrangeSlidesAdapter?.getMyData()

                for (model in myData!!) {
                    model.checked = false
                    model.checkedIndex = 0
                    for ((index, m) in arrangedData?.withIndex()!!) {
                        System.out.println(" model arranged " + m)

                        if (model.id == m.id) {

                            model.checked = true
                            model.checkedIndex = index + 1
                        }
                    }
                }

                arrangeSlidesAdapter?.setMyData(myData)


                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //TODO
            }

            //defines the enabled move directions in each state (idle, swiping, dragging).
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.START or ItemTouchHelper.END)
            }
        }

        val ith = ItemTouchHelper(_ithCallback)
        ith.attachToRecyclerView(massagesSlidesSelected)
    }

    private fun initViews() {
        done = findViewById(R.id.done)
        massagesSlides = findViewById(R.id.massagesSlides)
        massagesSlidesSelected = findViewById(R.id.massagesSlidesSelected)
        close = findViewById(R.id.close)
    }

    override fun openSlides(entity: ProductEntity?) {}

    override fun addEditMassage(massageEntity: MassageEntity?, productEntity: ProductEntity?, slides: MutableList<SlideModel>?, arrangedId: Int) {

    }

    override fun setOnMassageSelect(entity: MassageEntity?) {}

    override fun addCustomMassage(massageEntity: MassageEntity?, productEntity: ProductEntity?, list: List<SlideModel>) {


    }

    override fun setOnCustomSlideClick(url: String) {

        val showImage = ShowImage(context, url);
        showImage.setCanceledOnTouchOutside(true);
        showImage.show();

    }

    override fun setOnCustomSlideSelected(isSelected: Boolean, model: SlideModel) {

        val arrangedData = arrangeSlidesAdapter?.getArrangedData()


        if (isSelected) {

            arrangedData?.add(model)
        } else {
            arrangedData?.remove(model)

        }

        arrangedSelectedAdapter?.setMyData(arrangedData!!)
        val myData = arrangeSlidesAdapter?.getMyData()

        for (model in myData!!) {


            model.checked = false
            model.checkedIndex = 0

            for ((index, m) in arrangedData?.withIndex()!!) {
                System.out.println(" model arranged " + m)

                if (model.id == m.id) {

                    model.checked = true
                    model.checkedIndex = index + 1
                }
            }
        }

        arrangeSlidesAdapter?.setMyData(myData)


    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            R.id.done -> {


                onMassageSelect.addCustomMassage(massageEntity, productEntity, arrangeSlidesAdapter?.getArrangedData()!!)

                System.out.println("  DATAA "+ "\n" +massageEntity.toString() + "\n" +productEntity.toString()+ "\n" + arrangeSlidesAdapter?.getArrangedData()!!.size.toString())

                dismiss()
            }
            R.id.close -> {
                dismiss()
            }


        }
    }

    override fun setOnSelectedDelete(model: SlideModel?) {


        arrangedSelectedAdapter?.getMyData()?.remove(model)
        arrangedSelectedAdapter?.notifyDataSetChanged()
        val arrangedData = arrangeSlidesAdapter?.getArrangedData()
        arrangedData?.remove(model)

        arrangedSelectedAdapter?.setMyData(arrangedData!!)
        val myData = arrangeSlidesAdapter?.getMyData()

        for (model in myData!!) {


            model.checked = false
            model.checkedIndex = 0

            for ((index, m) in arrangedData?.withIndex()!!) {
                System.out.println(" model arranged " + m)

                if (model.id == m.id) {

                    model.checked = true
                    model.checkedIndex = index + 1
                }
            }
        }

        arrangeSlidesAdapter?.setMyData(myData)


    }


}
