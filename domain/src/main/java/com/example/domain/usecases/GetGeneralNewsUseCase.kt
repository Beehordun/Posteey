package com.example.domain.usecases

import com.example.domain.model.NewsResult
import com.example.domain.repositories.GeneralNewsRepository
import javax.inject.Inject

class GetGeneralNewsUseCase @Inject constructor(
    private val generalNewsRepository: GeneralNewsRepository
) {

    suspend fun getGeneralNews(query: Map<String, String>, pageSize: Int, page: Int): NewsResult {
        return generalNewsRepository.getGeneralNews(query, pageSize, page).also {
            it.articles.map {
                println("Log--> ${it.title.substring(0..13)}")
            }
        }
    }
}