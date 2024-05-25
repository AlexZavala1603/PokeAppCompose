package com.example.data.di

import com.example.data.datasources.PokemonRemoteDataSource
import com.example.data.repositories.PokemonRepositoryImpl
import com.example.domain.repositories.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun providePokemonRemoteRepositoryImpl(
        remoteDataSource: PokemonRemoteDataSource,
    ): PokemonRepository {
        return PokemonRepositoryImpl(remoteDataSource)
    }

}