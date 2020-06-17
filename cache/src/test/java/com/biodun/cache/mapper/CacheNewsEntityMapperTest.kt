package com.biodun.cache.mapper

import com.biodun.cache.db.DbConstants.BUSINESS_NEWS
import com.biodun.cache.factory.FakeCacheTestFactory
import com.biodun.cache.model.CacheNewsEntity
import com.example.data.model.NewsResultEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CacheNewsEntityMapperTest {

    private lateinit var newsResultEntity: NewsResultEntity
    private lateinit var cachedBusinessNewsEntityList: List<CacheNewsEntity>
    private lateinit var cacheNewsEntityMapper: CacheNewsEntityMapper

    @Before
    fun setUp() {
        cachedBusinessNewsEntityList = FakeCacheTestFactory.makeBusinessNewsList()
        newsResultEntity = FakeCacheTestFactory.makeNewsResultEntity()
        cacheNewsEntityMapper = CacheNewsEntityMapper()
    }

    @Test
    fun mapFromCacheTest() {
        val returnedNewsResultEntity: NewsResultEntity =
            cacheNewsEntityMapper.mapFromCache(cachedBusinessNewsEntityList)

        assertEquals(returnedNewsResultEntity.articles.size, cachedBusinessNewsEntityList.size)
        assertEquals(returnedNewsResultEntity.articles[0].sourceName, cachedBusinessNewsEntityList[0].sourceName)
        assertEquals(returnedNewsResultEntity.articles[0].title, cachedBusinessNewsEntityList[0].title)
        assertEquals(returnedNewsResultEntity.articles[0].content, cachedBusinessNewsEntityList[0].content)
        assertEquals(returnedNewsResultEntity.articles[0].url, cachedBusinessNewsEntityList[0].url)
        assertEquals(returnedNewsResultEntity.articles[0].urlToImage, cachedBusinessNewsEntityList[0].urlToImage)
    }

    @Test
    fun mapToCacheTest() {
        val returnedCachedBusinessNewsEntity: List<CacheNewsEntity> =
            cacheNewsEntityMapper.mapToCache(newsResultEntity, newsType = BUSINESS_NEWS)

        assertEquals(returnedCachedBusinessNewsEntity.size, cachedBusinessNewsEntityList.size)
        assertEquals(returnedCachedBusinessNewsEntity[0].content, cachedBusinessNewsEntityList[0].content)
        assertEquals(returnedCachedBusinessNewsEntity[0].sourceName, cachedBusinessNewsEntityList[0].sourceName)
        assertEquals(returnedCachedBusinessNewsEntity[0].title, cachedBusinessNewsEntityList[0].title)
        assertEquals(returnedCachedBusinessNewsEntity[0].url, cachedBusinessNewsEntityList[0].url)
        assertEquals(returnedCachedBusinessNewsEntity[0].urlToImage, cachedBusinessNewsEntityList[0].urlToImage)
    }
}
