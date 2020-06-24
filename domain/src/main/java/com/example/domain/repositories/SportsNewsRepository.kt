package com.example.domain.repositories

import com.example.domain.model.NewsResult

interface SportsNewsRepository {
    suspend fun getSportsNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult
}