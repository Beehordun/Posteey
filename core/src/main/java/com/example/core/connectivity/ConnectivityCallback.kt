package com.example.core.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.R
import javax.inject.Inject

class ConnectivityCallback (
    private val context: Context
): ConnectivityManager.NetworkCallback() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    override fun onUnavailable() {
        super.onUnavailable()
        _networkState.postValue(NetworkState.NoConnection(context.getString(R.string.no_internet_msg)))
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        _networkState.postValue(NetworkState.Connected)
    }
}
