package com.biodun.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.biodun.cache.db.DbConstants.DELETE_NEWS_RESULTS_QUERY
import com.biodun.cache.db.DbConstants.GET_NEWS_RESULTS_QUERY
import com.biodun.cache.model.CacheNewsResultEntity

@Dao
interface NewsResultDao {

    @Insert
    suspend fun insertAllNewsResult(newsResult: List<CacheNewsResultEntity>)

    @Query(GET_NEWS_RESULTS_QUERY)
    suspend fun getNewsResult(): List<CacheNewsResultEntity>

    @Query(DELETE_NEWS_RESULTS_QUERY)
    suspend fun clearNewsResult()
}
