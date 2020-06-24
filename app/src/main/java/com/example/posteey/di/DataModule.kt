package com.example.posteey.di


import com.example.data.repositories.*
import com.example.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindBusinessNewsRepository(businessNewsRepositoryImpl: BusinessNewsRepositoryImpl): BusinessNewsRepository
    @Binds
    abstract fun bindHealthNewsRepository(healthNewsRepositoryImpl: HealthNewsRepositoryImpl): HealthNewsRepository
    @Binds
    abstract fun bindTechnologyNewsRepository(technologyNewsRepositoryImpl: TechnologyNewsRepositoryImpl): TechnologyNewsRepository
    @Binds
    abstract fun bindEntertainmentNewsRepository(entertainmentNewsRepositoryImpl: EntertainmentNewsRepositoryImpl): EntertainmentNewsRepository
    @Binds
    abstract fun bindScienceNewsRepository(scienceNewsRepositoryImpl: ScienceNewsRepositoryImpl): ScienceNewsRepository
    @Binds
    abstract fun bindSportsNewsRepository(sportsNewsRepositoryImpl: SportsNewsRepositoryImpl): SportsNewsRepository
    @Binds
    abstract fun bindGeneralNewsRepository(generalNewsRepositoryImpl: GeneralNewsRepositoryImpl): GeneralNewsRepository
}
