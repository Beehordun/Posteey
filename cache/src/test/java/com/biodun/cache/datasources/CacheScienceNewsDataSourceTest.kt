package com.biodun.cache.datasources

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.factory.FakeCacheTestFactory
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheScienceNewsDataSource
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.data.model.NewsResultEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CacheScienceNewsDataSourceTest {
    private lateinit var newsDb: NewsResultDatabase
    private lateinit var cacheNewsEntityMapper: CacheNewsEntityMapper
    private lateinit var newsResultEntity: NewsResultEntity
    private lateinit var cacheScienceNewsDataSource: CacheScienceNewsDataSource

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDb = Room.inMemoryDatabaseBuilder(
            context, NewsResultDatabase::class.java).build()

        cacheNewsEntityMapper = CacheNewsEntityMapper()
        cacheScienceNewsDataSource = CacheScienceNewsDataSourceImpl(newsDb, cacheNewsEntityMapper)
        newsResultEntity = FakeCacheTestFactory.makeNewsResultEntity()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun getScienceNewsTest() = runBlocking {
        cacheScienceNewsDataSource.insertScienceNews(newsResultEntity)
        val returnedNewsEntity = cacheScienceNewsDataSource.getScienceNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun insertScienceNewsTest() = runBlocking {
        cacheScienceNewsDataSource.insertScienceNews(newsResultEntity)
        val returnedNewsEntity = cacheScienceNewsDataSource.getScienceNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun clearScienceNewsTest() = runBlocking {
        cacheScienceNewsDataSource.insertScienceNews(newsResultEntity)
        cacheScienceNewsDataSource.clearScienceNews()


        expectedException.expect(com.example.core.exceptions.NoDatabaseDataFoundException::class.java)
        expectedException.expectMessage(DbConstants.DATA_NOT_FOUND)

        cacheScienceNewsDataSource.getScienceNews()
        Unit
    }
}
