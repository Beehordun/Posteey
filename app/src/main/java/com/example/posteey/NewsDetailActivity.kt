package com.example.posteey

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.core.connectivity.NetworkInformation
import com.example.posteey.utils.NewsWebViewClient
import com.example.presentation.models.NewsArticle
import kotlinx.android.synthetic.main.activity_news_detail.*

const val NEWS_ARTICLE = "News Article"

class NewsDetailActivity : AppCompatActivity(), NewsWebViewClient.WebPageStateHandler {

    lateinit var newsArticle: NewsArticle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        checkNetworkStatusBeforeLaunchingUrl()
    }

    override fun onBackPressed() {
        if (news_details_view.canGoBack()) {
            news_details_view.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun handleOnPageLoading() {
        news_details_progress_bar.visibility = VISIBLE
    }

    override fun handleOnPageLoaded() {
        news_details_progress_bar.visibility = GONE
    }

    private fun checkNetworkStatusBeforeLaunchingUrl() {
        if (NetworkInformation.isConnectionOn(this)) {
            fetchNewsContent()
        } else {
            showOfflineScreen()
        }
    }

    private fun showOfflineScreen() {
        network_state_text.visibility = VISIBLE
    }

    private fun fetchNewsContent() {
        if (intent.hasExtra(NEWS_ARTICLE)) {
            newsArticle = intent.getParcelableExtra(NEWS_ARTICLE) as NewsArticle
            news_details_view.settings.javaScriptEnabled = true
            news_details_view.webViewClient = NewsWebViewClient(this)
                news_details_view.loadUrl(newsArticle.url)
        }
    }

    companion object {

        fun getIntent(context: Context, newsArticle: NewsArticle): Intent =
            Intent(context, NewsDetailActivity::class.java).apply {
                putExtra(NEWS_ARTICLE, newsArticle)
            }
    }
}
