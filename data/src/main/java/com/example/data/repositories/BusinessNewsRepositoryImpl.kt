package com.example.data.repositories

import com.example.core.exceptions.NoConnectivityException
import com.example.data.cache.CacheBusinessNewsDataSource
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.BusinessNewsRepository
import java.net.UnknownHostException
import javax.inject.Inject

class BusinessNewsRepositoryImpl @Inject constructor(
    private val businessNewsDataSource: CacheBusinessNewsDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val newsResultEntityMapper: NewsResultEntityMapper
) : BusinessNewsRepository {

    override suspend fun getBusinessNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResult {
        return runCatching {
            remoteDataSource.getNewsHeadlines(query, pageSize, page)
        }.fold(
            onSuccess = {
                if (page == 1) {
                    businessNewsDataSource.clearBusinessNews()
                }
                businessNewsDataSource.insertBusinessNews(it)
                newsResultEntityMapper.mapFromNewsResultEntity(
                    businessNewsDataSource.getBusinessNews()
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
                businessNewsDataSource.getBusinessNews()
            )
        } catch (exception: NoDatabaseDataFoundException) {
            throw NoDatabaseDataFoundException()
        }
    }
}
