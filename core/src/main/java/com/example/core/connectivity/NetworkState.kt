package com.example.core.connectivity

sealed class NetworkState {
    class NoConnection(val message: String): NetworkState()
    object Connected: NetworkState()
}
