package com.example.remote

import com.example.remote.model.RemoteNewsResult
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("/top-headlines")
    suspend fun getNewsHeadlines(@QueryMap query: Map<String, String>,
                                 @Query("pageSize") pageSize: Int,
                                 @Query("page") page: Int): RemoteNewsResult

    @GET("/everything")
    suspend fun searchNews(@QueryMap searchQuery: Map<String, String>,
                           @Query("pageSize") pageSize: Int,
                           @Query("page") page: Int): RemoteNewsResult
}
