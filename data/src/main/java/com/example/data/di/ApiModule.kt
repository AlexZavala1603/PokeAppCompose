package com.example.data.di

import com.example.data.apiservices.PokemonApiService
import com.example.data.datasources.PokemonRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideUsersApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideUsersRemoteDataSource(api: PokemonApiService): PokemonRemoteDataSource {
        return PokemonRemoteDataSource(api)
    }

}