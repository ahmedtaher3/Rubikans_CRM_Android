package com.devartlab.ui.main.ui.devartlink.handBook

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.databinding.BookDescItemBinding
import com.devartlab.databinding.BookSubsItemBinding
import com.devartlab.databinding.BookTitlesItemBinding
import com.devartlab.ui.main.ui.devartlink.handBook.model.BookItem

class BookAdapter(private var context: Context, private var myData: ArrayList<BookItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_TITLE = 0
    private val TYPE_SUB_TITLE = 1
    private val TYPE_DESCRIPTION = 2
    var SERACHED = -1

    fun setMyData(data: ArrayList<BookItem>) {
        myData.clear()
        myData.addAll(data)
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        when (myData[position].type) {

            0 -> {
                return TYPE_TITLE
            }

            1 -> {
                return TYPE_SUB_TITLE

            }

            2 -> {
                return TYPE_DESCRIPTION

            }
            else -> {
                return TYPE_TITLE

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TYPE_TITLE -> {
                return ViewHolderTitle(
                    BookTitlesItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            TYPE_SUB_TITLE -> {
                return ViewHolderSubTitle(
                    BookSubsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            TYPE_DESCRIPTION -> {
                return ViewHolderDescription(
                    BookDescItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            else -> {
                return ViewHolderTitle(
                    BookTitlesItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val model = myData[position]

        if (holder is ViewHolderTitle) {
            holder.binding.title.text =
                HtmlCompat.fromHtml(model.text!!, HtmlCompat.FROM_HTML_MODE_LEGACY)

            if (position == SERACHED) {


                holder.binding.layout.setBackgroundResource(R.drawable.searched_transition_view)
                val test = holder.binding.layout.getBackground() as TransitionDrawable
                test.startTransition(2000)

                SERACHED = -1

            } else {
                holder.binding.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }

        } else if (holder is ViewHolderSubTitle) {
            holder.binding.title.text =
                HtmlCompat.fromHtml(model.text!!, HtmlCompat.FROM_HTML_MODE_LEGACY)


            if (position == SERACHED) {


                holder.binding.layout.setBackgroundResource(R.drawable.searched_transition_view)
                val test = holder.binding.layout.getBackground() as TransitionDrawable
                test.startTransition(2000)

                SERACHED = -1

            } else {
                holder.binding.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
        } else if (holder is ViewHolderDescription) {
            holder.binding.title.text =
                HtmlCompat.fromHtml(model.text!!, HtmlCompat.FROM_HTML_MODE_LEGACY)

            if (position == SERACHED) {


                holder.binding.layout.setBackgroundResource(R.drawable.searched_transition_view)
                val test = holder.binding.layout.getBackground() as TransitionDrawable
                test.startTransition(2000)

                SERACHED = -1

            } else {
                holder.binding.layout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return myData.size
    }


    inner class ViewHolderTitle(var binding: BookTitlesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class ViewHolderSubTitle(var binding: BookSubsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class ViewHolderDescription(var binding: BookDescItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


}