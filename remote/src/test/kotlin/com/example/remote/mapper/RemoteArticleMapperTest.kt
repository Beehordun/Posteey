package com.example.remote.mapper

import com.example.data.model.ArticleEntity
import com.example.remote.fakeObjectsFactory.FakesFactory
import com.example.remote.model.RemoteArticle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteArticleMapperTest {

    private lateinit var remoteArticle: RemoteArticle
    private lateinit var articleEntity: ArticleEntity
    private lateinit var remoteArticleMapper: RemoteArticleMapper

    @Before
    fun setUp() {
        remoteArticle = FakesFactory.getFakeRemoteArticle()
        articleEntity = FakesFactory.getFakeArticleEntity()
        remoteArticleMapper = RemoteArticleMapper()
    }

    @Test
    fun mapRemoteArticleToArticleEntityTest() {
        val returnedEntity: ArticleEntity =
            remoteArticleMapper.mapRemoteArticleToArticleEntity(remoteArticle)

        assertEquals(articleEntity.sourceName, returnedEntity.sourceName)
        assertEquals(articleEntity.author, returnedEntity.author)
        assertEquals(articleEntity.title, returnedEntity.title)
        assertEquals(articleEntity.description, returnedEntity.description)
        assertEquals(articleEntity.url, returnedEntity.url)
        assertEquals(articleEntity.urlToImage, returnedEntity.urlToImage)
        assertEquals(articleEntity.publishedAt, returnedEntity.publishedAt)
        assertEquals(articleEntity.content, returnedEntity.content)
    }
}
