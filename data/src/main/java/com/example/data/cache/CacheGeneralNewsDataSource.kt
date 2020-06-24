package com.example.data.cache

import com.example.data.model.NewsResultEntity

interface CacheGeneralNewsDataSource {
    suspend fun getGeneralNews(): NewsResultEntity
    suspend fun insertGeneralNews(newsResults: NewsResultEntity)
    suspend fun clearGeneralNews()
}
