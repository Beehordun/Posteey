package com.example.domain.usecases

import com.example.domain.model.NewsResult
import com.example.domain.repositories.SportsNewsRepository
import javax.inject.Inject

class GetSportsNewsUseCase @Inject constructor(
    private val sportsNewsRepository: SportsNewsRepository
) {

    suspend fun getSportsNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult {
        return sportsNewsRepository.getSportsNews(query, pageSize, page)
    }
}