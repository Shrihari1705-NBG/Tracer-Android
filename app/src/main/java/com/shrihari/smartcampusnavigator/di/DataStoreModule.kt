package com.shrihari.smartcampusnavigator.di

import android.content.Context
import com.shrihari.smartcampusnavigator.data.datastore.SettingsDataStore
import com.shrihari.smartcampusnavigator.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): SettingsDataStore {
        return SettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        dataStore: SettingsDataStore
    ): SettingsRepository {
        return SettingsRepository(dataStore)
    }
}