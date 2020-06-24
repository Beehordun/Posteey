package com.example.data.cache

import com.example.data.model.NewsResultEntity

interface CacheEntertainmentNewsDataSource {
    suspend fun getEntertainmentNews(): NewsResultEntity
    suspend fun insertEntertainmentNews(newsResults: NewsResultEntity)
    suspend fun clearEntertainmentNews()
}
