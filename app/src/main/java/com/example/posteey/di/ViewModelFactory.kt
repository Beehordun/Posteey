package com.example.posteey.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

typealias ViewModelMap = MutableMap<Class<out ViewModel>, Provider<ViewModel>>
class ViewModelFactory @Inject constructor(
    private val viewModels: ViewModelMap
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as T
    }
}
