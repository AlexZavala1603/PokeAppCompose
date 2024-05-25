package com.example.data.mappers

import com.example.data.BuildConfig
import com.example.data.utils.getPokemonId
import com.example.domain.models.Types as DomainTypes
import com.example.domain.models.TypeInfo as DomainTypeInfo
import com.example.domain.models.Sprites as DomainSprites
import com.example.domain.models.Pokemon as DomainPokemon
import com.example.domain.models.PokemonDetail as DomainPokemonDetail
import com.example.data.models.Types as ServerTypes
import com.example.data.models.TypeInfo as ServerTypeInfo
import com.example.data.models.Sprites as ServerSprites
import com.example.data.models.Pokemon as ServerPokemon
import com.example.data.models.PokemonDetail as ServerPokemonDetail

fun List<ServerPokemon>.toDomainPokemonList(): List<DomainPokemon> {
    return this.map {
        DomainPokemon(
            id = it.url?.getPokemonId()?.toInt(),
            name = it.name,
            url = it.url,
            image = BuildConfig.API_MEDIA_URL + it.url?.getPokemonId() + ".png"
        )
    }
}

fun ServerPokemonDetail.toDomainPokemonDetail(): DomainPokemonDetail {
    return DomainPokemonDetail(
        id = this.id,
        name = this.name,
        sprites = this.sprites.toDomainSprites(),
        height = this.height,
        weight = this.weight,
        types = this.types.toDomainTypes()
    )
}

fun ServerSprites.toDomainSprites(): DomainSprites {
    return DomainSprites(
        backDefault = this.backDefault,
        backFemale = this.backFemale,
        backShiny = this.backShiny,
        backShinyFemale = this.backShinyFemale,
        frontDefault = this.frontDefault,
        frontFemale = this.frontFemale,
        frontShiny = this.frontShiny,
        frontShinyFemale = this.frontShinyFemale
    )
}

fun List<ServerTypes>.toDomainTypes(): List<DomainTypes> {
    return this.map {
        DomainTypes(
            slot = it.slot,
            type = it.type.toDomainTypeInfo()
        )
    }
}

fun ServerTypeInfo.toDomainTypeInfo(): DomainTypeInfo {
    return DomainTypeInfo(
        name = this.name,
        url = this.url
    )
}
