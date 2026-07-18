package com.shrihari.smartcampusnavigator.di

import com.shrihari.smartcampusnavigator.data.repository.HomeRepositoryImpl
import com.shrihari.smartcampusnavigator.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository
}
