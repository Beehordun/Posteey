package com.example.posteey.di

import com.biodun.cache.datasources.*
import com.example.data.cache.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCacheBusinessNewsDataSource(
        cacheBusinessNewsDataSourceImpl: CacheBusinessNewsDataSourceImpl
    ): CacheBusinessNewsDataSource

    @Binds
    abstract fun bindCacheTechnologyNewsDataSource(
        cacheTechnologyNewsDataSourceImpl: CacheTechnologyNewsDataSourceImpl
    ): CacheTechnologyNewsDataSource

    @Binds
    abstract fun bindCacheEntertainmentNewsDataSource(
        cacheEntertainmentNewsDataSourceImpl: CacheEntertainmentNewsDataSourceImpl
    ): CacheEntertainmentNewsDataSource

    @Binds
    abstract fun bindCacheSportsNewsDataSource(
        cacheSportsNewsDataSourceImpl: CacheSportsNewsDataSourceImpl
    ): CacheSportsNewsDataSource

    @Binds
    abstract fun bindCacheGeneralNewsDataSource(
        cacheGeneralNewsDataSourceImpl: CacheGeneralNewsDataSourceImpl
    ): CacheGeneralNewsDataSource

    @Binds
    abstract fun bindCacheScienceNewsDataSource(
        cacheScienceNewsDataSourceImpl: CacheScienceNewsDataSourceImpl
    ): CacheScienceNewsDataSource

    @Binds
    abstract fun bindCacheHealthNewsDataSource(
        cacheHealthNewsDataSourceImpl: CacheHealthNewsDataSourceImpl
    ): CacheHealthNewsDataSource
}
