package com.example.core.connectivity

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ActivityContext

class NetworkManager (
    @ActivityContext context: Context,
    private val callback: ConnectivityCallback,
    private val networkRequestFactory: NetworkRequestFactory
) {

    val networkState = callback.networkState

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    fun registerCallback() {
        val request = networkRequestFactory.networkRequest
        connectivityManager.registerNetworkCallback(request, callback)
    }

    fun unRegisterCallback() {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}
