package com.example.data.cache

import com.example.data.model.NewsResultEntity

interface CacheSportsNewsDataSource {
    suspend fun getSportsNews(): NewsResultEntity
    suspend fun insertSportsNews(newsResults: NewsResultEntity)
    suspend fun clearSportsNews()
}
