package com.devartlab.ui.main.ui.trade

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServicesGoogle
import com.devartlab.model.GoogleRequestResponse
import com.devartlab.utils.ProgressLoading
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddNewDialog(context: Context
                   , private val activity: AppCompatActivity
                   , private val FLAG: Int
                   , private val governmentNamesList: ArrayList<String>
                   , private val governmentIDSList: ArrayList<Int>

                   , private val citiesNamesList: ArrayList<String>
                   , private val citiesIDSList: ArrayList<Int>
                   , private val myAPI: ApiServicesGoogle
) : Dialog(context) {


    lateinit var government: ScrollView
    lateinit var city: ScrollView
    lateinit var area: ScrollView
    lateinit var addGovernment: Button
    lateinit var addCity: Button
    lateinit var addArea: Button
    lateinit var governmentText: EditText
    lateinit var cityText: EditText
    lateinit var areaText: EditText
    lateinit var governmentSpinner: SearchableSpinner
    lateinit var citySpinner: SearchableSpinner


    val govId = 0
    val cityId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_place_trade)

        government = findViewById(R.id.government)
        city = findViewById(R.id.city)
        area = findViewById(R.id.area)

        addGovernment = findViewById(R.id.addGovernment)
        addCity = findViewById(R.id.addCity)
        addArea = findViewById(R.id.addArea)


        governmentText = findViewById(R.id.governmentText)
        cityText = findViewById(R.id.cityText)
        areaText = findViewById(R.id.areaText)

        governmentSpinner = findViewById(R.id.governmentSpinner)
        citySpinner = findViewById(R.id.citySpinner)


        governmentSpinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, governmentNamesList)


        citySpinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, citiesNamesList)


        when (FLAG) {

            1 -> {
                government.visibility = View.VISIBLE
            }
            2 -> {
                city.visibility = View.VISIBLE

            }
            3 -> {
                area.visibility = View.VISIBLE

            }
        }


        addGovernment.setOnClickListener(View.OnClickListener {
            insertPlace("Government", "insert", "", "", governmentText.text.toString(), "", "")
        })

        addCity.setOnClickListener(View.OnClickListener {
            insertPlace("City", "insert", govId.toString(), "", "", cityText.text.toString(), "")

        })

        addArea.setOnClickListener(View.OnClickListener {
            insertPlace("Area", "insert", "", cityId.toString(), "", "", areaText.text.toString())
        })


    }

    fun insertPlace(sheet: String?,
                    action: String?,
                    governmentId: String,
                    cityId: String,
                    governmentName: String,
                    cityName: String,
                    areaName: String) {

        ProgressLoading.show(activity)

        myAPI?.tradeAddNewPlace(
                sheet!!
                , action!!
                , governmentId
                , cityId
                , governmentName
                , cityName
                , areaName)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GoogleRequestResponse> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: GoogleRequestResponse) {
                        ProgressLoading.dismiss()
                        Toast.makeText(context, "onNext", Toast.LENGTH_SHORT).show()

                        dismiss()
                    }

                    override fun onError(e: Throwable) {
                        ProgressLoading.dismiss()
                        Toast.makeText(context, "onError", Toast.LENGTH_SHORT).show()

                    }

                    override fun onComplete() {}
                })

    }


}
