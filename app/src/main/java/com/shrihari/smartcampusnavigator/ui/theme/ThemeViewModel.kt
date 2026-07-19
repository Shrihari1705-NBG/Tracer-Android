package com.shrihari.smartcampusnavigator.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    var darkThemeEnabled by mutableStateOf(false)
        private set

    init {

        viewModelScope.launch {

            repository.darkThemeEnabled.collectLatest {

                darkThemeEnabled = it

            }

        }

    }

    fun setDarkTheme(enabled: Boolean) {

        darkThemeEnabled = enabled

    }

}