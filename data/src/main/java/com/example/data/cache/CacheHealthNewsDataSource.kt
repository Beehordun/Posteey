package com.example.data.cache

import com.example.data.model.NewsResultEntity

interface CacheHealthNewsDataSource {
    suspend fun getHealthNews(): NewsResultEntity
    suspend fun insertHealthNews(newsResults: NewsResultEntity)
    suspend fun clearHealthNews()
}
