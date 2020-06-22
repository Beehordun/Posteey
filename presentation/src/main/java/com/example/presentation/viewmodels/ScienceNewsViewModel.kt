package com.example.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetScienceNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScienceNewsViewModel @Inject constructor(
    private val getScienceNewsUseCase: GetScienceNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var totalResults: Int = 0
    private var _scienceNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var scienceNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _scienceNewsLiveData

    fun getScienceNews(
        category: NewsCategory = NewsCategory.ScienceNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _scienceNewsLiveData.postValue(Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getScienceNewsUseCase.getScienceNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _scienceNewsLiveData.postValue(Success(newsArticleList))
                }.onFailure {
                    _scienceNewsLiveData.postValue(Error(""))
                }
            }
        }
    }

    fun loadMoreScienceNews(country: Country, currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults && _scienceNewsLiveData.value !is Loading<*>) {
            getScienceNews(country = country, page = currentPage + 1)
        }
    }
}
