package com.biodun.cache.datasources

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.factory.FakeCacheTestFactory
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheGeneralNewsDataSource
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.data.model.NewsResultEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CacheGeneralNewsDataSourceTest {

    private lateinit var newsDb: NewsResultDatabase
    private lateinit var cacheNewsEntityMapper: CacheNewsEntityMapper
    private lateinit var newsResultEntity: NewsResultEntity
    private lateinit var cacheGeneralNewsDataSource: CacheGeneralNewsDataSource

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDb = Room.inMemoryDatabaseBuilder(
            context, NewsResultDatabase::class.java).build()

        cacheNewsEntityMapper = CacheNewsEntityMapper()
        cacheGeneralNewsDataSource = CacheGeneralNewsDataSourceImpl(newsDb, cacheNewsEntityMapper)
        newsResultEntity = FakeCacheTestFactory.makeNewsResultEntity()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun getGeneralNewsTest() = runBlocking {
        cacheGeneralNewsDataSource.insertGeneralNews(newsResultEntity)
        val returnedNewsEntity = cacheGeneralNewsDataSource.getGeneralNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun insertGeneralNewsTest() = runBlocking {
        cacheGeneralNewsDataSource.insertGeneralNews(newsResultEntity)
        val returnedNewsEntity = cacheGeneralNewsDataSource.getGeneralNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun clearGeneralNewsTest() = runBlocking {
        cacheGeneralNewsDataSource.insertGeneralNews(newsResultEntity)
        cacheGeneralNewsDataSource.clearGeneralNews()


        expectedException.expect(com.example.core.exceptions.NoDatabaseDataFoundException::class.java)
        expectedException.expectMessage(DbConstants.DATA_NOT_FOUND)

        cacheGeneralNewsDataSource.getGeneralNews()
        Unit
    }

}