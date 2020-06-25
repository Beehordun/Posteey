package com.example.data.repositories

import com.example.core.exceptions.NoConnectivityException
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.data.cache.CacheTechnologyNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.TechnologyNewsRepository
import java.net.UnknownHostException
import javax.inject.Inject

class TechnologyNewsRepositoryImpl @Inject constructor(
    private val technologyNewsDataSource: CacheTechnologyNewsDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val newsResultEntityMapper: NewsResultEntityMapper
) : TechnologyNewsRepository {

    override suspend fun getTechnologyNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResult {
        return runCatching {
            remoteDataSource.getNewsHeadlines(query, pageSize, page)
        }.fold(
            onSuccess = {
                if (page == 1) {
                    technologyNewsDataSource.clearTechnologyNews()
                }
                technologyNewsDataSource.insertTechnologyNews(it)
                newsResultEntityMapper.mapFromNewsResultEntity(
                    technologyNewsDataSource.getTechnologyNews()
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
                technologyNewsDataSource.getTechnologyNews()
            )
        } catch (exception: NoDatabaseDataFoundException) {
            throw NoDatabaseDataFoundException()
        }
    }
}
