package com.example.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetTechnologyNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.Country
import com.example.presentation.utils.NewsCategory
import com.example.presentation.utils.QueryBuilder
import com.example.presentation.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TechnologyNewsViewModel @ViewModelInject constructor(
    private val getTechnologyNewsUseCase: GetTechnologyNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var pageNumber = 1
    var totalResults: Int = 0
    private var _technologyNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var technologyNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _technologyNewsLiveData

    fun getTechnologyNews(
        category: NewsCategory = NewsCategory.TechnologyNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _technologyNewsLiveData.postValue(ViewState.Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getTechnologyNewsUseCase.getTechnologyNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _technologyNewsLiveData.postValue(ViewState.Success(newsArticleList))
                }.onFailure {
                    _technologyNewsLiveData.postValue(ViewState.Error(""))
                }
            }
        }
    }

    fun loadMoreTechnologyNews(currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults &&
            _technologyNewsLiveData.value !is ViewState.Loading &&
            pageNumber == currentPage) {
            getTechnologyNews(page = currentPage + 1)
            pageNumber += 1
        }
    }
}
