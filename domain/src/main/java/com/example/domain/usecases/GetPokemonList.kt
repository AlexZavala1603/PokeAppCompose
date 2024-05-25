package com.example.domain.usecases

import com.example.domain.models.ErrorEntity
import com.example.domain.models.Pokemon
import com.example.domain.models.ResultWrapper
import com.example.domain.repositories.PokemonRepository
import javax.inject.Inject

class GetPokemonList @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend fun invoke(page: Int): ResultWrapper<ErrorEntity, List<Pokemon>> = repository.getPokemonList(page)

}