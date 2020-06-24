package com.example.posteey.di

import android.content.Context
import com.example.core.connectivity.ConnectivityCallback
import com.example.core.connectivity.NetworkManager
import com.example.core.connectivity.NetworkRequestFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object CoreModule {

    @Provides
    fun provideConnectivityCallback(@ApplicationContext context: Context): ConnectivityCallback {
        return ConnectivityCallback(context)
    }

    @Provides
    fun provideNetworkManager(@ApplicationContext context: Context,
                              callback: ConnectivityCallback,
                              networkRequestFactory: NetworkRequestFactory): NetworkManager {
        return NetworkManager(context, callback, networkRequestFactory)
    }
}
