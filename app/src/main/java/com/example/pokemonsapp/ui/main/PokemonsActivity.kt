package com.example.pokemonsapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokemonsapp.R
import com.example.pokemonsapp.ui.details.PokemonDetailsActivity
import com.example.pokemonsapp.ui.error.ErrorScreen
import com.example.pokemonsapp.ui.loading.LoadingScreen
import com.example.pokemonsapp.ui.main.MainViewModel.UiModel
import com.example.pokemonsapp.ui.theme.PokemonsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonsAppTheme {
                MainScreen(viewModel = viewModel, navigateToPokemonDetails = { pokemonId ->
                    navigateToPokemonDetails(pokemonId)
                })
            }
        }
    }

    private fun navigateToPokemonDetails(pokemonId: Int) {
        val intent = PokemonDetailsActivity.getStartIntent(this, pokemonId)
        startActivity(intent)
    }

}

@Composable
fun MainScreen(viewModel: MainViewModel, navigateToPokemonDetails: (Int) -> Unit) {
    val uiState by viewModel.model.observeAsState()
    val scrollState = rememberLazyStaggeredGridState()
    val buffer = 1

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == scrollState.layoutInfo.totalItemsCount - buffer
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            println("Reached end!!")
            viewModel.requestPokemonList()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is UiModel.Loading -> LoadingScreen()
            is UiModel.Error -> ErrorScreen(errorEntity = (uiState as UiModel.Error).errorEntity)
            is UiModel.LoadPokemonList -> PokemonsScreen(
                uiState as UiModel.LoadPokemonList,
                scrollState
            ) {
                navigateToPokemonDetails(it)
            }

            null -> {}
        }
    }

}

@Composable
fun PokemonsScreen(
    uiState: UiModel,
    scrollState: LazyStaggeredGridState,
    navigateToPokemonDetails: (pokemonId: Int) -> Unit
) {
    val pokemonList = (uiState as UiModel.LoadPokemonList).pokemons

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pokemon_red))
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painterResource(R.drawable.pokeapi_256),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.subtitle_home),
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 10.dp,
            content = {
                items(pokemonList) { pokemon ->
                    PokemonItem(pokemon, navigateToPokemonDetails)
                }
            }
        )
    }

}