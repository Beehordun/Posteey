package com.example.presentation.utils

object QueryBuilder {

    fun buildQuery(category: NewsCategory, country: Country): Map<String, String> {
        return mapOf("category" to category.newsType, "country" to country.countryName)
    }
}