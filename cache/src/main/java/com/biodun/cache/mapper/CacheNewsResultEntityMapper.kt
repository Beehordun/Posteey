package com.biodun.cache.mapper

import com.biodun.cache.model.CacheNewsResultEntity
import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheNewsResultEntityMapper @Inject constructor() {

    fun mapFromCache(cacheNewsResultEntity: List<CacheNewsResultEntity>): NewsResultEntity {
        val totalResult = cacheNewsResultEntity[0].totalResult
        val articleEntities: List<ArticleEntity> = cacheNewsResultEntity.map {
            ArticleEntity(
                sourceName = it.sourceName,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }

        return NewsResultEntity(totalResult = totalResult, articles = articleEntities)
    }

    fun mapToCache(newsResultEntity: NewsResultEntity): List<CacheNewsResultEntity> {
        val totalResults = newsResultEntity.totalResult
        return newsResultEntity.articles.map {
            CacheNewsResultEntity(
                sourceName = it.sourceName,
                author = it.author,
                description = it.description,
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                totalResult = totalResults
            )
        }
    }
}
