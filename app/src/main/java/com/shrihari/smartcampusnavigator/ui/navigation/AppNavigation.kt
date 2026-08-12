package com.shrihari.smartcampusnavigator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shrihari.smartcampusnavigator.ui.screens.home.HomeScreen
import com.shrihari.smartcampusnavigator.ui.screens.navigate.NavigateScreen
import com.shrihari.smartcampusnavigator.ui.screens.scan.QrScannerScreen
import com.shrihari.smartcampusnavigator.ui.screens.scan.ScanScreen
import com.shrihari.smartcampusnavigator.ui.screens.settings.SettingsScreen
import com.shrihari.smartcampusnavigator.ui.screens.splash.SplashScreen
import com.shrihari.smartcampusnavigator.ui.theme.ThemeViewModel
import com.shrihari.smartcampusnavigator.ui.viewmodel.HomeViewModel

@Composable
fun AppNavigation(
    themeViewModel: ThemeViewModel,
    deepLinkDestination: String? = null
) {

    val navController = rememberNavController()

// Automatically open the Navigation screen when launched from a Tracer Kiosk QR
    LaunchedEffect(deepLinkDestination) {
        if (deepLinkDestination != null) {
            navController.navigate(
                "navigate?recent=false&destination=$deepLinkDestination"
            ) {
                popUpTo(Screen.Splash.route) { inclusive = false }
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.Home.route) {

            val homeViewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                navController = navController,
                viewModel = homeViewModel
            )
        }

        composable(route = Screen.Scan.route) {
            ScanScreen(
                navController = navController
            )
        }

        composable(route = Screen.QrScanner.route) {
            QrScannerScreen(
                navController = navController
            )
        }

        composable(
            route = "navigate?recent={recent}&destination={destination}",
            arguments = listOf(
                navArgument("recent") {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument("destination") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->

            val recent =
                backStackEntry.arguments?.getBoolean("recent") ?: false

            val destination =
                backStackEntry.arguments?.getString("destination")

            NavigateScreen(
                navController = navController,
                openRecent = recent,
                deepLinkDestination = if (!destination.isNullOrBlank())
                    destination
                else
                    deepLinkDestination
            )
        }

        composable(Screen.Settings.route) {

            SettingsScreen(
                navController = navController,
                themeViewModel = themeViewModel
            )

        }
    }

}
