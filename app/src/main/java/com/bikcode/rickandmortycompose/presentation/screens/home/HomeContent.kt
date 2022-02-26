package com.bikcode.rickandmortycompose.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.bikcode.rickandmortycompose.R
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin
import com.bikcode.rickandmortycompose.ui.theme.*

@Composable
fun HomeContent(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val characters = homeViewModel.getAllCharacters.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(
            items = characters,
            key = { character -> character.id }
        ) { character ->
            character?.let {
                CharacterItem(character = character)
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {

    val painterCharacter = rememberImagePainter(data = character.image) {
        Log.d("CHARACTER URL", character.image)
        placeholder(R.drawable.ic_image)
        error(R.drawable.ic_broken_image)
    }
    Box(
        modifier = Modifier.height(CHARACTER_ITEM_HEIGHT),
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
        )
    )
}