package com.example.domain.usecases

import com.example.domain.model.NewsResult
import com.example.domain.repositories.TechnologyNewsRepository
import javax.inject.Inject

class GetTechnologyNewsUseCase @Inject constructor(
    private val technologyNewsRepository: TechnologyNewsRepository
) {

    suspend fun getTechnologyNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult {
        return technologyNewsRepository.getTechnologyNews(query, pageSize, page)
    }
}
