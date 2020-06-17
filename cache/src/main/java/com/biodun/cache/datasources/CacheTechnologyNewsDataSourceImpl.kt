package com.biodun.cache.datasources

import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheTechnologyNewsDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheTechnologyNewsDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsEntityMapper: CacheNewsEntityMapper
): CacheTechnologyNewsDataSource {

    override suspend fun getTechnologyNews(): NewsResultEntity {
        return cacheNewsEntityMapper.mapFromCache(
            newsResultDb.newsDao().getNews(DbConstants.TECHNOLOGY_NEWS)
        )
    }

    override suspend fun insertTechnologyNews(newsResults: NewsResultEntity) {
        newsResultDb.newsDao().insertAllNews(
            cacheNewsEntityMapper.mapToCache(newsResults, DbConstants.TECHNOLOGY_NEWS)
        )
    }

    override suspend fun clearTechnologyNews() {
        newsResultDb.newsDao().clearNews(DbConstants.TECHNOLOGY_NEWS)
    }
}
