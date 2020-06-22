package com.example.data.mappers

import com.example.data.model.NewsResultEntity
import com.example.domain.model.Article
import com.example.domain.model.NewsResult
import javax.inject.Inject

class NewsResultEntityMapper @Inject constructor() {

    fun mapFromNewsResultEntity(newsResultEntity: NewsResultEntity): NewsResult {
        return NewsResult(
            totalResults = newsResultEntity.totalResults,
            articles = newsResultEntity.articles.map {
                Article(
                    sourceName = it.sourceName,
                    title = it.title,
                    author = it.author,
                    url = it.url,
                    urlToImage = it.urlToImage,
                    content = it.content,
                    description = it.description,
                    publishedAt = it.publishedAt
                )
            }
        )
    }
}
