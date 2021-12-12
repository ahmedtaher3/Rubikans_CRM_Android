package com.devartlab.ui.main.ui.employeeservices.meals

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentMealsBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.employeeservices.meals.previousmeals.PreviousMealsFragment
import com.devartlab.ui.main.ui.moreDetailsAds.MoreDetailsAdsActivity
import com.devartlab.utils.*
import com.jarvanmo.exoplayerview.media.SimpleMediaSource
import ss.com.bannerslider.Slider
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketRequestTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealsFragment : BaseFragment<FragmentMealsBinding>(), View.OnClickListener {

    lateinit var binding: FragmentMealsBinding
    lateinit var viewModel: MealsViewModel
    lateinit var mediaSource: SimpleMediaSource
    lateinit var adapterWeekMeals: WeekMealsAdapter


    var meal = Meals.Breakfast.name
    var mealType = "وجبة ساخنة"
    var date = ""
    var quantity = "1"
    var price = "12"
    lateinit var simpleDateFormat: SimpleDateFormat

    override fun getLayoutId(): Int {
        return R.layout.fragment_meals
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Meals"

        } catch (e: Exception) {
        }

        try {
            CommonUtilities.sendMessage(baseActivity, false)
        } catch (e: Exception) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MealsViewModel::class.java)
        adapterWeekMeals = WeekMealsAdapter(baseActivity, ArrayList())
        simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' // 'hh:mm a", Locale.US)
        date = simpleDateFormat?.format(System.currentTimeMillis())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = viewDataBinding
        setHasOptionsMenu(true);

        setListeners()
        setObservers()
        setRecyclerViews()
        setSpinnerMeals()
        setSpinnerMealTypes()

        binding.date.setText(date.take(10))


        viewModel.getMeals(Meals.Breakfast.name)

        var model = AdModel()
        for (m in viewModel.dataManager.ads.ads!!) {
            if (m.pageCode?.toInt() == Constants.MEALS) {
                model = m
                break
            }
        }
        if (model.resourceLink.equals(null)
            && model.default_ad_image.equals(null)
            &&model.paragraph.equals(null)) {
            binding.constrAds.setVisibility(View.GONE)
        }
        else if (model.resourceLink.equals(null)
            &&model.paragraph.equals(null)) {
            binding.imageView.visibility = View.VISIBLE
            Glide.with(this).load(model.default_ad_image)
                .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
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

                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView)
            }
            "GIF" -> {
                binding.imageView.visibility = View.VISIBLE
                Glide.with(this).asGif().load(model.resourceLink)
                    .centerCrop().placeholder(R.drawable.dr_hussain).into(binding.imageView);
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

    private fun setListeners() {

        binding.submit.setOnClickListener(this)
        binding.increaseBtn.setOnClickListener(this)
        binding.decreaseBtn.setOnClickListener(this)
        binding.date.setOnClickListener(this)
        binding.submit.setOnClickListener(this)


        binding.mealsSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {

            adapterWeekMeals.setMyData(ArrayList())
            viewModel.getMeals(meal)


        })
    }


    private fun setRecyclerViews() {

        binding.weekMealsRecycler?.adapter = adapterWeekMeals


    }


    private fun setSpinnerMeals() {


        var list = ArrayList<String>()
        list.add(Meals.Breakfast.name)
        list.add(Meals.Lunch.name)

        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, list)


        binding.spinnerMeals.setAdapter(adapter)
        binding.spinnerMeals.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {

                meal = list[position]

                when (meal) {
                    Meals.Breakfast.name -> {
                        price = "12"
                        adapterWeekMeals.setMyData(ArrayList())
                        viewModel.getMeals(Meals.Breakfast.name)
                        binding.spinnerMealsType.visibility = View.GONE
                        mealType = MealsType.Standard.name
                    }
                    Meals.Lunch.name -> {
                        price = "30"


                        adapterWeekMeals.setMyData(ArrayList())
                        viewModel.getMeals(Meals.Lunch.name)
                        binding.spinnerMealsType.visibility = View.VISIBLE
                    }

                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }


    private fun setSpinnerMealTypes() {
        val list = ArrayList<String>()
        list.add("وجبة ساخنة")
        list.add("وجبة بدون نشويات")
        list.add("وجبة دايت")

        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, list)

        binding.spinnerMealsType.setAdapter(adapter)
        binding.spinnerMealsType.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, l: Long) {
                mealType = list[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }


    private fun setObservers() {

        viewModel.responseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            baseActivity.onBackPressed()
        })


        viewModel.responseLiveMeals.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            adapterWeekMeals.setMyData(it.meal!!)

            if (binding.mealsSwipeRefreshLayout.isRefreshing)
                binding.mealsSwipeRefreshLayout.isRefreshing = false
        })

        viewModel.progressWeek.observe(viewLifecycleOwner, androidx.lifecycle.Observer { progress ->

            when (progress) {
                0 -> {

                    binding.weekProgressBar.visibility = View.GONE
                }
                1 -> {

                    binding.weekProgressBar.visibility = View.VISIBLE
                }
            }
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer<Int?> { integer ->
            when (integer) {
                0 -> ProgressLoading.dismiss()
                1 -> ProgressLoading.show(baseActivity)
            }
        })

    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            R.id.submit -> {

                viewModel.insertMeal("Meals Requests", date, binding.date.text.toString() + " //", meal, mealType, quantity, binding.notes.text.toString(), price)

            }

            R.id.increaseBtn -> {

                quantity = (binding.mealCount.text.toString().toInt() + 1).toString()

                binding.mealCount.text = quantity


            }

            R.id.decreaseBtn -> {

                if (binding.mealCount.text != "1")
                    quantity = (binding.mealCount.text.toString().toInt() - 1).toString()
                binding.mealCount.text = quantity

            }

            R.id.date -> {
                val calendar = Calendar.getInstance()
                val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calendar[Calendar.YEAR] = year
                    calendar[Calendar.MONTH] = month
                    calendar[Calendar.DAY_OF_MONTH] = dayOfMonth


                    binding.date.setText(simpleDateFormat.format(calendar.time).take(10))


                }
                DatePickerDialog(baseActivity, dateSetListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
            }
        }

    }

    private fun replace_fragment(fragment: Fragment, s: String) {

        baseActivity.supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_left
        )
                .add(
                        R.id.Container,
                        fragment
                )
                .addToBackStack("s")
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.meal_menu, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            R.id.previousMeals -> {


                replace_fragment(PreviousMealsFragment(), "PreviousMealsFragment")


            }
        }
        return super.onOptionsItemSelected(item)
    }


}