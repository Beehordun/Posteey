package com.biodun.cache.factory

import com.biodun.cache.db.DbConstants.BUSINESS_NEWS
import com.biodun.cache.db.DbConstants.SPORTS_NEWS
import com.biodun.cache.model.CacheNewsEntity
import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity
import java.util.UUID

object FakeCacheTestFactory {

    fun makeBusinessNewsList(): List<CacheNewsEntity> {
        return listOf(
            CacheNewsEntity(
                id = UUID.randomUUID(),
                newsType = BUSINESS_NEWS,
                sourceName = "BBC NEWS",
                title = "Businesses need to find innovative ways in this period",
                author = "Jimmy Carter",
                description = "A Mckinsey expert advises business on innovative ways",
                url = "https/bbc-news.com/business",
                urlToImage = "https/bbc-news.com/business.jpg",
                publishedAt = "020-06-13T06:32:51Z",
                content = "Businesses need to find a way in this ..",
                totalResult = 23
            ),
            CacheNewsEntity(
                id = UUID.randomUUID(),
                newsType = BUSINESS_NEWS,
                sourceName = "CNN NEWS",
                title = "Businesses need to find innovative ways in this period",
                author = "Harry Carter",
                description = "A Mckinsey expert advises business on innovative ways",
                url = "https/bbc-news.com/business",
                urlToImage = "https/bbc-news.com/business.jpg",
                publishedAt = "020-06-13T06:32:51Z",
                content = "Businesses need to find a way in this ..",
                totalResult = 23
            ))
    }

    fun makeSportsNewsList(): List<CacheNewsEntity> {
        return listOf(
            CacheNewsEntity(
                id = UUID.randomUUID(),
                newsType = SPORTS_NEWS,
                sourceName = "CNN NEWS",
                title = "Businesses need to find innovative ways in this period",
                author = "Harry Carter",
                description = "A Mckinsey expert advises business on innovative ways",
                url = "https/bbc-news.com/business",
                urlToImage = "https/bbc-news.com/business.jpg",
                publishedAt = "020-06-13T06:32:51Z",
                content = "Businesses need to find a way in this ..",
                totalResult = 23
            )
       )
    }



    fun makeNewsList(): List<CacheNewsEntity> {
        return makeBusinessNewsList().plus(makeSportsNewsList())
    }

    fun makeNewsResultEntity(): NewsResultEntity {
        return NewsResultEntity(
            totalResult = 23,
            articles = getFakeArticleEntityList()
        )
    }

    private fun getFakeArticleEntityList(): List<ArticleEntity> {
        return listOf(
            ArticleEntity(
                sourceName = "BBC NEWS",
                title = "Businesses need to find innovative ways in this period",
                author = "Jimmy Carter",
                description = "A Mckinsey expert advises business on innovative ways",
                url = "https/bbc-news.com/business",
                urlToImage = "https/bbc-news.com/business.jpg",
                publishedAt = "020-06-13T06:32:51Z",
                content = "Businesses need to find a way in this .."
            ),

            ArticleEntity(
                sourceName = "CNN NEWS",
                title = "Businesses need to find innovative ways in this period",
                author = "Harry Carter",
                description = "A Mckinsey expert advises business on innovative ways",
                url = "https/bbc-news.com/business",
                urlToImage = "https/bbc-news.com/business.jpg",
                publishedAt = "020-06-13T06:32:51Z",
                content = "Businesses need to find a way in this .."
            )
        )
    }
}