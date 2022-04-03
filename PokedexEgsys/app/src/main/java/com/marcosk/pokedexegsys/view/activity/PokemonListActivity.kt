package com.marcosk.pokedexegsys.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.marcosk.pokedexegsys.R
import com.marcosk.pokedexegsys.databinding.ActivityPokemonListBinding
import com.marcosk.pokedexegsys.model.Pokemon
import com.marcosk.pokedexegsys.view.activity.recyclerview.adapter.PokemonListAdapter
import com.marcosk.pokedexegsys.view.viewmodel.PokemonViewModel
import com.marcosk.pokedexegsys.view.viewmodel.PokemonViewModelFactory

class PokemonListActivity : AppCompatActivity(R.layout.activity_pokemon_list) {

    private val binding by lazy {
        ActivityPokemonListBinding.inflate(layoutInflater)
    }


    private val viewModel by lazy{
        ViewModelProvider (this, PokemonViewModelFactory ())
            .get (PokemonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configPokemonViewModel()
        setContentView(binding.root)
    }

    private fun configPokemonViewModel() {
        viewModel.pokedex.observe(this, Observer {
            configRecyclerView(it)
        })
    }

    private fun configRecyclerView(pokedex: List<Pokemon?>) {
        val recyclerView = binding.pokemonListRecyclerView
        recyclerView.post{
            recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
            recyclerView.adapter = PokemonListAdapter(context = this, pokedex = pokedex)
        }
    }

}