package com.bikcode.rickandmortycompose.data.model


import com.bikcode.rickandmortycompose.domain.model.Location
import com.google.gson.annotations.SerializedName

data class LocationDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
){
    fun toLocationDomain(): Location = Location(name, url)
}