package com.biodun.cache.datasources

import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheSportsNewsDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheSportsNewsDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsEntityMapper: CacheNewsEntityMapper
): CacheSportsNewsDataSource {

    override suspend fun getSportsNews(): NewsResultEntity {
        return cacheNewsEntityMapper.mapFromCache(
            newsResultDb.newsDao().getNews(DbConstants.SPORTS_NEWS)
        )
    }

    override suspend fun insertSportsNews(newsResults: NewsResultEntity) {
        newsResultDb.newsDao().insertAllNews(
            cacheNewsEntityMapper.mapToCache(newsResults, DbConstants.SPORTS_NEWS)
        )
    }

    override suspend fun clearSportsNews() {
        newsResultDb.newsDao().clearNews(DbConstants.SPORTS_NEWS)
    }
}
