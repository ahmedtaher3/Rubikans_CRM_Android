package com.devartlab.ui.main.ui.callmanagement.plan.choosestartpoint

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.ApiServices
import com.devartlab.data.retrofit.RetrofitClient
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.activity.ActivityEntity
import com.devartlab.data.room.list.ListDao
import com.devartlab.data.room.list.ListEntity
import com.devartlab.data.shared.DataManager
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_choose_start_point.*
import retrofit2.Retrofit

private const val TAG = "ChooseStartPoint"
class ChooseStartPoint(
    private var activity: AppCompatActivity,
    private var chooseStartPointInterFace: ChooseStartPointInterFace,
    private var dataManager: DataManager,
    private var activityType: Int,
    private val activities: ActivityEntity?,
    private val DATE: String?,
    private val Type: Int?
) : Dialog(activity) {

    lateinit var recyclerView: RecyclerView
    lateinit var close: ImageView
    lateinit var startPointAdapter: StartPointAdapter
    var myAPI: ApiServices? = null
    var retrofit: Retrofit? = null
    var editText: EditText? = null
    var list: ArrayList<ListEntity>? = null
    var listDao: ListDao? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_start_point)
        retrofit = RetrofitClient(dataManager!!).instance!!
        myAPI = retrofit!!.create(ApiServices::class.java)
        startPointAdapter = StartPointAdapter(
            activity,
            chooseStartPointInterFace,
            dataManager,
            activityType,
            activities!!,
            DATE!!,
            Type!!
        )
        recyclerView = findViewById(R.id.startPointRecyclerView)
        close = findViewById(R.id.close)
        editText = findViewById(R.id.editText_search)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = startPointAdapter
        listDao = DatabaseClient.getInstance(context)?.appDatabase?.listDao()


        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


                if (charSequence.length > 0) {
                    getStartPointList("0", "0", "0", "0", charSequence.toString())

                } else {
                    getStartPointList("0", "0", "0", "0", "0")

                }


            }

            override fun afterTextChanged(editable: Editable) {}
        })

        close?.setOnClickListener(View.OnClickListener { dismiss() })

        getStartPointList("0", "0", "0", "0", "0")
    }


    public fun getStartPointList(
        TerrAssignIdStr: String,
        BrickIdStr: String,
        SpecialityIdStr: String,
        ClassIdStr: String,
        FilterText: String
    ) {


        if (dataManager.offlineMood) {

            Log.d(TAG, "getStartPointList: $FilterText")
            Completable.fromAction {
                if (FilterText.isNullOrEmpty() || FilterText == "0") {
                    startPointAdapter.setMyData(listDao?.pharmacies as ArrayList<ListEntity>)
                } else {
                    startPointAdapter.setMyData(listDao?.getPharmacies(FilterText) as ArrayList<ListEntity>)
                }
            }.subscribeOn(Schedulers.io())
                .subscribe()
        } else {

            ProgressBar.visibility = View.VISIBLE
            myAPI?.getStartPointList(
                dataManager.user.accId,
                TerrAssignIdStr,
                BrickIdStr,
                SpecialityIdStr,
                ClassIdStr,
                FilterText
            )!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<ListEntity>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(data: ArrayList<ListEntity>) {

                        list = data
                        startPointAdapter.setMyData(data)

                        Completable.fromAction {
                            listDao?.insertAll(data)
                        }.subscribeOn(Schedulers.io())
                            .subscribe()
                    }

                    override fun onError(e: Throwable) {

                        ProgressBar.visibility = View.GONE
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()

                    }

                    override fun onComplete() {
                        ProgressBar.visibility = View.GONE
                    }
                })
        }
    }
}
