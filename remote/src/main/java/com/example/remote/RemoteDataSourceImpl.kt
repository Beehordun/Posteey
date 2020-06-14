package com.example.remote

import com.example.data.model.NewsResultEntity
import com.example.data.remote.RemoteDataSource
import com.example.remote.mapper.RemoteNewsResultMapper
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val remoteNewsResultMapper: RemoteNewsResultMapper,
    private val newsApi: NewsApi
) : RemoteDataSource {

    override suspend fun getNewsHeadlines(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResultEntity {
        return remoteNewsResultMapper.mapRemoteNewsResultToNewsResultEntity(
            newsApi.getNewsHeadlines(query = query, pageSize = pageSize, page = page)
        )
    }

    override suspend fun searchNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResultEntity {
       return remoteNewsResultMapper.mapRemoteNewsResultToNewsResultEntity(
           newsApi.searchNews(searchQuery = query, pageSize = pageSize, page = page)
       )
    }
}
