package com.shrihari.smartcampusnavigator.data.repository

import com.shrihari.smartcampusnavigator.data.datastore.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: SettingsDataStore
) {

    val darkThemeEnabled: Flow<Boolean> =
        dataStore.darkThemeEnabled

    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.setDarkTheme(enabled)
    }

}