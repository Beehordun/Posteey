package com.example.data.mappers

import com.example.data.fakefactory.TestFakeFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsResultEntityMapperTest {

    @Test
    fun mapFromNewsResultEntityTest_returns_correct_newsResult() {
        val newsResultEntity = TestFakeFactory.makeNewsResultEntity()
        val newsResultEntityMapper = NewsResultEntityMapper()
        val expectedNewsResult = TestFakeFactory.makeNewsResultFromNewsEntity(newsResultEntity)

        val returnedNewsEntity = newsResultEntityMapper.mapFromNewsResultEntity(newsResultEntity)

        assertEquals(expectedNewsResult.totalResults, returnedNewsEntity.totalResults)
        assertEquals(expectedNewsResult.articles[0].author, returnedNewsEntity.articles[0].author)
        assertEquals(expectedNewsResult.articles[0].content, returnedNewsEntity.articles[0].content)
        assertEquals(expectedNewsResult.articles[0].description, returnedNewsEntity.articles[0].description)
        assertEquals(expectedNewsResult.articles[0].title, returnedNewsEntity.articles[0].title)
        assertEquals(expectedNewsResult.articles[0].sourceName, returnedNewsEntity.articles[0].sourceName)
        assertEquals(expectedNewsResult.articles[0].url, returnedNewsEntity.articles[0].url)
        assertEquals(expectedNewsResult.articles[0].urlToImage, returnedNewsEntity.articles[0].urlToImage)
    }
}
