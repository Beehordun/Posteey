package com.example.data.cache

import com.example.data.model.NewsResultEntity

interface CacheScienceNewsDataSource {
    suspend fun getScienceNews(): NewsResultEntity
    suspend fun insertScienceNews(newsResults: NewsResultEntity)
    suspend fun clearScienceNews()
}
