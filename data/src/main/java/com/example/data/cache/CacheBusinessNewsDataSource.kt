package com.example.data.cache

import com.example.data.model.NewsResultEntity

interface CacheBusinessNewsDataSource {
    suspend fun getBusinessNews(): NewsResultEntity
    suspend fun insertBusinessNews(newsResults: NewsResultEntity)
    suspend fun clearBusinessNews()
}
