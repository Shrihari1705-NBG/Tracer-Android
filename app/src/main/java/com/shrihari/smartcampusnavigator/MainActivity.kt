package com.shrihari.smartcampusnavigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shrihari.smartcampusnavigator.ui.navigation.AppNavigation
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shrihari.smartcampusnavigator.ui.theme.ThemeViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()

            TracerTheme(
                darkTheme = themeViewModel.darkThemeEnabled
            ) {
                AppNavigation(
                    themeViewModel = themeViewModel
                )
            }

        }
    }
}