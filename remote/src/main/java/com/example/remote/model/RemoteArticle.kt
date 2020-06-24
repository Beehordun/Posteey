package com.example.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteArticle(
    @SerializedName("source")
    val remoteSource: RemoteNewsSource,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = ""
)
