package com.mk.datingapp.di

import com.mk.datingapp.data.repository.AnalyticsRepositoryImpl
import com.mk.datingapp.domain.repository.AnalyticsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsRepository(
        impl : AnalyticsRepositoryImpl
    ) : AnalyticsRepository
}