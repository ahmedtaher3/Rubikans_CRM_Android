package com.devartlab.ui.main.ui.callmanagement.report.arrange.dailogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
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
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList


class ChooseMassages(context: Context, private val activity:
AppCompatActivity, private val productDao: ProductDao, private val massagesDao: MassagesDao,
                     private val slideDao: SlideDao, private val arrangedSlidesDao: ArrangedSlidesDao, private val onMassageSelect: OnMassageSelect, private val customerId: Int, private val id: Int, private val productEntity: ProductEntity) : Dialog(context),
        ProductsAdapter.OpenSlides, ArrangedSelectedAdapter.OnSelectedDelete, OnMassageSelect, ArrangeSlidesAdapter.OnCustomSlideClick {


    var massagesAdapter: MassagesAdapter? = null
    var close: ImageView? = null
    var productsMassages: RecyclerView? = null
    var massageEntity: MassageEntity? = null

    lateinit var chooseSlides:ChooseSlides

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_arrange_message)

        initViews()
        initRecyclerViews()


        Completable.fromAction {

            val list = massagesDao?.getAll(id)
            list.add(MassageEntity(
                    0,
                    productEntity?.itemId!!,
                    0,
                    "Custom Slides",
                    productEntity?.image
            ))

            massagesAdapter?.setMyData(list)

        }.subscribeOn(Schedulers.io())
                .subscribe()

        close?.setOnClickListener { dismiss() }
    }


    private fun initRecyclerViews() {
        massagesAdapter = MassagesAdapter(context, activity, this)

        val layoutManager2 = GridLayoutManager(context, 3)
        layoutManager2.orientation = LinearLayoutManager.VERTICAL
        productsMassages?.layoutManager = layoutManager2
        productsMassages?.adapter = massagesAdapter


    }

    private fun initViews() {
        productsMassages = findViewById(R.id.productsMassages)
        close = findViewById(R.id.close)
    }

    override fun openSlides(entity: ProductEntity?)  {}

    override fun addEditMassage(massageEntity: MassageEntity?, productEntity: ProductEntity?, slides: MutableList<SlideModel>?, arrangedId: Int) {

    }

    override fun setOnMassageSelect(entity: MassageEntity?) {
        massageEntity = entity


        chooseSlides = ChooseSlides(context, activity,  slideDao,arrangedSlidesDao, this,customerId , entity?.messageId!! , massageEntity!!,productEntity);
        chooseSlides.setCanceledOnTouchOutside(false);
        chooseSlides.setCancelable(false);
        val window = chooseSlides.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        chooseSlides.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseSlides.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseSlides.show();


    }

    override fun addCustomMassage(massageEntity: MassageEntity?, productEntity: ProductEntity?, list: List<SlideModel>) {
        onMassageSelect.addCustomMassage(massageEntity, productEntity, list)
        dismiss()
    }

    override fun setOnCustomSlideClick(url: String) {

        val showImage = ShowImage(context, url);
        showImage.setCanceledOnTouchOutside(true);
        showImage.show();

    }

    override fun setOnCustomSlideSelected(isSelected: Boolean, model: SlideModel) {}


    override fun setOnSelectedDelete(model: SlideModel?) {}


}
