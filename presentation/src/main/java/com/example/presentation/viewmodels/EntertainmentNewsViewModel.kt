package com.example.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetEntertainmentNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.Country
import com.example.presentation.utils.NewsCategory
import com.example.presentation.utils.QueryBuilder
import com.example.presentation.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EntertainmentNewsViewModel @ViewModelInject constructor(
    private val getEntertainmentNewsUseCase: GetEntertainmentNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var pageNumber = 1
    var totalResults: Int = 0
    private var _entertainmentNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var entertainmentNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _entertainmentNewsLiveData

    fun getEntertainmentNews(
        category: NewsCategory = NewsCategory.EntertainmentNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _entertainmentNewsLiveData.postValue(ViewState.Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getEntertainmentNewsUseCase.getEntertainmentNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _entertainmentNewsLiveData.postValue(ViewState.Success(newsArticleList))
                }.onFailure {
                    _entertainmentNewsLiveData.postValue(ViewState.Error(""))
                }
            }
        }
    }

    fun loadMoreEntertainmentNews(currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults &&
            _entertainmentNewsLiveData.value !is ViewState.Loading &&
            pageNumber == currentPage) {
            getEntertainmentNews(page = currentPage + 1)
            pageNumber += 1
        }
    }
}
