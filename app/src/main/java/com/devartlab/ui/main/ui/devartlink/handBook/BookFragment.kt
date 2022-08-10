package com.devartlab.ui.main.ui.devartlink.handBook

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentBookBinding
import com.devartlab.ui.main.ui.devartlink.handBook.model.BookItem
import com.devartlab.ui.main.ui.devartlink.handBook.model.HandBookItem


class BookFragment(private val list: ArrayList<HandBookItem>, private val text: String = "") :
    BaseFragment<FragmentBookBinding>() {

    lateinit var binding: FragmentBookBinding
    lateinit var adapter: BookAdapter
    lateinit var layoutManager: LinearLayoutManager
    val fullList = ArrayList<BookItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutManager = LinearLayoutManager(baseActivity)


        for (title in list) {
            fullList.add(BookItem(title.id , title.title, title.image, title.arrange, 0))
            for (subTitle in title.subs) {
                fullList.add(
                    BookItem(
                        subTitle.id,
                        subTitle.title,
                        subTitle.image,
                        subTitle.arrange,
                        1
                    )
                )
                for (desc in subTitle.sections) {
                    fullList.add(
                        BookItem(
                            subTitle.id,
                            subTitle.title,
                            subTitle.image,
                            subTitle.arrange,
                            2
                        )
                    )

                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
        adapter = BookAdapter(baseActivity, fullList)
        binding.titles.layoutManager = layoutManager
        binding.titles.adapter = adapter

        if (!text.isNullOrEmpty()) {
            var position = 0
            position = fullList.indexOfFirst { it.text == text }

            binding.titles.post(Runnable {
                layoutManager.scrollToPositionWithOffset(
                    position,
                    500
                );
            })
            adapter.SERACHED = position
            adapter.notifyDataSetChanged()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_book
    }


}