package com.bikcode.rickandmortycompose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bikcode.rickandmortycompose.data.model.CharacterDTO

@Entity(tableName = "character")
data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    fun toCharacterDTO(): CharacterDTO =
        CharacterDTO(
            created,
            episode,
            gender,
            id,
            image,
            location.toLocationDTO(),
            name,
            origin.toOriginDTO(),
            species,
            status,
            type,
            url
        )
}