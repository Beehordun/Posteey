package com.example.domain.repositories

import com.example.domain.model.NewsResult

interface TechnologyNewsRepository {
    suspend fun getTechnologyNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult
}
