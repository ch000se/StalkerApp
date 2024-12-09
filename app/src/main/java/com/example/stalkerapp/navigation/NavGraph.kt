package com.example.stalkerapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.example.stalkerapp.presentation.screen.details.DetailsScreen
import com.example.stalkerapp.presentation.screen.home.HomeScreen
import com.example.stalkerapp.presentation.screen.search.SearchScreen
import com.example.stalkerapp.presentation.screen.splash.SplashScreen
import com.example.stalkerapp.presentation.screen.welcome.WelcomeScreen
import com.example.stalkerapp.util.Constants.DETAILS_ARGUMENT_KEY

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun SetupNevGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Details.route, arguments =  listOf(navArgument(DETAILS_ARGUMENT_KEY) { type = NavType.IntType })) {
            DetailsScreen(navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController)
        }
    }
}