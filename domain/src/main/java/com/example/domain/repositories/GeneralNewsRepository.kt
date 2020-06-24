package com.example.domain.repositories

import com.example.domain.model.NewsResult

interface GeneralNewsRepository {
    suspend fun getGeneralNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult
}