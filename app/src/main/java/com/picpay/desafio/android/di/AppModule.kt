package com.picpay.desafio.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.ContactRepository
import use_case.GetContactUseCase
import use_case.GetContactUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesGetContactUseCase(repository: ContactRepository): GetContactUseCase {
        return GetContactUseCaseImpl(repository)
    }
}