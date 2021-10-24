package com.devartlab.ui.main.ui.trade

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.model.GoogleRequestResponse
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ChoosePlaceDialog(c: Context, private val activity: AppCompatActivity, private val myAPI: ApiServicesGoogle, private val onPlaceChoose: OnPlaceChoose)
    : Dialog(c) {

    private var governmentSpinner: MaterialBetterSpinner? = null
    private var citySpinner: MaterialBetterSpinner? = null
    private var areaSpinner: MaterialBetterSpinner? = null
    private var progressBar: ProgressBar? = null


    var governmentNamesList = ArrayList<String>()
    var cityNamesList = ArrayList<String>()
    var areaNamesList = ArrayList<String>()

    var governmentIDSList = ArrayList<Int>()
    var cityIDSList = ArrayList<Int>()
    var areaIDSList = ArrayList<Int>()

    var governmentID = 0
    var cityID = 0
    var areaID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_place)

        val list = ArrayList<String>()

        governmentSpinner = findViewById(R.id.governmentSpinner)
        citySpinner = findViewById(R.id.citySpinner)
        areaSpinner = findViewById(R.id.areaSpinner)
        progressBar = findViewById(R.id.progressBar)

        governmentSpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))
        citySpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))
        areaSpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, list))


        governmentSpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, position, l ->
            governmentID = governmentIDSList[position]

            getCities("getAll", governmentID.toString(), "", "")
        })

        citySpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, position, l ->

            cityID = cityIDSList[position]

            getAreas("getAll", cityIDSList[position].toString(), "")
        })

        areaSpinner?.setOnItemClickListener(OnItemClickListener { adapterView, view, position, l ->
            areaID = areaIDSList[position]

            onPlaceChoose.setOnPlaceChoose(areaID)
            dismiss()
        })



        getGovernments()
    }

    fun getGovernments() {

        progressBar?.visibility = View.VISIBLE
        myAPI?.trade("Government", "getAll", "", "", "", "", "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        progressBar?.visibility = View.GONE




                        governmentNamesList.clear()
                        governmentIDSList.clear()
                        cityNamesList.clear()
                        cityIDSList.clear()
                        areaNamesList.clear()
                        areaIDSList.clear()

                        for (model in data.governments!!) {
                            governmentNamesList.add(model.name)
                            governmentIDSList.add(model.id)

                        }

                        for (model in data.cities!!) {
                            cityNamesList.add(model.name)
                            cityIDSList.add(model.id)

                        }

                        for (model in data.areas!!) {
                            areaNamesList.add(model.name)
                            areaIDSList.add(model.id)

                        }

                        governmentSpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, governmentNamesList))
                        citySpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, cityNamesList))
                        areaSpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, areaNamesList))


                    }

                    override fun onError(e: Throwable) {
                        progressBar?.visibility = View.GONE
                    }

                    override fun onComplete() {}
                })

    }


    fun getCities(action: String?,
                  governmentId: String,
                  cityId: String,
                  areaId: String) {


        progressBar?.visibility = View.VISIBLE
        myAPI?.trade("City", action!!, governmentId, "", "", "", "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        progressBar?.visibility = View.GONE

                        cityNamesList.clear()
                        cityIDSList.clear()
                        for (model in data.cities!!) {
                            cityNamesList.add(model.name)
                            cityIDSList.add(model.id)

                        }

                        citySpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, cityNamesList))


                    }

                    override fun onError(e: Throwable) {
                        progressBar?.visibility = View.GONE
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }


    fun getAreas(action: String,
                 cityId: String,
                 areaId: String) {
        progressBar?.visibility = View.VISIBLE

        myAPI?.trade("Area", action, "", cityId, areaId, "", "", "", "", "", "", "", "", "")!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        progressBar?.visibility = View.GONE

                        areaNamesList.clear()
                        areaIDSList.clear()
                        for (model in data.areas!!) {
                            areaNamesList.add(model.name)
                            areaIDSList.add(model.id)

                        }

                        areaSpinner?.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, areaNamesList))

                    }

                    override fun onError(e: Throwable) {
                        progressBar?.visibility = View.GONE
                        println(e.message)
                        System.out.println(e.message)
                    }

                    override fun onComplete() {}
                })

    }

}
