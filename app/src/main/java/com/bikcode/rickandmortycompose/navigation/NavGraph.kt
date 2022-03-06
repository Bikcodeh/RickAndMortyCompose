package com.bikcode.rickandmortycompose.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bikcode.rickandmortycompose.presentation.screens.detail.DetailScreen
import com.bikcode.rickandmortycompose.presentation.screens.home.HomeScreen
import com.bikcode.rickandmortycompose.presentation.util.Constants.DETAILS_CHARACTER_KEY

@ExperimentalFoundationApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Splash.route) {

        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(DETAILS_CHARACTER_KEY) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt(DETAILS_CHARACTER_KEY)
            DetailScreen(navHostController = navController, characterId = characterId ?: -1)
        }
    }
}