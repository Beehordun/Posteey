package com.example.remote

import com.example.remote.model.RemoteNewsResult
import retrofit2.http.*

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getNewsHeadlines(@QueryMap query: Map<String, String>,
                                 @Query("pageSize") pageSize: Int,
                                 @Query("page") page: Int): RemoteNewsResult

    @GET("v2/everything")
    suspend fun searchNews(@QueryMap searchQuery: Map<String, String>,
                           @Query("pageSize") pageSize: Int,
                           @Query("page") page: Int): RemoteNewsResult
}
