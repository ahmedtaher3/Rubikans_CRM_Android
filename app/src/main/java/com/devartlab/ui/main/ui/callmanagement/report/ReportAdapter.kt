package com.devartlab.ui.main.ui.callmanagement.report

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.RecyclerAdBinding
import com.devartlab.databinding.ReportItemBinding
import com.devartlab.databinding.ReportItemMobileBinding
import com.devartlab.model.AdModel
import com.devartlab.ui.main.ui.callmanagement.report.arrange.ArrangeActivity
import com.google.gson.Gson
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension
import java.util.*

private const val TAG = "ReportAdapter"

class ReportAdapter(context: Context, reportInterface: ReportInterface, dataManager: DataManager, private val appCompatActivity: AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val myData: MutableList<PlanEntity>
    private val context: Context
    private var mDrawableBuilder: TextDrawable? = null
    private val mItemTouchHelperExtension: ItemTouchHelperExtension? = null
    var reportInterface: ReportInterface
    var dataManager: DataManager

    private val TYPE_AD = 0
    private val TYPE_PLAN = 1
    
    fun setDatas(datas: List<PlanEntity>?) {
        myData.clear()
        myData.addAll(datas!!)
    }

    fun getSelected() : Int {

        var count = 0
        for (i in myData)
        {
            count++
        }
        return count
    }

    fun updateData(datas: List<PlanEntity>) {
        setDatas(datas)
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int { // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous

        if (myData[position].isAd == true) {
            return TYPE_AD
        }
        else {
            return TYPE_PLAN
        }


    }

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        when (viewType) {
            TYPE_PLAN -> {
                if (dataManager.isTablet) {
                    return ViewHolder(ReportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
                else {
                    return ViewHolderMobile(ReportItemMobileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
            }
            TYPE_AD -> {
                return ViewHolderAD(RecyclerAdBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            }
            else -> {
                if (dataManager.isTablet) {
                    return ViewHolder(ReportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
                else {
                    return ViewHolderMobile(ReportItemMobileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
            }
        }
        
 
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (myData[position].isAd == true) {
            val baseViewHolder = holder as ViewHolderAD
            baseViewHolder.bind(myData[position])
        }
        else {
            if (dataManager.isTablet) {
                val baseViewHolder = holder as ViewHolder
                baseViewHolder.bind(myData[position])
            }
            else {
                val baseViewHolder = holder as ViewHolderMobile
                baseViewHolder.bind(myData[position])
            }
        }
    }

    private fun doDelete(adapterPosition: Int) {
        myData.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    fun move(from: Int, to: Int) {
        val prev = myData.removeAt(from)
        myData.add(if (to > from) to - 1 else to, prev)
        notifyItemMoved(from, to)
    }

    override fun getItemCount(): Int {
        return myData.size
    }



    inner class ViewHolderMobile(var binding: ReportItemMobileBinding) : RecyclerView.ViewHolder(binding.root) {

        lateinit var _binding: ReportItemMobileBinding

        init {
            this._binding = binding;
        }


        fun bind(model: PlanEntity) {
            when (model.activityTypeID) {
                1 -> { // ITEM_TYPE_SINGLE
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                2 -> { // ITEM_TYPE_DOUBLE
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = "( " + model.doubleVisitEmpName + " )"
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.GONE
                }
                4 -> { // ITEM_TYPE_SPECIAL_TASK
                    try {
                        _binding?.name.text = model.taskText
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                5 -> { // ITEM_TYPE_OFFICE
                    try {
                        _binding?.name.text = model.officeDescription
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                6 -> { // ITEM_TYPE_MEETing
                    try {
                        _binding?.name.text = model.meetingMembers
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                7 -> { // ITEM_TYPE_SOCIAL
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                else -> {
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
            }
            _binding?.activity.text = model.activityEnName
            _binding?.degree.text = model._class
            _binding?.specialist.text = model.speciality
            _binding?.brick.text = model.brick
            _binding?.address.text = model.address
            setName(model.customerName, model.planColor!!)






            if (model.extraVisit!!) {
                _binding?.mainContent.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                if (!model.reported!!) {
                    _binding?.deleteExtra.visibility = View.VISIBLE
                }

            }
            else {
                _binding?.mainContent.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                _binding?.deleteExtra.visibility = View.GONE
            }

            if (model.visit!!) {

                _binding?.location.visibility = View.VISIBLE
                if (model.cusLat == "" || model.cusLang == "" || model.cusLat == "0" || model.cusLang == "0" || model.cusLat == "1" || model.cusLang == "1") {

                    _binding?.location.setImageResource(R.drawable.not_valid_location)
                    _binding?.location.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_red_dark), PorterDuff.Mode.MULTIPLY)

                }
                else {
                    _binding?.location.setImageResource(R.drawable.valid_location)
                    _binding?.location.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_dark), PorterDuff.Mode.MULTIPLY)

                }



                _binding?.checkVisit.setImageResource(R.drawable.ic_check_circle_grey600_24dp)
                _binding?.checkVisit.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_light), PorterDuff.Mode.MULTIPLY)
            }
            else if (model.isStarted!!) {

                _binding?.location.visibility = View.GONE

                _binding?.checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                _binding?.checkVisit.setColorFilter(ContextCompat.getColor(context, R.color.yellow), PorterDuff.Mode.MULTIPLY)
            }
            else {

                _binding?.location.visibility = View.GONE
                _binding?.checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                _binding?.checkVisit.setColorFilter(ContextCompat.getColor(context, R.color.grey), PorterDuff.Mode.MULTIPLY)
            }


            _binding?.makeCall.setOnClickListener {



                if (dataManager.startShift) {
                    if (model.activityTypeID == 1 || model.activityTypeID == 2) reportInterface.startVisit(model)
                    else reportInterface.startSocialVisit(model)
                }
                else Toast.makeText(context, "You have to start shift first", Toast.LENGTH_SHORT).show()



                /*   val dialogBuilder = AlertDialog.Builder(context)
                   val dialogView: View = appCompatActivity.layoutInflater.inflate(R.layout.edit_layout, null)
                   dialogBuilder.setView(dialogView)
     
     
                   val call = dialogView.findViewById<LinearLayout>(R.id.call)
                   val order = dialogView.findViewById<LinearLayout>(R.id.order)
                   val cancel = dialogView.findViewById<LinearLayout>(R.id.cancel)
                   val collect = dialogView.findViewById<LinearLayout>(R.id.collect)
     
                   val alertDialog = dialogBuilder.create()
     
                   alertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                   alertDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
     
                   call.setOnClickListener(View.OnClickListener {
     
                       if (dataManager.startShift) {
                           if (model.activityTypeID == 1 || model.activityTypeID == 2) reportInterface.startVisit(model)
                           else reportInterface.startSocialVisit(model)
                       }
                       else Toast.makeText(context, "You have to start shift first", Toast.LENGTH_SHORT).show()
     
     
                       alertDialog.dismiss()
                   })
     
                   order.setOnClickListener(View.OnClickListener {
     
                       reportInterface.order(model)
     
                       alertDialog.dismiss()
                   })
     
                   collect.setOnClickListener(View.OnClickListener {
     
                       reportInterface.collect(model)
     
                       alertDialog.dismiss()
                   })
     
     
                   cancel.setOnClickListener(View.OnClickListener { alertDialog?.dismiss() })
     
                   alertDialog.show()
     */

            }

            itemView.setOnLongClickListener {

                Log.d(TAG, "bind: ")


                true
            }


            itemView.setOnLongClickListener {

                val intent = Intent(context, ArrangeActivity::class.java);
                intent.putExtra("CustomerModel", model);
                context.startActivity(intent);


                true
            }


            _binding?.deleteExtra.setOnClickListener { reportInterface.deleteExtra(model) }
        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder().buildRound(letter, color)
            _binding?.image.setImageDrawable(mDrawableBuilder)
        }


    }

    inner class ViewHolder(var binding: ReportItemBinding) : RecyclerView.ViewHolder(binding.root) {


        lateinit var _binding: ReportItemBinding

        init {
            this._binding = binding;
        }

        fun bind(model: PlanEntity) {
            when (model.activityTypeID) {
                1 -> { // ITEM_TYPE_SINGLE
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                2 -> { // ITEM_TYPE_DOUBLE
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = "( " + model.doubleVisitEmpName + " )"
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.GONE
                }
                4 -> { // ITEM_TYPE_SPECIAL_TASK
                    try {
                        _binding?.name.text = model.taskText
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                5 -> { // ITEM_TYPE_OFFICE
                    try {
                        _binding?.name.text = model.officeDescription
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                6 -> { // ITEM_TYPE_MEETing
                    try {
                        _binding?.name.text = model.meetingMembers
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                7 -> { // ITEM_TYPE_SOCIAL
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
                else -> {
                    try {
                        _binding?.name.text = model.customerName
                        _binding?.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                    _binding?.makeCall.visibility = View.VISIBLE
                }
            }
            _binding?.activity.text = model.activityEnName
            _binding?.degree.text = model._class
            _binding?.specialist.text = model.speciality
            _binding?.brick.text = model.brick
            _binding?.address.text = model.address
            setName(model.customerName, model.planColor!!)






            if (model.extraVisit!!) {
                _binding?.mainContent.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                if (!model.reported!!) {
                    _binding?.deleteExtra.visibility = View.VISIBLE
                }

            }
            else {
                _binding?.mainContent.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                _binding?.deleteExtra.visibility = View.GONE
            }

            if (model.visit!!) {

                _binding?.location.visibility = View.VISIBLE
                if (model.cusLat == "" || model.cusLang == "" || model.cusLat == "0" || model.cusLang == "0" || model.cusLat == "1" || model.cusLang == "1") {

                    _binding?.location.setImageResource(R.drawable.not_valid_location)
                    _binding?.location.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_red_dark), PorterDuff.Mode.MULTIPLY)

                }
                else {
                    _binding?.location.setImageResource(R.drawable.valid_location)
                    _binding?.location.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_dark), PorterDuff.Mode.MULTIPLY)

                }



                _binding?.checkVisit.setImageResource(R.drawable.ic_check_circle_grey600_24dp)
                _binding?.checkVisit.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_light), PorterDuff.Mode.MULTIPLY)
            }
            else if (model.isStarted!!) {

                _binding?.location.visibility = View.GONE

                _binding?.checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                _binding?.checkVisit.setColorFilter(ContextCompat.getColor(context, R.color.yellow), PorterDuff.Mode.MULTIPLY)
            }
            else {

                _binding?.location.visibility = View.GONE
                _binding?.checkVisit.setImageResource(R.drawable.ic_checkbox_blank_circle_grey600_24dp)
                _binding?.checkVisit.setColorFilter(ContextCompat.getColor(context, R.color.grey), PorterDuff.Mode.MULTIPLY)
            }


            _binding?.makeCall.setOnClickListener {



                if (dataManager.startShift) {
                    if (model.activityTypeID == 1 || model.activityTypeID == 2) reportInterface.startVisit(model)
                    else reportInterface.startSocialVisit(model)
                }
                else Toast.makeText(context, "You have to start shift first", Toast.LENGTH_SHORT).show()



                /*   val dialogBuilder = AlertDialog.Builder(context)
                   val dialogView: View = appCompatActivity.layoutInflater.inflate(R.layout.edit_layout, null)
                   dialogBuilder.setView(dialogView)
     
     
                   val call = dialogView.findViewById<LinearLayout>(R.id.call)
                   val order = dialogView.findViewById<LinearLayout>(R.id.order)
                   val cancel = dialogView.findViewById<LinearLayout>(R.id.cancel)
                   val collect = dialogView.findViewById<LinearLayout>(R.id.collect)
     
                   val alertDialog = dialogBuilder.create()
     
                   alertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
                   alertDialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
     
                   call.setOnClickListener(View.OnClickListener {
     
                       if (dataManager.startShift) {
                           if (model.activityTypeID == 1 || model.activityTypeID == 2) reportInterface.startVisit(model)
                           else reportInterface.startSocialVisit(model)
                       }
                       else Toast.makeText(context, "You have to start shift first", Toast.LENGTH_SHORT).show()
     
     
                       alertDialog.dismiss()
                   })
     
                   order.setOnClickListener(View.OnClickListener {
     
                       reportInterface.order(model)
     
                       alertDialog.dismiss()
                   })
     
                   collect.setOnClickListener(View.OnClickListener {
     
                       reportInterface.collect(model)
     
                       alertDialog.dismiss()
                   })
     
     
                   cancel.setOnClickListener(View.OnClickListener { alertDialog?.dismiss() })
     
                   alertDialog.show()
     */

            }

            itemView.setOnLongClickListener {

                Log.d(TAG, "bind: ")


                true
            }


            itemView.setOnLongClickListener {

                val intent = Intent(context, ArrangeActivity::class.java);
                intent.putExtra("CustomerModel", model);
                context.startActivity(intent);


                true
            }


            _binding?.deleteExtra.setOnClickListener { reportInterface.deleteExtra(model) }
        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder().buildRound(letter, color)
            _binding?.image.setImageDrawable(mDrawableBuilder)
        }


    }

    inner class ViewHolderAD(var binding: RecyclerAdBinding) : RecyclerView.ViewHolder(binding.root) {

        lateinit var _binding: RecyclerAdBinding

        init {
            this._binding = binding;
        }


        fun bind(model: PlanEntity) {


            val ad = Gson().fromJson(model.adModel, AdModel::class.java) as AdModel
            Log.d(TAG, "bind: ${ad.resourceLink}")
            Log.d(TAG, "bind: ${ad.type}")

            when (ad.type) {

                "Image" -> {
                    Glide.with(context).load(ad.resourceLink).fitCenter().placeholder(R.drawable.devart_logo).into(_binding.imageAd)

                }

                "GIF" -> {
                    Glide.with(context).asGif().load(ad.resourceLink).fitCenter().placeholder(R.drawable.devart_logo).into(_binding.imageAd)

                }
                else -> {
                    Glide.with(context).load(ad.resourceLink).fitCenter().placeholder(R.drawable.devart_logo).into(_binding.imageAd)

                }

            }

        }

    }


    
    
 
  init {
      myData = ArrayList()
      this.context = context
      this.reportInterface = reportInterface
      this.dataManager = dataManager
  }
}