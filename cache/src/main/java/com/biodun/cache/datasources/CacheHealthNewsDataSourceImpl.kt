package com.biodun.cache.datasources

import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheHealthNewsDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheHealthNewsDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsEntityMapper: CacheNewsEntityMapper
): CacheHealthNewsDataSource {

    override suspend fun getHealthNews(): NewsResultEntity {
        return cacheNewsEntityMapper.mapFromCache(
            newsResultDb.newsDao().getNews(DbConstants.HEALTH_NEWS)
        )
    }

    override suspend fun insertHealthNews(newsResults: NewsResultEntity) {
        newsResultDb.newsDao().insertAllNews(
            cacheNewsEntityMapper.mapToCache(newsResults, DbConstants.HEALTH_NEWS)
        )
    }

    override suspend fun clearHealthNews() {
        newsResultDb.newsDao().clearNews(DbConstants.HEALTH_NEWS)
    }
}
