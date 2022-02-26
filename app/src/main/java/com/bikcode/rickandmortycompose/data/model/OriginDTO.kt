package com.bikcode.rickandmortycompose.data.model


import com.bikcode.rickandmortycompose.domain.model.Origin
import com.google.gson.annotations.SerializedName

data class OriginDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun toOriginDomain(): Origin = Origin(name, url)
}