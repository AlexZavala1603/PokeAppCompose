package com.example.pokemonsapp.utils.extensions

import java.util.Locale

fun String.toCapitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}