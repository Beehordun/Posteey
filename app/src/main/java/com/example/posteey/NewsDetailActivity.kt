package com.example.posteey

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.core.connectivity.NetworkManager
import com.example.core.connectivity.NetworkState
import com.example.posteey.utils.NewsWebViewClient
import com.example.presentation.models.NewsArticle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_detail.*
import javax.inject.Inject

const val NEWS_ARTICLE = "News Article"

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity(), NewsWebViewClient.WebPageStateHandler {

    @Inject
    lateinit var networkManager: NetworkManager
    lateinit var newsArticle: NewsArticle
    private val owner = { lifecycle }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        monitorNetworkState()
    }

    override fun onStart() {
        super.onStart()
        networkManager.registerCallback()
    }

    override fun onStop() {
        super.onStop()
        networkManager.unRegisterCallback()
    }

    private fun monitorNetworkState() {
        networkManager.networkState.observe(owner) { networkState ->
            when(networkState) {

                is NetworkState.Connected -> showNewsContent()
                is NetworkState.NoConnection -> {
                    network_state_text.apply {
                        text = networkState.message
                        visibility = VISIBLE
                    }
                    news_details_progress_bar.visibility = GONE
                }
            }
        }
    }

    private fun showNewsContent() {
        if (intent.hasExtra(NEWS_ARTICLE)) {
            newsArticle = intent.getParcelableExtra(NEWS_ARTICLE) as NewsArticle
            news_details_view.settings.javaScriptEnabled = true
            news_details_view.webViewClient = NewsWebViewClient(this)
                news_details_view.loadUrl(newsArticle.url)
        }
    }

    override fun onBackPressed() {
        if (news_details_view.canGoBack()) {
            news_details_view.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {

        fun getIntent(context: Context, newsArticle: NewsArticle): Intent =
            Intent(context, NewsDetailActivity::class.java).apply {
                putExtra(NEWS_ARTICLE, newsArticle)
            }
    }

    override fun handleOnPageLoading() {
        news_details_progress_bar.visibility = VISIBLE
    }

    override fun handleOnPageLoaded() {
       news_details_progress_bar.visibility = GONE
    }
}
