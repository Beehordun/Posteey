package com.biodun.cache.datasources

import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheScienceNewsDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheScienceNewsDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsEntityMapper: CacheNewsEntityMapper
): CacheScienceNewsDataSource {

    override suspend fun getScienceNews(): NewsResultEntity {
        return cacheNewsEntityMapper.mapFromCache(
            newsResultDb.newsDao().getNews(DbConstants.SCIENCE_NEWS)
        )
    }

    override suspend fun insertScienceNews(newsResults: NewsResultEntity) {
        newsResultDb.newsDao().insertAllNews(
            cacheNewsEntityMapper.mapToCache(newsResults, DbConstants.SCIENCE_NEWS)
        )
    }

    override suspend fun clearScienceNews() {
        newsResultDb.newsDao().clearNews(DbConstants.SCIENCE_NEWS)
    }
}
