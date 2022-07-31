package com.devartlab.ui.main.ui.devartlink.handBook

import android.view.LayoutInflater
import android.view.ViewGroup
import com.devartlab.R
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookItem
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookSubs
import com.iamkamrul.expandablerecyclerviewlist.adapter.ExpandableRecyclerAdapter
import com.iamkamrul.expandablerecyclerviewlist.model.ParentListItem


class HandBookAdapter : ExpandableRecyclerAdapter<HandBookViewHolder, HandBookListViewHolder>(){

    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup
    ): HandBookViewHolder {
        val view = LayoutInflater.from(parentViewGroup.context).inflate(R.layout.hand_book_item, parentViewGroup, false)
        return HandBookViewHolder(view)
    }

    override fun onCreateChildViewHolder(parentViewGroup: ViewGroup): HandBookListViewHolder {
        val view = LayoutInflater.from(parentViewGroup.context).inflate(R.layout.hand_book_subs_item, parentViewGroup, false)
        return HandBookListViewHolder(view)
    }

    override fun onBindParentViewHolder(parentViewHolder: HandBookViewHolder, position: Int, parentListItem: ParentListItem) {
        val data = parentListItem as HandBookItem
        parentViewHolder.bind(data)
    }

    override fun onBindChildViewHolder(childViewHolder: HandBookListViewHolder, position: Int, childListItem: Any) {
        val data = childListItem as HandBookSubs
        childViewHolder.bind(data)
    }
}