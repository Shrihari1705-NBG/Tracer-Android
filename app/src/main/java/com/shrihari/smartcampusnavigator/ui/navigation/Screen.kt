package com.shrihari.smartcampusnavigator.ui.navigation

sealed class Screen(val route: String) {


    object Splash : Screen("splash_screen")

    object Home : Screen("home_screen")

    object Scan : Screen("scan_screen")

    object QrScanner : Screen("qr_scanner")

    object Navigate : Screen("navigate?recent={recent}")

    object Settings : Screen("settings_screen")


}
