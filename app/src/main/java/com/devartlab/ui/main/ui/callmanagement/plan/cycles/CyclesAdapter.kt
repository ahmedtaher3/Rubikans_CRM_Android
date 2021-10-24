package com.devartlab.ui.main.ui.callmanagement.plan.cycles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.shared.DataManager
import com.devartlab.model.Cycle
import java.util.*

class CyclesAdapter(
    context: Context,
    cycleInterface: CycleInterface,
    dataManager: DataManager
) : RecyclerView.Adapter<CyclesAdapter.MyViewHolder>() {
    private var myData: List<Cycle>
    private val context: Context
    private val cycleInterface: CycleInterface
    private val dataManager: DataManager

    interface CycleInterface {
        fun setCycle(cycle: Cycle?)
    }

    /**
     * check if record getting from myData return 0 else 1
     *
     * in case that mean he create a new plan with planID = 0
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == myData.size) 1 else 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View
        itemView = if (viewType == 0) {
            LayoutInflater.from(parent.context).inflate(R.layout.cycle_item, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cycle_item_new, parent, false)
        }
        return MyViewHolder(
            itemView
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        when (holder.itemViewType) {
            0 -> {
                val model = myData[position]
                holder.CycleName.text = model.cycleArName
                holder.CycleStart.text = model.fromDate.substring(0, 10)
                holder.CycleEnd.text = model.toDate.substring(0, 10)
                holder.cardCycle.setOnClickListener {
                    dataManager.isNewCycle(false)
                    cycleInterface.setCycle(model)
                }
            }
            1 -> {
                /**
                 * check if open cycle id which get from loginData api = last cycle id which get from get all cycles api
                 */
                try {
                    if (dataManager.newOldCycle.openCycleId == myData[myData.size - 1]
                            .cycleId
                    ) {
                        holder.cardCycle.visibility = View.GONE
                    }
                } catch (e: Exception) {
                }
                holder.CycleStart.text =
                    dataManager.newOldCycle.openCycleFromDate!!.substring(0, 10)
                holder.CycleEnd.text = dataManager.newOldCycle.openCycleToDate!!.substring(0, 10)
                holder.cardCycle.setOnClickListener {
                    dataManager.isNewCycle(true)
                    cycleInterface.setCycle(
                        Cycle(
                            dataManager.newOldCycle.openPLanCycleId
                            , dataManager.newOldCycle.openCycleId
                            , dataManager.newOldCycle.openCycleFromDate
                            , dataManager.newOldCycle.openCycleToDate
                            , 0
                            , ""
                            , true
                            , dataManager.newOldCycle.openFromDateMs
                            , dataManager.newOldCycle.openToDateMs
                        )
                    )
                }
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size + 1
    }

    fun setMyData(myData: List<Cycle>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var CycleName: TextView
        var CycleEnd: TextView
        var CycleStart: TextView
        var cardCycle: CardView

        init {
            CycleName = v.findViewById(R.id.CycleName)
            CycleStart = v.findViewById(R.id.CycleStart)
            CycleEnd = v.findViewById(R.id.CycleEnd)
            cardCycle = v.findViewById(R.id.cardCycle)
        }
    }

    init {
        myData = ArrayList()
        this.context = context
        this.cycleInterface = cycleInterface
        this.dataManager = dataManager
    }
}