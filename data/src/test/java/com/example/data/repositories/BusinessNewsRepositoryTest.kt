package com.example.data.repositories

import com.example.data.cache.CacheBusinessNewsDataSource
import com.example.data.fakefactory.FakeCacheBusinessNewsDataSource
import com.example.data.fakefactory.FakeRemoteDataSource
import com.example.data.mappers.NewsResultEntityMapper
import com.example.data.remote.RemoteDataSource
import org.junit.Before

class BusinessNewsRepositoryTest {

    private lateinit var cacheBusinessNewsDataSource: CacheBusinessNewsDataSource
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var newsResultEntityMapper: NewsResultEntityMapper
    private lateinit var businessNewsRepositoryImpl: BusinessNewsRepositoryImpl

    @Before
    fun setUp() {
        cacheBusinessNewsDataSource = FakeCacheBusinessNewsDataSource()
        remoteDataSource = FakeRemoteDataSource()
        newsResultEntityMapper = NewsResultEntityMapper()
        businessNewsRepositoryImpl = BusinessNewsRepositoryImpl(
            cacheBusinessNewsDataSource,
            remoteDataSource,
            newsResultEntityMapper
        )
    }
}