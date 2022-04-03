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
    val game_indices: List<PokemonGameIndices>
)

data class PokemonTypeSlot (
    val slot: Int,
    val type: PokemonType
)

data class PokemonGameIndices(
    val game_index: Int,
    val version: GameVersion
)

data class GameVersion (
    val name: String,
)
