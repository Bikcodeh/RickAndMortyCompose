package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@ExperimentalFoundationApi
@Composable
fun HomeScreen(navHostController: NavHostController) {

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            HomeContent(navHostController = navHostController)
        }
    )
}