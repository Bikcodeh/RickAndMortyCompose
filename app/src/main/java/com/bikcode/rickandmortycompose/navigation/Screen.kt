package com.bikcode.rickandmortycompose.navigation

sealed class Screen(
    val route: String
) {
    object Splash: Screen(Screens.SPLASH)
    object Home: Screen(Screens.HOME)
    object Detail: Screen(Screens.DETAIL) {
        fun passCharacterId(characterId: Int): String {
            return "detail_screen/$characterId"
        }
    }
}