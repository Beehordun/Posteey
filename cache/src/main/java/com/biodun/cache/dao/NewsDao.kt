package com.biodun.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.biodun.cache.db.DbConstants
import com.biodun.cache.model.CacheNewsEntity

@Dao
interface NewsDao {

    @Insert
    suspend fun insertAllNews(news: List<CacheNewsEntity>)

    @Query(DbConstants.GET_NEWS_QUERY)
    suspend fun getNews(newsType: String): List<CacheNewsEntity>

    @Query(DbConstants.DELETE_NEWS_QUERY)
    suspend fun clearNews(newsType: String)
}