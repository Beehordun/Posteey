package com.example.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetGeneralNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeneralNewsViewModel @Inject constructor(
    private val getGeneralNewsUseCase: GetGeneralNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var totalResults: Int = 0
    private var _generalNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var generalNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _generalNewsLiveData

    fun getGeneralNews(
        category: NewsCategory = NewsCategory.GeneralNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _generalNewsLiveData.postValue(Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getGeneralNewsUseCase.getGeneralNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _generalNewsLiveData.postValue(Success(newsArticleList))
                }.onFailure {
                    _generalNewsLiveData.postValue(Error(""))
                }
            }
        }
    }

    fun loadMoreGeneralNews(country: Country, currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults && _generalNewsLiveData.value !is Loading<*>) {
            getGeneralNews(country = country, page = currentPage + 1)
        }
    }
}
