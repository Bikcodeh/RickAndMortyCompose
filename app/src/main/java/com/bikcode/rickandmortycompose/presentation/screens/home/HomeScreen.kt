package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems

@ExperimentalFoundationApi
@Composable
fun HomeScreen(navHostController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {

    val characters = homeViewModel.getAllCharacters.collectAsLazyPagingItems()
    val state = rememberLazyListState()

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            HomeContent(navHostController = navHostController, characters = characters, state = state)
        }
    )
}