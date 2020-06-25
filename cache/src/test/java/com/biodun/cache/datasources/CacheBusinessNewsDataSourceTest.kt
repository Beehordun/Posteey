package com.biodun.cache.datasources

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.biodun.cache.db.DbConstants.DATA_NOT_FOUND
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.factory.FakeCacheTestFactory
import com.biodun.cache.mapper.CacheNewsEntityMapper
import com.example.data.cache.CacheBusinessNewsDataSource
import com.example.core.exceptions.NoDatabaseDataFoundException
import com.example.data.model.NewsResultEntity
import kotlinx.coroutines.runBlocking
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CacheBusinessNewsDataSourceTest {

    private lateinit var newsDb: NewsResultDatabase
    private lateinit var cacheNewsEntityMapper: CacheNewsEntityMapper
    private lateinit var newsResultEntity: NewsResultEntity
    private lateinit var cacheBusinessNewsDataSource: CacheBusinessNewsDataSource

    @get:Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDb = Room.inMemoryDatabaseBuilder(
            context, NewsResultDatabase::class.java).build()

        cacheNewsEntityMapper = CacheNewsEntityMapper()
        cacheBusinessNewsDataSource = CacheBusinessNewsDataSourceImpl(newsDb, cacheNewsEntityMapper)
        newsResultEntity = FakeCacheTestFactory.makeNewsResultEntity()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun getBusinessNewsTest() = runBlocking {
        cacheBusinessNewsDataSource.insertBusinessNews(newsResultEntity)
        val returnedNewsEntity = cacheBusinessNewsDataSource.getBusinessNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun insertBusinessNewsTest() = runBlocking {
        cacheBusinessNewsDataSource.insertBusinessNews(newsResultEntity)
        val returnedNewsEntity = cacheBusinessNewsDataSource.getBusinessNews()

        Assert.assertEquals(returnedNewsEntity, newsResultEntity)
    }

    @Test
    fun clearBusinessNewsTest() = runBlocking {
        cacheBusinessNewsDataSource.insertBusinessNews(newsResultEntity)
        cacheBusinessNewsDataSource.clearBusinessNews()


        expectedException.expect(com.example.core.exceptions.NoDatabaseDataFoundException::class.java)
        expectedException.expectMessage(DATA_NOT_FOUND)

        cacheBusinessNewsDataSource.getBusinessNews()
        Unit
    }
}
