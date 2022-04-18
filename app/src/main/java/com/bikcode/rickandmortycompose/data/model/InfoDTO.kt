package com.bikcode.rickandmortycompose.data.model


import com.google.gson.annotations.SerializedName

data class InfoDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: String?
)