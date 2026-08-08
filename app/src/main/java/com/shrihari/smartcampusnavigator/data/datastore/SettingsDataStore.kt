package com.shrihari.smartcampusnavigator.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SETTINGS_NAME = "tracer_settings"

private val Context.dataStore by preferencesDataStore(
    name = SETTINGS_NAME
)

class SettingsDataStore(
    private val context: Context
) {

    companion object {

        val DARK_THEME =
            booleanPreferencesKey("dark_theme")

        val RECENT_DESTINATION =
            stringPreferencesKey("recent_destination")
    }

    val darkThemeEnabled: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[DARK_THEME] ?: false
        }

    val recentDestination: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[RECENT_DESTINATION] ?: "No recent destination"
        }

    suspend fun setDarkTheme(enabled: Boolean) {

        context.dataStore.edit { preferences ->

            preferences[DARK_THEME] = enabled

        }
    }

    suspend fun setRecentDestination(destination: String) {

        context.dataStore.edit { preferences ->

            preferences[RECENT_DESTINATION] = destination

        }
    }
}