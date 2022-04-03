package com.marcosk.pokedexegsys.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.marcosk.pokedexegsys.R
import com.marcosk.pokedexegsys.api.PokeApi
import com.marcosk.pokedexegsys.databinding.ActivityPokemonListBinding
import com.marcosk.pokedexegsys.model.Pokemon
import com.marcosk.pokedexegsys.model.PokemonType
import com.marcosk.pokedexegsys.ui.recyclerview.adapter.PokemonListAdapter

class PokemonListActivity : AppCompatActivity(R.layout.activity_pokemon_list) {

    private val binding by lazy {
        ActivityPokemonListBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread(Runnable {
            loadPokedex()
        }).start()
        setContentView(binding.root)
    }

    private fun loadPokedex(

    ){
        val pokemonsApiResult = PokeApi.listPokemon()
        pokemonsApiResult?.results?.let {
            val pokedex: List<Pokemon?> = it.map { pokemonData ->
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
                        }
                    )
                }

            }

            configRecyclerView(pokedex)

        }
    }

    private fun configRecyclerView(pokedex: List<Pokemon?>) {
        val recyclerView = binding.pokemonListRecyclerView
        recyclerView.post{
            recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
            recyclerView.adapter = PokemonListAdapter(context = this, pokedex = pokedex)
        }
    }

}