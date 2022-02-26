package com.bikcode.rickandmortycompose.navigation

sealed class Screen(
    val route: String
) {
    object Splash: Screen(Screens.SPLASH)
    object Home: Screen(Screens.HOME)
}