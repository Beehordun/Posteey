package com.biodun.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.biodun.cache.dao.NewsDao
import com.biodun.cache.db.DbConstants.DB_VERSION
import com.biodun.cache.model.CacheNewsEntity

@TypeConverters(UUIDTypeConverter::class)
@Database(
    entities = [CacheNewsEntity::class], version = DB_VERSION, exportSchema = false
)
abstract class NewsResultDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
