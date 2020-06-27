package com.example.posteey.di

import android.content.Context
import com.example.core.Interceptors.AuthInterceptor
import com.example.core.Interceptors.NoInternetConnectionInterceptor
import com.example.posteey.BuildConfig
import com.example.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    private const val BASE_URL = "https://newsapi.org/"

    @Provides
    @Singleton
    fun provideNewsApi(okHttpClient: OkHttpClient): NewsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggerInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        }

        return OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(NoInternetConnectionInterceptor(context))
            .addInterceptor(loggerInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()
    }
}