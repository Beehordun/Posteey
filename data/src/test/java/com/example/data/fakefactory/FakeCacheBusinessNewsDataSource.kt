package com.example.data.fakefactory

import com.example.data.cache.CacheBusinessNewsDataSource
import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity

class FakeCacheBusinessNewsDataSource(): CacheBusinessNewsDataSource {

    private val allNewsArticles: MutableList<ArticleEntity> = mutableListOf()
    private val newsResultEntity = NewsResultEntity(totalResults = 10, articles = allNewsArticles)

    override suspend fun getBusinessNews(): NewsResultEntity {
        return newsResultEntity
    }

    override suspend fun insertBusinessNews(newsResults: NewsResultEntity) {
        allNewsArticles.addAll(
            newsResults.articles.map {
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
        )
    }

    override suspend fun clearBusinessNews() {
        allNewsArticles.clear()
    }

}