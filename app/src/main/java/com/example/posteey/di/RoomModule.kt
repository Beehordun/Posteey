package com.example.posteey.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.biodun.cache.db.DbConstants
import com.biodun.cache.db.NewsResultDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideNewsResultDatabase(@ApplicationContext application: Context): NewsResultDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            NewsResultDatabase::class.java,
            DbConstants.DATABASE_NAME
        ).build()
    }
}