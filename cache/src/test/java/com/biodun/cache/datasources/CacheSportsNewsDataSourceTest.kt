package com.biodun.cache.datasources

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.factory.FakeCacheTestFactory
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheSportsNewsDataSource
import com.example.data.exceptions.NoDatabaseDataFoundException
import com.example.data.model.NewsResultEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CacheSportsNewsDataSourceTest {
    private lateinit var newsDb: NewsResultDatabase
    private lateinit var cacheNewsEntityMapper: CacheNewsEntityMapper
    private lateinit var newsResultEntity: NewsResultEntity
    private lateinit var cacheSportsNewsDataSource: CacheSportsNewsDataSource

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDb = Room.inMemoryDatabaseBuilder(
            context, NewsResultDatabase::class.java).build()

        cacheNewsEntityMapper = CacheNewsEntityMapper()
        cacheSportsNewsDataSource = CacheSportsNewsDataSourceImpl(newsDb, cacheNewsEntityMapper)
        newsResultEntity = FakeCacheTestFactory.makeNewsResultEntity()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun getSportsNewsTest() = runBlocking {
        cacheSportsNewsDataSource.insertSportsNews(newsResultEntity)
        val returnedNewsEntity = cacheSportsNewsDataSource.getSportsNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun insertSportsNewsTest() = runBlocking {
        cacheSportsNewsDataSource.insertSportsNews(newsResultEntity)
        val returnedNewsEntity = cacheSportsNewsDataSource.getSportsNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun clearSportsNewsTest() = runBlocking {
        cacheSportsNewsDataSource.insertSportsNews(newsResultEntity)
        cacheSportsNewsDataSource.clearSportsNews()


        expectedException.expect(NoDatabaseDataFoundException::class.java)
        expectedException.expectMessage(DbConstants.DATA_NOT_FOUND)

        cacheSportsNewsDataSource.getSportsNews()
        Unit
    }

}