package com.bikcode.rickandmortycompose.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen() {
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SearchTopBar(text = text, onTextChange = {
                text = it
            })
        }, content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Hola mundo")
            }
        }
    )
}