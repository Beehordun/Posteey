package com.biodun.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.biodun.cache.db.DbConstants
import java.util.UUID

@Entity(tableName = DbConstants.NEWS_TABLE)
data class CacheNewsEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = DbConstants.COLUMN_NEWS_TYPE)
    val newsType: String,
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
