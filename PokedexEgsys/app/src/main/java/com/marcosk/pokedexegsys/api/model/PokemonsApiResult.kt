package com.marcosk.pokedexegsys.api.model

import com.marcosk.pokedexegsys.model.PokemonType

data class PokemonsApiResult (
    val count: Int,
    val next: String?,
    val previus: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonApiResult (
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeSlot>,
)

data class PokemonTypeSlot (
    val slot: Int,
    val type: PokemonType
)
