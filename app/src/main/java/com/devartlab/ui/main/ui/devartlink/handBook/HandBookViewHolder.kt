package com.devartlab.ui.main.ui.devartlink.handBook

import android.text.Html
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.devartlab.R
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookItem
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookSubs
import com.iamkamrul.expandablerecyclerviewlist.viewholder.ChildViewHolder
import com.iamkamrul.expandablerecyclerviewlist.viewholder.ParentViewHolder



class HandBookViewHolder(itemView: View) : ParentViewHolder(itemView) {
    private lateinit var animation: RotateAnimation

    fun bind(item: HandBookItem){
      itemView.findViewById<TextView>(R.id.handbook_title).text = HtmlCompat.fromHtml(item.title!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    override fun onExpansionToggled(expanded: Boolean) {
        super.onExpansionToggled(expanded)
        animation = if (expanded)
            RotateAnimation(180f,0f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        else
            RotateAnimation(-1 * 180f,0f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)

        animation.duration = 200
        animation.fillAfter = true
        itemView.findViewById<ImageView>(R.id.iv_arrow_expand).startAnimation(animation)

    }

    override fun setExpanded(expanded: Boolean) {
        super.setExpanded(expanded)
        if (expanded)itemView.findViewById<ImageView>(R.id.iv_arrow_expand).rotation = 180f
        else itemView.findViewById<ImageView>(R.id.iv_arrow_expand).rotation = 0f
    }
}


class HandBookListViewHolder(view:View) : ChildViewHolder(view){
    fun bind(item : HandBookSubs){
        itemView.findViewById<TextView>(R.id.handbook_sub_title).text = HtmlCompat.fromHtml(item.title!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}