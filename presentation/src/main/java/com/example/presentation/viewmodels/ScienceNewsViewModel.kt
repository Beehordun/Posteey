package com.example.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.domain.usecases.GetScienceNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScienceNewsViewModel @ViewModelInject constructor(
    private val getScienceNewsUseCase: GetScienceNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper
) : ViewModel() {

    var pageNumber = 1
    var totalResults: Int = 0
    private var _scienceNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var scienceNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _scienceNewsLiveData

    fun getScienceNews(
        category: NewsCategory = NewsCategory.ScienceNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _scienceNewsLiveData.postValue(ViewState.Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getScienceNewsUseCase.getScienceNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _scienceNewsLiveData.postValue(ViewState.Success(newsArticleList))
                }.onFailure { exception ->
                    when(exception) {
                        is NoDatabaseDataFoundException -> {
                            _scienceNewsLiveData.postValue(ViewState.Error.NoDatabaseDataError())
                        }

                        is ServerErrorException -> {
                            _scienceNewsLiveData.postValue(ViewState.Error.ServerError())
                        }
                    }
                }
            }
        }
    }

    fun loadMoreScienceNews(currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults &&
            _scienceNewsLiveData.value !is ViewState.Loading &&
                pageNumber == currentPage) {
            getScienceNews(page = currentPage + 1)
            pageNumber += 1
        }
    }
}
