package com.example.remote.fakeObjectsFactory

import com.example.remote.NewsApi
import com.example.remote.model.RemoteNewsResult

class FakeNewsApi: NewsApi {

    override suspend fun getNewsHeadlines(
        query: Map<String, String>,
        pageSize: Int,
        page: Int
    ): RemoteNewsResult {
        return FakesFactory.getFakeRemoteNewsResult()
    }

    override suspend fun searchNews(
        searchQuery: Map<String, String>,
        pageSize: Int,
        page: Int
    ): RemoteNewsResult {
        return FakesFactory.getFakeRemoteNewsResult()
    }
}
