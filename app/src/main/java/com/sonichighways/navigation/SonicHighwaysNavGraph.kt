package com.sonichighways.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sonichighways.feature.home.presentation.ui.view.home.HomeAdaptiveScreen
import com.sonichighways.splash.SplashScreen


object Destinations {
    const val SPLASH = "splash"
    const val HOME_ADAPTIVE = "home_adaptive"
}

@Composable
fun SonicHighwaysNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SPLASH
    ) {
        composable(Destinations.SPLASH) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Destinations.HOME_ADAPTIVE) {
                        popUpTo(Destinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.HOME_ADAPTIVE) {
            HomeAdaptiveScreen()
        }
    }
}