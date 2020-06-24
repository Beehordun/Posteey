package com.example.domain.repositories

import com.example.domain.model.NewsResult

interface EntertainmentNewsRepository {
    suspend fun getEntertainmentNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int): NewsResult
}
