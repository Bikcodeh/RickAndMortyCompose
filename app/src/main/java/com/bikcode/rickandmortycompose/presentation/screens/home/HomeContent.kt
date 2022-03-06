package com.bikcode.rickandmortycompose.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.bikcode.rickandmortycompose.R
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin
import com.bikcode.rickandmortycompose.navigation.Screen
import com.bikcode.rickandmortycompose.ui.theme.*

@ExperimentalFoundationApi
@Composable
fun HomeContent(
    navHostController: NavHostController,
    characters: LazyPagingItems<Character>,
    state: LazyListState
) {
    val result = handlePagingResult(characters = characters)
    var isLoading by remember { mutableStateOf(false) }
    isLoading = characters.loadState.append is LoadState.Loading

    if (characters.loadState.refresh is LoadState.Loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }

    Box(contentAlignment = Alignment.BottomStart) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = SMALL_PADDING),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
                state = state
            ) {
                items(
                    items = characters,
                    key = { character -> character.id }
                ) { character: Character? ->
                    character?.let {
                        CharacterItem(
                            character = character,
                            navHostController = navHostController
                        )
                    }
                }
                if (result) {
                    item {
                        ErrorMoreRetryItem(isVisible = true) {
                            characters.retry()
                        }
                    }
                }
            }
        }
        LoadingItem(isVisible = isLoading)
    }
}

@Composable
fun handlePagingResult(
    characters: LazyPagingItems<Character>
): Boolean {
    characters.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return error != null
    }
}

@Composable
fun CharacterItem(
    character: Character,
    navHostController: NavHostController
) {

    val painterCharacter = rememberImagePainter(data = character.image) {
        placeholder(R.drawable.ic_image)
        error(R.drawable.ic_broken_image)
    }
    Box(
        modifier = Modifier
            .height(CHARACTER_ITEM_HEIGHT)
            .clickable {
                navHostController.navigate(
                    Screen.Detail.passCharacterId(character.id)
                )
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterCharacter,
                contentDescription = stringResource(id = R.string.character_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = character.name, modifier = Modifier
                        .padding(horizontal = LARGE_PADDING)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.topAppBarContentColor
                )
            }
        }
    }
}

@Composable
fun LoadingItem(isVisible: Boolean) {
    if (isVisible) {
        Row(
            modifier = Modifier
                .height(LOADING_ITEM_HEIGHT)
                .fillMaxWidth()
                .padding(LOADING_ITEM_PADDING),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(LOADING_ITEM_PROGRESS_SIZE),
                strokeWidth = LOADING_ITEM_STROKE_WIDTH
            )
            Text(
                text = stringResource(id = R.string.loading),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = LOADING_ITEM_TEXT_PADDING),
                fontSize = LOADING_ITEM_TEXT_SIZE,
            )
        }
    }
}

@Composable
fun ErrorMoreRetryItem(isVisible: Boolean = false, retry: () -> Unit) {
    if (isVisible) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = { retry() },
                modifier = Modifier
                    .padding(LARGE_PADDING)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(3.dp),
                colors = textButtonColors(backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor),
            ) {
                Text(text = stringResource(id = R.string.try_again), color = MaterialTheme.colors.topAppBarContentColor)
            }
        }
    }
}

@Preview
@Composable
fun LoadingItemPreview() {
    LoadingItem(isVisible = true)
}

@Preview
@Composable
fun Preview() {
    ErrorMoreRetryItem(retry = {}, isVisible = true)
}

@Composable
@Preview
fun CharacterItemPreview() {
    CharacterItem(
        character = Character(
            created = "",
            episode = listOf(),
            gender = "",
            id = 0,
            image = "",
            location = Location(name = "", url = ""),
            name = "Rick Sanchez",
            origin = Origin(name = "", url = ""),
            species = "",
            status = "",
            type = "",
            url = ""
        ),
        navHostController = rememberNavController()
    )
}