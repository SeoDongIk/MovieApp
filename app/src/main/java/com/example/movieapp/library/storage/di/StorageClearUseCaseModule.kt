package com.example.movieapp.library.storage.di

import com.example.movieapp.library.storage.usecases.IStorageClearUseCase
import com.example.movieapp.library.storage.usecases.StorageClearUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageClearUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindStorageClearUseCase(
        storageClearUseCase: StorageClearUseCase
    ) : IStorageClearUseCase

}