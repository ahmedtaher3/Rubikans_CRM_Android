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
import com.devartlab.ui.main.ui.callmanagement.report.arrange.dailogs.ProductsAdapter
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList


class ChooseArrangedProducts(context: Context, private val activity:
AppCompatActivity, private val productDao: ProductDao, private val massagesDao: MassagesDao,
                             private val slideDao: SlideDao, private val arrangedSlidesDao: ArrangedSlidesDao, private val onMassageSelect: OnMassageSelect, private val customerId: Int) : Dialog(context),
        ProductsAdapter.OpenSlides, ArrangedSelectedAdapter.OnSelectedDelete, OnMassageSelect, ArrangeSlidesAdapter.OnCustomSlideClick, View.OnClickListener {


    var arrangeAdapter: ProductsAdapter? = null
    var products: RecyclerView? = null
    var close: ImageView? = null
    var productEntity: ProductEntity? = null
    lateinit var chooseMassages: ChooseMassages


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_arrange_products)

        initViews()
        initRecyclerViews()


        Completable.fromAction {

            arrangeAdapter?.setMyData(productDao?.allList)

        }.subscribeOn(Schedulers.io())
                .subscribe()

        close?.setOnClickListener { dismiss() }
    }


    private fun initRecyclerViews() {
        arrangeAdapter = ProductsAdapter(context, activity, this)


        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        products?.layoutManager = layoutManager
        products?.adapter = arrangeAdapter


    }

    private fun initViews() {

        products = findViewById(R.id.products)
        close = findViewById(R.id.close)
    }

    override fun openSlides(entity: ProductEntity?) {

        chooseMassages = ChooseMassages(context, activity, productDao, massagesDao,slideDao,arrangedSlidesDao, this,customerId , entity?.ItemId!! , entity);
        chooseMassages.setCanceledOnTouchOutside(false);
        chooseMassages.setCancelable(false);
        val window = chooseMassages.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        chooseMassages.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseMassages.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseMassages.show();

    }

    override fun addEditMassage(massageEntity: MassageEntity?, productEntity: ProductEntity?, slides: MutableList<SlideModel>?, arrangedId: Int) {

    }

    override fun setOnMassageSelect(entity: MassageEntity?) {}

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

    override fun onClick(p0: View?) {}

    override fun setOnSelectedDelete(model: SlideModel?) {}


}
