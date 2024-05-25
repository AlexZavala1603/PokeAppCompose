package com.example.pokemonsapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ErrorEntity
import com.example.domain.models.PokemonDetail
import com.example.domain.models.ResultWrapper
import com.example.domain.usecases.GetPokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailedViewModel @Inject constructor(
    private val getPokemonDetail: GetPokemonDetail
): ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        data object Loading : UiModel()
        data class Error(val errorEntity: ErrorEntity) : UiModel()
        data class LoadPokemonDetail(val pokemon: PokemonDetail) : UiModel()
    }

    fun requestPokemonDetail(id: Int) {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            when (val response = getPokemonDetail.invoke(id)) {
                is ResultWrapper.Left -> _model.value = UiModel.Error(response.l)
                is ResultWrapper.Right -> _model.value = UiModel.LoadPokemonDetail(response.r)
            }
        }
    }

}