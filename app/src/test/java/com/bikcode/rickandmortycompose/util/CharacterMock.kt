package com.bikcode.rickandmortycompose.util

import com.bikcode.rickandmortycompose.data.model.CharacterDTO
import com.bikcode.rickandmortycompose.data.model.LocationDTO
import com.bikcode.rickandmortycompose.data.model.OriginDTO
import com.bikcode.rickandmortycompose.domain.model.Character
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin

val character = Character(
    created = "",
    episode = listOf(),
    gender = "",
    id = 0,
    image = "",
    location = Location(name = "", url = ""),
    name = "Dummy",
    origin = Origin(name = "", url = ""),
    species = "",
    status = "",
    type = "",
    url = ""
)
val characterDTO = CharacterDTO(
    created = "",
    episode = listOf(),
    gender = "",
    id = 0,
    image = "",
    location = LocationDTO(name = "", url = ""),
    name = "Dummy",
    origin = OriginDTO(name = "", url = ""),
    species = "",
    status = "",
    type = "",
    url = ""
)