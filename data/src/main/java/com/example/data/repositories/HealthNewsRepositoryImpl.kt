package com.example.data.repositories

import com.example.core.exceptions.NoConnectivityException
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.data.cache.CacheHealthNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.HealthNewsRepository
import java.net.UnknownHostException
import javax.inject.Inject

class HealthNewsRepositoryImpl @Inject constructor(
    private val healthNewsDataSource: CacheHealthNewsDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val newsResultEntityMapper: NewsResultEntityMapper
) : HealthNewsRepository {

    override suspend fun getHealthNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResult {
        return runCatching {
            remoteDataSource.getNewsHeadlines(query, pageSize, page)
        }.fold(
            onSuccess = {
                if (page == 1) {
                    healthNewsDataSource.clearHealthNews()
                }
                healthNewsDataSource.insertHealthNews(it)
                newsResultEntityMapper.mapFromNewsResultEntity(
                    healthNewsDataSource.getHealthNews()
                )
            },
            onFailure = { exception ->
                when (exception) {
                    is NoConnectivityException -> {
                        fetchDataFromDatabase()
                    }
                    is UnknownHostException -> {
                        fetchDataFromDatabase()
                    }
                    else -> {
                        throw ServerErrorException()
                    }
                }
            }
        )
    }

    private suspend fun fetchDataFromDatabase(): NewsResult {
        return try {
            newsResultEntityMapper.mapFromNewsResultEntity(healthNewsDataSource.getHealthNews())
        } catch (exception: NoDatabaseDataFoundException) {
            throw NoDatabaseDataFoundException()
        }
    }
}
