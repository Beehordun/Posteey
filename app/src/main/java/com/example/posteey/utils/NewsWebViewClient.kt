package com.example.posteey.utils

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient

class NewsWebViewClient constructor(
    private val webPageStateHandler: WebPageStateHandler
): WebViewClient() {

    interface WebPageStateHandler {
        fun handleOnPageLoading()
        fun handleOnPageLoaded()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        webPageStateHandler.handleOnPageLoaded()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        webPageStateHandler.handleOnPageLoading()
    }
}