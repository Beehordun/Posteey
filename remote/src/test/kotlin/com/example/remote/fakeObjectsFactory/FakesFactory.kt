package com.example.remote.fakeObjectsFactory

import com.example.data.model.ArticleEntity
import com.example.data.model.NewsResultEntity
import com.example.remote.model.RemoteArticle
import com.example.remote.model.RemoteNewsResult
import com.example.remote.model.RemoteNewsSource

object FakesFactory {

    fun getFakeRemoteNewsResult(): RemoteNewsResult {
        return RemoteNewsResult(
            totalResults = 23,
            remoteArticles = listOf(getFakeRemoteArticle())
        )
    }

    fun getFakeNewsResultEntity(): NewsResultEntity {
        return NewsResultEntity(
            totalResults = 23,
            articles = listOf(getFakeArticleEntity())
        )
    }

    fun getFakeRemoteArticle(): RemoteArticle {
        return RemoteArticle(
            remoteSource = RemoteNewsSource(name = "BBC"),
            author = "BBC News",
            title = "Black lives matter",
            description = "People all over the world have been protesting against racial injustice",
            url = "https:/bbc-news.com/black-lives",
            urlToImage = "https:content.bbc-news.com/black-lives.jpg",
            publishedAt = "2020-06-13T06:32:51Z",
            content = "Following the latest events of racial injustice"
        )
    }

    fun getFakeArticleEntity(): ArticleEntity {
        return ArticleEntity(
            sourceName = "BBC",
            author = "BBC News",
            title = "Black lives matter",
            description = "People all over the world have been protesting against racial injustice",
            url = "https:/bbc-news.com/black-lives",
            urlToImage = "https:content.bbc-news.com/black-lives.jpg",
            publishedAt = "2020-06-13T06:32:51Z",
            content = "Following the latest events of racial injustice"
        )
    }
}
