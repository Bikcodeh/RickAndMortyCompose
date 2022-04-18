package com.bikcode.rickandmortycompose.presentation.screens.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bikcode.rickandmortycompose.R
import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.data.model.EpisodeDTO
import com.bikcode.rickandmortycompose.data.model.LocationDTO
import com.bikcode.rickandmortycompose.data.model.OriginDTO
import com.bikcode.rickandmortycompose.ui.theme.*

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    characterId: Int,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val state = detailViewModel.state

    LaunchedEffect(key1 = true) {
        detailViewModel.getSelectedCharacter(characterId = characterId)
    }
    val characterSelected by detailViewModel.characterSelected.collectAsState()

    LaunchedEffect(key1 = characterSelected) {
        characterSelected?.let {
            if(state.episodes.isEmpty())
                detailViewModel.getEpisodes(it.episode)
        }
    }

    characterSelected?.let {
        DetailContent(character = it, state, onCloseClicked = {
            navHostController.popBackStack()
        })
    }
}


@Composable
fun DetailContent(character: CharacterDTO, state: DetailState, onCloseClicked: () -> Unit) {
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
    ) {
        LazyColumn() {
            item {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MEDIUM_PADDING, vertical = MEDIUM_PADDING)
                ) {
                    val (characterName, closeButton) = createRefs()
                    Text(
                        text = character.name,
                        modifier = Modifier.constrainAs(characterName) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.textColor
                    )
                    IconButton(
                        modifier = Modifier
                            .constrainAs(closeButton) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            },
                        onClick = { onCloseClicked() }) {
                        Icon(
                            modifier = Modifier.size(INFO_ICON_SIZE),
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close_icon),
                            tint = MaterialTheme.colors.textColor
                        )
                    }
                }
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
                    color = MaterialTheme.colors.topAppBarBackgroundColor
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING)
                    ) {
                        Text(
                            text = stringResource(id = R.string.information_label),
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.LightGray
                        )
                    }
                }
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_biotech),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.species_label),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = character.species,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.textColor
                    )
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 16.dp)
                )
                Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(
                            id = if (character.gender == "Male") R.drawable.ic_gender_male else R.drawable.ic_gender_female
                        ), contentDescription = null,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.gender_label),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = character.gender,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.textColor
                    )
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 16.dp)
                )
                Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_status),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.status_label),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = character.status,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.textColor
                    )
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 16.dp)
                )
                Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_map_black),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.location_label),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = character.location.name,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.textColor
                    )
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 16.dp)
                )
                Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_origin),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.origin_label),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.textColor
                    )
                    Text(
                        text = character.origin.name,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.textColor
                    )
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    color = MaterialTheme.colors.topAppBarBackgroundColor
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING)
                    ) {
                        Text(
                            text = stringResource(id = R.string.episodes_label),
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.LightGray
                        )
                    }
                }
            }
            if (state.isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(state.episodes, key = { it.name }) { episode ->
                    EpisodeItem(episodeDTO = episode)
                }
            }
        }
    }
}

@Composable
fun EpisodeItem(episodeDTO: EpisodeDTO) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
        shape = Shapes.medium,
        elevation = 8.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        backgroundColor = MaterialTheme.colors.cardBackgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = episodeDTO.name, modifier = Modifier,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                color = MaterialTheme.colors.textColor
            )
            Text(
                text = episodeDTO.airDate,
                modifier = Modifier,
                fontSize = MaterialTheme.typography.caption.fontSize,
                color = MaterialTheme.colors.textColor
            )
            Text(
                text = episodeDTO.episode,
                modifier = Modifier.fillMaxWidth(),
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                textAlign = TextAlign.End,
                color = MaterialTheme.colors.textColor
            )
        }
    }
}

@Preview
@Composable
fun EpisodeItemPreview() {
    EpisodeItem(
        episodeDTO = EpisodeDTO(
            "Welcome to Racon City",
            "1995/09/14",
            "S1E1"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    DetailContent(
        character = CharacterDTO(
            created = "",
            episode = listOf(),
            gender = "Male",
            id = 0,
            image = "",
            location = LocationDTO(name = "Earth", url = ""),
            name = "Rick Sanchez",
            origin = OriginDTO(name = "Earth", url = ""),
            species = "Human",
            status = "Alive",
            type = "",
            url = ""
        ),
        onCloseClicked = {},
        state = DetailState()
    )
}