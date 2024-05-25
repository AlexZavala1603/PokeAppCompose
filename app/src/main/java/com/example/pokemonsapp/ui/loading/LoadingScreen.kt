package com.example.pokemonsapp.ui.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.pokemonsapp.R

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pokemon_red)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}