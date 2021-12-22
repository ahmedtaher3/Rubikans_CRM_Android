package com.devartlab.ui.main.ui.callmanagement.plan

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.devartlab.R
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.databinding.PlanItemBinding
import com.devartlab.databinding.PlanItemMobileBinding
import com.devartlab.databinding.RecyclerAdBinding
import com.devartlab.model.AdModel
import com.google.gson.Gson


private const val TAG = "PlansAdapter"

class PlansAdapter(context: Context, dataManager: DataManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val myData: ArrayList<PlanEntity>
    private val context: Context
    private var mDrawableBuilder: TextDrawable? = null
    private val dataManager: DataManager

    private val TYPE_AD = 0
    private val TYPE_PLAN = 1


    fun setMyData(myData: ArrayList<PlanEntity>) {
        this.myData.clear()
        this.myData.addAll(myData)
        notifyDataSetChanged()
    }


    private val layoutInflater: LayoutInflater
        private get() = LayoutInflater.from(context)


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
                    return ViewHolder(PlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
                else {
                    return ViewHolderMobile(PlanItemMobileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
            }
            TYPE_AD -> {
                return ViewHolderAD(RecyclerAdBinding.inflate(LayoutInflater.from(parent.context), parent, false))

            }
            else -> {
                if (dataManager.isTablet) {
                    return ViewHolder(PlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
                else {
                    return ViewHolderMobile(PlanItemMobileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }


    inner class ViewHolderMobile(var binding: PlanItemMobileBinding) : RecyclerView.ViewHolder(binding.root) {

        lateinit var _binding: PlanItemMobileBinding

        init {
            this._binding = binding;
        }


        fun bind(model: PlanEntity) {
            if (model.updated!!) {
                _binding.updated.setImageResource(R.drawable.ic_check)
            }
            else {
                _binding.updated.setImageResource(R.drawable.ic_exclamation)
            }
            if (model.extraVisit!!) {
                _binding.mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            }
            else {
                _binding.mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            when (model.activityTypeID) {
                1 ->                     // SINGLE
                    try {
                        _binding.image.setImageResource(R.drawable.single_visit)
                        _binding.name.text = model.customerName
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                2 ->                     // DOUBLE
                    try {
                        _binding.image.setImageResource(R.drawable.double_visit)
                        _binding.name.text = model.customerName
                        _binding.doubleEmpName.text = "( " + model.doubleVisitEmpName + " )"
                    }
                    catch (e: Exception) {
                    }
                4 ->                     //  Special Task
                    try {
                        _binding.image.setImageResource(R.drawable.special_task)
                        _binding.name.text = model.taskText
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                5 ->                     // OFFICE
                    try {
                        _binding.image.setImageResource(R.drawable.office)
                        _binding.name.text = model.officeDescription
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                6 ->                     // Meeting
                    try {
                        _binding.image.setImageResource(R.drawable.meeting)
                        _binding.name.text = model.meetingMembers
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                7 ->                     // SOCIAL
                    try {
                        _binding.image.setImageResource(R.drawable.single_visit)
                        _binding.name.text = model.customerName
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                else -> try {
                    _binding.image.setImageResource(R.drawable.single_visit)
                    _binding.name.text = model.customerName
                    _binding.doubleEmpName.text = ""
                }
                catch (e: Exception) {
                }
            }


            _binding.degree.text = model._class
            _binding.activity.text = model.activityEnName
            _binding.specialist.text = model.speciality
            _binding.brick.text = model.brick
            _binding.address.text = model.address

            itemView.setOnClickListener { Log.d(TAG, "bind: " + model.startAt) }


        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder().buildRound(letter, color)
            _binding.image.setImageDrawable(mDrawableBuilder)
        }


    }

    inner class ViewHolder(var binding: PlanItemBinding) : RecyclerView.ViewHolder(binding.root) {


        lateinit var _binding: PlanItemBinding

        init {
            this._binding = binding;
        }

        fun bind(model: PlanEntity) {
            if (model.updated!!) {
                _binding.updated.setImageResource(R.drawable.ic_check)
            }
            else {
                _binding.updated.setImageResource(R.drawable.ic_exclamation)
            }
            if (model.extraVisit!!) {
                _binding.mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            }
            else {
                _binding.mViewContent.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            when (model.activityTypeID) {
                1 ->                     // SINGLE
                    try {
                        _binding.image.setImageResource(R.drawable.single_visit)
                        _binding.name.text = model.customerName
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                2 ->                     // DOUBLE
                    try {
                        _binding.image.setImageResource(R.drawable.double_visit)
                        _binding.name.text = model.customerName
                        _binding.doubleEmpName.text = "( " + model.doubleVisitEmpName + " )"
                    }
                    catch (e: Exception) {
                    }
                4 ->                     //  Special Task
                    try {
                        _binding.image.setImageResource(R.drawable.special_task)
                        _binding.name.text = model.taskText
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                5 ->                     // OFFICE
                    try {
                        _binding.image.setImageResource(R.drawable.office)
                        _binding.name.text = model.officeDescription
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                6 ->                     // Meeting
                    try {
                        _binding.image.setImageResource(R.drawable.meeting)
                        _binding.name.text = model.meetingMembers
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                7 ->                     // SOCIAL
                    try {
                        _binding.image.setImageResource(R.drawable.single_visit)
                        _binding.name.text = model.customerName
                        _binding.doubleEmpName.text = ""
                    }
                    catch (e: Exception) {
                    }
                else -> try {
                    _binding.image.setImageResource(R.drawable.single_visit)
                    _binding.name.text = model.customerName
                    _binding.doubleEmpName.text = ""
                }
                catch (e: Exception) {
                }
            }


            _binding.degree.text = model._class
            _binding.activity.text = model.activityEnName
            _binding.specialist.text = model.speciality
            _binding.brick.text = model.brick
            _binding.address.text = model.address

            itemView.setOnClickListener { Log.d(TAG, "bind: " + model.startAt) }


        }

        fun setName(title: String?, color: Int) {
            var letter = "A"
            if (title != null && !title.isEmpty()) {
                letter = title.substring(0, 1)
            }
            mDrawableBuilder = TextDrawable.builder().buildRound(letter, color)
            _binding.image.setImageDrawable(mDrawableBuilder)
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
        this.dataManager = dataManager
    }
}