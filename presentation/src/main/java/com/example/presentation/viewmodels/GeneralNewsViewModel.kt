package com.example.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetGeneralNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.Country
import com.example.presentation.utils.NewsCategory
import com.example.presentation.utils.QueryBuilder
import com.example.presentation.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralNewsViewModel @ViewModelInject constructor(
    private val getGeneralNewsUseCase: GetGeneralNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var totalResults: Int = 0
    private var _generalNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var generalNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _generalNewsLiveData
    var pageNumber = 1

    fun getGeneralNews(
        category: NewsCategory = NewsCategory.GeneralNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _generalNewsLiveData.postValue(ViewState.Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getGeneralNewsUseCase.getGeneralNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _generalNewsLiveData.postValue(ViewState.Success(newsArticleList))
                }.onFailure {
                    _generalNewsLiveData.postValue(ViewState.Error(""))
                }
            }
        }
    }

    fun loadMoreGeneralNews(currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults && _generalNewsLiveData.value !is ViewState.Loading && pageNumber == currentPage) {
            getGeneralNews(page = currentPage + 1)
            pageNumber += 1
        }
    }
}
