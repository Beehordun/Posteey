package com.example.presentation.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.domain.usecases.GetBusinessNewsUseCase
import com.example.presentation.mappers.NewsArticleMapper
import com.example.presentation.models.NewsArticle
import com.example.presentation.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val DEFAULT_PAGE_SIZE = 10
class BusinessNewsViewModel @ViewModelInject constructor(
    private val getBusinessNewsUseCase: GetBusinessNewsUseCase,
    private val newsArticleMapper: NewsArticleMapper,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var totalResults: Int = 0
    private var _businessNewsLiveData = MutableLiveData<ViewState<List<NewsArticle>>>()
    var businessNewsLiveData: LiveData<ViewState<List<NewsArticle>>> = _businessNewsLiveData

    fun getBusinessNews(
        category: NewsCategory = NewsCategory.BusinessNews,
        country: Country = Country.NG,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int) {

        val query = QueryBuilder.buildQuery(category, country)

        _businessNewsLiveData.postValue(Loading())

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    getBusinessNewsUseCase.getBusinessNews(query, pageSize, page)
                }.onSuccess {
                    totalResults = it.totalResults
                    val newsArticleList = newsArticleMapper.mapFromDomainToPresentation(it)
                    _businessNewsLiveData.postValue(Success(newsArticleList))
                }.onFailure {
                     it.printStackTrace()
                    _businessNewsLiveData.postValue(Error(""))
                }
            }
        }
    }

    fun loadMoreBusinessNews(country: Country, currentPage: Int) {
        if (currentPage * DEFAULT_PAGE_SIZE < totalResults && _businessNewsLiveData.value !is Loading<*>) {
            getBusinessNews(country = country, page = currentPage + 1)
        }
    }
}
