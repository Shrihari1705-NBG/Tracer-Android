package com.shrihari.smartcampusnavigator.ui.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")

    object Home : Screen("home_screen")

    object Scan : Screen("scan_screen")

    object Navigate : Screen("navigate_screen")

    object Settings : Screen("settings_screen")
}