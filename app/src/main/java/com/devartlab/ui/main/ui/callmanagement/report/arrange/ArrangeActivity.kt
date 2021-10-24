package com.devartlab.ui.main.ui.callmanagement.report.arrange

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amulyakhare.textdrawable.TextDrawable
import com.devartlab.R
import com.devartlab.base.BaseActivity
import com.devartlab.data.room.arranged.ArrangedEntity
import com.devartlab.data.room.massages.MassageEntity
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.databinding.ActivityArrangeBinding
import com.devartlab.model.SlideModel
import com.devartlab.ui.dialogs.massages.OnMassageSelect
import com.devartlab.ui.main.ui.callmanagement.report.arrange.dailogs.ChooseArrangedProducts
import com.devartlab.ui.main.ui.callmanagement.report.arrange.dailogs.EditMassage

private const val TAG = "ArrangeActivity"
class ArrangeActivity : BaseActivity<ActivityArrangeBinding>(),  OnMassageSelect, ArrangeAdapter.OnCustomMassageClick {


    var model: PlanEntity? = null
    lateinit var binding: ActivityArrangeBinding

    var viewModel: ArrangeViewModel? = null
    var arrangeAdapter: ArrangeAdapter? = null
    private var mDrawableBuilder: TextDrawable? = null

    lateinit var chooseMassages: ChooseArrangedProducts
    lateinit var editMassage: EditMassage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        viewModel = ViewModelProviders.of(this).get(ArrangeViewModel::class.java)
        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "configure Activity"

        val extras = intent.extras
        if (extras == null) {
            model = null
        } else {
            try {
                model = intent.extras?.getParcelable("CustomerModel")
            } catch (e: Exception) {

            }
        }


        if (model != null) {


            try {
                setImage(model!!.customerName, model!!.planColor!!)

            } catch (e: java.lang.Exception) {
            }


            try {
                binding.customerName.text = model!!.customerName.toString()

            } catch (e: java.lang.Exception) {
            }


            try {
                binding.brick.text = model!!.brick.toString()

            } catch (e: java.lang.Exception) {
            }


            try {
                binding.degree.text = model!!._class.toString()
            } catch (e: java.lang.Exception) {
            }


            try {
                binding.specialist.text = model!!.speciality.toString()
            } catch (e: java.lang.Exception) {
            }

            try {
                binding.address.text = model!!.address.toString()
            } catch (e: java.lang.Exception) {
            }

        } else {
            finish()
        }

        arrangeAdapter = ArrangeAdapter(this, this)

        val layoutManager = GridLayoutManager(this, 4)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding?.arrangedRecyclerView?.layoutManager = layoutManager
        binding.arrangedRecyclerView.adapter = arrangeAdapter



        binding?.addToReport?.setOnClickListener(View.OnClickListener {


            chooseMassages = ChooseArrangedProducts(this, this, viewModel?.productDao!!, viewModel?.massagesDao!!, viewModel?.slideDao!!, viewModel?.arrangedSlidesDao!!, this, model?.customerid!!);
            chooseMassages.setCanceledOnTouchOutside(false);
            chooseMassages.setCancelable(false);
            val window = chooseMassages.getWindow();
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            chooseMassages.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            chooseMassages.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            chooseMassages.show();


        })

        setObserves()

        Log.d(TAG, "onCreate: " + model?.customerid!!)

        viewModel?.getAllProducts(model?.customerid!!)
    }

    private fun setObserves() {

        viewModel?.responseLive?.observe(this, Observer {

            arrangeAdapter?.setMyData(it)
        })


    }

    override fun onBackPressed() {


        super.onBackPressed()


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_arrange
    }



    override fun addEditMassage(massageEntity: MassageEntity?, productEntity: ProductEntity?, slides: MutableList<SlideModel>?, arrangedId: Int) {


        viewModel?.editCustomMassage( model?.customerid!!,arrangedId ,slides!!)
    }

    override fun setOnMassageSelect(massageEntity: MassageEntity?) {
        chooseMassages = ChooseArrangedProducts(this, this, viewModel?.productDao!!, viewModel?.massagesDao!!, viewModel?.slideDao!!, viewModel?.arrangedSlidesDao!!, this, model?.customerid!!);
        chooseMassages.setCanceledOnTouchOutside(false);
        chooseMassages.setCancelable(false);
        val window = chooseMassages.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        chooseMassages.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        chooseMassages.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chooseMassages.show();

    }

    override fun addCustomMassage(massageEntity: MassageEntity?, productEntity: ProductEntity?, slides: List<SlideModel>) {


        viewModel?.insertCustomMassage(ArrangedEntity(
                model?.customerid,
                productEntity?.ItemId,
                productEntity?.name,
                massageEntity?.messageId,
                massageEntity?.messageDescription,
                massageEntity?.messageLogoUrl
        ), model?.customerid!!, slides)
    }

    override fun setOnCustomMassageDelete(model: ArrangedEntity) {
        viewModel?.deleteCustomMassage(model, this.model?.customerid!!)
    }

    override fun setOnCustomMassageClick(model: ArrangedEntity) {

       editMassage = EditMassage(this, this
               , viewModel?.productDao!!, viewModel?.massagesDao!!, viewModel?.slideDao!!, viewModel?.arrangedSlidesDao!!,
               this, model);
        editMassage.setCanceledOnTouchOutside(false);
        editMassage.setCancelable(false);
        val window = editMassage.getWindow();
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        editMassage.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        editMassage.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editMassage.show();
    }

    fun setImage(title: String?, color: Int) {
        var letter = "A"
        if (title != null && !title.isEmpty()) {
            letter = title.substring(0, 1)
        }
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color)
        binding.customerImage.setImageDrawable(mDrawableBuilder)
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
