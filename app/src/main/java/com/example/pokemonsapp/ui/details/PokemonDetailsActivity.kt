package com.example.pokemonsapp.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.domain.models.PokemonDetail
import com.example.domain.models.Types
import com.example.pokemonsapp.R
import com.example.pokemonsapp.ui.details.PokemonDetailedViewModel.UiModel
import com.example.pokemonsapp.ui.error.ErrorScreen
import com.example.pokemonsapp.ui.loading.LoadingScreen
import com.example.pokemonsapp.ui.theme.PokemonsAppTheme
import com.example.pokemonsapp.utils.extensions.toCapitalize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsActivity : ComponentActivity() {
    private val viewModel: PokemonDetailedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pokemonId = intent.getIntExtra(POKEMON_ID, NOT_VALUE)
        setContent {
            PokemonsAppTheme {
                PokemonDetailedScreen(pokemonId, viewModel) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    companion object {
        private const val POKEMON_ID = "POKEMON_ID"
        private const val NOT_VALUE = -1

        fun getStartIntent(mContext: Context, pokemonId: Int): Intent {
            return Intent(mContext, PokemonDetailsActivity::class.java)
                .putExtra(POKEMON_ID, pokemonId)
        }
    }

}

@Composable
fun PokemonDetailedScreen(
    pokemonId: Int,
    viewModel: PokemonDetailedViewModel,
    onBackClicked: () -> Unit
) {
    val uiState by viewModel.model.observeAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.requestPokemonDetail(pokemonId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is UiModel.Loading -> LoadingScreen()
            is UiModel.Error -> ErrorScreen(errorEntity = (uiState as UiModel.Error).errorEntity)
            is UiModel.LoadPokemonDetail -> PokemonDetailScreen(
                pokemon = (uiState as UiModel.LoadPokemonDetail).pokemon,
                onBackClicked
            )

            null -> {}
        }
    }
}

@Composable
fun PokemonDetailScreen(pokemon: PokemonDetail, onBackClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .background(colorResource(id = R.color.pokemon_red))
        ) {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = stringResource(id = R.string.title_activity_pokemon_details),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(pokemon.sprites.frontDefault.toString()),
                contentDescription = null,
                modifier = Modifier.size(160.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pokemon.name.toCapitalize(),
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Altura: ${pokemon.height} m",
            style = TextStyle(fontSize = 18.sp),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Peso: ${pokemon.weight} kg",
            style = TextStyle(fontSize = 18.sp),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tipos: ${getAllTypes(pokemon.types)}",
            style = TextStyle(fontSize = 18.sp),
            color = Color.Gray
        )
    }
}

@Composable
private fun getAllTypes(types: List<Types>): String {
    return buildString {
        types.forEachIndexed { index, type ->
            if (index > 0) append(", ")
            append(type.type.name)
        }
    }
}