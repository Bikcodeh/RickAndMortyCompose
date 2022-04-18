package com.bikcode.rickandmortycompose.domain.model

import com.bikcode.rickandmortycompose.data.model.OriginDTO

data class Origin(
    val name: String,
    val url: String
) {
    fun toOriginDTO(): OriginDTO = OriginDTO(name, url)
}
