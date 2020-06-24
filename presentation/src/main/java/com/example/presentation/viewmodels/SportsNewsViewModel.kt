package com.example.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetSportsNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.Country
import com.example.presentation.utils.NewsCategory
import com.example.presentation.utils.QueryBuilder
import com.example.presentation.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SportsNewsViewModel @ViewModelInject constructor(
    private val getSportsNewsUseCase: GetSportsNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var pageNumber = 1
    var totalResults: Int = 0
    private var _sportsNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var sportsNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _sportsNewsLiveData

    fun getSportsNews(
        category: NewsCategory = NewsCategory.SportsNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _sportsNewsLiveData.postValue(ViewState.Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getSportsNewsUseCase.getSportsNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _sportsNewsLiveData.postValue(ViewState.Success(newsArticleList))
                }.onFailure {
                    _sportsNewsLiveData.postValue(ViewState.Error(""))
                }
            }
        }
    }

    fun loadMoreSportsNews(currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults &&
            _sportsNewsLiveData.value !is ViewState.Loading &&
                pageNumber == currentPage) {
            getSportsNews(page = currentPage + 1)
            pageNumber += 1
        }
    }
}
