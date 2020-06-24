package com.biodun.cache.mapper

import com.biodun.cache.db.DbConstants.DATA_NOT_FOUND
import com.biodun.cache.model.CacheNewsEntity
import com.example.data.exceptions.NoDatabaseDataFoundException
import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity
import javax.inject.Inject

class CacheNewsEntityMapper @Inject constructor() {

    fun mapFromCache(cacheNewsEntity: List<CacheNewsEntity>): NewsResultEntity {
        if (cacheNewsEntity.isNotEmpty()) {
            val totalResult = cacheNewsEntity[0].totalResult
            val articleEntities: List<ArticleEntity> = cacheNewsEntity.map {
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
            return NewsResultEntity(totalResults = totalResult, articles = articleEntities)
        }

        throw NoDatabaseDataFoundException(DATA_NOT_FOUND)
    }

    fun mapToCache(newsResultEntity: NewsResultEntity, newsType: String): List<CacheNewsEntity> {
        val totalResults = newsResultEntity.totalResults
        return newsResultEntity.articles.map {
            CacheNewsEntity(
                sourceName = it.sourceName,
                newsType = newsType,
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