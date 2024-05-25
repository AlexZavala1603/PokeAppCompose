package com.example.data.apiservices

import com.example.data.models.BaseResponse
import com.example.data.models.Pokemon
import com.example.data.models.PokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): BaseResponse<List<Pokemon>>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetailById(
        @Path("id") id: Int
    ): PokemonDetail
}