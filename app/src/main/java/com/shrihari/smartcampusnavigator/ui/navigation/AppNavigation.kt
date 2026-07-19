package com.shrihari.smartcampusnavigator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shrihari.smartcampusnavigator.ui.screens.home.HomeScreen
import com.shrihari.smartcampusnavigator.ui.screens.splash.SplashScreen
import com.shrihari.smartcampusnavigator.ui.viewmodel.HomeViewModel
import com.shrihari.smartcampusnavigator.ui.screens.scan.ScanScreen
import com.shrihari.smartcampusnavigator.ui.screens.navigate.NavigateScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

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
        composable(route = Screen.Navigate.route) {
            NavigateScreen(
                navController = navController
            )
        }
    }
}
