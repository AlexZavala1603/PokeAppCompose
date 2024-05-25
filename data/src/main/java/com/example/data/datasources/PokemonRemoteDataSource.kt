package com.example.data.datasources

import com.example.data.apiservices.PokemonApiService
import com.example.data.models.BaseResponse
import com.example.data.models.Pokemon
import com.example.data.models.PokemonDetail

class PokemonRemoteDataSource(
    private val api: PokemonApiService
) {

    suspend fun getPokemonList(offset: Int, limit: Int): BaseResponse<List<Pokemon>> = api.getPokemonList(offset, limit)

    suspend fun getPokemonDetail(id: Int): PokemonDetail = api.getPokemonDetailById(id)

}