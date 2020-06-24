package com.example.data.cache

import com.example.data.model.NewsResultEntity

interface CacheTechnologyNewsDataSource {
    suspend fun getTechnologyNews(): NewsResultEntity
    suspend fun insertTechnologyNews(newsResults: NewsResultEntity)
    suspend fun clearTechnologyNews()
}
