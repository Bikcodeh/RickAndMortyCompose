package com.bikcode.rickandmortycompose.data.model


import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("info")
    val info: InfoDTO,
    @SerializedName("results")
    val results: List<CharacterDTO>
)