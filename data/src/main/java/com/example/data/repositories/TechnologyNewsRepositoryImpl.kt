package com.example.data.repositories

import com.example.data.cache.CacheTechnologyNewsDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import com.example.domain.model.NewsResult
import com.example.domain.repositories.TechnologyNewsRepository
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
            onFailure = {
                newsResultEntityMapper.mapFromNewsResultEntity(
                    technologyNewsDataSource.getTechnologyNews()
                )
            })
    }
}
