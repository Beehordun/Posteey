package com.example.remote.model

data class RemoteArticle(
    val remoteSource: RemoteNewsSource,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)
