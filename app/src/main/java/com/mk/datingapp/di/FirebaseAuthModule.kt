package com.mk.datingapp.di

import com.mk.datingapp.data.repository.AuthRepositoryImpl
import com.mk.datingapp.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class FirebaseAuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl : AuthRepositoryImpl
    ) : AuthRepository
}