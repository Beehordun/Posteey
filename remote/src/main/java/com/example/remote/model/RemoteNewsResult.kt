package com.example.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteNewsResult(
    val totalResults: Int,
    @SerializedName("articles")
    val remoteArticles: List<RemoteArticle>
)
