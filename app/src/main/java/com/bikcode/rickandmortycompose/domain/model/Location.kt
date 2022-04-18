package com.bikcode.rickandmortycompose.domain.model

import com.bikcode.rickandmortycompose.data.model.LocationDTO

data class Location(
    val name: String,
    val url: String
) {
    fun toLocationDTO(): LocationDTO = LocationDTO(name, url)
}