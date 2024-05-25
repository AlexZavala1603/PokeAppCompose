package com.example.data.utils

fun String.getPokemonId(): String {
    val regex = "/(\\d+)/$".toRegex()
    val matchResult = regex.find(this)
    val id = matchResult?.groupValues?.get(1)
    val numericId = id?.toIntOrNull()
    return numericId?.toString() ?: "0"
}