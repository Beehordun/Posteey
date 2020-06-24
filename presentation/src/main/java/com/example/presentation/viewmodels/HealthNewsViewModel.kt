package com.example.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetHealthNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HealthNewsViewModel @ViewModelInject constructor(
    private val getHealthNewsUseCase: GetHealthNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var pageNumber = 1
    var totalResults: Int = 0
    private var _healthNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var healthNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _healthNewsLiveData

    fun getHealthNews(
        category: NewsCategory = NewsCategory.HealthNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _healthNewsLiveData.postValue(ViewState.Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getHealthNewsUseCase.getHealthNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _healthNewsLiveData.postValue(ViewState.Success(newsArticleList))
                }.onFailure {
                    _healthNewsLiveData.postValue(ViewState.Error(""))
                }
            }
        }
    }

    fun loadMoreHealthNews(currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults &&
            _healthNewsLiveData.value !is ViewState.Loading &&
                pageNumber == currentPage) {
            getHealthNews(page = currentPage + 1)
            pageNumber += 1
        }
    }
}
