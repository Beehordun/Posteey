package com.biodun.cache.datasources

import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheGeneralNewsDataSource
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheGeneralNewsDataSourceImpl @Inject constructor(
    private val newsResultDb: NewsResultDatabase,
    private val cacheNewsEntityMapper: CacheNewsEntityMapper
): CacheGeneralNewsDataSource {

    override suspend fun getGeneralNews(): NewsResultEntity {
        return cacheNewsEntityMapper.mapFromCache(
            newsResultDb.newsDao().getNews(DbConstants.GENERAL_NEWS)
        )
    }

    override suspend fun insertGeneralNews(newsResults: NewsResultEntity) {
        newsResultDb.newsDao().insertAllNews(
            cacheNewsEntityMapper.mapToCache(newsResults, DbConstants.GENERAL_NEWS)
        )
    }

    override suspend fun clearGeneralNews() {
        newsResultDb.newsDao().clearNews(DbConstants.GENERAL_NEWS)
    }
}
