package com.example.domain.model

data class NewsResult(
    val totalResults: Int,
    val articles: List<Article>
)