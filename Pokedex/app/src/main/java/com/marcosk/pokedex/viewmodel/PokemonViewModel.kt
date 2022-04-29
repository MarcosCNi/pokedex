package com.marcosk.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcosk.pokedex.api.PokeApi
import com.marcosk.pokedex.model.Pokemon

class PokemonViewModel : ViewModel(){
    var pokedex = MutableLiveData<List<Pokemon?>>()

    init {
        Thread {
            loadPokedex()
        }.start()
    }

    private fun loadPokedex(

    ){
        val pokemonsApiResult = PokeApi.listPokemon()
        pokemonsApiResult?.results?.let {
            pokedex.postValue (it.map { pokemonData ->
                val number = pokemonData.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/","").toInt()
                val pokemonApiResult = PokeApi.getPokemon(number)

                pokemonApiResult?.let {
                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonData.url,
                        pokemonApiResult.types.map { type ->
                            type.type
                        },
                        pokemonApiResult.height,
                        pokemonApiResult.weight,
                    )

                }
            })
        }
    }
}