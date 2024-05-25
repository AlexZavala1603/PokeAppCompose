package com.example.data.repositories

import com.example.data.datasources.PokemonRemoteDataSource
import com.example.data.mappers.toDomainPokemonDetail
import com.example.data.mappers.toDomainPokemonList
import com.example.domain.models.ErrorEntity
import com.example.domain.models.Pokemon
import com.example.domain.models.PokemonDetail
import com.example.domain.models.ResultWrapper
import com.example.domain.repositories.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
): PokemonRepository {

    override suspend fun getPokemonList(page: Int): ResultWrapper<ErrorEntity, List<Pokemon>> {
        return try {
            val limit = 25
            val offset = page * limit
            val response = pokemonRemoteDataSource.getPokemonList(offset, limit).data?.toDomainPokemonList()
            if (response != null) {
                ResultWrapper.Right(response)
            } else {
                ResultWrapper.Left(ErrorEntity.ApiError.ResponseNull)
            }
        } catch (throwable: Throwable) {
            val errorMessage = throwable.message.toString()
            ResultWrapper.Left(ErrorEntity.ApiError.BadRequest(errorMessage))
        }
    }

    override suspend fun getPokemonDetail(id: Int): ResultWrapper<ErrorEntity, PokemonDetail> {
        return try {
            val response = pokemonRemoteDataSource.getPokemonDetail(id).toDomainPokemonDetail()
            ResultWrapper.Right(response)
        } catch (throwable: Throwable) {
            val errorMessage = throwable.message.toString()
            ResultWrapper.Left(ErrorEntity.ApiError.BadRequest(errorMessage))
        }
    }

}