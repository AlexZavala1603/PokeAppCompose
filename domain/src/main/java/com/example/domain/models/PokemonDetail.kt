package com.example.domain.models

data class PokemonDetail (
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val height: Int,
    val weight: Int,
    val types: List<Types>
)
