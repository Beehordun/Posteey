/*
package com.example.posteey.di

import android.app.Application
import com.example.posteey.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CacheModule::class, DataModule::class,
        NetworkModule::class, RemoteModule::class, RoomModule::class, ViewModelModule::class]
)
interface AppComponent {

    val application: Application
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}*/
