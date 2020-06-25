package com.example.data.repositories

import com.example.core.exceptions.NoConnectivityException
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.data.cache.CacheEntertainmentNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.EntertainmentNewsRepository
import java.net.UnknownHostException
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
            newsResultEntityMapper.mapFromNewsResultEntity(
                entertainmentNewsDataSource.getEntertainmentNews()
            )
        } catch (exception: NoDatabaseDataFoundException) {
            throw NoDatabaseDataFoundException()
        }
    }
}
