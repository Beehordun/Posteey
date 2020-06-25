package com.example.data.repositories

import com.example.core.exceptions.NoConnectivityException
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.data.cache.CacheGeneralNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.GeneralNewsRepository
import java.net.UnknownHostException
import javax.inject.Inject

class GeneralNewsRepositoryImpl @Inject constructor(
    private val generalNewsDataSource: CacheGeneralNewsDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val newsResultEntityMapper: NewsResultEntityMapper
) : GeneralNewsRepository {

    override suspend fun getGeneralNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResult {
        return runCatching {
            remoteDataSource.getNewsHeadlines(query, pageSize, page)
        }.fold(
            onSuccess = {
                println("OnSucesss")
                if (page == 1) {
                    generalNewsDataSource.clearGeneralNews()
                }
                generalNewsDataSource.insertGeneralNews(it)
                newsResultEntityMapper.mapFromNewsResultEntity(
                    generalNewsDataSource.getGeneralNews()
                )
            },
            onFailure = {
                when (it) {
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
                generalNewsDataSource.getGeneralNews()
            )
        } catch (exception: NoDatabaseDataFoundException) {
            throw NoDatabaseDataFoundException()
        }
    }
}
