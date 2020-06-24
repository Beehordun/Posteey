package com.example.data.repositories

import com.example.data.cache.CacheScienceNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.ScienceNewsRepository
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
           onFailure = {
               newsResultEntityMapper.mapFromNewsResultEntity(
                   scienceNewsDataSource.getScienceNews()
               )
           })
    }
}
