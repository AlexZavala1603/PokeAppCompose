package com.example.pokemonsapp.ui.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.domain.models.ErrorEntity
import com.example.domain.utils.getErrorMessage

@Composable
fun ErrorScreen(errorEntity: ErrorEntity) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: ${errorEntity.getErrorMessage()}")
    }
}