package com.bikcode.rickandmortycompose.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bikcode.rickandmortycompose.R
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin
import com.bikcode.rickandmortycompose.ui.theme.CHARACTER_ITEM_HEIGHT
import com.bikcode.rickandmortycompose.ui.theme.MEDIUM_PADDING

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    characterId: Int,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        detailViewModel.getSelectedCharacter(characterId = characterId)
    }
    val characterSelected by detailViewModel.characterSelected.collectAsState()

    characterSelected?.let { DetailContent(character = it) }
}


@Composable
fun DetailContent(character: Character) {
    val modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(horizontal = MEDIUM_PADDING)

    val painterCharacter = rememberImagePainter(data = character.image) {
        placeholder(R.drawable.ic_image)
        error(R.drawable.ic_broken_image)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        Text(
            text = character.name,
            modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING, vertical = MEDIUM_PADDING),
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterCharacter,
            contentDescription = stringResource(id = R.string.character_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(CHARACTER_ITEM_HEIGHT)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            color = MaterialTheme.colors.primary
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING)
            ) {
                Text(
                    text = stringResource(id = R.string.information_label),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.species_label), modifier = Modifier.weight(1f))
            Text(
                text = character.species,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.gender_label), modifier = Modifier.weight(1f))
            Text(text = character.gender, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.status_label), modifier = Modifier.weight(1f))
            Text(text = character.status, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.location_label),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = character.location.name,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.origin_label), modifier = Modifier.weight(1f))
            Text(
                text = character.origin.name,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Divider(
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    DetailContent(
        character = Character(
            created = "",
            episode = listOf(),
            gender = "Male",
            id = 0,
            image = "",
            location = Location(name = "Earth", url = ""),
            name = "Rick Sanchez",
            origin = Origin(name = "Earth", url = ""),
            species = "Human",
            status = "Alive",
            type = "",
            url = ""
        )
    )
}