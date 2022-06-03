package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bikcode.rickandmortycompose.R
import com.bikcode.rickandmortycompose.ui.theme.topAppBarBackgroundColor
import com.bikcode.rickandmortycompose.ui.theme.topAppBarContentColor

@Composable
fun HomeTopBar(homeViewModel: HomeViewModel = hiltViewModel()) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.title_app),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_filter),
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            }

            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = {
                    homeViewModel.filterCharacters(status = "all")
                    showMenu = false
                }) {
                    Text(text = stringResource(id = R.string.all_label))
                }
                DropdownMenuItem(onClick = {
                    homeViewModel.filterCharacters(status = "Alive")
                    showMenu = false
                }) {
                    Canvas(modifier = Modifier.size(30.dp)) {
                        drawCircle(
                            color = Color.Green,
                            radius = size.minDimension / 4
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.alive_label),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                DropdownMenuItem(onClick = {
                    homeViewModel.filterCharacters(status = "Dead")
                    showMenu = false
                }) {
                    Canvas(modifier = Modifier.size(30.dp)) {
                        drawCircle(
                            color = Color.Red,
                            radius = size.minDimension / 4
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.dead_label),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}

@Composable
fun Circle() {
    Row {
        Canvas(modifier = Modifier.size(30.dp)) {
            drawCircle(
                color = Color.Green,
                radius = size.minDimension / 4
            )
        }
    }
}

@Preview
@Composable
fun CirclePreview() {
    Circle()
}

@Composable
@Preview
fun HomeTopBarPreview() {
    HomeTopBar()
}