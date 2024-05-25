package com.example.pokemonsapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ErrorEntity
import com.example.domain.models.Pokemon
import com.example.domain.models.ResultWrapper
import com.example.domain.usecases.GetPokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    private val _pokemons = MutableStateFlow<MutableList<Pokemon>>(mutableListOf())

    private var page = 1

    sealed class UiModel {
        data object Loading : UiModel()
        data class Error(val errorEntity: ErrorEntity) : UiModel()
        data class LoadPokemonList(val pokemons: List<Pokemon>) : UiModel()
    }

    init {
        requestPokemonList()
    }

    fun requestPokemonList() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            when (val response = getPokemonList.invoke(page)) {
                is ResultWrapper.Left -> _model.value = UiModel.Error(response.l)
                is ResultWrapper.Right -> {
                    _pokemons.value.addAll(response.r)
                    _model.value = UiModel.LoadPokemonList(_pokemons.value)
                    page++
                }
            }
        }
    }

}