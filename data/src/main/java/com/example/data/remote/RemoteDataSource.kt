package com.example.data.remote

import com.example.data.model.NewsResultEntity

interface RemoteDataSource {

    suspend fun getNewsHeadlines(query: Map<String, String>,
                                 pageSize: Int,
                                 page: Int): NewsResultEntity

    suspend fun searchNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResultEntity
}
