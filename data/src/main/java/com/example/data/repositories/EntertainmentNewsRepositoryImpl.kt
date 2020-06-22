package com.example.data.repositories

import com.example.data.cache.CacheEntertainmentNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.EntertainmentNewsRepository
import javax.inject.Inject

class EntertainmentNewsRepositoryImpl @Inject constructor(
    private val entertainmentNewsDataSource: CacheEntertainmentNewsDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val newsResultEntityMapper: NewsResultEntityMapper
) : EntertainmentNewsRepository {

    override suspend fun getEntertainmentNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResult {
        return runCatching {
            remoteDataSource.getNewsHeadlines(query, pageSize, page)
        }.fold(
            onSuccess = {
                if (page == 1) {
                    entertainmentNewsDataSource.clearEntertainmentNews()
                }
                entertainmentNewsDataSource.insertEntertainmentNews(it)
                newsResultEntityMapper.mapFromNewsResultEntity(entertainmentNewsDataSource.getEntertainmentNews())

            },
            onFailure = {
                newsResultEntityMapper.mapFromNewsResultEntity(
                    entertainmentNewsDataSource.getEntertainmentNews()
                )
            })
    }
}
