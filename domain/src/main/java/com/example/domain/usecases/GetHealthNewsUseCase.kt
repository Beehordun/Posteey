package com.example.domain.usecases

import com.example.domain.model.NewsResult
import com.example.domain.repositories.HealthNewsRepository
import javax.inject.Inject

class GetHealthNewsUseCase @Inject constructor(
    private val healthNewsRepository: HealthNewsRepository
) {

    suspend fun getHealthNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult {
        return healthNewsRepository.getHealthNews(query, pageSize, page)
    }
}
