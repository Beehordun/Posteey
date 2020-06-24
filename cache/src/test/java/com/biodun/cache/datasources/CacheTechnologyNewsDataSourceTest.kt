package com.biodun.cache.datasources

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.factory.FakeCacheTestFactory
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheTechnologyNewsDataSource
import com.example.data.exceptions.NoDatabaseDataFoundException
import com.example.data.model.NewsResultEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CacheTechnologyNewsDataSourceTest {

    private lateinit var newsDb: NewsResultDatabase
    private lateinit var cacheNewsEntityMapper: CacheNewsEntityMapper
    private lateinit var newsResultEntity: NewsResultEntity
    private lateinit var cacheTechnologyNewsDataSource: CacheTechnologyNewsDataSource

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDb = Room.inMemoryDatabaseBuilder(
            context, NewsResultDatabase::class.java
        ).build()

        cacheNewsEntityMapper = CacheNewsEntityMapper()
        cacheTechnologyNewsDataSource =
            CacheTechnologyNewsDataSourceImpl(newsDb, cacheNewsEntityMapper)
        newsResultEntity = FakeCacheTestFactory.makeNewsResultEntity()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun getTechnologyNewsTest() = runBlocking {
        cacheTechnologyNewsDataSource.insertTechnologyNews(newsResultEntity)
        val returnedNewsEntity = cacheTechnologyNewsDataSource.getTechnologyNews()

        assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun insertTechnologyNewsTest() = runBlocking {
        cacheTechnologyNewsDataSource.insertTechnologyNews(newsResultEntity)
        val returnedNewsEntity = cacheTechnologyNewsDataSource.getTechnologyNews()

        assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun clearTechnologyNewsTest() = runBlocking {
        cacheTechnologyNewsDataSource.insertTechnologyNews(newsResultEntity)
        cacheTechnologyNewsDataSource.clearTechnologyNews()


        expectedException.expect(NoDatabaseDataFoundException::class.java)
        expectedException.expectMessage(DbConstants.DATA_NOT_FOUND)

        cacheTechnologyNewsDataSource.getTechnologyNews()
        Unit
    }
}
