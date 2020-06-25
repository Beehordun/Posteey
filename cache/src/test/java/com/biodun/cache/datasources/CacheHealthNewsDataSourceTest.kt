package com.biodun.cache.datasources

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.factory.FakeCacheTestFactory
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheHealthNewsDataSource
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.data.model.NewsResultEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CacheHealthNewsDataSourceTest {

    private lateinit var newsDb: NewsResultDatabase
    private lateinit var cacheNewsEntityMapper: CacheNewsEntityMapper
    private lateinit var newsResultEntity: NewsResultEntity
    private lateinit var cacheHealthNewsDataSource: CacheHealthNewsDataSource

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDb = Room.inMemoryDatabaseBuilder(
            context, NewsResultDatabase::class.java).build()

        cacheNewsEntityMapper = CacheNewsEntityMapper()
        cacheHealthNewsDataSource = CacheHealthNewsDataSourceImpl(newsDb, cacheNewsEntityMapper)
        newsResultEntity = FakeCacheTestFactory.makeNewsResultEntity()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun getHealthNewsTest() = runBlocking {
        cacheHealthNewsDataSource.insertHealthNews(newsResultEntity)
        val returnedNewsEntity = cacheHealthNewsDataSource.getHealthNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun insertHealthNewsTest() = runBlocking {
        cacheHealthNewsDataSource.insertHealthNews(newsResultEntity)
        val returnedNewsEntity = cacheHealthNewsDataSource.getHealthNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun clearHealthNewsTest() = runBlocking {
        cacheHealthNewsDataSource.insertHealthNews(newsResultEntity)
        cacheHealthNewsDataSource.clearHealthNews()


        expectedException.expect(com.example.core.exceptions.NoDatabaseDataFoundException::class.java)
        expectedException.expectMessage(DbConstants.DATA_NOT_FOUND)

        cacheHealthNewsDataSource.getHealthNews()
        Unit
    }
}
