package com.example.domain.repositories

import com.example.domain.model.NewsResult

interface ScienceNewsRepository {
    suspend fun getScienceNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult
}
