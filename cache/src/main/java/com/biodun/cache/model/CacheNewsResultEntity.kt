package com.biodun.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.biodun.cache.db.DbConstants.NEWS_RESULT_TABLE_NAME
import java.util.*

@Entity(tableName = NEWS_RESULT_TABLE_NAME)
data class CacheNewsResultEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val totalResult: Int
)
