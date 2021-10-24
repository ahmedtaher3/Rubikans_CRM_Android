package com.devartlab.ui.dialogs.massages

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.room.DatabaseClient
import com.devartlab.data.room.massages.MassagesDao
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class MassagesActivity(context: Context , private var id :String
                       ,   private var activity :AppCompatActivity
                       ,  private var massagesInterface : OnMassageSelect)   : Dialog(context) {

    lateinit var massages :RecyclerView
    lateinit var massagesDao :MassagesDao
    lateinit var massagesAdapter :MassagesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_massages)
        massagesDao =  DatabaseClient.getInstance(activity.application)?.appDatabase?.massagesDao()!!

        massagesAdapter = MassagesAdapter(context , activity, massagesInterface)
        massages = findViewById(R.id.massages)
        massages.layoutManager = LinearLayoutManager(context)
        massages.adapter = massagesAdapter


        Completable.fromAction {

            massagesAdapter.setMyData( massagesDao.getAll(id.toInt()))

        }.subscribeOn(Schedulers.io())
                .subscribe()

    }
}
