package com.example.presentation.mappers

import com.example.domain.model.NewsResult
import com.example.presentation.models.NewsArticle
import java.util.*
import javax.inject.Inject

class NewsArticleMapper @Inject constructor() {

    fun mapFromDomainToPresentation(newsResult: NewsResult): List<NewsArticle> {
        return newsResult.articles.map {
            NewsArticle(
                id = UUID.randomUUID().toString(),
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
    }
}
