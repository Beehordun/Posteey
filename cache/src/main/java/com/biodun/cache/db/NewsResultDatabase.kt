package com.biodun.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biodun.cache.dao.NewsResultDao
import com.biodun.cache.model.CacheNewsResultEntity
import javax.inject.Inject

@Database(entities = [CacheNewsResultEntity::class], version = 1)
abstract class NewsResultDatabase @Inject constructor(): RoomDatabase() {
    abstract fun newsResultDao(): NewsResultDao
}