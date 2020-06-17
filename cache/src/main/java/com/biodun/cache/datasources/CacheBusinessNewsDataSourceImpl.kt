package com.biodun.cache.datasources

import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheBusinessNewsDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheBusinessNewsDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsEntityMapper: CacheNewsEntityMapper
): CacheBusinessNewsDataSource {

    override suspend fun getBusinessNews(): NewsResultEntity {
        return cacheNewsEntityMapper.mapFromCache(
            newsResultDb.newsDao().getNews(DbConstants.BUSINESS_NEWS)
        )
    }

    override suspend fun insertBusinessNews(newsResults: NewsResultEntity) {
        newsResultDb.newsDao().insertAllNews(
            cacheNewsEntityMapper.mapToCache(newsResults, DbConstants.BUSINESS_NEWS)
        )
    }

    override suspend fun clearBusinessNews() {
        newsResultDb.newsDao().clearNews(DbConstants.BUSINESS_NEWS)
    }
}
