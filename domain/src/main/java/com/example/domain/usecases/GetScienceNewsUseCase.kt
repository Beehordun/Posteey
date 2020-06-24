package com.example.domain.usecases

import com.example.domain.model.NewsResult
import com.example.domain.repositories.ScienceNewsRepository
import javax.inject.Inject

class GetScienceNewsUseCase @Inject constructor(
    private val scienceNewsRepository: ScienceNewsRepository
) {

    suspend fun getScienceNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult {
        return scienceNewsRepository.getScienceNews(query, pageSize, page)
    }
}