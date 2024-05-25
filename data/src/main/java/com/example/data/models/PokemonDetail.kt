package com.example.data.models

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("types") val types: List<Types>,
)
