package com.example.remote.mapper

import com.example.data.model.NewsResultEntity
import com.example.remote.fakeObjectsFactory.FakesFactory
import com.example.remote.model.RemoteNewsResult
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteNewsResultMapperTest {

    private val remoteNewsResult: RemoteNewsResult =  FakesFactory.getFakeRemoteNewsResult()
    private val newsResultEntity: NewsResultEntity = FakesFactory.getFakeNewsResultEntity()
    private val remoteNewsResultMapper: RemoteNewsResultMapper =
        RemoteNewsResultMapper(RemoteArticleMapper())


    @Test
    fun mapRemoteNewsResultToNewsResultEntityTest() {
        val returnedNewsResultEntity: NewsResultEntity =
            remoteNewsResultMapper.mapRemoteNewsResultToNewsResultEntity(remoteNewsResult)

        assertEquals(newsResultEntity.totalResult, returnedNewsResultEntity.totalResult)
        assertEquals(newsResultEntity.articles, returnedNewsResultEntity.articles)
    }
}
