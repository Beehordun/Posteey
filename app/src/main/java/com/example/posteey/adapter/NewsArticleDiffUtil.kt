package com.example.posteey.features

import androidx.recyclerview.widget.DiffUtil
import com.example.presentation.models.NewsArticle

class NewsArticleDiffUtil(
    private val oldList: List<NewsArticle>,
    private val newList: List<NewsArticle>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
