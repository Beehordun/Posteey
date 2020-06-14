package com.biodun.cache

import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsResultEntityMapper
import com.example.data.cache.CacheDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsResultEntityMapper: CacheNewsResultEntityMapper
): CacheDataSource {

    override suspend fun getNewsResults(): NewsResultEntity {
        return cacheNewsResultEntityMapper.mapFromCache(
            newsResultDb.newsResultDao().getNewsResult()
        )
    }

    override suspend fun insertNewsResults(newsResults: NewsResultEntity) {
        newsResultDb.newsResultDao().insertAllNewsResult(
            cacheNewsResultEntityMapper.mapToCache(newsResults)
        )
    }

    override suspend fun clearNewsResults() {
        newsResultDb.newsResultDao().clearNewsResult()
    }
}
