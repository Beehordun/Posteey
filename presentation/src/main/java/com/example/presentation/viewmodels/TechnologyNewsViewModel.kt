package com.example.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetTechnologyNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TechnologyNewsViewModel @Inject constructor(
    private val getTechnologyNewsUseCase: GetTechnologyNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var totalResults: Int = 0
    private var _technologyNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var technologyNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _technologyNewsLiveData

    fun getTechnologyNews(
        category: NewsCategory = NewsCategory.TechnologyNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _technologyNewsLiveData.postValue(Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getTechnologyNewsUseCase.getTechnologyNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _technologyNewsLiveData.postValue(Success(newsArticleList))
                }.onFailure {
                    _technologyNewsLiveData.postValue(Error(""))
                }
            }
        }
    }

    fun loadMoreTechnologyNews(country: Country, currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults && _technologyNewsLiveData.value !is Loading<*>) {
            getTechnologyNews(country = country, page = currentPage + 1)
        }
    }
}