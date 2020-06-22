package com.example.domain.usecases

import com.example.domain.model.NewsResult
import com.example.domain.repositories.BusinessNewsRepository
import javax.inject.Inject

class GetBusinessNewsUseCase @Inject constructor(
    private val businessNewsRepository: BusinessNewsRepository
) {

    suspend fun getBusinessNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult {
        return businessNewsRepository.getBusinessNews(query, pageSize, page)
    }
}
