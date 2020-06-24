package com.example.domain.repositories

import com.example.domain.model.NewsResult

interface BusinessNewsRepository {
    suspend fun getBusinessNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult
}
