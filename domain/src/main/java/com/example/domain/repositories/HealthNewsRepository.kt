package com.example.domain.repositories

import com.example.domain.model.NewsResult

interface HealthNewsRepository {
    suspend fun getHealthNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult
}
