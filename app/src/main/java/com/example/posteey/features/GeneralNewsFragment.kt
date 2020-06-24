package com.example.posteey.features

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posteey.NewsDetailActivity
import com.example.posteey.R
import com.example.posteey.adapter.NewsAdapter
import com.example.posteey.adapter.RecyclerViewPaginator
import com.example.posteey.utils.clearShimmer
import com.example.posteey.utils.displayShimmer
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.Country
import com.example.presentation.utils.ViewState
import com.example.presentation.viewmodels.GeneralNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_base_news.*

@AndroidEntryPoint
class GeneralNewsFragment : BaseNewsFragment() {

    private val generalNewsViewModel: GeneralNewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private val generalNewsArticles: MutableList<NewsArticle> = mutableListOf()
    private val owner = { lifecycle }
    private val recyclerViewPaginator by lazy {
        object : RecyclerViewPaginator(recycler_view) {
            override fun loadMore(currentPage: Int) {
                generalNewsViewModel.loadMoreGeneralNews(currentPage)
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_base_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            generalNewsViewModel.getGeneralNews(page = 1)
        }

        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(generalNewsArticles, requireContext())
        newsAdapter.setNewsArticleClickListener {
            startActivity(NewsDetailActivity.getIntent(this.requireContext(), it))
        }
        recycler_view.adapter = newsAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.addOnScrollListener(recyclerViewPaginator as RecyclerViewPaginator)
    }

    private fun observeViewModel() {
        generalNewsViewModel.generalNewsLiveData.observe(owner) { viewState ->
            when(viewState) {
                is ViewState.Loading -> {
                    if (generalNewsViewModel.pageNumber > 1) {
                        load_more_progress.visibility = VISIBLE
                    } else {
                        shimmer_view_container.displayShimmer()
                    }
                }
                is ViewState.Success -> {
                    load_more_progress.visibility = GONE
                    shimmer_view_container.clearShimmer()
                    val state = recycler_view.layoutManager?.onSaveInstanceState()
                    newsAdapter.updateItems(viewState.data)
                    recycler_view.layoutManager?.onRestoreInstanceState(state)
                }
                is ViewState.Error -> {
                    load_more_progress.visibility = GONE
                    shimmer_view_container.clearShimmer()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GeneralNewsFragment()
    }
}
