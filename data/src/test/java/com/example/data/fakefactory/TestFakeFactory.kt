package com.example.data.fakefactory

import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity
import com.example.domain.model.Article
import com.example.domain.model.NewsResult

object TestFakeFactory {
    fun makeNewsResultEntity(): NewsResultEntity {
        return NewsResultEntity(
            totalResults = 10,
            articles = listOf(
                ArticleEntity(
                    sourceName = "BBC",
                    title = "Say no to racism",
                    author = "Ben Mani",
                    url = "https://ben-madi.com",
                    urlToImage = "https;//ben-madi.com/pic.jpg",
                    content = "Let's all say no to all forms of racial injustice",
                    description = "No to racism",
                    publishedAt = "020-06-13T06:32:51Z"
                )
            )
        )
    }

    fun makeNewsResultFromNewsEntity(newsResultEntity: NewsResultEntity): NewsResult {
        return NewsResult(
            totalResults = newsResultEntity.totalResults,
            articles = listOf(
                Article(
                    sourceName = newsResultEntity.articles[0].sourceName,
                    title = newsResultEntity.articles[0].title,
                    author = newsResultEntity.articles[0].author,
                    url = newsResultEntity.articles[0].url,
                    urlToImage = newsResultEntity.articles[0].urlToImage,
                    content = newsResultEntity.articles[0].content,
                    description = newsResultEntity.articles[0].description,
                    publishedAt = newsResultEntity.articles[0].publishedAt
                )
            )
        )
    }
}