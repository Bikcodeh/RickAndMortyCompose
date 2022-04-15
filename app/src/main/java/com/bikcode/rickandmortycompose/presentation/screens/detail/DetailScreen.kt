package com.bikcode.rickandmortycompose.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.bikcode.rickandmortycompose.data.model.LocationDTO
import com.bikcode.rickandmortycompose.data.model.OriginDTO
import com.bikcode.rickandmortycompose.ui.theme.*

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

    characterSelected?.let {
        DetailContent(character = it, onCloseClicked = {
            navHostController.popBackStack()
        })
    }
}


@Composable
fun DetailContent(character: CharacterDTO, onCloseClicked: () -> Unit) {
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
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
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
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
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
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
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
                .padding(horizontal = 8.dp)
        )
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
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
        onCloseClicked = {}
    )
}