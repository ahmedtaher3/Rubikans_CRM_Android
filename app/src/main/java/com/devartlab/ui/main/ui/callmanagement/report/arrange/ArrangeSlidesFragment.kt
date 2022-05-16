package com.devartlab.ui.main.ui.callmanagement.report.arrange

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.arranged.ArrangedDao
import com.devartlab.data.room.arrangedslides.ArrangedSlidesDao
import com.devartlab.data.room.poduct.ProductEntity
import com.devartlab.data.room.slides.SlideDao
import com.devartlab.data.room.slides.SlideEntity
import com.devartlab.databinding.FragmentArrangeSlidesBinding
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers


/**
 * A simple [Fragment] subclass.
 */
class ArrangeSlidesFragment : BaseFragment<FragmentArrangeSlidesBinding>(), SlidesAdapter.SetSlides {

    var fragmentArrangeSlidesBinding: FragmentArrangeSlidesBinding? = null
    var selectedSlidesAdapter: SelectedSlidesAdapter? = null
    var slidesAdapter: SlidesAdapter? = null
    var model: ProductEntity? = null
    var planID: String? = null
    var arrangedDao: ArrangedDao? = null
    var arrangedSlidesDao: ArrangedSlidesDao? = null
    var slideDao: SlideDao? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentArrangeSlidesBinding = viewDataBinding!!
        slideDao = DatabaseClient.getInstance(baseActivity.application)?.appDatabase?.slideDao()
        arrangedSlidesDao = DatabaseClient.getInstance(baseActivity.application)?.appDatabase?.arrangedSlidesDao()
        arrangedDao = DatabaseClient.getInstance(baseActivity.application)?.appDatabase?.arrangedDao()

        model = arguments?.getSerializable("MODEL") as ProductEntity
        planID = arguments?.getString("PLAN_ID")
        if (model != null) {
            Toast.makeText(baseActivity, model?.name, Toast.LENGTH_SHORT).show()

        }
        selectedSlidesAdapter = SelectedSlidesAdapter(baseActivity , slideDao!!)
        slidesAdapter = SlidesAdapter(baseActivity, this)


        val layoutManager = GridLayoutManager(baseActivity, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        Completable.fromAction {


          //  slidesAdapter?.setMyData(slideDao?.getAllByID2("Phara Ferro"))

        }.subscribeOn(Schedulers.io())
                .subscribe()


    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_arrange_slides
    }

    override fun addSlide(entity: SlideEntity) {
        selectedSlidesAdapter?.setItem(entity)
    }

}
