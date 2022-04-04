package com.marcosk.pokedexegsys.api

import com.marcosk.pokedexegsys.api.model.PokemonApiResult
import com.marcosk.pokedexegsys.api.model.PokemonsApiResult
import com.marcosk.pokedexegsys.util.NetworkUtils.Companion.getRetrofitInstance

object PokeApi {

    private val service: PokemonService

    init {
        val retrofit = getRetrofitInstance("https://pokeapi.co/api/v2/")

        service = retrofit.create(PokemonService::class.java)
    }

    fun listPokemon(limit: Int = 9): PokemonsApiResult? {
        val call = service.listPokemon(limit)
        return call.execute().body()
    }

    fun getPokemon(number: Int): PokemonApiResult? {
        val call = service.getPokemon(number)

        return call.execute().body()
    }
}