package com.example.posteey.features

import android.os.Bundle
import android.view.View
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
import com.example.presentation.viewmodels.TechnologyNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_base_news.*

@AndroidEntryPoint
class TechnologyNewsFragment : BaseNewsFragment() {

    private val technologyNewsViewModel: TechnologyNewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private val technologyNewsArticles: MutableList<NewsArticle> = mutableListOf()
    private val owner = { lifecycle }
    private val recyclerViewPaginator by lazy {
        object : RecyclerViewPaginator(recycler_view) {
            override fun loadMore(currentPage: Int) {
                technologyNewsViewModel.loadMoreTechnologyNews(currentPage)
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_base_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            technologyNewsViewModel.getTechnologyNews(page = 1, country = Country.US)
        }

        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(technologyNewsArticles, requireContext())
        newsAdapter.setNewsArticleClickListener {
            startActivity(NewsDetailActivity.getIntent(this.requireContext(), it))
        }
        recycler_view.adapter = newsAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.addOnScrollListener(recyclerViewPaginator as RecyclerViewPaginator)
    }

    private fun observeViewModel() {
        technologyNewsViewModel.technologyNewsLiveData.observe(owner) { viewState ->
            when(viewState) {
                is ViewState.Loading -> {
                    if (technologyNewsViewModel.pageNumber > 1) {
                        load_more_progress.visibility = View.VISIBLE
                    } else {
                        shimmer_view_container.displayShimmer()
                    }
                }
                is ViewState.Success -> {
                    load_more_progress.visibility = View.GONE
                    shimmer_view_container.clearShimmer()
                    val state = recycler_view.layoutManager?.onSaveInstanceState()
                    newsAdapter.updateItems(viewState.data)
                    recycler_view.layoutManager?.onRestoreInstanceState(state)
                }
                is ViewState.Error -> {
                    load_more_progress.visibility = View.GONE
                    shimmer_view_container.clearShimmer()

                    when(viewState) {
                        is ViewState.Error.ServerError -> {
                            Toast.makeText(context,
                                context?.getString(R.string.server_error_msg),
                                Toast.LENGTH_SHORT).show()
                        }

                        is ViewState.Error.NoDatabaseDataError -> {
                            Toast.makeText(context,
                                context?.getString(R.string.no_data_msg),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TechnologyNewsFragment()
    }
}
