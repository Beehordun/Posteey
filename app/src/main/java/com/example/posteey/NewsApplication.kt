package com.example.posteey

import android.app.Application

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication: Application() {
/*
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }*/
}
