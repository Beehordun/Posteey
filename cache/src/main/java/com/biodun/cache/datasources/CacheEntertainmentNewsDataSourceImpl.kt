package com.biodun.cache.datasources

import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheEntertainmentNewsDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheEntertainmentNewsDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsEntityMapper: CacheNewsEntityMapper
): CacheEntertainmentNewsDataSource {

    override suspend fun getEntertainmentNews(): NewsResultEntity {
        return cacheNewsEntityMapper.mapFromCache(
            newsResultDb.newsDao().getNews(DbConstants.ENTERTAINMENT_NEWS)
        )
    }

    override suspend fun insertEntertainmentNews(newsResults: NewsResultEntity) {
        newsResultDb.newsDao().insertAllNews(
            cacheNewsEntityMapper.mapToCache(newsResults, DbConstants.ENTERTAINMENT_NEWS)
        )
    }

    override suspend fun clearEntertainmentNews() {
        newsResultDb.newsDao().clearNews(DbConstants.ENTERTAINMENT_NEWS)
    }
}
