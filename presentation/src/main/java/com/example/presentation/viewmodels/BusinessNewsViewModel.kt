package com.example.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.GetBusinessNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.Country
import com.example.presentation.utils.NewsCategory
import com.example.presentation.utils.QueryBuilder
import com.example.presentation.utils.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val DEFAULT_PAGE_SIZE = 10
class BusinessNewsViewModel @ViewModelInject constructor(
    private val getBusinessNewsUseCase: GetBusinessNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var totalResults: Int = 0
    var pageNumber = 1
    private var _businessNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var businessNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _businessNewsLiveData

    fun getBusinessNews(
        category: NewsCategory = NewsCategory.BusinessNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _businessNewsLiveData.postValue(ViewState.Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getBusinessNewsUseCase.getBusinessNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _businessNewsLiveData.postValue(ViewState.Success(newsArticleList))
                }.onFailure {
                     it.printStackTrace()
                    _businessNewsLiveData.postValue(ViewState.Error(""))
                }
            }
        }
    }

    fun loadMoreBusinessNews(currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults &&
            _businessNewsLiveData.value !is ViewState.Loading &&
            pageNumber == currentPage) {
            getBusinessNews(page = currentPage + 1)
            pageNumber += 1
        }
    }
}
