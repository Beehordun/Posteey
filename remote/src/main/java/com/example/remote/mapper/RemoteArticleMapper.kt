package com.example.remote.mapper

import com.example.data.model.ArticleEntity
import com.example.remote.model.RemoteArticle
import javax.inject.Inject

class RemoteArticleMapper @Inject constructor() {

    fun mapRemoteArticleToArticleEntity(remoteArticle: RemoteArticle): ArticleEntity {
        return ArticleEntity(
            sourceName = remoteArticle.remoteSource.name,
            author = remoteArticle.author ?: "",
            title = remoteArticle.title ?: "",
            description = remoteArticle.description ?: "",
            url = remoteArticle.url ?: "",
            urlToImage = remoteArticle.urlToImage ?: "",
            publishedAt = remoteArticle.publishedAt ?: "",
            content = remoteArticle.content ?: ""
        )
    }
}