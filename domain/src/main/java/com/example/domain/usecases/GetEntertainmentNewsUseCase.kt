package com.example.domain.usecases

import com.example.domain.model.NewsResult
import com.example.domain.repositories.EntertainmentNewsRepository
import javax.inject.Inject

class GetEntertainmentNewsUseCase @Inject constructor(
    private val entertainmentNewsRepository: EntertainmentNewsRepository
) {

    suspend fun getEntertainmentNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult {
        return entertainmentNewsRepository.getEntertainmentNews(query, pageSize, page)
    }
}
