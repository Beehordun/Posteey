package com.example.data.repositories

import com.example.core.exceptions.NoConnectivityException
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.data.cache.CacheSportsNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.SportsNewsRepository
import java.net.UnknownHostException
import javax.inject.Inject

class SportsNewsRepositoryImpl @Inject constructor(
    private val sportsNewsDataSource: CacheSportsNewsDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val newsResultEntityMapper: NewsResultEntityMapper
) : SportsNewsRepository {

    override suspend fun getSportsNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResult {
        return runCatching {
            remoteDataSource.getNewsHeadlines(query, pageSize, page)
        }.fold(
            onSuccess = {
                if (page == 1) {
                    sportsNewsDataSource.clearSportsNews()
                }
                sportsNewsDataSource.insertSportsNews(it)
                newsResultEntityMapper.mapFromNewsResultEntity(
                    sportsNewsDataSource.getSportsNews()
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
            newsResultEntityMapper.mapFromNewsResultEntity(
                sportsNewsDataSource.getSportsNews()
            )
        } catch (exception: NoDatabaseDataFoundException) {
            throw NoDatabaseDataFoundException()
        }
    }
}
