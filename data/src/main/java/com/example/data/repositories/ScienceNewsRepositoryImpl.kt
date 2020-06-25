package com.example.data.repositories

import com.example.core.exceptions.NoConnectivityException
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.core.exceptions.ServerErrorException
import com.example.data.cache.CacheScienceNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.ScienceNewsRepository
import java.net.UnknownHostException
import javax.inject.Inject

class ScienceNewsRepositoryImpl @Inject constructor(
    private val scienceNewsDataSource: CacheScienceNewsDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val newsResultEntityMapper: NewsResultEntityMapper
) : ScienceNewsRepository {

    override suspend fun getScienceNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResult {
       return  runCatching {
           remoteDataSource.getNewsHeadlines(query, pageSize, page)
       }.fold(
           onSuccess = {
               if (page == 1) {
                   scienceNewsDataSource.clearScienceNews()
               }
               scienceNewsDataSource.insertScienceNews(it)
               newsResultEntityMapper.mapFromNewsResultEntity(
                   scienceNewsDataSource.getScienceNews()
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
                scienceNewsDataSource.getScienceNews()
            )
        } catch (exception: NoDatabaseDataFoundException) {
            throw NoDatabaseDataFoundException()
        }
    }
}
