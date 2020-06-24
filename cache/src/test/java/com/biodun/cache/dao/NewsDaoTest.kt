package com.biodun.cache.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import com.biodun.cache.factory.FakeCacheTestFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NewsDaoTest {

    private lateinit var newsDb: NewsResultDatabase
    private lateinit var newsDao: NewsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDb = Room.inMemoryDatabaseBuilder(
            context, NewsResultDatabase::class.java).build()

        newsDao = newsDb.newsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun insertAllNews_whereNewsTypeIsBusiness() = runBlocking {
        val news = FakeCacheTestFactory.makeNewsList(DbConstants.BUSINESS_NEWS)

        newsDao.insertAllNews(news)
        val insertedBusinessNews = newsDao.getNews(DbConstants.BUSINESS_NEWS)

        Assert.assertEquals(insertedBusinessNews.size, news.size)
    }

    @Test
    fun getNews_whereNewsTypeIsSport() = runBlocking{
        val news = FakeCacheTestFactory.makeNewsList(DbConstants.SPORTS_NEWS)

        newsDao.insertAllNews(news)
        val returnedNews = newsDao.getNews(DbConstants.SPORTS_NEWS)

        Assert.assertEquals(returnedNews.size, news.size)
        Assert.assertEquals(returnedNews[0].newsType, DbConstants.SPORTS_NEWS)
    }

    @Test
    fun clearNews_whereNewsTypeIsBusiness() = runBlocking {
        val news = FakeCacheTestFactory.makeNewsList(DbConstants.BUSINESS_NEWS)

        newsDao.insertAllNews(news)
        newsDao.clearNews(DbConstants.BUSINESS_NEWS)

        val returnedNews = newsDao.getNews(DbConstants.BUSINESS_NEWS)

        Assert.assertEquals(returnedNews.size, 0)
    }
}
