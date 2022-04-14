package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bikcode.rickandmortycompose.navigation.Screen

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            HomeTopBar(onSearchClicked = {
                navHostController.navigate(Screen.Search.route)
            })
        },
        content = {
            HomeContent(
                navHostController = navHostController
            )
        }
    )
}