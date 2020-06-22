/*
package com.example.posteey.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.viewmodels.*
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BusinessNewsViewModel::class)
    abstract fun bindBusinessNewsViewModel(viewModel: BusinessNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TechnologyNewsViewModel::class)
    abstract fun bindTechnologyNewsViewModel(viewModel: TechnologyNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SportsNewsViewModel::class)
    abstract fun bindSportsNewsViewModel(sportsNewsViewModel: SportsNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScienceNewsViewModel::class)
    abstract fun bindScienceNewsViewModel(scienceNewsViewModel: ScienceNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GeneralNewsViewModel::class)
    abstract fun bindGeneralNewsViewModel(generalNewsViewModel: GeneralNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HealthNewsViewModel::class)
    abstract fun bindHealthNewsViewModel(healthNewsViewModel: HealthNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EntertainmentNewsViewModel::class)
    abstract fun bindEntertainmentNewsViewModel(entertainmentNewsViewModel: EntertainmentNewsViewModel): ViewModel


    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)*/
