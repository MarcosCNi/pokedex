package com.marcosk.pokedexegsys.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.marcosk.pokedexegsys.R
import com.marcosk.pokedexegsys.databinding.ActivityPokemonListBinding
import com.marcosk.pokedexegsys.model.Pokemon
import com.marcosk.pokedexegsys.ui.recyclerview.adapter.PokemonListAdapter

class PokemonListActivity : AppCompatActivity(R.layout.activity_pokemon_list) {

    val bulbasauto = Pokemon(
        1,
        "1",
        "Bulbasauto",
        "https://pm1.narvii.com/6946/3665e65094aa52212a0d9e10e9c6e56059f3579br1-225-225v2_hq.jpg",
        listOf(
            "assalto"
        ),
        "9",
        "55"
    )
    val pokedex = listOf(
        bulbasauto,
        bulbasauto,
        bulbasauto
    )

    private val binding by lazy {
        ActivityPokemonListBinding.inflate(layoutInflater)
    }

    private val adapter = PokemonListAdapter(
        context = this,
        pokedex = pokedex
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configRecyclerView()
        setContentView(binding.root)
    }

    private fun configRecyclerView() {
        val recyclerView = binding.pokemonListRecyclerView
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = adapter
    }

}