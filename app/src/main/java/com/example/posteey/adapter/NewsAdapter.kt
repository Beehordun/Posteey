package com.example.posteey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.posteey.R
import com.example.posteey.utils.DateTimeUtil
import com.example.presentation.models.NewsArticle
import kotlinx.android.synthetic.main.news_item.view.*

typealias NewsArticleClickListener = ((newsArticle: NewsArticle) -> Unit)
class NewsAdapter(private var newsArticles: List<NewsArticle>,
                  private val context: Context
) : RecyclerView.Adapter<NewsAdapter.AdapterViewHolder>() {

    private var newsArticleClickListener: NewsArticleClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_item, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val article = newsArticles[position]

        Glide.with(context)
            .load(article.urlToImage)
            .centerCrop()
            .placeholder(R.color.light_gray)
            .into(holder.itemView.news_image)

        holder.itemView.date_text.text = DateTimeUtil.getFormattedDateTime(article.publishedAt)
        holder.itemView.title_text.text = article.title
    }

    override fun getItemCount(): Int = newsArticles.size

    fun setNewsArticleClickListener(listener: NewsArticleClickListener) {
        newsArticleClickListener = listener
    }

    fun updateItems(newItem: List<NewsArticle>) {
        DiffUtil.calculateDiff(NewsArticleDiffUtil(newsArticles, newItem)).dispatchUpdatesTo(this)
        this.newsArticles = newItem
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val clickedFilter = newsArticles[adapterPosition]
            newsArticleClickListener?.invoke(clickedFilter)
        }
    }
}
