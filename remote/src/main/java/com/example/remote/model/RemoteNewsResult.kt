package com.example.remote.model

data class RemoteNewsResult(
    val totalResult: Int,
    val remoteArticles: List<RemoteArticle>
)
