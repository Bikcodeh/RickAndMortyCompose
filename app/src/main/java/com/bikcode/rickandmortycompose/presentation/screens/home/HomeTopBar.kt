package com.bikcode.rickandmortycompose.presentation.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bikcode.rickandmortycompose.R
import com.bikcode.rickandmortycompose.ui.theme.topAppBarBackgroundColor
import com.bikcode.rickandmortycompose.ui.theme.topAppBarContentColor

@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.title_app),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
    )
}

@Composable
@Preview
fun HomeTopBarPreview() {
    HomeTopBar()
}