package com.example.remote.mapper

import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity
import com.example.remote.model.RemoteArticle
import com.example.remote.model.RemoteNewsResult
import javax.inject.Inject

class RemoteNewsResultMapper @Inject constructor(
    private val remoteArticleMapper: RemoteArticleMapper
) {

    fun mapRemoteNewsResultToNewsResultEntity(
        remoteNewsResult: RemoteNewsResult
    ): NewsResultEntity {
        return NewsResultEntity(
            totalResults = remoteNewsResult.totalResults,
            articles = mapRemoteArticleList(remoteNewsResult.remoteArticles)
        )
    }

    private fun mapRemoteArticleList(remoteArticleList: List<RemoteArticle>): List<ArticleEntity> {
        return remoteArticleList.map {
            remoteArticleMapper.mapRemoteArticleToArticleEntity(it)
        }
    }
}
