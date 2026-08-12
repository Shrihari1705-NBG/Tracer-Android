package com.shrihari.smartcampusnavigator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.shrihari.smartcampusnavigator.ui.navigation.AppNavigation
import com.shrihari.smartcampusnavigator.ui.theme.ThemeViewModel
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var deepLinkDestination by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle QR / deep link if the app was launched from Tracer Kiosk
        handleDeepLink(intent)

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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {

        val data: Uri = intent?.data ?: return

        if (data.scheme == "tracer" && data.host == "navigate") {

            val destination = data.getQueryParameter("destination")
            val start = data.getQueryParameter("start")
            val floor = data.getQueryParameter("floor")
            val version = data.getQueryParameter("version")

            Log.d(
                "TracerDeepLink",
                "Destination=$destination, Start=$start, Floor=$floor, Version=$version"
            )

            deepLinkDestination = destination
        }
    }

}
