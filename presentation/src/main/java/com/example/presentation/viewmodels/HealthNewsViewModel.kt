package com.example.presentation.viewmodels

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

class HealthNewsViewModel @Inject constructor(
    private val getHealthNewsUseCase: GetHealthNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var totalResults: Int = 0
    private var _healthNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var healthNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _healthNewsLiveData

    fun getHealthNews(
        category: NewsCategory = NewsCategory.HealthNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _healthNewsLiveData.postValue(Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getHealthNewsUseCase.getHealthNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _healthNewsLiveData.postValue(Success(newsArticleList))
                }.onFailure {
                    _healthNewsLiveData.postValue(Error(""))
                }
            }
        }
    }

    fun loadMoreHealthNews(country: Country, currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults && _healthNewsLiveData.value !is Loading<*>) {
            getHealthNews(country = country, page = currentPage + 1)
        }
    }
}
