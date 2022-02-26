package com.bikcode.rickandmortycompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bikcode.rickandmortycompose.presentation.screens.home.HomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Splash.route) {

        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navController)
        }
    }
}