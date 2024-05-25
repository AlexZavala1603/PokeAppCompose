package com.example.pokemonsapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.models.Pokemon
import com.example.pokemonsapp.R
import com.example.pokemonsapp.utils.extensions.toCapitalize

@Composable
fun PokemonItem(pokemon: Pokemon, navigateToPokemonDetails: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { pokemon.id?.let { navigateToPokemonDetails(it) } },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PokemonImageFromURLWithPlaceHolder(pokemon.image.toString())

            Text(
                text = pokemon.name.toString().toCapitalize(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun PokemonImageFromURLWithPlaceHolder(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.img_placeholder),
        error = painterResource(R.drawable.error_image_placeholder),
        contentDescription = stringResource(R.string.app_name),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(96.dp)
            .height(96.dp)
            .clip(RectangleShape)
    )
}