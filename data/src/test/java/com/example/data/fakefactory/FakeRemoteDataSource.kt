package com.example.data.fakefactory

import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity
import com.example.data.remote.RemoteDataSource

class FakeRemoteDataSource (): RemoteDataSource {

    override suspend fun getNewsHeadlines(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResultEntity {
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

    override suspend fun searchNews(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): NewsResultEntity {
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
}
