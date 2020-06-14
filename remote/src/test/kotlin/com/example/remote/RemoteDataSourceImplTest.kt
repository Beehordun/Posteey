package com.example.remote

import com.example.data.model.NewsResultEntity
import com.example.remote.fakeObjectsFactory.FakeNewsApi
import com.example.remote.fakeObjectsFactory.FakesFactory
import com.example.remote.mapper.RemoteArticleMapper
import com.example.remote.mapper.RemoteNewsResultMapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RemoteDataSourceImplTest {

    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    private lateinit var newsResultEntity: NewsResultEntity

    @Before
    fun setUp() {
        val fakeNewsApi = FakeNewsApi()
        val remoteArticleMapper = RemoteArticleMapper()
        val remoteNewsResultMapper = RemoteNewsResultMapper(remoteArticleMapper)

        remoteDataSourceImpl = RemoteDataSourceImpl(remoteNewsResultMapper, fakeNewsApi)
        newsResultEntity = FakesFactory.getFakeNewsResultEntity()
    }

    @Test
    fun getNewsHeadLinesReturnsNewsResultEntity() {
        val query = mapOf("country" to "ng", "category" to "business")
        val pageSize = 10
        val page = 1

        runBlocking {
            val returnedNewsEntity = remoteDataSourceImpl.getNewsHeadlines(query, pageSize, page)

            Assert.assertEquals(newsResultEntity.totalResult, returnedNewsEntity.totalResult)
            Assert.assertEquals(newsResultEntity.articles, returnedNewsEntity.articles)
        }
    }

    @Test
    fun searchNewsReturnsNewsResultEntity() {
        val query = mapOf("country" to "ng", "category" to "business")
        val pageSize = 10
        val page = 1

        runBlocking {
            val returnedNewsEntity = remoteDataSourceImpl.searchNews(query, pageSize, page)

            Assert.assertEquals(newsResultEntity.totalResult, returnedNewsEntity.totalResult)
            Assert.assertEquals(newsResultEntity.articles, returnedNewsEntity.articles)
        }
    }
}
